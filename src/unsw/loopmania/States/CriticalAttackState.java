package unsw.loopmania.States;

import unsw.loopmania.Helpers.Chance;

public class CriticalAttackState implements AttackState {
    private int attack;
    private int storedCrits;
    private int minCritDamage;
    private int maxCritDamage;

    public CriticalAttackState(int attack, int minCritDamage, int maxCritDamage, int minStoredCrits, int maxStoredCrits) {
        this.attack = attack;
        this.minCritDamage = minCritDamage;
        this.maxCritDamage = maxCritDamage;

        storedCrits = Chance.intRangeChance(minStoredCrits, maxStoredCrits);
    }
    /**
     * Attack strategy to switching between normal attacking state and critical
     * attack states
     * @param hasShield - boolean to indicate whether a shield is equipped
     * @return integer containing damage or -1 if the state needs to change first
     */
    public int attack(boolean hasShield) {
        if (storedCrits <= 0) {
            return -1;
        }
        int damage = this.attack + Chance.intRangeChance(minCritDamage, maxCritDamage);
        storedCrits --;
        return damage;
    }
}
