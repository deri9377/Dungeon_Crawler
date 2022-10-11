package main.fight;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.Die;
import main.world.object.Treasure;

import java.util.Hashtable;
import java.util.Random;

public class Stealth extends FightAttribute{

    /**
     * This function is the interaction between a creature and this adventurer
     * @param adventurer: the adventurer that is in the fight
     * @param creature: the creature object that the character is fighting
     */
    public boolean fight(Adventurer adventurer, Creature creature) {
        if (new Random().nextInt(2) == 1) {
            return super.fight(adventurer, creature, 0);
        }
        return false;
    }
}
