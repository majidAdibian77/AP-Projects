import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * this class is for all action that are in panel of progressBar
 */
public class ActionProgressBar implements ActionListener, MouseListener {
    private InformationNewDownload newDownload;
    private JPanel rightPanel;
    private JPanel progressPanel;

     public ActionProgressBar(InformationNewDownload newDownload, JPanel rightPanel , JPanel progressPanel) {
        this.newDownload = newDownload;
        this.rightPanel = rightPanel;
        this.progressPanel = progressPanel;
    }

    /**
     * this method is used for action of buttons in progress bar
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < SaveInformation.getDownloadClickeds().size(); i++) {
            SaveInformation.getDownloadClickeds().get(i).setClicked(false);
            SaveInformation.getDownloadClickeds().remove(i);
        }
        Component component = (Component) e.getSource();
        String tempButton = component.getName();

        switch (tempButton) {
            case "Resume":
                // this line is for checking that this download is not competed
                if (newDownload.getSizeOfDownloaded() < newDownload.getSizeOfDownload()) {
                // these lines are showing error if number of downloads at the same time is less than that user selected in setting
                boolean test3 = false;
                for (int j = 0; j < SaveInformation.getProcessingDownloads().size(); j++) {
                    if (SaveInformation.getProcessingDownloads().get(j).equals(newDownload)) {
                        test3 = true;
                    }
                }
                int number = 0;
                if (!test3) {
                    number = 1;
                }
                if (SaveInformation.getProcessingDownloads().size() + number > SaveInformation.getSettingInformation().getLimitNumberOfDownload() && SaveInformation.getSettingInformation().getLimitNumberOfDownload() != 0) {
                    JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("Number of downloads at same time is more than you select in setting!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
                    SaveInformation.getWatingDowmloads().add(newDownload);
                } else {

                    //these lines are for checking that this download is selected before or not to do not add it egan
                    boolean test = false;
                    for (InformationNewDownload newDownload1 : SaveInformation.getProcessingDownloads()) {
                        if (newDownload1.equals(newDownload)) {
                            test = true;
                            break;
                        }
                    }
                    if (!test) {
                        newDownload.setStopDownload(false);
                        SaveInformation.addToProcessingDownloads(newDownload);
                    }
                }

                    CFrame.removeAllRightPanel();
                    CFrame.createRightPanel();
                }
                break;
            case "Pause":
                //these lines are for checking that this download is selected before or not to do not add it egan
                boolean test = false;
                for (InformationNewDownload newDownload1 : SaveInformation.getProcessingDownloads()) {
                    if (newDownload1.equals(newDownload)) {
                        test = true;
                        break;
                    }
                }
                if (test) {
                    newDownload.setStopDownload(true);
                    SaveInformation.getProcessingDownloads().remove(newDownload);
                }

                for (int i = 0; i < SaveInformation.getThreadsDownloads().size(); i++) {
                    if (SaveInformation.getThreadsDownloads().get(i).getFile().equals(newDownload)) {
                        SaveInformation.getThreadsDownloads().get(i).stopFromPause();
                        SaveInformation.getThreadsDownloads().remove(i);
                        break;
                    }
                }

                // this line is for stop processing of download (now we set 1 to size of downloaded
                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();

                break;
            case "Cancel":
                // these lines are for show a massage to become sure to cancel download
                boolean test2 = false;
                int selectedOption = JOptionPane.showConfirmDialog(null,
                        SaveInformation.getAllWords().get("Are you sure to cancel download?"),
                        SaveInformation.getAllWords().get("Cancel"),
                        JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION) {
                    test2 = true;
                }
                if (test2) {
                    newDownload.setStopDownload(true);
                    SaveInformation.getProcessingDownloads().remove(newDownload);
                }
                newDownload.setSizeOfDownloaded(0);

                for (int m = 0; m < SaveInformation.getThreadsDownloads().size(); m++) {
                    if (SaveInformation.getThreadsDownloads().get(m).getFile().equals(newDownload)) {
                        SaveInformation.getThreadsDownloads().get(m).stopFromCancelOrRemove();
                        SaveInformation.getThreadsDownloads().remove(m);
                    }
                }

                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
                // these lines are for open file (now we show error)
                break;
            case "Open":
                openFile();
                break;

        }
    }


    // this method is used for create a panel to show mor detals of download
    @Override
    public void mouseClicked(MouseEvent e) {
        // if user double clicked on download file of download opens
        if (e.getClickCount() == 2) {
            openFile();
        } else {

            // if user clicked right on download we show a ew panel to him
            if (e.getButton() == 3) {
                if (!newDownload.getClicked()) {
                    JPanel infoDownLoadClicked = new JPanel(new GridLayout(10, 1));
                    JLabel name = new JLabel(SaveInformation.getAllWords().get("Name") + ": " + newDownload.getNameNewDownload());
                    makeLabel(name, name.getText());

                    JLabel sizeOfFileLabel = new JLabel(SaveInformation.getAllWords().get("Size") + ": " + Sort.recognizeSizeType(newDownload.getSizeOfDownload()));
                    makeLabel(sizeOfFileLabel, sizeOfFileLabel.getText());


                    JLabel downloaded = new JLabel(SaveInformation.getAllWords().get("Downloaded") + ": " + Sort.recognizeSizeType(newDownload.getSizeOfDownloaded()));
                    makeLabel(downloaded, downloaded.getText());

                    JLabel link = new JLabel(SaveInformation.getAllWords().get("URL") + ": " + newDownload.getLinkNewDownload());
                    makeLabel(link, link.getText());

                    JLabel speed = new JLabel(SaveInformation.getAllWords().get("Speed") + ": " + newDownload.getSpeedOfDownload());
                    makeLabel(speed, speed.getText());

                    JLabel saveTo = new JLabel();
                    saveTo.setText(SaveInformation.getAllWords().get("Save To") + ": " + SaveInformation.getSettingInformation().getPathOfDownload());
                    makeLabel(saveTo, saveTo.getText());

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    JLabel date = new JLabel(SaveInformation.getAllWords().get("Date") + ": " + dateFormat.format(newDownload.getDateOfDownload()));
                    makeLabel(date, date.getText());

                    infoDownLoadClicked.add(name);
                    infoDownLoadClicked.add(link);
                    infoDownLoadClicked.add(sizeOfFileLabel);
                    infoDownLoadClicked.add(downloaded);
                    infoDownLoadClicked.add(speed);
                    infoDownLoadClicked.add(saveTo);
                    infoDownLoadClicked.add(date);
                    infoDownLoadClicked.setBorder(new EmptyBorder(5, 10, 5, 10));
                    infoDownLoadClicked.setBackground(Color.LIGHT_GRAY);

                    SaveInformation.addToDownloadClicked(newDownload);
                    newDownload.setClicked(true);
                    // these lines are for change color of panel that is selected
                    progressPanel.setBackground(Color.WHITE);

                    CFrame.removeAllRightPanel();
                    rightPanel.add(infoDownLoadClicked, BorderLayout.EAST);
                    CFrame.createRightPanel();
                } else {
                    newDownload.setClicked(false);
                    SaveInformation.getDownloadClickeds().remove(newDownload);
                    // these lines are for change color of panel that is selected
                    progressPanel.setBackground(Color.cyan);

                    CFrame.removeAllRightPanel();
                    CFrame.createRightPanel();
                }
            }
            // if user clicked left on download, this download is selected
            if (e.getButton() == 1) {
                if (newDownload.getClicked()) {
                    newDownload.setClicked(false);
                    SaveInformation.getDownloadClickeds().remove(newDownload);
                    // these lines are for change color of panel that is selected
                    progressPanel.setBackground(Color.WHITE);
                } else {
                    newDownload.setClicked(true);
                    SaveInformation.addToDownloadClicked(newDownload);
                    // these lines are for change color of panel that is selected
                    progressPanel.setBackground(Color.cyan);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    /**
     * in this method we create a label that has not size more than a number
     *
     * @param s is tooltip of label
     */
    public void makeLabel(JLabel label, String s) {
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        if (label.getText().length() > 20) {
            label.setText(label.getText().substring(0, 20) + "...");
        }
        label.setToolTipText(s);
    }


    /**
     * this method used for open file
     */
    private void openFile(){

        if (newDownload.getSizeOfDownloaded() < newDownload.getSizeOfDownload() || newDownload.getSizeOfDownloaded()==0) {
            JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("You can not open file before Before completing downloading!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);

        } else {
            File file = new File(SaveInformation.getSettingInformation().getPathOfDownload() + "\\" + newDownload.getNameNewDownload());
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
