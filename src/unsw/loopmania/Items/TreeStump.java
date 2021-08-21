package unsw.loopmania.Items;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class TreeStump extends Shield {
    private static final double defenceStat = 0.7;
    private final double bossDefenceStat = 0.55;
    private String additionalConfusingItem;
    private double rareChargesRemaining;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.changeDefenceStat(defenceStat);
    }

    public double returnBossDefence() {
        return this.bossDefenceStat;
    }

    public void setConfusingModeItem() {
        this.rareChargesRemaining = Double.POSITIVE_INFINITY;
        Random rand = new Random();
        int num = rand.nextInt(2);

        if (num == 0) {
            this.additionalConfusingItem = "TheOneRing";
            this.rareChargesRemaining = 1;
        } else {
            this.additionalConfusingItem = "Anduril";
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
        return "treeStump";
    }

}
