package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class DefenceItem extends Equippable {
    private int slot;

    public DefenceItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int slot) {
        super(x, y);
        this.slot = slot;
    }

    /**
     * @return the integer of the slot for the item
     */
    public int getSlot() {
        return slot;
    }
    
    /**
     * @return the defence stat of the defence item
     */
    abstract public double returnDefenceStat();
}
