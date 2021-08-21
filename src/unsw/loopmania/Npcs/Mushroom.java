package unsw.loopmania.Npcs;

import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import unsw.loopmania.Enemies.BasicEnemy;

/**
 * Mushroom moves in a straight line however bounces off
 * enemies. Coming into contact with the player character will
 * increase the character's health permanently and consume the mushroom
 */
public class Mushroom extends UtilityNpc {
    private static final int RADIUS = 1;
    private static final boolean DESTROYABLE = false;

    private static final int HEALTH_INCREASE = 10;

    // list of all enemies in the world
    private List<BasicEnemy> enemies;
    // the direction which the mushroom is moving true is down and false is up
    private boolean direction;

    /**
     * Constructor for a mushroom NPC
     * @param position - position of the NPC on the map
     */
    public Mushroom(PathPosition position, List<BasicEnemy> enemies) {
        super(position, RADIUS, DESTROYABLE);
        this.enemies = enemies;
        this.direction = true;
    }

    /**
     * Move in a straight line until the next tile has an enemy
     * if so, reverse the direction of the mushroom
     */
    @Override
    public void move() {
        int nextX;
        int nextY;
        if (direction) {
            nextX = this.getMoveDownX();
            nextY = this.getMoveDownY();
        } else {
            nextX = this.getMoveUpX();
            nextY = this.getMoveUpY();
        }
        for (BasicEnemy enemy : enemies) {
            if (nextX == enemy.getX() && nextY == enemy.getY()) {
                direction = !direction;
            }
        }
        if (direction) {
            this.moveDownPath();
        } else {
            this.moveUpPath();
        }
    }

    /**
     * If the character is within the radius, increase the character's maximum
     * health by 10 and destroy the mushroom
     * 
     * @return boolean reflecting whether the NPC is to be removed
     */
    public boolean specialAbility(LoopManiaWorld world) {
        world.increaseCharacterHealth(HEALTH_INCREASE);
        return true;
    }

    /*
     * getter for object type
     * @return string containing the object type
     */
    @Override
    public String getType() {
        return "mushroom";
    }
}
