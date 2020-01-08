package interfaceGraphic;
import game.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import menu.*;
import player.Player;
import player.HPlayer;
import player.RPlayer;

/**
 *principal class when playing on interface graphic
 */
public class GameGUI extends JFrame implements MouseListener
        , ActionListener, KeyListener, Observer{
    private Game game;
    private Player player1,player2;
    private GamePanel currentPanel, panel1, panel2;
    private AbstractMenu menu, pause, victory;
    private int numberMenu = 0;
    private int mouseShootX, mouseShootY;
    
    public GameGUI() throws IOException{
        initPlayer();
        setTitle("Bataille Navale");
        menu = new PrincipalMenu(this);
        pause = new PauseMenu(this);
        victory = new VictoryMenu(this);
        setContentPane(menu);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Visibilité       
        setVisible(true);
        refresh();
    }
    
    /**
     *two methods to choose of playing human versus human or human versus random
     */
    public void initPlayer(){
        player1 = new HPlayer("Player1",2);
        player2 = new RPlayer("Player2");
        initGame();
    }
    
    public void initPlayer2(){
        player1 = new HPlayer("Player1",2);
        player2 = new HPlayer("Player2",2);
        initGame();
    }
    
    /**
     *init game and game panel 
     */
    public void initGame(){
        game = new Game(player1,player2,10,10);
        panel1 = new GamePanel(game.getSea1(),game.getSea2(),player1,this);
        panel2 = new GamePanel(game.getSea2(),game.getSea1(),player2,this);
        currentPanel = panel1;
        setSize(panel1.getCote()*12*2,panel1.getCote()*12+32);
        addKeyListener(this);
        panel1.frontPanel.addMouseListener(this);
        panel2.frontPanel.addMouseListener(this);
        panel1.selectPanel.getInitBoat().addObserver(this);
        panel2.selectPanel.getInitBoat().addObserver(this);
        refresh();
    }
    
    /**
     *basically important to actualize the painting of element
     */
    public void refresh(){
        repaint();
        revalidate();
    }
    
    public void gameReset(){
        initPlayer();
    }
    
    public void switchMenu(int number){
        numberMenu = number;
        if (numberMenu == 0){
            setContentPane(menu);
        }
        else if (numberMenu == 1){
            setContentPane(currentPanel);
        }
        else if (numberMenu == 2){
            setContentPane(pause);
        }
        else if (numberMenu == 3){
            setContentPane(victory);
        }
        refresh();
    }
    
    public void switchCurrentPlayer(){
        if (player1 instanceof HPlayer && player2 instanceof HPlayer){
            currentPanel = currentPanel == panel1 ? panel2 : panel1;
            switchMenu(1);
        }
        game.switchCurrentPlayer();
        refresh();
    }

    /**
     *get a event on a click with the mouse (principaly for shooting)
     * @param arg0
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        mouseShootX = (arg0.getX() - 32) / panel1.getCote();
        mouseShootY = (arg0.getY() - 32) / panel1.getCote();
        play();
        refresh();
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    /**
     *get a event when push a button
     * @param arg0
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == panel1.selectPanel.getValidSelection()){
            panel1.selectPanel.setBoatInSea();
            if (panel1.selectPanel.getInitBoat().checkBoatAllPlaced()){
                if (panel2.selectPanel.getInitBoat().checkBoatAllPlaced()){
                    panel1.switchMenu(0);
                }
                else{
                    panel1.switchMenu(2);
                }
                if (player1 instanceof HPlayer && player2 instanceof HPlayer){
                    currentPanel = panel2;
                    switchMenu(1);
                    refresh();
                }
            }
        }
        else if (arg0.getSource() == panel2.selectPanel.getValidSelection()){
            panel2.selectPanel.setBoatInSea();
            if (panel2.selectPanel.getInitBoat().checkBoatAllPlaced()){
                if (panel1.selectPanel.getInitBoat().checkBoatAllPlaced()){
                    panel2.switchMenu(0);
                }
                else{
                    panel2.switchMenu(2);
                }
                if (player1 instanceof HPlayer && player2 instanceof HPlayer){
                    currentPanel = panel1;
                    switchMenu(1);
                    refresh();
                }
            }
        }
        else if (arg0.getSource() == menu.getButton1() 
                || arg0.getSource() == pause.getButton1()){
            switchMenu(1);
        }
        else if (arg0.getSource() == menu.getButton3()){
            initPlayer2();
            switchMenu(1);
        }
        else if (arg0.getSource() == victory.getButton1() 
                || arg0.getSource() == pause.getButton3()){
            gameReset();
            victory.resetLabel();
            switchMenu(0);
        }
        refresh();
    }
    
    /**
     *method to play a turn of the game
     */
    public void play(){
        try{
            game.getCurrentPlayer()
                        .setListValidShoot(game.getCurrentSea().checkValidShoot());
            if (game.getCurrentPlayer() instanceof HPlayer){
                if (game.getCurrentPlayer().listContainsCoord(mouseShootX,mouseShootY)){
                    game.getCurrentPlayer().setShootX(mouseShootX);
                    game.getCurrentPlayer().setShootY(mouseShootY);
                    game.getCurrentPlayer().shoot(game.getCurrentSea());
                }
                else{
                    switchCurrentPlayer();
                }
            }
            else if (game.getCurrentPlayer() instanceof RPlayer){
                game.getCurrentPlayer().shoot(game.getCurrentSea());
            }
            game.checkCollision(game.getCurrentPlayer().getShootX()
                , game.getCurrentPlayer().getShootY());
        }
        catch(ArrayIndexOutOfBoundsException aioobe){
            switchCurrentPlayer();
        }
        if (game.isFinished()){
            victory.setWinner(game.getCurrentPlayer().toString());
            switchMenu(3);
        }
        else{
            switchCurrentPlayer();
            if (game.getCurrentPlayer() instanceof RPlayer){
                play();
            }
        }
        refresh();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        try{
            if (panel2.getNumberMenu() == 2 
                    && panel1.selectPanel.getInitBoat().getAllPlaced()){
                        panel2.switchMenu(0);
            }
            else if (panel1.getNumberMenu() == 2 
                    && panel2.selectPanel.getInitBoat().getAllPlaced()){
                        panel1.switchMenu(0);
            }
        }
        catch(NullPointerException npe){}
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyChar() == 'p'){
            switchMenu(2);
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }
}
