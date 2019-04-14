import java.awt.geom.Area;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class is for bullet tank.
 * This class Saves current place and final place of bullet of tank and can change current place of bullet of tank.
 *
 * @author : Majid Adibian
 */
public class BulletTank extends Collidable implements Serializable
{
    private boolean simpleShoot;
    private boolean power;
    private int currentPlaceOfBulletX;
    private int currentPlaceOfBulletY;
    private double angle;
    private boolean valid = true;
    private double remainingDecimalX = 0;
    private double remainingDecimalY = 0;
    private boolean isEnemy = false;

    public BulletTank(int firstPlaceOfBulletX, int firstPlaceOfBulletY, boolean simpleShoot, double angle , boolean isEnemy , boolean power)
    {
        super(firstPlaceOfBulletX, firstPlaceOfBulletY, 1, 1, "Bul");
        currentPlaceOfBulletX = firstPlaceOfBulletX;
        currentPlaceOfBulletY = firstPlaceOfBulletY;
        this.angle = (angle);
        this.simpleShoot = simpleShoot;
        this.isEnemy = isEnemy;
        this.power = power;
    }

    /**
     * This method is for changing place of bullet according to direction of movement of bullet
     *
     * @param changePlaceOfTankX is changing place of tank. we need this for showing bullet in new place.
     * @param changePlaceOfTankY is changing place of tank. we need this for showing bullet in new place.
     */
    public void changeCurrentPlaceOfBullet(int changePlaceOfTankX, int changePlaceOfTankY)
    {
        if (!move)
        {
            double newPlaceX;
            double newPlaceY;
            if (simpleShoot)
            {
                newPlaceX = 5 * (Math.cos(angle));
                newPlaceY = 5 * (Math.sin(angle));
            } else
            {
                newPlaceX = 10 * (Math.cos(angle));
                newPlaceY = 10 * (Math.sin(angle));
            }
            remainingDecimalX += (newPlaceX - (int) newPlaceX);
            remainingDecimalY += (newPlaceY - (int) newPlaceY);
            currentPlaceOfBulletX += (int) (newPlaceX);
            currentPlaceOfBulletY -= (int) (newPlaceY);
            currentPlaceOfBulletX += (int) remainingDecimalX;
            currentPlaceOfBulletY -= (int) remainingDecimalY;
            if (Math.abs(remainingDecimalX) > 1)
            {
                remainingDecimalX = remainingDecimalX - (int) remainingDecimalX;
            }
            if (Math.abs(remainingDecimalY) > 1)
            {
                remainingDecimalY = remainingDecimalY - (int) remainingDecimalY;
            }
            currentPlaceOfBulletX -= changePlaceOfTankX;
            currentPlaceOfBulletY -= changePlaceOfTankY;
        }
    }

    public boolean isSimpleShoot()
    {
        return simpleShoot;
    }

    public int getCurrentPlaceOfBulletX()
    {
        return currentPlaceOfBulletX;
    }

    public int getCurrentPlaceOfBulletY()
    {
        return currentPlaceOfBulletY;
    }

    public double getAngle()
    {
        return angle;
    }

    public boolean checkValid()
    {
        if (!valid)
            return true;
        if (currentPlaceOfBulletX < 0 || currentPlaceOfBulletX >  MapMaker.getMapMaker().getSizeX()|| currentPlaceOfBulletY < 0 || currentPlaceOfBulletY> MapMaker.getMapMaker().getSizeY())
        {
            return true;
        }
        else return false;
    }

    @Override
    protected Area setArea()
    {
        return super.setArea(currentPlaceOfBulletX, currentPlaceOfBulletY, 23, 12, angle);
    }

    @Override
    protected boolean checkCollide(Collidable collidable, Area secondArea, int xSecond, int ySecond, int widthSecond, int heighSecond)
    {
        setArea();

        boolean b = super.checkCollide(collidable, secondArea, xSecond, ySecond, widthSecond, heighSecond);
        if (b)
        {

            valid = false;
               Audio.getAudio().playBulletCollide();
        } else
        {
        }
        return b;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BulletTank that = (BulletTank) o;
        return simpleShoot == that.simpleShoot &&
                currentPlaceOfBulletX == that.currentPlaceOfBulletX &&
                currentPlaceOfBulletY == that.currentPlaceOfBulletY &&
                Double.compare(that.angle, angle) == 0;
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(simpleShoot, currentPlaceOfBulletX, currentPlaceOfBulletY, angle);
    }

    public boolean isEnemy()
    {
        return isEnemy;
    }

    public boolean isPower() {
        return power;
    }
}


