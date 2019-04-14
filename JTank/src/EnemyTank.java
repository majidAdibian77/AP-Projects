import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * This class is for tank of enemies.
 * We have 4 type of enemy and this tanks move In two directions.
 */
public class EnemyTank extends Collidable implements Serializable {
    private int locX, locY;
    private int health;
    private boolean abilityToMove, power,collide;
    private boolean moveRightLeft;
    private boolean downOrLeft;
    private int locMyTankX;
    private int locMyTankY;
    private double angle;
    private int counter;
    private boolean canShoot;
    private  double distance;

    /**
     * Constructor of class.
     *
     * @param locX          is length of location
     * @param locY          is width of location
     * @param abilityToMove is for ability to move or not
     * @param power         : some tanks are stronger
     * @param moveRightLeft : two directions for moving tanks
     */
    public EnemyTank(int locX, int locY, boolean abilityToMove, boolean power, boolean moveRightLeft, int locMyTankX, int locMyTankY) {
        super(locX, locY, 90, 90, "enemyTank");
        this.locX = locX;
        this.locY = locY;
        this.abilityToMove = abilityToMove;
        this.power = power;
        this.moveRightLeft = moveRightLeft;
        this.downOrLeft = true;
        this.locMyTankX = locMyTankX;
        this.locMyTankY = locMyTankY;
        counter = 0;
        canShoot = false;
        if (abilityToMove) {
            if (power) {
                health = 4 *Informations.getInfo().difficultyLevel;
            }
            if (!power) {
                health = 3*Informations.getInfo().difficultyLevel;
            }
        } else {
            if (power) {
                health = 6*Informations.getInfo().difficultyLevel;
            }
            if (!power) {
                health = 5*Informations.getInfo().difficultyLevel;
            }
        }
    }

    /**
     * This method is for movement of enemy tank
     * in first if this enemy tank us near to my tank this tank can move
     * then if this tank is in frame it can shoots
     *
     * @param locMyTankX is location X for My tank
     * @param locMyTankY is location Y for My tank
     */
    public void move(int locMyTankX, int locMyTankY)
    {
        this.locMyTankX = locMyTankX;
        this.locMyTankY = locMyTankY;
        validationMove();
        if (checkToShow()) {
            if (abilityToMove) {
                if (collide)
                {
                    {
                        downOrLeft = !downOrLeft;
                        collide = false;
                    }
                }
                int[] location = changeLocation();
                locX = location[0];
                locY = location[1];
            } else {
                downOrLeft = !downOrLeft;
            }
        }
        if (canShoot) {
            counter++;
            if (counter % (130-5*Informations.getInfo().difficultyLevel) == 0) {
                shootBullet();
            }
            if (counter == 390-15*Informations.getInfo().difficultyLevel) {
                counter = 0;
            }
        }

    }

    /**
     * this method is for health of enemy tank
     *
     * @return true or false for remove killed tank
     */
    public boolean isValid() {
        if (health <= 0) {
            return false;
        } else return true;
    }

    /**
     * In this method we check that enemy tank is near to my tank or not
     *
     * @return true or false
     */
    private boolean checkToShow()
    {
        boolean test = false;
        distance = Math.sqrt((locX - locMyTankX) * (locX - locMyTankX) + (locY - locMyTankY) * (locY - locMyTankY));
        if (distance < 600)
        {
            canShoot = true;
            test = true;
            if(distance<200)
            {
                if(distance < 120) {
                    test = false;
                }
                if(moveRightLeft){
                    if(locX>locMyTankX){
                        downOrLeft = false;
                    }else {
                        downOrLeft = true;
                    }
                }else {
                    if(locY>locMyTankY){
                        downOrLeft = true;
                    }else {
                        downOrLeft = false;
                    }
                }
            }
        } else {
            test = false;
            if(moveRightLeft){
                if(locX>locMyTankX){
                    downOrLeft = true;
                }else {
                    downOrLeft = false;
                }
            }else {
                if(locY>locMyTankY){
                    downOrLeft = false;
                }else {
                    downOrLeft = true;
                }
            }
        }
        float y = (locMyTankY - locY);
        float x = (locMyTankX - locX);
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
            if (x > 0 && y > 0) {
                angle = -angle;
            } else if (y > 0 && x < 0) {
                angle = Math.PI + angle;

            } else if (y < 0 && x < 0) {
                angle = Math.PI - angle;
            }
        }
        return test;
    }

    /**
     * This method is for checking that enemy tank is in frame or not and it collide to another thing or not
     * and if it is true we change the direction of movement
     */
    public void validationMove()
    {
        int[] location = changeLocation();
        int tempLocX = location[0];
        int tempLocY = location[1];
//        if (moveRightLeft&&(tempLocX - MapMaker.getMapMaker().getLocxShow() < 0 || tempLocX - MapMaker.getMapMaker().getLocxShow() > 1180))
//        {
//            downOrLeft = !downOrLeft;
//        }else if(!moveRightLeft&&(tempLocY - MapMaker.getMapMaker().getLocyShow() < 0 || tempLocY - MapMaker.getMapMaker().getLocyShow() > 620)){
//            downOrLeft = !downOrLeft;
//        }
//        if(tempLocX - MapMaker.getMapMaker().getLocxShow() < 0 || tempLocX - MapMaker.getMapMaker().getLocxShow() > 1280 || tempLocY - MapMaker.getMapMaker().getLocyShow() < 0 || tempLocY - MapMaker.getMapMaker().getLocyShow() > 720){
//            canShoot = false;
//        }
        if (moveRightLeft&&tempLocX - MapMaker.getMapMaker().getLocxShow() < 0)
        {
            downOrLeft=false;
        }
        if (moveRightLeft&&tempLocX - MapMaker.getMapMaker().getLocxShow() >1180 )
        {
            downOrLeft=true;
        }  if (!moveRightLeft&&tempLocY - MapMaker.getMapMaker().getLocyShow() < 0)
        {
            downOrLeft=true;
        }
        if (!moveRightLeft&&tempLocY - MapMaker.getMapMaker().getLocyShow() >620 )
        {
            downOrLeft=false;
        }
        if(tempLocX - MapMaker.getMapMaker().getLocxShow() < 0 || tempLocX - MapMaker.getMapMaker().getLocxShow() > 1280 || tempLocY - MapMaker.getMapMaker().getLocyShow() < 0 || tempLocY - MapMaker.getMapMaker().getLocyShow() > 720){
            canShoot = false;
        }

    }

    public boolean isPower()
    {
        return power;
    }

    public double getAngle()
    {
        return angle;
    }


    public int getLocX() {
        return locX;
    }

    public int getLocY() {
        return locY;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAbilityToMove() {
        return abilityToMove;
    }

    public boolean isMoveRightLeft() {
        return moveRightLeft;
    }

    /**
     * In this method the location of enemy tank changes
     * @return a array of X and Y for new location
     */
    private int[] changeLocation() {
        int tempLocX, tempLocY;
        tempLocX = this.locX;
        tempLocY = this.locY;
        if (moveRightLeft) {
            if (downOrLeft) {
                if (power) {
                    tempLocX -= 7;
                } else {
                    tempLocX -= 4;
                }
            } else {
                if (power) {
                    tempLocX += 7;
                } else {
                    tempLocX += 4;
                }
            }
        } else {
            if (downOrLeft) {
                if (power) {
                    tempLocY += 7;
                } else {
                    tempLocY += 4;
                }
            } else {


                if (power) {
                    tempLocY -= 7;
                } else {
                    tempLocY -= 4;
                }
            }
        }
        int[] location = new int[2];
        location[0] = tempLocX;
        location[1] = tempLocY;
        return location;
    }


    /**
     * this method a new bullet for enemy tank is created.
     */
    private void shootBullet() {
        BulletTank bullet = new BulletTank(locX+50-11+(int)(80*Math.cos(angle)), locY+50-6-(int)(80*Math.sin(angle)), true, angle,true, false);
        Informations.getInfo().bullets.add(bullet);
        Audio.getAudio().playShoot1();
    }

    @Override
    protected Area setArea()
    {
        return super.setArea(locX, locY, 90, 90, 0);
    }


    @Override
    protected boolean checkCollide(Collidable collidable, Area secondArea, int xSecond, int ySecond, int widthSecond, int heighSecond) {
        setArea();

        boolean b =super.checkCollide(collidable,secondArea, xSecond, ySecond, widthSecond, heighSecond);

        if (b)
        {
            if (collidable instanceof BulletTank) {
                BulletTank bulletTank = (BulletTank) collidable;
                if (!bulletTank.isEnemy()) {
                    if (bulletTank.isSimpleShoot()) {
                        health -= 2;
                    } else {
                        health--;
                    }
                    if(bulletTank.isPower()){
                        health--;
                    }
                    if(health <= 0){
                        Informations.getInfo().enemyTanks.remove(this);
                        Explosion explosion = new Explosion(locX-20,locY-20,300);
                        Informations.getInfo().explosions.add(explosion);
                    }
                }

                if (health <= 0)
                {
                    Random random = new Random();
                    int r=random.nextInt(Informations.getInfo().difficultyLevel*15);
                    if (r%(Informations.getInfo().difficultyLevel*3)==0)
                    {
                        Item item = new Item(locX,locY,50,50,"Item");
                        switch (r%5)
                        {
                            case 0:
                                item.setType(Item.Type.CANNON);
                                break;
                            case 1:
                                item.setType(Item.Type.MACHINE);
                                break;
                            case 2:
                                item.setType(Item.Type.UPGRADE);
                                break;
                            case 3:
                                item.setType(Item.Type.RAPAIR);
                                break;
                            case 4:
                                item.setType(Item.Type.SHIELD);
                                break;
                        }
                        Informations.getInfo().items.add(item);
                    }
                }
            } else
            {

                collide = true;
            }
        } else
        {

        }
        return b;

    }
}