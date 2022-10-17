package main.command;

import main.adventurers.Adventurer;
import main.creatures.Creature;
import main.world.Room;

public class FightCommand implements Command {

    private Adventurer adventurer;
    private Creature creature;

    public FightCommand(Adventurer a, Creature c) {
        adventurer = a;
        creature = c;
    }

    @Override
    public void execute() {
        this.adventurer.fight(creature);
    }
}
