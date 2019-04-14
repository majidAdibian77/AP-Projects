/**
 * a method for keep infromation that all class may need it
 *
 * @author Mohammad mighani
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Informations implements Serializable {
    int difficultyLevel;
    int level = 1;
    private static Informations informations = null;
    ArrayList<Wall> walls = new ArrayList<>();
    ArrayList<SoftWall> softWalls = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<EnemyTank> enemyTanks = new ArrayList<>();
    ArrayList<BulletTank> bullets = new ArrayList<>();
    Vector<BulletTank> bulletOwnTank = new Vector<>();
    ArrayList<BulletTank> newBullets = new ArrayList<>();
    ArrayList<Explosion> explosions = new ArrayList<>();


    public static Informations getInfo() {
        if (informations == null)
            informations = new Informations();
        return informations;
    }

    public static void setInformation(Informations information) {
        Informations.getInfo().enemyTanks = information.enemyTanks;
        Informations.getInfo().softWalls = information.softWalls;
        Informations.getInfo().items = information.items;
        Informations.getInfo().bullets = information.bullets;
        Informations.getInfo().level = information.level;
        Informations.getInfo().difficultyLevel = information.difficultyLevel;
    }

    public static void resetInformation() {
        informations = null;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
