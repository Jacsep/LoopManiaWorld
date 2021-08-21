package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.TowerBuilding;

public class TowerBuildingTest {
    @Test
    public void testCheckRadiusFunctional() {
        TowerBuilding newTower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newTower.checkRadius(3, 2), true);
    }

    @Test
    public void testCheckRadiusTooFar() {
        TowerBuilding newTower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newTower.getAttack(), 10);
        assertEquals(newTower.checkRadius(4, 4), false);
    }

    @Test
    public void testInvalid() {
        TowerBuilding newTower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(newTower.checkRadius(-1, 4), false);
        assertEquals(newTower.checkRadius(-1, -4), false);
        assertEquals(newTower.checkRadius(1, -4), false);
    }

    @Test
    public void testString() {
        TowerBuilding newTower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(0, 0));
        newTower.specialAbility(new Character(new PathPosition(0, newList)), new ArrayList<>());

        assertEquals(newTower.getType(), "tower");
    }
}
