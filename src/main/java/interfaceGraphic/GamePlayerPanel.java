package interfaceGraphic;

import gameElement.Case;
import gameElement.Sea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *paint the player sea and his boat
 */
public class GamePlayerPanel extends JPanel implements Observer{
    private final int cote = 32;
    private final static Color[] colors = 
    {new Color(232,126,4),new Color(17,58,83)};
    protected Case[][] grille;
    protected Sea sea;

    /**
     * Constructeur d'un panneau personalise qui agrandis les fonctionalitees de
     * base d'un panneau
     * @param sea
     * @param frame
     */
    public GamePlayerPanel(Sea sea, GameGUI frame) {
        super();
        this.sea = sea;
        grille = this.sea.getGrille();
        this.sea.addObserver(this);
        this.setLayout(null);
        this.setBackground(colors[1]);
    }
    
    public void afficheImg(Graphics g, int x, int y, Image img){
        g.drawImage(img, x, y, cote, cote, null);
    }
    
    public void gameReset(){
        //game.gameReset();
        //grille = sea.getGrille();
    }
    
    @Override
    public void update(Observable obs, Object arg){
        this.repaint();
    }
    
    public int getCote(){
        return cote;
    }

    /**
     * Surcharge de la methode paint qui est appelée a chaque fois que l'on met
     * à jour l'ecran
     *
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
                    g.setColor(Color.BLACK);
                    g.fillOval(j*cote+32,i*cote+32,cote,cote);
                    if (grille[j][i].touched){
                        if (grille[j][i].flowed){
                        g.setColor(Color.RED);
                        g.fillOval(j*cote+32,i*cote+32,cote,cote);
                        }
                        else{
                            g.setColor(colors[0]);
                            g.fillOval(j*cote+32,i*cote+32,cote,cote);
                        }
                    }
                }
            }
        }
        
        
    }
}
