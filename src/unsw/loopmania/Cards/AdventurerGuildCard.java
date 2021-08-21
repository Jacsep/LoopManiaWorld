package unsw.loopmania.Cards;

import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Character;
import unsw.loopmania.StrategyPattern.PlacementCheckVillage;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Enemies.BasicEnemy;

/**
 * Can be placed on an existing village to upgrade it
 * When upgraded, the village spawns an adventurer when passing through
 */
public class AdventurerGuildCard extends UtilityCard {
    List<Building> buildings;
    /*
     * Constructor for an adventurer guild card
     * @param x - x coordinate of the card on the board
     * @param y - y coordinate of the card on the board
     */
    public AdventurerGuildCard(SimpleIntegerProperty x, SimpleIntegerProperty y, List<Building> buildings, List<BasicEnemy> enemies) {
        super(x, y, new PlacementCheckVillage(), buildings, enemies);
        this.buildings = buildings;
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "adventurerGuildCard";
    }

    /**
     * Apply the cards special ability
     * @param x - the x coordinate the card is being played to
     * @param y - the y coordinate the card is being played to
     * @param character - the player character
     */
    @Override
    public void specialAbility(int x, int y, Character character) {
        for (Building building : buildings) {
            if (building instanceof VillageBuilding && building.getX() == x && building.getY() == y) {
                ((VillageBuilding )building).upgrade();
                break;
            }
        }
    }
}
