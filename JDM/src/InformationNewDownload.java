
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * this class is used for every new download
 */
public class InformationNewDownload implements Serializable {
    private String linkNewDownload;
    private String nameNewDownload;
    private boolean downloadNow;
    private long sizeOfDownload;
    private long sizeOfDownloaded;
    private String speedOfDownload;
    private boolean clicked = false;
    private Date dateOfDownload = new Date();
    private boolean stopDownload;

    public void setStopDownload(boolean stopDownload) {
        this.stopDownload = stopDownload;
    }

    public boolean isStopDownload() {
        return stopDownload;
    }

    public void setLinkNewDownload(String linkNewDownload) {
        this.linkNewDownload = linkNewDownload;
    }

    public void setNameNewDownload(String nameNewDownload) {
        this.nameNewDownload = nameNewDownload;
    }

    public void setDownloadNow(boolean wayOfDowload) {
        this.downloadNow = wayOfDowload;
    }

    public void setSizeOfDownload(long sizeOfDownload) {
        this.sizeOfDownload = sizeOfDownload;
    }

    public String getLinkNewDownload() {

        return linkNewDownload;

    }

    public String getNameNewDownload() {
        return nameNewDownload;
    }

    public boolean isDownloadNow() {
        return downloadNow;
    }

    public long getSizeOfDownload() {
        return sizeOfDownload;
    }

    public long getSizeOfDownloaded() {
        return sizeOfDownloaded;
    }

    public void setSizeOfDownloaded(long sizeOfDownloaded) {
        this.sizeOfDownloaded = sizeOfDownloaded;
    }

    public void setClicked(boolean clicked){
        this.clicked = clicked;
    }

    public boolean getClicked(){
        return clicked;
    }

    public String getSpeedOfDownload() {
        return speedOfDownload;
    }

    public void setSpeedOfDownload(String speedOfDownload) {
        this.speedOfDownload = speedOfDownload;
    }

    public Date getDateOfDownload() {

        return dateOfDownload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationNewDownload download = (InformationNewDownload) o;
        return downloadNow == download.downloadNow &&
                Float.compare(download.sizeOfDownload, sizeOfDownload) == 0 &&
                Float.compare(download.sizeOfDownloaded, sizeOfDownloaded) == 0 &&
                clicked == download.clicked &&
                stopDownload == download.stopDownload &&
                Objects.equals(linkNewDownload, download.linkNewDownload) &&
                Objects.equals(nameNewDownload, download.nameNewDownload) &&
                Objects.equals(dateOfDownload, download.dateOfDownload);
    }

    @Override
    public int hashCode() {

        return Objects.hash(linkNewDownload, nameNewDownload, downloadNow, sizeOfDownload, sizeOfDownloaded, speedOfDownload, clicked, dateOfDownload, stopDownload);
    }
}
