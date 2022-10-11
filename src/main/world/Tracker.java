package main.world;

import main.adventurers.Adventurer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tracker {
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
//        for (int i = 0; i < treasures.size(); i++) {
//            try {
//                FileWriter fr = new FileWriter(this.file, true);
//                fr.write("Logger: Turn " + turn);
//                fr.write(name + " " + pos[0] + " " + pos[1] + " " + pos[2] + " " + health + " ");
//                if (i != treasures.size() - 1) {
//                    fr.write(" " + treasures.get(i) + ", ");
//                } else {
//                    fr.write(treasures.get(i));
//                }
//                fr.close();
//            } catch (IOException e) {
//                System.out.println("Could not log adveturer statistics");
//            }
//        }
    }
}
