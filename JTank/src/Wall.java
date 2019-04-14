import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class is for walls that don not break
 */
public class Wall extends Collidable implements Serializable {

    private int x, y, width = 100,height = 100,radiate = 0;

    /**
     * constructor of class
     * @param x is for location
     * @param y is for location
     */
    public Wall(int x, int y, int width, int height)
    {
        super(x, y, width, height,"wall");
        this.x =x ;
        this.y =y ;
        this.width =width ;
        this.height =height ;
    }

    @Override
    protected Area setArea()
    {
        return super.setArea(this.x, this.y, this.width, this.height, this.radiate);
    }
    /**
     * This mehod is for chcking collide wall to another object
     */
    @Override
    protected boolean checkCollide(Collidable collidable,Area secondArea,int xSecond,int ySecond,int widthSecond,int heighSecond)
    {
        setArea();
        boolean b =super.checkCollide(collidable,secondArea, xSecond, ySecond, widthSecond, heighSecond);
        if (b)
        {

        } else
        {
        }
        return b;
    }


    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getRadiate()
    {
        return radiate;
    }
//    public boolean checkValid(){
//        if(x-MapMaker.getLocxShow()<-100 || x-MapMaker.getLocxShow()>1380 || y-MapMaker.getLocyShow()<-100 || y-MapMaker.getLocyShow()>820){
//            return true;
//        }
//        else return false;
//    }
}
