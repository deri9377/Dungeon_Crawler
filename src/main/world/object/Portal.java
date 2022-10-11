package main.world.object;

/**
 * This class teleports someone to a random location
 */
public class Portal extends Treasure{


    public Portal(){
        super();
        setName("portal");
    }
    public Portal(int[] pos) {
        super(pos);
        setName("portal");
    }
}
