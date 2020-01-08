package gameElement;

import java.util.Observable;
import java.util.Observer;

/**
 *implements observer to observe the sea and actualize when notified
 * used for game in terminal
 */
public class ViewSea implements Observer{
    protected Sea sea;
    
    public ViewSea(Sea sea){
        this.sea = sea;
        this.sea.addObserver(this);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(situationToString());
    }
    
    public String situationToString(){
        String ligne = "   A  B  C  D  E  F  G  H  I  J " 
                + System.lineSeparator() 
                + "  +──+──+──+──+──+──+──+──+──+──+" 
                + System.lineSeparator() + 1 + " |";
        for (int i = 0; i < sea.getGrille().length ; i++){
            for (int j = 0; j < sea.getGrille()[i].length ; j++){
                ligne += (sea.getGrille()[j][i])+"|";
            }
            if(i < sea.getGrille()[i].length-1){
                ligne += System.lineSeparator() 
                        + "  +──+──+──+──+──+──+──+──+──+──+" 
                        + System.lineSeparator();
                if (i+2 != 10){
                    ligne += i+2 + " |";
                }
                else {
                    ligne += i+2 + "|";
                }
                
            }
        }
        return ligne + System.lineSeparator() 
                + "  +──+──+──+──+──+──+──+──+──+──+";
    }
    
    public Sea getSea(){
        return sea;
    }
    
}
