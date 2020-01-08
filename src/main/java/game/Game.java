package game;

import gameElement.Boat;
import gameElement.Case;
import gameElement.Sea;
import player.Player;

/**
 *representation of the game and basics methods
 */
public class Game {
    private Player currentPlayer, player1, player2;
    private Sea currentSea, sea1, sea2;
    
    /**
     *requires two Player (the abstract class, both HPlayer or RPlayer)
     * and the size of the sea
     * @param player1
     * @param player2
     * @param hauteur
     * @param largeur
     */
    public Game(Player player1, Player player2, int hauteur, int largeur){
        this.player1 = player1;
        this.player2 = player2;
        sea1 = new Sea(new Coordinate(hauteur,largeur), player1);
        sea2 = new Sea(new Coordinate(hauteur,largeur), player2);
        currentPlayer = this.player1;
        currentSea = sea2;
    }
    
    /**
     *return true if one sea get all is boat flowed
     */
    public boolean isFinished(){
        return (sea1.isFinished() || sea2.isFinished());
    }
    
    public void switchCurrentPlayer(){
            currentPlayer = currentPlayer == player2 ? player1 : player2;
            currentSea = currentSea == sea1 ? sea2 : sea1;
    }
    
    /**
     *check for x and y position if there is a boat, if true set him to touched
     * and call second check method
     * @param x
     * @param y
     */
    public void checkCollision(int x, int y){
        if (currentSea.getGrille()[x][y].getBoat() != null){
            currentSea.getGrille()[x][y].touched = true;
            if (checkBoatIsFlowed(currentSea.getGrille()[x][y])){
                currentSea.decreaseNumberBoats(1);
            }
        }
        currentSea.notifyObservers();
    }
    
    /**
     *secondary check for a boat, if all is case (position) are touched set him
     * flowed 
     * @param aCase
     */
    public boolean checkBoatIsFlowed(Case aCase){
        Boat boat = aCase.getBoat();
        if (boat.flowed){
            return false;
        }
        for (int i = 0; i < boat.length; i++){
            Coordinate coordTmp = boat.getCoordList().get(i);
            if (!currentSea.getGrille()[coordTmp.x][coordTmp.y].touched){
                return false;
            }
            currentSea.getGrille()[coordTmp.x][coordTmp.y].update();
        }
        boat.flowed = true;
        updateBoatCase(boat);
        return boat.flowed;
    }
    
    public void updateBoatCase(Boat boat){
        for (Coordinate coord : boat.getCoordList()){
            currentSea.getGrille()[coord.getX()][coord.getY()].flowed = true;
        }
    }
    
    public Sea getSea1(){
        return sea1;
    }
    
    public Sea getSea2(){
        return sea2;
    }
    
    public Sea getCurrentSea(){
        return currentSea;
    }
    
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
}
