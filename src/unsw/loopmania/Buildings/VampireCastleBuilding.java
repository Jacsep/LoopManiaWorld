package unsw.loopmania.Buildings;

import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building {
    private int roundSpawned;
    private int numOfVampiresToSpawn;
    /**
     * Create a vampire castle building
     * @param x - x coordinate on the map
     * @param y - y coordinate on the map
     */
    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.roundSpawned = 0;
    }

    @Override
    public String getType() {
        return "vampireCastle";
    }

    /**
     * Determine the random number of vampires to spawn and store this number
     */
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {
        Random rand = new Random();
        int numToSpawn = rand.nextInt(3) + 1;
        this.numOfVampiresToSpawn = numToSpawn;
    }

    /**
     * Keep track of the round number in which the vampire castle was spawned
     * @param roundSpawned
     */
    public void setRoundSpawned(int roundSpawned) {
        this.roundSpawned = roundSpawned;
    }
    public int getRoundSpawned() {
        return this.roundSpawned;
    }

    public int getNumVampiresSpawn() {
        return this.numOfVampiresToSpawn;
    }

}
