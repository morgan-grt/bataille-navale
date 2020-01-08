package interfaceGraphic;

import game.Coordinate.Direction;
import static game.Coordinate.Direction.*;
import controler.InitBoat;
import gameElement.Boat;
import gameElement.Sea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *panel to choose for a human where he want to set is boat at the beginning
 * of the game
 */
public class GameSelectBoatPanel extends JPanel implements Observer
        , MouseListener, MouseMotionListener{
    protected Sea sea;
    private final static Color[] colors = 
    {new Color(232,126,4),new Color(17,58,83)
            ,new Color(0,0,0,75),new Color(12,41,59)};
    protected final InitBoat initBoat;
    private Point mousePosition;
    protected EllipseBoolean boat1,boat2,boat3,boat4,boat5;
    protected List<EllipseBoolean> ellipseList = new ArrayList<>();
    protected JButton validSelection;
    protected JComboBox combo1;
    private int numberForm = 0;
    
    public class EllipseBoolean{
        
        protected Ellipse2D ellipse;
        protected boolean isUsed = false;
        
        public EllipseBoolean(Ellipse2D ellipse, boolean isUsed){
            this.ellipse = ellipse;
            this.isUsed = isUsed;
        }
        public Ellipse2D getEllipse(){
            return ellipse;
        }
        public boolean getIsUsed(){
            return isUsed;
        }
        public void setIsUsed(boolean bool){
            isUsed = bool;
        }
    }
    
    public GameSelectBoatPanel(Sea sea, GameGUI frame){
        this.sea = sea;
        initBoat = new InitBoat(frame,this,this.sea);
        initBoatForm();
        sea.getPlayer().setBoat(initBoat);
        setLayout(null);
        validSelection = new JButton("SELECT");
        validSelection.setBounds(416,320,128,32);
        validSelection.setForeground(Color.WHITE);
        validSelection.setBackground(colors[2]);
        combo1 = new JComboBox(initBoat.getSelectionBoat());
        combo1.setBounds(542,320,160,32);
        combo1.setForeground(Color.WHITE);
        combo1.setBackground(colors[3]);
        GridBagConstraints pos = new GridBagConstraints();
        validSelection.setVisible(true);
        validSelection.setFocusable(false);
        validSelection.setRolloverEnabled(false);
        combo1.setVisible(true);
        combo1.setFocusable(false);
        pos.gridx = 0;
        pos.gridwidth = 2;
        pos.gridheight = 2;
        pos.gridy = 0;
        add(combo1,pos);
        pos.gridx = 0;
        pos.gridwidth = 2;
        pos.gridheight = 2;
        pos.gridy = 2;
        add(validSelection,pos);
        this.setBackground(colors[1]);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    /**
     *depending on selected prefabricated list of boat, the graphical form of boat
     */
    public void initBoatForm(){
        ellipseList.clear();
        boat1 = setBoatForm(initBoat.getLengthTab()[initBoat.getSelectedTab()][0]);
        boat2 = setBoatForm(initBoat.getLengthTab()[initBoat.getSelectedTab()][1]);
        boat3 = setBoatForm(initBoat.getLengthTab()[initBoat.getSelectedTab()][2]);
        boat4 = setBoatForm(initBoat.getLengthTab()[initBoat.getSelectedTab()][3]);
        boat5 = setBoatForm(initBoat.getLengthTab()[initBoat.getSelectedTab()][4]);
        ellipseList.add(boat1);
        ellipseList.add(boat2);
        ellipseList.add(boat3);
        ellipseList.add(boat4);
        ellipseList.add(boat5);
        repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        mousePosition = arg0.getPoint();
        repaint();
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
     *permit to moove an ellipse since she had been clicked
     * @param arg0
     */
    @Override
    public void mouseDragged(MouseEvent arg0) {
        int dx = arg0.getX() - mousePosition.x;
        int dy = arg0.getY() - mousePosition.y;
        for (EllipseBoolean ellipse : ellipseList){
            if (ellipse.getEllipse().contains(arg0.getX(), arg0.getY())){
                double xc = ellipse.getEllipse().getX();
                double yc = ellipse.getEllipse().getY();
                ellipse.getEllipse().setFrame(xc+=dx, yc+=dy
                        , ellipse.getEllipse().getWidth()
                        , ellipse.getEllipse().getHeight());
            }
        }
        
        mousePosition = arg0.getPoint();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        removeAll();
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i<10; i++){
            g.setColor(Color.BLACK);
            g.drawString(""+(i + 1),8,(i * 32) + 48);
            g.drawString(""+((char) (65 + i)), 42 + (i * 32), 16);
            for (int j = 0; j<10; j++){
                g.setColor(Color.WHITE);
                g.drawRect(j*32+32,i*32+32,32,32);
            }
        }
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2d.fill(boat1.getEllipse());
        g2d.fill(boat2.getEllipse());
        g2d.fill(boat3.getEllipse());
        g2d.fill(boat4.getEllipse());
        g2d.fill(boat5.getEllipse());
    }
    
    /**
     *set the size, width and length of an ellipse depending a boat
     * @param length
     */
    public EllipseBoolean setBoatForm(int length){
        Direction dir = VERTICAL;
        if (numberForm % 2 == 0){
            dir = HORIZONTAL;
        }
        numberForm++;
        int x = (704 - length * 64) + 16;
        int y = (320 - length * 64) + 16;
        int width = 32 + ((length-1) * 32 * dir.getDx());
        int height = 32 + ((length-1) * 32 * dir.getDy());
        List<Integer> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        list.add(width);
        list.add(height);
        return (new EllipseBoolean(
                new Ellipse2D.Double(
                        list.get(0),list.get(1),list.get(2)
                        ,list.get(3)), false));
    }
    
    public List<EllipseBoolean> getEllipseList(){
        return ellipseList;
    }
    
    /**
     *check if an ellipse is placed on the graphical sea
     * @param ellipse
     * @param boat
     */
    public boolean checkBoatInSea(EllipseBoolean ellipse, Boat boat){
        int x = (int) (ellipse.getEllipse().getX() + 16) / 32;
        int y = (int) (ellipse.getEllipse().getY() + 16) / 32;
        if (x > 10 || y > 10 || ellipse.getIsUsed()){
            return false;
        }
        else if (!ellipse.getIsUsed() && !boat.getIsUsed()){
            int width = (int) ellipse.getEllipse().getWidth() / 32;
            int height  = (int) ellipse.getEllipse().getHeight() / 32;
                if ((boat.length * boat.dir.getDx()) == width 
                        || (boat.length * boat.dir.getDy()) == height 
                        && !boat.getIsUsed()){
                    return true;
                }
            }
        return false;
    }
    
    /**
     *depending on a ellipse position, create and set a boat in the game sea
     */
    public void setBoatInSea(){
        for (EllipseBoolean ellipse : ellipseList){
            for (Boat boat : initBoat.getBoatList()){
                if (checkBoatInSea(ellipse, boat)){
                    int x = (int) (ellipse.getEllipse().getX() 
                        + 16) / 32 - 1;
                    int y = (int) (ellipse.getEllipse().getY() 
                        + 16) / 32 - 1;
                    boat.setCoord(x,y);
                    if (sea.checkBoatSet(boat)){
                        sea.placer(boat,boat.getCoord(),boat.dir);
                        ellipse.setIsUsed(true);
                        boat.setIsUsed(true);
                    }
                }
            }
        }
    }

    public JButton getValidSelection(){
        return validSelection;
    }
    
    public JComboBox getCombo1(){
        return combo1;
    }
    
    public InitBoat getInitBoat(){
        return initBoat;
    }
    
    public Sea getSea(){
        return sea;
    }
    
}
