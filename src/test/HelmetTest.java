package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Helmet;

public class HelmetTest {
    @Test
    public void testReduceAttack() {
        Helmet newHelmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertEquals(newHelmet.reduceAttack(), 0.9);
    }

    @Test
    public void testReduceDefence() {
        Helmet newHelmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertEquals(newHelmet.returnDefenceStat(), 5);
    }

    @Test
    public void testString() {
        Helmet newHelmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertEquals(newHelmet.getType(), "helmet");
    }

    @Test
    public void TestvalidCoords() {
        Helmet newHelmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newHelmet.validEquipCoords(1, 0), true);
    }

    @Test
    public void TestInvalidCoords() {
        Helmet newHelmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newHelmet.validEquipCoords(1, 2), false);
    }
}
