package unsw.loopmania.Enemies;

import org.javatuples.Triplet;
import java.util.List;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.PathPosition;

import unsw.loopmania.Helpers.Chance;
import unsw.loopmania.Helpers.Distance;

import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Items.Stake;


public class VampireEnemy extends BasicEnemy {
    private static final int HEALTH = 50;
    private static final int ATTACK = 30;
    private static final int CRIT = 35;
    private static final int MIN_CRIT_DAMAGE = 0;
    private static final int MAX_CRIT_DAMAGE = 10;
    private static final int MIN_STORED_CRITS = 1;
    private static final int MAX_STORED_CRITS = 3;
    private static final int BATTLE_RADIUS = 3;
    private static final int SUPPORT_RADIUS = 5;
    private static final int EXPERIENCE = 50;

    private List<Building> buildings;

    public VampireEnemy(PathPosition position, List<Building> buildings) {
        super(position, HEALTH, ATTACK, BATTLE_RADIUS, SUPPORT_RADIUS, EXPERIENCE);
        super.setCrit(CRIT);
        super.setMinCritDamage(MIN_CRIT_DAMAGE);
        super.setMaxCritDamage(MAX_CRIT_DAMAGE);
        super.setMinStoredCrits(MIN_STORED_CRITS);
        super.setMaxStoredCrits(MAX_STORED_CRITS);
        super.resetAttackState();

        this.buildings = buildings;
    }

    /*
     * Move the enemy, includes vampire's movement away from campfires
     */
    @Override
    public void move() {
        Triplet<Integer, Integer, Double> campCoords = getClosestCampfire();
        if (campCoords != null) {
            int campX = campCoords.getValue0();
            int campY = campCoords.getValue1();
            double distance = campCoords.getValue2();
            int xDown = super.getMoveDownX();
            int yDown = super.getMoveDownY();
            int xUp = super.getMoveUpX();
            int yUp = super.getMoveUpY();

            double distanceDown = Distance.calculate(xDown, yDown, campX, campY);
            double distanceUp = Distance.calculate(xUp, yUp, campX, campY);

            if (distanceDown > distanceUp && distanceDown > distance) {
                super.moveDownPath();
            } else if (distanceUp > distanceDown && distanceUp > distance) {
                super.moveUpPath();
            } else if (distanceDown == distanceUp && distanceDown > distance) {
                boolean var = Chance.binomialChance(50);
                if (var) {
                    super.moveDownPath();
                } else {
                    super.moveUpPath();
                }
            }
        } else {
            super.move();
        }
    }

    /**
     * Determine the nearest campfire to the vampire
     * @return a triplet containing the closest campfire's x coord, y coord and the distance
     */
    private Triplet<Integer, Integer, Double> getClosestCampfire() {
        int x = super.getX();
        int y = super.getY();
        
        double minDistance = -1;
        Triplet<Integer, Integer, Double> result = null;

        for (Building building : buildings) {
            if (building.getType().equals("campfire")) {
                double distance = Distance.calculate(x, y, building.getX(), building.getY());
                if (distance < minDistance || minDistance == -1) {
                    minDistance = distance;
                    result = new Triplet<>(building.getX(), building.getY(), distance);
                }
            }
        }
        return result;
    }

    /**
     * Reduce the enemy's health by an amount (doesn't check if health is lower than 0)
     * @param damage - the amount of health lost
     * @return int containing the enemy's remaining health
     */
    public int reduceHealth(int damage, Weapon weapon) {
        if (weapon instanceof Stake) {
            Stake stake = (Stake )weapon;
            damage += stake.criticalDamageVampire();
        }
        return super.reduceHealth(damage, weapon);
    }

    /*
     * getter for type
     * @return String containing type
     */
    @Override
    public String getType() {
        return "vampire";
    }

}
