import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

/**
 * This class is for showing first frames in starting game.
 *
 * @author : Majid Adibian
 */
public class StartFrames {
    private static final int GAME_HEIGHT = 720;
    private static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;
    private static JPanel mainPanel;
    private static JFrame frame;
    private static boolean singlePlayer = true;
    private static boolean server;
    private static int difficultyLevel = 1;
    private static Vector<JButton> buttons = new Vector<>();
    private static JTextField ipAddress;
    private static KeyHandler keyHandler;
    private static JLabel label;
    private static ImageIcon backGround;
    private static boolean map = false;

    /**
     * This method is first method to call another methods
     */
    public static void firstFrames() {
        //creating frame
        frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);
        label = new JLabel();
        mainPanel.add(label);
        frame.setVisible(true);

        Audio.getAudio().playMenukMusic();

        backGround = new ImageIcon("Images/firstFrame1.png");
        clickPanel();
    }

    /**
     * This method is for showing and selecting single player or multiPlayer
     */
    private static void firstPanel() {
        frame.removeKeyListener(keyHandler);
        label.removeAll();
        label.setVisible(false);
        label.setVisible(true);
        label.setIcon(backGround);
        // creating first buttons
        JButton singlePlayer = buttonMaker("Single player", "Single player");
        singlePlayer.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150);
        JButton multiPlayer = buttonMaker("Multi player", "Multi player");
        multiPlayer.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 100);
        JButton mapMaker = buttonMaker("MapMaker", "MapMaker");
        mapMaker.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2)- 50);
        JButton exit = buttonMaker("Exit", "Exit");
        exit.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2));
    }

    /**
     * This method is for showing and selecting new game or resume game
     */
    private static void secondPanel() {
        label.removeAll();
        label.setVisible(false);
        label.setVisible(true);
        label.setIcon(backGround);

        JButton newGame = buttonMaker("New Game", "New Game");
        newGame.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150);
        JButton resumeGame = buttonMaker("Resume Game", "Resume Game");
        resumeGame.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 100);
        JButton back = buttonMaker("Bake", "Bake1");
        back.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 50);

    }

    /**
     * This method is for showing second frame for selecting difficulty level of game
     */
    private static void thirdPanel() {
        label.removeAll();
        label.setVisible(false);
        label.setVisible(true);
        label.setIcon(backGround);

        JButton easy = buttonMaker("Easy", "Easy");
        easy.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150);
        JButton medium = buttonMaker("Medium", "Medium");
        medium.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150 + 50);
        JButton hard = buttonMaker("Hard", "Hard");
        hard.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150 + 100);
        JButton back = buttonMaker("Back", "Back2");
        back.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150 + 150);
    }

    /**
     * This method is for showing and selecting server or client
     */
    private static void maltyPlayFrame() {
        label.removeAll();
        label.setVisible(false);
        label.setVisible(true);
        label.setIcon(backGround);
        // creating malty play buttons
        JButton serverButton = buttonMaker("Server", "Server");
        serverButton.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150);
        JButton clientButton = buttonMaker("Client", "Client");
        clientButton.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150 + 50);
        JButton back = buttonMaker("Back", "Back3");
        back.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150 + 100);

        JLabel label2 = new JLabel("Enter your teammate IP address:");
        label2.setFont(label2.getFont().deriveFont(Font.BOLD).deriveFont(17.0F));
        label2.setForeground(new Color(160, 130, 135));
        label2.setSize(300, 50);
        label.add(label2);
        label2.setLocation((GAME_WIDTH / 2) - 120, (GAME_HEIGHT / 2) - 150 + 150);

        ipAddress = new JTextField();
        ipAddress.setFont(ipAddress.getFont().deriveFont(Font.BOLD).deriveFont(17.0F));
        ipAddress.setSize(200, 30);
        label.add(ipAddress);
        ipAddress.setLocation((GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 150 + 200);
    }

    /**
     * This method is for click to start menu
     */
    private static void clickPanel() {
        label.setIcon(new ImageIcon("Images/firstFrame2.png"));
        JLabel textLabel = new JLabel("Press any button to continue...");
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD).deriveFont(20.0f));
        textLabel.setForeground(new Color(240, 240, 240));
        textLabel.setSize(300, 50);
        label.add(textLabel);
        textLabel.setLocation(500, 30);

        keyHandler = new KeyHandler();
        frame.addKeyListener(keyHandler);
    }


    /**
     * This method is for creating every buttons
     *
     * @param name is name of button
     * @return button created
     */
    private static JButton buttonMaker(String text, String name) {
        JButton button = new JButton(text);
        button.setName(name);
        button.setSize(200, 50);
        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(15.0f));
        button.setForeground(new Color(220, 230, 200));
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        label.add(button);
        button.addActionListener(new ActionButton());
        button.addMouseListener(new HandelMouseEntered());
        buttons.add(button);
        return button;
    }

    /**
     * This class is for Action of all buttons
     */
    private static class ActionButton implements ActionListener {

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
                case "Single player":
                    singlePlayer = true;
                    secondPanel();
                    break;
                case "Multi player":
                    singlePlayer = false;
                    maltyPlayFrame();
                    break;
                case "New Game":
                    thirdPanel();
                    break;
                case "Resume Game":
                    try (
                            FileInputStream fs1 = new FileInputStream("files/MapMaker.game");
                            FileInputStream fs2 = new FileInputStream("files/Information.game");
                            FileInputStream fs3 = new FileInputStream("files/GameState.game")
                    ) {
                        ObjectInputStream os1 = new ObjectInputStream(fs1);
                        MapMaker.setMapMaker((MapMaker) os1.readObject());

                        os1.close();
                        fs1.close();

                        ObjectInputStream os2 = new ObjectInputStream(fs2);
                        Informations informations = (Informations) os2.readObject();
                        Informations.setInformation(informations);

                        os2.close();
                        fs2.close();

                        ObjectInputStream os3 = new ObjectInputStream(fs3);
                        GameState gameState = (GameState) os3.readObject();
                        os3.close();
                        fs3.close();

                        difficultyLevel = Informations.getInfo().difficultyLevel;
                        frame.removeAll();
                        GameFrame gFrame = new GameFrame(frame);
                        gFrame.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gFrame.getFrame().setVisible(true);
                        gFrame.initBufferStrategy();
                        // Create and execute the game-loop
                        GameLoop game = new GameLoop(gFrame, gameState);
                        gFrame.getFrame().addKeyListener(gameState.getKeyListener());
                        gFrame.getFrame().addMouseListener(gameState.getMouseListener());
                        gFrame.getFrame().addMouseMotionListener(gameState.getMouseMotionListener());
                        Audio.getAudio().playerMenu.close();
                        ThreadPool.execute(game);
                    } catch (IOException | ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "Game can not read file to resume game!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Client":
                    server = false;
                    startGame();

                    break;
                case "Server":
                    server = true;
                    thirdPanel();
                    break;
                case "Easy":
                    difficultyLevel = 1;
                    Informations.getInfo().difficultyLevel = difficultyLevel;
                    frame.removeAll();
                    startGame();
                    break;
                case "Medium":
                    difficultyLevel = 2;
                    Informations.getInfo().difficultyLevel = difficultyLevel;
                    frame.removeAll();
                    startGame();
                    break;
                case "Hard":
                    difficultyLevel = 3;
                    Informations.getInfo().difficultyLevel = difficultyLevel;
                    frame.removeAll();
                    startGame();
                    break;
                case "Bake1":
                    firstPanel();
                    break;
                case "Back2":
                    secondPanel();
                    break;
                case "Back3":
                    firstPanel();
                    break;
                case "Exit":
                    frame.dispose();
                    System.exit(0);
                    break;
                case "MapMaker":
                    singlePlayer = true;
                    Map.map();
                    map = true;
                    Informations.getInfo().difficultyLevel = 1;
                    break;
            }
        }
    }

    /**
     * This method is for start game
     */
    public static void startGame() {
        for (int i = 0; i < frame.getMouseListeners().length; i++) {
            frame.removeMouseListener(frame.getMouseListeners()[i]);
        }

        for (int i = 0; i < frame.getMouseMotionListeners().length; i++) {
            frame.removeMouseMotionListener(frame.getMouseMotionListeners()[i]);
        }

        for (int i = 0; i < frame.getKeyListeners().length; i++) {
            frame.removeKeyListener(frame.getKeyListeners()[i]);
        }

        Audio.getAudio().playerMenu.close();

        ThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                GameFrame gFrame = new GameFrame(frame);
                gFrame.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gFrame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(gFrame);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });

    }

    /**
     * This inner class is for action of button to change text of button when mouse entered and exit from it
     */
    private static class HandelMouseEntered extends MouseInputAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(20.0f));
            button.setForeground(new Color(140, 3, 6));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(15.0f));
            button.setForeground(new Color(220, 230, 200));

        }
    }


    /**
     * The keyboard handler.
     */
    private static class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            label.removeAll();
            firstPanel();

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static boolean isServer() {
        return server;
    }

    public static String getIP() {
        return ipAddress.getText();
    }

    public static boolean isSinglePlayer() {
        return singlePlayer;
    }

    public static void setFrame(JFrame frame2) {
        frame = frame2;
    }

    public static void setSinglePlayer(boolean singlePlayer) {
        StartFrames.singlePlayer = singlePlayer;
    }

    public static boolean isMap() {
        return map;
    }
}

