package unsw.loopmania.Enemies;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Helpers.Chance;
import unsw.loopmania.Helpers.Distance;

import unsw.loopmania.Items.Weapon;

import unsw.loopmania.States.AttackState;
import unsw.loopmania.States.CriticalAttackState;
import unsw.loopmania.States.NormalAttackState;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity {
    private static final int DEFAULT_CRIT = 0;
    private static final int DEFAULT_MIN_CRIT_DAMAGE = 0;
    private static final int DEFAULT_MAX_CRIT_DAMAGE = 0;
    private static final int DEFAULT_MIN_STORED_CRITS = 0;
    private static final int DEFAULT_MAX_STORED_CRITS = 0;
    private static final int DEFAULT_TURN = 0;
    private static final int DEFAULT_STUN = 0;

    private int health;
    private int attack;
    private int battleRadius;
    private int supportRadius;
    private int experience;

    private int trancedRoundsLeft;
    private boolean isCaught;

    private int crit = DEFAULT_CRIT;
    private int minCritDamage = DEFAULT_MIN_CRIT_DAMAGE;
    private int maxCritDamage = DEFAULT_MAX_CRIT_DAMAGE;
    private int minStoredCrits = DEFAULT_MIN_STORED_CRITS;
    private int maxStoredCrits = DEFAULT_MAX_STORED_CRITS;

    private int turn = DEFAULT_TURN;

    private int stun = DEFAULT_STUN;

    private AttackState attackState;

    /**
     * Constructor for a BasicEnemy
     * @param position - position of the enemy on the map
     * @param health - amount of health
     * @param attack - amount of damage it does with one attack
     * @param battleRadius - the battle radius of the enemy
     * @param supportRadius - the support radius of the enemy
     * @param experience - amount of experience given to the character is defeated
     */
    public BasicEnemy(PathPosition position, int health, int attack, int battleRadius, int supportRadius, int experience) {
        super(position);

        this.health = health;
        this.attack = attack;
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.experience = experience;
        this.trancedRoundsLeft = 0;
        this.isCaught = false;

        resetAttackState();
    }

    /*
     * move the enemy with a 25% chance to move up, 25% chance to move down
     * and 50% to do nothing
     */
    public void move(){
        int directionChoice = Chance.intRangeChance(1, 100);
        if (directionChoice <= 25) {
            moveUpPath();
        } else if (directionChoice > 75){
            moveDownPath();
        }
    }

    /**
     * Trance the enemy for a duraction between 1 and 3 (inclusive)
     * attacks
     */
    public void randomTrancedRounds() {
        this.trancedRoundsLeft = Chance.intRangeChance(1, 3);
    }

    /**
     * Get the number of tranced rounds remaining
     * @return integer containing value of remaining tranced rounds
     */
    public int trancedRoundsRemaning() {
        return this.trancedRoundsLeft;
    }

    /**
     * Reduce the amount of tranced rounds by one if the enemy has not
     * been caught using a poker ball
     */
    public void decrementTrancedRounds() {
        if (!isCaught) {
            this.trancedRoundsLeft = this.trancedRoundsLeft - 1;
        }
    }

    /*
     * Get damage as dictated by the enemys attack state
     * @return int containing damage
     */
    public int attack(boolean hasShield) {
        int damage = attackState.attack(hasShield);
        while (damage < 0) {
            if (damage == -1) {
                attackState = new NormalAttackState(attack, crit);
                damage = attackState.attack(hasShield);
            } 
            if (damage == -2) {
                attackState = new CriticalAttackState(attack, minCritDamage, maxCritDamage, minStoredCrits, maxStoredCrits);
                damage = attackState.attack(hasShield);
            } 
        }
        return damage;
    }

    /*
     * Generate whether the soldier will turn into a zombie
     * @return boolean containin whether the soldier will turn
     */
    public boolean turn() {
        return Chance.binomialChance(turn);
    }

    /*
     * Generate whether the soldier will be stunned
     * @return boolean containing whether the character will be stunned
     */
    public boolean stun() {
        return Chance.binomialChance(stun);
    }

    /*
     * Check if a coordinate lies within an enemy's battle radius (doesn't check if the coordinate is inside
     * the world)
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within the enemy's battle radius
     */
    public boolean checkBattleRadius(int x, int y) {
        
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= battleRadius) {
            return true;
        }
        return false;
    }

    /*
     * Check if a coordinate lies within an enemy's support radius (doesn't check if the coordinate is inside
     * the world)
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return boolean containing whether or not the coordinate pair lies within the enemy's support radius
     */
    public boolean checkSupportRadius(int x, int y) {
        if (Distance.calculate(super.getX(), super.getY(), x, y) <= supportRadius) {
            return true;
        }
        return false;
    }

    /**
     * Reduce the enemy's health by an amount (doesn't check if health is lower than 0)
     * @param damage - the amount of health lost
     * @param weapon - the weapon used (does nothing for non-vampires)
     * @return int containing the enemy's remaining health
     */
    public int reduceHealth(int damage, Weapon weapon) {
        health -= damage;
        return health;
    }

    /**
     * reset the attack state to normal attack state, for use
     * after setting crit chance
     */
    protected void resetAttackState() {
        attackState = new NormalAttackState(attack, crit);
    }

    /**
     * Getter for enemy's health
     * @return int containing enemy's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter for enemy's health
     * @param health - int containing enemy's health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Getter for enemy's crit chance
     * @return int containing enemy's health
     */
    public int getCrit() {
        return crit;
    }

    /**
     * Setter for enemy's crit chance
     * @param crit - int containing enemy's crit chance percentage
     */
    protected void setCrit(int crit) {
        this.crit = crit;
    }

    /**
     * Getter for enemy's min crit damage
     * @return int containing enemy's health
     */
    public int getMinCritDamage() {
        return minCritDamage;
    }

    /**
     * Setter for enemy's minimum crit damage
     * @param minCritDamage - int containing enemy's minimum crit damage
     */
    protected void setMinCritDamage(int minCritDamage) {
        this.minCritDamage = minCritDamage;
    }

    /**
     * Getter for enemy's max crit damage
     * @return int containing enemy's health
     */
    public int getMaxCritDamage() {
        return maxCritDamage;
    }

    /**
     * Setter for enemy's maximum crit damage
     * @param maxCritDamage - int containing enemy's maximum crit damage
     */
    protected void setMaxCritDamage(int maxCritDamage) {
        this.maxCritDamage = maxCritDamage;
    }

    /**
     * Getter for enemy's min stored crits
     * @return int containing enemy's health
     */
    public int getMinStoredCrits() {
        return minStoredCrits;
    }

    /**
     * Setter for enemy's minimum stored crits
     * @param minStoredCrits - int containing enemy's minimum stored crits
     */
    protected void setMinStoredCrits(int minStoredCrits) {
        this.minStoredCrits = minStoredCrits;
    }

    /**
     * Getter for enemy's max stored crits
     * @return int containing enemy's health
     */
    public int getMaxStoredCrits() {
        return maxStoredCrits;
    }

    /**
     * Setter for enemy's maximum stored crits
     * @param minStoredCrits - int containing enemy's maximum stored crits
     */
    protected void setMaxStoredCrits(int maxStoredCrits) {
        this.maxStoredCrits = maxStoredCrits;
    }

    /**
     * Getter for enemy's turn chance
     * @return int containing turn chance
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Setter for enemy's turn chance
     * @param turn - int containing enemy's turn chance percentage
     */
    protected void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Getter for enemy's stun chance
     * @return int containing enemy's stun chance
     */
    public int getStun() {
        return stun;
    }

    /**
     * Setter for enemy's stun chance
     * @param stun - int containing enemy's stun chance percentage
     */
    protected void setStun(int stun) {
        this.stun = stun;
    }

    /**
     * Getter for enemy's experience drop
     * @return int containing experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Catch the enemy (enemy will be permanently tranced)
     */
    public void setCaught() {
        this.trancedRoundsLeft = 1;
        this.isCaught = true;
    }

}
