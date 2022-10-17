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
    public void createAdventurer(World w, String type, String name){
//      TODO: Custom Char Creation
        ArrayList<Room> rooms = w.getRooms();
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getLevel() == 0 && room.getY() == 1 && room.getX() == 1) {
                if(type == "thief"){
                    Thief a = new Thief(0, 1, 1);
                    a.setPlayerName(name);
                    room.addAdventurer(a);
                }
                if(type == "brawler"){
                    Brawler a = new Brawler(0, 1, 1);
                    a.setPlayerName(name);
                    room.addAdventurer(a);
                }
                if(type == "runner"){
                    Runner a = new Runner(0, 1, 1);
                    a.setPlayerName(name);
                    room.addAdventurer(a);
                }
                if(type == "sneaker"){
                    Sneaker a = new Sneaker(0, 1, 1);
                    a.setPlayerName(name);
                    room.addAdventurer(a);
                }
                else{
                    System.out.println("FACTORY ERROR: Invalid Character Type Selected");
                }
            }
        }
        return;
    }
    public void createCreature(World w, String type){
//      TODO: Custom Creature Creation
        int numLevels = w.getNumLevels();
        int width = w.getWidth();
        int depth = w.getDepth();
        if(type == "Blinker"){
            Blinker c = new Blinker(numLevels, width, depth);
            w.addCreature(c);
//            w.getCreatures(getCreatures().add(c));
//            w.setCreatures((w.getCreatures()).add(c));
//            w.setCreatures(.getCreatures().add(c));
        }
        if(type == "Orbiter"){
            Orbiter c = new Orbiter(numLevels, width, depth);
        }
        if(type == "Seeker"){
            Seeker c = new Seeker(numLevels, width, depth);
        }
        return;
    }
}
