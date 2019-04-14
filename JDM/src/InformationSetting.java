
import java.io.Serializable;
import java.util.ArrayList;

public class InformationSetting implements Serializable {
    private boolean infinite = true;
    private int limitNumberOfDownload = 0;
    private String pathOfDownload = "C:\\Users\\Majid\\Desktop";
    private ArrayList<String> unauthorizedSites = new ArrayList<>();
    private String lookAndFeel = "Windows look and feel";
    private String  language = "English";

    public int getLimitNumberOfDownload() {
        return limitNumberOfDownload;
    }

    public String getPathOfDownload() {
        return pathOfDownload;
    }

    public boolean isInfinite() {
        return infinite;
    }


    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public void setInfinite(boolean infinite) {

        this.infinite = infinite;
    }

    public void setLimitNumberOfDownload(int limitNumberOfDownload) {
        this.limitNumberOfDownload = limitNumberOfDownload;
    }

    public void setPathOfDownload(String pathOfDownload) {
        this.pathOfDownload = pathOfDownload;
    }

    public ArrayList<String> getUnauthorizedSitesAdded() {
        return unauthorizedSites;
    }

    public void addToUnauthorizedSitesAdded(String unauthorizedSite){
        this.unauthorizedSites.add(unauthorizedSite);
    }

    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
