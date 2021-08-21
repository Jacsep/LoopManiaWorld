package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public class ItemFactory {
    public static Item generateItem(String type, int x, int y) {
        Item result = null;
        if (type.equals("sword")) {
            result = new Sword(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("stake")) {
            result = new Stake(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("staff")) {
            result = new Staff(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("armour")) {
            result = new Armour(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("helmet")) {
            result = new Helmet(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("shield")) {
            result = new Shield(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("healthPotion")) {
            result = new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("anduril")) {
            result = new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("treeStump")) {
            result = new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("theonering")) {
            result = new TheOneRing(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("doggieCoin")) {
            result = new DoggieCoin(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        }
        return result;
    }
}
