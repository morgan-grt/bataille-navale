package gameElement;
import static game.Coordinate.Direction.*;

/**
 *create a new missile after a shoot
 */
public class Missile extends GameElement{
    
    /**
     *require just x and y position
     * @param x
     * @param y
     */
    public Missile(int x, int y) {
        super(x, y, '!',1,VERTICAL);
    }
    
}
