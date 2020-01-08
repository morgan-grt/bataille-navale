package gameElement;
import game.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import player.Player;

/**
 *representation of the game
 * case per case
 * extends Observable because it's the principal actor in the game
 */
public class Sea extends Observable{
    protected Case grille[][];
    protected Player player;
    protected int numberBoats = 0;
    protected List<Boat> boatList = new ArrayList<>();
    
    public Sea(Coordinate coord, Player player){
        super();
        this.player = player;
        grille = new Case[coord.getX()][coord.getY()];
        for (int i = 0; i<grille.length;i++){
            for (int j = 0; j<grille[i].length;j++){
                grille[i][j] = new Case(this);
                
            }
        }
    }
    
    /**
     *set a boat on the sea
     * @param boat
     * @param coord
     * @param dir
     */
    public void placer(Boat boat, Coordinate coord, Coordinate.Direction dir){
        Coordinate tmp = coord;
        for(int i = 0; i < boat.length; i++){
            if (grille.length > tmp.getX() || grille[0].length > tmp.getY()){
                grille[tmp.getX()][tmp.getY()].placer(boat);
                tmp = Coordinate.decaler(tmp,dir);
            }
        }
        boatList.add(boat);
        numberBoats++;
        setChanged();
        notifyObservers();
    }
    
    /**
     *return true if all his boat are flowed
     * numberBoats decrease when a boat is flowed
     */
    public boolean isFinished(){
        return (numberBoats == 0);
    }
    
    /**
     *set a missile on the sea
     * @param missile
     * @param coord
     */
    public void placer(Missile missile, Coordinate coord){
        grille[coord.getX()][coord.getY()].placer(missile);
        setChanged();
    }
    
    public Case[][] getGrille(){
        return grille;
    }
    
    public int getNumberBoats(){
        return numberBoats;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public void decreaseNumberBoats(int n){
        numberBoats -= n;
    }
    
    /**
     *check when setting a boat if the case already have a boat
     * @param checkBoat
     */
    public boolean checkBoatSet(Boat checkBoat){
        for (Coordinate coord : checkBoat.getCoordList()){
            if (grille[coord.getX()][coord.getY()].getBoat() != null
                    && grille[coord.getX()][coord.getY()].getBoat() != checkBoat){
                return false;
            }
        }
        return true;
    }
    
    /**
     *return a list of validshoot to permit of not shoot in a case who already
     * have a missile
     */
    public List<Coordinate> checkValidShoot(){
        List<Coordinate> list = new ArrayList<>();
        for (int i = 0; i < getGrille().length; i++) {
            for (int j = 0; j < getGrille()[i].length; j++) {
                if (getGrille()[i][j].missile == null){
                    list.add((new Coordinate(i,j)));
                }
            }
        }
        return list;
    }
    
    @Override
    public String toString(){
        return "Sea:"+player;
    }
            
}
