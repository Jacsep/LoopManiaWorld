package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Character;

public class VillageBuildingTest {
    @Test
    public void TestDifferentTiles() {
        VillageBuilding newVillage = new VillageBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(0, 1));
        newList.add(new Pair<Integer, Integer>(1, 1));

        Character newCharacter = new Character(new PathPosition(0, newList));
        newCharacter.changeHealth(-20);

        assertEquals(newCharacter.getHealth(), 80);

        newVillage.specialAbility(newCharacter, new ArrayList<>());

        assertEquals(newCharacter.getHealth(), 80);
    }

    @Test
    public void TestMaximumHealth() {
        VillageBuilding newVillage = new VillageBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        Character newCharacter = new Character(new PathPosition(0, newList));
        newCharacter.changeHealth(-10);

        assertEquals(newCharacter.getHealth(), 90);

        newVillage.specialAbility(newCharacter, new ArrayList<>());

        assertEquals(newCharacter.getHealth(), 100);
    }

    @Test
    public void TestFunctional() {
        VillageBuilding newVillage = new VillageBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        Character newCharacter = new Character(new PathPosition(0, newList));
        newCharacter.changeHealth(-50);

        assertEquals(newCharacter.getHealth(), 50);

        newVillage.specialAbility(newCharacter, new ArrayList<>());

        assertEquals(newCharacter.getHealth(), 70);
    }

    @Test
    public void TestString() {
        VillageBuilding newVillage = new VillageBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newVillage.getType(), "village");
    }
}
