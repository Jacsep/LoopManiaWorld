package unsw.loopmania.Cards;

import java.util.List;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Buildings.Building;

import unsw.loopmania.Enemies.BasicEnemy;

import unsw.loopmania.StrategyPattern.PlacementStrategy;
import unsw.loopmania.StaticEntity;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    private PlacementStrategy strategy;
    private List<Building> buildings;
    private List<BasicEnemy> enemies;

    /**
     * Constructor for card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y, PlacementStrategy strategy, List<Building> buildings, List<BasicEnemy> enemies) {
        super(x, y);
        this.strategy = strategy;
        this.buildings = buildings;
        this.enemies = enemies;
    }

    /**
     * Based on whether the card is meant to be put on a tile or non-tile based on the coordinates of
     * which tile the user tried to put it on
     * @param x - x value to be tested
     * @param y - y value to be tested
     * @param orderedPath - list of pairs containing tiles which have a path in order
     * @return boolean containing whether or not the building is allowed to be placed here
     */
    public boolean validPlacement(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        return strategy.doPlacementCheck(x, y, orderedPath, buildings, enemies);
    }
}
