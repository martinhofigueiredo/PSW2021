package dkeep.cli;
import dkeep.logic.Game;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
       
        //Game Loop

        Scanner s = new Scanner(System.in);
        char uc;
        boolean isGameOver = false;
        System.out.print("How many dragons?>");
        int dn = s.nextInt();
        Game game = new Game();
        game.start(dn);
        do{
            //clear terminal
            clearMaze();
            //print maze
            printMaze(game.getMap());
            if(!isGameOver) System.out.println(game.getOutputMsg());
            System.out.println("K = " + game.getscore());
            //Read Input
            System.out.print(">");
            uc = s.next().charAt(0);
            //Update world
            isGameOver = game.updateHero(uc);
            isGameOver = game.updatedragons();
        } while ((uc != 'q') && (!isGameOver));

        //Last Screen Conclusion
        printMaze(game.getMap());
        System.out.println(game.getOutputMsg());

        System.out.println("Closing!");
        s.close();

    
    }
    private static void printMaze(char[][] map){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    private static void clearMaze(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}