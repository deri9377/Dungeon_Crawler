package main.search;

import main.adventurers.Adventurer;
import main.world.Room;
import main.world.object.Treasure;

import java.util.ArrayList;

public abstract class SearchMethod {
    public abstract void search(Adventurer adventurer, Room room);
}
