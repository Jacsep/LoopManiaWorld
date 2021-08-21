package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

import unsw.loopmania.Helpers.Distance;

/**
 * An invulnerable slow marking (moving) utility npc
 * who's shaky home internet connection causes enemies within
 * his radius to be be unable to attack or support
 * Only one will spawn at a random location at the beginning of the game
 */
public class MatthiasPierre extends Npc {
    private static final int RADIUS = 4;
    private static final int MAX_MOVEMENT_COOLDOWN = 2;

    private int movementCooldown;

    /**
     * Constructor for a matthias pierre NPC
     * @param position - position of the NPC on the map
     */
    public MatthiasPierre(PathPosition position) {
        super(position);
        this.movementCooldown = MAX_MOVEMENT_COOLDOWN;
    }

    /*
     * Move matthias, includes his slower movement int the form
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

    /**
     * Check if a coordinate lies the utility
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within utility npc's radius
     */
    public boolean checkRadius(int x, int y) {
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= RADIUS) {
            return true;
        }
        return false;
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "matthiasPierre";
    }
}
