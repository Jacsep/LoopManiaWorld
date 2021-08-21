package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends DefenceItem {
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, Inventory.HEAD_SLOT);
    }

    /**
     * @return the character's new attack ratio when equipping a helmet
     */
    public double reduceAttack() {
        return 0.9;
    }

    /**
     * @return the defence statistic of the hemlet so that it can be applied to the character
     */
    @Override
    public double returnDefenceStat() {
        return 5;
    }

    @Override
    public String getType() {
        return "helmet";
    }

    /*
     * Given a coordinate return whether or not the item can be equipped there
     * @return boolean
     */
    @Override
    public boolean validEquipCoords(int x, int y) {
        if (x == 1 && y == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Get inventory slot coordinates
     * @return x value
     */
    @Override
    public int getEquippedX() {
        return Inventory.HEAD_SLOT_X;
    }

    /**
     * Get inventory slot coordinates
     * @return y value
     */
    @Override
    public int getEquippedY() {
        return Inventory.HEAD_SLOT_Y;
    }
}
