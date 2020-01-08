
package player;
import controler.Controler;
import game.Coordinate;
import controler.InitBoat;
import gameElement.Missile;
import gameElement.Sea;
import java.util.List;

/**
 *abstract class for player to define basical attributs and methods
 */
public abstract class Player {
    protected String playerName;
    protected int shootX, shootY, number;
    protected List<Coordinate> listValidShoot;
    protected Controler controler;
    
    public Player(String player, int number){
        playerName = player;
        this.number = number;
        controler = new Controler(this);
    }
    
    /**
     *shoot on the sea at given position
     * @param sea
     * @param ligne
     * @param col
     */
    public void shoot(Sea sea, int ligne, int col){
        Missile tmp = new Missile(ligne,col);
        sea.placer(tmp, tmp.getCoord());
    }   
    
    /**
     *shoot on the sea after set position depending on controler's set
     * @param sea
     */
    public void shoot(Sea sea){
        controler.setShootControl();
        shoot(sea,shootX,shootY);
    }    
    
    public abstract void setBoat(InitBoat initBoat);
    
    /**
     *check if x and y are valid shoot (if they haven't been already taken)
     * @param x
     * @param y
     */
    public boolean listContainsCoord(int x, int y){
        for (Coordinate coord : listValidShoot){
            if (coord.getX() == x && coord.getY() == y){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return playerName;
    }

    public int getShootX() {
        return shootX;
    }

    public int getShootY() {
        return shootY;
    }
    
    public void setShootX(int x){
        shootX = x;
    }
    
    public void setShootY(int y){
        shootY = y;
    }
    
    public int getNumber(){
        return number;
    }
    
    public void setListValidShoot(List<Coordinate> list){
        listValidShoot = list;
    }
}