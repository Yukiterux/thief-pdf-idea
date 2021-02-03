package com.thief.yuki;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * @author by xinghaowen
 * @version 1.0
 * @classname Mypanel
 */
public class Mypanel extends JPanel {
    private Image timg;
    private boolean isTransparent;
    private int filterRgb;

    Mypanel(Image image, boolean isTransparent, int filterRgb) {
        this.timg = image;
        this.isTransparent = isTransparent;
        this.filterRgb = filterRgb;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image im;
        if (isTransparent) {
            ImageFilter imgf = new MyFilter(255, filterRgb);
            FilteredImageSource fis = new FilteredImageSource(timg.getSource(), imgf);
            im = Toolkit.getDefaultToolkit().createImage(fis);
        } else {
            im = timg;
        }
        Graphics2D g1 = (Graphics2D) g;
        g1.drawImage(im, 0, 0, this);
    }

    static class MyFilter extends RGBImageFilter {// ������RGBImageFilter��ImageFilter�����࣬
        // �̳���ʵ��ͼ��ARGB�Ĵ���
        int alpha = 0;
        private int filterRgb;


        public MyFilter(int alpha, int filterRgb) {// ������������������Ҫ����ͼ��ĳߴ磬�Լ�͸����
            this.canFilterIndexColorModel = true;
            this.filterRgb = filterRgb;
            // TransparentImageFilter��̳���RGBImageFilter�����Ĺ��캯��Ҫ����ԭʼͼ��Ŀ�Ⱥ͸߶ȡ�
            // ����ʵ����filterRGB������
            // ��ȱʡ�ķ�ʽ�£��ú�����x��y����ʶ�����ص�ARGBֵ���룬����Ա����һ���ĳ����߼�����󷵻ظ������µ�ARGBֵ
            this.alpha = alpha;

        }

        @Override
        public int filterRGB(int x, int y, int rgb) {
            DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
            // DirectColorModel��������ARGBֵ�����ֽ����
            int red = dcm.getRed(rgb);
            int green = dcm.getGreen(rgb);
            int blue = dcm.getBlue(rgb);
//            int alp = dcm.getAlpha(rgb);
            int redFilter = dcm.getRed(filterRgb);
            int greenFilter = dcm.getGreen(filterRgb);
            int blueFilter = dcm.getBlue(filterRgb);
            if ((red == redFilter && blue == blueFilter && green == greenFilter)) {//
                alpha = 0;
            } else {
                alpha = 255;
            }
            return alpha << 24 | red << 16 | green << 8 | blue;// ���б�׼ARGB�����ʵ��ͼ�����
        }
    }
}
