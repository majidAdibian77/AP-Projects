import java.awt.geom.Area;
import java.io.Serializable;

/**
 * This class is for walls that can break
 */
public class SoftWall extends Wall implements Serializable {

    private int x, y, wdith = 100,height = 100,radiate = 0,health;

    /**
     * constructor of class.
     * @param x is for location
     * @param y is for location
     */
    public SoftWall(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        this.x =x ;
        this.y =y ;
        this.wdith =width ;
        this.height =height ;
        health =3;
    }

    /**
     * This method is for checking health of wall to remove it or not
     * @return true or false
     */
    public boolean isValid() {
        if(health == 0){
            return false;
        }
        else return true;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    public int getHealth()
    {
        return health;
    }

    /**
     * This mehod is for chcking collide wall to another object
     */
    @Override
    protected boolean checkCollide(Collidable collidable, Area secondArea, int xSecond, int ySecond, int widthSecond, int heighSecond)
    {
        if (health==0)
            return false;
        setArea();
        boolean b =super.checkCollide(collidable,secondArea, xSecond, ySecond, widthSecond, heighSecond);
        if (b)
        {
            if (collidable instanceof BulletTank&&!((BulletTank) collidable).isEnemy())
            {
                health--;
            }
        }
        return b;
    }
}
