package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;

public class DoggieEnemy extends BossEnemy {
    private static final int HEALTH = 20;
    private static final int ATTACK = 5;
    private static final int STUN = 10;
    private static final int BATTLE_RADIUS = 2;
    private static final int SUPPORT_RADIUS = 2;
    private static final int EXPERIENCE = 20;

    public DoggieEnemy(PathPosition position) {
        super(position, HEALTH, ATTACK, BATTLE_RADIUS, SUPPORT_RADIUS, EXPERIENCE);
        super.setStun(STUN);
    }

    public String getType() {
        return "doggie";
    }
}
