package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

/**
 * Randomly spawning neutral combat NPC which moves back
 * forth and has the same statline as an allied soldier
 */
public class Bandit extends NeutralCombatNpc {
    private static final int HEALTH = 35;
    private static final int ATTACK = 10;
    private static final int GOLD = 70;
    private static final int EXPERIENCE = 60;

    /**
     * Constructor for a bandit NPC
     * @param position - position of the NPC on the map
     */
    public Bandit(PathPosition position) {
        super(position, HEALTH, ATTACK, GOLD, EXPERIENCE);
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "bandit";
    }
}
