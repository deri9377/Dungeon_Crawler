package main.world.object;

import java.util.Random;

/**
 * Parent class of all treasure objects
 * Holds the position if its obtain or on the floor
 * has information on if it has bonuses for certain things
 */
public abstract class Treasure {

    private int[] pos;
    private String name;
    private boolean obtained = false;

    public Treasure() {
        pos = new int[]{0, 0, 0};
    }
    public Treasure(int[] pos) {
        this.pos = pos;
    }
    public Treasure(int numlevels, int width, int depth) {
        Random rand = new Random();
        int level = rand.nextInt(numlevels) + 1;
        int x = rand.nextInt(width);
        int y = rand.nextInt(depth);
        pos = new int[]{numlevels, width, depth};
    }

    public int combatBonus() {
        return 0;
    } //by default is 0 subclasses can override

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public boolean isObtained() {
        return obtained;
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int healthBonus(){ return 0;}

}
