package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.StrategyPattern.PlacementCheckAdjacent;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends BuildingCard {
    /*
     * Constructor for vampire castle card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new PlacementCheckAdjacent());
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "vampireCastleCard";
    }

    /*
     * Create and return an object of the corresponding building
     * @pre the combination of x and y is a valid placement for the building
     * @param x - x coordinate of where the building will be placed
     * @param y - y coordinate of where the building will be placed
     * @return object of type VampireCastleBuilding
     */
    @Override
    public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VampireCastleBuilding(x, y);
    }    

}
