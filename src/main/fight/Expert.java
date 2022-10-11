package main.fight;

import main.adventurers.Adventurer;
import main.creatures.Creature;

public class Expert extends FightAttribute{

    @Override
    public boolean fight(Adventurer adventurer, Creature creature, int advBonus) {
        return super.fight(adventurer, creature, 2);
    }
}
