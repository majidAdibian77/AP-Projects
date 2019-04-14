
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class is for when player is gamed over or esc is pressed or player is wined.
 * for any works we have a method.
 *
 * @author Majid Adibian
 */
public class EndGame {
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JFrame oldFrame;
    private JFrame frame;
    private JLabel label;
    private GameFrame gameFrame;
    private GameState gameState;

    public EndGame(JFrame frame2) {
        oldFrame = frame2;
        oldFrame.removeAll();
        frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(frame2.getSize());
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);
        label = new JLabel();
        label.setIcon(new ImageIcon("Images/endFrame.png"));
        mainPanel.add(label);
        frame.setVisible(true);
    }

    /**
     * This method is for when player is gamed over
     */
    public void finishGame() {
        Audio.getAudio().playerback.close();
        Audio.getAudio().playGameOver();

        if (!StartFrames.isSinglePlayer()) {
            MultiPlayer.resetConnection();
        }
        JLabel textLabel = new JLabel();
        textLabel.setSize(400, 100);
        textLabel.setLocation(530, 70);
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD).deriveFont(40.0f));
        textLabel.setForeground(Color.lightGray);
        textLabel.setText("Game Over");
        label.add(textLabel);

        JButton startAgain = buttonMaker("Start again");
        JButton exitButton = buttonMaker("Exit");
        buttons.add(startAgain);
        buttons.add(exitButton);
        label.add(startAgain);
        label.add(exitButton);
        startAgain.setLocation(550, 200);
        exitButton.setLocation(550, 250);

        ActionButton actionButton = new ActionButton();
        exitButton.addActionListener(actionButton);
        startAgain.addActionListener(actionButton);

        StartFrames.setSinglePlayer(true);

    }

    /**
     * This method is for when esc is pressed.
     *
     * @param gameFrame is used in action of button to resume or...
     * @param gameState is used in action of button to resume or...
     */
    public void pressEsc(GameFrame gameFrame, GameState gameState) {
        this.gameFrame = gameFrame;
        this.gameState = gameState;

        JButton resumeButton = buttonMaker("Resume");
        JButton exitButton = buttonMaker("Exit and save");
        JButton mainMenuButton = buttonMaker("Main Menu");
        buttons.add(resumeButton);
        buttons.add(exitButton);
        buttons.add(mainMenuButton);

        label.add(resumeButton);
        label.add(exitButton);
        label.add(mainMenuButton);

        resumeButton.setLocation(550, 200);
        mainMenuButton.setLocation(550, 250);
        exitButton.setLocation(550, 300);

        ActionButton actionButton = new ActionButton();
        exitButton.addActionListener(actionButton);
        resumeButton.addActionListener(actionButton);
        mainMenuButton.addActionListener(actionButton);
    }

    /**
     * This method is used when game is ended and player is wined
     */
    public void winPlayer() {
        Audio.getAudio().playerback.close();
        Audio.getAudio().playVictory();
        JLabel textLabel = new JLabel();
        textLabel.setSize(400, 100);
        textLabel.setLocation(530, 70);
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD).deriveFont(40.0f));
        textLabel.setForeground(Color.lightGray);
        textLabel.setText("*YOU WIN*");
        label.add(textLabel);

        JButton nextLevel = buttonMaker("Next Level");
        JButton exitButton = buttonMaker("Exit");
        buttons.add(nextLevel);
        buttons.add(exitButton);
        label.add(nextLevel);
        label.add(exitButton);
        nextLevel.setLocation(550, 200);
        exitButton.setLocation(550, 250);

        ActionButton actionButton = new ActionButton();
        exitButton.addActionListener(actionButton);
        nextLevel.addActionListener(actionButton);

    }


    /**
     * This method is for creating every buttons
     *
     * @param name is name of button
     * @return button created
     */
    private JButton buttonMaker(String name) {
        JButton button = new JButton(name);
        button.setName(name);
        button.setSize(200, 50);
        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(20.0f));
        button.setForeground(new Color(220, 230, 200));
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.addMouseListener(new HandelMouseEntered2());
        return button;
    }


    /**
     * This class is for Action of all buttons
     */
    private class ActionButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String nameButton = "";
            for (JButton button : buttons) {
                if (e.getSource().equals(button)) {
                    nameButton = button.getName();
                    break;
                }
            }
            switch (nameButton) {
                case "Start again":
                    Audio.getAudio().playerGameOver.close();
                    Informations.resetInformation();
                    Informations.getInfo().difficultyLevel = 1;
                    ThreadPool.init();
                    MapMaker.getMapMaker().resetMap();
                    StartFrames.firstFrames();
                    frame.dispose();
                    oldFrame.dispose();
                    break;
                case "Exit and save":
                    save();
                    frame.dispose();
                    oldFrame.dispose();
                    System.exit(0);
                    break;
                case "Resume":
                    GameLoop gameLoop = new GameLoop(gameFrame, gameState);
                    frame.dispose();
                    ThreadPool.execute(gameLoop);
                    break;
                case "Main Menu":
                    Audio.getAudio().playerback.close();
                    save();
                    Informations.resetInformation();
                    Informations.getInfo().difficultyLevel = 1;
                    ThreadPool.init();
                    StartFrames.firstFrames();
                    MapMaker.getMapMaker().resetMap();
                    frame.dispose();
                    oldFrame.dispose();
                    break;
                case "Exit":
                    oldFrame.dispose();
                    frame.dispose();
                    System.exit(0);
                    break;
                case "Next Level":
                    Audio.getAudio().playerVictory.close();
                    int difficultyLevel = Informations.getInfo().difficultyLevel;
                    int level = Informations.getInfo().level;
                    level++;
                    Informations.resetInformation();
                    Informations.getInfo().setDifficultyLevel(difficultyLevel);
                    Informations.getInfo().setLevel(level);
                    save();
                    MapMaker.getMapMaker().resetMap();
                    frame.dispose();
                    StartFrames.startGame();
            }
        }
    }


    /**
     * This method is used for saving information
     */
    private void save() {
        try (FileOutputStream fs1 = new FileOutputStream("files/MapMaker.game");
             FileOutputStream fs2 = new FileOutputStream("files/Information.game");
             FileOutputStream fs3 = new FileOutputStream("files/GameState.game")) {
            ObjectOutputStream os1 = new ObjectOutputStream(fs1);
            ObjectOutputStream os2 = new ObjectOutputStream(fs2);
            ObjectOutputStream os3 = new ObjectOutputStream(fs3);

            os1.writeObject(MapMaker.getMapMaker());
            fs1.close();
            os1.flush();

            os2.writeObject(Informations.getInfo());
            fs2.close();
            os2.flush();

            os3.writeObject(gameState);
            fs3.close();
            os3.flush();

        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Game do not saved!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This inner class is for action of button to change text of button when mouse entered and exit from it
     */
    private class HandelMouseEntered2 extends MouseInputAdapter {
        /**
         * method for when mouse entered button
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(25.0f));
            button.setForeground(new Color(140, 3, 6));
        }

        /**
         * method for when mouse entered button
         */
        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(20.0f));
            button.setForeground(new Color(220, 230, 200));

        }
    }
}

//    }



