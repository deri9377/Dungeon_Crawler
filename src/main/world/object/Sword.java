package main.world.object;

public class Sword extends Treasure {

    public final int BONUS = 1;

    public Sword() {
        super();
        setName("sword");
    }

    public Sword(int[] pos) {
        super(pos);
        setName("sword");
    }

    public Sword(int numLevels, int width, int depth) {
        super(numLevels, width, depth);
        setName("sword");
    }

    @Override
    public int combatBonus() {
        return BONUS;
    }
}
