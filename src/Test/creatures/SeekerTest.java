package Test.creatures;

import main.adventurers.Adventurer;
import main.adventurers.Brawler;
import main.creatures.Seeker;
import main.world.World;

import java.util.ArrayList;

public class SeekerTest {

    public static void main(String[] args) {


        World w = new World();
        w.addAdventurer(new Brawler(4, 2, 2));
        Seeker test1 = new Seeker(4, 3, 3);
        test1.setLevel(4);
        test1.setY(1);
        test1.setX(2);
        test1.turn(w);
        test1.turn(w);
        System.out.println(test1.isAlive());
    }


}
