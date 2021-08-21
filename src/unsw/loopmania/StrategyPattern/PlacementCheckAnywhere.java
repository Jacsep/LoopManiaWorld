package unsw.loopmania.StrategyPattern;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;

public class PlacementCheckAnywhere implements PlacementStrategy {
    /**
     * Just return true
     * @param x - the x coordinate to be tested
     * @param y - the y coordinate to be tested
     * @param buildings - list of all building in the world can be null
     * @param enemies - list of all enemies in the world can be null
     */
    @Override
    public boolean doPlacementCheck(int x, int y, List<Pair<Integer, Integer>> orderedPath, List<Building> buildings, List<BasicEnemy> enemies) {
        return true;
    }
}
