package unsw.loopmania.States;

import unsw.loopmania.Helpers.Chance;

public class NormalAttackState implements AttackState {
    private int attack;
    private int crit;

    public NormalAttackState(int attack, int crit) {
        this.attack = attack;
        this.crit = crit;
    }

    /**
     * Attack strategy to switching between normal attacking state and critical
     * attack states
     * @param hasShield - boolean to indicate whether a shield is equipped
     * @return integer containing damage or -1 if the state needs to change first
     */
    public int attack(boolean hasShield) {
        int damage = this.attack;
        int critChance = this.crit;

        if (hasShield) {
            critChance = (int )Math.round(critChance * 0.4);
        }
        if (Chance.binomialChance(critChance)) {
            damage = -2;
        }
        return damage;
    }
}
