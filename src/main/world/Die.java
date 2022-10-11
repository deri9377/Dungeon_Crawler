package main.world;
import java.util.Random;

/**
 * One of my favorite classes in this entire project. Myself and henry came up with the idea of making a die class
 * as it would make rolling die a much simpler process and a great example of encapsulation.
 */
public class Die {

    // Need at least the fight bonus and search bonus for the Adventurers and they default to 0 for Test.creatures
    private Random rand;
    private int dieSize;
    private int fightBonus;
    private int searchBonus;
    private int level = 0;

    // Need the default constructor for Enemies
    public Die() {
        dieSize = 6;
        fightBonus = 0;
        searchBonus = 0;
        rand = new Random();
    }

    // Need the paramaterized constructor for different Adventurer classes
    public Die(int fightBonus, int searchBonus) {
        dieSize = 6;
        this.fightBonus = fightBonus;
        this.searchBonus = searchBonus;
        rand = new Random();
    }


    // Another constructor that allows for future use if different sized die were to be used
    public Die(int dieSize) {
        this.dieSize = dieSize;
        rand = new Random();
    }

    // Calculates the fight total roll based on fight bonus
    public int fightDie() {
        return rand.nextInt(dieSize) + 1 + rand.nextInt(dieSize) + 1 + fightBonus;
    }

    // Calculates the search total roll based on search bonus
    public int searchDie() {
        return rand.nextInt(dieSize) + 1 + rand.nextInt(dieSize) + 1 + searchBonus;
    }

    public void setLevel(int level) {
        this.level = level;
    }




}
