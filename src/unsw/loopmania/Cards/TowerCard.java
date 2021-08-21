package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.StrategyPattern.PlacementCheckAdjacent;

public class TowerCard extends BuildingCard {
    /*
     * Constructor for tower card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new PlacementCheckAdjacent());
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "towerCard";
    }

    /*
     * Create and return an object of the corresponding building
     * @pre the combination of x and y is a valid placement for the building
     * @param x - x coordinate of where the building will be placed
     * @param y - y coordinate of where the building will be placed
     * @return object of type TowerBuilding
     */
    @Override
    public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TowerBuilding(x, y);
    }    

}
