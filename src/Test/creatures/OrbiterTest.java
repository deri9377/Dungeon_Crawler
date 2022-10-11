package Test.creatures;

import main.creatures.Orbiter;
import main.creatures.Seeker;
import main.world.World;

import java.util.ArrayList;

public class OrbiterTest {
    public static void main(String[] args) {
        int numLevels = 4;
        int width = 3;
        int depth = 3;

        ArrayList<ArrayList<Integer>> playerPos = new ArrayList<>();
        World w = new World();
        Orbiter test1 = new Orbiter(numLevels, width, depth);
        test1.setLevel(4);
        test1.setY(0);
        test1.setX(2);
        test1.move(w);
    }
}
