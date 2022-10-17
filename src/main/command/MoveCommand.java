package main.command;

import main.adventurers.Adventurer;
import main.world.World;

public class MoveCommand implements Command{

    private World world;
    private Adventurer adventurer;

    public MoveCommand(Adventurer a, World w) {
        world = w;
        adventurer = a;
    }

    @Override
    public void execute() {
        this.adventurer.move(world);
    }

}
