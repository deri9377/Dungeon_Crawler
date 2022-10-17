package main.search;

import main.adventurers.Adventurer;
import main.world.Room;
import main.world.object.Treasure;

import java.util.ArrayList;
import java.util.Random;

/**
 * This Class has the search logic for Careful search
 */
public class Careful extends SearchMethod{

    private final int rollThreshold = 4;

    @Override
    public void search(Adventurer adventurer, Room room) {
        ArrayList<Treasure> treasures = room.getTreasure();
        int roll = adventurer.getDie().searchDie();
        if (roll >= rollThreshold) {                                    //Need to roll above 7
            for (int i = 0; i < treasures.size(); i++) {
                Treasure t = treasures.get(i);
                if (t.getName().equals("trap")) {           // Takes damage from a found trap
                    if (new Random().nextInt() == 1) {
                        adventurer.addTreasure(t);
                        adventurer.o.treasureFound(adventurer, treasures.get(i).getName());
                    }
                } else {
                    adventurer.addTreasure(t);
                    adventurer.o.treasureFound(adventurer, treasures.get(i).getName());
                }
                room.remove(t);                             //Remove the treasure from the room so thers only 1 item
            }
        }
    }
}
