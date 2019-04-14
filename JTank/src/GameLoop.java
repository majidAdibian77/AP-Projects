/*** In The Name of Allah ***/

import javax.swing.*;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 * <p>
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 * http://gameprogrammingpatterns.com/game-loop.html
 *
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 60;

    private GameFrame canvas;
    private GameState state;
    private GameState friend = null;
    private MultiPlayer multiPlayer;
    private ShareData ShareData;

    public GameLoop(GameFrame frame) {
        canvas = frame;
    }

    public GameLoop(GameFrame frame, GameState state) {
        canvas = frame;
        this.state = state;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        state = new GameState();

        canvas.getFrame().addKeyListener(state.getKeyListener());
        canvas.getFrame().addMouseListener(state.getMouseListener());
        canvas.getFrame().addMouseMotionListener(state.getMouseMotionListener());
    }

    @Override
    public void run() {
        boolean gameOver = false;
        boolean pressEscape = false;
        boolean win = false;
        if (!StartFrames.isSinglePlayer() && Informations.getInfo().level == 1) {
            multiPlayer = new MultiPlayer();
            multiPlayer.setServer(StartFrames.isServer());
            multiPlayer.setHost(StartFrames.getIP());
//            multiPlayer.setHost("127.0.0.1");
            try {
                multiPlayer.startConnection();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Your teammate isn't connected !", "Error", JOptionPane.ERROR_MESSAGE);
                StartFrames.setSinglePlayer(true);
            }

            if (!StartFrames.isServer()) {
                state.setLocX(200);
                state.setLocY(100);
            }
        }
        if (!StartFrames.isSinglePlayer()&&Informations.getInfo().walls.size()==0)
        {
            compactInformation();
            share();
            extractMap();
        }
        if(StartFrames.isSinglePlayer()&&Informations.getInfo().walls.size()==0){
            extractMap();
        }

        while (!gameOver && !pressEscape && !win) {
            try {
                Audio.getAudio().playBackMusic();

                long start = System.currentTimeMillis();
                if (!StartFrames.isSinglePlayer()) {
                    checkCollide();

                    compactInformation();

                    share();

                    updateBullets();
                    updateEnemy(state, friend);

                    state.update();

                    canvas.render(state, friend);
                } else {
                    checkCollide();

                    updateBullets();

                    updateEnemy(state, friend);

                    state.update();

                    canvas.render(state, friend);

                }
                pressEscape = state.getPressEscape();

                gameOver = state.isGameOver();

                win = state.isWin();
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);

            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "There is a problem in game!", "Error", JOptionPane.ERROR_MESSAGE);
                Informations.resetInformation();
                Informations.getInfo().difficultyLevel=1;
                MapMaker.getMapMaker().resetMap();
                StartFrames.firstFrames();
            }
        }
        // Draw GAME OVER
        if (state.isGameOver())
        {
            EndGame endGame = new EndGame(canvas.getFrame());
            endGame.finishGame();
        }
        if(state.isWin()){
            if (StartFrames.isSinglePlayer()) {
                EndGame endGame = new EndGame(canvas.getFrame());
                endGame.winPlayer();
            }else {
                int difficultyLevel = Informations.getInfo().difficultyLevel;
                int level = Informations.getInfo().level;
                level++;
                Informations.resetInformation();
                Informations.getInfo().setDifficultyLevel(difficultyLevel);
                Informations.getInfo().setLevel(level);
                MapMaker.getMapMaker().resetMap();
                StartFrames.startGame();
            }
        }
        if (pressEscape) {
            EndGame endGame = new EndGame(canvas.getFrame());
            endGame.pressEsc(canvas, state);
            state.setPressEscape(false);
        }
    }

    private void checkCollide() {
        GameFrame.shapes.removeAll(GameFrame.shapes);
        state.reset();
        if (friend != null)
            friend.reset();


        for (int j = 0; j < Informations.getInfo().walls.size(); j++) {

            Wall wall = Informations.getInfo().walls.get(j);
            CheckArrrayObjects(wall, Informations.getInfo().bulletOwnTank);

            checkObjects(wall);


            for (int i = 0; i < Informations.getInfo().enemyTanks.size(); i++) {
                EnemyTank enemyTank = Informations.getInfo().enemyTanks.get(i);

                checkCollideTwoObject(enemyTank, wall);
            }

            checkCollideTwoObject(wall, state);

            if (friend != null)
                checkCollideTwoObject(wall, friend);

        }


        for (int j = 0; j < Informations.getInfo().softWalls.size(); j++) {

            SoftWall softWall = Informations.getInfo().softWalls.get(j);

            if (!softWall.isValid()) {
                Informations.getInfo().softWalls.remove(j);
                j--;
            }
            {
                for (int i2 = 0; i2 < Informations.getInfo().bulletOwnTank.size(); i2++) {
                    BulletTank bulletTankS = Informations.getInfo().bulletOwnTank.get(i2);

                    if (checkCollideTwoObject(bulletTankS, softWall)) {
                        Informations.getInfo().bulletOwnTank.remove(i2);
                        i2--;
                    }

                }

                checkObjects(softWall);

                for (int i = 0; i < Informations.getInfo().enemyTanks.size(); i++) {
                    EnemyTank enemyTank = Informations.getInfo().enemyTanks.get(i);

                    checkCollideTwoObject(enemyTank, softWall);
                }

                checkCollideTwoObject(softWall, state);

                if (friend != null)
                    checkCollideTwoObject(softWall, friend);

            }
        }

        for (int i = 0; i < Informations.getInfo().items.size(); i++) {
            Item item = Informations.getInfo().items.get(i);

            if (!item.isValid()) {
                Informations.getInfo().items.remove(i);
                i--;
            } else {
                checkCollideTwoObject(state, item);


                if (friend != null)
                    checkCollideTwoObject(item, friend);
            }
        }

        for (int i = 0; i < Informations.getInfo().bullets.size(); i++) {
            BulletTank bulletTank = Informations.getInfo().bullets.get(i);

            if (checkCollideTwoObject(state, bulletTank)) {
                Informations.getInfo().bullets.remove(i);
                i--;
            }

            if (friend != null)
                if (checkCollideTwoObject(friend, bulletTank)) {
                    Informations.getInfo().bullets.remove(i);
                    i--;
                }
        }


        checkState(Informations.getInfo().bulletOwnTank);
        if (friend != null)
            checkCollideTwoObject(state, friend);


        for (int i = 0; i < Informations.getInfo().enemyTanks.size(); i++) {
            checkCollideTwoObject( Informations.getInfo().enemyTanks.get(i),state);
            if (friend != null) {
                checkCollideTwoObject(Informations.getInfo().enemyTanks.get(i),friend);
            }
        }

    }

    private void checkState(Vector<BulletTank> bulletTanks) {
        for (int i = 0; i < bulletTanks.size(); i++) {
            BulletTank bulletTank = bulletTanks.get(i);
            boolean test1 = false;
            for (int i1 = 0; i1 < Informations.getInfo().enemyTanks.size(); i1++) {
                EnemyTank enemyTank = Informations.getInfo().enemyTanks.get(i1);
                if (checkCollideTwoObject(bulletTank, enemyTank)){
                    test1 = true;
                    break;
                }
            }

            if (test1) {
                bulletTanks.remove(i);
                i--;
            }

        }
    }

    private void checkObjects(Wall wall) {
        for (int i1 = 0; i1 < Informations.getInfo().bullets.size(); i1++) {
            BulletTank bulletTankS = Informations.getInfo().bullets.get(i1);

            if (checkCollideTwoObject(bulletTankS, wall)) {
                Informations.getInfo().bullets.remove(i1);
                i1--;
            }
        }
    }

    private void CheckArrrayObjects(Wall wall, Vector<BulletTank> bulletTanks) {
        for (int i2 = 0; i2 < bulletTanks.size(); i2++) {
            BulletTank bulletTankS = bulletTanks.get(i2);

            if (checkCollideTwoObject(bulletTankS, wall)) {
                bulletTanks.remove(i2);
                i2--;
            }

        }
    }

    private boolean checkCollideTwoObject(Collidable collidable1, Collidable collidable2) {
        collidable1.setArea();
        Area secondArea = collidable2.setArea();
        if (Math.abs(collidable1.x - collidable2.x) <= 150 && Math.abs(collidable1.y - collidable2.y) <= 150) {
            if (collidable1.checkCollide(collidable2, secondArea, collidable2.x, collidable2.y, 100, 100)) {
                Area firstArea = collidable1.setArea();
                collidable2.checkCollide(collidable1, firstArea, collidable1.x, collidable1.y, 90, 90);
                return true;
            }
        }

        return false;


    }


    private void share() {
        ShareData getInformation = null;
        try {
            getInformation = multiPlayer.sendInformation(ShareData);
        if (getInformation != null) {
            if (StartFrames.isServer()) {
                friend = getInformation.getFriendTank();
            } else {
                friend = getInformation.getFriendTank();
                Informations.getInfo().difficultyLevel = getInformation.getDifficultyLevel();
            }
            if(friend.isWin()){
                int difficultyLevel = Informations.getInfo().difficultyLevel;
                int level = Informations.getInfo().level;
                level++;
                Informations.resetInformation();
                Informations.getInfo().setDifficultyLevel(difficultyLevel);
                Informations.getInfo().setLevel(level);
                MapMaker.getMapMaker().resetMap();
                StartFrames.startGame();
            }
            getInformation.getNewBullets().size();
            for (int i = 0; i < getInformation.getNewBullets().size(); i++)
                Informations.getInfo().bulletOwnTank.add(getInformation.getNewBullets().get(i));
        }
        }catch(IOException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Your teammate do not connected !", "Error", JOptionPane.ERROR_MESSAGE);
            StartFrames.setSinglePlayer(true);
        }

    }



        private void extractMap () {

            MapMaker mapMaker = MapMaker.getMapMaker();
            ArrayList<String> strings = mapMaker.getStrings();

            for (int i = 0; i < strings.size(); i++) {
                for (int j = 0; j < strings.get(i).length(); j++) {
                    switch (strings.get(i).charAt(j)) {
                        case 's':
                            Wall wall = new Wall((100 * j), (100 * i), 100, 100);
                            Informations.getInfo().walls.add(wall);
                            break;
                        case 'b':
                            SoftWall softWall = new SoftWall((100 * j), (100 * i), 100, 100);
                            Informations.getInfo().softWalls.add(softWall);
                            mapMaker.changeChar(100 * j, 100 * i, 'g');
                            j--;
                            break;
                        case 'E': {
                            EnemyTank enemyTank = new EnemyTank(100 * j, 100 * i, true, true, true, state.getLocX(), state.getLocY());
                            Informations.getInfo().enemyTanks.add(enemyTank);
                            mapMaker.changeChar(100 * j, 100 * i, 'g');
                            break;
                        }
                        case 'e': {
                            EnemyTank enemyTank = new EnemyTank(100 * j, 100 * i, true, false, false, state.getLocX(), state.getLocY());
                            Informations.getInfo().enemyTanks.add(enemyTank);
                            mapMaker.changeChar(100 * j, 100 * i, 'g');
                            break;
                        }
                        case 'F': {
                            EnemyTank enemyTank = new EnemyTank(100 * j, 100 * i, false, true, false, state.getLocX(), state.getLocY());
                            Informations.getInfo().enemyTanks.add(enemyTank);
                            mapMaker.changeChar(100 * j, 100 * i, 'g');
                            break;
                        }
                        case 'f': {
                            EnemyTank enemyTank = new EnemyTank(100 * j, 100 * i, false, false, false, state.getLocX(), state.getLocY());
                            Informations.getInfo().enemyTanks.add(enemyTank);
                            mapMaker.changeChar(100 * j, 100 * i, 'g');
                            break;
                        }

                        case 'r':
                        case 'R':
                        case 'c':
                        case 'C':
                        case 'm':
                        case 'M':
                        case 'u':
                        case 'U':
                        case 'h':
                        case 'H':
                        case 'l':
                            String c = String.valueOf(strings.get(i).charAt(j));
                            char ground;
                            if (c.equals(c.toUpperCase())) {
                                ground = 'b';
                            } else {
                                ground = 'g';
                            }
                            Item item = new Item((100 * (j)), 100 * (i), 50, 50, "Item");
                            switch (String.valueOf(strings.get(i).charAt(j)).toUpperCase().toCharArray()[0]) {
                                case 'C':
                                    item.setType(Item.Type.CANNON);
                                    break;
                                case 'M':
                                    item.setType(Item.Type.MACHINE);
                                    break;
                                case 'U':
                                    item.setType(Item.Type.UPGRADE);
                                    break;
                                case 'R':
                                    item.setType(Item.Type.RAPAIR);
                                    break;
                                case 'H':
                                    item.setType(Item.Type.SHIELD);
                                    break;
                                case 'L':
                                    item.setType(Item.Type.FLAG);
                                    break;
                            }
                            mapMaker.changeChar(100 * j, 100 * i, ground);
                            Informations.getInfo().items.add(item);
                            j--;
                            break;

                    }
                }
            }
        }


        private void updateBullets () {
            for (int i = 0; i < Informations.getInfo().bulletOwnTank.size(); i++) {
                BulletTank bulletTank = Informations.getInfo().bulletOwnTank.get(i);
                bulletTank.changeCurrentPlaceOfBullet(0, 0);
                if (bulletTank.checkValid()) {
                    Informations.getInfo().bulletOwnTank.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < Informations.getInfo().bullets.size(); i++) {
                BulletTank bulletTank = Informations.getInfo().bullets.get(i);
                bulletTank.changeCurrentPlaceOfBullet(0, 0);
                if (bulletTank.checkValid()) {
                    Informations.getInfo().bullets.remove(i);
                    i--;
                }
            }


        }

        private void updateEnemy (GameState state, GameState friend){
            for (int i = 0; i < Informations.getInfo().enemyTanks.size(); i++) {
                EnemyTank enemyTank = Informations.getInfo().enemyTanks.get(i);
                if (friend == null || Math.sqrt((((enemyTank.getLocX() - state.getLocX()) * (enemyTank.getLocX() - state.getLocX())))
                        + (((enemyTank.getLocY() - friend.getLocY()) * (enemyTank.getLocY() - friend.getLocY()))))
                        <
                        Math.sqrt((((enemyTank.getLocX() - friend.getLocX()) * (enemyTank.getLocX() - friend.getLocX())))
                                + (((enemyTank.getLocY() - friend.getLocY()) * (enemyTank.getLocY() - friend.getLocY())))))

                {
                    enemyTank.move(state.getLocX(), state.getLocY());
                } else {
                    enemyTank.move(friend.getLocX(), friend.getLocY());
                }

            }
        }

        private void compactInformation () {
            ArrayList<BulletTank> bulletTanks = new ArrayList<>();
            bulletTanks.addAll(Informations.getInfo().newBullets);
            int bulletsent = bulletTanks.size();
            for (int i = 0; i < bulletsent; i++) {
                Informations.getInfo().newBullets.remove(i);
                i--;
                bulletsent--;
            }
            ShareData = new ShareData(state, bulletTanks, Informations.getInfo().items, Informations.getInfo().difficultyLevel);

        }
    }


