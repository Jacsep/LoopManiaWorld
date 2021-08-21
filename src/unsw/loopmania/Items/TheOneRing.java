package unsw.loopmania.Items;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;

public class TheOneRing extends Item {
    private String additionalConfusingItem;

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Given that the character is about to die, restore them back to full health
     */
    public void revive(Character character) {
        character.fullHeal();
    }

    public void setConfusingModeItem() {
        Random rand = new Random();
        int num = rand.nextInt(2);

        if (num == 0) {
            this.additionalConfusingItem = "TreeStump";
        } else {
            this.additionalConfusingItem = "Anduril";
        }
    }

    public String additionalItem() {
        return this.additionalConfusingItem;
    }

    @Override
    public String getType() {
        return "theOneRing";
    }
}
