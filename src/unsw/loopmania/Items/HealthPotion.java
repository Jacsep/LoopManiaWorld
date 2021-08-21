package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public class HealthPotion extends Item {
    private static final int healthRestore = 40;

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Consume a health potion and restore the character's health
     * @return health to be restored
     */
    public int getHealthRestore() {
        return healthRestore;
    }

    public String getType() {
        return "healthPotion";
    }
}
