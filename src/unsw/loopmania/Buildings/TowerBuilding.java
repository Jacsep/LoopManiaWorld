package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Helpers.Distance;

public class TowerBuilding extends Building {
    //private List<MovingEntity> entitiesInRadius;
    private int baseDamage;
    private int radius;
    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.baseDamage = 10;
        this.radius = 4;
    }

    @Override
    public String getType() {
        return "tower";
    }

    /*
     * Getter for tower's attack
     * @return int containing tower's attack
     */
    public int getAttack() {
        return baseDamage;
    }

    /*
     * Check if a coordinate lies within an tower's support radius (doesn't check if the coordinate is inside
     * the world)
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within the enemy's support radius
     */
    public boolean checkRadius(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= radius) {
            return true;
        }
        return false;
    }

    @Override
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {}    
}
