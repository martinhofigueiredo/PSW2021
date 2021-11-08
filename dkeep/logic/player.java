package dkeep.logic;
import java.util.Random;

public class player {
    int x=0;
    int y=0;
    char piece = ' ';
    boolean isDead = true;
    boolean isArmed = false;
    Random rand = new Random();
    boolean hasKey = false;

    
    public void setposition(int a, int b){
        this.x = a;
        this.y = b;
    }
    public void setpiece(char p){
        this.piece = p;
    }
    public void arming(){
        this.isArmed = true;
        this.piece = 'A';
    }
    public void wipe(){
        this.isDead = true;
        this.piece = ' ';
    }
    
}
