package main.world;

import main.adventurers.*;
import main.creatures.Blinker;
import main.creatures.Creature;
import main.creatures.Orbiter;
import main.creatures.Seeker;
import main.world.object.*;
import main.world.Observer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class World {

    /**
     * The Test.world class builds a game Test.world as well as populates it with the correct Test.creatures and Test.adventurers. In addition to this
     * it handles the progression of the game as well as the termination cases for the game (all Test.adventurers dead, treasure threshold, all Test.creatures dead)
     * //    TURN Order
     * //    1. All Test.adventurers attempt move
     * //    2. Adventurers Rooms positions update
     * //    3. All Test.adventurers act (fight if in room with monsters, loot for treasure if now in empty room)
     * //    (Optional) 4. If runners alive, go back to 1. with just runner and begin.
     * //    5. Monsters Turn (Fight/Move)
     */

    private ArrayList<Room> rooms;
    public static final int TREASURE_THRESHOLD = 10; //treasure search
    public static final int MAX_TREASURE = 10;          //Win condition: all characters collect 10 treasure as a sum
    private final int MAX_TURNS = 100;
    private int numLevels;
    private int width;
    private int depth;
    private int turn;

    private ArrayList<Adventurer> adventurers;
    private ArrayList<Creature> creatures;

    private Printer printer = new Printer();

    public Observer observer;

    public World() {
        numLevels = 4;
        width = 3;
        depth = 3;
        rooms = new ArrayList<>();
        generateWorld();
        observer = new Observer();
    }

    public World(int numLevels, int width, int depth) {
        this.numLevels = numLevels;
        this.width = width;
        this.depth = depth;
        rooms = new ArrayList<>();
        generateWorld();
        observer = new Observer();
    }

    public Observer getObserver() {
        return observer;
    }

    /**
     * runGame() will begin the simulation and run turns over and over continuously until a termination condition (win/lose)
     * is met. It utilizes methods from instances of Test.creatures, Test.adventurers, room,  and the printer class to achieve this.
     * At the end of each turn iteration, this function calls runTurns to perform each characters moves as well as the printer.
     */
    private int countTreasure(ArrayList<Adventurer> a){
        int t = 0;
        for(int i = 0; i < a.size(); i++) {
            Hashtable<String, Treasure> tempTreasure = a.get(i).getTreasure();
            for (String key : tempTreasure.keySet()) {
                if (tempTreasure.get(key).isObtained()) {
                    t += 1;
                }
            }
        }
        return t;
    }
    public void runGame() {
        setTurn(0);
        while (true) {
            Logger logger = observer.createLogger(getTurn());
            // Start of turn checking win/lose conditions
            boolean aAlive = false;
            boolean cAlive = false;
            int treasure = 0;
            for (int i = 0; i < rooms.size(); i++) {
                treasure += countTreasure(adventurers);
                ArrayList<Adventurer> a = rooms.get(i).getAdventurers();
                ArrayList<Creature> c = rooms.get(i).getCreatures();
                for (int j = 0; j < a.size(); j++) {
                    if (a.get(j).isAlive()) {
                        aAlive = true;
                    }
                }
                for (int j = 0; j < c.size(); j++) {
                    if (c.get(j).isAlive()) {
                        cAlive = true;
                    }
                }
            }
            if (!aAlive) {
                printer.printer(this);
                System.out.println("All the characters are dead. Game Over!");
                return;
            }
            if(!cAlive) {
                printer.printer(this);
                System.out.println("Congragulations! You won by killing all the monsters");
                return;
            }
            if (treasure == 12) {
                printer.printer(this);
                System.out.println("Congragulations! You won by collecting all the treasure.");
                return;
            }

            //Every normal turn
            printer.printer(this);
            runTurns(); //Performs all turn operations
            observer.deleteLogger(logger);
            observer.updateTracker(this);
            setTurn(getTurn() + 1);

        }
    }
    public void setAdventurers(ArrayList<Adventurer> a){
        adventurers = a;
    }
    public ArrayList<Adventurer> getAdventurers(){
        return adventurers;
    }
    public void setCreatures(ArrayList<Creature> c){
        creatures = c;
    }
    public ArrayList<Creature> getCreatures(){
        return creatures;
    }

    /**
     * runTurns() performs all turn operations for every monster and creature that is currently alive in the simulation.
     * First Test.adventurers, then Test.creatures.
     */
    public void runTurns(){
            for (int i = 0; i < adventurers.size(); i++) {
                adventurers.get(i).turn(this);
            }
            for (int i = 0; i < creatures.size(); i++) {
                if(creatures.get(i).isAlive()) {
                    creatures.get(i).turn(this);
                }
            }
        }

    /**
     * Generates the Test.world and populates it with rooms
     */
    private void generateWorld() {
        Room room = new Room(new int[]{0, 1, 1});
        rooms.add(room);
        for(int i = 1; i <= numLevels; i++) {
            for (int j = 0; j < depth; j++) {
                for (int k = 0; k < width; k++) {
                    int[] arr = new int[]{i, j, k};
                    room = new Room(arr);
                    rooms.add(room);
                }
            }
        }
    }
    //Members for adding Test.creatures and Test.adventurers to rooms

    public void addAdventurer(Adventurer a) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getLevel() == a.getLevel() && room.getY() == a.getY() && room.getX() == a.getX()) {
                room.addAdventurer(a);
            }
        }
    }

    public void addCreature(Creature c) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getLevel() == c.getLevel() && room.getY() == c.getY() && room.getX() == c.getX()) {
                room.addCreature(c);
            }
        }
    }

    public void addTreasure(Treasure t) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            int[] pos = t.getPos();
            if (room.getLevel() == pos[0] && room.getY() == pos[1] && room.getX() == pos[2]) {
                room.addTreasure(t);
            }
        }
    }

//Generates starting pos for each adventurer/creature and populates referential arrays
    public void generateAdventurers() {
        ArrayList<Adventurer> adv = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getLevel() == 0 && room.getY() == 1 && room.getX() == 1) {
                Brawler b = new Brawler(0, 1, 1);
                Runner r = new Runner(0, 1, 1);
                Sneaker s = new Sneaker(0, 1, 1);
                Thief t = new Thief(0, 1, 1);
                b.o = observer;
                r.o = observer;
                s.o = observer;
                t.o = observer;
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
        setAdventurers(adv);
        printer.setAdventurer(adv);
    }

    public void generateCreatures() {
        ArrayList<Creature> creat = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Blinker b = new Blinker(getNumLevels(), getWidth(), getDepth());
            Seeker s = new Seeker(getNumLevels(), getWidth(), getDepth());
            Orbiter o = new Orbiter(getNumLevels(), getWidth(), getDepth());
            addCreature(b);
            addCreature(s);
            addCreature(o);
            creat.add(b);
            creat.add(s);
            creat.add(o);
        }
        setCreatures(creat);
        printer.setCreature(creat);
    }

    public void generateTreasure() {
        for (int i = 0; i < 4; i++) {
            Sword s = new Sword(randomPosition());
            Armor a = new Armor(randomPosition());
            Gem g = new Gem(randomPosition());
            Trap t = new Trap(randomPosition());
            Potion p = new Potion(randomPosition());
            Portal portal = new Portal(randomPosition());
            addTreasure(s);
            addTreasure(a);
            addTreasure(g);
            addTreasure(t);
            addTreasure(p);
            addTreasure(portal);
        }
    }

    public int getNumLevels() {
        return numLevels;
    }
    public int getWidth() {
        return width;
    }
    public int getDepth() {
        return depth;
    }
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int level, int y, int x) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (level == room.getLevel() && y == room.getY() && x == room.getX()) {
                return room;
            }
        }
        return new Room(new int[]{level, y, x});
    }
    public int[] randomPosition() {
        Random rand = new Random();
        int level = rand.nextInt(getNumLevels()) + 1;
        int y = rand.nextInt(getDepth());
        int x = rand.nextInt(getWidth());
        return new int[]{level, y, x};
    }
    public int getTurn() {
        return turn;
    }
    public void setTurn(int turn) {
        this.turn = turn;
    }


    public static void main(String[] args) {
        World w = new World();
        w.generateAdventurers();
        w.generateCreatures();
        w.generateTreasure();
        w.runGame();
    }
}
