package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;

import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Items.Anduril;


public abstract class BossEnemy extends BasicEnemy {
    public BossEnemy (PathPosition position, int health, int attack, int battleRadius, int supportRadius, int experience) {
        super(position, health, attack, battleRadius, supportRadius, experience);
    }

    /**
     * Reduce the enemy's health by an amount (doesn't check if health is lower than 0)
     * @param damage - the amount of health lost
     * @return int containing the enemy's remaining health
     */
    @Override
    public int reduceHealth(int damage, Weapon weapon) {
        if (weapon instanceof Anduril) {
            Anduril anduril = (Anduril )weapon;
            damage *= anduril.bossDamageMultiplier();
        }
        return super.reduceHealth(damage, weapon);
    }

}
