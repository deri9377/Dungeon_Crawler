package Test.creatures;

import main.creatures.Blinker;
import main.world.World;

import java.util.ArrayList;

public class BlinkerTest {

    public static void main(String[] args) {
        int numLevels = 4;
        int width = 3;
        int depth = 3;


        Blinker test1 = new Blinker(numLevels, width, depth);
        test1.setLevel(4);
        test1.setY(2);
        World w = new World();
        w.addCreature(test1);
    }
}
