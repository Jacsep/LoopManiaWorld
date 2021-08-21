package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.Character;

public class VampireCastleBuildingTest {
    @Test
    public void testRoundSpawn() {
        VampireCastleBuilding newVampireCastle = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        newVampireCastle.setRoundSpawned(3);

        assertEquals(newVampireCastle.getRoundSpawned(), 3);
    }

    @Test
    public void testRandomSpawnNum() {
        VampireCastleBuilding newVampireCastle = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 2));

        assertEquals(newVampireCastle.getNumVampiresSpawn(), 0);

        newVampireCastle.specialAbility(new Character(new PathPosition(0, newList)), new ArrayList<>());
        int numToSpawn = newVampireCastle.getNumVampiresSpawn();

        assertTrue(numToSpawn >= 1);

        assertTrue(numToSpawn <= 3);
    }

    @Test
    public void testString() {
        VampireCastleBuilding newVampireCastle = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newVampireCastle.getType(), "vampireCastle");
    }
}
