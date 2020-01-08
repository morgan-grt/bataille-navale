package player;
import controler.InitBoat;

/**
 *human player class
 */
public class HPlayer extends Player {
    
    public HPlayer(String player, int number) {
        super(player,number);
    }
    
    @Override
    public void setBoat(InitBoat initBoat){
        initBoat.setBoat();
    }
}