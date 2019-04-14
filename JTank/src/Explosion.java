import java.io.Serializable;

public class Explosion implements Serializable {
    private int locX;
    private int locY;
    private int counter;

    public Explosion(int locX, int locY, int counter) {
        this.locX = locX;
        this.locY = locY;
        this.counter = counter;
    }

    public void count(){
        counter--;
        if(counter < 0){
            Informations.getInfo().explosions.remove(this);
        }
    }

    public int getLocX() {
        return locX;
    }

    public int getLocY() {
        return locY;
    }
}
