package main.world.object;

public class Trap extends Treasure{

    public final int BONUS = -1;
    public Trap() {
        super();
        setName("trap");
    }

    public Trap(int[] pos) {
        super(pos);
        setName("trap");

    }

    public int healthBonus() {return BONUS;}
}
