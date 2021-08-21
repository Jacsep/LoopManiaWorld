package unsw.loopmania.StrategyPattern;

import java.util.List;
import org.javatuples.Pair;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;


/**
 * Strategy pattern interface for the algorithms to determine whether a building can be placed at a specific spot
 * @param x - the x coordinate to be tested
 * @param y - the y coordinate to be tested
 * @param buildings - list of all building in the world
 * @param enemies - list of all enemies in the world
 */
public interface PlacementStrategy {
    public boolean doPlacementCheck(int x, int y, List<Pair<Integer, Integer>> orderedPath, List<Building> buildings, List<BasicEnemy> enemies);
}