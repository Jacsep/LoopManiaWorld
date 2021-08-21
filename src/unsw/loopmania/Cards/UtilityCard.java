package unsw.loopmania.Cards;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Character;

import unsw.loopmania.StrategyPattern.PlacementStrategy;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;

public abstract class UtilityCard extends Card {
    /**
     * Constructor for Building Card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     * @param strategy - the placement strategy of the card
     */
    public UtilityCard(SimpleIntegerProperty x, SimpleIntegerProperty y, PlacementStrategy strategy, List<Building> buildings, List<BasicEnemy> enemies) {
        super(x, y, strategy, buildings, enemies);
    }

    /**
     * Apply the cards special ability
     * @param x - the x coordinate the card is being played to
     * @param y - the y coordinate the card is being played to
     * @param character - the player character
     */
    public abstract void specialAbility(int x, int y, Character character);
}
