package com.thief.yuki;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class ShowPdfAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (e.getProject() != null) {
            // ����Ŀ����ToolWindow��id���룬��ȡ�ؼ�����
            ToolWindow toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("TerminalX");
            if (toolWindow != null) {
                // ���۵�ǰ״̬Ϊ�ر�/�򿪣�����ǿ�ƴ�ToolWindow
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                if (toolWindow.getContentManager().getContentCount() < 1) {
                    MainUi mainUi = new MainUi();
                    mainUi.createToolWindowContent(e.getProject(), toolWindow);
                }
            }
        }
    }
}