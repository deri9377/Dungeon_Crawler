package main.creatures;
import main.adventurers.Adventurer;
import main.world.Die;
import main.world.World;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * This class is similar to the Adventurer class and has many similar methods. We though about making a super class that
 * Creature and Adventure would instantiate but we found enough differences that we wanted to keep them seperate.
 */
public abstract class Creature {
    /*
        The Creatures have similar attributes to Adventurer apart from the fact that they have acess to the size of the
        World inharently. This is becuase some of the move opperations are a bit more broad than the Adventurer. The
        isAlive attribute is also crutial so that Adventurers dont try to fight a creature that is no longer alive
     */
    private int numLevels;
    private int width;
    private int depth;
    private int[] pos = new int[3];
    private Die die = new Die();
    private boolean isAlive;

    private String name;

    /*
        Basic constructer that is paramaterized. Some of the Creatures need to know the size of the Test.world in order to
        be initialized to a random room that is valid in the Test.world.
     */
    public Creature(int numLevels, int width, int depth) {
        this.numLevels = numLevels;
        this.width = width;
        this.depth = depth;
        isAlive = true;
    }

    /**
     * The main method of the Creature that will be called every turn of the Test.world. This differs from the Adventurer
     * as they will fight anyone in the room first otherwise they will move to a new room. This method is an example of
     * inheratince. We instantiate the turn method in the parent class and as a benefit all of the subclasses will
     * inherate the method and code which allows us to re-use the code easier
     * @param w
     */
    public void turn(World w) {
        ArrayList<Adventurer> a = w.getRoom(getLevel(), getY(), getX()).getAdventurers();
        if (!a.isEmpty()) {
            for (int i = 0; i < a.size(); i++) { //Loops through every Adventurer
                if (isAlive()) {                 //This is important if the Creature dies while mid-turn
                    a.get(i).getFightAttribute().fight(a.get(i), this);   //Calling in respect to the Adventurer reduces the amount of code
                }
            }
        } else {
            // Executes the same logic as above but does so after moving if the original room was empty. This is because
            // We decided that the Creature should be able to fight or move then fight
            move(w);
            a = w.getRoom(getLevel(), getY(), getX()).getAdventurers();
            if (!a.isEmpty()) {
                for (int i = 0; i < a.size(); i++) {
                    if (isAlive()) {
                        a.get(i).getFightAttribute().fight(a.get(i), this);
                    }
                }
            }
        }


    }
    // N = 0 , S = 1 ; E = 2, W = 3 ; UP = 4, DOWN = 5
    // We force the subclasses to instantiate move because they each move in very different ways
    // Move is a great example of abstraction because we only need to specify the signatures of said function, since we are
    // know that every class will need to move differently, but still move nonetheless
    public abstract void move(World world);

    // A simple die that has the monster roll their default die for fighting
    public int roll() {
        return die.fightDie();
    }

    // The rest of the methods are basic getters and setters for the Creature class
    public int getLevel() {
        return pos[0];
    }

    public void setLevel(int level) {
        pos[0] = level;
    }

    public int getY() {
        return pos[1];
    }

    public void setY(int y) {
        pos[1] = y;
    }

    public int getX() {
        return pos[2];
    }

    public void setX(int x) {
        pos[2] = x;
    }

    public int[] getPos() {
        return pos;
    }

    public int getNumLevels() {
        return numLevels;
    }
    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setName(String n) { name = n;}

    public String getName() { return name; }
}
