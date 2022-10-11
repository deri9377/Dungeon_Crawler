package main.creatures;

import main.world.World;

import java.util.ArrayList;
import java.util.Random;

public class Blinker extends Creature{

    /**
     * This calls the super constructor in order to get all of the attributes instantiated and then also calculates the
     * random position that it will start in
     * @param numLevels: the number of floors in the Test.world
     * @param width: the width of rooms in the x axis
     * @param depth: the depth of rooms in the y axis
     */
    public Blinker(int numLevels, int width, int depth) {
        super(numLevels, width, depth);
        setLevel(4);                        // Blinkers always start on the 4th floor
        setY(new Random().nextInt(width)); // start on a random room in the 4th floor
        setX(new Random().nextInt(depth));
        setName("Blinker");
    }

    /**
     * The Blinker moves randomly to any valid room within the Test.world. This method is a great example of polymorphism.
     * All of the Creature subclasses instantiate the move method in their own way but in the eyes of the Test.world all the
     * Test.world has to do is ask each creature to move and they will figure out how to do that on their own.
     * @param w: instance of the Test.world when the Creature attempts to move
     */
    @Override
    public void move(World w) {
        w.getRoom(getLevel(), getY(), getX()).remove(this); // Remove from room so the blinkers dont duplicate on move
        Random rand = new Random();
        // need to add 1 to the random level so that level 0 is never chosen
        int tempLevel = rand.nextInt(getNumLevels()) + 1;
        // choose any random level within the width, depth bounds
        int tempX = rand.nextInt(getWidth());
        int tempY = rand.nextInt(getDepth());
        // Need to update its own position so that it knows where it is and other objects can use it
        setLevel(tempLevel);
        setY(tempY);
        setX(tempX);
        w.getRoom(getLevel(), getY(), getX()).addCreature(this);
        w.observer.move_event(this, this.getPos());
    }
}
