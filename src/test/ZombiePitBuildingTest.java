package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.ZombiePitBuilding;
import unsw.loopmania.Character;

public class ZombiePitBuildingTest {
    @Test
    public void TestGenerateRandom() {
        ZombiePitBuilding newZombiePit = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertEquals(newZombiePit.getNumZombiesSpawn(), 0);

        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 2));

        newZombiePit.specialAbility(new Character(new PathPosition(0, newList)), new ArrayList<>());

        assertTrue(newZombiePit.getNumZombiesSpawn() >= 1);
        assertTrue(newZombiePit.getNumZombiesSpawn() <= 2);
    }

    @Test
    public void testString() {
        ZombiePitBuilding newZombiePit = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newZombiePit.getType(), "zombiePit");
    }
}
