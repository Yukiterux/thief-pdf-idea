package com.thief.yuki.ui;

import com.thief.yuki.PersistentState;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.io.File;

public class SettingUi {


    public JPanel mainPanel;
    public JLabel chooseFileLabel;
    private JTextField filePathText;
    private JButton chooseFileButton;
    private JLabel chooseAlterLabel;
    private JTextField alterFilePath;
    private JButton chooseAlterButton;
    private JRadioButton hideRadio;
    private JRadioButton showAlterRadio;
    private JTextField preText;
    private JLabel thiefKeyBehaviorLable;
    private JLabel preLable;
    private JLabel nextLabel;
    private JLabel thiefKeyLabel;
    private JLabel chooseTransparentLabel;
    private JLabel rgbLabel;
    private JTextField nextText;
    private JTextField thiefKeyText;
    private JRadioButton noTransparent;
    private JRadioButton yesTransparent;
    private JTextField rgbText;
    private JTextField tipEdit;
    public JLabel label1;
    public JButton button2;
    public JButton button3;
    private JLabel choosePdfFileLabel;
    private JLabel label2;
    private String chooseHide;
    private String transparented;

    public SettingUi() {
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showOpenDialog(mainPanel);
            File file = fileChooser.getSelectedFile();
            filePathText.setText(file.getPath());
        });
        chooseAlterButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showOpenDialog(mainPanel);
            File file = fileChooser.getSelectedFile();
            alterFilePath.setText(file.getPath());
        });

        hideRadio.addItemListener(e->itemStateChanged(e));
        showAlterRadio.addItemListener(e->itemStateChanged(e));
        noTransparent.addItemListener(e->itemStateChanged(e));
        yesTransparent.addItemListener(e->itemStateChanged(e));

    }




    public void innit(PersistentState persistentState) {
        if (null!= persistentState.getPdfPathText()){
            filePathText.setText( persistentState.getPdfPathText());

        }
        if (null!=persistentState.getAlterPathText()){
            alterFilePath.setText(persistentState.getAlterPathText());
        }
        preText.setText("Alt + ¡û");
        preText.setEditable(false);
        nextText.setText("Alt + ¡ú");
        nextText.setEditable(false);
        thiefKeyText.setText("Alt + E");
        thiefKeyText.setEditable(false);
        rgbText.setText("#FFFFFF");
        String showFlag = persistentState.getShowFlag();
        if ("1".equals(showFlag)){
            chooseHide = "1";
            showAlterRadio.setSelected(true);
        }else {
            chooseHide = "0";
            hideRadio.setSelected(true);
        }
        String transparented = persistentState.getTransparented();
        if ("1".equals(transparented)){
            transparented = "1";
            yesTransparent.setSelected(true);
        }else {
            transparented = "0";
            noTransparent.setSelected(true);
        }
        String transparentedRgb = persistentState.getTransparentedRgb();
        if (null!=transparentedRgb){
            rgbText.setText(transparentedRgb);

        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (hideRadio.isSelected()) {
            chooseHide = "0";
        }
        if (showAlterRadio.isSelected()){
            chooseHide = "1";
        }
        if (noTransparent.isSelected()){
            transparented = "0";
        }
        if (yesTransparent.isSelected()){
            transparented = "1";
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public String getChooseHide() {
        return chooseHide;
    }

    public String getTransparented() {
        return transparented;
    }

    public JLabel getChooseFileLabel() {
        return chooseFileLabel;
    }

    public void setChooseFileLabel(JLabel chooseFileLabel) {
        this.chooseFileLabel = chooseFileLabel;
    }

    public JTextField getFilePathText() {
        return filePathText;
    }

    public void setFilePathText(JTextField filePathText) {
        this.filePathText = filePathText;
    }

    public JButton getChooseFileButton() {
        return chooseFileButton;
    }

    public void setChooseFileButton(JButton chooseFileButton) {
        this.chooseFileButton = chooseFileButton;
    }

    public JLabel getChooseAlterLabel() {
        return chooseAlterLabel;
    }

    public void setChooseAlterLabel(JLabel chooseAlterLabel) {
        this.chooseAlterLabel = chooseAlterLabel;
    }

    public JTextField getAlterFilePath() {
        return alterFilePath;
    }

    public void setAlterFilePath(JTextField alterFilePath) {
        this.alterFilePath = alterFilePath;
    }

    public JButton getChooseAlterButton() {
        return chooseAlterButton;
    }

    public void setChooseAlterButton(JButton chooseAlterButton) {
        this.chooseAlterButton = chooseAlterButton;
    }

    public JRadioButton getHideRadio() {
        return hideRadio;
    }

    public void setHideRadio(JRadioButton hideRadio) {
        this.hideRadio = hideRadio;
    }

    public JRadioButton getShowAlterRadio() {
        return showAlterRadio;
    }

    public void setShowAlterRadio(JRadioButton showAlterRadio) {
        this.showAlterRadio = showAlterRadio;
    }

    public JTextField getPreText() {
        return preText;
    }

    public void setPreText(JTextField preText) {
        this.preText = preText;
    }

    public JLabel getThiefKeyBehaviorLable() {
        return thiefKeyBehaviorLable;
    }

    public void setThiefKeyBehaviorLable(JLabel thiefKeyBehaviorLable) {
        this.thiefKeyBehaviorLable = thiefKeyBehaviorLable;
    }

    public JLabel getPreLable() {
        return preLable;
    }

    public void setPreLable(JLabel preLable) {
        this.preLable = preLable;
    }

    public JLabel getNextLabel() {
        return nextLabel;
    }

    public void setNextLabel(JLabel nextLabel) {
        this.nextLabel = nextLabel;
    }

    public JLabel getThiefKeyLabel() {
        return thiefKeyLabel;
    }

    public void setThiefKeyLabel(JLabel thiefKeyLabel) {
        this.thiefKeyLabel = thiefKeyLabel;
    }

    public JLabel getChooseTransparentLabel() {
        return chooseTransparentLabel;
    }

    public void setChooseTransparentLabel(JLabel chooseTransparentLabel) {
        this.chooseTransparentLabel = chooseTransparentLabel;
    }

    public JLabel getRgbLabel() {
        return rgbLabel;
    }

    public void setRgbLabel(JLabel rgbLabel) {
        this.rgbLabel = rgbLabel;
    }

    public JTextField getNextText() {
        return nextText;
    }

    public void setNextText(JTextField nextText) {
        this.nextText = nextText;
    }

    public JTextField getThiefKeyText() {
        return thiefKeyText;
    }

    public void setThiefKeyText(JTextField thiefKeyText) {
        this.thiefKeyText = thiefKeyText;
    }

    public JRadioButton getNoTransparent() {
        return noTransparent;
    }

    public void setNoTransparent(JRadioButton noTransparent) {
        this.noTransparent = noTransparent;
    }

    public JRadioButton getYesTransparent() {
        return yesTransparent;
    }

    public void setYesTransparent(JRadioButton yesTransparent) {
        this.yesTransparent = yesTransparent;
    }

    public JTextField getRgbText() {
        return rgbText;
    }

    public void setRgbText(JTextField rgbText) {
        this.rgbText = rgbText;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public void setLabel1(JLabel label1) {
        this.label1 = label1;
    }

    public JButton getButton2() {
        return button2;
    }

    public void setButton2(JButton button2) {
        this.button2 = button2;
    }


    public JButton getButton3() {
        return button3;
    }

    public void setButton3(JButton button3) {
        this.button3 = button3;
    }

    public JLabel getChoosePdfFileLabel() {
        return choosePdfFileLabel;
    }

    public void setChoosePdfFileLabel(JLabel choosePdfFileLabel) {
        this.choosePdfFileLabel = choosePdfFileLabel;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public void setLabel2(JLabel label2) {
        this.label2 = label2;
    }



}