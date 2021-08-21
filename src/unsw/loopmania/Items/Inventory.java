package unsw.loopmania.Items;

import java.lang.Math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

import unsw.loopmania.Character;

import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.BossEnemy;

import unsw.loopmania.Helpers.Chance;

public class Inventory {
    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    private static final int RARE_ITEM_CHANCE = 2;

    public static final int HEAD_SLOT = 0;
    public static final int WEAPON_SLOT = 1;
    public static final int BODY_SLOT = 2;
    public static final int SHIELD_SLOT = 3;

    public static final int HEAD_SLOT_X = 1;
    public static final int HEAD_SLOT_Y = 0;

    public static final int WEAPON_SLOT_X = 0;
    public static final int WEAPON_SLOT_Y = 1;

    public static final int BODY_SLOT_X = 1;
    public static final int BODY_SLOT_Y = 1;

    public static final int SHIELD_SLOT_X = 2;
    public static final int SHIELD_SLOT_Y = 1;
    
    // Only contains four slots (head - 0, weapon - 1, body - 2, shield - 3)
    private Map<Integer, Equippable> equippedItems;
    private List<Item> unequippedItems;
    private String mode;

    public Inventory() {
        unequippedItems = new ArrayList<Item>();
        equippedItems = new HashMap<Integer, Equippable>();
        equippedItems.put(HEAD_SLOT, null);
        equippedItems.put(WEAPON_SLOT, null);
        equippedItems.put(BODY_SLOT, null);
        equippedItems.put(SHIELD_SLOT, null);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Get damage dealt based on equipped items
     * @param attack - the base attack to be modified
     * @param isCampfire - double final damage if campfire is present
     * @return damage to be dealt
     */
    public int modifyOutgoingDamage(int attack, boolean isCampfire) {
        Weapon weapon = (Weapon )equippedItems.get(WEAPON_SLOT);
        Helmet helmet = (Helmet )equippedItems.get(HEAD_SLOT);
        if (weapon != null) {
            attack += weapon.attackDamage();
        }
        if (helmet != null) {
            attack = (int )Math.round(attack * 0.9);
        }
        if (isCampfire) {
            attack *= 2;
        }
        if (mode != null && mode.equals("Confusing")) {
            for (Item item : unequippedItems) {
                if (item instanceof TheOneRing) {
                    TheOneRing newRing = (TheOneRing) item;
                    if (newRing.additionalItem().equals("Anduril")) {
                        attack += 20;
                    }
                }
                if (item instanceof TreeStump) {
                    TreeStump newTreeStump = (TreeStump) item;
                    if (newTreeStump.additionalItem().equals("Anduril")) {
                        attack += 20;
                    }
                }
            }
        }
        return attack;
    }

    /**
     * Get whether or not the weapon will cause the enemy to be tranced
     * @return true if trance will occur and false otherwise
     */
    public boolean trance() {
        Weapon weapon = (Weapon )equippedItems.get(WEAPON_SLOT);
        if (weapon != null) {
            return weapon.trance();
        }
        return false;
    }

    /**
     * Fluctuate the value of the doggie coin
     */
    public void fluctateValueOfDoggieCoin() {
        for (Item item : unequippedItems) {
            if (item instanceof DoggieCoin) {
                DoggieCoin dogCoin = (DoggieCoin) item;
                dogCoin.fluctuatePrice();
            }
        }
    }

    /**
     * Get damage taken based on equipped items
     * @param damage - the incoming damage
     * @return damage after being modified by equippment
     */
    public int modifyIncomingDamage(int damage, BasicEnemy enemy) {
        Armour armour = (Armour )equippedItems.get(BODY_SLOT);
        Shield shield = (Shield )equippedItems.get(SHIELD_SLOT);
        Helmet helmet = (Helmet )equippedItems.get(HEAD_SLOT);

        if (armour != null) {
            damage = (int )Math.round(damage * armour.returnDefenceStat());
        }
        if (shield != null) {
            if (enemy instanceof BossEnemy && shield instanceof TreeStump) {
                TreeStump specialShield = (TreeStump) shield;
                damage = (int )Math.round(damage * specialShield.returnBossDefence());
            } else {
                damage = (int )Math.round(damage * shield.returnDefenceStat());
            }
        }
        if (mode != null && mode.equals("Confusing")) {
            for (Item item : unequippedItems) {
                if (item instanceof TheOneRing) {
                    TheOneRing newRing = (TheOneRing) item;
                    if (newRing.additionalItem().equals("TreeStump")) {
                        damage = (int )Math.round(damage * 0.7);
                    }
                }
                if (item instanceof Anduril) {
                    Anduril newAnduril = (Anduril) item;
                    if (newAnduril.additionalItem().equals("TreeStump")) {
                        damage = (int )Math.round(damage * 0.7);
                    }
                }
            }
        }
        if (helmet != null) {
            damage -= helmet.returnDefenceStat();
        }
        return damage;
    }

    /**
     * If a the one ring is equipped restore the charcter to full health and
     * unequip the ring
     * @param character - the character
     * @return boolean indicating whether or not the character has been successfully revived by the ring
     */
    public boolean getOneRingEquipped(Character character) {
        for (Item item : unequippedItems) {
            if (item instanceof TheOneRing) {
                ((TheOneRing )item).revive(character);
                removeUnequippedInventoryItem(item);
                return true;
            }
        } 
        return false;
    }

    /**
     * Use health potion
     * @return the amount of health the potion will restore
     */
    public int useHealthPotion() {
        for (Item item : unequippedItems) {
            if (item instanceof HealthPotion) {
                HealthPotion healthPotion = (HealthPotion) item;
                removeUnequippedInventoryItem(item);
                return healthPotion.getHealthRestore();
            }
        }
        return 0;
    }

    /**
     * Adds an equpped item to the equipped list
     * @pre the item slot is already empty
     * @param item - the item to be equipped
     */
    public void addEquippedItem(Equippable item) {
        item.setX(item.getEquippedX());
        item.setY(item.getEquippedY());
        equippedItems.put(item.getSlot(), item);
        unequippedItems.remove(item);
        resetUnequippedCoords();
    }

    public Map<Integer, Equippable> returnEquippedItems() {
        return this.equippedItems;
    }

    /**
     * Remove an equipped item by slot number and change character's statistics accordingly
     * @param slot
     * @return the item which has been unequipped
     */
    public Item unequipItem(int slot) {
        Item item = equippedItems.get(slot);
        Pair<Integer, Integer> coords = getFirstAvailableSlotForItem();
        item.setX(coords.getValue0());
        item.setY(coords.getValue1());
        unequippedItems.add(item);
        equippedItems.put(slot, null);
        resetUnequippedCoords();
        return item;
    }

    /**
     * Check if equipment slot is free
     * @param slot - equipment slot number
     * @return boolean containing result
     */
    public boolean isEquipmentSlotFree(int slot) {
        if (slot >= HEAD_SLOT && slot <= SHIELD_SLOT) {
            return equippedItems.get(slot) == null;
        }
        return false;
    }

    /**
     * spawn a random item in the world and return it
     * @return an item to be spawned in the controller as a JavaFX node
     */
    public Item addUnequippedItem(IntegerProperty gold, IntegerProperty experience){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        // if inventory is full eject the oldest unequipped item and replace it
        if (firstAvailableSlot == null){
            gold.set(gold.get() + Chance.intRangeChance(30, 50));
            experience.set(experience.get() + Chance.intRangeChance(20, 40));
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        // now we insert the new sword, as we know we have at least made a slot available...
        Item spawnedItem = randomItem(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), mode);
        unequippedItems.add(spawnedItem);
        return spawnedItem;
    }

    public void removeUnequippedItem(Item item) {
        unequippedItems.remove(item);
        resetUnequippedCoords();
    }

    /**
     * Add an item object to the unequipped inventory
     * @pre there is sufficient room in the unequipped inventory
     * @param item - the item to be added
     */
    public void addUnequippedItem(Item item) {
        unequippedItems.add(item);
        resetUnequippedCoords();
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Item item){
        item.destroy();
        unequippedItems.remove(item);
        resetUnequippedCoords();
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Item getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Item e: unequippedItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * Getter for all unequipped items
     * @return list containing all unequipped items
     */
    public List<Item> getUnequippedItems() {
        return unequippedItems;
    }

    /**
     * Get the item at specfied slot
     * @param slot - the slot number to be accessed
     * @return null if slot not equipped or invalid slot, the item otherwise
     */
    public Equippable getEquippedItemBySlot(int slot) {
        return equippedItems.get(slot);
    }

    /**
     * Getter for number of unequipped items
     * @return int containing result
     */
    public int numUnequippedItems() {
        return this.unequippedItems.size();
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Remove all items from the inventory and destroy them;
     */
    public void clear() {
        for (Item item : unequippedItems) {
            item.destroy();
        }
        unequippedItems = new ArrayList<Item>();
        for (int key : equippedItems.keySet()) {
            if (equippedItems.get(key) != null) {
                equippedItems.get(key).destroy();
                equippedItems.put(key, null);
            }
        }
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Item item = unequippedItems.get(index);
        item.destroy();
        unequippedItems.remove(index);
    }

    /**
     * Shift up all items
     */
    public void resetUnequippedCoords() {
        int counter = 0;
        while (counter < unequippedItems.size()) {
            Item item = unequippedItems.get(counter);
            item.setX(counter % unequippedInventoryWidth);
            item.setY(counter / unequippedInventoryHeight);
            counter ++;
        }
    }

    /**
     * Generate a randomised item
     * @param x - x coordinate of item to be spawned
     * @param y - y coordinate of item to be spawned
     * @param the game mode being played
     * @return the new item object
     */
    private Item randomItem(SimpleIntegerProperty x, SimpleIntegerProperty y, String mode) {
        if (Chance.binomialChance(RARE_ITEM_CHANCE)) {
            int randNum = Chance.intRangeChance(0, 2);
            if (randNum == 1) {
                TheOneRing newRing = new TheOneRing(x, y);
                if (mode != null && mode.equals("Confusing")) {
                    newRing.setConfusingModeItem();
                }
                return newRing;
            } else if (randNum == 2) {
                Anduril newAnduril = new Anduril(x, y);
                if (mode != null && mode.equals("Confusing")) {
                    newAnduril.setConfusingModeItem();
                }
                return newAnduril;
            } else {
                TreeStump newTreeStump = new TreeStump(x, y);
                if (mode != null && mode.equals("Confusing")) {
                    newTreeStump.setConfusingModeItem();
                }
                return newTreeStump;
            }
        }
        Item result = null;
        switch (Chance.intRangeChance(0, 6)) {
            case 0: 
                result = new Sword(x, y);
                break;
            case 1:
                result = new Stake(x, y);
                break;
            case 2:
                result = new Staff(x, y);
                break;
            case 3:
                result = new Armour(x, y);
                break;
            case 4:
                result = new Shield(x, y);
                break;
            case 5: 
                result = new Helmet(x, y);
                break;
            case 6:
                result = new HealthPotion(x, y);
                break;
        }
        return result;
    }
}
