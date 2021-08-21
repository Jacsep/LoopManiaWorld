package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Equippable extends Item {

    public Equippable(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * @return the slot that the item uses when equipped
     */
    abstract public int getSlot();

    /**
     * Given a coordinate return whether or not the item can be equipped there
     * @return boolean
     */
    public abstract boolean validEquipCoords(int x, int y);

    /**
     * Get inventory slot coordinates
     * @return x value
     */
    public abstract int getEquippedX();

    /**
     * Get inventory slot coordinates
     * @return y value
     */
    public abstract int getEquippedY();
}
