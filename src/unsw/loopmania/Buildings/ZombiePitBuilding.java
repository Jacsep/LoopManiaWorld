package unsw.loopmania.Buildings;

import java.util.List;
import java.util.Random;


import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;

public class ZombiePitBuilding extends Building {
    private int numOfZombiesToSpawn;
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.numOfZombiesToSpawn = 0;
    }
    
    /**
     * Determine a random number of zombies to spawn in the next turn and store that
     */
    public void specialAbility(Character character, List<BasicEnemy> enemyList) {
        Random rand = new Random();
        int numToSpawn = rand.nextInt(2) + 1;
        this.numOfZombiesToSpawn = numToSpawn;
    }

    public int getNumZombiesSpawn() {
        return this.numOfZombiesToSpawn;
    }

    @Override
    public String getType() {
        return "zombiePit";
    }
}
