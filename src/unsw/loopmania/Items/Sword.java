package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {
    private static final int DAMAGE = 15;
    
    /**
     * Create a sword object
     * @param x - x coordinate of item in inventory
     * @param y -  y coordinate of item in inventory
     * Pass through the predetermined baseDamage of a sword item
     */
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, DAMAGE);
    }

    @Override
    public String getType() {
        return "sword";
    }  
}
