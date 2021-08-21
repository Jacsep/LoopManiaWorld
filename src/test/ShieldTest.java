package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Shield;

public class ShieldTest {
    @Test
    public void TestReturnDefence() {
        Shield newShield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newShield.returnDefenceStat(), 0.75);
    }

    @Test
    public void TestString() {
        Shield newShield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newShield.getType(), "shield");
    }

    @Test
    public void TestChangeDefence() {
        Shield newShield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        double initialStat = newShield.returnDefenceStat();
        newShield.changeDefenceStat(0.01);
        assertTrue(newShield.returnDefenceStat() != initialStat);
    }

    @Test
    public void TestvalidCoords() {
        Shield newShield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newShield.validEquipCoords(2, 1), true);
    }

    @Test
    public void TestInvalidCoords() {
        Shield newShield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newShield.validEquipCoords(1, 2), false);
    }
}
