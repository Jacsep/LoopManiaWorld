package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends DefenceItem {
    private double defenceStat;

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, Inventory.SHIELD_SLOT);
        this.defenceStat = 0.75;
    }

    public void changeDefenceStat(double newDefence) {
        this.defenceStat = newDefence;
    }

    /**
     * @return the defence stat of the shield item
     */
    @Override
    public double returnDefenceStat() {
        return defenceStat;
    }

    @Override
    public String getType() {
        return "shield";
    }

    /*
     * Given a coordinate return whether or not the item can be equipped there
     * @return boolean
     */
    @Override
    public boolean validEquipCoords(int x, int y) {
        if (x == 2 && y == 1) {
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
        return Inventory.SHIELD_SLOT_X;
    }

    /**
     * Get inventory slot coordinates
     * @return y value
     */
    @Override
    public int getEquippedY() {
        return Inventory.SHIELD_SLOT_Y;
    }
}
