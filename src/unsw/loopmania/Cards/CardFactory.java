package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;

public class CardFactory {
    public static Card generateItem(String type, int x, int y) {
        Card result = null;
        if (type.equals("barracksCard")) {
            result = new BarracksCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("campfireCard")) {
            result = new CampfireCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("towerCard")) {
            result = new TowerCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("trapCard")) {
            result = new TrapCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("vampireCastleCard")) {
            result = new VampireCastleCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("villageCard")) {
            result = new VillageCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("zombiePitCard")) {
            result = new ZombiePitCard(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        }
        return result;
    }
}
