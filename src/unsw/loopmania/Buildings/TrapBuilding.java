package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;

public class TrapBuilding extends Building {
    private int baseDamage;
    private boolean exists;
    /**
     * Constructor for a trap entity
     * @param x - x coordinate on the map
     * @param y - y coordinate on the map
     */
    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.baseDamage = 20;
        this.exists = true;
    }

    /**
     * If a enemy is standing on the trap tile, inflict damage and destroy trap
     */
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {
        if (this.exists) {
            for (BasicEnemy enemy : enemyList) {
                if (enemy.getX() == this.getX() && enemy.getY() == this.getY()) {
                    enemy.reduceHealth(baseDamage, null);
                    if (enemy.getHealth() <= 0) {
                        enemy.destroy();
                        enemyList.remove(enemy);
                    }
                    this.exists = false;
                    break;
                }
            }
        }

    }
    @Override
    public String getType() {
        return "trap";
    }

    public boolean getExists() {
        return this.exists;
    }
}
