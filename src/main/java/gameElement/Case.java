package gameElement;

/**
 *representation of the sea case per case
 * can have a boat, a missile
 * and set the boat touched, flowed
 */
public class Case {
    protected Boat boat;
    public Missile missile;
    public Sea sea;
    public boolean touched = false, flowed = false;
    
    public Case(Boat boat, Missile missile, Sea sea){
        this.boat = boat;
        this.missile = missile;
        this.sea = sea;
    }
    
    public Case(Sea sea){
        this.sea = sea;
    }
    
    /**
     *permit to set a boat on a case
     * same for the missile
     * @param boat
     */
    public void placer(Boat boat){
        this.boat = boat;
    }
    
    public void placer(Missile missile){
        this.missile = missile;
    }
    
    public void update(){
        if (boat != null){
            if (boat.flowed){
                flowed = true;
            }
        }
    }
    
    public Boat getBoat(){
        return boat;
    }
    
    @Override
    public String toString(){
        if (boat != null){
            if (flowed){
                return " "+'X';
            }
            else if (touched){
                return " "+'/';
            }
            else{
                return " "+boat.toString();
            }
        }
        else if (missile != null){
            return " "+missile.toString();
        }
        return "  ";
    }
}
