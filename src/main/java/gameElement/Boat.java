package gameElement;
import game.Coordinate;
import static game.Coordinate.Direction.*;

/**
 *gameElement for boat extends of GameElement
 */
public class Boat extends GameElement{
    public boolean flowed = false;
    
    /**
     *have two constructor one for length = 1 other for length > 1
     * with a direction
     * @param x
     * @param y
     */
    public Boat(int x, int y){
        super(x,y,'O',1,VERTICAL);
    }
    
    public Boat(int x, int y, int length, Coordinate.Direction dir){
        super(x,y,'O',length,dir);
    }
        
    /**
     *check if x and y position are in the coordlist of the boat
     * @param x
     * @param y
     */
    public Coordinate getCoordInList(int x, int y){
        for (Coordinate coord : coordList){
            if (coord.getX() == x && coord.getY() == y){
                return coord;
            }
        }
        return (new Coordinate(x,y));
    }
}
