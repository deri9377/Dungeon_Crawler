package Test.adventurers;

import main.adventurers.Brawler;
import main.world.Room;
import main.world.World;

public class BrawlerTest {

    public static void main(String[] args) {
        Brawler b = new Brawler(0, 1, 1);
        World w = new World();
        w.addAdventurer(b);
        for (int i = 0; i < w.getRooms().size(); i++) {
            Room room = w.getRooms().get(i);
            if (room.getLevel() == b.getLevel() && room.getY() == b.getY() && room.getX() == b.getX()) {
                System.out.println("turn");
                for (int j = 0; j < 10; j++) {
                    b.turn(w);
                }
            }
        }
        System.out.println(b.getLevel() + " " + b.getY() + " " + b.getX() + " " + b.getTreasure());
    }
}
