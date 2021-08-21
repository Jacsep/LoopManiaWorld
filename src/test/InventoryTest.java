package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.ElanMuskeEnemy;
import unsw.loopmania.Items.Anduril;
import unsw.loopmania.Items.Inventory;
import unsw.loopmania.Items.ItemFactory;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Items.TreeStump;

public class InventoryTest {
    @Test
    public void testModifyOutgoingNothing() {
        Inventory inventory = new Inventory();
        assertEquals(5, inventory.modifyOutgoingDamage(5, false));
    }

    @Test
    public void testModifyOutgoingConfusingStump() {
        int counter = 0;
        int andurils = 0;
        while (counter < 1000) {
            Inventory inventory = new Inventory();
            inventory.setMode("Confusing");
            TreeStump stump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            stump.setConfusingModeItem();
            inventory.addUnequippedItem(stump);
            if (inventory.modifyOutgoingDamage(5, false) == 25) {
                andurils ++;
            }
            counter ++;
        }
        assertTrue(andurils > 0);
    }

    @Test
    public void testModifyOutgoingConfusingRing() {
        int counter = 0;
        int andurils = 0;
        while (counter < 1000) {
            Inventory inventory = new Inventory();
            inventory.setMode("Confusing");
            TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            ring.setConfusingModeItem();
            inventory.addUnequippedItem(ring);
            if (inventory.modifyOutgoingDamage(5, false) == 25) {
                andurils ++;
            }
            counter ++;
        }
        assertTrue(andurils > 0);
    }

    @Test
    public void testModifyIncomingStump() {
        Inventory inventory = new Inventory();
        TreeStump stump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        stump.setConfusingModeItem();
        inventory.addEquippedItem(stump);
        inventory.useHealthPotion();
        assertEquals(55, inventory.modifyIncomingDamage(100, new ElanMuskeEnemy(null)));
    }

    @Test
    public void testModifyIncomingConfusingAnduril() {
        int counter = 0;
        int stumps = 0;
        while (counter < 1000) {
            Inventory inventory = new Inventory();
            inventory.setMode("Confusing");
            Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            anduril.setConfusingModeItem();
            inventory.addUnequippedItem(anduril);
            if (inventory.modifyIncomingDamage(100, null) == 70) {
                stumps ++;
            }
            counter ++;
        }
        assertTrue(stumps > 0);
    }

    @Test
    public void testModifyIncomingConfusingRing() {
        int counter = 0;
        int stumps = 0;
        while (counter < 1000) {
            Inventory inventory = new Inventory();
            inventory.setMode("Confusing");
            TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            ring.setConfusingModeItem();
            inventory.addUnequippedItem(ring);
            if (inventory.modifyIncomingDamage(100, null) == 70) {
                stumps ++;
            }
            counter ++;
        }
        assertTrue(stumps > 0);
    }

    @Test
    public void testCreateRandomConfusing() {
        int counter = 0;
        while (counter < 3000) {
        Inventory inventory = new Inventory();
        inventory.setMode("Confusing");
        inventory.addUnequippedItem(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        counter ++;
        }
    }

    @Test
    public void testRemainingItemFactory() {
        assertTrue(ItemFactory.generateItem("anduril", 0, 0).getType().equals("anduril"));
        assertTrue(ItemFactory.generateItem("treeStump", 0, 0).getType().equals("treeStump"));
        assertTrue(ItemFactory.generateItem("theonering", 0, 0).getType().equals("theOneRing"));
        assertTrue(ItemFactory.generateItem("doggieCoin", 0, 0).getType().equals("doggieCoin"));
    }
}
