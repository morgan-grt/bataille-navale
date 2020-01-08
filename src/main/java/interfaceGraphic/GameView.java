package interfaceGraphic;
import controler.InitBoat;
import game.*;
import gameElement.ViewSea;
import player.*;

/**
 *principal class when playing on therminal
 */
public class GameView {
    protected Game game;
    private ViewSea currentViewSea, viewSea1, viewSea2;
    protected InitBoat initBoat1,initBoat2;
    
    public GameView(){
        initGame();
        play();
    }
    
    /**
     *init game with human player and random player preselected
     */
    public void initGame(){
        Player player1 = new HPlayer("Player1",1);
        Player player2 = new RPlayer("Player2");
        game = new Game(player1,player2,10,10);
        viewSea1 = new ViewSea(game.getSea1());
        viewSea2 = new ViewSea(game.getSea2());
        currentViewSea = viewSea2;
        initBoat1 = new InitBoat(game.getSea1());
        initBoat2 = new InitBoat(game.getSea2());
        player1.setBoat(initBoat1);
        player2.setBoat(initBoat2);
    }
    
    public void switchCurrentPlayer(){
        game.switchCurrentPlayer();
        System.out.println(game.getCurrentSea());
        currentViewSea = currentViewSea == viewSea1 ? viewSea2 : viewSea1;
    }
    
    /**
     *pemit to play the game while he is finished
     */
    public void play(){
        while(!game.isFinished()){
            game.getCurrentPlayer()
                    .setListValidShoot(game.getCurrentSea().checkValidShoot());
            game.getCurrentPlayer().shoot(game.getCurrentSea());
            game.checkCollision(game.getCurrentPlayer().getShootY()
                    , game.getCurrentPlayer().getShootX());
            switchCurrentPlayer();
        }
        switchCurrentPlayer();
        System.out.println(currentViewSea.situationToString() + "\n");
        System.out.println(game.getCurrentPlayer()+" vous avez gagné!");
    }
}
