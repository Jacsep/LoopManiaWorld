package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Helpers.Distance;

/**
 * Class representing a campfire entity
 */

public class CampfireBuilding extends Building {
    private int radius;

    /**
     * Create an instance of a campfirebuilding
     * @param x
     * @param y
     */
    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.radius = 5;
    }

    /**
     * Get the type in the form of a string
     */
    @Override
    public String getType() {
        return "campfire";
    }

    /**
     * @return whether the character is within the radius of the campfire
     */
    public boolean checkCharacterInRadius(Character character) {
        if (Distance.calculate(character.getX(), character.getY(), this.getX(), this.getY()) <= radius) {
            return true;
        }
        return false;
    }

    @Override
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {
    }
}
