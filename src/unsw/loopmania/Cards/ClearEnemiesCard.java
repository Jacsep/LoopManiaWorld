package unsw.loopmania.Cards;

import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Character;
import unsw.loopmania.StrategyPattern.PlacementCheckAnywhere;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;

/**
 * Remove all current enemies from the world
 */
public class ClearEnemiesCard extends UtilityCard {
    List<BasicEnemy> enemies;
    /*
     * Constructor for clear enemies card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public ClearEnemiesCard(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Building> buildings, List<BasicEnemy> enemies) {
        super(x, y, new PlacementCheckAnywhere(), buildings, enemies);
        this.enemies = enemies;
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "clearEnemiesCard";
    }

    /**
     * Apply the card's special ability
     * @param x - the x coordinate the card is being played to
     * @param y - the y coordinate the card is being played to
     * @param character - the player character
     */
    @Override
    public void specialAbility(int x, int y, Character character) {
        for (BasicEnemy enemy : enemies) {
            enemy.destroy();
        }
        enemies.clear();
    }
}
