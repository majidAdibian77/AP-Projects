/*** In The Name of Allah ***/


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.Vector;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 */
public class GameState extends Collidable implements Serializable {
    private int locY, locX;
    private int locMoseY, locMoseX;
    private boolean levelCanon, levelMachine;
    private boolean gameOver;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean simpleShoot = true;
    private double angle;
    private double angleBody = 0;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private MouseMoveHandler mouseMoveHandeler;
    private boolean pressEscape = false;
    private String code = "";
    //
    private int health;
    private int bulletNumber1, bulletNumber2;
    private boolean shield;
    private boolean win;
    private int counter = 0;
//    static final long serialVersionUID = 1L;

    public GameState() {
        super(640, 360, 100, 100, "tank");
        locX = 100;
        locY = 100;
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        //
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        mouseMoveHandeler = new MouseMoveHandler();
        health = 10;
        bulletNumber1 = 10;
        bulletNumber2 = 30;
        shield = false;
        win = false;
        levelCanon = false;
        levelMachine = false;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        boolean clockwise = false;
        double secondAngle = -1;


        if ((keyUP || keyRIGHT || keyLEFT || keyDOWN))
            Audio.getAudio().playMove();


        if (keyUP && keyRIGHT) {
            if (Math.abs(angleBody - Math.PI / 4) - 0.05 < Math.abs(angleBody - (Math.PI + Math.PI / 4))) {
                secondAngle = Math.PI / 4;
            } else {
                secondAngle = Math.PI + Math.PI / 4;
            }
        } else if (keyUP && keyLEFT) {
            if (Math.abs(angleBody - 3 * Math.PI / 4) - 0.05 < Math.abs(angleBody + Math.PI / 4)) {
                secondAngle = 3 * Math.PI / 4;
            } else {
                secondAngle = -Math.PI / 4;
            }
        } else if (keyDOWN && keyRIGHT) {
            if (Math.abs(angleBody - 3 * Math.PI / 4) - 0.05 < Math.abs(angleBody + Math.PI / 4)) {
                secondAngle = 3 * Math.PI / 4;
            } else {
                secondAngle = -Math.PI / 4;
            }
        } else if (keyDOWN && keyLEFT) {
            if (Math.abs(angleBody - 5 * Math.PI / 4) - 0.05 < Math.abs(angleBody - Math.PI / 4)) {
                secondAngle = 5 * Math.PI / 4;
            } else {
                secondAngle = Math.PI / 4;
            }
        } else if (keyUP) {
            if (Math.abs(angleBody - Math.PI / 2) - 0.05 < Math.abs(angleBody + Math.PI / 2)) {
                secondAngle = Math.PI / 2;
            } else {
                secondAngle = -Math.PI / 2;
            }
        } else if (keyDOWN) {
            if (Math.abs(angleBody - 3 * Math.PI / 2) - 0.05 < Math.abs(angleBody - Math.PI / 2)) {
                secondAngle = 3 * Math.PI / 2;
            } else {
                secondAngle = Math.PI / 2;
            }
        } else if (keyLEFT) {
            if (Math.abs(angleBody) > Math.abs(angleBody - Math.PI) - 0.05) {
                secondAngle = Math.PI;
            } else {
                secondAngle = 0;
            }
        } else if (keyRIGHT && !right) {
            if (Math.abs(angleBody) - 0.05 > Math.abs(angleBody - Math.PI)) {
                secondAngle = Math.PI;
            } else {
                secondAngle = 0;
            }
        }
        if (secondAngle != -1 && angleBody != secondAngle) {
            if (angleBody - secondAngle > 0)
                clockwise = true;
            else
                clockwise = false;

            if (Math.abs(angleBody - secondAngle) < .1)
                angleBody = secondAngle;
            else if (clockwise)
                angleBody -= .1;
            else
                angleBody += .10;
            if (Math.abs(angleBody - Math.PI * 2) < .01 || angleBody > Math.PI * 2 || angleBody < -Math.PI * 2)
                angleBody = 0;
        } else {
            if (keyUP && !up) {
                locY -= 4;
                counter++;
            }
            if (keyDOWN && !down) {
                locY += 4;
                counter++;
            }
            if (keyLEFT && !left) {
                locX -= 4;
                counter++;
            }
            if (keyRIGHT && !right) {
                locX += 4;
                counter++;
            }
            if (counter == 100) {
                counter = 0;
            }


            locX = Math.max(locX, 0);
            locX = Math.min(locX, MapMaker.getMapMaker().getLocxShow() + GameFrame.GAME_WIDTH);
            locY = Math.max(locY, 0);
            locY = Math.min(locY, MapMaker.getMapMaker().getLocyShow() + GameFrame.GAME_HEIGHT);
            changeAngle();
        }
    }


    public KeyListener getKeyListener() {
        return keyHandler;
    }

    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    public MouseMotionListener getMouseMotionListener() {
        return mouseMoveHandeler;
    }


    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter implements Serializable {
//        static final long serialVersionUID = 1L;

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_W:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_S:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_A:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_D:
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    pressEscape = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                    case KeyEvent.VK_W:
                    keyUP = false;
                    break;

                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_S:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_A:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
                case KeyEvent.VK_D:
                    keyRIGHT = false;
                    break;
            }
            code = code + e.getKeyChar();

            if (code.contains("gun1")) {
                bulletNumber1 += 50;
                code = "";
            } else if (code.contains("gun2")) {
                bulletNumber2 += 50;
                code = "";
            } else if (code.contains("shield")) {
                shield = true;
                code = "";
            } else if (code.contains("health")) {
                health = 10;
                code = "";
            } else if (code.contains("upgrade")) {
                levelMachine = true;
                levelCanon = true;
                code = "";
            }

            if (code.length() > 20) {
                code = "";
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter implements Serializable {
//        static final long serialVersionUID = 1L;

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 3) {
                simpleShoot = !simpleShoot;
            }
            if (e.getButton() == 1) {
                if ((simpleShoot && bulletNumber1 > 0) || (!simpleShoot && bulletNumber2 > 0)) {
                    int firstPlaceOfBulletX = locX + 50 - 11 + (int) (80 * Math.cos(angle));
                    int firstPlaceOfBulletY = locY + 50 - 6 + (int) (-80 * Math.sin(angle));
                    BulletTank bullet;
                    if (simpleShoot) {
                        bullet = new BulletTank(firstPlaceOfBulletX, firstPlaceOfBulletY, true, angle, false, levelCanon);
                    } else {
                        bullet = new BulletTank(firstPlaceOfBulletX, firstPlaceOfBulletY, false, angle, false, levelMachine);
                    }
                    Informations.getInfo().bulletOwnTank.add(bullet);
                    Informations.getInfo().newBullets.add(bullet);
                    if (simpleShoot) {
                        Audio.getAudio().playShoot1();
                        bulletNumber1--;
                    } else {
                        Audio.getAudio().playShoot2();
                        bulletNumber2--;
                    }
                }
            } else {
                Audio.getAudio().playEmpty();
            }
        }
    }

    class MouseMoveHandler implements MouseMotionListener, Serializable {
//        static final long serialVersionUID = 1L;

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            locMoseX = e.getX();
            locMoseY = e.getY();
        }
    }

    public int getLocY() {
        return locY;
    }

    public int getLocX() {
        return locX;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    public double getAngle() {
        return angle;
    }

    public boolean isSimpleShoot() {
        return simpleShoot;
    }

    @Override
    protected Area setArea() {
        return super.setArea(locX, locY, 95, 95, angleBody);
    }

    @Override
    protected boolean checkCollide(Collidable collidable, Area secondArea, int xSecond, int ySecond, int widthSecond, int heighSecond) {
        setArea();
        boolean b = super.checkCollide(collidable, secondArea, xSecond, ySecond, widthSecond, heighSecond);
        if (b) {
            if (collidable instanceof Item) {

//                Audio.getAudio().playItems();
                switch (((Item) collidable).getType()) {

                    case CANNON:
                        bulletNumber1 += 30;
                        break;
                    case RAPAIR:
                        health = 5;
                        break;
                    case MACHINE:
                        bulletNumber2 += 30;
                        break;
                    case UPGRADE:
                        if (simpleShoot && !levelCanon)
                            levelCanon = true;
                        else if (!simpleShoot && !levelMachine)
                            levelMachine = true;
                        break;
                    case SHIELD:
                        shield = true;
                        break;
                    case FLAG:
                        win = true;
                        break;
                }
            } else if (collidable instanceof BulletTank) {
                if (shield) {
                    shield = false;
                } else {
                    health--;
                    if (health == 0)
                        gameOver = true;
                }
            }
        } else {
        }
        return b;
    }

    public double getAngleBody() {
        return angleBody;
    }

    /**
     * This method is for changing angle of head of tank
     */
    private void changeAngle() {
        float y = (locMoseY - 50 - locY + MapMaker.getMapMaker().getLocyShow());
        float x = (locMoseX - 50 - locX + MapMaker.getMapMaker().getLocxShow());
        if (y == 0) {
            if (x > 0) {
                angle = 0;
            } else {
                angle = Math.PI;
            }
        } else if (x == 0) {
            if (y > 0) {
                angle = -Math.PI / 2;
            } else {
                angle = Math.PI / 2;
            }
        } else {
            angle = Math.atan(Math.abs(y / x));
            if (y > 0 && x > 0) {
                angle = -angle;
            } else if (y > 0 && x < 0) {
                angle = Math.PI + angle;
            } else if (y < 0 && x < 0) {
                angle = Math.PI - angle;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public int getBulletNumber1() {
        return bulletNumber1;
    }

    public int getBulletNumber2() {
        return bulletNumber2;
    }

    public boolean getLevelCanon() {
        return levelCanon;
    }

    public boolean getLevelMachine() {
        return levelMachine;
    }

    public Boolean getPressEscape() {
        return pressEscape;
    }

    public void setPressEscape(Boolean pressEscape) {
        this.pressEscape = pressEscape;
    }


    public boolean isShield() {
        return shield;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public boolean isWin() {
        return win;
    }

    public void setPressEscape(boolean pressEscape) {
        this.pressEscape = pressEscape;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getCounter() {
        return counter;
    }
}

