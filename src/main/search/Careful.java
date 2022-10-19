package main.search;

import main.adventurers.Adventurer;
import main.world.Logger;
import main.world.Room;
import main.world.World;
import main.world.object.Treasure;

import java.util.ArrayList;
import java.util.Random;

/**
 * This Class has the search logic for Careful search
 */
public class Careful extends SearchMethod{

    private final int rollThreshold = 4;

    @Override
    public void search(Adventurer adventurer, World w) {
        Room room = w.getRoom(adventurer.getLevel(), adventurer.getY(), adventurer.getX());
        ArrayList<Treasure> treasures = room.getTreasure();
        int roll = adventurer.getDie().searchDie();
        if (roll >= rollThreshold) {                                    //Need to roll above 7
            for (int i = 0; i < treasures.size(); i++) {
                Treasure t = treasures.get(i);
                if (t.getName().equals("trap")) {           // Takes damage from a found trap
                    if (new Random().nextInt() == 1) {
                        adventurer.addTreasure(t);
                        Logger.getLogger().treasureFound(adventurer.getPlayerName(), treasures.get(i).getName());
                    }
                }
                else if(t.getName().equals("portal")){
                        Random rand = new Random();
                        int[] newPos = new int[]{rand.nextInt(1,4), rand.nextInt(1,4), rand.nextInt(1,4)};
                        adventurer.setPos(newPos);
                        room.remove(adventurer);
                        w.getRoom(adventurer.getLevel(), adventurer.getY(), adventurer.getX()).addAdventurer(adventurer);
                }
                else {
                    adventurer.addTreasure(t);
                    Logger.getLogger().treasureFound(adventurer.getPlayerName(), treasures.get(i).getName());
                }
                room.remove(t);                             //Remove the treasure from the room so thers only 1 item
            }
        }
    }
}
