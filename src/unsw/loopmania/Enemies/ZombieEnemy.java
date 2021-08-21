package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;

public class ZombieEnemy extends BasicEnemy {
    private static final int HEALTH = 30;
    private static final int ATTACK = 15;
    private static final int TURN = 20;
    private static final int BATTLE_RADIUS = 3;
    private static final int SUPPORT_RADIUS = 3;
    private static final int EXPERIENCE = 30;
    private static final int MAX_MOVEMENT_COOLDOWN = 1;

    private int movementCooldown;

    public ZombieEnemy(PathPosition position) {
        super(position, HEALTH, ATTACK, BATTLE_RADIUS, SUPPORT_RADIUS, EXPERIENCE);
        super.setTurn(TURN);
        
        movementCooldown = MAX_MOVEMENT_COOLDOWN;
    }

    /*
     * Move the enemy, includes the zombie's slower movement int the form
     * of a movement cooldown
     */
    @Override
    public void move() {
        if (movementCooldown > 0) {
            movementCooldown --;
        } else {
            super.move();
            movementCooldown = MAX_MOVEMENT_COOLDOWN;
        }
        
    }

    /*
     * getter for type
     * @return String containing type
     */
    @Override
    public String getType() {
        return "zombie";
    }
}
