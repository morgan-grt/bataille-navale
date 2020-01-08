package interfaceGraphic;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *for visual aspect only, create a chargement progress bar, when a player
 * haven't choose is boat
 * create a new thread to change values of the progress bar independently
 * of the main thread for the game
 */
public class GameWaitPanel extends JPanel{
    protected JProgressBar progressBar;
    protected Thread thread;
    protected int barSpeed = 50;
    protected GameGUI frame;
    
    public GameWaitPanel(GameGUI frame){
        this.frame = frame;
        initBar();
    }
    
    public void initBar(){
        JLabel label = new JLabel("Veuillez patienter pendant que votre adversaire choisis...");
        progressBar = new JProgressBar(0, 100); 
        add("Center", progressBar); 
        add("Center", label);
        thread = new Thread(new MyRunnable());
        thread.start();
    }
    
    public class MyRunnable  implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i < 100; i++) {
                progressBar.setValue(i);
                try {
                        thread.sleep(barSpeed);
                }
                catch(Exception e) {
                        e.printStackTrace();
                }
                if (i == 99){
                    i = 0;
                }
            }
        }
    }
}
