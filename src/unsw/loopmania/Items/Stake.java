package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends Weapon {
    public static final int DAMAGE = 5;
    /**
     * Create a stake object
     * @param x - x coordinate of item in inventory
     * @param y -  y coordinate of item in inventory
     * Pass through the predetermined baseDamage of a stake item
     */
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, DAMAGE);
    }

    /**
     * @return the bonus amount of damage a stake does to a vampire
     */
    public int criticalDamageVampire() {
        return 30;
    }

    @Override
    public String getType() {
        return "stake";
    }
}
