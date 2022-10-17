package main.world;

import main.adventurers.*;
import main.command.CelebrateCommand;
import main.command.FightCommand;
import main.command.MoveCommand;
import main.command.SearchCommand;
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
import java.util.Scanner;

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

    private ArrayList<Adventurer> adventurers = new ArrayList<>();
    private ArrayList<Creature> creatures = new ArrayList<>();

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
            boolean aAlive = true;
            for (int i = 0; i < rooms.size(); i++) {
                ArrayList<Adventurer> a = getAdventurers();
                for (int j = 0; j < a.size(); j++) {
                    if (!a.get(j).isAlive()) {
                        aAlive = false;
                    }
                }
            }
            if (!aAlive) {
                printer.printer(this);
                System.out.println("You Died. Game Over!");
                return;
            }

            //Every normal turn
            printer.printer(this);
            runTurns(); //Performs all turn operations
            // observer.deleteLogger(logger);
            // observer.updateTracker(this);
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
     * This function acts as the main method for the Test.adventurers this keeps our code properly segmented and easy to read
     * This function performs a move and then the appropriate action phase
     */
    public void runTurns(){
            for (int i = 0; i < adventurers.size(); i++) {
                Adventurer a = adventurers.get(i);
                if (!a.isAlive()) {
                    return;
                }
                Scanner scanner = new Scanner(System.in);
                Room room = getRoom(a.getLevel(), a.getY(), a.getX());
                if(room.checkAliveCreatures()){
                    System.out.println("What action would you like to take");
                    System.out.println("1: Fight");
                    System.out.println("2: Move");
                    String action = scanner.nextLine().toLowerCase();
                    if (action.equals("1") || action.equals("fight")) {
                        ArrayList<Creature> creatures = room.getCreatures();
                        for (int j = 0; j < creatures.size(); j++) {
                            if (creatures.get(j).isAlive()) {
                                FightCommand fc = new FightCommand(a, creatures.get(j));
                                fc.execute();
                            }
                        }
                    } else if (action.equals("2") || action.equals("move")) {
                        MoveCommand mc = new MoveCommand(a, this);
                        mc.execute();
                    }
                } else {
                    System.out.println("What action would you like to take");
                    System.out.println("1: Move");
                    System.out.println("2: Search");
                    System.out.println("3: Celebrate");
                    String action = scanner.nextLine().toLowerCase();
                    if (action.equals("1") || action.equals("move")) {
                        MoveCommand mc = new MoveCommand(a, this);
                        mc.execute();
                    } else if (action.equals("2") || action.equals("search")) {
                        SearchCommand sc = new SearchCommand(a, room);
                        sc.execute();
                    } else if (action.equals("3") || action.equals("Celebrate")) {
                        //TODO: there is an issue here. We need to be able to celebrate without fighting
                        CelebrateCommand cc = new CelebrateCommand(a);
                        cc.execute();
                    }
                }
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
        adventurers.add(a);
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
    public void createAdventurers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a character name.");
        String name = scanner.nextLine();
        System.out.println("Please enter a valid class: brawler, runner, sneaker or theif");
        String adventurerType = scanner.nextLine();
        adventurerType = adventurerType.toLowerCase();
        while (!(adventurerType.equals("brawler") || adventurerType.equals("runner") || adventurerType.equals("sneaker")
                || adventurerType.equals("theif"))) {
            System.out.println("Please enter a valid class: brawler, runner, sneaker or theif");
            adventurerType = scanner.nextLine();
            adventurerType = adventurerType.toLowerCase();
        }
        adventurerType = adventurerType.toLowerCase();
        if  (adventurerType.equals("brawler")) {
            Brawler brawler = new Brawler(0, 1, 1);
            addAdventurer(brawler);
        } else if (adventurerType.equals("runner")) {
            Runner runner = new Runner(0, 1, 1);
            addAdventurer(runner);
        } else if (adventurerType.equals("sneaker")) {
            Sneaker sneaker = new Sneaker(0, 1, 1);
            addAdventurer(sneaker);
        } else if (adventurerType.equals("theif")) {
            Thief theif = new Thief(0, 1, 1);
            addAdventurer(theif);
        }
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
        w.createAdventurers();
        w.generateCreatures();
        w.generateTreasure();
        w.runGame();
    }
}
