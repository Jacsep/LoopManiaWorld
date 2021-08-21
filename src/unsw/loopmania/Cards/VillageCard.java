package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.StrategyPattern.PlacementCheckPath;


public class VillageCard extends BuildingCard {
    /*
     * Constructor for village card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new PlacementCheckPath());
    }  

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "villageCard";
    }

    /*
     * Create and return an object of the corresponding building
     * @pre the combination of x and y is a valid placement for the building
     * @param x - x coordinate of where the building will be placed
     * @param y - y coordinate of where the building will be placed
     * @return object of type villageBuilding
     */
    @Override
    public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VillageBuilding(x, y);
    }
}
