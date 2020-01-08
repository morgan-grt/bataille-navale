package interfaceGraphic;
import gameElement.Sea;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import player.*;

/**
 *have sub panels to delegate actions to do
 */
public class GamePanel extends JPanel implements ActionListener{
    protected final int cote = 32;
    protected Sea sea1,sea2;
    protected Player player;
    private GameGUI frame;
    protected GamePlayerPanel playerPanel;
    protected GameFrontPanel frontPanel;
    protected GameSelectBoatPanel selectPanel;
    protected GameWaitPanel waitPanel;
    protected int numberMenu;

    /**
     * Constructeur d'un panneau personalise qui agrandis les fonctionalitees de
     * base d'un panneau
     * @param sea
     * @param frame
     */
    public GamePanel(Sea sea1, Sea sea2,Player player, GameGUI frame) {
        super();
        this.frame = frame;
        this.sea1 = sea1;
        this.sea2 = sea2;
        this.player = player;
        playerPanel = new GamePlayerPanel(this.sea1,this.frame);
        frontPanel = new GameFrontPanel(this.sea2,this.frame);
        selectPanel = new GameSelectBoatPanel(this.sea1,this.frame);
        waitPanel = new GameWaitPanel(this.frame);
        setLayout(new GridLayout(1,2));
        if (this.player instanceof HPlayer){
            add(selectPanel);
        }
        else if (this.player instanceof RPlayer){
            switchMenu(0);
        }
        addMouseListener(selectPanel);
        addMouseMotionListener(selectPanel);
        selectPanel.getValidSelection().addActionListener(this.frame);
        selectPanel.getCombo1().addActionListener(this);
        refresh();
        
    }
    
    public int getCote(){
        return cote;
    }
    
    public void refresh(){
        repaint();
        revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    public void switchMenu(int number){
        numberMenu = number;
        removeAll();
        if (numberMenu == 0){
            add(playerPanel);
            add(frontPanel);
        }
        else if (numberMenu == 1){
            add(selectPanel);
        }
        else if (numberMenu == 2){
            add(waitPanel);
        }
        frame.refresh();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == selectPanel.getCombo1()){
            String string = (String) selectPanel.getCombo1().getSelectedItem();
            int item = selectPanel.getInitBoat().getIndexSelectionBoat(string);
            selectPanel.getInitBoat().setSelectedTab(item);
            selectPanel.initBoatForm();
        }
    }
    
    public int getNumberMenu(){
        return numberMenu;
    }
}
