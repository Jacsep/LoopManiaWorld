package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.StrategyPattern.PlacementCheckPath;

import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Buildings.Building;

public class BarracksCard extends BuildingCard {
    /*
     * Constructor for barracks card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new PlacementCheckPath());
    }  

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "barracksCard";
    }

    /*
     * Create and return an object of the corresponding building
     * @pre the combination of x and y is a valid placement for the building
     * @param x - x coordinate of where the building will be placed
     * @param y - y coordinate of where the building will be placed
     * @return object of type BarracksBuilding
     */
    @Override
    public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new BarracksBuilding(x, y);
    }

}
