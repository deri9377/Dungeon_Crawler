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

    /**
     * The Runner gets to perform 2 turns instead of just 1 like all the other Test.creatures
     * @param world We needed to pass an instance of the Test.world in so that the character could have access to the rooms
     *             that it moved to. In order to determine to fight or to search for treasure
     */
    @Override
    public void turn(World world){
        if (!isAlive()) {
            return;
        }
        // Perform the first turn just like the other characters which involves moving first and then either fighting and looting
        move(world);
        world.getObserver().move_event(this, this.getPos());
        ArrayList<Creature> creatures = world.getRoom(getLevel(), getY(), getX()).getCreatures();
        if(!creatures.isEmpty()){
            for (int i = 0; i < creatures.size(); i++) {
                getFightAttribute().fight(this, creatures.get(i));
            }
        } else {
            getSearchMethod().search(this,world.getRoom(getLevel(), getY(), getX()));
        }
        // The exact same processis then repeated so that the Runner gets moved 2 times
        move(world);
        creatures = world.getRoom(getLevel(), getY(), getX()).getCreatures();
        if(!creatures.isEmpty()){
            for (int i = 0; i < creatures.size(); i++) {
                getFightAttribute().fight(this, creatures.get(i));
            }
        } else {
            getSearchMethod().search(this, world.getRoom(getLevel(), getY(), getX()));
        }
    }

}
