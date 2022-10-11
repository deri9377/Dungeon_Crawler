package Test.world;

import main.adventurers.Brawler;
import main.adventurers.Runner;
import main.adventurers.Sneaker;
import main.adventurers.Thief;
import main.creatures.Blinker;
import main.creatures.Orbiter;
import main.creatures.Seeker;
import main.world.object.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import main.world.Room;
import main.world.World;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs all the Junit tests. We used a lot of AssertALL function to minimize the number asserts that get
 * outputed as a lot were running at once
 */
public class WorldTest {

    public static World w;
    @BeforeAll
    static void initAll() {
        w = new World();
        w.generateAdventurers();
        w.generateCreatures();
        w.generateTreasure();
    }

    @Test
    public void adventurerGenerationSuccess() {
        adventurerCorrectPosition();
        adventurerCorrectNumber();
    }

    @Test
    public void adventurerCorrectPosition() {
        int[] correct_pos = new int[]{0, 1, 1};
        for (int i = 0; i < w.getAdventurers().size(); i++) {
            assertArrayEquals(w.getAdventurers().get(i).getPos(), correct_pos);
        }
    }

    /**
     * Testing that there are the correct number of Adventurers Instantiated
     */
    @Test
    public void adventurerCorrectNumber() {
        int numAdventurers = 4;
        assertAll("Testing if all 4 Adventurers are instantiated in the start position",
                () -> assertEquals(w.getAdventurers().size(), numAdventurers),
                () -> assertEquals(w.getAdventurers().stream().filter(a -> a instanceof Brawler).count(), 1),
                () -> assertEquals(w.getAdventurers().stream().filter(a -> a instanceof Runner).count(), 1),
                () -> assertEquals(w.getAdventurers().stream().filter(a -> a instanceof Sneaker).count(), 1),
                () -> assertEquals(w.getAdventurers().stream().filter(a -> a instanceof Thief).count(), 1));
    }

    /**
     * Testing that all the creatures are being instantiated
     */
    @Test
    public void creatureGenerationSuccess() {
        assertAll("Testing the correct number of each Creature is instantiated",
                () -> assertEquals(w.getCreatures().stream().filter(a -> a instanceof Blinker).count(), 4),
                () -> assertEquals(w.getCreatures().stream().filter(a -> a instanceof Orbiter).count(), 4),
                () -> assertEquals(w.getCreatures().stream().filter(a -> a instanceof Seeker).count(), 4));
    }

    /**
     * Testing that all the treasure got instantiated
     */
    @Test
    public void treasureGenerationSuccess() {
        ArrayList<Treasure> treasures = new ArrayList<>();
        int numTreasures = 4;
        for (int i = 0; i < w.getRooms().size(); i++) {
            Room r = w.getRooms().get(i);
            for (int j = 0; j < r.getTreasure().size(); j++) {
                treasures.add(r.getTreasure().get(j));
            }
        }
        assertAll("Testing the correct number of each treasure is instantiated",
                () -> assertEquals(treasures.stream().filter(a -> a instanceof Armor).count(), numTreasures),
                () -> assertEquals(treasures.stream().filter(a -> a instanceof Gem).count(), numTreasures),
                () -> assertEquals(treasures.stream().filter(a -> a instanceof Potion).count(), numTreasures),
                () -> assertEquals(treasures.stream().filter(a -> a instanceof Sword).count(), numTreasures),
                () -> assertEquals(treasures.stream().filter(a -> a instanceof Portal).count(), numTreasures),
                () -> assertEquals(treasures.stream().filter(a -> a instanceof Trap).count(), numTreasures));
    }

}
