package player;
import controler.InitBoat;

/**
 *random player class
 */
public class RPlayer extends Player{
    
    public RPlayer(String player) {
        super(player,0);
    }
    
    @Override
    public void setBoat(InitBoat initBoat){
        initBoat.setBoatRandom();
    }
}
