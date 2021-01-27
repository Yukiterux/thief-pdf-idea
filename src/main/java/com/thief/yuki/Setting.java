package com.thief.yuki;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.thief.yuki.ui.SettingUi;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Setting implements SearchableConfigurable {


    private SettingUi settingUi;

    private PersistentState persistentState = PersistentState.getInstance();


    @SuppressWarnings("FieldCanBeLocal")
    private final Project project;


    public Setting(@NotNull Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public String getId() {
        return "thiefPdf.id";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "TerminalX-config";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        if (settingUi == null) {
            settingUi = new SettingUi();
        }
        settingUi.innit(persistentState);

        return settingUi.mainPanel;

    }

    @Override
    public boolean isModified() {
        return !StringUtils.equals(persistentState.getAlterPathText(), settingUi.getAlterFilePath().getText())||
                !StringUtils.equals(persistentState.getPdfPathText(), settingUi.getFilePathText().getText())||
                !StringUtils.equals(persistentState.getShowFlag(),settingUi.getChooseHide())||
                !StringUtils.equals(persistentState.getTransparented(),settingUi.getTransparented())||
                !StringUtils.equals(persistentState.getTransparentedRgb(),settingUi.getRgbText().getText());


    }

    @Override
    public void apply() {
        persistentState.setAlterPathText(settingUi.getAlterFilePath().getText());
        persistentState.setPdfPathText(settingUi.getFilePathText().getText());
        persistentState.setShowFlag(settingUi.getChooseHide());
        persistentState.setTransparented(settingUi.getTransparented());
        persistentState.setTransparentedRgb(settingUi.getRgbText().getText());
        }

    @Override
    public void reset() {
//        settingUi.bookPathText.setText("");
//        settingUi.showFlag.setSelected(false);
//        settingUi.fontSize.setSelectedItem("5");
//        settingUi.before.setText("");
//        settingUi.next.setText("");
    }

    @Override
    public void disposeUIResources() {

    }
}