package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;
/**
 * Neutral entity which spawns from a dragon's lair
 * (this is a baby dragon so its attacks are rather weak)
 * Its also lazy so its radius is very small
 */
public class Dragon extends NeutralCombatNpc {
    private static final int HEALTH = 300;
    private static final int ATTACK = 20;
    private static final int GOLD = 200;
    private static final int EXPERIENCE = 300;

    /**
     * Constructor for a bandit NPC
     * @param position - position of the NPC on the map
     */
    public Dragon(PathPosition position) {
        super(position, HEALTH, ATTACK, GOLD, EXPERIENCE);
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "dragon";
    }
}
