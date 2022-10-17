package main.adventurers;

import main.fight.Expert;
import main.search.Careless;
import main.world.Die;

import java.util.ArrayList;

public class Brawler extends Adventurer{

    /**
     * Default constructor that assigns a 2 fight bonus and 0 search bonus
     */
    public Brawler() {
        super();        //instantiate varaibles through super class
        build();
    }

    /**
     * Starts the Brawler at the position given
     * @param level: level to start at
     * @param y: y position to start at
     * @param x: x position to start at
     */
    public Brawler(int level, int y, int x) {
        super(level, y, x);             //instantate variables through super class
        build();
    }

    private void build() {
        setDie(new Die(2, 0));
        setName("Brawler");
        setHealth(12);
        setFightAttribute(new Expert());
        setSearchMethod(new Careless());
    }

}
