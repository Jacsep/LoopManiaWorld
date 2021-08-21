package unsw.loopmania.Npcs;

import unsw.loopmania.PathPosition;

/**
 * Combat moving entities NPC are able to fight other moving entities
 * They are also able to engage in battles not containing the player character
 */
public abstract class CombatNpc extends Npc {
    private int health;
    private int attack;

    /**
     * Constructor for a combat NPC
     * @param position - position of the NPC on the map
     * @param health - combat NPC's health
     * @param attack - combat NPC's attack
     */
    public CombatNpc (PathPosition position, int health, int attack) {
        super(position);
        this.health = health;
        this.attack = attack;
    }

    /**
     * Get amount of damage which the combat NPC deals
     * @return int containing damage value
     */
    public int attack() {
        return attack;
    }

    /**
     * Reduce the combat NPC's health by an amount (doesn't check if health is below 0)
     * @param damage - the amount of health the be lost
     * @return int containing the combat NPC's remaining health value
     */
    public int reduceHealth(int damage) {
        health -= damage;
        return health;
    }
}
