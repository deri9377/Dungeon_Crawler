package Test.world;

import main.adventurers.*;
import main.creatures.Blinker;
import main.creatures.Creature;
import main.creatures.Orbiter;
import main.creatures.Seeker;
import main.world.object.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import main.world.Room;
import main.world.World;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs all the Junit tests. We used a lot of AssertALL function to minimize the number asserts that get
 * outputed as a lot were running at once
 */
public class WorldTest {

    public static World w;
    @BeforeEach
    void initAll() {
        w = new World();
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
        Adventurer a = new Brawler(0, 1, 1);
        int[] correct_pos = new int[]{0, 1, 1};
        assertArrayEquals(a.getPos(), correct_pos);
    }

    /**
     * Testing that there are the correct number of Adventurers Instantiated
     */
    @Test
    public void adventurerCorrectNumber() {
        Adventurer a = new Brawler(0, 1, 1);
        w.addAdventurer(a);
        int numAdventurers = 1;
        assertAll("Testing if all the Adventurers is instantiated in the start position",
                () -> assertEquals(w.getAdventurers().size(), numAdventurers),
                () -> assertEquals(a instanceof Adventurer, true));
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

    @Test
    public void endGameAllMonstersKilled() {
        for (int i = 0; i < w.getCreatures().size(); i++) {
            w.getCreatures().get(i).setAlive(false);
        }
        assertFalse(w.isCreaturesAlive());
    }

    @Test
    public void endGameAllTreasureFound() {
        Adventurer a = new Brawler(0, 1, 1);
        for (Map.Entry<String, Treasure> set:  a.getTreasure().entrySet()) {
            set.getValue().setObtained(true);
        }
        assertTrue(w.isTreasureObtained(a));
    }

    @Test
    public void endGamePosition() {
        Adventurer a = new Brawler(0, 1, 1);
        a.setLeftStart(true);
        w.addAdventurer(a);
        assertTrue(w.gameOver());
    }

    @Test
    public void endGameDeath() {
        Adventurer a = new Brawler(0, 1, 1);
        a.setHealth(0);
        w.addAdventurer(a);
        assertTrue(w.gameOver());
    }

    @Test
    public void portalSuccess() {
        Adventurer a = new Brawler(0, 1, 1);
        int[] pos = a.getPos();
        a.addTreasure(new Portal());
        assertNotEquals(pos, a.getPos());
    }

    @Test
    public void potionSuccess() {
        Adventurer a = new Brawler(0, 1, 1);
        int health = a.getHealth();
        a.addTreasure(new Potion());
        assertNotEquals(health, a.getHealth());
    }

    @Test
    public void trapSuccess() {
        Adventurer a = new Brawler(0, 1, 1);
        int health = a.getHealth();
        a.addTreasure(new Trap());
        assertNotEquals(health, a.getHealth());
    }

    @Test
    public void combatSuccess() {
        Adventurer a = new Brawler(0, 1, 1);
        Creature c = new Blinker(0, 1, 1);
        int aHealth = a.getHealth();
        a.fight(c);
        assertTrue(aHealth != a.getHealth() || !c.isAlive());
    }

    @Test
    public void creatureDoesntMoveWithAdventurer() {
        Adventurer a = new Brawler(1, 1, 1);
        Creature c = new Blinker(1, 1, 1);
        w.addAdventurer(a);
        w.addCreature(c);
        int[] pos = c.getPos();
        c.turn(w);
        assertTrue(c.getPos() == pos);
    }

    @Test
    public void cantPickUpDuplicateItems() {
        Adventurer a = new Brawler(0, 1, 1);
        a.addTreasure(new Potion());
        int health = a.getHealth();
        a.addTreasure(new Potion());
        assertTrue(health == a.getHealth());
    }





}
