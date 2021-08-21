package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.loopmania.AlliedSoldier;


public class AlliedSoldierTest {
    @Test
    public void testTakeDamage() {
        AlliedSoldier newSoldier = new AlliedSoldier();
        assertEquals(newSoldier.getHealth(), 35);

        newSoldier.takeDamage(-10);

        assertEquals(newSoldier.getHealth(), 25);

        newSoldier.takeDamage(-30);

        assertEquals(newSoldier.getHealth(), -5);
    }

    @Test 
    public void testGetDamage() {
        AlliedSoldier newSoldier = new AlliedSoldier();

        assertEquals(newSoldier.getBaseDamage(), 10);
    }
}
