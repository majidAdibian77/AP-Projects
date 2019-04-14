import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * this class is for Action of buttons of tool bar
 */

public class ActionTollBar implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        Component component = (Component) e.getSource();
        String tempButton = component.getName();

        switch (tempButton) {
            case "New download": {
                // this line is for creating a CFrame for new download and refresh CFrame
                NewDownloadFrame createNewDownload = new NewDownloadFrame();
                break;
            }
            case "Resume": {
                //these lines are for checking that number of downloads at the same time is mor than what user selected in setting or not
                int number = SaveInformation.getDownloadClickeds().size();
                for (int i = 0; i < SaveInformation.getProcessingDownloads().size(); i++) {
                    for (int j = 0; j < SaveInformation.getDownloadClickeds().size(); j++) {
                        if (SaveInformation.getDownloadClickeds().get(j).equals(SaveInformation.getProcessingDownloads().get(i))) {
                            number--;
                        }
                    }
                }
                if (SaveInformation.getProcessingDownloads().size() + number > SaveInformation.getSettingInformation().getLimitNumberOfDownload() && SaveInformation.getSettingInformation().getLimitNumberOfDownload() != 0) {
                    JOptionPane.showMessageDialog(null, "Number of downloads at same time is more than you select in setting!", "Error", JOptionPane.ERROR_MESSAGE);
                    for (int i = 0; i < SaveInformation.getDownloadClickeds().size(); i++) {
                        if (SaveInformation.getDownloadClickeds().get(i).isStopDownload()) {
                            SaveInformation.addToWaitingDownloads(SaveInformation.getQueuesDownload().get(i));
                        }
                    }
                } else {
                    if (SaveInformation.getDownloadClickeds() != null) {
                        for (int j = 0; j < SaveInformation.getDownloadClickeds().size(); j++) {
                            // this if is for checking that progress bar is completed or not
                            if (SaveInformation.getDownloadClickeds().get(j).getSizeOfDownloaded() < SaveInformation.getDownloadClickeds().get(j).getSizeOfDownload()) {
                                boolean test = false;
                                for (InformationNewDownload newDownload : SaveInformation.getProcessingDownloads()) {
                                    if (newDownload.equals(SaveInformation.getDownloadClickeds().get(j))) {
                                        test = true;
                                        break;
                                    }
                                }
                                if (!test) {
                                    SaveInformation.getDownloadClickeds().get(j).setStopDownload(false);
                                    SaveInformation.addToProcessingDownloads(SaveInformation.getDownloadClickeds().get(j));
                                }

                            }
                        }
                        CFrame.removeAllRightPanel();
                        CFrame.createRightPanel();
                    }
                    break;
                }
            }

            case "Pause": {
                if (SaveInformation.getDownloadClickeds() != null) {
                    // in this line should add needed processes
                    for (int j = 0; j < SaveInformation.getDownloadClickeds().size(); j++) {
                        // these lines are for removing progress bar from processing download
                        for (int i = 0; i < SaveInformation.getProcessingDownloads().size(); i++) {
                            if (SaveInformation.getProcessingDownloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                SaveInformation.getDownloadClickeds().get(j).setStopDownload(true);
                                SaveInformation.getProcessingDownloads().remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < SaveInformation.getThreadsDownloads().size(); i++) {
                            if (SaveInformation.getThreadsDownloads().get(i).getFile().equals(SaveInformation.getDownloadClickeds().get(j))) {
                                SaveInformation.getThreadsDownloads().get(i).stopFromPause();
                                SaveInformation.getThreadsDownloads().remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < SaveInformation.getWatingDowmloads().size(); i++) {
                            if (SaveInformation.getWatingDowmloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                SaveInformation.getWatingDowmloads().remove(i);
                                break;
                            }
                        }

                    }

                    CFrame.removeAllRightPanel();
                    CFrame.createRightPanel();
                }
                break;
            }
            case "Cancel": {
                if (SaveInformation.getDownloadClickeds() != null) {
                    // In first we should check that these clicked progress bars canceled before or not and show a frame to click yes or not
                    boolean test2 = false;
                    int selectedOption = JOptionPane.showConfirmDialog(null,
                            "Are you sure to cancel downloads?",
                            "Cancel",
                            JOptionPane.YES_NO_OPTION);
                    if (selectedOption == JOptionPane.YES_OPTION) {
                        test2 = true;
                    }
                    if (test2) {
                        // in this line should add needed processes
                        for (int j = 0; j < SaveInformation.getDownloadClickeds().size(); j++) {
                            // these lines are for removing progress bar from processing download
                            for (int i = 0; i < SaveInformation.getProcessingDownloads().size(); i++) {
                                if (SaveInformation.getProcessingDownloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getDownloadClickeds().get(j).setSizeOfDownloaded(0);
                                    SaveInformation.getDownloadClickeds().get(i).setStopDownload(true);
                                    SaveInformation.getProcessingDownloads().remove(i);

                                    // In this lines we stop thread and remove his not completed file
                                    for (int m = 0; m < SaveInformation.getThreadsDownloads().size(); m++) {
                                        if (SaveInformation.getThreadsDownloads().get(m).getFile().equals(SaveInformation.getProcessingDownloads().get(i))) {
                                            SaveInformation.getThreadsDownloads().get(m).stopFromCancelOrRemove();
                                            SaveInformation.getThreadsDownloads().remove(m);
                                        }
                                    }
                                    // this for is for remove download from waiting
                                    for (int k = 0; k < SaveInformation.getWatingDowmloads().size(); k++) {
                                        if (SaveInformation.getWatingDowmloads().get(k).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                            SaveInformation.getWatingDowmloads().remove(k);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    CFrame.removeAllRightPanel();
                    CFrame.createRightPanel();
                }
                break;
            }

            case "Remove": {

                if (!SaveInformation.getDownloadClickeds().isEmpty()) {
                    boolean test = false;
                    int selectedOption = JOptionPane.showConfirmDialog(null,
                            "Are you sure to remove?",
                            "Remove",
                            JOptionPane.YES_NO_OPTION);
                    if (selectedOption == JOptionPane.YES_OPTION) {
                        test = true;
                    }
                    if (test) {
                        // in these lines we checks all list of downloads for removing a download
                        for (int j = 0; j < SaveInformation.getDownloadClickeds().size(); j++) {

                            //these lines are for taking back up from removed downloads
                            try {
                                FileWriter fileWriter = new FileWriter("files/removed.jdm", true);
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                bufferedWriter.write("Name: " + SaveInformation.getDownloadClickeds().get(j).getNameNewDownload());
                                bufferedWriter.newLine();
                                bufferedWriter.write("Link: " + SaveInformation.getDownloadClickeds().get(j).getLinkNewDownload());
                                bufferedWriter.newLine();
                                bufferedWriter.write("Date: " + SaveInformation.getDownloadClickeds().get(j).getDateOfDownload());
                                bufferedWriter.newLine();
                                bufferedWriter.newLine();
                                bufferedWriter.close();
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "You can not remove file!", "Error", JOptionPane.ERROR_MESSAGE);

                            }

                            for (int i = 0; i < SaveInformation.getAllDownloads().size(); i++) {
                                if (SaveInformation.getAllDownloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getAllDownloads().remove(i);

                                    break;
                                }
                            }
                            for (int i = 0; i < SaveInformation.getQueuesDownload().size(); i++) {
                                if (SaveInformation.getQueuesDownload().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getQueuesDownload().remove(i);
                                    break;
                                }
                            }
                            for (int i = 0; i < SaveInformation.getCompletedDownloads().size(); i++) {
                                if (SaveInformation.getCompletedDownloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getCompletedDownloads().remove(i);
                                    break;
                                }
                            }
                            for (int i = 0; i < SaveInformation.getProcessingDownloads().size(); i++) {
                                if (SaveInformation.getProcessingDownloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getProcessingDownloads().remove(i);
                                    break;
                                }
                            }
                            for (int i = 0; i < SaveInformation.getSearchDownloads().size(); i++) {
                                if (SaveInformation.getSearchDownloads().get(i).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getSearchDownloads().remove(i);
                                    break;
                                }
                            }
                            // this for is for remove download from waiting
                            for (int k = 0; k < SaveInformation.getWatingDowmloads().size(); k++) {
                                if (SaveInformation.getWatingDowmloads().get(k).equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getWatingDowmloads().remove(k);
                                    break;
                                }
                            }

                            SaveInformation.getDownloadClickeds().get(j).setClicked(false);
                            // In this lines we check if download have a thread (is processing), we stop thread and remove his not completed file
                            for (int m = 0; m < SaveInformation.getThreadsDownloads().size(); m++) {
                                if (SaveInformation.getThreadsDownloads().get(m).getFile().equals(SaveInformation.getDownloadClickeds().get(j))) {
                                    SaveInformation.getThreadsDownloads().get(m).stopFromCancelOrRemove();
                                    SaveInformation.getThreadsDownloads().remove(m);
                                }
                            }
                        }
                        SaveInformation.getDownloadClickeds().clear();
                        CFrame.removeAllRightPanel();
                        CFrame.createRightPanel();
                    }
                }
                break;
            }

            case "Setting": {
                // this line is for create a CFrame for setting
                SettingFrame createSettingCFrame = new SettingFrame();
                createSettingCFrame.show();
                break;
            }

            case "Export": {
                try {
                    FileOutputStream fos = new FileOutputStream("files.zip");
                    ZipOutputStream zos = new ZipOutputStream(fos);
                    String[] filesPath = new String[2];
                    filesPath[0] = "files/queue.jdm";
                    filesPath[1] = "files/list.jdm";

                    for (String aFile : filesPath) {
                        zos.putNextEntry(new ZipEntry(new File(aFile).getName()));
                        byte[] bytes = Files.readAllBytes(Paths.get(aFile));
                        zos.write(bytes, 0, bytes.length);
                        zos.closeEntry();
                    }
                    zos.close();
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }
            case "Move up": {
                int firstIndex = 0;
                for (int i = 0; i < SaveInformation.getQueuesDownload().size(); i++) {
                    if (SaveInformation.getQueuesDownload().get(i).getClicked()) {
                        firstIndex++;
                    } else {
                        break;
                    }
                }
                InformationNewDownload temp;
                for (int i = firstIndex; i < SaveInformation.getQueuesDownload().size(); i++) {
                    if (SaveInformation.getQueuesDownload().size() > i + 1) {
                        if (!SaveInformation.getQueuesDownload().get(i).getClicked() && SaveInformation.getQueuesDownload().get(i + 1).getClicked()) {
                            temp = SaveInformation.getQueuesDownload().get(i);
                            SaveInformation.setNewToDownloadQueue(SaveInformation.getQueuesDownload().get(i + 1), i);
                            SaveInformation.setNewToDownloadQueue(temp, i + 1);
                        }
                    }
                }
                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
                break;
            }

            case "Move down": {
                int firstIndex = 0;
                for (int i = SaveInformation.getQueuesDownload().size() - 1; i >= 0; i--) {
                    if (SaveInformation.getQueuesDownload().get(i).getClicked()) {
                        firstIndex++;
                    } else {
                        break;
                    }
                }
                InformationNewDownload temp;
                for (int i = SaveInformation.getQueuesDownload().size() - firstIndex - 1; i >= 0; i--) {
                    if (i - 1 >= 0) {
                        if (!SaveInformation.getQueuesDownload().get(i).getClicked() && SaveInformation.getQueuesDownload().get(i - 1).getClicked()) {
                            temp = SaveInformation.getQueuesDownload().get(i);
                            SaveInformation.setNewToDownloadQueue(SaveInformation.getQueuesDownload().get(i - 1), i);
                            SaveInformation.setNewToDownloadQueue(temp, i - 1);
                        }
                    }
                }
                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
                break;
            }
            case "about": {
                // this line is for creating a CFrame for about IDM
                JOptionPane.showMessageDialog(null, "This program is a java download manager...\n" +
                        "This program is made by Majid Adibian , student of Amirkabir university.\n" +
                        "You can copy link of download and past it in URL n new download and download it.\n"+
                        "Queue is for downloads you want to download one after another.\n"+
                        "Also you can search and sort downloads.");
                break;
            }
            case "Exit": {
                //this line is for exit CFrame
                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Do you wanna close the window?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION) {
                    CFrame.disposeFrame();
                }
                break;
            }
            case "Add to queue": {

                for (int i = 0; i < SaveInformation.getDownloadClickeds().size(); i++) {
                    boolean test1 = true;
                    for (int j = 0; j < SaveInformation.getQueuesDownload().size(); j++) {
                        if (SaveInformation.getQueuesDownload().get(j).equals(SaveInformation.getDownloadClickeds().get(i))) {
                            test1 = false;
                        }
                    }
                    if (test1) {
                        SaveInformation.addToQueuesDownload(SaveInformation.getDownloadClickeds().get(i));
                    }
                }
                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
                break;
            }

            // this method is for remove a download from queue
            case "Remove from queue": {
                for (int i = 0; i < SaveInformation.getDownloadClickeds().size(); i++) {
                    SaveInformation.getQueuesDownload().remove(SaveInformation.getDownloadClickeds().get(i));
                }

                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
                break;
            }

            case "Start Queue": {
                for (int i = 0; i < SaveInformation.getQueuesDownload().size(); i++) {
                    if (!SaveInformation.getCompletedDownloads().contains(SaveInformation.getQueuesDownload().get(i)) && !SaveInformation.getProcessingDownloads().contains(SaveInformation.getQueuesDownload().get(i))) {
                        SaveInformation.addToProcessingDownloads(SaveInformation.getQueuesDownload().get(i));
                        break;
                    }
                }
                CFrame.removeAllRightPanel();
                CFrame.createRightPanel();
                break;
            }

            // these ifs are for action of sort menu items
            case "Name(Ascending)": {
                controlTypeOfSort("Name(Ascending)");
                CFrame.removeAllRightPanel();
                CFrame.removeAllTopPanel();
                CFrame.createRightPanel();
                CFrame.createTopPanel();
                break;

            }
            case "Name(Descending)": {
                controlTypeOfSort("Name(Descending)");
                CFrame.removeAllRightPanel();
                CFrame.removeAllTopPanel();
                CFrame.createRightPanel();
                CFrame.createTopPanel();
                break;
            }

            case "Size(Ascending)": {
                controlTypeOfSort("Size(Ascending)");
                CFrame.removeAllRightPanel();
                CFrame.removeAllTopPanel();
                CFrame.createRightPanel();
                CFrame.createTopPanel();
                break;
            }

            case "Size(Descending)": {
                controlTypeOfSort("Size(Descending)");

                CFrame.removeAllRightPanel();
                CFrame.removeAllTopPanel();
                CFrame.createRightPanel();
                CFrame.createTopPanel();
                break;
            }
            case "Date(Ascending)": {
                controlTypeOfSort("Date(Ascending)");
                CFrame.removeAllRightPanel();
                CFrame.removeAllTopPanel();
                CFrame.createRightPanel();
                CFrame.createTopPanel();
                break;
            }
            case "Date(Descending)": {
                controlTypeOfSort("Date(Descending)");
                CFrame.removeAllRightPanel();
                CFrame.removeAllTopPanel();
                CFrame.createRightPanel();
                CFrame.createTopPanel();
                break;
            }
        }

    }

    /**
     * In this method we control type of sort that user has entered that can not select ascending and descending at the same time
     *
     * @param typeOfSort is new type selected
     */
    private void controlTypeOfSort(String typeOfSort) {
        if (SaveInformation.getTypeOfSort().contains(typeOfSort)) {
            if (SaveInformation.getTypeOfSort().size() > 1) {
                SaveInformation.getTypeOfSort().remove(typeOfSort);
            }

        } else {
            if (typeOfSort.equals("Date(Descending)") && SaveInformation.getTypeOfSort().contains("Date(Ascending)")) {
                SaveInformation.getTypeOfSort().remove("Date(Ascending)");
            } else if (typeOfSort.equals("Date(Ascending)") && SaveInformation.getTypeOfSort().contains("Date(Descending)")) {
                SaveInformation.getTypeOfSort().remove("Date(Descending)");
            } else if (typeOfSort.equals("Size(Descending)") && SaveInformation.getTypeOfSort().contains("Size(Ascending)")) {
                SaveInformation.getTypeOfSort().remove("Size(Ascending)");
            } else if (typeOfSort.equals("Size(Ascending)") && SaveInformation.getTypeOfSort().contains("Size(Descending)")) {
                SaveInformation.getTypeOfSort().remove("Size(Descending)");
            } else if (typeOfSort.equals("Name(Descending)") && SaveInformation.getTypeOfSort().contains("Name(Ascending)")) {
                SaveInformation.getTypeOfSort().remove("Name(Ascending)");
            } else if (typeOfSort.equals("Name(Ascending)") && SaveInformation.getTypeOfSort().contains("Name(Descending)")) {
                SaveInformation.getTypeOfSort().remove("Name(Descending)");
            }
            SaveInformation.addToTypeOfSort(typeOfSort);
        }
    }

}

