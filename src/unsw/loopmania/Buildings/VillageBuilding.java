package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;

public class VillageBuilding extends Building {
    private boolean hasAdventurerGuild;
    /**
     * Create a villagebuilding
     * @param x - x coordinate on map
     * @param y - y coordinate on map
     */
    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.hasAdventurerGuild = false;
    }

    @Override
    public String getType() {
        return "village";
    }

    /**
     * If the character is passing over the village entity, restore health
     */
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {
        if (character.getX() == this.getX() && character.getY() == this.getY()) {
            character.changeHealth(20);
        }
    }

    /**
     * Upgrade village to have an adventurer guild
     */
    public void upgrade() {
        this.hasAdventurerGuild = true;
    }

    /**
     * Check if the village has an adventurer guild
     * @return boolean indicating whether the village has been upgraded
     */
    public boolean hasAdventurerGuild() {
        return this.hasAdventurerGuild;
    }
}
