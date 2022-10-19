package main.fight.celebration;
import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.fight.FightAttribute;


import java.util.Random;

/**
 * This class is the parent class of the decorator and it extends the parent of the fight attributes
 * It has its own fight method and does the appropriate celebrations on top of the fight method
 */

public abstract class Celebrate extends FightAttribute {
    private String name;
    private FightAttribute fightAttribute;

    public Celebrate(FightAttribute fightAttribute) {
        this.fightAttribute = fightAttribute;
    }
    Random rand = new Random();

    /**
     * Pre-calculate how many times this one will print its celebration
     */
    public void celebrate(){
        int i = (rand.nextInt(3));
        for (int j = i; j > 0; j--){
            System.out.print(name);
        }
    }
    public void setName(String n){name = n;}
    public String getName() {return name;}

    public FightAttribute getFightAttribute() {
        return fightAttribute;
    }       //must track the fight attribute as each object is passed into another

    public void setFightAttribute(FightAttribute fightAttribute) {
        this.fightAttribute = fightAttribute;
    }

    /**
     * This method calls it FightAttributes fight until it recursively goes all the way up to the regular fight method.
     * When it pops back up the stack the celebrations are printed out along the way
     * @param adventurer the adventurer that is fighting
     * @param creature the creature that is fighting
     * @return if the creature died or not
     */
    @Override
    public boolean fight(Adventurer adventurer, Creature creature) {
        int hp = adventurer.getHealth();
        boolean isDead = fightAttribute.fight(adventurer, creature);
        if(adventurer.getHealth() == hp){
            if(adventurer.startedDancing == false){
                System.out.print(adventurer.getName() + " celebrated!:");
                adventurer.startedDancing = true;
            }
            celebrate();
        }
        return isDead;
    }

}