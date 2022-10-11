package main.world.object;

public class Armor extends Treasure{

    public final int BONUS = -1;

    public Armor() {
        super();
        setName("armor");
    }

    public Armor(int[] pos) {
        super(pos);
        setName("armor");
    }

    public Armor(int numlevels, int width, int depth) {
        super(numlevels, width, depth);
        setName("armor");
    }

    @Override
    public int combatBonus() {
        return BONUS;
    } // Returns a bonus of -1 to be added to the creatures attack
}
