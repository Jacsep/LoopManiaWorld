package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Items.HealthPotion;
import unsw.loopmania.Character;

public class HealthPotionTest {
    @Test
    public void TestTooMuchHealth() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        Character newCharacter = new Character(new PathPosition(0, newList));

        newCharacter.changeHealth(-30);

        assertEquals(newCharacter.getHealth(), 70);

        HealthPotion newPotion = new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        newCharacter.changeHealth(newPotion.getHealthRestore());

        assertEquals(newCharacter.getHealth(), 100);
    }

    @Test
    public void TestFunctional() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        Character newCharacter = new Character(new PathPosition(0, newList));

        newCharacter.changeHealth(-50);

        assertEquals(newCharacter.getHealth(), 50);

        HealthPotion newPotion = new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        newCharacter.changeHealth(newPotion.getHealthRestore());

        assertEquals(newCharacter.getHealth(), 90);
    }

    @Test 
    public void TestString() {
        HealthPotion newPotion = new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newPotion.getType(), "healthPotion");
    }
}
