package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.TrapBuilding;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.SlugEnemy;
import unsw.loopmania.Character;

public class TrapBuildingTest {
    @Test
    public void testTrapNoEnemies() {
        TrapBuilding newTrap = new TrapBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertEquals(newTrap.getExists(), true);

        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        newTrap.specialAbility(new Character(new PathPosition(0, newList)), new ArrayList<>());

        assertEquals(newTrap.getExists(), true);
    }

    @Test
    public void testFunctional() {
        TrapBuilding newTrap = new TrapBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertEquals(newTrap.getExists(), true);

        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));
        newList.add(new Pair<Integer, Integer>(1, 3));

        List<BasicEnemy> enemyList = new ArrayList<BasicEnemy>();
        SlugEnemy newSlug = new SlugEnemy(new PathPosition(0, newList));
        enemyList.add(newSlug);

        assertEquals(enemyList.size(), 1);

        newTrap.specialAbility(new Character(new PathPosition(2, newList)), enemyList);

        assertEquals(newTrap.getExists(), false);
        assertEquals(newSlug.getHealth(), 0);

        assertEquals(enemyList.size(), 0);
    }

    @Test
    public void testString() {
        TrapBuilding newTrap = new TrapBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(newTrap.getType(), "trap");
    }
}
