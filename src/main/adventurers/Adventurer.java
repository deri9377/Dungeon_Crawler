package main.adventurers;

import main.creatures.Creature;
import main.fight.FightAttribute;
import main.fight.celebration.*;
import main.search.SearchMethod;
import main.world.*;
import main.world.object.*;
import java.util.*;

/**
 * This is a great example of an abstract class. We made this class abstract so that we could have the subclasses
 * of adventurer be forced to implement a couple different methods in their own way.
 */
public abstract class Adventurer {
    // We wanted the Test.adventurers to track their own position, health, treasure, and die so that that information was
    // encapsulated within the class and other classes did not have to worry about it and to keep the coupling loose
    private int[] pos;
    private int health;
    private boolean leftStart = false;
    private Hashtable<String, Treasure> treasure = new Hashtable<>(); //Not sure what the different between hashmap and hashtable is
    private ArrayList<String> treasureBag = new ArrayList<>();
    private int level = 1;
    private FightAttribute fightAttribute;
    private SearchMethod searchMethod;
    public static int CAREFUL = 1;
    public static int QUICK = 2;
    public static int CARELESS = 3;

    public boolean startedDancing = false;

    private Die die;

    private String name;
    private String playerName;

    // Standard default and paramaterized constructors assign the position and other variables so they are never null
    public Adventurer() {
        pos = new int[]{0,1,1};
        init();
    }

    public Adventurer(int level, int y, int x) {
        pos = new int[]{level, y, x};
        init();
    }

    /**
     * The initialization of the inventory
     */
    public void init() {
        health = 3;
        die = new Die();
        treasure.put("sword", new Sword());
        treasure.put("gem", new Gem());
        treasure.put("armor", new Armor());
        treasure.put("potion", new Potion());
        treasure.put("portal", new Portal());
        treasure.put("trap", new Portal());
    }

    /**
     * If the adventurer decides to celebrate do the celebrations
     */
    public void celebrate() {
        //TODO: Implement a way to celebrate without fighting
        int celebrationChoice = 0;
        Random random = new Random();
        ArrayList<Celebrate> c = new ArrayList<>();
        c.add(new Dance(fightAttribute));
        c.add(new Spin(fightAttribute));
        c.add(new Shout(fightAttribute));
        c.add(new Jump(fightAttribute));
        c.add(new Griddy(fightAttribute));
        System.out.print(getPlayerName() + " celebrated! : ");
        for (int i = 0; i < 5; i++){
            c.get(i).celebrate();
            Logger.getLogger().notifyCelebration(getPlayerName(), c.get(i).getName());
        }
    }

    /**
     * Fights the creatue that is passed in
     * @param creature: the creature that the adventurer is fighting
     * @return if the creature died
     */
    public boolean fight(Creature creature) {
        FightAttribute temp = getFightAttribute();
        fightAttribute = new Dance(fightAttribute);
        fightAttribute = new Jump(fightAttribute);
        fightAttribute = new Shout(fightAttribute);
        fightAttribute = new Spin(fightAttribute);
        boolean isDead = fightAttribute.fight(this, creature);
        fightAttribute = temp;
        startedDancing = false;
        System.out.println("\n");
        return isDead;
    }

    /**
     * The most complex method within the Adventurer class. We instantiated a default move method that all of the
     * Adventurers follow.
     * @param w: The instance of the World at the time this method is called
     */
    public void move(World w) {
        /*
            The instructions specified that the characters always move first even if their are monsters in the room. The
            World parameter is required in order to get the bounds of the Test.world so that it is impossible for the Adventurer
            to move to an invalid room position.
        */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current Position: " + getLevel() + "-" + getY() + "-" + getX());
        w.getRoom(getLevel(), getY(), getX()).remove(this); // The character needs to update the room it leaves as well as goes into so that its being tracked
        int numLevels = w.getNumLevels();
        int depth = w.getDepth();
        int width = w.getWidth();
        if (getLevel() == 0) {
            System.out.println("Move From starting room to 1-1-1");
            setLeftStart(true);
            setLevel(getLevel() + 1);
            w.getRoom(getLevel(), getY(), getX()).addAdventurer(this);
            return;
        }
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        if (getY() - 1 >= 0) {
            possibleMoves.add(0);
        }
        if (getY() + 1 < depth){
            possibleMoves.add(1);
        }
        if (getX() + 1 < width) {
            possibleMoves.add(2);
        }
        if (getX() - 1 >= 0) {
            possibleMoves.add(3);
        }
        if (getX() == width/2 && getY() == depth/2) {
            if (getLevel() < numLevels) {
                possibleMoves.add(4);
            }
            if (getLevel() > 1) {
                possibleMoves.add(5);
            }
        }
        if (getX() == 1 && getY() == 1 && getLevel() == 1) {
            possibleMoves.add(5);
        }
        // int moveDir = possibleMoves.get(new Random().nextInt(possibleMoves.size())); //choose a random move out of the possible rooms the Adventurer can move to
        System.out.println("Possible moves are: ");
        for (int i = 0; i < possibleMoves.size(); i ++) {
            if (possibleMoves.get(i) == 0) {
                System.out.println("North: " + getLevel() + "-" + (getY() - 1) + "-" + getX());
            } else if (possibleMoves.get(i) == 1) {
                System.out.println("South: " + getLevel() + "-" + (getY() + 1) + "-" + getX());
            } else if (possibleMoves.get(i) == 2) {
                System.out.println("East: " + getLevel() + "-" + getY() + "-" + (getX() - 1));
            } else if (possibleMoves.get(i) == 3) {
                System.out.println("West: " + getLevel() + "-" + getY() + "-" + (getX() - 1));
            } else if (possibleMoves.get(i) == 4) {
                System.out.println("Up: " + (getLevel() + 1) + "-" + getY() + "-" + getX());
            } else if (possibleMoves.get(i) == 5) {
                System.out.println("Down: " + (getLevel() - 1) + "-" + getY() + "-" + getX());
            }
        }
        String moveDir = scanner.nextLine();
        if(moveDir.equals("0") || moveDir.equals("north")){ //Move North
            setY(getY() - 1);
        }
        if(moveDir.equals("1") || moveDir.equals("south")){ //Move South
            setY(getY() + 1);
        }
        if(moveDir.equals("2") || moveDir.equals("east")){ //Move East
            setX(getX() + 1);
        }
        if(moveDir.equals("3") || moveDir.equals("west")){ //Move West
            setX(getX() - 1);
        }
        if (moveDir.equals("4") || moveDir.equals("up")) { // Move Up
            setLevel(getLevel() + 1);
        }
        if (moveDir.equals("5") || moveDir.equals("down")) { //Move down
            setLevel(getLevel() - 1);
        }
        w.getRoom(getLevel(), getY(), getX()).addAdventurer(this); // Add the Adventurer to the new room it has entered
        Logger.getLogger().moveEvent(this, pos);
    }

    public void setPlayerName(String name){
        playerName = name;
        return;
    }
    public String getPlayerName(){
        return playerName;
    }
    /*
    The way the treasure currently works is that we have an array list of all the items that a player can hold and then
    set the object to either true or false if the character has obtained it or not.
     */
    public void addTreasure(Treasure t) {
//        System.out.println("TREASURE WOOOOO"); didnt work :(
        if (getTreasure().get(t.getName()).isObtained()) {
            return;
        }
        setHealth(getHealth() + t.healthBonus());
        getTreasure().get(t.getName()).setObtained(true);
        if(t.getName().equals("portal")){
            Random rand = new Random();
            int[] newPos = new int[]{rand.nextInt(1,4), rand.nextInt(1,4), rand.nextInt(1,4)};
            pos = newPos;


        }
        if (!(t.getName().equals("trap") || t.getName().equals("portal"))) {
            treasureBag.add(t.getName());
        }
    }

    // Everything below is basic getters and setters for the Adventurer class
    public boolean isAlive() {
        return health > 0;
    }

    public void setPos(int p[]){
        pos = p;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {

        this.health = health;
        Logger.getLogger().notifyHealthChange(this, health);
        if(health < 1){
            Logger.getLogger().defeated(playerName);
        }
    }

    public Hashtable<String, Treasure> getTreasure(){
        return treasure;
    }
    public ArrayList<String> getTreasures() {
        return treasureBag;
    }

    public int[] getPos() {
        return pos;
    }

    public Die getDie() {
        return die;
    }

    public void setDie(Die die) {
        this.die = die;
    }

    public String getName(){ return name; }

    public void setName(String n){  name = n; }

    public FightAttribute getFightAttribute() {
        return fightAttribute;
    }

    public void setFightAttribute(FightAttribute fightAttribute) {
        this.fightAttribute = fightAttribute;
    }

    public void setSearchMethod(SearchMethod searchMethod) {
        this.searchMethod = searchMethod;
    }
    public SearchMethod getSearchMethod() {return searchMethod;}

    public void setLeftStart(boolean b) {
        leftStart = b;
    }
    public boolean getLeftStart() {
        return leftStart;
    }
}
