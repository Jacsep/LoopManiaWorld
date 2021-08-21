package unsw.loopmania.StrategyPattern;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Enemies.BasicEnemy;

public class PlacementCheckVillage implements PlacementStrategy {
    /**
     * Return true if there is a village on that tile
     * @param x - the x coordinate to be tested
     * @param y - the y coordinate to be tested
     * @param buildings - list of all building in the world
     * @param enemies - list of all enemies in the world can be null
     */
    @Override
    public boolean doPlacementCheck(int x, int y, List<Pair<Integer, Integer>> orderedPath, List<Building> buildings, List<BasicEnemy> enemies) {
        for (Building building : buildings) {
            if (building instanceof VillageBuilding && building.getX() == x && building.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
