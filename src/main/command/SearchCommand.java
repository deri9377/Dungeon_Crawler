package main.command;

import main.adventurers.Adventurer;
import main.world.Room;
import main.world.World;

public class SearchCommand implements Command{

    private Adventurer adventurer;
    private World world;

    public SearchCommand(Adventurer a, World w) {
        adventurer = a;
        world = w;
    }

    @Override
    public void execute() {
        this.adventurer.getSearchMethod().search(adventurer, world);
    }

}
