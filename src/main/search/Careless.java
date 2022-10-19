package main.search;

import main.adventurers.Adventurer;
import main.world.Logger;
import main.world.Room;
import main.world.World;
import main.world.object.Treasure;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class holds the logic for careless
 */
public class Careless extends SearchMethod{

    private final int rollThreshold = 7;
    @Override
    public void search(Adventurer adventurer, World w) {
        Room room = w.getRoom(adventurer.getLevel(), adventurer.getY(), adventurer.getX());
        ArrayList<Treasure> treasures = room.getTreasure();
        int roll = adventurer.getDie().searchDie();
        if (roll >= rollThreshold) {                                       //must roll above a 10
            for (int i = 0; i < treasures.size(); i++) {
                Treasure t = treasures.get(i);
                if(t.getName().equals("portal")){
                    Random rand = new Random();
                    int[] newPos = new int[]{rand.nextInt(1,4), rand.nextInt(1,4), rand.nextInt(1,4)};
                    adventurer.setPos(newPos);
                    room.remove(adventurer);
                    w.getRoom(adventurer.getLevel(), adventurer.getY(), adventurer.getX()).addAdventurer(adventurer);
                } else {
                    adventurer.addTreasure(treasures.get(i));
                    Logger.getLogger().treasureFound(adventurer.getPlayerName(), treasures.get(i).getName());
                    room.remove(treasures.get(i));                  //must remove the single item from the room
                }
            }
        }
    }
}
