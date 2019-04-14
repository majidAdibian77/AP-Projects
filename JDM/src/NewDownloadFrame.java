
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * this class is for crate new download frame
 */
public class NewDownloadFrame {
    private JFrame newDownloadFrame;
    private JPanel mainNewDownloadPanel;
    private TextField linkNewDownload;
    private TextField nameNewDownload;
    private ArrayList<JRadioButton> waysOfDownload = new ArrayList<>();
    // this field is array of InformationNewDownload that have fields for all information of every new downloads
    private InformationNewDownload newDownload = new InformationNewDownload();

    public NewDownloadFrame() {
        newDownloadFrame = new JFrame(SaveInformation.getAllWords().get("New Download"));
        newDownloadFrame.setSize(450, 280);
        newDownloadFrame.setResizable(false);
        newDownloadFrame.setLocationRelativeTo(null);
        mainNewDownloadPanel = new JPanel(null);
        newDownloadFrame.setContentPane(mainNewDownloadPanel);


        JLabel linkLabel = new JLabel(SaveInformation.getAllWords().get("URL") + " :");
        linkLabel.setIcon(new ImageIcon("imageIcon/link2.png"));
        mainNewDownloadPanel.add(linkLabel);
        linkLabel.setSize(100, 30);
        linkLabel.setLocation(10, 20);
        linkNewDownload = new TextField();
        linkNewDownload.setSize(300, 25);
        linkNewDownload.setLocation(120, 20);
        mainNewDownloadPanel.add(linkNewDownload);

        JLabel nameNewDownloadLabel = new JLabel(SaveInformation.getAllWords().get("Name") + " :");
        nameNewDownloadLabel.setIcon(new ImageIcon("imageIcon/name of file2.png"));
        mainNewDownloadPanel.add(nameNewDownloadLabel);
        nameNewDownloadLabel.setSize(100, 30);
        nameNewDownloadLabel.setLocation(10, 60);
        nameNewDownload = new TextField();
        nameNewDownload.setText("example: file.mp4");
        mainNewDownloadPanel.add(nameNewDownload);
        nameNewDownload.setSize(300, 25);
        nameNewDownload.setLocation(120, 60);

        waysOfDownload.add(new JRadioButton(SaveInformation.getAllWords().get("Download now") + " :"));
        ButtonGroup buttonGroup = new ButtonGroup();
        mainNewDownloadPanel.add(waysOfDownload.get(0));
        waysOfDownload.get(0).setSize(150, 20);
        waysOfDownload.get(0).setLocation(20, 120);
        buttonGroup.add(waysOfDownload.get(0));
        waysOfDownload.add(new JRadioButton(SaveInformation.getAllWords().get("Download in queue") + " :"));
        mainNewDownloadPanel.add(waysOfDownload.get(1));
        waysOfDownload.get(1).setSize(150, 25);
        waysOfDownload.get(1).setLocation(20, 160);
        buttonGroup.add(waysOfDownload.get(1));
        waysOfDownload.get(1).setSelected(true);

        JButton okButton = new JButton(SaveInformation.getAllWords().get("OK"));
        HandelOk handelOk = new HandelOk();
        mainNewDownloadPanel.add(okButton);
        okButton.setSize(80, 25);
        okButton.setLocation(330, 210);
        okButton.addActionListener(handelOk);
        JButton cancelButton = new JButton(SaveInformation.getAllWords().get("Cancel"));
        HandelCancel handelCancel = new HandelCancel();
        mainNewDownloadPanel.add(cancelButton);
        cancelButton.setSize(80, 25);
        cancelButton.setLocation(230, 210);
        cancelButton.addActionListener(handelCancel);

        show();

    }

    private void show() {
        newDownloadFrame.setVisible(true);
    }

    /**
     * this method is for handeling save information
     */
    private class HandelOk implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean testInfo = true;
            //these lines are for checking unauthorized URL
            boolean testLink = false;
            for (int i = 0; i < SaveInformation.getSettingInformation().getUnauthorizedSitesAdded().size(); i++) {
                if (linkNewDownload.getText().contains(SaveInformation.getSettingInformation().getUnauthorizedSitesAdded().get(i))) {
                    testLink = true;
                    testInfo = false;
                    break;
                }
            }
            if (testLink) {
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("You entered a unauthorized URL!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            }
            boolean testName = false;
            for (int i = 0; i < SaveInformation.getAllDownloads().size(); i++) {
                String nameText;
                if (nameNewDownload.getText().isEmpty()) {
                    nameText = linkNewDownload.getText().substring(linkNewDownload.getText().lastIndexOf('/') + 1);
                } else {
                    nameText = nameNewDownload.getText();
                }
                if (SaveInformation.getAllDownloads().get(i).getNameNewDownload().equals(nameText)) {
                    testName = true;
                    testInfo = false;
                    break;
                }
            }
            if (testName) {
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get(SaveInformation.getAllWords().get("You have a download with this name! Change name.")), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            }

            // these lines is for show error if no URL is entered
            if (linkNewDownload.getText().isEmpty()) {
                testInfo = false;
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("You do not enter any URL!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            }
            if (testInfo) {
                // these lines are for get name and URL and if name is empty name of file is a part of URL
                newDownload.setLinkNewDownload(linkNewDownload.getText());
                if (nameNewDownload.getText().isEmpty()) {
                    if (linkNewDownload.getText().contains("/")) {
                        newDownload.setNameNewDownload(linkNewDownload.getText().substring(linkNewDownload.getText().lastIndexOf('/') + 1));
                    } else {
                        newDownload.setNameNewDownload(linkNewDownload.getText());
                    }
                } else {
                    newDownload.setNameNewDownload(nameNewDownload.getText());
                }
                if (waysOfDownload.get(0).isSelected()) {
                    newDownload.setDownloadNow(true);
                } else {
                    newDownload.setDownloadNow(false);
                }

                // these lines is for set some value for new download
                setSizeOfDownload();
                newDownload.setSizeOfDownloaded(0);
                newDownload.setSpeedOfDownload("0.0");

                boolean testNumberOfDownloads = true;
                if (newDownload.isDownloadNow()) {
                    if (SaveInformation.getSettingInformation().getLimitNumberOfDownload() < SaveInformation.getProcessingDownloads().size() + 1 && SaveInformation.getSettingInformation().getLimitNumberOfDownload() != 0) {
                        testNumberOfDownloads = false;
                        JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("Number of downloads at same time is more than you select in setting!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
                    }
                }

                // every new download adds to all downloads and adds either processing downloads or queues downloads
                SaveInformation.addNewToAllDownloads(newDownload);
                if (newDownload.isDownloadNow()) {
                    newDownload.setStopDownload(false);
                    if (testNumberOfDownloads) {
                        SaveInformation.addToProcessingDownloads(newDownload);
                    } else {
                        SaveInformation.addToWaitingDownloads(newDownload);
                    }
                } else {
                    newDownload.setStopDownload(true);
                    SaveInformation.addToQueuesDownload(newDownload);
                }

                // these lines are for refresh frame
                newDownloadFrame.dispose();
                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
            }
        }
    }

    /**
     * this method is for handeling cancel button
     */
    private class HandelCancel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            newDownloadFrame.dispose();
            CFrame.removeAllRightPanel();
            CFrame.createRightPanel();
        }
    }

    private void setSizeOfDownload() {
        try {
            URL url = new URL(newDownload.getLinkNewDownload());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            newDownload.setSizeOfDownload(connection.getContentLength());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("there is a problem in connection!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            newDownload.setSizeOfDownload(0);
        }
    }
}
