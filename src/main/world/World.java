package main.world;

import main.adventurers.*;
import main.command.CelebrateCommand;
import main.command.FightCommand;
import main.command.MoveCommand;
import main.command.SearchCommand;
import main.creatures.Creature;
import main.world.factories.CharacterFactory;
import main.world.object.*;

import java.util.*;

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
    private final int MAX_TURNS = 50;
    private int numLevels;
    private int width;
    private int depth;
    private int turn;
    private CharacterFactory factory = new CharacterFactory();

    private ArrayList<Adventurer> adventurers = new ArrayList<>();
    private ArrayList<Creature> creatures = new ArrayList<>();

    private Printer printer = new Printer();

    public World() {
        numLevels = 4;
        width = 3;
        depth = 3;
        rooms = new ArrayList<>();
        generateWorld();
    }

    public World(int numLevels, int width, int depth) {
        this.numLevels = numLevels;
        this.width = width;
        this.depth = depth;
        rooms = new ArrayList<>();
        generateWorld();
    }

    /**
     * This function is the main game engine. It checks to see if any endgame conditions have occured otherwise
     * it runs the adventurer and creature turns
     */
    public void runGame() {
        setTurn(0);
        while (!gameOver() && turn <= MAX_TURNS) {
            Logger logger = Logger.getLogger(turn);
            printer.printer(this);
            for (int i = 0; i < adventurers.size(); i++) {
                if (adventurers.get(i).getName().equals("Runner")) {
                    runAdventurerTurn(adventurers.get(i));
                }
                runAdventurerTurn(adventurers.get(i));
            }
            runCreatureTurns(); //Performs all turn operations
            Logger.deleteLogger();
            Tracker.getInstance().update(this);
            setTurn(getTurn() + 1);
        }
        boolean creaturesAlive = isCreaturesAlive();
        boolean treasureObtained = isTreasureObtained(adventurers.get(0));
        if (treasureObtained || !creaturesAlive) {
            System.out.println("Congratulations You Win!");
            if (treasureObtained) {
                System.out.println("You collected all the treasure");
            }
            if (!creaturesAlive) {
                System.out.println("You killed all the enemies");
            }
        } else {
            System.out.println("Game Over. You Lose!");
        }

    }

    /**
     *
     * @return if the game is over return true
     */
    public boolean gameOver() {
        boolean aAlive = true;
        for (int i = 0; i < adventurers.size(); i++) {
            if (!adventurers.get(i).isAlive()) {
                aAlive = false;
            }
            if (adventurers.get(i).getLeftStart() && adventurers.get(i).getLevel() == 0) {
                System.out.println("You returned to spawn.");
                return true;
            }
        }
        if (!aAlive) {
            printer.printer(this);
            System.out.println("You Died. Game Over!");
            return true;
        }
        return false;
    }

    /**
     * Returns is any of the creatures are still alive
     * @return if any ceature is alive
     */
    public boolean isCreaturesAlive() {
        boolean creaturesAlive = false;
        for (int i = 0; i < creatures.size(); i++) {
            if (creatures.get(i).isAlive()) {
                creaturesAlive = true;
            }
        }
        return creaturesAlive;
    }

    /**
     * Returns is the a has found all of the treasure
     * @param a: the adventurer
     * @return if the adventurer has obtained all treasure
     */
    public boolean isTreasureObtained(Adventurer a) {
        boolean treasureObtained = true;
        for (Map.Entry<String, Treasure> set:  a.getTreasure().entrySet()) {
            if (!set.getValue().isObtained()) {
                treasureObtained = false;
            }
        }
        return treasureObtained;
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
    public void runAdventurerTurn(Adventurer a){
        if (!a.isAlive()) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        Room room = getRoom(a.getLevel(), a.getY(), a.getX());
        if (room.checkAliveCreatures()) {
            //Takes in a string so the user can input the number or the string
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
                a.setHealth(a.getHealth() - 1);                 //if the a tries to move while a creature is there
                mc.execute();
            }
        } else {
            // Needs to take in a string so that you can analyze if it is the number or string inputted
            System.out.println("What action would you like to take");
            System.out.println("1: Move");
            System.out.println("2: Search");
            System.out.println("3: Celebrate");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("1") || action.equals("move")) {
                MoveCommand mc = new MoveCommand(a, this);
                mc.execute();
            } else if (action.equals("2") || action.equals("search")) {
                SearchCommand sc = new SearchCommand(a, this);
                sc.execute();
            } else if (action.equals("3") || action.equals("celebrate")) {
                //TODO: there is an issue here. We need to be able to celebrate without fighting
                CelebrateCommand cc = new CelebrateCommand(a);
                cc.execute();
            }
        }
    }

    private void runCreatureTurns() {
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
        creatures.add(c);
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
        System.out.println("Please enter a valid class: brawler, runner, sneaker or thief");
        String adventurerType = scanner.nextLine();
        adventurerType = adventurerType.toLowerCase();
        while (!(adventurerType.equals("brawler") || adventurerType.equals("runner") || adventurerType.equals("sneaker")
                || adventurerType.equals("thief"))) {
            System.out.println("Please enter a valid class: brawler, runner, sneaker or thief");
            adventurerType = scanner.nextLine();
            adventurerType = adventurerType.toLowerCase();
        }
        adventurerType = adventurerType.toLowerCase();
        factory.createAdventurer(this, adventurerType, name);
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

    public void generateCreatures() {
        for (int i = 0; i < 4; i++) {
            factory.createCreature(this, "blinker");
            factory.createCreature(this, "orbiter");
            factory.createCreature(this, "seeker");
        }
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
        Logger logger = Logger.getLogger(0);
        w.createAdventurers();
        w.generateCreatures();
        w.generateTreasure();
        w.runGame();
    }
}
