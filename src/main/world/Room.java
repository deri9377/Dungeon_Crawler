package main.world;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.object.Treasure;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This entire class is basically a wrapper for the Test.world to organize the list of Test.adventurers and Test.creatures
 * We did this to keep the World class a lot more clean looking and to allow characters to see what other
 * Adventurers and Test.creatures are in the specific room that they are also in
 */

public class Room {

    private int[] pos;
    private ArrayList<Adventurer> adventurers = new ArrayList<>();
    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<Treasure> treasures = new ArrayList<>();

    public Room(int[] pos) {
        this.pos = pos;
    }

    public void addAdventurer(Adventurer a) {
        adventurers.add(a);
    }

    public void remove(Adventurer a) {
        adventurers.remove(a);
    }

    public void addCreature(Creature c) {creatures.add(c);}

    public void remove(Creature c) {creatures.remove(c);}

    public void addTreasure(Treasure t) {treasures.add(t);}

    public void remove(Treasure t){treasures.remove(t);}


    public ArrayList<Adventurer> getAdventurers() {
        return adventurers;
    }

    public void setAdventurers(ArrayList<Adventurer> adventurers) {
        this.adventurers = adventurers;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(ArrayList<Creature> creatures) {
        this.creatures = creatures;
    }

    public int getLevel() {
        return pos[0];
    }

    public void setLevel(int level) {
        pos[0] = level;
    }

    public int getY() {
        return pos[1];
    }

    public void setY(int y) {
        pos[1] = y;
    }

    public int getX() {
        return pos[2];
    }

    public void setX(int x) {
        pos[2] = x;
    }

    public int[] getPos() {
        return pos;
    }

    public ArrayList<Treasure> getTreasure() {
        return treasures;
    }
}
