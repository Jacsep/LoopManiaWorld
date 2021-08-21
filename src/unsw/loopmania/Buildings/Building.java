package unsw.loopmania.Buildings;
import unsw.loopmania.StaticEntity;


import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;


public abstract class Building extends StaticEntity {
    /**
     * Construct a building entity
     * @param x -  x coordinate on map
     * @param y -  y coordinate on map
     */
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

    }

    /**
     * Return the type of building
     * @return
     */
    public abstract String getType();

    /**
     * Use the special ability to the building if the conditions are met
     * @param character
     * @param enemyList
     */
    abstract public void specialAbility(Character character, List<BasicEnemy> enemyList);

}
