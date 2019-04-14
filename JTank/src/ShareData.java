import java.io.Serializable;
import java.util.ArrayList;

/**
 * a class for compact all of information that shared in network
 */
public class ShareData implements Serializable
{
    private GameState friendTank;
    private ArrayList<BulletTank> newBullets;
//    private Temp informations;
    private ArrayList<Item> items ;
    private int difficultyLevel;
//    static final long serialVersionUID = 1L;
    public ShareData(GameState friendTank, ArrayList<BulletTank> newBullets,ArrayList<Item> items, int difficultyLevel)
    {
        this.friendTank = friendTank;
        this.newBullets = newBullets;
//        this.informations = informations;
        this.items = items;
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * setter and getters
     * @return
     */

    public GameState getFriendTank()
    {
        return friendTank;
    }

    public ArrayList<BulletTank> getNewBullets()
    {
        return newBullets;
    }

//    public Temp getInformations()
//    {
//        return informations;
//    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
}


