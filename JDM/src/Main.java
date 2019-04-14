import javax.swing.*;
import javax.xml.soap.SAAJMetaFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Name;
import static javax.swing.text.StyleConstants.Size;

/**
 * this class is Main Class
 */

public class Main {
    public static void main(String[] args) {

        // thi line is for initialize information
        initializeInformation();
        CFrame.createFrame();
    }


    /**
     * this method is for  initialize some fields
     */
    private static void initializeInformation() {
        ArrayList<InformationNewDownload> tempQueueDownloads = new ArrayList<>();
        try (FileInputStream fs = new FileInputStream("files/queue.jdm")) {
            ObjectInputStream os = new ObjectInputStream(fs);
            ArrayList<InformationNewDownload> queueDownloads = (ArrayList<InformationNewDownload>) os.readObject();
            os.close();
            fs.close();
            SaveInformation.setQueuesDownloads(queueDownloads);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDM could not find file of your queue!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try (FileInputStream fs = new FileInputStream("files/list.jdm")) {
            ObjectInputStream os = new ObjectInputStream(fs);
            ArrayList<InformationNewDownload> allDownloads = (ArrayList<InformationNewDownload>) os.readObject();
            os.close();
            fs.close();
            SaveInformation.setAllDownloads(allDownloads);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDM could not find file of your all downloads!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try (FileInputStream fs = new FileInputStream("files/settings.jdm")) {
            ObjectInputStream os = new ObjectInputStream(fs);
            InformationSetting settingDownloads = (InformationSetting) os.readObject();
            SaveInformation.setSettingInformation(settingDownloads);
            os.close();
            fs.close();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDM could not find file of your saved setting!", "Error", JOptionPane.ERROR_MESSAGE);
        }


        for (int i = 0; i < SaveInformation.getAllDownloads().size(); i++) {
            for (int j = 0; j < SaveInformation.getQueuesDownload().size(); j++) {
                if (SaveInformation.getQueuesDownload().get(j).equals(SaveInformation.getAllDownloads().get(i))) {
                    tempQueueDownloads.add(SaveInformation.getAllDownloads().get(i));
                }
            }
            if (SaveInformation.getAllDownloads().get(i).getSizeOfDownload() <= SaveInformation.getAllDownloads().get(i).getSizeOfDownloaded() && SaveInformation.getAllDownloads().get(i).getSizeOfDownload()!= 0) {
                SaveInformation.addToCompletedDownloads(SaveInformation.getAllDownloads().get(i));
            }
            SaveInformation.getAllDownloads().get(i).setClicked(false);
        }
        SaveInformation.setQueuesDownloads(tempQueueDownloads);


        SaveInformation.setKindOfList("Default");
        SaveInformation.addToTypeOfSort("Date(Descending)");

        File file;
        HashMap<String, String> allWords = new HashMap<>();
        String word2;
        try {
            if (SaveInformation.getSettingInformation().getLanguage().equals("English") || SaveInformation.getSettingInformation().getLanguage().equals("انگلیسی")) {
                file = new File("files/all words_E.txt");
            } else {
                file = new File("files/all words_F.txt");
            }

            BufferedReader word = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            while ((word2 = word.readLine()) != null) {
                allWords.put(word2.substring(0, word2.indexOf(",")), word2.substring(word2.indexOf(",") + 1, word2.length()));
            }

            SaveInformation.setAllWords(allWords);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "JDM can not fined file of words!", "Error", JOptionPane.ERROR_MESSAGE);
            setHashMapOfWords();
        }
    }


    private static void setHashMapOfWords() {
        HashMap<String, String> allWords = new HashMap<>();
        allWords.put("Help", "Help");
        allWords.put("Download", "Download");
        allWords.put("Sort by", "Sort by");
        allWords.put("about", "about");
        allWords.put("New Download", "New Download");
        allWords.put("Resume", "Resume");
        allWords.put("Pause", "Pause");
        allWords.put("Cancel", "Cancel");
        allWords.put("Remove", "Remove");
        allWords.put("Open", "Open");
        allWords.put("Setting", "Setting");
        allWords.put("Exit", "Exit");
        allWords.put("Name(Ascending)", "Name(Ascending)");
        allWords.put("Name(Descending)", "Name(Descending)");
        allWords.put("Size(Ascending)", "Size(Ascending)");
        allWords.put("Size(Descending)", "Size(Descending)");
        allWords.put("Date(Ascending)", "Date(Ascending)");
        allWords.put("Date(Descending)", "Date(Descending)");
        allWords.put("Remove from queue", "Remove from queue");
        allWords.put("Move up", "Move up");
        allWords.put("Move down", "Move down");
        allWords.put("Start Queue", "Start Queue");
        allWords.put("Add to queue", "Add to queue");
        allWords.put("Search", "Search");
        allWords.put("Processing", "Processing");
        allWords.put("Completed", "Completed");
        allWords.put("Queue", "Queue");
        allWords.put("All downloads", "All downloads");
        allWords.put("Look and feel", "Look and feel");
        allWords.put("infinite", "infinite");
        allWords.put("finite", "finite");
        allWords.put("limit number of download", "limit number of download");
        allWords.put("Path", "Path");
        allWords.put("Brows", "Brows");
        allWords.put("Unauthorized sites list", "Unauthorized sites list");
        allWords.put("Add", "Add");
        allWords.put("Save", "Save");
        allWords.put("Number of download at same time can not be less than one!", "Number of download at same time can not be less than one!");
        allWords.put("Number of download at same time is less than number of downloads is processing!", "Number of download at same time is less than number of downloads is processing!");
        allWords.put("Error", "Error");
        allWords.put("JDM could not save your change!", "JDM could not save your change!");
        allWords.put("Are you sure to cancel download?", "Are you sure to cancel download?");
        allWords.put("Name", "Name");
        allWords.put("Size", "Size");
        allWords.put("Downloaded", "Downloaded");
        allWords.put("Speed", "Speed");
        allWords.put("URL", "URL");
        allWords.put("Save To", "Save To");
        allWords.put("Date", "Date");
        allWords.put("JDM could not find file of your queue!", "JDM could not find file of your queue!");
        allWords.put("JDM could not find file of your all downloads!", "JDM could not find file of your all downloads!");
        allWords.put("JDM could not find file of your saved setting!", "JDM could not find file of your saved setting!");
        allWords.put("You can not open file before Before completing downloading!", "You can not open file before Before completing downloading!");
        allWords.put("select look and feel", "select look and feel");
        allWords.put("English", "English");
        allWords.put("Pension", "Pension");
        allWords.put("More", "More");
        allWords.put("New download", "New download");
        allWords.put("For Change language you should run JDM again!", "For Change language you should run JDM again!");
        allWords.put("Download now", "Download now");
        allWords.put("Download in queue", "Download in queue");
        allWords.put("OK", "OK");
        allWords.put("You do not enter any URL!", "You do not enter any URL!");
        allWords.put("You entered a unauthorized URL!", "You entered a unauthorized URL!");
        allWords.put("General", "General");
        allWords.put("Number of downloads at same time is more than you select in setting!", "Number of downloads at same time is more than you select in setting!");
        allWords.put("select language", "select language");
        allWords.put("number of downloads at same time", "number of downloads at same time");
        allWords.put("there is a problem in connection!", "there is a problem in connection!");
        allWords.put("Export", "Export");

        SaveInformation.setAllWords(allWords);
        SaveInformation.getSettingInformation().setLanguage("English");
    }
}