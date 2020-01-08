package controler;

import game.Coordinate;
import game.Coordinate.Direction;
import static game.Coordinate.Direction.*;
import gameElement.Boat;
import gameElement.Sea;
import interfaceGraphic.GameGUI;
import interfaceGraphic.GameSelectBoatPanel;
import interfaceGraphic.GameSelectBoatPanel.EllipseBoolean;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

/**
 *used to init boat on sea for player
 */
public class InitBoat extends Observable{
    protected List<Boat> boatList = new ArrayList<>();
    protected final int[][] lengthTab = 
    {{5,4,3,3,2},{5,4,4,2,2},{5,5,3,2,2}};
    protected int selectedTab = 0;
    private Random random = new Random();
    private Sea sea;
    protected boolean allPlaced = false;
    private final String[] selectionBoat = 
            new String[]{"Boat 1", "Boat 2", "Boat 3"};
    protected GameSelectBoatPanel selectPanel;
    
    /**
     *
     * @param frame
     * @param selectPanel
     * @param sea
     */
    public InitBoat(GameGUI frame,GameSelectBoatPanel selectPanel, Sea sea){
        addObserver(frame);
        this.sea = sea;
        this.selectPanel = selectPanel;
    }
    
    public InitBoat(Sea sea){
        this.sea = sea;
    }
    
    /**
     *determine if interface graphic is used
     */
    public void setBoat(){
        if (selectPanel != null){
            setBoatEllipse();
        }
        else{
            setBoatScanner();
        }
    }
    
    /**
     *for HPlayer, set boat in sea if interface graphic is used
     */
    public void setBoatEllipse(){
        List<EllipseBoolean> list = selectPanel.getEllipseList();
        if (!boatList.isEmpty()){
            boatList.clear();
        }
        for (EllipseBoolean ellipse : list){
            int x = (int) (ellipse.getEllipse().getX() + 16) / 32 + 1;
            int y = (int) (ellipse.getEllipse().getY() + 16) / 32 + 1;
            int length = Math.max((int) ellipse.getEllipse().getWidth() / 32
                    ,(int) ellipse.getEllipse().getHeight() / 32);
            Direction dir  = VERTICAL;
            if (((int) ellipse.getEllipse().getWidth() / 32) > 1){
                dir = HORIZONTAL;
            }
            Boat boat = new Boat(x,y,length,dir);
            boatList.add(boat);
        }
    }
    
    /**
     *for HPlayer, set boat in sea if interface graphic is not used
     */
    public void setBoatScanner(){
        getBoatList().clear();
        System.out.println("Choisir la longueur de vos bâteaux:\n");
        System.out.println(getStringLenghtTab());
        Scanner scanner = new Scanner(System.in);
        setSelectedTab(scanner.nextInt() - 1);
        if (getSelectedTab() < 3 && getSelectedTab() >= 0){
            System.out.println("Pour chaque bâteau indiquez: \n"
                    + "- la position x\n"
                    + "- la position y\n"
                    + "- la direction :\n"
                    + "   0 pour VERTICAL\n"
                    + "   1 pour HORIZONTAL   (par défaut HORIZONTAL)");
            for (int lenght : getLengthTab()[getSelectedTab()]){
                if (getBoatList().size() != 5){
                    System.out.println("Bâteaux de longueur: "+lenght+"\n");
                    boolean done = false;
                    while(!done){
                        try {
                            int x = scanner.nextInt() - 1;
                            int y = scanner.nextInt() - 1;
                            int intDir = scanner.nextInt();
                            Coordinate.Direction dir = HORIZONTAL;
                            if (intDir == 0){
                                dir = VERTICAL;
                            }
                            Boat boat = new Boat(y, x, lenght, dir);
                            try{
                                if (sea.checkBoatSet(boat)){
                                    getBoatList().add(boat);
                                    done = true;
                                }
                                else{
                                    System.out.println("Valeur impossible, "
                                            + "il y a déjà un bâteau, ressayez");
                                }
                            }
                            catch(ArrayIndexOutOfBoundsException aioobe){
                                System.out.println("Valeur impossible, ressayez");
                            }
                        }
                        catch(InputMismatchException ime){
                            System.out.println("Valeur impossible, ressayez");
                        }
                    }
                }
            }
            if (getBoatList().size() == 5){
                for (Boat boat : getBoatList()){
                    sea.placer(boat, boat.getCoord(), boat.dir);
                    boat.setIsUsed(true);
                }
            }
        }
        else {
            System.out.println("Valeur impossible, ressayez");
            setBoatScanner();
        }
    }
    
    /**
     *for RPlayer, set boat in sea
     */
    public void setBoatRandom(){
        getBoatList().clear();
        int r = random.nextInt(3);
        setSelectedTab(r);
        if (getSelectedTab() < 3 && getSelectedTab() >= 0){
            for (int length : getLengthTab()[getSelectedTab()]){
                int intDir = random.nextInt(2);
                Coordinate.Direction dir = HORIZONTAL;
                if (intDir == 0){
                    dir = VERTICAL;
                }
                boolean done = false;
                while(!done){
                    int x = random.nextInt(10 - (length * dir.getDx()));
                    int y = random.nextInt(10 - (length * dir.getDy()));
                    Boat boat = new Boat(x, y, length, dir);
                    if (sea.checkBoatSet(boat)){
                        sea.placer(boat, boat.getCoord(), boat.dir);
                        boat.setIsUsed(true);
                        getBoatList().add(boat);
                        done = true;
                    }
                }
                
            }
            checkBoatAllPlaced();
        }
        else {
            setBoatRandom();
        }
    }
    
    public GameSelectBoatPanel getSelectPanel(){
        return selectPanel;
    }

    /**
     *check if  all boat in list are placed in the sea
     */
    public boolean checkBoatAllPlaced(){
        if ( boatList.isEmpty()){
            return false;
        }
        else{
            for (Boat boat : boatList){
                if (!boat.getIsUsed()){
                    return false;
                }
            }
        }
        setAllPlaced(true);
        setChanged();
        notifyObservers();
        return true;
    }
    
    public List<Boat> getBoatList(){
        return boatList;
    }
    
    public int[][] getLengthTab(){
        return lengthTab;
    }
    
    /**
     *return the boat and her lenght for the three prefabricated list of boat
     */
    public String getStringLenghtTab(){
        String string = "";
        for (int i = 0; i<lengthTab.length;i++){
            string += "Choix n°"+ (i+1) + System.lineSeparator();
            string += "longeur: ";
            for (int j = 0; j<lengthTab[i].length;j++){
                string += lengthTab[i][j] + " ";
            }
            string += System.lineSeparator();
        }
        return string;
    }
    
    public String[] getSelectionBoat(){
        return selectionBoat;
    }
    
    public int getSelectedTab(){
        return selectedTab;
    }
    
    /**
     *return index for choosing which prefabricated list of boat use
     * @param string
     */
    public int getIndexSelectionBoat(String string){
        int i = 0;
        for (String selectionString : selectionBoat){
            if ( selectionString == string){
                return i;
            }
            i++;
        }
        return 0;
    }
    
    public void setSelectedTab(int n){
        selectedTab = n;
    }
    
    public boolean getAllPlaced(){
        return allPlaced;
    }
    
    public void setAllPlaced(boolean bool){
        allPlaced = bool;
    }
}
