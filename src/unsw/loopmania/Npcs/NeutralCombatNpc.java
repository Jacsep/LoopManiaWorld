package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

import unsw.loopmania.Helpers.Distance;

/**
 * Neutral Combat NPCs are combat NPCs which can fight all other moving
 * entities. They get attacked by
 * enemies and may attack the player character. They drop high
 * amounts of gold and experience when defeated and also have the same chance
 * as enemies to drop items.
 * They will all have a player attack radius of 1
 */
public abstract class NeutralCombatNpc extends CombatNpc {
    private final int RADIUS = 1;

    private int gold;
    private int experience;
    /**
     * Constructor for a neutral combat NPC
     * @param position - position of the NPC on the map
     * @param health - combat NPC's health
     * @param attack - combat NPC's attack
     */
    public NeutralCombatNpc (PathPosition position, int health, int attack, int gold, int experience) {
        super(position, health, attack);
        this.gold = gold;
        this.experience = experience;
    }

    /**
     * Check if a coordinate lies the utility
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within utility npc's radius
     */
    public boolean checkRadius(int x, int y) {
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= RADIUS) {
            return true;
        }
        return false;
    }

    /**
     * Get amount of gold awarded for defeating the npc
     * @return amount of gold 
     */
    public int getGold() {
        return gold;
    }

    /**
     * Get amount of experience awarded for defeating the npc
     * @return amount of experience 
     */
    public int getExperience() {
        return experience;
    }
}
