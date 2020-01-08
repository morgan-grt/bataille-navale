package game;

/**
 *create couple of x and y 
 */
public class Coordinate {
    protected int x;
    protected int y;

    /**
     *enum of direction
     */
    public static enum Direction{
        HORIZONTAL(1,0),
        VERTICAL(0,1);
        protected int dx ;
        protected int dy ;
        
        Direction (int dx, int dy){
            this.dx = dx;
            this.dy = dy;
        }
        
        public int getDx(){
            return dx;
        }
        
        public int getDy(){
            return dy;
        }
    }
    
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int i){
        x = i;
    }
    
    public void setY(int j){
        y = j;
    }
    
    /**
     *for a boat's lenght permit to create next coord
     * @param coord
     * @param dir
     */
    public static Coordinate decaler(Coordinate coord, Direction dir){
        return (new Coordinate(dir.dx+coord.x,dir.dy+coord.y));
    }
    
    /**
     *return coordinate of a direction
     * @param dir
     */
    public Coordinate getDirectionCoord(Direction dir){
        if (dir == Direction.VERTICAL){
            return (new Coordinate(0,1));
        }
        else{
            return (new Coordinate(1,0));
        }
    }

    @Override
    public String toString(){
        return "(" + (x+1) + "," + (y+1) + ")";
    }
}
