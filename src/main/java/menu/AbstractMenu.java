package menu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *prefabricated menu who set bacisal methods and attributs of a menu
 */
public abstract class AbstractMenu extends JPanel{
    private JLabel label;
    private final static String error = "ERROR";
    protected JButton button1, button2, button3, button4;
    protected JComboBox combo1;
    private JFrame frame;
    private Image img;
    public final static Color color1 = new Color(0,0,0,75);
    public final static Color color2 = new Color(0,114,152);
    public final static Font font = new Font("Bitstream Vera Sans Mono",Font.PLAIN,28);
    public final static Font font2 = new Font("Bitstream Vera Sans Mono",Font.ITALIC,14);
    public GridBagConstraints pos = new GridBagConstraints();
    
    public AbstractMenu(JFrame frame, String BUTTON1, String BUTTON2,String nomImgFond)
            throws IOException{
        this.frame = frame;
        button1 = createJButton(BUTTON1,320,96);
        setJButtonIcon(button1,"play_icon");
        button1.addActionListener((ActionListener) this.frame);
        button2 = createJButton(BUTTON2,320,96);
        setJButtonIcon(button2,"cross_icon");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setLayout(new GridBagLayout());
        pos.fill = GridBagConstraints.BOTH;
        setJButton(button1,0, 0, 2, 1);
        setJButton(button2,0,1,1,1);
        try{
            this.img = ImageIO.read(this.getClass().getResource("/sprites/" + nomImgFond + ".png"));
        }
        catch(FileNotFoundException fnfe){

        }
    }
                
    public JButton getButton1(){
        return button1;
    }
    
    public JButton getButton2(){
        return button2;
    }
    
    public JButton getButton3(){
        return button3;
    }
    
    public JButton getButton4(){
        return button4;
    }
    
    public JComboBox getCombo1(){
        return combo1;
    }
    
    /**
     *permit to create new JButton and set some of his attributs
     * @param NAME
     * @param width
     * @param height
     */
    public JButton createJButton(String NAME, int width, int height){
        JButton button = new JButton(NAME);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(color1);
        button.setFocusable(false);
        button.setRolloverEnabled(false);
        button.setBorder(new LineBorder(Color.WHITE));
        return button;
    }
    
    /**
     *for JButton permit to charge an icon
     * @param button
     * @param imgString
     * @throws IOException
     */
    public void setJButtonIcon(JButton button,String imgString) throws IOException{
        try {
            Image icon = ImageIO.read(this.getClass().getResource("/sprites/" + imgString + ".png"));
            button.setIcon(new ImageIcon(icon));
        } 
        catch (IOException ex) {}
    }
    
    /**
     *permit to create new JComboBox and set some of his attributs
     * @param tab
     * @param width
     * @param height
     */
    public JComboBox createJComboBox(String[] tab, int width, int height){
        JComboBox box = new JComboBox<String>(tab);
        box.setPreferredSize(new Dimension(width, height));
        box.setFont(font);
        box.setForeground(Color.white);
        box.setBackground(color2);
        box.setFocusable(false);
        box.setBorder(new LineBorder(Color.WHITE));
        return box;
    }
    
    /**
     *permit to set a button in the graphical environment
     * @param button
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setJButton(JButton button, int x, int y, int width, int height){
        pos.gridx = x;
        pos.gridwidth = width;
        pos.gridheight = height;
        pos.gridy = y;
        add(button,pos);
    }
    
    public void setJComboBox(JComboBox button, int x, int y, int width, int height){
        pos.gridx = x;
        pos.gridwidth = width;
        pos.gridheight = height;
        pos.gridy = y;
        add(button,pos);
    }
    
    /**
     *permit to set the winner's name
     * @param name
     */
    public void setWinner(String name){
        label = new JLabel(name+" won the Game!");
        Font font = new Font("Bitstream Vera Sans Mono",Font.PLAIN,28);
        label.setFont(font);
        label.setBackground(Color.darkGray);
        label.setForeground(Color.WHITE);
        label.setLayout(new GridBagLayout());
        GridBagConstraints pos = new GridBagConstraints();
        
        //case de depart du composant button1
        pos.gridx=0;
        pos.gridy=4;
        this.add(label,pos);
    }
    
    public void resetLabel(){
        try{
            this.remove(label);
        }
        catch(NullPointerException npe){}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, frame.getWidth(), frame.getHeight(), null);
    }
}
