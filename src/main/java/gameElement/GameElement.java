package gameElement;
import game.*;
import java.util.ArrayList;
import java.util.List;

/**
 *create the basics attribut for Boat, Missile
 */
public abstract class GameElement {
    protected Coordinate coord;
    protected List<Coordinate> coordList = new ArrayList<>();
    protected char symbol;
    public Coordinate.Direction dir;
    public int length;
    protected boolean isUsed = false;
    
    public GameElement(int x, int y, char symbol, int length, Coordinate.Direction dir){
        this.symbol = symbol;
        this.coord = new Coordinate(x,y);
        this.dir = dir;
        this.length = length;
        Coordinate tmp = new Coordinate(x,y);
        coordList.add(tmp);
        for(int i = 0; i < length-1; i++){
            tmp = Coordinate.decaler(tmp,dir);
            coordList.add(tmp);
        }
    }

    public char getSymbol(){
        return symbol;
    }
    
    public void setSymbol(char newSymbol){
        symbol = newSymbol;
    }
    
    public Coordinate getCoord(){
        return coord;
    }
    
    public void setCoord(int i, int j){
        coord.setX(i);
        coord.setY(j);
        updateCoordList(coord);
    }
    
    public List<Coordinate> getCoordList(){
        return coordList;
    }
    
    public void updateCoordList(Coordinate coord){
        coordList.clear();
        Coordinate tmp = coord;
        coordList.add(tmp);
        for(int i = 0; i < length-1; i++){
            tmp = Coordinate.decaler(tmp,dir);
            coordList.add(tmp);
        }
    }
    
    @Override
    public String toString(){
        return ""+symbol;
    }
    
    public boolean getIsUsed(){
            return isUsed;
    }
    public void setIsUsed(boolean bool){
        isUsed = bool;
    } 
    
}
