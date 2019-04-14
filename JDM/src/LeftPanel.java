import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this class is for create left panel for button of showing list and logo of program
 */
public class LeftPanel {
    ArrayList<JButton> buttons;
    JPanel rightPanel ;

    public LeftPanel(JPanel leftPanel , JPanel rightPanel) {
        this.rightPanel = rightPanel;
        JPanel buttonLeftPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        buttonLeftPanel.setOpaque(false);
        JLabel imageLeftPanel = new JLabel();
        imageLeftPanel.setIcon(new ImageIcon("imageIcon/logo2.png"));
        leftPanel.add(imageLeftPanel , BorderLayout.NORTH);

        leftPanel.add(buttonLeftPanel , BorderLayout.CENTER);
        buttons = new ArrayList<>();
        Handler handler = new Handler();
        // In these lines we create buttons to show every kind of list of downloads
        // Search is button for find downloads
        JButton searchButton = buttonMaker("   "+SaveInformation.getAllWords().get("Search"));
        searchButton.setIcon(new ImageIcon("imageIcon/search.png"));
        searchButton.setName("Search");
        buttonLeftPanel.add(searchButton);
        buttons.add(searchButton);
        searchButton.addActionListener(handler);

        //Processing is a button for downloads that are processing
        JButton processingButton = buttonMaker("   "+SaveInformation.getAllWords().get("Processing"));
        processingButton.setIcon(new ImageIcon("imageIcon/downloaded.png"));
        processingButton.setName("Processing");
        buttonLeftPanel.add(processingButton);
        buttons.add(processingButton);
        processingButton.addActionListener(handler);

        //Completed is a button for downloads that are completed
        JButton completedButton = buttonMaker("   "+SaveInformation.getAllWords().get("Completed"));
        completedButton.setIcon(new ImageIcon("imageIcon/checked.png"));
        completedButton.setName("Completed");
        buttonLeftPanel.add(completedButton);
        buttons.add(completedButton);
        completedButton.addActionListener(handler);

        //Queues is a button for downloads that are in queue
        JButton queuesButton = buttonMaker("  "+SaveInformation.getAllWords().get("Queue"));
        queuesButton.setIcon(new ImageIcon("imageIcon/queue1.png"));
        queuesButton.setName("Queues");
        buttonLeftPanel.add(queuesButton);
        buttons.add(queuesButton);
        queuesButton.addActionListener(handler);

        //Default is a button for all downloads
        JButton defaultButton = buttonMaker("   "+SaveInformation.getAllWords().get("All downloads"));
        defaultButton.setName("Default");
        buttonLeftPanel.add(defaultButton);
        buttons.add(defaultButton);
        defaultButton.addActionListener(handler);

        setColor(leftPanel);
    }

    private JButton buttonMaker(String text) {
        JButton button = new JButton(text);
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
        return button;
    }

    private void setColor(JPanel leftPanel) {
        leftPanel.setBackground(Color.DARK_GRAY);
    }

    /**
     * in these class we change kind of list of downloads
     */
    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String tempButton = "";
            for (JButton button : buttons) {
                if (e.getSource().equals(button)) {
                    tempButton = button.getName();
                    break;
                }
            }

            // these lines are for set false for all downloads clicked and remove downloads clicked from array
            SaveInformation.getDownloadClickeds().clear();
            for (int i =0 ; i<SaveInformation.getAllDownloads().size() ; i++){
                SaveInformation.getAllDownloads().get(i).setClicked(false);
            }
            if (tempButton.equals("Search")) {
                rightPanel.removeAll();
                SaveInformation.setKindOfList("Search");
                CFrame.createRightPanel();

            }else if (tempButton.equals("Processing")) {
                rightPanel.removeAll();
                SaveInformation.setKindOfList("Processing");
                CFrame.createRightPanel();

            } else if (tempButton.equals("Completed")) {
                rightPanel.removeAll();
                SaveInformation.setKindOfList("Completed");
                CFrame.createRightPanel();

            } else if (tempButton.equals("Queues")) {
                rightPanel.removeAll();
                SaveInformation.setKindOfList("Queues");
                CFrame.createRightPanel();
            }
            else if(tempButton.equals("Default")){
                SaveInformation.setKindOfList("Default");
                rightPanel.removeAll();
                CFrame.createRightPanel();
            }
        }
    }
}



