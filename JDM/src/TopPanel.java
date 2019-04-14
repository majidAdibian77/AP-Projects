import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * this class is for create top panel for menu
 */
public class TopPanel {
    private ArrayList<JMenuItem> menuItems;

    public TopPanel(JPanel topPanel ) {
        ActionTollBar handel = new ActionTollBar();

        topPanel.setBackground(Color.WHITE);
        menuItems = new ArrayList<>();
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu(SaveInformation.getAllWords().get("Help"));
        menuBar.add(helpMenu);
        JMenu downloadMenu = new JMenu(SaveInformation.getAllWords().get("Download"));

        menuBar.add(downloadMenu);
        JMenu sortMenu = new JMenu(SaveInformation.getAllWords().get("Sort by"));
        menuBar.add(sortMenu);
        helpMenu.setMnemonic('h');
        downloadMenu.setMnemonic('d');
        sortMenu.setMnemonic('s');

        JMenuItem aboutMenuItem = new JMenuItem();
        createMenuItem(aboutMenuItem, helpMenu, "about", 'a');
        aboutMenuItem.addActionListener(handel);
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenuItem newDownloadMenuItem = new JMenuItem();
        createMenuItem(newDownloadMenuItem, downloadMenu, "New download", 'n');
        newDownloadMenuItem.addActionListener(handel);
        newDownloadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        JMenuItem resumeMenuItem = new JMenuItem();
        createMenuItem(resumeMenuItem, downloadMenu, "Resume", 'r');
        resumeMenuItem.addActionListener(handel);
        resumeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

        JMenuItem pauseMenuItem = new JMenuItem();
        createMenuItem(pauseMenuItem, downloadMenu, "Pause", 'p');
        pauseMenuItem.addActionListener(handel);
        pauseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem cancelMenuItem = new JMenuItem();
        createMenuItem(cancelMenuItem, downloadMenu, "Cancel", 'c');
        cancelMenuItem.addActionListener(handel);
        cancelMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem removeMenuItem = new JMenuItem();
        createMenuItem(removeMenuItem, downloadMenu, "Remove", 'm');
        removeMenuItem.addActionListener(handel);
        removeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));

        JMenuItem settingMenuItem = new JMenuItem();
        createMenuItem(settingMenuItem, downloadMenu, "Setting", 's');
        settingMenuItem.addActionListener(handel);
        settingMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem exportMenuItem = new JMenuItem();
        createMenuItem(exportMenuItem, downloadMenu, "Export", 'x');
        exportMenuItem.addActionListener(handel);
        exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem exitMenuItem = new JMenuItem();
        createMenuItem(exitMenuItem, downloadMenu, "Exit", 'e');
        exitMenuItem.addActionListener(handel);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));


        JMenuItem sortByNameAscending = new JMenuItem();
        createMenuItem(sortByNameAscending, sortMenu, "Name(Ascending)", 'e');
        sortByNameAscending.addActionListener(handel);
        sortByNameAscending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        JMenuItem sortByNameDescending = new JMenuItem();
        createMenuItem(sortByNameDescending, sortMenu, "Name(Descending)", 'u');
        sortByNameDescending.addActionListener(handel);
        sortByNameDescending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));

        JMenuItem sortBySizeAscending = new JMenuItem();
        createMenuItem(sortBySizeAscending, sortMenu, "Size(Ascending)", 'i');
        sortBySizeAscending.addActionListener(handel);
        sortBySizeAscending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        JMenuItem sortBySizeDescending = new JMenuItem();
        createMenuItem(sortBySizeDescending, sortMenu, "Size(Descending)", 'z');
        sortBySizeDescending.addActionListener(handel);
        sortBySizeDescending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

        JMenuItem sortByDateAscending = new JMenuItem();
        createMenuItem(sortByDateAscending, sortMenu, "Date(Ascending)", 't');
        sortByDateAscending.addActionListener(handel);
        sortByDateAscending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        JMenuItem sortByDateDescending = new JMenuItem();
        createMenuItem(sortByDateDescending, sortMenu, "Date(Descending)", 'y');
        sortByDateDescending.addActionListener(handel);
        sortByDateDescending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

        // in these lines we add icon to type of sort we select before
        for (JMenuItem menuItem : menuItems) {
            menuItem.setIcon(null);
        }

        String icon = "";
        int i = 1;
        for (String typeOfSort : SaveInformation.getTypeOfSort()) {
            if (i == 1) {
                icon = "imageIcon/one.png";
            } else if (i == 2) {
                icon = "imageIcon/two.png";
            } else if (i == 3) {
                icon = "imageIcon/three.png";
            }
            if (typeOfSort.equals(sortByNameAscending.getName())) {
                sortByNameAscending.setIcon(new ImageIcon(icon));
                sortByNameDescending.setIcon(null);
            } else if (typeOfSort.contains(sortByNameDescending.getName())) {
                sortByNameDescending.setIcon(new ImageIcon(icon));
                sortByNameAscending.setIcon(null);
            } else if (typeOfSort.contains(sortBySizeAscending.getName())) {
                sortBySizeAscending.setIcon(new ImageIcon(icon));
                sortBySizeDescending.setIcon(null);
            } else if (typeOfSort.contains(sortBySizeDescending.getName())) {
                sortBySizeDescending.setIcon(new ImageIcon(icon));
                sortBySizeAscending.setIcon(null);
            } else if (typeOfSort.contains(sortByDateAscending.getName())) {
                sortByDateAscending.setIcon(new ImageIcon(icon));
                sortByDateDescending.setIcon(null);
            } else if (typeOfSort.contains(sortByDateDescending.getName())) {
                sortByDateDescending.setIcon(new ImageIcon(icon));
                sortByDateAscending.setIcon(null);
            }
            i++;
        }
        topPanel.add(menuBar, BorderLayout.WEST);
        menuBar.setVisible(true);
    }

    private void createMenuItem(JMenuItem menuItem , JMenu menu , String name , char mnemonic){
        menuItem.setText(SaveInformation.getAllWords().get(name));
        menuItem.setName(name);
        menuItem.setMnemonic(mnemonic);
        menuItems.add(menuItem);
        menu.add(menuItem);
    }
}
