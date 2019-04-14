
import javax.sound.sampled.Line;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class is for store information and use information in other class
 */
public class SaveInformation {
    private static InformationSetting settingInformation = new InformationSetting();
    private static ArrayList<InformationNewDownload> allDownloads = new ArrayList<>();
    // this field is array of clicked downloads
    private static ArrayList<InformationNewDownload> downloadClickeds = new ArrayList<>();
    private static ArrayList<InformationNewDownload> queuesDownloads = new ArrayList<>();
    private static ArrayList<InformationNewDownload> processingDownloads = new ArrayList<>();
    private static ArrayList<InformationNewDownload> completedDownloads = new ArrayList<>();
    // this field is array that we selected (queue or all download or processing or completed)
    private static ArrayList<InformationNewDownload> kindOfList = new ArrayList<>();
    // this field is String of name of list of download we select (queue or all download or processing or completed)
    private static String kindOfList1Name;
    //this field is for searching downloads
    private static ArrayList<InformationNewDownload> searchDownloads = new ArrayList<>();
    //this field is for saving type of sort
    private static ArrayList<String> typeOfSort = new ArrayList<>();
    // this field is a array of thread of downloads that are processing
    private static ArrayList<ThreadOfDownload> threadsDownloads = new ArrayList<>();
    //this field is for place of scroll bar for when we create right panel again we use it
    private static JViewport viewpoint = new JViewport();
    //this field is for language of all words in program
    private static HashMap<String , String> allWords = new HashMap<>();
    // thi array is for when we add new download but number of downloads at same time is more than we choice in setting
    private static ArrayList<InformationNewDownload> watingDowmloads = new ArrayList<>();

    public static void setSettingInformation(InformationSetting settingInformation) {
        SaveInformation.settingInformation = settingInformation;
    }

    public static InformationSetting getSettingInformation() {

        return settingInformation;
    }

    /**
     * this method adds a new download to first of downloads or last of downloads
     *
     * @param newDownloadInformation is new download added
     */
    public static void addNewToAllDownloads(InformationNewDownload newDownloadInformation) {
        allDownloads.add(newDownloadInformation);
    }

    public static ArrayList<InformationNewDownload> getAllDownloads() {
        return allDownloads;
    }

    public static void addToDownloadClicked(InformationNewDownload downloadClicked) {
        SaveInformation.downloadClickeds.add(downloadClicked);
    }

    public static ArrayList<InformationNewDownload> getDownloadClickeds() {
        return downloadClickeds;
    }

    public static void addToQueuesDownload(InformationNewDownload newDownload) {
        queuesDownloads.add(newDownload);
    }

    public static ArrayList<InformationNewDownload> getQueuesDownload() {
        return queuesDownloads;
    }

    /**
     * this method is for determine list of downloads that we selected
     *
     * @param kindOfList1
     */
    public static void setKindOfList(String kindOfList1) {
        if (kindOfList1.equals("Search")) {
            kindOfList = searchDownloads;
            kindOfList1Name = kindOfList1;
        } else if (kindOfList1.equals("Processing")) {
            kindOfList = processingDownloads;
            kindOfList1Name = kindOfList1;
        } else if (kindOfList1.equals("Completed")) {
            kindOfList = completedDownloads;
            kindOfList1Name = kindOfList1;
        } else if (kindOfList1.equals("Queues")) {
            kindOfList = queuesDownloads;
            kindOfList1Name = kindOfList1;
        } else if (kindOfList1.equals("Default")) {
            kindOfList = allDownloads;
            kindOfList1Name = kindOfList1;
        }
    }

    public static ArrayList<InformationNewDownload> getKindOfList() {
        return kindOfList;
    }

    public static ArrayList<InformationNewDownload> getProcessingDownloads() {
        return processingDownloads;
    }

    public static ArrayList<InformationNewDownload> getCompletedDownloads() {
        return completedDownloads;
    }

    public static void addToProcessingDownloads(InformationNewDownload newDownload) {
        processingDownloads.add(newDownload);
    }

    public static void addToCompletedDownloads(InformationNewDownload newDownload) {
        completedDownloads.add(newDownload);
    }

    public static String getKindOfList1Name() {
        return kindOfList1Name;
    }

    public static void setNewToDownloadQueue(InformationNewDownload newDownload, int i) {
        queuesDownloads.set(i, newDownload);
    }

    public static ArrayList<InformationNewDownload> getSearchDownloads() {
        return searchDownloads;
    }

    public static void setAllDownloads(ArrayList<InformationNewDownload> allDownloads) {
        SaveInformation.allDownloads = allDownloads;
    }

    public static void setDownloadClickeds(ArrayList<InformationNewDownload> downloadClickeds) {
        SaveInformation.downloadClickeds = downloadClickeds;
    }

    public static void setQueuesDownloads(ArrayList<InformationNewDownload> queuesDownloads) {
        SaveInformation.queuesDownloads = queuesDownloads;
    }

    public static void setProcessingDownloads(ArrayList<InformationNewDownload> processingDownloads) {
        SaveInformation.processingDownloads = processingDownloads;
    }

    public static void setCompletedDownloads(ArrayList<InformationNewDownload> completedDownloads) {
        SaveInformation.completedDownloads = completedDownloads;
    }

    public static void addToSearchDownloads(InformationNewDownload download) {
        searchDownloads.add(download);
    }


public static void addToThreadsDownloads(ThreadOfDownload thread){
        threadsDownloads.add(thread);
}

    public static ArrayList<ThreadOfDownload> getThreadsDownloads() {
        return threadsDownloads;
    }


    public static ArrayList<String> getTypeOfSort() {
        return typeOfSort;
    }

    public static void setTypeOfSort(ArrayList<String> typeOfSort) {
        SaveInformation.typeOfSort = typeOfSort;
    }

    public static void addToTypeOfSort(String typeOfSort1){
        typeOfSort.add(typeOfSort1);
    }

    public static JViewport getViewport() {
        return viewpoint;
    }

    public static void setViewport(JViewport viewpoint) {
        SaveInformation.viewpoint = viewpoint;
    }

    public static HashMap<String, String> getAllWords() {
        return allWords;
    }

    public static void setAllWords(HashMap<String, String> allWords) {
        SaveInformation.allWords = allWords;
    }

    public static ArrayList<InformationNewDownload> getWatingDowmloads() {
        return watingDowmloads;
    }
    public static void addToWaitingDownloads(InformationNewDownload newDownload){
        watingDowmloads.add(newDownload);
    }
}

