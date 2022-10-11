package Test.world;

import main.world.Die;

public class DieTest {

    public static void main(String[] args) {
        Die d1 = new Die();
        Die brawler = new Die(2, 0);
        for (int i = 0; i < 10; i++) {
            System.out.println(brawler.fightDie() + " " + brawler.searchDie());
        }
    }
}
