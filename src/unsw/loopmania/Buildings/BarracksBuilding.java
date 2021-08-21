package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;

/**
 * Class representing a barracks building entity
 */
public class BarracksBuilding extends Building {
    /**
     * Constructor for a barracks building
     * @param x -  x coordinate of building on the map
     * @param y -  y coordinate of building on the map
     */
    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Check whether the character is on the same path tile as the barracks building
     * Spawn an allied soldier if that is the case
     */
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {
        if (character.getX() == this.getX() && character.getY() == this.getY()) {
            if (character.numOfAlliedSoldiers() < 4) {
                character.addAlliedSoldier();
            }  
        }
    }
    
    @Override
    public String getType() {
        return "barracks";
    }
}
