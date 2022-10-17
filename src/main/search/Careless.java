package main.search;

import main.adventurers.Adventurer;
import main.world.Room;
import main.world.object.Treasure;

import java.util.ArrayList;

/**
 * This class holds the logic for careless
 */
public class Careless extends SearchMethod{

    private final int rollThreshold = 7;
    @Override
    public void search(Adventurer adventurer, Room room) {
        ArrayList<Treasure> treasures = room.getTreasure();
        int roll = adventurer.getDie().searchDie();
        if (roll >= rollThreshold) {                                       //must roll above a 10
            for (int i = 0; i < treasures.size(); i++) {
                adventurer.addTreasure(treasures.get(i));
                adventurer.o.treasureFound(adventurer, treasures.get(i).getName());
                room.remove(treasures.get(i));                  //must remove the single item from the room
            }
        }
    }
}
