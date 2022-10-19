package main.world;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.object.Treasure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class Logger {

    // Reference for Lazy and Eager singleton: https://betterprogramming.pub/what-is-a-singleton-2dc38ca08e92

    private static Logger instance = null; // lazy instantiation
    ArrayList<Adventurer> advMove = new ArrayList<Adventurer>();
    ArrayList<int[]> advPos = new ArrayList<>();

    ArrayList<Creature> creMove = new ArrayList<>();
    ArrayList<int[]> crePos = new ArrayList<>();

    public static int turn;
    protected File file;

    /**
     * Creates a new file in the logs directory using current path
     * @param turnCount the current turn of the world
     */
    public Logger(int turnCount){
        super();
        Logger.turn = turnCount;
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
            fr.write("[Adventurer]" + a.getPlayerName() + " moved into room: " + p[0] + "-" +  p[1] + "-" + p[2] + "\n");
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
            fr.write(a.getPlayerName() + "'s health changed to:" + h + "\n");
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

    public void moveEvent(Adventurer a,int[] p) {
        advMove.add(a);
        advPos.add(p);
        notifySubs(0);
    }
    public void moveEvent(Creature c,int[] p) {
        creMove.add(c);
        crePos.add(p);
        notifySubs(6);
    }

    public void notifySubs(int eventNumber) {
        if(eventNumber == 0){
            movement(advMove.get(advMove.size()-1), advPos.get(advPos.size()-1));
            //Character Moves
        }
        else if(eventNumber == 6){
            //Creature Moves
            movement(creMove.get(creMove.size()-1), crePos.get(crePos.size()-1));
        }
    }

    public static Logger getLogger() {
        return Logger.getLogger(Logger.turn);
    }

    public static Logger getLogger(int turn) {
        if (instance == null) {
            instance = new Logger(turn);
        }
        return instance;
    }

    public static void deleteLogger() {
        instance = null;
    }

    public void notifyCelebration(String name, String celebration) {
        try {
            FileWriter fr = new FileWriter(this.file, true);
            fr.write("[Adventurer]" + name + " celebrated: " + celebration + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("Error Logging Celebration");
        }
//        notifySubs(2);
    }
    public void notifyCombat(Adventurer a, Creature c, boolean advWon){
        combat(a.getPlayerName(), c.getName(), advWon);
    }
    public void notifyHealthChange(Adventurer a, int h) {
        healthChange(a, h);
        if(a.getHealth() < 1){
            defeated(a.getName());
        }
    }
//    public void treasureFound(Adventurer a, String treasure){
//        treasureFound(a.getName(), treasure);
//    }

}
