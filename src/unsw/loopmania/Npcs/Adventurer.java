package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

/**
 * Adventurer is spawned from an adventurer guild upgraded village
 * they have better gear than allied soldiers (moderately high stats)
 * and have a large support radius
 */
public class Adventurer extends FriendlyCombatNpc {
    private static final int HEALTH = 60;
    private static final int ATTACK = 20;
    private static final int SUPPORT_RADIUS = 4;

    /**
     * Constructor for a bandit NPC
     * @param position - position of the NPC on the map
     */
    public Adventurer(PathPosition position) {
        super(position, HEALTH, ATTACK, SUPPORT_RADIUS);
    }

        /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "adventurer";
    }
}
