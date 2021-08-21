package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.StrategyPattern.PlacementCheckNonPath;

public class CampfireCard extends BuildingCard {
    /*
     * Constructor for campfire card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new PlacementCheckNonPath());
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "campfireCard";
    }
    
    /*
     * Create and return an object of the corresponding building
     * @pre the combination of x and y is a valid placement for the building
     * @param x - x coordinate of where the building will be placed
     * @param y - y coordinate of where the building will be placed
     * @return object of type CampfireBuilding
     */
    @Override
    public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new CampfireBuilding(x, y);
    }   
}
