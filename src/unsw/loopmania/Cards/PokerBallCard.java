package unsw.loopmania.Cards;

import java.util.List;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;

import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaApplication;
import unsw.loopmania.StrategyPattern.PlacementCheckEnemy;

/**
 * Can be thrown at an enemy to permanently entrance them
 */
public class PokerBallCard extends UtilityCard {
    LoopManiaWorld world;
    /*
     * Constructor for poker ball card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public PokerBallCard(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Building> buildings, LoopManiaWorld world) {
        super(x, y, new PlacementCheckEnemy(), buildings, world.getEnemies());
        this.world = world;
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "pokerBallCard";
    }

    /**
     * Apply the cards special ability
     * Catch the enemy at the coordinate
     * If multiple enemies are on the same tile then catch them all
     * @param x - the x coordinate the card is being played to
     * @param y - the y coordinate the card is being played to
     * @param character - the player character
     */
    @Override
    public void specialAbility(int x, int y, Character character) {
        List<BasicEnemy> caughtEnemies = new ArrayList<BasicEnemy>();
        for (BasicEnemy enemy : world.getEnemies()) {
            if (enemy.getX() == x && enemy.getY() == y) {
                character.addCaughtEnemy(enemy);
                caughtEnemies.add(enemy);
            }
        }
        for (BasicEnemy enemy : caughtEnemies) {
            // enemy will no longer be displayed in the frontend
            world.killEnemy(enemy);
        }
    }
}
