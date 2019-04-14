
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * this class is for create right panel
 */
public class RightPanel {
    private ArrayList<JButton> buttons;
    private ArrayList<CreateProgressBar> progressBarsDownloads = new ArrayList<>();
    private JPanel rightPanel;
    private JTextField searchText;

    public RightPanel(JPanel rightPanel) {

        this.rightPanel = rightPanel;

        buttons = new ArrayList<>();
        JPanel rightTopPanel = new JPanel();
        createToolBar(rightTopPanel);
        rightPanel.add(rightTopPanel, BorderLayout.NORTH);
        JPanel rightMainPanel;
        // these lines are for set number of rows in GridLayout to use scrollPane
        if (SaveInformation.getKindOfList1Name().equals("Search")) {
            if (SaveInformation.getKindOfList().size() < 5) {
                rightMainPanel = new JPanel(new GridLayout(6, 1));
            } else {
                rightMainPanel = new JPanel(new GridLayout(SaveInformation.getKindOfList().size() + 1, 1));
            }
        } else {
            if (SaveInformation.getKindOfList().size() < 6) {
                rightMainPanel = new JPanel(new GridLayout(6, 1));
            } else {
                rightMainPanel = new JPanel(new GridLayout(SaveInformation.getKindOfList().size(), 1));
            }
        }
        JScrollPane scrollPane = new JScrollPane(rightMainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        if (SaveInformation.getKindOfList1Name().equals("Search")) {
            createPanelForSearch(rightMainPanel);
        }
        createProgressBars(rightMainPanel, rightPanel);
        setColor(rightTopPanel);

    }

    /**
     * this method is for create tool bar
     *
     * @param rightTopPanel is a panel that tool bar adds to it
     */
    private void createToolBar(JPanel rightTopPanel) {
        JPanel toolBar = new JPanel(new FlowLayout());
        toolBar.setOpaque(false);

        ActionTollBar handler = new ActionTollBar();

        ImageIcon newImage = new ImageIcon("imageIcon/new1.png");
        JButton newDownloadButton = buttonMaker(newImage, "New download");
        toolBar.add(newDownloadButton);
        buttons.add(newDownloadButton);
        newDownloadButton.addActionListener(handler);
        ImageIcon resumeImage = new ImageIcon("imageIcon/resume1.png");
        JButton resumeButton = buttonMaker(resumeImage, "Resume");
        toolBar.add(resumeButton);
        buttons.add(resumeButton);
        resumeButton.addActionListener(handler);
        ImageIcon pauseImage = new ImageIcon("imageIcon/pause1.png");
        JButton pauseButton = buttonMaker(pauseImage, "Pause");
        toolBar.add(pauseButton);
        buttons.add(pauseButton);
        pauseButton.addActionListener(handler);
        ImageIcon cancelImage = new ImageIcon("imageIcon/cancel1.png");
        JButton cancelButton = buttonMaker(cancelImage, "Cancel");
        toolBar.add(cancelButton);
        buttons.add(cancelButton);
        cancelButton.addActionListener(handler);
        ImageIcon removeImage = new ImageIcon("imageIcon/remove1.png");
        JButton removeButton = buttonMaker(removeImage, "Remove");
        toolBar.add(removeButton);
        buttons.add(removeButton);
        removeButton.addActionListener(handler);
        ImageIcon settingImage = new ImageIcon("imageIcon/setting1.png");
        JButton settingButton = buttonMaker(settingImage, "Setting");
        toolBar.add(settingButton);
        buttons.add(settingButton);
        settingButton.addActionListener(handler);


        // these lines are for create move up and down and remove from queue when queue button is clicked
        if (SaveInformation.getKindOfList1Name().equals("Queues")) {
            ImageIcon RemoveFromQueueImage = new ImageIcon("imageIcon/remove from queue.png");
            JButton RemoveFromQueue = buttonMaker(RemoveFromQueueImage, "Remove from queue");
            toolBar.add(RemoveFromQueue);
            buttons.add(RemoveFromQueue);
            RemoveFromQueue.addActionListener(handler);


            ImageIcon moveUpImage = new ImageIcon("imageIcon/moveUp3.png");
            JButton moveUpButton = buttonMaker(moveUpImage, "Move up");
            ImageIcon moveDownImage = new ImageIcon("imageIcon/moveDown2.png");
            JButton moveDownButton = buttonMaker(moveDownImage, "Move down");

//            if (SaveInformation.getKindOfList().size() < 2 || SaveInformation.getDownloadClickeds().isEmpty()) {
//                moveUpButton.setEnabled(false);
//                moveDownButton.setEnabled(false);
//            } else {
//                moveUpButton.setEnabled(true);
//                moveDownButton.setEnabled(true);
//            }
            toolBar.add(moveUpButton);
            toolBar.add(moveDownButton);
            buttons.add(moveDownButton);
            buttons.add(moveUpButton);
            moveDownButton.addActionListener(handler);
            moveUpButton.addActionListener(handler);

            ImageIcon startQueueIcon = new ImageIcon("imageIcon/start queue.png");
            JButton startQueue = buttonMaker(startQueueIcon, "Start Queue");
            toolBar.add(startQueue);
            buttons.add(startQueue);
            startQueue.addActionListener(handler);

        }
        // this button is just for Default to add a download to queue
        if (SaveInformation.getKindOfList1Name().equals("Default")) {
            ImageIcon addToQueueImage = new ImageIcon("imageIcon/add to queue.png");
            JButton addToQueue = buttonMaker(addToQueueImage, "Add to queue");

            toolBar.add(addToQueue);
            buttons.add(addToQueue);
            addToQueue.addActionListener(handler);
        }

        rightTopPanel.add(toolBar);
    }

    /**
     * this method is for create all buttons
     *
     * @param imageIcon is image of button
     * @param name      is name of button
     * @return
     */
    private JButton buttonMaker(ImageIcon imageIcon, String name) {
        JButton button = new JButton();
        button.setIcon(imageIcon);
        button.setName(name);
        button.setToolTipText(SaveInformation.getAllWords().get(name));
        button.setVisible(true);
        return button;
    }

    /**
     * this method is for creating all progress bar
     *
     * @param rightMainPanel is panel that progress bars should add to it
     * @param rightPanel     is a panel that rightMainPanel is ceter of it
     */
    private void createProgressBars(JPanel rightMainPanel, JPanel rightPanel) {
        // we clear array and create it again
        progressBarsDownloads.clear();
        if (!SaveInformation.getKindOfList().isEmpty()) {
            boolean test;
            // creating all panel of progress of selected list
            for (int j = 0; j < SaveInformation.getKindOfList().size(); j++) {
                test = false;
                ThreadOfDownload threadOfDownload2 = null;
                CreateProgressBar progressBar;
                // check that is there a thread of this download or not
                for (int i = 0; i < SaveInformation.getThreadsDownloads().size(); i++) {
                    if (SaveInformation.getThreadsDownloads().get(i).getFile().equals(SaveInformation.getKindOfList().get(j))) {
                        threadOfDownload2 = SaveInformation.getThreadsDownloads().get(i);
                        test = true;
                    }
                }
                // create panel of progress and add it to rightMainPanel
                if (!test) {
                    progressBar = new CreateProgressBar(SaveInformation.getKindOfList().get(j), rightPanel);
                    rightMainPanel.add(progressBar.getPanelOfProgress());
                    progressBarsDownloads.add(progressBar);

                    // check that this download is new started download or not and create new thread for it
                    if (SaveInformation.getProcessingDownloads().size() > SaveInformation.getThreadsDownloads().size() && SaveInformation.getProcessingDownloads().contains(SaveInformation.getKindOfList().get(j))) {
                        boolean test2 = false;
                        for (int i = 0; i < SaveInformation.getThreadsDownloads().size(); i++) {
                            if (SaveInformation.getThreadsDownloads().get(i).getFile().equals(SaveInformation.getKindOfList().get(j))) {
                                test2 = true;
                            }
                        }
                        if (!test2) {
                            ThreadOfDownload threadOfDownload = new ThreadOfDownload(progressBar, SaveInformation.getKindOfList().get(j));
                            SaveInformation.addToThreadsDownloads(threadOfDownload);
                            threadOfDownload.execute();
                        }
                    }
                    // add panel of all thread to right panel
                } else {
                    rightMainPanel.add(threadOfDownload2.getProgressBarClass().getPanelOfProgress());
                }
            }
        }
    }

    private void setColor(JPanel rightPanel) {
        rightPanel.setBackground(Color.gray);
    }

    private void createPanelForSearch(JPanel rightMainPanel) {
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        ActionSearch handel = new ActionSearch();

        searchText = new JTextField();
        searchText.setPreferredSize(new Dimension(250, 25));

        JButton searChButton = new JButton(SaveInformation.getAllWords().get("Search"));
        searChButton.setPreferredSize(new Dimension(100, 30));
        searChButton.setName("Search");
        searChButton.addActionListener(handel);

        searchPanel.add(searchText);
        searchPanel.add(searChButton);
        rightMainPanel.add(searchPanel);

    }

    private class ActionSearch implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SaveInformation.getSearchDownloads().clear();
            String searchText2 = searchText.getText();
            if (!searchText2.isEmpty()) {
                for (InformationNewDownload download : SaveInformation.getAllDownloads()) {
                    if (download.getLinkNewDownload().contains(searchText2) || download.getNameNewDownload().contains(searchText2)) {
                        SaveInformation.addToSearchDownloads(download);
                    }
                }
            }
            rightPanel.removeAll();
            CFrame.createRightPanel();
        }
    }

}
