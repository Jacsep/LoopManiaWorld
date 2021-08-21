package test;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Stake;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StakeTest {
    @Test
    public void TestCriticalDamage() {
        Stake newStake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        
        assertEquals(newStake.criticalDamageVampire(), 30);
    }

    @Test
    public void TestString() {
        Stake newStake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        
        assertEquals(newStake.getType(), "stake");
    }
}
