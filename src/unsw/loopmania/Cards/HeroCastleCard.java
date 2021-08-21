package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.StrategyPattern.PlacementCheckAdjacent;

/**
 * represents a vampire castle card in the backend game world
 */
public class HeroCastleCard extends BuildingCard {
    public HeroCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new PlacementCheckAdjacent());
    }

    @Override
    public String getType() {
        return "heroCastleCard";
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return null;
    }    
}
