package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;

/**
 * Class representing a basic slug enemy entity
 */
public class SlugEnemy extends BasicEnemy {
    private static final int HEALTH = 20;
    private static final int ATTACK = 5;
    private static final int BATTLE_RADIUS = 2;
    private static final int SUPPORT_RADIUS = 2;
    private static final int EXPERIENCE = 20;

    public SlugEnemy(PathPosition position) {
        super(position, HEALTH, ATTACK, BATTLE_RADIUS, SUPPORT_RADIUS, EXPERIENCE);
    }

    /*
     * getter for type
     * @return String containing type
     */
    @Override
    public String getType() {
        return "slug";
    }
}
