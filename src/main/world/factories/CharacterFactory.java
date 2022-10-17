package main.world.factories;

import jdk.jshell.spi.ExecutionControl;
import main.adventurers.*;
import main.world.Room;
import main.world.World;
import main.creatures.*;

import java.util.ArrayList;

public class CharacterFactory {
    public void generateAdventurers(World w) {
        ArrayList<Room> rooms = w.getRooms();
        ArrayList<Adventurer> adv = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getLevel() == 0 && room.getY() == 1 && room.getX() == 1) {
                Brawler b = new Brawler(0, 1, 1);
                Runner r = new Runner(0, 1, 1);
                Sneaker s = new Sneaker(0, 1, 1);
                Thief t = new Thief(0, 1, 1);
                room.addAdventurer(b);
                room.addAdventurer(r);
                room.addAdventurer(s);
                room.addAdventurer(t);
                adv.add(b);
                adv.add(r);
                adv.add(s);
                adv.add(t);
            }
        }
        w.setAdventurers(adv);
    }

    public void generateCreatures(World w) {
        ArrayList<Creature> creat = new ArrayList<>();
        int numLevels = w.getNumLevels();
        int width = w.getWidth();
        int depth = w.getDepth();
        for (int i = 0; i < 4; i++) {
            Blinker b = new Blinker(numLevels, width, depth);
            Seeker s = new Seeker(numLevels, width, depth);
            Orbiter o = new Orbiter(numLevels, width, depth);
            w.addCreature(b);
            w.addCreature(s);
            w.addCreature(o);
            creat.add(b);
            creat.add(s);
            creat.add(o);
        }
        w.setCreatures(creat);
    }

//    For single creations
    public void createAdventurer(String type){
//      TODO: Custom Char Creation
        return;
    }
    public void createCreature(String type){
//      TODO: Custom Creature Creation
        return;
    }
}
