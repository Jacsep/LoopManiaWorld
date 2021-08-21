package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.StrategyPattern.PlacementCheckAdjacent;
import unsw.loopmania.StrategyPattern.PlacementCheckNonPath;
import unsw.loopmania.StrategyPattern.PlacementCheckPath;


public class PlacementStrategyTest {

    @Test
    public void testAdjacent() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));

        PlacementCheckAdjacent test = new PlacementCheckAdjacent();

        assertEquals(test.doPlacementCheck(0, 0, newList, null, null), true);
        assertEquals(test.doPlacementCheck(2, 2, newList, null, null), false);
    }

    @Test
    public void testPath() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));

        PlacementCheckPath test = new PlacementCheckPath();

        assertEquals(test.doPlacementCheck(0, 0, newList, null, null), false);
        assertEquals(test.doPlacementCheck(1, 0, newList, null, null), true);
    }

    @Test
    public void testNonPath() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));

        PlacementCheckNonPath test = new PlacementCheckNonPath();

        assertEquals(test.doPlacementCheck(1, 0, newList, null, null), false);
        assertEquals(test.doPlacementCheck(0, 0, newList, null, null), true);
    }
}
