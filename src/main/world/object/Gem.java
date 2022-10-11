package main.world.object;

public class Gem extends Treasure{

    public final int BONUS = 1;

    public Gem() {
        super();
        setName("gem");
    }

    public Gem(int[] pos) {
        super(pos);
        setName("gem");
    }

    public int combatBonus() {
        return BONUS;
    } // Gem gives the creature +1 combat bonus
}
