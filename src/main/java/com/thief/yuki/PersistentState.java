package com.thief.yuki;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(
        name = "PersistentState",
        storages = {@Storage(
                value = "TerminalX.xml"
        )}
)
public class PersistentState implements PersistentStateComponent<Element> {

    private static PersistentState persistentState;
    /**
     * �滻�ļ�·��
     **/
    private String alterPathText;
    /**
     * �����ļ�·��
     */
    private String pdfPathText;

    /**
     * �Ƿ�͸��
     */
    private String transparented;

    /**
     * ͸��RGBֵ
     */
    private String transparentedRgb;

    /**
     * չʾ�հ׻��滻�ļ�
     * 0:չʾ�հ�
     * 1:չʾ�滻�ļ�
     **/
    private String showFlag;

    /**
     * �����ļ����������ҳ��
     */
    private String currentPage;
    /**
     * �滻�ļ����������ҳ��
     */
    private String alterCurrentPage;




    public static PersistentState getInstance() {
        if (persistentState == null) {
            persistentState = ServiceManager.getService(PersistentState.class);
        }
        return persistentState;
    }

    public static PersistentState getInstanceForce() {
        return ServiceManager.getService(PersistentState.class);
    }


    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("PersistentState");
        if (null!=this.getAlterPathText()){
            element.setAttribute("alterPath", this.getAlterPathText());
        }
        if (null!=this.getPdfPathText()){
            element.setAttribute("pdfPath", this.getPdfPathText());
        }
        if (null!=this.getShowFlag()){
            element.setAttribute("showFlag", this.getShowFlag());
        }
        if (null!=this.getCurrentPage()){
            element.setAttribute("currentPage", this.getCurrentPage());
        }
        if (null!=this.getAlterCurrentPage()){
            element.setAttribute("alterPage", this.getAlterCurrentPage());
        }
        if (null!=this.getTransparented()){
            element.setAttribute("transparented", this.getTransparented());
        }
        if (null!=this.getTransparentedRgb()){
            element.setAttribute("transparentedRgb", this.getTransparentedRgb());
        }
        return element;
    }

    @Override
    public void loadState(@NotNull Element state) {
        this.setAlterPathText(state.getAttributeValue("alterPath"));
        this.setPdfPathText(state.getAttributeValue("pdfPath"));
        this.setShowFlag(state.getAttributeValue("showFlag"));
        this.setCurrentPage(state.getAttributeValue("currentPage"));
        this.setAlterCurrentPage(state.getAttributeValue("alterPage"));
        this.setTransparented(state.getAttributeValue("transparented"));
        this.setTransparentedRgb(state.getAttributeValue("transparentedRgb"));
    }

    @Override
    public void noStateLoaded() {

    }

    public static PersistentState getPersistentState() {
        return persistentState;
    }

    public static void setPersistentState(PersistentState persistentState) {
        PersistentState.persistentState = persistentState;
    }

    public String getAlterPathText() {
        return alterPathText;
    }

    public void setAlterPathText(String alterPathText) {
        this.alterPathText = alterPathText;
    }

    public String getShowFlag() {
        return StringUtils.isEmpty(showFlag) ? "0" : this.showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
    public String getPdfPathText() {
        return pdfPathText;
    }

    public String getAlterCurrentPage() {
        return alterCurrentPage;
    }

    public void setAlterCurrentPage(String alterCurrentPage) {
        this.alterCurrentPage = alterCurrentPage;
    }

    public void setPdfPathText(String pdfPathText) {
        this.pdfPathText = pdfPathText;
    }

    public String getTransparented() {
        return transparented;
    }

    public void setTransparented(String transparented) {
        this.transparented = transparented;
    }

    public String getTransparentedRgb() {
        return transparentedRgb;
    }

    public void setTransparentedRgb(String transparentedRgb) {
        this.transparentedRgb = transparentedRgb;
    }
}