package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class BuildingFactory {
    public static Building generateItem(String type, int x, int y) {
        Building result = null;
        if (type.equals("barracks")) {
            result = new BarracksBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("campfire")) {
            result = new CampfireBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("tower")) {
            result = new TowerBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("trap")) {
            result = new TrapBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("vampireCastle")) {
            result = new VampireCastleBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("village")) {
            result = new VillageBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        } else if (type.equals("zombiePit")) {
            result = new ZombiePitBuilding(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        }
        return result;
    }
}