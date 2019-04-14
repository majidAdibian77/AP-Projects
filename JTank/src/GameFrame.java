/*** In The Name of Allah ***/
/*** In The Name of Allah ***/

import com.sun.prism.Graphics;

import java.awt.*;
import java.awt.font.ShapeGraphicAttribute;
import java.awt.geom.Area;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 * <p>
 * g=ground , s = hard wall , b=soft wall , E,e = enemy dynamic , f,F = enemy static ,r = rapair on ground t = grass h =shield
 */
public class GameFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
    private JFrame frame;
    private long lastRender;
    private ArrayList<Float> fpsHistory;
    static ArrayList<Area> shapes = new ArrayList<>();
    private int counter = 0;
    private ArrayList<Integer> trees = new ArrayList<>();
    private BufferStrategy bufferStrategy;


    private BufferedImage repair = null;
    private BufferedImage upgrade = null;
    private BufferedImage tree = null;
    private BufferedImage machine = null;
    private BufferedImage cannon = null;
    private BufferedImage softWall1 = null;
    private BufferedImage softWall2 = null;
    private BufferedImage softWall3 = null;
    private BufferedImage imageGun1 = null;
    private BufferedImage imageGun2 = null;
    private BufferedImage EnemyGun = null;
    private BufferedImage bulletEnemyTank = null;
    private BufferedImage enemyTank1 = null;
    private BufferedImage enemyTank2 = null;
    private BufferedImage enemyTank3 = null;
    private BufferedImage enemyTank4 = null;
    private BufferedImage tankBody1 = null;
    private BufferedImage tankBody2 = null;
    private BufferedImage tankTube1Level1 = null;
    private BufferedImage tankTube1Level2 = null;
    private BufferedImage tankTube2Level1 = null;
    private BufferedImage tankTube2Level2 = null;
    private BufferedImage bulletTank1 = null;
    private BufferedImage bulletTank2 = null;
    private BufferedImage imageHealth = null;
    private BufferedImage groundImage = null;
    private BufferedImage wallImage = null;
    private BufferedImage shield = null;
    private BufferedImage shieldItem = null;
    private BufferedImage flag = null;
    private BufferedImage explosionTank = null;


    public GameFrame(JFrame frame) {
        this.frame = frame;
        frame.removeAll();
        frame.requestFocus();
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
        try {
            tree = ImageIO.read(new File("Images\\tree.png"));
            repair = ImageIO.read(new File("Images\\RepairFood.png"));
            upgrade = ImageIO.read(new File("Images\\upgrade.png"));
            cannon = ImageIO.read(new File("Images\\CannonFood.png"));
            machine = ImageIO.read(new File("Images\\MashinGunFood.png"));
            softWall1 = ImageIO.read(new File("Images\\softWall11.png"));
            softWall2 = ImageIO.read(new File("Images\\softWall2.png"));
            softWall3 = ImageIO.read(new File("Images\\softWall3.png"));
            imageGun1 = ImageIO.read(new File("Images/NumberOfHeavyBullet2.png"));
            imageGun2 = ImageIO.read(new File("Images/NumberOfMachinGun2.png"));
            bulletEnemyTank = ImageIO.read(new File("Images/Enemy2Bullet.png"));
            EnemyGun = ImageIO.read(new File("Images/BigEnemyGun.png"));
            enemyTank1 = ImageIO.read(new File("Images/BigEnemyH.png"));
            enemyTank2 = ImageIO.read(new File("Images\\Enemy2.png"));
            enemyTank3 = ImageIO.read(new File("Images\\Enemy1H.png"));
            enemyTank4 = ImageIO.read(new File("Images\\Enemy1.png"));
            tankTube1Level1 = ImageIO.read(new File("Images/tankGun01.png"));
            tankTube1Level2 = ImageIO.read(new File("Images/tankGun1.png"));
            tankTube2Level1 = ImageIO.read(new File("Images/tankGun02.png"));
            tankTube2Level2 = ImageIO.read(new File("Images/tankGun2.png"));
            tankBody1 = ImageIO.read(new File("Images/tank.png"));
            tankBody2 = ImageIO.read(new File("Images/tank2.png"));
            bulletTank1 = ImageIO.read(new File("Images/HeavyBullet.png"));
            bulletTank2 = ImageIO.read(new File("Images/LightBullet.png"));
            imageHealth = ImageIO.read(new File("Images/health.png"));
            groundImage = ImageIO.read(new File("Images\\ground.png"));
            wallImage = ImageIO.read(new File("Images\\wall.png"));
            shield = ImageIO.read(new File("Images\\shield.png"));
            shieldItem = ImageIO.read(new File("Images\\shieldItem.png"));
            flag = ImageIO.read(new File("Images\\flag.png"));
            explosionTank = ImageIO.read(new File("Images\\explosionTank.png"));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "you don not have all images!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        frame.createBufferStrategy(3);
        bufferStrategy = frame.getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state, GameState friend) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state, friend);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state, GameState friend) {
        trees.removeAll(trees);
        renderGround(g2d, state);
        renderItems(g2d, state);
        renderSoftWall(g2d, state);
        renderTank(g2d, state);
        if (friend != null) {
            renderTank(g2d, friend);
        }
        renderEnemyTank(g2d, state);
        renderBullets(g2d);
        renderExplosion(g2d);
        renderTrees(g2d);
        renderFrame(g2d, state);
        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
                    avg, (currentRender - lastRender));
            g2d.setColor(Color.CYAN);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            int strHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight + 50);
        }
        lastRender = currentRender;

    }

    /**
     * This class is for sowing ground (grass and ground
     *
     * @param g     is graphic for paint in frame
     * @param state is for my tank
     */
    //g=ground , s = hard wall , b=soft wall , E,e = enemy dynamic , f,F = enemy static ,r = rapair on ground t = grass h =shield
    private void renderGround(Graphics2D g, GameState state) {
        MapMaker mapMaker = MapMaker.getMapMaker().getMapMaker();
        Vector<String> strings = MapMaker.getMapMaker().getSpecialPartMap(state.getLocX(), state.getLocY());
        for (int i = 0; i < strings.size(); i++) {
            for (int j = 0; j < strings.get(i).length(); j++) {

                switch (strings.get(i).charAt(j)) {
                    case 'g':
                        g.drawImage(groundImage, 100 * j - MapMaker.getMapMaker().getRemaindX(), 100 * i - MapMaker.getMapMaker().getRemaindY(), null);
                        break;
                    case 't':
                        g.drawImage(groundImage, 100 * j - mapMaker.getRemaindX(), 100 * i - mapMaker.getRemaindY(), null);
                        trees.add(100 * j - mapMaker.getRemaindX());
                        trees.add(100 * i - mapMaker.getRemaindY());
                        break;
//
                    case 's':
                        g.drawImage(wallImage, 100 * j - MapMaker.getMapMaker().getRemaindX(), 100 * i - MapMaker.getMapMaker().getRemaindY(), null);
                        break;
                }
            }
        }
    }

    /**
     * this method is for showing Tank and bullet
     */
    private void renderTank(Graphics2D g2d, GameState state) {
        g2d.rotate(-state.getAngleBody(), state.getLocX() - MapMaker.getMapMaker().getLocxShow() + 50, state.getLocY() - MapMaker.getMapMaker().getMapMaker().getLocyShow() + 50);
        if ((state.getCounter() % 4) < 2 && (state.getCounter() % 4) >= 0) {
            g2d.drawImage(tankBody1, state.getLocX() - MapMaker.getMapMaker().getLocxShow(), state.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
        } else {
            g2d.drawImage(tankBody2, state.getLocX() - MapMaker.getMapMaker().getLocxShow(), state.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
        }

        g2d.rotate(state.getAngleBody(), state.getLocX() - MapMaker.getMapMaker().getLocxShow() + 50, state.getLocY() - MapMaker.getMapMaker().getLocyShow() + 50);

        //this for is for showing bullets
        for (int i = 0; i < Informations.getInfo().bulletOwnTank.size(); i++) {
            BulletTank bulletTank = Informations.getInfo().bulletOwnTank.get(i);
            g2d.rotate(-bulletTank.getAngle(), bulletTank.getCurrentPlaceOfBulletX() + 11 - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() + 6 - MapMaker.getMapMaker().getLocyShow());
            if (bulletTank.isSimpleShoot()) {
                g2d.drawImage(bulletTank1, bulletTank.getCurrentPlaceOfBulletX() - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow(), null);
            } else {
                g2d.drawImage(bulletTank2, bulletTank.getCurrentPlaceOfBulletX() - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow(), null);
            }
            g2d.rotate(bulletTank.getAngle(), bulletTank.getCurrentPlaceOfBulletX() + 11 - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() + 6 - MapMaker.getMapMaker().getLocyShow());
        }


        BufferedImage tankGun;

        if (state.isSimpleShoot())

        {
            if (!state.getLevelCanon())
                tankGun = tankTube1Level1;
            else
                tankGun = tankTube1Level2;
        } else

        {

            
            if (!state.getLevelMachine())
                tankGun = tankTube2Level1;
            else
                tankGun = tankTube2Level2;
        }
        g2d.rotate(-state.getAngle(), state.getLocX() - MapMaker.getMapMaker().getLocxShow() + 50, state.getLocY() - MapMaker.getMapMaker().getLocyShow() + 50);
        g2d.drawImage(tankGun, state.getLocX() - MapMaker.getMapMaker().getLocxShow(), state.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
        g2d.rotate(state.getAngle(), state.getLocX() - MapMaker.getMapMaker().getLocxShow() + 50, state.getLocY() - MapMaker.getMapMaker().getLocyShow() + 50);



        if (state.isShield()) {
            g2d.rotate(-0.1 * counter, state.getLocX() - MapMaker.getMapMaker().getLocxShow() + 50, state.getLocY() - MapMaker.getMapMaker().getLocyShow() + 50);
            g2d.drawImage(shield, state.getLocX() - MapMaker.getMapMaker().getLocxShow() - 5, state.getLocY() - MapMaker.getMapMaker().getLocyShow() - 5, null);
            g2d.rotate(0.1 * counter, state.getLocX() - MapMaker.getMapMaker().getLocxShow() + 50, state.getLocY() - MapMaker.getMapMaker().getLocyShow() + 50);
        }
        counter++;
        if (counter > 63) {
            counter = 0;
        }
    }

    public static void draw(Area rectangle) {
        shapes.add(rectangle);

    }


    /**
     * This method is for showing enemy tanks
     *
     * @param g2d
     * @param state
     */
    private void renderEnemyTank(Graphics2D g2d, GameState state) {

        for (int i = 0; i < Informations.getInfo().enemyTanks.size(); i++) {
            EnemyTank enemyTank = Informations.getInfo().enemyTanks.get(i);
            if (enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow() > -100 && enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow() < 1380 &&
                    enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow() > -100 && enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow() < 820 &&
                    enemyTank.isAbilityToMove() && enemyTank.isPower()) {
                g2d.drawImage(enemyTank1, enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
            } else if (enemyTank.isAbilityToMove() && !enemyTank.isPower()) {
                g2d.drawImage(enemyTank2, enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
            } else if (!enemyTank.isAbilityToMove() && enemyTank.isPower()) {
                g2d.drawImage(enemyTank3, enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
            } else if (!enemyTank.isAbilityToMove() && !enemyTank.isPower()) {
                g2d.drawImage(enemyTank4, enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
            }
            g2d.rotate(-enemyTank.getAngle(), enemyTank.getLocX() + 50 - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() + 50 - MapMaker.getMapMaker().getLocyShow());
            g2d.drawImage(EnemyGun, enemyTank.getLocX() - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
            g2d.rotate(enemyTank.getAngle(), enemyTank.getLocX() + 50 - MapMaker.getMapMaker().getLocxShow(), enemyTank.getLocY() + 50 - MapMaker.getMapMaker().getLocyShow());

        }
    }

    private void renderBullets(Graphics2D g2d) {
        for (int j = 0; j < Informations.getInfo().bullets.size(); j++) {
            BulletTank bulletTank = Informations.getInfo().bullets.get(j);
            if (bulletTank.getCurrentPlaceOfBulletX() - MapMaker.getMapMaker().getLocxShow() > -100 && bulletTank.getCurrentPlaceOfBulletX() - MapMaker.getMapMaker().getLocxShow() < 1380 &&
                    bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow() > -100 && bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow() < 820) {
                g2d.rotate(-bulletTank.getAngle(), bulletTank.getCurrentPlaceOfBulletX() + 11 - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow() + 6);
                g2d.drawImage(bulletEnemyTank, bulletTank.getCurrentPlaceOfBulletX() - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow(), null);
                g2d.rotate(bulletTank.getAngle(), bulletTank.getCurrentPlaceOfBulletX() + 11 - MapMaker.getMapMaker().getLocxShow(), bulletTank.getCurrentPlaceOfBulletY() - MapMaker.getMapMaker().getLocyShow() + 6);
            }
        }
    }

    /**
     * This method is for showing some image i frame(number of bullets and health of tank)
     *
     * @param g2d
     * @param state
     */
    private void renderFrame(Graphics2D g2d, GameState state) {

        g2d.drawImage(imageGun1, 5, 30, null);
        g2d.setColor(Color.CYAN);
        g2d.setFont(g2d.getFont().deriveFont(30.0f));
        g2d.drawString(state.getBulletNumber1() + "", 60, 70);
        g2d.drawImage(imageGun2, 100, 20, null);
        g2d.drawString(state.getBulletNumber2() + "", 160, 70);
        for (int i = 0; i < state.getHealth() / 2; i++) {
            g2d.drawImage(imageHealth, 1230 - i * 40, 40, null);
        }
    }

    /**
     * This method is for showing soft walls
     *
     * @param g2d
     * @param state
     */
    private void renderSoftWall(Graphics2D g2d, GameState state) {


        for (int i = 0; i < Informations.getInfo().softWalls.size(); i++) {
            SoftWall softWall = Informations.getInfo().softWalls.get(i);
            if (softWall.getX() - MapMaker.getMapMaker().getLocxShow() > -100 && softWall.getX() - MapMaker.getMapMaker().getLocxShow() < 1380 &&
                    softWall.getY() - MapMaker.getMapMaker().getLocyShow() > -100 && softWall.getY() - MapMaker.getMapMaker().getLocyShow() < 820) {
                BufferedImage image = null;
                if (softWall.getHealth() == 3)
                    image = softWall1;
                if (softWall.getHealth() == 2)
                    image = softWall2;
                if (softWall.getHealth() == 1)
                    image = softWall3;
                g2d.drawImage(image, softWall.getX() - MapMaker.getMapMaker().getLocxShow(), softWall.getY() - MapMaker.getMapMaker().getLocyShow(), null);
            }
        }
    }

    /**
     * This method is dor showing all items
     *
     * @param g2d
     * @param state
     */
    private void renderItems(Graphics2D g2d, GameState state) {


        for (int i = 0; i < Informations.getInfo().items.size(); i++) {
            Item item = Informations.getInfo().items.get(i);
            if (item.getX() - MapMaker.getMapMaker().getLocxShow() > -100 && item.getX() - MapMaker.getMapMaker().getLocxShow() < 1380 &&
                    item.getY() - MapMaker.getMapMaker().getLocyShow() > -100 && item.getY() - MapMaker.getMapMaker().getLocyShow() < 820) {
                BufferedImage image = null;
                if (item.getType().equals(Item.Type.CANNON))
                    image = cannon;
                else if (item.getType().equals(Item.Type.MACHINE))
                    image = machine;
                else if (item.getType().equals(Item.Type.RAPAIR))
                    image = repair;
                else if (item.getType().equals(Item.Type.UPGRADE))
                    image = upgrade;
                else if (item.getType().equals(Item.Type.SHIELD))
                    image = shieldItem;
                else if (item.getType().equals(Item.Type.FLAG)) {
                    image = flag;
                }
                g2d.drawImage(image, item.getX() - MapMaker.getMapMaker().getLocxShow(), item.getY() - MapMaker.getMapMaker().getLocyShow(), null);
            }
        }
    }

    private void renderTrees(Graphics2D g2d) {
        for (int i = 0; i < trees.size(); i += 2) {
            g2d.drawImage(tree, trees.get(i), trees.get(i + 1), null);
        }
    }

    private void renderExplosion(Graphics2D g2d){
        for (int i = 0 ; i < Informations.getInfo().explosions.size(); i++){
            Explosion explosion = Informations.getInfo().explosions.get(i);
            g2d.drawImage(explosionTank, explosion.getLocX() - MapMaker.getMapMaker().getLocxShow(), explosion.getLocY() - MapMaker.getMapMaker().getLocyShow(), null);
            explosion.count();
        }
    }

    public JFrame getFrame() {
        return frame;
    }

}
