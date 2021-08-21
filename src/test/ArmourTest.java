package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Armour;

public class ArmourTest {
    @Test
    public void testSimple() {
        Armour newArmour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newArmour.returnDefenceStat(), 0.5);
    }

    @Test
    public void testString() {
        Armour newArmour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newArmour.getType(), "armour");
    }

    @Test
    public void TestvalidCoords() {
        Armour newArmour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newArmour.validEquipCoords(1, 1), true);
    }

    @Test
    public void TestInvalidCoords() {
        Armour newArmour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newArmour.validEquipCoords(1, 2), false);
    }
}
