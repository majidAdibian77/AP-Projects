import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * this class is for setting frame
 * in this class we have some tabs and we can save information by sending information to SaveInformation class
 */
public class SettingFrame {
    private JFrame settingFrame;
    // generalSettingPanel is panel for number of downloads and path
    private JPanel generalSettingPanel;
    // lookAndFeelPanel is panel for selecting look and feel of IDM
    private JPanel lookAndFeelPanel;
    //this panel have more options
    private JPanel more;
    // these fields are for generalSettingPanel
    private JLabel browserLabel;
    private JRadioButton infiniteRadioButton;
    private JRadioButton finiteRadioButton;
    private JLabel nameLimitDownload;
    private JSpinner limitDownload;
    // this array is saving information in order "finite or infinite" , "number of download at same time" , "path of download" , "lok and feel"
    private InformationSetting setting;
    // these fields are for look and feel
    private ArrayList<JRadioButton> lookAndFeelRadioButton = new ArrayList<>();

    private TextField unauthorizedSitesText;
    private JComboBox unauthorizedSitesAdded;
    private JButton removeUnauthorizedSitesButton;
    //this field is for select language
    private JComboBox language;

    /**
     * this is constructor of class and we use in this some methods to create panels
     */
    public SettingFrame() {
        settingFrame = new JFrame(SaveInformation.getAllWords().get("Setting"));
        settingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        settingFrame.setSize(500, 350);
        settingFrame.setResizable(false);
        settingFrame.setLocationRelativeTo(null);
        generalSettingPanel = new JPanel(null);
        lookAndFeelPanel = new JPanel(null);
        more = new JPanel(null);
        JTabbedPane tab = new JTabbedPane();
        settingFrame.setContentPane(tab);
        tab.addTab(SaveInformation.getAllWords().get("General"), generalSettingPanel);
        tab.addTab(SaveInformation.getAllWords().get("Look and feel"), lookAndFeelPanel);
        tab.addTab(SaveInformation.getAllWords().get("More"), more);

        setting = SaveInformation.getSettingInformation();
        // this method is for create generalSettingPanel
        createGeneralPanel();
        createLookAndFeel();
        createMoreTab();
    }

    public void show() {
        settingFrame.setVisible(true);
    }


    private void createGeneralPanel() {
        HandlerBrowser handlerBrowser = new HandlerBrowser();
        ButtonGroup buttonGroup = new ButtonGroup();
        HandelRadioButton handelRadioButton = new HandelRadioButton();
        infiniteRadioButton = new JRadioButton(SaveInformation.getAllWords().get("infinite"));
        infiniteRadioButton.addActionListener(handelRadioButton);
        finiteRadioButton = new JRadioButton(SaveInformation.getAllWords().get("finite"));
        finiteRadioButton.addActionListener(handelRadioButton);
        buttonGroup.add(infiniteRadioButton);
        buttonGroup.add(finiteRadioButton);
        if (setting.isInfinite()) {
            infiniteRadioButton.setSelected(true);
        } else {
            finiteRadioButton.setSelected(true);
        }
        generalSettingPanel.add(infiniteRadioButton);
        infiniteRadioButton.setSize(70, 20);
        infiniteRadioButton.setLocation(20, 20);
        generalSettingPanel.add(finiteRadioButton);
        finiteRadioButton.setSize(70, 20);

        finiteRadioButton.setLocation(100, 20);

        nameLimitDownload = new JLabel(SaveInformation.getAllWords().get("number of downloads at same time"));
        limitDownload = new JSpinner();
        generalSettingPanel.add(nameLimitDownload);
        nameLimitDownload.setSize(300, 20);
        nameLimitDownload.setLocation(20, 60);
        generalSettingPanel.add(limitDownload);
        limitDownload.setSize(50, 30);
        limitDownload.setLocation(250, 55);
        if (setting.getLimitNumberOfDownload() == 0) {
            nameLimitDownload.setEnabled(false);
            limitDownload.setValue(0);
            limitDownload.setEnabled(false);
        } else {
            nameLimitDownload.setEnabled(true);
            limitDownload.setValue(setting.getLimitNumberOfDownload());
            limitDownload.setEnabled(true);
        }

        JLabel pathLabel = new JLabel(SaveInformation.getAllWords().get("Path") + " :");
        generalSettingPanel.add(pathLabel);
        pathLabel.setSize(70, 20);
        pathLabel.setLocation(20, 130);

        JButton browserButton = new JButton(SaveInformation.getAllWords().get("Brows"));
        generalSettingPanel.add(browserButton);
        browserButton.setSize(90, 20);
        browserButton.setLocation(80, 160);
        browserButton.addActionListener(handlerBrowser);
        browserLabel = new JLabel(setting.getPathOfDownload());

        generalSettingPanel.add(browserLabel);
        browserLabel.setSize(270, 20);
        browserLabel.setLocation(80, 130);
        browserLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));


        JButton saveButton = new JButton(SaveInformation.getAllWords().get("Save"));
        generalSettingPanel.add(saveButton);
        saveButton.setSize(70, 25);
        saveButton.setLocation(390, 240);
        HandelSave handelSave = new HandelSave();
        saveButton.addActionListener(handelSave);

        JButton cancelButton = new JButton(SaveInformation.getAllWords().get("Cancel"));
        generalSettingPanel.add(cancelButton);
        cancelButton.setSize(70, 25);
        cancelButton.setLocation(300, 240);
        HandelCancel handelCancel = new HandelCancel();
        cancelButton.addActionListener(handelCancel);


    }

    /**
     * this method is for creating look and feel tap
     */
    private void createLookAndFeel() {
        JLabel textLookAndFeel = new JLabel(SaveInformation.getAllWords().get("select look and feel") + " :");
        textLookAndFeel.setSize(200, 20);
        lookAndFeelPanel.add(textLookAndFeel);
        textLookAndFeel.setLocation(30, 20);
        ButtonGroup buttonGroup = new ButtonGroup();

        lookAndFeelRadioButton.add(new JRadioButton("Metal look and feel"));
        buttonGroup.add(lookAndFeelRadioButton.get(0));
        lookAndFeelPanel.add(lookAndFeelRadioButton.get(0));
        lookAndFeelRadioButton.get(0).setSize(200, 20);
        lookAndFeelRadioButton.get(0).setLocation(20, 60);

        lookAndFeelRadioButton.add(new JRadioButton("Nimbus look and feel"));
        buttonGroup.add(lookAndFeelRadioButton.get(1));
        lookAndFeelPanel.add(lookAndFeelRadioButton.get(1));
        lookAndFeelRadioButton.get(1).setSize(200, 20);
        lookAndFeelRadioButton.get(1).setLocation(20, 100);

        lookAndFeelRadioButton.add(new JRadioButton("Motif look and feel"));
        buttonGroup.add(lookAndFeelRadioButton.get(2));
        lookAndFeelPanel.add(lookAndFeelRadioButton.get(2));
        lookAndFeelRadioButton.get(2).setSize(200, 20);
        lookAndFeelRadioButton.get(2).setLocation(20, 140);

        lookAndFeelRadioButton.add(new JRadioButton("Windows look and feel"));
        buttonGroup.add(lookAndFeelRadioButton.get(3));
        lookAndFeelPanel.add(lookAndFeelRadioButton.get(3));
        lookAndFeelRadioButton.get(3).setSize(200, 20);
        lookAndFeelRadioButton.get(3).setLocation(20, 180);

        lookAndFeelRadioButton.add(new JRadioButton("Windows classic look and feel"));
        buttonGroup.add(lookAndFeelRadioButton.get(4));
        lookAndFeelPanel.add(lookAndFeelRadioButton.get(4));
        lookAndFeelRadioButton.get(4).setSize(200, 20);
        lookAndFeelRadioButton.get(4).setLocation(20, 220);
        for (JRadioButton r : lookAndFeelRadioButton) {
            if (r.getText().equals(setting.getLookAndFeel())) {
                r.setSelected(true);
                break;
            }
        }

        JButton saveButton = new JButton(SaveInformation.getAllWords().get("Save"));
        lookAndFeelPanel.add(saveButton);
        saveButton.setSize(70, 25);
        saveButton.setLocation(390, 240);
        HandelSave handelSave = new HandelSave();
        saveButton.addActionListener(handelSave);

        JButton cancelButton = new JButton(SaveInformation.getAllWords().get("Cancel"));
        lookAndFeelPanel.add(cancelButton);
        cancelButton.setSize(70, 25);
        cancelButton.setLocation(300, 240);
        HandelCancel handelCancel = new HandelCancel();
        cancelButton.addActionListener(handelCancel);

    }

    /**
     * this method is for creates a tab and add some option to it.( selecting language , .....)
     */
    private void createMoreTab() {
        JLabel languageLabel = new JLabel(SaveInformation.getAllWords().get("select language") + " :");
        languageLabel.setSize(150, 20);
        more.add(languageLabel);
        languageLabel.setLocation(20, 20);

        language = new JComboBox();
        language.setName("ComboBox");
        language.setSize(100, 25);
        language.setLocation(170, 20);
        more.add(language);
        language.addItem("English");
        language.addItem("Pension");
        language.setSelectedItem(setting.getLanguage());


        // these lines are for Unauthorized sites list (label , button for add ,...)
        JLabel unauthorizedSitesLabel = new JLabel(SaveInformation.getAllWords().get("Unauthorized sites list") + " :");
        unauthorizedSitesLabel.setSize(200, 20);
        more.add(unauthorizedSitesLabel);
        unauthorizedSitesLabel.setLocation(20, 100);

        unauthorizedSitesText = new TextField();
        more.add(unauthorizedSitesText);
        unauthorizedSitesText.setSize(200, 25);
        unauthorizedSitesText.setLocation(180, 140);

        JButton addUnauthorizedSitesButton = new JButton(SaveInformation.getAllWords().get("Add"));
        addUnauthorizedSitesButton.setName("Add");
        more.add(addUnauthorizedSitesButton);
        addUnauthorizedSitesButton.setSize(80, 25);
        addUnauthorizedSitesButton.setLocation(390, 140);
        HandlerUnauthorizedSite handlerUnauthorizedSite = new HandlerUnauthorizedSite();
        addUnauthorizedSitesButton.addActionListener(handlerUnauthorizedSite);

        unauthorizedSitesAdded = new JComboBox();
        unauthorizedSitesAdded.setName("ComboBox");
        unauthorizedSitesAdded.setSize(200, 25);
        unauthorizedSitesAdded.setLocation(180, 170);
        more.add(unauthorizedSitesAdded);
        for (int i = 0; i < setting.getUnauthorizedSitesAdded().size(); i++) {
            unauthorizedSitesAdded.addItem(setting.getUnauthorizedSitesAdded().get(i));
        }
        unauthorizedSitesAdded.addActionListener(handlerUnauthorizedSite);


        removeUnauthorizedSitesButton = new JButton(SaveInformation.getAllWords().get("Remove"));
        removeUnauthorizedSitesButton.setName("Remove");
        more.add(removeUnauthorizedSitesButton);
        removeUnauthorizedSitesButton.setSize(80, 25);
        removeUnauthorizedSitesButton.setLocation(390, 170);
        removeUnauthorizedSitesButton.setEnabled(false);
        removeUnauthorizedSitesButton.addActionListener(handlerUnauthorizedSite);


        JButton saveButton = new JButton(SaveInformation.getAllWords().get("Save"));
        more.add(saveButton);
        saveButton.setSize(70, 25);
        saveButton.setLocation(400, 240);
        HandelSave handelSave = new HandelSave();
        saveButton.addActionListener(handelSave);

        JButton cancelButton = new JButton(SaveInformation.getAllWords().get("Cancel"));
        more.add(cancelButton);
        cancelButton.setSize(70, 25);
        cancelButton.setLocation(310, 240);
        HandelCancel handelCancel = new HandelCancel();
        cancelButton.addActionListener(handelCancel);



    }


    /**
     * this method is for handeling cancel
     */
    private class HandelCancel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            settingFrame.dispose();
        }
    }

    /**
     * this method is for handeling save information
     */
    private class HandelSave implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!infiniteRadioButton.isSelected() && Integer.parseInt(limitDownload.getValue() + "") < 1) {
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("Number of download at same time can not be less than one!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            } else if (!infiniteRadioButton.isSelected() && Integer.parseInt(limitDownload.getValue() + "") < SaveInformation.getProcessingDownloads().size()) {
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("Number of download at same time is less than number of downloads is processing!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            } else {
                if (infiniteRadioButton.isSelected()) {
                    setting.setInfinite(true);
                    setting.setLimitNumberOfDownload(0);
                    limitDownload.setValue(0);
                } else {
                    setting.setInfinite(false);
                    setting.setLimitNumberOfDownload((Integer) limitDownload.getValue());
                }

                setting.getUnauthorizedSitesAdded().clear();
                for (int i = 0; i < unauthorizedSitesAdded.getItemCount(); i++) {
                    setting.addToUnauthorizedSitesAdded(unauthorizedSitesAdded.getItemAt(i).toString());
                }
                setting.setPathOfDownload(browserLabel.getText());
                for (JRadioButton r : lookAndFeelRadioButton) {
                    if (r.isSelected()) {
                        setting.setLookAndFeel(r.getText());
                        break;
                    }
                }
                // this boolean is for checking that language changes or not
                boolean test1 = true;
                if (!setting.getLanguage().equals(language.getSelectedItem())) {
                    test1 = false;
                    setting.setLanguage(language.getSelectedItem() + "");
                    SaveInformation.setSettingInformation(setting);
                }
                // these lines are for save setting in file
                try (FileOutputStream fs = new FileOutputStream("files/settings.jdm")) {
                    ObjectOutputStream os = new ObjectOutputStream(fs);
                    os.writeObject(setting);
                    os.flush();
                    fs.close();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("JDM could not save your change (setting)!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
                }

                if (!test1) {
                    JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("For Change language you should run JDM again!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
                    settingFrame.dispose();
                    CFrame.disposeFrame();
                } else {
                    settingFrame.dispose();
                    CFrame.disposeFrameFromSetting();
                    CFrame.createFrame();
                }
            }
        }
    }

    /**
     * this method is for handel RadioButton event
     */
    private class HandelRadioButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(infiniteRadioButton)) {
                nameLimitDownload.setEnabled(false);
                limitDownload.setEnabled(false);
            } else {
                limitDownload.setValue(1);
                nameLimitDownload.setEnabled(true);
                limitDownload.setEnabled(true);
            }
        }
    }

    /**
     * this method is for handeling the event done on the browser button.
     */
    private class HandlerBrowser implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            String chooserTitle = "Browser";
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("C:\\Users\\Majid\\Desktop"));
            chooser.setDialogTitle(chooserTitle);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // disable the "All files" option.
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(settingFrame) == JFileChooser.APPROVE_OPTION) {
                browserLabel.setText(chooser.getSelectedFile().toString());
            } else {
                System.out.println("No Selection ");
            }
        }

    }

    private class HandlerUnauthorizedSite implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Component component = (Component) e.getSource();
            String tempButton = component.getName();
            switch (tempButton) {
                case "Add": {
                    boolean exists = false;
                    for (int index = 0; index < unauthorizedSitesAdded.getItemCount(); index++) {
                        if (unauthorizedSitesText.getText().equals(unauthorizedSitesAdded.getItemAt(index))) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists && !unauthorizedSitesText.getText().isEmpty()) {
                        unauthorizedSitesAdded.addItem(unauthorizedSitesText.getText());
                    }
                    unauthorizedSitesText.setText("");
                    break;
                }
                case "Remove": {
                    unauthorizedSitesAdded.removeItem(unauthorizedSitesAdded.getSelectedItem());
                    if (unauthorizedSitesAdded.getItemCount() == 0) {
                        removeUnauthorizedSitesButton.setEnabled(false);
                    }
                    break;
                }
                case "ComboBox": {
                    removeUnauthorizedSitesButton.setEnabled(true);
                    break;
                }
            }

        }
    }
}

