package main.creatures;

import main.adventurers.Adventurer;
import main.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Seeker extends Creature {

    /**
     * The seeker starts at a random position within the Test.world
     * @param numLevels: number of floors in the Test.world
     * @param width: number of rooms in the x axis
     * @param depth: number of rooms in the y axis
     */
    public Seeker(int numLevels, int width, int depth) {
        super(numLevels, width, depth);             // Need to call super to instantiate variables
        Random rand = new Random();
        setLevel(rand.nextInt(numLevels) + 1);      // Need to add 1 so that 0 is never chosen
        setX(rand.nextInt(width));
        setY(rand.nextInt(depth));
        setName("Seeker");
    }

    /**
     * The Seeker looks to see if there are any Adventurers in the rooms connected to the one the seeker is in and
     * will move to go attack any Adventurer in the other rooms
     * @param world: An instance of the Test.world when the move is called
     */
    @Override
    public void move(World world) {
        world.getRoom(getLevel(), getY(), getX()).remove(this); // Need to remove from current room so that the seeker doesnt duplicate
        ArrayList<int[]> possiblePositions = new ArrayList<>();    // Need to track all of the possible position to move to for later
        for (int i = 0; i < world.getRooms().size(); i++) {
            if (!world.getRooms().get(i).getAdventurers().isEmpty()) {
                int[] position = world.getRooms().get(i).getPos();
                int tempLevel = position[0];
                int tempX = position[2];
                int tempY = position[1];
                // Ensure that only the 4 neighboring rooms are allowed to be added if they have Adventurers in them
                if (tempLevel == getLevel() && (tempX == getX() - 1 || tempX == getX() + 1) && tempY == getY() || ((tempY == getY() - 1 || tempY == getY() + 1) && tempX == getX())) {
                    possiblePositions.add(position);
                }
            }
        }
        // Ensure that the Seeker does not move if there are no possible moves available
        if (possiblePositions.isEmpty()) {
            world.getRoom(getLevel(), getY(), getX()).addCreature(this);
            return;
        }
        // Select a random move out of the possible ones as there is no current AI
        int[] selectedMove = possiblePositions.get(new Random().nextInt(possiblePositions.size()));
        setLevel(selectedMove[0]);
        setY(selectedMove[1]);
        setX(selectedMove[2]);
        world.getRoom(getLevel(), getY(), getX()).addCreature(this); // Add this to new Room so that the Test.world can see the update
        world.observer.move_event(this, this.getPos());
    }
}
