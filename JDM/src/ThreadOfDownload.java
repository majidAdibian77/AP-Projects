import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * this class is a Thread of download
 * in this class we create 8 threads for 8 part of download to increase speed of download
 * in process of this class gui changes
 */
public class ThreadOfDownload extends SwingWorker {
    // file is file of we want to download
    private InformationNewDownload file;
    // this field is a object of class that create panel of progress bar
    private CreateProgressBar progressBarClass;
    // this field is for when we stop download
    private boolean exit = false;
    // this field is size of downloaded
    private long fileDownloadedSize;
    // this field is size of file we want to download
    private long fileDownloadSize;
    private String fileName = "";
    private String saveFilePath = SaveInformation.getSettingInformation().getPathOfDownload();
    // this field is for when download is completed
    private boolean isCompleted = false;
    private Timer timer = new Timer();
    // this field is used in Timer for speed
    private long tempSizeofDownload = 0;

    public ThreadOfDownload(CreateProgressBar progressBarClass, InformationNewDownload download) {
        this.file = download;
        this.progressBarClass = progressBarClass;
        fileDownloadSize = file.getSizeOfDownload();
    }

    @Override
    protected Object doInBackground() {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(file.getLinkNewDownload());
            if(url.getProtocol().equals("http")) {
                connection = (HttpURLConnection) url.openConnection();
            }else if(url.getProtocol().equals("https")){
                connection = (HttpsURLConnection) url.openConnection();
            }
            int responseCode = connection.getResponseCode();
            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.disconnect();
            }
        } catch (IOException e) {
            exit = true;
            JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("there is a problem in connection!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            timer.cancel();
            checkQueueAndWaitingDownloads();
            SaveInformation.getWatingDowmloads().remove(file);
            SaveInformation.getProcessingDownloads().remove(file);
        }
        // this if is for when thread goes to before catch
        if (!exit) {
            fileName = file.getNameNewDownload();
            //these lines are for timer for speed of download
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    String speed = Sort.recognizeSizeType((fileDownloadedSize - tempSizeofDownload) * 2) + "/s";
                    file.setSpeedOfDownload(speed);
                    progressBarClass.getSpeed().setText( "Speed:" + speed);
                    tempSizeofDownload = fileDownloadedSize;
                }
            }, 0, 500);
            for (int i = 0; i < 8; i++) {
                ThreadPart threadPart;
                if (i != 7) {
                    threadPart = new ThreadPart(i, (fileDownloadSize / 8) * i, (fileDownloadSize / 8) * (i + 1));
                } else {
                    threadPart = new ThreadPart(i, (fileDownloadSize / 8) * i, fileDownloadSize);
                }
                threadPart.start();
            }
        }
        return null;
    }

    /**
     * in this method of Swing worker we change gui
     */
    @Override
    protected void process(List chunks) {
        fileDownloadedSize = getSizeOfFiles();
        file.setSizeOfDownloaded(fileDownloadedSize);
        progressBarClass.getSizeOfFile().setText("Size:" + Sort.recognizeSizeType(file.getSizeOfDownload()));
        progressBarClass.getProgressBar().setValue((int) ((100 * file.getSizeOfDownloaded()) / file.getSizeOfDownload()));
        progressBarClass.getDownloadedLabel().setText("Downloaded:" + Sort.recognizeSizeType(file.getSizeOfDownloaded()));
        String s = ((100 * file.getSizeOfDownloaded()) / file.getSizeOfDownload()) + "";
        if (Float.parseFloat(s) > 100) {
            s = "100";
        }
        progressBarClass.getDownloadPercentageLabel().setText(Sort.cuttingNumber(s) + "%");
        progressBarClass.getProgressBar().repaint();
        progressBarClass.getPanelOfProgress().repaint();


        if (fileDownloadedSize >= fileDownloadSize && !isCompleted) {
            isCompleted = true;
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                FileOutputStream OutputStreamResult = new FileOutputStream(saveFilePath + "\\" + fileName, true);
                for (int j = 0; j < 8; j++) {
                    File file = new File(saveFilePath + "\\" + fileName + j);
                    FileInputStream inputStreamResult = new FileInputStream(file);
                    while ((bytesRead = inputStreamResult.read(buffer)) != -1) {
                        OutputStreamResult.write(buffer, 0, bytesRead);
                    }
                    inputStreamResult.close();
                    file.delete();
                }
                OutputStreamResult.close();
                // this if is for remove completed downloads
                timer.cancel();
                SaveInformation.getWatingDowmloads().remove(file);
                SaveInformation.getProcessingDownloads().remove(file);
                SaveInformation.getCompletedDownloads().add(file);
                for (int i = 0; i < SaveInformation.getThreadsDownloads().size(); i++) {
                    if (SaveInformation.getThreadsDownloads().get(i).getFile().equals(file)) {
                        SaveInformation.getThreadsDownloads().remove(i);
                    }
                }
                checkQueueAndWaitingDownloads();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("there is a problem in connection!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void stopFromPause() {
        exit = true;
    }

    public void stopFromCancelOrRemove() {
        exit = true;
        for (int i = 0 ; i < 8 ; i++) {
            File file = new File(SaveInformation.getSettingInformation().getPathOfDownload() + "\\" + fileName + i);
            file.delete();
        }
    }

    public InformationNewDownload getFile() {
        return file;
    }

    public CreateProgressBar getProgressBarClass() {
        return progressBarClass;
    }

    private class ThreadPart extends Thread {

        private long firstSize;
        private long lastSize;
        private int i;

        private ThreadPart(int i, long firstSize, long lastSize) {
            this.i = i;
            this.firstSize = firstSize;
            this.lastSize = lastSize;
        }

        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(file.getLinkNewDownload());
                HttpURLConnection connection = null;
                if(url.getProtocol().equals("http")) {
                    connection = (HttpURLConnection) url.openConnection();
                }else if(url.getProtocol().equals("https")){
                    connection = (HttpsURLConnection) url.openConnection();
                }
                File fileOfPart = new File(saveFilePath + "\\" + fileName + i);
                firstSize += fileOfPart.length();
                connection.setRequestProperty("Range", "bytes=" + firstSize + "-" + (lastSize - 1));
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath + "\\" + fileName + i, true);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1 && !exit) {
                    outputStream.write(buffer, 0, bytesRead);
                    publish();
                }
                outputStream.close();
                inputStream.close();
                connection.disconnect();

                if (exit) {
                    timer.cancel();
                    if(!SaveInformation.getWatingDowmloads().isEmpty()){
                        SaveInformation.addToProcessingDownloads(SaveInformation.getWatingDowmloads().get(0));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("there is a problem in connection!"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
                timer.cancel();
                stopFromPause();
                checkQueueAndWaitingDownloads();
                SaveInformation.getWatingDowmloads().remove(file);
                SaveInformation.getProcessingDownloads().remove(file);
            }
        }
    }

    /**
     * these lines are for when start queue clicked to go to next download
     */
    private void checkQueueAndWaitingDownloads() {
        // check that is there download is waiting to start or not
        if (!SaveInformation.getWatingDowmloads().isEmpty()) {
            SaveInformation.getProcessingDownloads().add(SaveInformation.getWatingDowmloads().get(0));

            CFrame.removeAllRightPanel();
            CFrame.createRightPanel();
            // we check that this download is in queue or not
        } else if (SaveInformation.getQueuesDownload().contains(file) && SaveInformation.getKindOfList().equals(SaveInformation.getQueuesDownload())) {
            boolean test1 = true;
            // we check that this download is started from (start queue) or from (resume)
            for (int i = 0; i < SaveInformation.getQueuesDownload().indexOf(file); i++) {
                if (SaveInformation.getQueuesDownload().get(i).getSizeOfDownloaded() < SaveInformation.getQueuesDownload().get(i).getSizeOfDownload()) {
                    test1 = false;
                }
            }
            //checking this download is last download of queue or ot
            if (SaveInformation.getQueuesDownload().indexOf(file) == SaveInformation.getQueuesDownload().size() - 1) {
                test1 = false;
            }
            if (test1) {
                boolean test2 = false;
                int i;
                // checking after download in queue is completed or is processing or not
                for (i = SaveInformation.getQueuesDownload().indexOf(file) + 1; i < SaveInformation.getQueuesDownload().size(); i++) {
                    if (!SaveInformation.getCompletedDownloads().contains(SaveInformation.getQueuesDownload().get(i)) && !SaveInformation.getProcessingDownloads().contains(SaveInformation.getQueuesDownload().get(i))) {
                        test2 = true;
                        break;
                    }
                }
                if (test2) {
                    SaveInformation.getProcessingDownloads().add(SaveInformation.getQueuesDownload().get(i));
                    CFrame.removeAllRightPanel();
                    CFrame.createRightPanel();
                }
            }
        }
    }

    /**
     * this method compute sum of size of files part of downloads
     * @return
     */
    private long getSizeOfFiles() {
        long fileDownloadedSize2 = 0;
        for (int i = 0; i < 8; i++) {
            File file = new File(saveFilePath + "\\" + fileName + i);
            fileDownloadedSize2 += file.length();
        }
        return fileDownloadedSize2;
    }
}