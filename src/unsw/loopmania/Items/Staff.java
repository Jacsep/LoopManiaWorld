package unsw.loopmania.Items;

import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.BasicEnemy;

public class Staff extends Weapon {
    public static final int DAMAGE = 2;
    public static final int TRANCE = 30;
    /**
     * Create a staff object
     * @param x - x coordinate of item in inventory
     * @param y -  y coordinate of item in inventory
     * Pass through the predetermined baseDamage of a staff item
     */
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, DAMAGE);
        super.setTrance(TRANCE);
    }
    
    /**
     * Determines whether the trance ability has been successful
     * If successful, pick a random enemy in the battle to be tranced
     * @param battleEnemies
     * @return the tranced enemy
     */
    public BasicEnemy trance(List<BasicEnemy> battleEnemies) {
        Random rand = new Random();

        if (battleEnemies.size() == 0) {
            return null;
        }

        int chance = rand.nextInt(100);

        // Trance a random enemy
        if (chance < 30) {
            BasicEnemy tranced = battleEnemies.get(rand.nextInt(battleEnemies.size()));
            return tranced;
        }
        
        return null;
    }

    public String getType() {
        return "staff";
    }
}
