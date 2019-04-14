import java.awt.geom.Area;

/**
 *  a class for menage item
 *  that tank get ir
 */
public class Item extends Collidable
{
    public enum Type
    {
        CANNON, RAPAIR, MACHINE, UPGRADE, SHIELD, FLAG
    }
    private int x, y, width, height;
    private Type type;
    private boolean valid = true;

    public Item(int x, int y, int width, int height, String name)
    {
        super(x+25, y+25, width, height, name);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * return that a item getted or not
     * @return
     */
    public boolean isValid()
    {
        return valid;
    }

    public int getX()
    {
        return x;
    }


    public int getY()
    {
        return y;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * a method from super class over writted
     * that check a item collide with another object
     * @param collidable
     * @param secondArea
     * @param xSecond
     * @param ySecond
     * @param widthSecond
     * @param heighSecond
     * @return
     */
    @Override
    protected boolean checkCollide(Collidable collidable, Area secondArea, int xSecond, int ySecond, int widthSecond, int heighSecond)
    {
        if (!valid)
            return false;
        setArea();
        boolean b = super.checkCollide(collidable, secondArea, xSecond, ySecond, widthSecond, heighSecond);
        if (b)
        {
            if (collidable instanceof GameState)
            {
                valid = false;
                Audio.getAudio().playItems();
            }
        } else
        {
        }
        return b;
    }

    /**
     * update shape on the item
     * @return
     */
    protected Area setArea()
    {
        return super.setArea(this.x+25, this.y+25, this.width, this.height, 0);
    }
}
