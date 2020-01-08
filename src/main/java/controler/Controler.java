package controler;
import java.util.Random;
import java.util.Scanner;
import player.Player;

/**
 *used to set player control
 */
public class Controler {
    private Player player;
    private Random random = new Random();
    
    /**
     *
     * @param player
     */
    public Controler(Player player){
        this.player = player;
    }
    
    /**
     *permit to set x and y for player shoot
     */
    public void setShootControl(){
        boolean done = false;
        if (player.getNumber() == 0){
            while (!done){
                player.setShootX(random.nextInt(10));
                player.setShootY(random.nextInt(10));
                done = player.listContainsCoord(player.getShootX(),player.getShootY());
            }
        }
        else if (player.getNumber() == 1){
            while (!done){
                System.out.println("Entrez un x et un y pour tirer!");
                Scanner scanner = new Scanner(System.in);
                player.setShootX(scanner.nextInt()-1);
                player.setShootY(scanner.nextInt()-1);
                done = player.listContainsCoord(player.getShootX(),player.getShootY());
                System.out.println("Valeurs impossibles ou déjà utilisées!");
            }
        }
    }
}
