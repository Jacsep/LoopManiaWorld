package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import unsw.loopmania.States.AttackState;
import unsw.loopmania.States.NormalAttackState;
import unsw.loopmania.States.CriticalAttackState;

public class AttackStateTest {
    @Test
    public void normalAttackStateTest() {
        AttackState normalAttackState = new NormalAttackState(10, 0);
        assertEquals(10, normalAttackState.attack(false));

        normalAttackState = new NormalAttackState(12, 100);
        assertEquals(-2, normalAttackState.attack(false));

        int counter = 0;
        int nonCrits = 0;
        while (counter < 100) {
            normalAttackState = new NormalAttackState(100, 100);
            if (normalAttackState.attack(true) == 100) {
                nonCrits ++;
            }
            counter ++;
        }

        assertTrue(nonCrits > 0);

    }

    @Test
    public void criticalAttackStateTest() {
        int counter = 0;
        while (counter < 100) {
            AttackState criticalAttackState = new CriticalAttackState(10, 1, 10, 1, 3);
            assertTrue(10 < criticalAttackState.attack(false));
            criticalAttackState.attack(false);
            criticalAttackState.attack(false);
            assertEquals(-1, criticalAttackState.attack(false));
            counter ++;
        }

        
    }
}
