package dkeep.logic;
import dkeep.logic.player;
import java.util.*;
import java.lang.Math.*;

public class Game {
        
    char[][] map = {{'X','X','X','X','X','X','X','X','X','X'},
                    {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
                    {'X',' ','X','X',' ','X',' ','X',' ','X'},
                    {'X',' ','X','X',' ','X',' ','X',' ','X'},
                    {'X',' ','X','X',' ','x',' ','X',' ','X'},
                    {'X',' ',' ',' ',' ',' ',' ','X',' ','X'},
                    {'X',' ','X','X',' ','X',' ','X',' ','X'},
                    {'X',' ','X','X',' ','X',' ','X',' ','X'},
                    {'X',' ','X','X',' ',' ',' ',' ',' ','X'},
                    {'X','X','X','X','X','X','X','X','X','X'},}; //10x10

    
    boolean isGameOver = false;
    String outputMessage = "";
    String[] MessageList = {"","You Died","Got The KEY!","Congratulations you managed to escape!","You need 3 Key For this Door.","That's a Wall","You Found A SWORD!","You have Slayed The Dragon"};
    String scorestr = "K = ";
    Random rand = new Random();
    player hero = new player();
    player dragon = new player();
    player sword = new player();
    player exit = new player();
    ArrayList<player> dragonsList = new ArrayList<>();

  


    public boolean updateHero(char uc) {
        
        int a = 0;
        int b = 0;
        switch(uc) {
            case 'w': //UP
                a = -1;
                b = 0;   
            break;

            case 's': //Down
                a = 1;
                b = 0;
            break;

            case 'a': //LEFT
                a = 0;
                b = -1;
            break;                
    
            case 'd': //Right
            a = 0;
            b = 1;
            break;
        }

        switch(map[hero.x+a][hero.y+b]){
            case ' ':
                this.move(a,b, hero);
                outputMessage = MessageList[0];
            break;

            case 'S':
                this.move(a,b, hero);
                hero.arming();
                sword.wipe();
                outputMessage = MessageList[6];
            break;

        
            case 'E':
            if(hero.hasKey){//&& dragonsList.){ //Number of Keys need to open the lock
                this.move(a,b, hero);
                outputMessage = MessageList[3]; //WIN
                isGameOver = true;
            } else {
            outputMessage = MessageList[4]; //Locked
            }
            break;
        
            case 'D':
                if(hero.isArmed){
                    this.move(a,b, hero);
                    hero.hasKey = true;
                    outputMessage = MessageList[7];
                    for(player p: dragonsList){
                        if((hero.x==p.x)&&(hero.y==p.y)){
                            p.wipe();
                        }
                    }
                    
                }else{
                    outputMessage = MessageList[1];
                    isGameOver = true;
                    hero.isDead = true;
                }
                
            break;
        
            case 'K':
                /*/not Used
                this.move(a,b, hero);
                outputMessage = MessageList[2];
                hero.hasKey = true; //1MEEC_T02 Quest I Here be the dragons
                //key.wipe();
                /*if(hero.hasKey < 3){
                    this.placeRandom(key);
        
                } */
            break;
        
            case 'X':
                outputMessage = MessageList[5];
            break;
            

        }
        isGameOver = hero.isDead;
        return isGameOver;
    }

    public boolean updatedragons() { //TODO: Dragons dont die for some reason;
        for(player p: dragonsList){
            
            p.setpiece('D');
            int a = rand.nextInt(2)-1;
            int b = rand.nextInt(2)-1;

            double chance  = Math.random(); //BESERK MODE TODO:improve zoomies
            if(chance < 0.35){
                a = a + rand.nextInt(2)+1;
                b = b + rand.nextInt(2)+1;
            }
            a = this.limit(p.x+a, 1, 8) - p.x;
            b = this.limit(p.y+b, 1, 8) - p.y;

            switch(map[p.x+a][p.y+b]){
                case ' ':
                this.move(a,b, p);
                break;
    
                case 'S':
                p.setpiece('F');
                this.move(a,b, p);               
                break;
    
            
                case 'E':
               
                break;
            
                case 'D':
                    
                break;
            
                case 'K':
                    
                break;
            
                case 'X':
                
                break;

                case 'H':
                    hero.isDead = true;
                break;
                
                case 'A':
                    p.wipe();
                break;
            }

        }
        this.placexy(sword);
        isGameOver = hero.isDead;
        return isGameOver;
    }

    public String getOutputMsg() {
        return outputMessage;
    }
    public char[][] getMap() {
        return map;
    }
    private void move(int a, int b, player player){
        map[player.x+a][player.y+b] = player.piece;
        map[player.x][player.y] = ' ';
        player.x=player.x+a;
        player.y=player.y+b;
    } 
    public void start(int dn) {
        hero.setpiece('H');
        //dragon.setpiece('D');
        for(int i=0; i<dn; i++){
            dragonsList.add(new player());
            dragonsList.get(i).setpiece('D');
            this.placeRandom(dragonsList.get(i));
        }
        
        sword.setpiece('S');
        exit.setpiece('E');
        //key.setpiece('K');
        
        
        hero.setposition(1, 1);
        this.placexy(hero);
        //this.placeRandom(dragon);
        
        
        this.placeRandom(sword);
        this.placeRandom(exit);
        //this.placeRandom(key);
        
    }

    public void placeRandom(player player){
          //generate random position
        do{
            player.x = rand.nextInt(8)+1;
            player.y = rand.nextInt(8)+1;
            if(player.piece == 'E'){
                switch(rand.nextInt(4)){
                    case 0: //North
                        player.y = 0;
                    break;
                    case 1: //East
                        player.x = 9;
                    break;
                    case 2: //South
                        player.y = 9;
                    break;
                    case 3: //West
                        player.x = 0; 
                    break;
                }
                map[player.x][player.y] = player.piece;    //Places Key
                player.isDead = false;
            }
            if(map[player.x][player.y] == ' '){   //Check is empty - should be a methon but oh well
                map[player.x][player.y] = player.piece;    //Places Key
                player.isDead = false;
            }
        }
        while(player.isDead);
    }
    
    public void placexy(player player){
        map[player.x][player.y] = player.piece;
        player.isDead = false;    
    }

    public boolean getscore() {
        return hero.hasKey;
    }

    public int limit(int a, int x1, int x2){
        if(a > x2)
            return x2;
        if(a < x1)
            return x1;
        else
            return a;
    }
   
    
}
