package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

import unsw.loopmania.Helpers.Distance;

/**
 * Friendly combat NPCs are combat NPCs which only fight basic enemies
 * They will fight enemies which they enter the radius of and can support
 * the player character (they will joing the friendly player if also within
 * the enemy's attack radius)
 */
public abstract class FriendlyCombatNpc extends CombatNpc {
    private int radius;
    /**
     * Constructor for a friendly combat NPC
     * @param position - position of the NPC on the map
     * @param health - combat NPC's health
     * @param attack - combat NPC's attack
     */
    public FriendlyCombatNpc (PathPosition position, int health, int attack, int radius) {
        super(position, health, attack);
        this.radius = radius;
    }

    /*
     * Check if a coordinate lies within an friendly combat npc's support radius (doesn't check if the coordinate is inside
     * the world)
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within the friendly combat npc's
     */
    public boolean checkSupportRadius(int x, int y) {
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= radius) {
            return true;
        }
        return false;
    }
}
