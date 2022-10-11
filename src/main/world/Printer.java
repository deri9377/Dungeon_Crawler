package main.world;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.object.Treasure;

import java.util.ArrayList;
import java.util.Hashtable;

public class Printer {
        private ArrayList<Adventurer> a;
    /**
     *  These privates hold references for the Test.adventurers and Test.creatures to print their statuses each turn
     */
        private ArrayList<Creature> c;
        private int SPACING = 15;

        private ArrayList<Integer> counts = new ArrayList<>();
        // Counts for counting the endings in the MultipleRunGame.txt output
        //These are not used in typical cases.
        public void setCounts(int i){
        counts.set(i, counts.get(i) + 1);
    }
        public void printCounts(){
            System.out.println("All Test.adventurers dead: " + counts.get(0));
            System.out.println("All Test.creatures dead: " + counts.get(1));
            System.out.println("Wins by treasure: " + counts.get(2));
        }

    /**
     * Prints the entire map with all current positions, health values, treasure values, and monster counts.
     * This class is a great example of a strong cohesion class because it has a very clear and defined purpose and does just
     * that. It does not go above and beyond to access things it should not or change anything, it simply accepts data and
     * processes what was passed to it, exactly how it is defined to do so in the code.
     * @param world: current game Test.world
     *
     */
        public void printer(World world){
//            First section prints the grid of the entire map
            System.out.println(" ");
            System.out.println("=====================================================");
            System.out.println("RotLA Turn " + world.getTurn() + ": ");
            for(int i = 0; i < world.getNumLevels()+1; i++){
                for(int j = 0; j < world.getDepth(); j++){
                    ArrayList<String> lines = new ArrayList<>();
                    for(int k = 0; k < world.getWidth(); k++) {
                        String line = new String();
                        if (i > 0 || (j == 1 && k == 1)) {
                            line += (i + "-" + j + "-" + k + ":");
                            Room room = world.getRoom(i, j, k);
                            ArrayList<Creature> creatures = room.getCreatures();
                            ArrayList<Adventurer> adventurers = room.getAdventurers();
                            boolean isAdventurer = false;
                            boolean isCreature = false;
                            for (int z = 0; z < adventurers.size(); z++) { //Checking to only print living player positions
                                if (adventurers.get(z).isAlive()) {
                                    isAdventurer = true;
                                    line += ("" + (adventurers.get(z).getName().charAt(0)));
                                }
                            }
                            if(!isAdventurer){
                                line += ("-");
                            }
                            line += ("" + ":");
                            for (int z = 0; z < creatures.size(); z++) { //Only printing living creature positions
                                Creature c = creatures.get(z);
                                if (c.isAlive()) {
                                    isCreature = true;
                                    line += (creatures.get(z).getName().charAt(0));
                                }
                            }
                            if(!isCreature){
                                line += ("-");
                            }

                        }
                        lines.add(line);
                    }
                    if(i == 0 && j == 1){ //Prints the starting floor room only
                        System.out.println(lines.get(1));
                    }
                    else if(i == 0){

                    }
                    else {
                        System.out.printf("%-"+ SPACING + "s%-" + SPACING + "s%-" + SPACING + "s\n", lines.get(0), lines.get(1), lines.get(2));
                    }
                }}
            // The below function is a good example of how important object identity is. Knowing that each of these characters exists
            // uniquely allows us to access their public methods by reference and know that the data will be live and accurrate
            // because each of them have a unique identity.
            System.out.print("\n");
            for (int advInd = 0; advInd < a.size(); advInd++){ //Prints the status of each adventurer
                Adventurer tempAdventurer = a.get(advInd);
                String living = "";
                if(!tempAdventurer.isAlive()){
                    living = "(ELIMINATED)";
                }
                System.out.print(tempAdventurer.getName() + " - Treasure: ");
                Hashtable<String, Treasure> tempTreasure = tempAdventurer.getTreasure();
                for (String key : tempTreasure.keySet()) {
                    if (tempTreasure.get(key).isObtained()) {
                        System.out.print(key + " ");
                    }
                }
                System.out.println(" - HP: " + tempAdventurer.getHealth() + living);
            }
            //Printing creature counts
//            ArrayList<Integer> counts = creatureCounts();
//            System.out.print("\n");
//            System.out.println("Orbiters - " + counts.get(0) + " remaining");
//            System.out.println("Seekers - " + counts.get(1) + " remaining");
//            System.out.println("Blinkers - " + counts.get(2) + " remaining");
        }
        public void setAdventurer(ArrayList<Adventurer> aa){ a = aa;}

        public void setCreature(ArrayList<Creature> cc) {c = cc;}

        //Counts all the monsters currently alive via their reference in isAlive()
        private ArrayList<Integer> creatureCounts(){
            ArrayList<Integer> counts = new ArrayList<>();
            counts.add(0);
            counts.add(0);
            counts.add(0);
            for (int i = 0; i < c.size(); i++){
                    if(c.get(i).isAlive()) {
                        String n = c.get(i).getName();
                        if (n == "O") { //Counting Orbiters
                            counts.set(0, counts.get(0) + 1);
                        }
                        if (n == "S") { //Counting Seekers
                            counts.set(1, counts.get(1) + 1);
                        }
                        if (n == "B") { //Counting Blinkers
                            counts.set(2, counts.get(2) + 1);
                        }
                    }
            }
            return counts;
        }
}
