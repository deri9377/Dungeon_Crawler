package main.adventurers;

import main.creatures.Creature;
import main.fight.Untrained;
import main.search.Quick;
import main.world.Die;
import main.world.Room;
import main.world.World;

import java.util.ArrayList;

public class Runner extends Adventurer{

    /**
     * Instantiates a Runner at the specified location
     * @param level starting level
     * @param y: starting y position
     * @param x: starting x positon
     */
    public Runner(int level, int y, int x) {
        super(level, y, x);         // instantiate defaults through super class
        setName("Runner");
        setHealth(10);
        setFightAttribute(new Untrained());
        setSearchMethod(new Quick());
    }

}
