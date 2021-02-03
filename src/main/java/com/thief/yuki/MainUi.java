package com.thief.yuki;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.componentsList.components.ScrollablePanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.JBUI;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jetbrains.annotations.NotNull;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainUi implements ToolWindowFactory, DumbAware {

    private PersistentState persistentState = PersistentState.getInstance();
    /**
     * ��ʾ��ҳ��
     **/
    private JLabel total = new JLabel();

    private JBScrollPane jbScrollPanel;

    /**
     * ��ǰ�ļ���ҳ��
     **/
    private int totalPage = 0;

    /**
     * ��ǰ�ļ���ҳ��
     **/
    private int alterFileTotalPage = 0;


    /**
     * ��ǰ�����Ķ�ҳ��
     **/
    private int currentPage = 0;
    /**
     * �滻�����Ķ�ҳ��
     **/
    private int alterCurrentPage = 0;

    /**
     * �Ƿ����ؽ���
     **/
    private boolean hide = false;

    private PDFRenderer pdfRenderer;

    private PDFRenderer alterRenderer;

    boolean isTransparent = true;
    int filterRgb = 0XFFFFFF;

    private JTextField current;

    /**
     * ��ǰ�Ƿ��滻�ļ�
     **/
    private boolean isAlter = false;
    /**
     * false;����
     * ���ص�ǰչʾ��չʾ�滻�ļ�
     **/
    private boolean isShowFlag = false;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        try {
            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
            Content content = contentFactory.createContent(initPanel(), "TerminalX", false);
            toolWindow.getContentManager().addContent(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ʼ���������
     *
     * @return
     */
    private JPanel initPanel() throws IOException {
        BufferedImage image = initPdfFile();
        isShowFlag = "1".equals(persistentState.getShowFlag());
        isTransparent = "1".equals(persistentState.getTransparented());
        if (StringUtils.isNotEmpty(persistentState.getAlterCurrentPage())) {
            alterCurrentPage = Integer.parseInt(persistentState.getAlterCurrentPage());
        }
        String transparentedRgb = persistentState.getTransparentedRgb().trim();
        if (transparentedRgb != null && transparentedRgb.length() > 1) {
            String tmp = transparentedRgb.substring(1);
            filterRgb = Integer.parseInt(tmp, 16);
        }
        Mypanel mypanel = getMypanel(image, isTransparent, filterRgb);
        initJbScrollPanel(image.getWidth(), image.getHeight(), mypanel);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(jbScrollPanel, BorderLayout.CENTER);
        panel.add(initOperationPanel(), BorderLayout.EAST);
        // ע��Ӧ�ó���ȫ�ּ����¼�, ���еļ����¼����ᱻ���¼�����������.

        return panel;
    }

    private void initJbScrollPanel(int width, int height, Mypanel mypanel) {
        jbScrollPanel = new JBScrollPane(mypanel) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        jbScrollPanel.setViewportView(mypanel);
    }

    @NotNull
    private Mypanel getMypanel(BufferedImage image, boolean isTransparent, int filterRgb) {
        Mypanel mypanel = new Mypanel(image, isTransparent, filterRgb);
        mypanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        mypanel.setLayout(new BorderLayout());
        mypanel.add(new ScrollablePanel());
        mypanel.setSize(1080, 100);
        return mypanel;
    }

    private BufferedImage initPdfFile() throws IOException {
        String pdfPathText = persistentState.getPdfPathText();
        File pdfFile = new File(pdfPathText);
        PDDocument document = PDDocument.load(pdfFile);
        pdfRenderer = new PDFRenderer(document);
        totalPage = document.getPages().getCount();
        if (StringUtils.isNotEmpty(persistentState.getCurrentPage())) {
            currentPage = Integer.parseInt(persistentState.getCurrentPage());
        }
        BufferedImage image = pdfRenderer.renderImage(currentPage);
        return image;
    }

    private void changePage(int index, boolean isAlter) throws IOException {

        BufferedImage image;
        if (isAlter) {
            if (null == alterRenderer) {
                initAlterRenderer();
            }
            image = alterRenderer.renderImage(index);
            alterCurrentPage = index;
            if (index < 0) {
                index = 0;
            } else if (index > alterFileTotalPage) {
                index = alterFileTotalPage;
            }
            total.setText("/" + alterFileTotalPage);
        } else {
            if (index < 0) {
                index = 0;
            } else if (index > totalPage) {
                index = totalPage;
            }
            image = pdfRenderer.renderImage(index);
            currentPage = index;
            total.setText("/" + totalPage);
        }
        Mypanel mypane = new Mypanel(image, isTransparent, filterRgb);
        mypane.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        jbScrollPanel.setViewportView(mypane);
        persistentState.setCurrentPage(String.valueOf(currentPage));
        persistentState.setAlterCurrentPage(String.valueOf(alterCurrentPage));
        current.setText(String.valueOf(index + 1));
    }

    private void initAlterRenderer() throws IOException {
        if (alterRenderer != null) {
            return;
        }
        File pdfFile = new File(persistentState.getAlterPathText());
        PDDocument document = PDDocument.load(pdfFile);
        alterRenderer = new PDFRenderer(document);
        alterFileTotalPage = document.getPages().getCount();
        if (StringUtils.isNotEmpty(persistentState.getCurrentPage())) {
            alterCurrentPage = Integer.parseInt(persistentState.getCurrentPage());
        }
    }


    /**
     * ��ʼ���������
     **/
    private JPanel initOperationPanel() {
        isShowFlag = "1".equals(persistentState.getShowFlag());
        // ������
        total.setText("/" + totalPage);
        JPanel panelRight = new JPanel();
        panelRight.setBorder(JBUI.Borders.empty(0, 20));
        panelRight.setPreferredSize(new Dimension(280, 30));
        //��ҳ
        current = initTextField();
        panelRight.add(current, BorderLayout.EAST);
        panelRight.add(total, BorderLayout.EAST);
        //���ذ�ť
        JButton fresh = initFreshButton();
        panelRight.add(fresh, BorderLayout.EAST);
        //��һҳ
        JButton up = initUpButton();
        panelRight.add(up, BorderLayout.EAST);
        //��һҳ
        JButton down = initDownButton();
        panelRight.add(down, BorderLayout.EAST);
        //�ϰ��
//        JButton boss = initBossButton(new JButton[]{fresh, up, down});
//        panelRight.add(boss, BorderLayout.SOUTH);
        JButton[] buttons = {fresh, up, down};
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyTyped(NativeKeyEvent e) {

//                    System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                }

                @Override
                public void nativeKeyPressed(NativeKeyEvent e) {
                    if ((e.getModifiers() & NativeKeyEvent.ALT_MASK)!=0 &&
                            e.getKeyCode() == NativeKeyEvent.VC_LEFT) {
                        try {
                            if (!isAlter) {
                                changePage(currentPage - 1, isAlter);
                            } else {
                                changePage(alterCurrentPage - 1, isAlter);
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }else if ((e.getModifiers() & NativeKeyEvent.ALT_MASK)!=0 &&
                            e.getKeyCode() == NativeKeyEvent.VC_RIGHT){
                        try {
                            if (!isAlter) {
                                changePage(currentPage + 1, isAlter);
                            } else {
                                changePage(alterCurrentPage + 1, isAlter);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }else if ((e.getModifiers() & NativeKeyEvent.ALT_MASK)!=0 &&
                            e.getKeyCode() == NativeKeyEvent.VC_E){
                        if (!isShowFlag) {
                            if (hide) {
                                for (JButton b : buttons) {
                                    b.setVisible(true);
                                }
                                current.setVisible(true);
                                total.setVisible(true);
                                jbScrollPanel.setVisible(true);
                            } else {
                                for (JButton b : buttons) {
                                    b.setVisible(false);
                                }
                                current.setVisible(false);
                                total.setVisible(false);
                                jbScrollPanel.setVisible(false);
                            }
                            hide = !hide;
                        } else {
                            isAlter = !isAlter;
                            if (isAlter) {
                                try {
                                    changePage(alterCurrentPage, isAlter);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            } else {
                                try {
                                    changePage(currentPage, isAlter);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }

                        }
                    }


                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent e) {
//                    System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return panelRight;
    }

    /**
     * ��ҳ�����
     **/
    private JTextField initTextField() {
        JTextField current = new JTextField("current Page:");
        current.setPreferredSize(new Dimension(50, 30));
        current.setOpaque(false);
        current.setBorder(JBUI.Borders.empty(0));
        if (!isAlter) {
            current.setText(currentPage + 1 + "");
        } else {
            current.setText(alterCurrentPage + 1 + "");
        }
        current.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //�жϰ��µļ��Ƿ��ǻس���
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        String input = current.getText();
                        String inputCurrent = input.split("/")[0].trim();
                        int i = Integer.parseInt(inputCurrent);
                        if (i <= 0) {
                            i = 0;
                        }
                        changePage(i - 1, isAlter);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        return current;
    }

    /**
     * ˢ�°�ť?
     **/
    private JButton initFreshButton() {
        JButton refresh = new JButton("\uD83D\uDD04");
        refresh.setPreferredSize(new Dimension(20, 20));
        refresh.setContentAreaFilled(false);
        refresh.setBorderPainted(false);
        refresh.addActionListener(e -> {
            try {
                changePage(currentPage, isAlter);
            } catch (Exception newE) {
                newE.printStackTrace();
            }
        });
        return refresh;
    }

    /**
     * ���Ϸ�ҳ��ť
     **/
    private JButton initUpButton() {
        JButton afterB = new JButton("prev");
        afterB.setPreferredSize(new Dimension(40, 20));
        afterB.setContentAreaFilled(false);
        afterB.setBorderPainted(false);
        afterB.addActionListener(e -> {
            try {
                if (!isAlter) {
                    changePage(currentPage - 1, isAlter);
                } else {
                    changePage(alterCurrentPage - 1, isAlter);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

//        afterB.registerKeyboardAction(afterB.getActionListeners()[0],
//                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_MASK),
//                JComponent.WHEN_IN_FOCUSED_WINDOW);

        return afterB;
    }

    /**
     * ���·�ҳ��ť
     **/
    private JButton initDownButton() {
        JButton nextB = new JButton("next");
        nextB.setPreferredSize(new Dimension(40, 20));
        nextB.setContentAreaFilled(false);
        nextB.setBorderPainted(false);
        nextB.addActionListener(e -> {
            try {
                if (!isAlter) {
                    changePage(currentPage + 1, isAlter);
                } else {
                    changePage(alterCurrentPage + 1, isAlter);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
//        nextB.registerKeyboardAction(nextB.getActionListeners()[0],
//                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_MASK),
//                JComponent.WHEN_IN_FOCUSED_WINDOW);

        return nextB;
    }

    /**
     * ���ذ�ť
     **/
    private JButton initBossButton(JButton[] buttons) {
        //�ϰ��
        JButton bossB = new JButton(" ");
        bossB.setPreferredSize(new Dimension(5, 5));
        bossB.setContentAreaFilled(false);
        bossB.setBorderPainted(false);
        bossB.addActionListener(e -> {
            if (!isShowFlag) {
                if (hide) {
                    for (JButton b : buttons) {
                        b.setVisible(true);
                    }
                    current.setVisible(true);
                    total.setVisible(true);
                    jbScrollPanel.setVisible(true);
                } else {
                    for (JButton b : buttons) {
                        b.setVisible(false);
                    }
                    current.setVisible(false);
                    total.setVisible(false);
                    jbScrollPanel.setVisible(false);
                }
                hide = !hide;
            } else {
                isAlter = !isAlter;
                if (isAlter) {
                    try {
                        changePage(alterCurrentPage, isAlter);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    try {
                        changePage(currentPage, isAlter);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

            }
        });
//        bossB.setMnemonic(KeyEvent.VK_E);
        return bossB;
    }


}