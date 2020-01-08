package menu;
import java.io.IOException;
import javax.swing.*;

/**
 *menu when a player won the game
 */
public class VictoryMenu extends AbstractMenu{
    public VictoryMenu(JFrame frame) throws IOException {
        super(frame,"MENU","QUIT","victory");
        setJButtonIcon(button1,"menu_icon");
    }
}
