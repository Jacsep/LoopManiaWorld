package unsw.loopmania.Npcs;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import unsw.loopmania.Helpers.Distance;

public abstract class UtilityNpc extends Npc {
    private int radius;
    private boolean destroyable;

    /**
     * Constructor for a utility NPC
     * @param position - position of the NPC on the map
     * @param radius - the utility NPC's effective radius
     * @param destroyable - whether or not the utility npc can be destroyed by enemies
     */
    public UtilityNpc (PathPosition position, int radius, boolean destroyable) {
        super(position);
        this.radius = radius;
        this.destroyable = destroyable;
    }

    /**
     * Apply the utility NPC's special ablity to the world
     * @return boolean reflecting the success of the ability
     */
    public abstract boolean specialAbility(LoopManiaWorld world);

    /**
     * Check if a coordinate lies the utility
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within utility npc's radius
     */
    public boolean checkRadius(int x, int y) {
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= radius) {
            return true;
        }
        return false;
    }

    /**
     * Get whether or not the NPC is able to be destroyed by enemies
     * @return boolean containing the value of the utility NPC's destroyable attribute
     */
    public boolean getDestroyable() {
        return destroyable;
    }
}
