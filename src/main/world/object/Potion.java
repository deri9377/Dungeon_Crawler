package main.world.object;

public class Potion extends Treasure{

    public final int BONUS = 1;

    public Potion() {

        super();
        setName("potion");
    }

    public Potion(int[] pos) {
        super(pos);
        setName("potion");
    }

    public int healthBonus() {
        return BONUS;
    } // gives a
}
