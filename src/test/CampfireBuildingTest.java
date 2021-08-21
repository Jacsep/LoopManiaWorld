package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.CampfireBuilding;
import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuildingTest {
    @Test
    public void testCheckRadiusFunctional() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<>(1, 1));
        newList.add(new Pair<>(4, 4));
        Character testChar = new Character(new PathPosition(0, newList));
        CampfireBuilding newCampfire = new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(newCampfire.checkCharacterInRadius(testChar), true);
    }

    @Test
    public void testCheckRadiusFail() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<>(1, 1));
        newList.add(new Pair<>(4, 4));
        Character testChar = new Character(new PathPosition(1, newList));
        CampfireBuilding newCampfire = new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(newCampfire.checkCharacterInRadius(testChar), false);
    }

    @Test
    public void testString() {
        CampfireBuilding newCampfire = new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(newCampfire.getType(), "campfire");
    }
}
