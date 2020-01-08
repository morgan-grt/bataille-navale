package interfaceGraphic;

import gameElement.Case;
import gameElement.Sea;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *view of the other player sea with not his boat, to shoot on 
 */
public class GameFrontPanel extends JPanel implements Observer{
    private final int cote = 32;
    private final static Color[] colors = 
    {new Color(232,126,4),new Color(17,58,83)};
    protected Case[][] grille;
    protected Sea sea;
    
    public GameFrontPanel(Sea sea, GameGUI frame) {
        super();
        this.sea = sea;
        grille = this.sea.getGrille();
        this.sea.addObserver(this);
        this.setLayout(null);
        this.setBackground(colors[1]);
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        this.repaint();
    }
    
    public int getCote(){
        return cote;
    }
    
    /**
     *paint the sea like PlayerPanel with one difference, it doesn't paint the main
     * boats
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < grille.length; i++) {
            g.setColor(Color.BLACK);
            g.drawString(""+(i + 1),8,(i * 32) + 48);
            g.drawString(""+((char) (65 + i)), 42 + (i * 32), 16);
            for (int j = 0; j < grille[i].length; j++) {
                g.setColor(Color.WHITE);
                g.drawRect(j*cote+32,i*cote+32,cote,cote);
                if (grille[j][i].missile != null){
                    g.setColor(Color.GREEN);
                    g.fillOval(j*cote+32,i*cote+32,cote,cote);
                }
                if (grille[j][i].getBoat() != null){
                    if (grille[j][i].touched){
                        g.setColor(colors[0]);
                        g.fillOval(j*cote+32,i*cote+32,cote,cote);
                        if (grille[j][i].flowed){
                            g.setColor(Color.RED);
                            g.fillOval(j*cote+32,i*cote+32,cote,cote);
                        } 
                    }
                }
            }
        }
    }
}
