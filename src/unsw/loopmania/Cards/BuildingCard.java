package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Buildings.Building;

import unsw.loopmania.StrategyPattern.PlacementStrategy;

public abstract class BuildingCard extends Card {
    /**
     * Constructor for Building Card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     * @param strategy - the placement strategy of the card
     */
    public BuildingCard(SimpleIntegerProperty x, SimpleIntegerProperty y, PlacementStrategy strategy) {
        super(x, y, strategy, null, null);
    }

    /**
     * Create and return an object of the corresponding building
     * @pre the combination of x and y is a placement for the building
     * @param x - x coordinate of where the building will be placed
     * @param y - y coordinate of where the building will be placed
     * @return object of type Building
     */
    abstract public Building createBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y);
}
