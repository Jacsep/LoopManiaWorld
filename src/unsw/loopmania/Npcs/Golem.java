package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

/**
 * Very slow moving stone golem has very high base stats and
 * a tiny support radius (only supports if on the same tile which would
 * place it within the enemy's attack radius anyway). Spawned from a rare card
 */
public class Golem extends FriendlyCombatNpc {
    private static final int HEALTH = 500;
    private static final int ATTACK = 20;
    private static final int SUPPORT_RADIUS = 0;
    private static final int MAX_MOVEMENT_COOLDOWN = 3;

    private int movementCooldown;
    /**
     * Constructor for a mushroom NPC
     * @param position - position of the NPC on the map
     */
    public Golem(PathPosition position) {
        super(position, HEALTH, ATTACK, SUPPORT_RADIUS);
        this.movementCooldown = MAX_MOVEMENT_COOLDOWN;
    }

    /*
     * Move the golem, includes the golems's slower movement int the form
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
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "golem";
    }
}
