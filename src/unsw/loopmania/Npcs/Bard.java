package unsw.loopmania.Npcs;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

/**
 * Music based healer which has a chance of spawning at the character's
 * position once every cycle
 */
public class Bard extends UtilityNpc {
    private static final int RADIUS = 5;
    private static final boolean DESTROYABLE = true;
    private static final int HEAL = 5;

    /**
     * Constructor for a bard NPC
     * @param position - position of the NPC on the map
     */
    public Bard(PathPosition position) {
        super(position, RADIUS, DESTROYABLE);
    }

    /**
     * If the character is within the radius heal
     * by 5 HP (occurs once per movement)
     * @return boolean reflecting whether the NPC is to be removed
     */
    public boolean specialAbility(LoopManiaWorld world) {
        world.healCharacter(HEAL);
        return false;
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "bard";
    }
}
