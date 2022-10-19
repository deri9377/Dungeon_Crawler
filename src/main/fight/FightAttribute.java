package main.fight;


import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.Die;
import main.world.Logger;
import main.world.object.Treasure;

import java.util.HashMap;
import java.util.Hashtable;

public abstract class FightAttribute {
    public static int STEALTH = 0;
    public static int UNTRAINED = 1;
    public static int TRAINED = 2;
    public static int EXPERT = 3;

    /**
     * This function is the interaction between a creature and this adventurer
     * @param adventurer: the adventurer that is in the fight
     * @param creature: the creature object that the character is fighting
     */
    public boolean fight(Adventurer adventurer, Creature creature, int advBonus) {
        Die die = adventurer.getDie();
        boolean creatureDied = false;
        Hashtable<String, Treasure> treasure = adventurer.getTreasure();
        if (!adventurer.isAlive()) {
            return false;
        }
        /* it was important for each character to have its own die based on its attributes so that we could just call
            roll on the creature or fightDie/searchDie dependign on the situation. Also the damage taken needed to be
            handled within this fight method so that both the adventurer and Creatures track their own health and it
            is encapsulated within the fight function and no where else.
        */
        int creatureRoll = creature.roll() + treasure.get("gem").combatBonus() + treasure.get("armor").combatBonus();
        int advRoll = die.fightDie() + treasure.get("sword").combatBonus() + advBonus;
        if(creatureRoll > advRoll){
//            Damage Taken
            adventurer.setHealth(adventurer.getHealth() - 1);
            Logger.getLogger().combat(adventurer.getPlayerName(), creature.getName(), false);
        }
        if(creatureRoll < advRoll){
//            Victory Against Creature
            creature.setAlive(false);
            creatureDied = true;
            Logger.getLogger().combat(adventurer.getPlayerName(), creature.getName(), true);
        }
        return creatureDied;
    }

    public boolean fight(Adventurer adventurer, Creature creature) {
        return fight(adventurer, creature, 0);
    }
}
