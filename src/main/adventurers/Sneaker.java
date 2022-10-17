package main.adventurers;

import main.creatures.Creature;
import main.fight.Stealth;
import main.search.Quick;

import java.util.ArrayList;
import java.util.Random;

public class Sneaker extends Adventurer{

    /**
     * Starts the Sneaker at the given position
     * @param level: start level
     * @param y: start y position
     * @param x: start x position
     */
    public Sneaker(int level, int y, int x) {
        super(level, y, x);     // Initializes default variables through super
        setName("Sneaker");
        setHealth(8);
        setFightAttribute(new Stealth());
        setSearchMethod(new Quick());
    }

}
