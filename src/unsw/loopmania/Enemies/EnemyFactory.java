package unsw.loopmania.Enemies;

import java.util.List;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Building;

public class EnemyFactory {
    public static BasicEnemy generateEnemy(String type, int health, PathPosition pos, List<Building> buildings) {
        BasicEnemy result = null;
        if (type.equals("doggie")) {
            result = new DoggieEnemy(pos);
        } else if (type.equals("elanMuske")) {
            result = new ElanMuskeEnemy(pos);
        } else if (type.equals("slug")) {
            result = new SlugEnemy(pos);
        } else if (type.equals("vampire")) {
            result = new VampireEnemy(pos, buildings);
        } else if (type.equals("zombie")) {
            result = new ZombieEnemy(pos);
        }
        result.setHealth(health);
        return result;
    }
}
