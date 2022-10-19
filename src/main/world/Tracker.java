package main.world;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.object.Treasure;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public final class Tracker {

    // Reference for eager and lazy: https://betterprogramming.pub/what-is-a-singleton-2dc38ca08e92

    private static Tracker instance = new Tracker(); //Eager instatiation
    public void setTurn(int turn) {
        this.turn = turn;
    }

    private int turn = 0;

    public void setAdventurers(ArrayList<String> adventurers) {
        this.adventurers = adventurers;
    }

    private ArrayList<String> adventurers;

    public void setAdventurerPositions(ArrayList<int[]> adventurerPositions) {
        this.adventurerPositions = adventurerPositions;
    }

    public void setAdventurerHealths(ArrayList<Integer> adventurerHealths) {
        this.adventurerHealths = adventurerHealths;
    }

    public void setCreatures(ArrayList<String> creatures) {
        this.creatures = creatures;
    }

    public void setCreaturePositions(ArrayList<int[]> creaturePositions) {
        this.creaturePositions = creaturePositions;
    }

    private ArrayList<int[]> adventurerPositions;
    private ArrayList<Integer> adventurerHealths;

    public void setTreasures(ArrayList<ArrayList<String>> treasures) {
        this.treasures = treasures;
    }

    private ArrayList<ArrayList<String>> treasures;
    private ArrayList<String> creatures;
    private ArrayList<int[]> creaturePositions;

    private int SPACING = 15;

    public Tracker(){
        super();

    }

    public static Tracker getInstance() {
        return instance;
    }

    public void update(World w){
        ArrayList<Adventurer> a = w.getAdventurers();
        ArrayList<String> aNames = new ArrayList<>();
        ArrayList<Integer> hp = new ArrayList<>();
        ArrayList<int[]> aPos = new ArrayList<>();

        ArrayList<ArrayList<String>> treasures = new ArrayList<>();

        ArrayList<Creature> c = w.getCreatures();
        ArrayList<String> cNames = new ArrayList<>();
        ArrayList<int[]> cPos = new ArrayList<>();

        for(int i = 0; i < a.size(); i++){
            if(a.get(i).getHealth() > 0) {
                aNames.add(a.get(i).getName());
                hp.add(a.get(i).getHealth());
                aPos.add(a.get(i).getPos());
                Hashtable<String, Treasure> tempTreasure = a.get(i).getTreasure();
                ArrayList<String> advTreasure = new ArrayList<>();
                for (String key : tempTreasure.keySet()) {
                    if (tempTreasure.get(key).isObtained()) {
                        advTreasure.add(key);
                    }
                }
                if(advTreasure.size() < 1) {
                    advTreasure.add("");
                }
                treasures.add(advTreasure);
            }
        }
        for(int i = 0; i < c.size(); i++){
            if(c.get(i).isAlive()) {
                cNames.add(c.get(i).getName());
                cPos.add(c.get(i).getPos());
            }

        }
        setTurn(w.getTurn());
        setAdventurers(aNames);
        setAdventurerHealths(hp);
        setAdventurerPositions(aPos);
        setTreasures(treasures);
        setCreatures(cNames);
        setCreaturePositions(cPos);
        log();
    }

    /**
     * Logs out all of the important tracker update data
     * prints all the adventurers first followed by the creatures
     */
    public void log() {
        System.out.println("\n=====TRACKER UPDATE=====");
//        System.out.println(treasures);
        System.out.println("Tracker: Turn "  + turn);
        System.out.println("Total Active Adventurers: " + adventurers.size());
        System.out.printf("%-"+ SPACING + "s%-" + SPACING + "s%-" + SPACING + "s%-" + SPACING + "s\n" , "Adventurers", "Room", "Health", "Treasure");
        for (int i = 0; i < adventurers.size(); i++){
            int x = adventurerPositions.get(i)[0];
            int y = adventurerPositions.get(i)[1];
            int z = adventurerPositions.get(i)[2];
            System.out.printf("%-"+ SPACING + "s%-" + SPACING + "s%-" + SPACING + "s%-" + SPACING + "s\n" , adventurers.get(i) , x + "-" + y + "-" + z  , adventurerHealths.get(i), treasures.get(i));
        }
        System.out.println("Total Active Creatures: " + creatures.size());
        System.out.printf("%-" + SPACING + "s%-" + SPACING + "s\n" , "Creatures", "Room");
        for (int i = 0; i < creatures.size(); i++){
            int x = creaturePositions.get(i)[0];
            int y = creaturePositions.get(i)[1];
            int z = creaturePositions.get(i)[2];
            System.out.printf("%-"+ SPACING + "s%-" + SPACING + "s\n" , creatures.get(i) , x + "-" + y + "-" + z);
        }
    }
}
