package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Anduril;

public class AndurilTest {
    @Test
    public void TestString() {
        Anduril newAnduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newAnduril.getType(), "anduril");
    }

    @Test
    public void returnBossMultiplier() {
        Anduril newAnduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newAnduril.bossDamageMultiplier(), 3);
    }

    @Test
    public void TestReturnDamage() {
        Anduril newAnduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newAnduril.attackDamage(), 20);
    }
}
