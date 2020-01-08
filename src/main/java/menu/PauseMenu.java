package menu;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

/**
 *menu when "p" key is pushed
 */
public class PauseMenu extends AbstractMenu {
    public PauseMenu(JFrame frame) throws IOException {
        super(frame,"RESUME","QUIT","pause");
        button2.setPreferredSize(new Dimension(160, 96));
        button3 = createJButton("MENU",160,96);
        setJButtonIcon(button3,"menu_icon");
        button3.addActionListener((ActionListener) frame);
        setJButton(button3,1,1,1,1);
    }
}
