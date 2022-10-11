package main.search;

import main.adventurers.Adventurer;
import main.world.Room;
import main.world.object.Treasure;

import java.util.ArrayList;
import java.util.Random;

public class Quick extends SearchMethod{


    @Override
    public void search(Adventurer adventurer, Room room) {
        ArrayList<Treasure> treasures = room.getTreasure();
        int roll = adventurer.getDie().searchDie();
        if (new Random().nextInt(3) != 1 && roll >= 9) {       //either misses the search or has to roll above a 9
            for (int i = 0; i < treasures.size(); i++) {
                adventurer.addTreasure(treasures.get(i));
                adventurer.o.treasureFound(adventurer, treasures.get(i).getName());
                room.remove(treasures.get(i));          //must remove the item from the room
            }
        }
    }
}
