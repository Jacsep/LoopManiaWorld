package unsw.loopmania.Items;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class Anduril extends Weapon {
    private static final int DAMAGE = 20;
    private String additionalConfusingItem;
    private double rareChargesRemaining;

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, DAMAGE);
    }

    public void setConfusingModeItem() {
        this.rareChargesRemaining = Double.POSITIVE_INFINITY;
        Random rand = new Random();
        int num = rand.nextInt(2);

        if (num == 0) {
            this.additionalConfusingItem = "TheOneRing";
            this.rareChargesRemaining = 1;
        } else {
            this.additionalConfusingItem = "TreeStump";
        }
    }

    public void reduceCharges() {
        this.rareChargesRemaining = this.rareChargesRemaining - 1;
    }

    public double numOfCharges() {
        return this.rareChargesRemaining;
    }

    public String additionalItem() {
        return this.additionalConfusingItem;
    }

    @Override
    public String getType() {
        return "anduril";
    }

    public int bossDamageMultiplier() {
        return 3;
    }
}
