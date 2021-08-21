package unsw.loopmania.StrategyPattern;

import java.util.List;
import org.javatuples.Pair;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;

public class PlacementCheckAdjacent implements PlacementStrategy {
    /**
     * Checks whether a building is able to be placed a specified spot based on the criteria that the spot is adjacent to the path
     * @param x - the x coordinate to be tested
     * @param y - the y coordinate to be tested
     * @param buildings - list of all building in the world can be null
     * @param enemies - list of all enemies in the world can be null
     */
    @Override
    public boolean doPlacementCheck(int x, int y, List<Pair<Integer, Integer>> orderedPath, List<Building> buildings, List<BasicEnemy> enemies) {
        if (orderedPath.indexOf(new Pair<Integer, Integer>(x, y)) != -1) {
            return false;
        }

        for (Pair<Integer, Integer> path : orderedPath) {
            int pathX = path.getValue0();
            int pathY = path.getValue1();
            System.out.println(Math.abs(pathY - y));
            if (pathX == x && Math.abs(pathY - y) == 1) {
                return true;
            }
            if (pathY == y && Math.abs(pathX - x) == 1) {
                return true;
            }
        }
        return false;
    }
}
