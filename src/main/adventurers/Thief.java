package main.adventurers;

import java.util.ArrayList;

import main.fight.Stealth;
import main.search.Careful;
import main.world.Die;

public class Thief extends Adventurer {

    /**
     * Default constructor that assigns a fight bonus of 1 and search bonus of 1
     */
    public Thief(){
        super();
        setDie(new Die(1, 1));
        setName("Thief");
        setFightAttribute(new Stealth());
        setSearchMethod(new Careful());
    }

    /**
     * Constructor that starts the adventurer at a given position
     * @param level: starting level
     * @param y: starting y position
     * @param x: starting x position
     */
    public Thief(int level, int y, int x) {
        super(level, y, x);
        setDie((new Die(1, 1)));
        setName("Thief");
        setFightAttribute(new Stealth());
        setSearchMethod(new Careful());
    }
}
