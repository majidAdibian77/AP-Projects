

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * this class is for create frame of JDM and using other class to comleted it
 */
public class CFrame {
    private static JFrame frame;
    private static JPanel mainPanel;
    private static JPanel panelRight;
    private static JPanel panelLeft;
    private static JPanel topPanel;

    public static void createFrame() {

        setLookAndFeel(SaveInformation.getSettingInformation().getLookAndFeel());
        frame = new JFrame("Internet Download Manager");
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);

        panelRight = new JPanel(new BorderLayout());
        mainPanel.add(panelRight, BorderLayout.CENTER);
        panelLeft = new JPanel(new BorderLayout());

        panelLeft.setBorder(new EmptyBorder(5, 10, 5, 10));
        mainPanel.add(panelLeft, BorderLayout.WEST);
        topPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);

        createTopPanel();
        createLeftPanel(panelRight);
        createRightPanel();

        mainPanel.setVisible(true);
        frame.setVisible(true);

        tray();
    }

    private static void setLookAndFeel(String lookAndFeel) {
        switch (lookAndFeel) {
            case "Metal look and feel":
                setLookAndFeelException("javax.swing.plaf.metal.MetalLookAndFeel");
                break;
            case "Nimbus look and feel":
                setLookAndFeelException("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                break;
            case "Motif look and feel":
                setLookAndFeelException("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                break;
            case "Windows look and feel":
                setLookAndFeelException("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                break;
            case "Windows classic look and feel":
                setLookAndFeelException("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                break;
        }
    }

    private static void setLookAndFeelException(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void disposeFrame() {
        frame.dispose();
        System.exit(1);
    }

    public static void disposeFrameFromSetting() {
        frame.dispose();
    }

    public static void createRightPanel() {
        saveOnFile();
        Sort.sortArray();
        RightPanel createRightPanel1 = new RightPanel(panelRight);
        frame.setVisible(true);
    }

    public static void removeAllRightPanel() {
        panelRight.removeAll();
    }

    public static void removeAllTopPanel() {
        topPanel.removeAll();
    }

    private static void createLeftPanel(JPanel rightPanel) {
        LeftPanel createLeftPanel1 = new LeftPanel(panelLeft, rightPanel);
        frame.setVisible(true);
    }

    public static void createTopPanel() {
        TopPanel createTopPanel1 = new TopPanel(topPanel);
        frame.setVisible(true);
    }

    private static void tray() {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("imageIcon/logo3.png"));
        try {
            tray.add(trayIcon);

        } catch (AWTException e) {
            e.printStackTrace();
        }
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.setVisible(true);
            }
        });
    }

    private static void saveOnFile() {

        try (FileOutputStream fs = new FileOutputStream("files/queue.jdm")) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(SaveInformation.getQueuesDownload());
            fs.close();
            os.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("JDM could not save your change !") , SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
        }

        try (FileOutputStream fs = new FileOutputStream("files/list.jdm")) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(SaveInformation.getAllDownloads());
            fs.close();
            os.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, SaveInformation.getAllWords().get("JDM could not save your change !"), SaveInformation.getAllWords().get("Error"), JOptionPane.ERROR_MESSAGE);
        }

    }

}


