package main.world;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.object.Treasure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Logger {
    protected int turn;
    protected File file;

    /**
     * Creates a new file in the logs directory using current path
     * @param turnCount the current turn of the world
     */
    public Logger(int turnCount){
        super();
        this.turn = turnCount;
        String currentPath = System.getProperty("user.dir");
        file = new File(currentPath + "/src/main/world/logs", "Logger-" + turn + ".txt");
        try {
            new FileWriter(file, false).close();
        } catch (IOException e) {
            System.out.println("Bruh moment");
        }
    }

    /**
     * Prints the movement of an adventurer
     * @param a adventurer
     * @param p the position the adventurer moves to
     */
    public void movement(Adventurer a, int[] p) {
        try {
            FileWriter fr = new FileWriter(this.file, true);
            fr.write("[Adventurer]" + a.getName() + " moved into room: " + p[0] + "-" +  p[1] + "-" + p[2] + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("Could not log Adventurer move");
        }

    }

    /**
     * Prints out the movements of creatures
     * @param a the name of the creature
     * @param p the position the creature is moving to
     */
    public void movement(Creature a, int[] p) {
        try {
            FileWriter fr = new FileWriter(this.file, true);
            fr.write("[Creature]" + a.getName() + " moved into room: " + p[0] + "-" +  p[1] + "-" + p[2] + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("Could not log Creature move");
        }
    }

    /**
     * Prints out when treasure is found
     * @param a name of adventurer
     * @param t name of the treasure found
     */
    public void treasureFound(String a, String t){
        try {
            FileWriter fr = new FileWriter(this.file, true);
            fr.write(a + " discovered a " + t + " treasure while searching. " + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("Could not log Creature move");
        }
    }

    /**
     * Prints out if the health has changed
     * @param a the adventurer object
     * @param h the new health of the adventurer
     */
    public void healthChange(Adventurer a, int h) {
        try {
            FileWriter fr = new FileWriter(this.file, true);
            fr.write(a.getName() + "'s health changed to:" + h + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("Could not log health change");
        }

    }

    /**
     * prints who won in combas
     * @param a the name of the adventurer
     * @param c the name of the creature
     * @param advWon bool of if the adventurer won
     */
    public void combat(String a, String c, boolean advWon) {
        try {
            FileWriter fr = new FileWriter(this.file, true);
            if(advWon) {
                fr.write(a + " won against " + c + "\n");
                fr.close();
                defeated(c);
            }
            else {
                fr.write(c + " won against " + a + "\n");
                fr.close();
            }
        } catch (IOException e) {
            System.out.println("Could not log combat");
        }
    }

    /**
     * Prints if anything dies
     * @param a name of the object that got defeted
     */
    public void defeated(String a){
        try {
            FileWriter fr = new FileWriter(this.file, true);
            fr.write(a + " was defeated \n");
            fr.close();
        } catch (IOException e) {
            System.out.println("");
        }
    }
}
