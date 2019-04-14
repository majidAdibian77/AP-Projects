
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * This class is for Map Editor
 *  @author : Majid Adibian
 */
public class Map {
    private static JPanel mainPanel;
    private static JPanel downPanel;
    private static String pathClicked;
    private static Vector<JLabel> labels = new Vector<>();
    private static HashMap<String, String> hashMap = new HashMap<>();
    private static JFrame frame ;

    public static void map() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(480, 840);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        pathClicked = "Icons/ground.png";

        firstMap();
        makeButtons();

    }

    /**
     * This method is for making buttons
     * @param path is path of icon of button
     * @return button
     */
    private static JButton buttonMaker(String path) {
        JButton button = new JButton();
        button.setName(path);
        button.setPreferredSize(new Dimension(50, 40));
        downPanel.add(button);
        button.setIcon(new ImageIcon(path));
        button.setToolTipText(path.substring(path.indexOf('/') + 1, path.indexOf('.')));
        button.addMouseListener(new HandelClicked1());
        return button;
    }

    /**
     * This method is just for button of save and reset
     * @param name is name of button
     * @return button
     */
    private static JButton buttonSaveAndReset(String name) {
        JButton button = new JButton(name.substring(0,1));
        button.setName(name);
        button.setPreferredSize(new Dimension(50, 40));
        downPanel.add(button);
        button.addMouseListener(new HandelClicked1());
        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(15.0f));
        button.setToolTipText(name);
        button.setVisible(false);
        button.setVisible(true);
        return button;
    }

    /**
     * This inner class is for handel click on button and labels
     */
    private static class HandelClicked1 implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getName().equals("Reset")) {
                    pathClicked = "Icons/ground.png";
                    firstMap();
                    makeButtons();
                } else if (button.getName().equals("Save")) {
                    save();
                    StartFrames.startGame();
                }else {
                    pathClicked = button.getName();
                }
            } else if (component instanceof JLabel) {
                JLabel label = (JLabel) e.getComponent();
                if(!label.getName().contains("*") && !label.getName().contains("#")){
                    clickOnLabel(label);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * This method is for creating first map
     */
    private static void firstMap() {
        JPanel centerPanel = new JPanel(new GridLayout(30, 20, 1, 1));
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                JLabel label = new JLabel();
                if (j == 19) {
                    label.setName("g/");
                } else {
                    label.setName("g");
                }
                labels.add(label);
                label.setSize(24, 24);
                centerPanel.add(label, BorderLayout.CENTER);
                label.setLocation(j * 24, i * 24);
                if ((j == 0 && (i < 4)||(i == 0 && (j < 4)) || (j == 3 && (i < 4)))) {
                    label.setIcon(new ImageIcon("Icons/stoneWall.png"));
                    label.setName("s");
                } else if(i == 1 && j == 2) {
                    label.setIcon(new ImageIcon("Icons/tank.png"));
                }else if(i == 27 && j == 17) {
                    label.setIcon(new ImageIcon("Icons/flag.png"));
                    label.setName("l");
                    label.setName(label.getName()+"#");
                }else{
                    label.setIcon(new ImageIcon("Icons/ground.png"));
                }
                if(i<4 && j<4){
                    label.setName(label.getName()+"*");
                }
                label.addMouseListener(new HandelClicked1());
            }

        }
        downPanel = new JPanel(new GridLayout(2, 7));
        mainPanel.add(downPanel, BorderLayout.SOUTH);

    }

    /**
     * This method is for save file of map
     */
    private static void save() {
        try {
            FileWriter fileWriter = new FileWriter("files/mapText.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < labels.size(); i++) {
                if (labels.get(i).getName().contains("/")) {
                    bufferedWriter.write(labels.get(i).getName().substring(0, 1));
                    bufferedWriter.newLine();
                } else if(labels.get(i).getName().contains("*")) {
                    bufferedWriter.write(labels.get(i).getName().substring(0, 1));
                }else if(labels.get(i).getName().contains("#")) {
                    bufferedWriter.write("l");
                }else {
                    bufferedWriter.write(labels.get(i).getName());
                }
            }
            bufferedWriter.flush();
            fileWriter.close();
            bufferedWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Your map do not saved!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame.dispose();
    }

    /**
     * This method is for when clicked on label
     * @param label
     */
    private static void clickOnLabel(JLabel label) {
        label.setIcon(new ImageIcon(pathClicked));
        if (label.getName().contains("/")) {
            label.setName(hashMap.get(pathClicked) + "/");
        } else {
            label.setName(hashMap.get(pathClicked));
        }
    }

    private static void makeButtons() {

        JButton ground = buttonMaker("Icons/ground.png");
        hashMap.put("Icons/ground.png", "g");
        JButton softWall = buttonMaker("Icons/softWall.png");
        hashMap.put("Icons/softWall.png", "b");
        JButton wallWithHeart = buttonMaker("Icons/wallWithHeart.png");
        hashMap.put("Icons/wallWithHeart.png", "R");
        JButton wallWithUpgrade = buttonMaker("Icons/wallWithUpgrade.png");
        hashMap.put("Icons/wallWithUpgrade.png", "U");
        JButton wallWithShield = buttonMaker("Icons/wallWithShield.png");
        hashMap.put("Icons/wallWithShield.png", "H");
        JButton wallWithBullet1 = buttonMaker("Icons/wallWithBullet1.png");
        hashMap.put("Icons/wallWithBullet1.png", "C");
        JButton wallWithBullet2 = buttonMaker("Icons/wallWithBullet2.png");
        hashMap.put("Icons/wallWithBullet2.png", "M");
        JButton stoneWall = buttonMaker("Icons/stoneWall.png");
        hashMap.put("Icons/stoneWall.png", "s");
        JButton enemy1 = buttonMaker("Icons/enemy1.png");
        hashMap.put("Icons/enemy1.png", "e");
        JButton save = buttonSaveAndReset("Save");
        JButton enemy2 = buttonMaker("Icons/enemy2.png");
        hashMap.put("Icons/enemy2.png", "E");
        JButton enemy3 = buttonMaker("Icons/enemy3.png");
        hashMap.put("Icons/enemy3.png", "f");
        JButton enemy4 = buttonMaker("Icons/enemy4.png");
        hashMap.put("Icons/enemy4.png", "F");
        JButton grass = buttonMaker("Icons/grass.png");
        hashMap.put("Icons/grass.png", "t");
        JButton heartItem = buttonMaker("Icons/heartItem.png");
        hashMap.put("Icons/heartItem.png", "r");
        JButton upgradeGunItem = buttonMaker("Icons/upgradeGunItem.png");
        hashMap.put("Icons/upgradeGunItem.png", "u");
        JButton bulletItem1 = buttonMaker("Icons/bulletItem1.png");
        hashMap.put("Icons/bulletItem1.png", "c");
        JButton bulletItem2 = buttonMaker("Icons/bulletItem2.png");
        hashMap.put("Icons/bulletItem2.png", "m");
        JButton shieldItem1 = buttonMaker("Icons/shieldItem1.png");
        hashMap.put("Icons/shieldItem1.png", "h");
        JButton reset = buttonSaveAndReset("Reset");


    }
}

