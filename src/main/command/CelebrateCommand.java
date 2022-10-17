package main.command;

import main.adventurers.Adventurer;

public class CelebrateCommand implements Command{

    private Adventurer adventurer;

    public CelebrateCommand(Adventurer a) {
        adventurer = a;
    }

    @Override
    public void execute() {
        this.adventurer.celebrate();
    }
}
