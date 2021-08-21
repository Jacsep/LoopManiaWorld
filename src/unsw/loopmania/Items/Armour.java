package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class for the armour item
 */
public class Armour extends DefenceItem {
    /**
     * Construct for an armour item
     * @param x - x coordinate in the unequipped inventory
     * @param y - y coordiante in the unequipped inventory
     */
    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, Inventory.BODY_SLOT);
    }

    /**
     * @return the defence stat of an armour item
     */
    @Override
    public double returnDefenceStat() {
        return 0.5;
    }

    @Override
    public String getType() {
        return "armour";
    }

    /*
     * Given a coordinate return whether or not the item can be equipped there
     * @return boolean
     */
    @Override
    public boolean validEquipCoords(int x, int y) {
        if (x == 1 && y == 1) {
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
        return Inventory.BODY_SLOT_X;
    }

    /**
     * Get inventory slot coordinates
     * @return y value
     */
    @Override
    public int getEquippedY() {
        return Inventory.BODY_SLOT_Y;
    }
}
