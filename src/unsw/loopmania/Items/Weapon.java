package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Helpers.Chance;

public abstract class Weapon extends Equippable {
    private static final int DEFAULT_TRANCE = 0;

    private int attackDamage;
    private int trance;
    private int slot;

    /**
     * Create a weapon entity
     * @param x - x coordinate in inventory
     * @param y -  y coordinate in inventory
     * @param ad - base attackDamage
     */
    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, int ad) {
        super(x, y);
        this.attackDamage = ad;
        this.trance = DEFAULT_TRANCE;
        this.slot = Inventory.WEAPON_SLOT;
    }

    /**
     * Get attack damage
     * @return damage
     */
    public int attackDamage() {
        return this.attackDamage;
    }
    
    public void changeAttackDamage(int newAttackDamage) {
        this.attackDamage = newAttackDamage;
    }
    
    /**
     * Setter for trance chance
     * @param trance - percentage chance for trance to occur
     */
    protected void setTrance(int trance) {
        this.trance = trance;
    }

    /**
     * Determine whether the enemy will be tranced
     * @return true if enemy will be tranced, false otherwise
     */
    public boolean trance() {
        if (Chance.binomialChance(trance)) {
            return true;
        }
        return false;
    }

    /**
     * Get the weapon's slot number
     * @return weapon's slot number
     */
    public int getSlot() {
        return this.slot;
    }

    /*
     * Given a coordinate return whether or not the item can be equipped there
     * @return boolean
     */
    @Override
    public boolean validEquipCoords(int x, int y) {
        if (x == Inventory.WEAPON_SLOT_X && y == Inventory.WEAPON_SLOT_Y) {
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
        return Inventory.WEAPON_SLOT_X;
    }

    /**
     * Get inventory slot coordinates
     * @return y value
     */
    @Override
    public int getEquippedY() {
        return Inventory.WEAPON_SLOT_Y;
    }
}
