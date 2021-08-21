package unsw.loopmania.Enemies;

import java.util.List;

import unsw.loopmania.PathPosition;

public class ElanMuskeEnemy extends BossEnemy {
    private static final int HEALTH = 250;
    private static final int ATTACK = 40;
    private static final int HEAL = 10;
    private static final int BATTLE_RADIUS = 2;
    private static final int SUPPORT_RADIUS = 2;
    private static final int EXPERIENCE = 250;

    public ElanMuskeEnemy(PathPosition position) {
        super(position, HEALTH, ATTACK, BATTLE_RADIUS, SUPPORT_RADIUS, EXPERIENCE);
    }

    public String getType() {
        return "elanMuske";
    }

    public void healEnemies(List<BasicEnemy> enemies) {
        for (BasicEnemy enemy : enemies) {
            if (enemy != this) {
                enemy.reduceHealth(-HEAL, null);
            }
        }
    }
}
