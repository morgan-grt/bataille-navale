/*
 * affichage du menu
 */
package menu;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

/**
 *main menu when launched in graphical environment
 */
public class PrincipalMenu extends AbstractMenu {
    
    public PrincipalMenu(JFrame frame) throws IOException{
        super(frame,"PLAY","QUIT","principal");
        button2.setPreferredSize(new Dimension(160, 96));
        button3 = createJButton("2 PLAYER",224,96);
        button3.addActionListener((ActionListener) frame);
        setJButton(button3,1,1,1,1);
    }
}
