package unsw.loopmania;

/**
 * Class representing an alliedSoldier entity
 */
public class AlliedSoldier {
    private int health;
    private int baseDamage;
    
    /**
     * Construct an allied soldier with a preset health and attack damage
     */
    public AlliedSoldier() {
        this.health = 35;
        this.baseDamage = 10;
    }

    /** 
     * @return the amount of health the soldier has remaining
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Alter the soldier's health when they are attacked
     * @param amount
     */
    public void takeDamage(int amount) {
        this.health = this.health + amount;
    }

    /**
     * @return the amount of the damage the soldier does
     */
    public int getBaseDamage() {
        return this.baseDamage;
    }
}
