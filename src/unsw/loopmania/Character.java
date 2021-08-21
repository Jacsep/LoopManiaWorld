package unsw.loopmania;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

import unsw.loopmania.Enemies.BasicEnemy;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private List<AlliedSoldier> alliedSoldiers;
    private List<BasicEnemy> caughtEnemies;
    private int maxHealth;
    private int health;
    private int baseDamage;
    private boolean isStunned;

    IntegerProperty soldierNumberProperty;
    IntegerProperty healthProperty;

    /**
     * Create a new character
     * Keeps track of a list of alliedSoldiers
     * Keeps track of a list of trancedEnemies through the staff weapon
     * @param position - position of the character on the orderedPath
     */
    public Character(PathPosition position) {
        super(position);
        this.alliedSoldiers = new ArrayList<>();
        this.caughtEnemies = new ArrayList<>();
        this.maxHealth = 100;
        this.health = 100;
        this.baseDamage = 5;
        this.isStunned = false;

        soldierNumberProperty = new SimpleIntegerProperty(0);
        healthProperty = new SimpleIntegerProperty(health);
    }

    /**
     * Change the health of the character by the appropriate amount
     * Health cannot exceed 100
     * @param amount - how much the character's health will be increased by
     */
    public void changeHealth(int amount) {
        this.health += amount;
        if (this.health > maxHealth)  {
            this.health = maxHealth;
        }
        healthProperty.set(health);
    }

    /**
     * Increase the character's maximum health and increase the 
     * character's current health by the same amount
     * @param amount - the amount the character's health will be increased by
     */
    public void increaseMaxHealth(int amount) {
        this.maxHealth += amount;
        this.health += amount;
        healthProperty.set(this.health);
    }

    /**
     * Getter for character's base damage
     * @return int containing character's base damage
     */
    public int getBaseDamage() {
        return this.baseDamage;
    }


    /**
     * Add a new allied soldier when the character passes through the barracks
     */
    public void addAlliedSoldier() {
        alliedSoldiers.add(new AlliedSoldier());
        soldierNumberProperty.set(alliedSoldiers.size());
    }

    /**
     * Remove an allied soldier from the list
     * @param soldier - the soldier to be removed
     */
    public void destroySoldier(AlliedSoldier soldier) {
        this.alliedSoldiers.remove(soldier);
    }
    
    /**
     * Return a list of allied Sodliers
     * @return
     */
    public List<AlliedSoldier> getListOfAllies() {
        return this.alliedSoldiers;
    }
    
    /**
     * Return the number of allied soldiers
     * @return int containing the number of allied soldiers
     */
    public int numOfAlliedSoldiers() {
        return alliedSoldiers.size();
    }

    /**
     * Getter for character's health
     * @return int containing the character's health
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Return integer property containing the character's amount of health for frontend
     * @return integer property
     */
    public IntegerProperty getHealthProperty() {
        return healthProperty;
    }

    /**
     * Getter for character's stun status
     * @return boolean indicating true if the character is stunned and false otherwise
     */
    public boolean getIsStunned() {
        return this.isStunned;
    }

    /**
     * Setter for character stun status
     * @param isStunned - boolean containing whether the character is to be
     * stunned
     */
    public void setStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    /**
     * @return the type of entity
     */
    public String getType() {
        return "character";
    }

    /**
     * Add a caught enemy to the character
     * @param enemy - the enemy to be inprisoned
     */
    public void addCaughtEnemy(BasicEnemy enemy) {
        enemy.setCaught();
        caughtEnemies.add(enemy);
    }

    /**
     * Get the list of all caught enemies
     * @return list of all caught enemies
     */
    public List<BasicEnemy> getCaughtEnemies() {
        return caughtEnemies;
    }

    /**
     * Remove all allied soldiers
     */
    public void resetAlliedSoldiers() {
        for (Iterator<AlliedSoldier> iter = alliedSoldiers.iterator(); iter.hasNext(); ) {
            iter.next();
            iter.remove();
        }
    }

    public void fullHeal() {
        this.health = maxHealth;
        healthProperty.set(this.health);
    }
}
