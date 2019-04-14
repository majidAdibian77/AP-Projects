/**
 * a class for handel collide of all object
 */

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.Objects;

/**
 *  a class for menege all collide in game
 *  this class is super class of bullet and tanks and walls
*  @author Mohammad Mighani
 */
public class Collidable implements Serializable {
    private Rectangle rectangle;
    transient private Area area;
    public int x, y, width, height;
    protected boolean up, down, left, right, move;

    private String name;

    public Collidable(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;

    }

    /**
     * a method for update that shape on the object for check with intersect method
     * @param x
     * @param y
     * @param width
     * @param height
     * @param radiate
     * @return
     */
    protected Area setArea(int x, int y, int width, int height, double radiate) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle = new Rectangle(x - MapMaker.getMapMaker().getLocxShow(), y - MapMaker.getMapMaker().getLocyShow(), width, height);
        area = new Area(rectangle);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(-radiate, x - MapMaker.getMapMaker().getLocxShow() + width / 2, y - MapMaker.getMapMaker().getLocyShow() + height / 2);
        area = area.createTransformedArea(affineTransform);
        GameFrame.draw(area);
        return area;
    }

    /**
     * this method will be overwrite by subclasses
     * @return
     */
    protected Area setArea() {
        return null;
    }

    /**
     * a method for reset collide of a object
     */
    public void reset() {
        up = false;
        down = false;
        left = false;
        right = false;
        move = false;

    }

    /**
     * check colide method : check that are two shape have a collide or not
     * @param collidable
     * @param secondArea
     * @param xSecond
     * @param ySecond
     * @param widthSecond
     * @param heighSecond
     * @return
     */
    protected boolean checkCollide(Collidable collidable, Area secondArea, int xSecond, int ySecond, int widthSecond, int heighSecond) {
        area.intersect(secondArea);
        if (!area.isEmpty()) {
            move = true;
            if (x < xSecond && x + width >= xSecond && Math.abs(x + width - xSecond) < Math.abs(y - (ySecond + heighSecond)) && Math.abs(x + width - xSecond) < Math.abs(y + heighSecond - (ySecond)) && Math.abs(x + width - xSecond) < Math.abs(y+height+ySecond)) {
                right = true;
            } else if (x > xSecond && x <= xSecond + widthSecond && Math.abs(x - (xSecond + widthSecond)) < Math.abs(y - (ySecond + heighSecond)) && Math.abs(x - (xSecond + widthSecond)) < Math.abs(y + heighSecond - (ySecond))) {
                left = true;
            } else if (y <= ySecond + heighSecond && y + height > ySecond + heighSecond && Math.abs(x + width - xSecond) > 10 && Math.abs(x - xSecond - widthSecond) > 10 ){
                up = true;
            } else if (y < ySecond && y + height >= ySecond  && Math.abs(x + width - xSecond) > 10 && Math.abs(x - xSecond - widthSecond) > 10) {
                down = true;
            }
        }

        return !area.isEmpty();
    }


}
