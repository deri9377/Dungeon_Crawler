package main.adventurers;

import main.creatures.Creature;
import main.fight.FightAttribute;
import main.fight.Untrained;
import main.fight.celebration.*;
import main.search.SearchMethod;
import main.world.Die;
import main.world.Observer;
import main.world.Room;
import main.world.World;
import main.world.object.*;

import javax.imageio.IIOException;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.io.IOException;

/**
 * This is a great example of an abstract class. We made this class abstract so that we could have the subclasses
 * of adventurer be forced to implement a couple different methods in their own way.
 */
public abstract class Adventurer {
    // We wanted the Test.adventurers to track their own position, health, treasure, and die so that that information was
    // encapsulated within the class and other classes did not have to worry about it and to keep the coupling loose
    private int[] pos;
    private int health;
    private Hashtable<String, Treasure> treasure = new Hashtable<>(); //Not sure what the different between hashmap and hashtable is
    private ArrayList<String> treasureBag = new ArrayList<>();
    private int level = 1;
    private FightAttribute fightAttribute;
    private SearchMethod searchMethod;
    public static int CAREFUL = 1;
    public static int QUICK = 2;
    public static int CARELESS = 3;

    public boolean startedDancing = false;
    public Observer o;

    private Die die;

    private String name;

    // Standard default and paramaterized constructors assign the position and other variables so they are never null
    public Adventurer() {
        pos = new int[]{0,1,1};
        init();
    }

    public Adventurer(int level, int y, int x) {
        pos = new int[]{level, y, x};
        init();
    }

    public void init() {
        health = 3;
        die = new Die();
        treasure.put("sword", new Sword());
        treasure.put("gem", new Gem());
        treasure.put("armor", new Armor());
        treasure.put("potion", new Potion());
    }

    /**
     * This function acts as the main method for the Test.adventurers this keeps our code properly segmented and easy to read
     * This function performs a move and then the appropriate action phase
     * @param world: We needed to pass an instance of the Test.world in so that the character could have access to the rooms
     *             that it moved to. In order to determine to fight or to search for treasure
     */
    public void turn(World world) {
        if (!isAlive()) {
            return;
        }
        move(world);
        o = world.getObserver();
        //TODO:observer call here probs (need method to call observers)
        o.move_event(this, this.pos);
        ArrayList<Creature> creatures = world.getRoom(getLevel(), getY(), getX()).getCreatures();
        if(!creatures.isEmpty()){
            for (int i = 0; i < creatures.size(); i++) {
                if (creatures.get(i).isAlive()) {
                    fight(creatures.get(i));
                }
            }
        } else {
            searchMethod.search(this, world.getRoom(getLevel(), getY(), getX()));
        }
    }

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
        w.getRoom(getLevel(), getY(), getX()).remove(this); // The character needs to update the room it leaves as well as goes into so that its being tracked
        int numLevels = w.getNumLevels();
        int depth = w.getDepth();
        int width = w.getWidth();
        if (getLevel() == 0) {
            setLevel(1);
            w.getRoom(getLevel(), getY(), getX()).addAdventurer(this);
            return;
        }
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        if (getY() - 1 > 0) {
            possibleMoves.add(0);
        }
        if (getY() + 1 < depth){
            possibleMoves.add(1);
        }
        if (getX() + 1 < width) {
            possibleMoves.add(2);
        }
        if (getX() - 1 > 0) {
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
        int moveDir = possibleMoves.get(new Random().nextInt(possibleMoves.size())); //choose a random move out of the possible rooms the Adventurer can move to
        if(moveDir == 0){ //Move North
            setY(getY() - 1);
        }
        if(moveDir == 1){ //Move South
            setY(getY() + 1);
        }
        if(moveDir == 2){ //Move East
            setX(getX() + 1);
        }
        if(moveDir == 3){ //Move West
            setX(getX() - 1);
        }
        if (moveDir == 4) { // Move Up
            setLevel(getLevel() + 1);
        }
        if (moveDir == 5) { //Move down
            setLevel(getLevel() - 1);
        }
        w.getRoom(getLevel(), getY(), getX()).addAdventurer(this); // Add the Adventurer to the new room it has entered
    }

    /*
    The way the treasure currently works is that we have an array list of all the items that a player can hold and then
    set the object to either true or false if the character has obtained it or not.
     */
    public void addTreasure(Treasure t) {
//        System.out.println("TREASURE WOOOOO"); didnt work :(
        setHealth(t.healthBonus());
        if(t.getName().equals("portal")){
            Random rand = new Random();
            int[] newPos = new int[]{rand.nextInt(1,4), rand.nextInt(1,4), rand.nextInt(1,4)};
            pos = newPos;
        }
        if (!(t.getName().equals("trap") || t.getName().equals("portal"))) {
            treasure.get(t.getName()).setObtained(true);
            treasureBag.add(t.getName());
        }
    }

    // Everything below is basic getters and setters for the Adventurer class
    public boolean isAlive() {
        return health > 0;
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
}
