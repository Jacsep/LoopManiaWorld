package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.TreeStump;

public class TreeStumpTest {
    @Test
    public void TestReturnDefence() {
        TreeStump newTreeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newTreeStump.returnDefenceStat(), 0.7);
    }

    @Test
    public void TestString() {
        TreeStump newTreeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newTreeStump.getType(), "treeStump");
    }

    @Test
    public void TestChangeDefence() {
        TreeStump newTreeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        double initialStat = newTreeStump.returnDefenceStat();
        newTreeStump.changeDefenceStat(0.01);
        assertTrue(newTreeStump.returnDefenceStat() != initialStat);
    }

    @Test
    public void TestvalidCoords() {
        TreeStump newTreeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newTreeStump.validEquipCoords(2, 1), true);
    }

    @Test
    public void TestInvalidCoords() {
        TreeStump newTreeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newTreeStump.validEquipCoords(1, 2), false);
    }

    @Test
    public void TestBossDefence() {
        TreeStump newTreeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newTreeStump.returnBossDefence(), 0.55);
    }
}
