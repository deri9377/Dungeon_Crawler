package main.command;

import main.adventurers.Adventurer;
import main.world.Room;

public class SearchCommand implements Command{

    private Adventurer adventurer;
    private Room room;

    public SearchCommand(Adventurer a, Room r) {
        adventurer = a;
        room = r;
    }

    @Override
    public void execute() {
        this.adventurer.getSearchMethod().search(adventurer, room);
    }

}
