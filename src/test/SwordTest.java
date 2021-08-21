package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Sword;


public class SwordTest {
    @Test
    public void testAttackDamage() {
        Sword newSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertEquals(newSword.attackDamage(), 15);
    }

    @Test
    public void testString() {
        Sword newSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertEquals(newSword.getType(), "sword");
    }

    @Test
    public void testEquip() {
        Sword newSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(newSword.validEquipCoords(0, 1));
        assertFalse(newSword.validEquipCoords(0, 0));
        assertFalse(newSword.validEquipCoords(1, 0));
        assertFalse(newSword.validEquipCoords(1, 1));
        newSword.changeAttackDamage(10);
    }
}
