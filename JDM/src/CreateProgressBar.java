import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * this class creates a progress bar
 */
public class CreateProgressBar {
    private JPanel panelOfProgress;
    private JPanel rightPanel;
    private InformationNewDownload newDownload;
    private ArrayList<JButton> buttons = new ArrayList<>();
    // these under fields are for using in Thread
    // this field is amount of downloaded
    private JLabel downloadedLabel;
    // this field is size of downloaded
    private JLabel speedDownloadLabel;
    // this field is downloadPercentage

    private JLabel downloadPercentageLabel;
    private JLabel sizeOfFile;
    //this field is progressbar
    private JProgressBar progressBar;
    public CreateProgressBar(InformationNewDownload newDownload , JPanel rightPanel) {
        this.newDownload = newDownload;
        this.rightPanel = rightPanel;

        panelOfProgress = new JPanel(new BorderLayout());
        panelOfProgress.setBorder(new EmptyBorder(10, 5, 0, 20));
        JPanel southOfProgressBar = new JPanel(new BorderLayout());
        southOfProgressBar.setOpaque(false);

        progressBar = new JProgressBar(0, 0, 100);
        if(newDownload.getSizeOfDownload() != 0) {
            progressBar.setValue((int) (100 * ((float)newDownload.getSizeOfDownloaded() / (float)newDownload.getSizeOfDownload())));
        }else {
            progressBar.setValue(0);
        }
        panelOfProgress.add(progressBar, BorderLayout.CENTER);
        progressBar.setPreferredSize(new Dimension(0, 20));

        panelOfProgress.add(southOfProgressBar, BorderLayout.SOUTH);

        createLabelInProgressBar(southOfProgressBar);
        createButtonInProgressBar(southOfProgressBar);

        JLabel image = new JLabel(new ImageIcon("imageIcon/downloaded.png"));
        image.setBorder(new EmptyBorder(0, 10, 0, 10));

        panelOfProgress.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        panelOfProgress.add(image, BorderLayout.WEST);


    }

    /**
     * this method create buttons in progress bar
     *
     * @param southOfProgressBar
     */
    private void createButtonInProgressBar(JPanel southOfProgressBar) {
        ActionProgressBar clickOnButtonInProgressBar = new ActionProgressBar(newDownload , rightPanel , panelOfProgress);

        JPanel buttonsInProgressBar = new JPanel(new FlowLayout());
        buttonsInProgressBar.setOpaque(false);
        ImageIcon resumeImage = new ImageIcon("imageIcon/resumeProgressBar.png");
        JButton resumeButton = buttonMaker(resumeImage, "Resume");
        buttonsInProgressBar.add(resumeButton);
        buttons.add(resumeButton);
        resumeButton.addActionListener(clickOnButtonInProgressBar);
        ImageIcon pauseImage = new ImageIcon("imageIcon/pauseProgressBar.png");
        JButton pauseButton = buttonMaker(pauseImage, "Pause");
        buttonsInProgressBar.add(pauseButton);
        buttons.add(pauseButton);
        pauseButton.addActionListener(clickOnButtonInProgressBar);
        ImageIcon cancelImage = new ImageIcon("imageIcon/cancelProgressBar.png");
        JButton cancelButton = buttonMaker(cancelImage, "Cancel");
        buttonsInProgressBar.add(cancelButton);
        buttons.add(cancelButton);
        cancelButton.addActionListener(clickOnButtonInProgressBar);
        pauseButton.addActionListener(clickOnButtonInProgressBar);

        ImageIcon openImage = new ImageIcon("imageIcon/openProgressBar2.png");
        JButton openButton = buttonMaker(openImage, "Open");
        buttonsInProgressBar.add(openButton);
        buttons.add(openButton);
        openButton.addActionListener(clickOnButtonInProgressBar);

        southOfProgressBar.add(buttonsInProgressBar, BorderLayout.WEST);
    }

    /**
     * we use this method for create buttons
     *
     * @param imageIcon is image of button
     * @param name      is name and tooltip of button
     */
    private JButton buttonMaker(ImageIcon imageIcon, String name) {
        JButton button = new JButton();
        button.setIcon(imageIcon);
        button.setName(name);
        button.setToolTipText(SaveInformation.getAllWords().get(name));
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(30, 50));
        return button;
    }

    /**
     * @param southOfProgressBar is a panel that is south of progress bar to add som button and label to it
     */
    private void createLabelInProgressBar(JPanel southOfProgressBar) {
        ActionProgressBar clickOnProgressBar = new ActionProgressBar(newDownload , rightPanel , panelOfProgress);
        JPanel infoOfDownload = new JPanel(new GridLayout(1, 2));

        // in these lines we determine percent of downloaded
        String s;
        if(newDownload.getSizeOfDownload() != 0) {
            s = ((100 *newDownload.getSizeOfDownloaded()) / newDownload.getSizeOfDownload()) + "";
            if (Integer.parseInt(s) > 100) {
                s = "100";
            }
        }else {
            s = "0";
        }
        downloadPercentageLabel = new JLabel(Sort.cuttingNumber(s) + "%");
        downloadPercentageLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
        panelOfProgress.add(downloadPercentageLabel, BorderLayout.EAST);

        // in these lines we create some label to show som ditals of download
        JLabel name = new JLabel(SaveInformation.getAllWords().get("Name") + ": " + newDownload.getNameNewDownload());
        panelOfProgress.add(name, BorderLayout.NORTH);
        name.setBorder(new EmptyBorder(0, 45, 5, 5));

        sizeOfFile = new JLabel(SaveInformation.getAllWords().get("Size") + ": " + Sort.recognizeSizeType(newDownload.getSizeOfDownload()));
        infoOfDownload.add(sizeOfFile);
        sizeOfFile.setBorder(new EmptyBorder(0, 10, 0, 5));

        downloadedLabel = new JLabel(SaveInformation.getAllWords().get("Downloaded") + ": " + Sort.recognizeSizeType(newDownload.getSizeOfDownloaded()));
        infoOfDownload.add(downloadedLabel);
        downloadedLabel.setBorder(new EmptyBorder(0, 0, 0, 5));

        speedDownloadLabel = new JLabel(SaveInformation.getAllWords().get("Speed") + ": 0.0");
        infoOfDownload.add(speedDownloadLabel);
        speedDownloadLabel.setBorder(new EmptyBorder(0, 0, 0, 5));

        southOfProgressBar.add(infoOfDownload, BorderLayout.CENTER);

        panelOfProgress.addMouseListener(clickOnProgressBar);

        infoOfDownload.setOpaque(false);

        if (newDownload.getClicked()) {
            panelOfProgress.setBackground(Color.cyan);
        } else {
            panelOfProgress.setBackground(Color.WHITE);
        }

    }



    public JLabel getDownloadedLabel() {
        return downloadedLabel;
    }

    public JLabel getSpeed() {
        return speedDownloadLabel;
    }

    public JLabel getDownloadPercentageLabel() {
        return downloadPercentageLabel;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JPanel getPanelOfProgress() {
        return panelOfProgress;
    }

    public JLabel getSizeOfFile() {
        return sizeOfFile;
    }

}
