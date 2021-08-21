package test;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import java.util.stream.Stream;

import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Enemies.SlugEnemy;
import unsw.loopmania.Enemies.VampireEnemy;
import unsw.loopmania.Enemies.ZombieEnemy;

public class EnemyTest {
    private int SLUG_HEALTH = 20;
    private int ZOMBIE_HEALTH = 30;
    private int VAMPIRE_HEALTH = 50;
    
    private int SLUG_ATTACK = 5;
    private int ZOMBIE_ATTACK = 15;
    private int VAMPIRE_ATTACK = 30;

    private int SLUG_CRIT = 0;
    private int ZOMBIE_CRIT = 0;
    private int VAMPIRE_CRIT = 35;

    private int SLUG_MIN_CRIT_DAMAGE = 0;
    private int VAMPIRE_MIN_CRIT_DAMAGE = 0;
    private int ZOMBIE_MIN_CRIT_DAMAGE = 0;

    private int SLUG_MAX_CRIT_DAMAGE = 0;
    private int VAMPIRE_MAX_CRIT_DAMAGE = 10;
    private int ZOMBIE_MAX_CRIT_DAMAGE = 0;

    private int SLUG_MIN_STORED_CRITS = 0;
    private int VAMPIRE_MIN_STORED_CRITS = 1;
    private int ZOMBIE_MIN_STORED_CRITS = 0;

    private int SLUG_MAX_STORED_CRITS = 0;
    private int VAMPIRE_MAX_STORED_CRITS = 3;
    private int ZOMBIE_MAX_STORED_CRITS = 0;

    private int SLUG_TURN = 0;
    private int ZOMBIE_TURN = 20;
    private int VAMPIRE_TURN = 0;

    private int SLUG_EXPERIENCE = 20;
    private int ZOMBIE_EXPERIENCE = 30;
    private int VAMPIRE_EXPERIENCE = 50;

    /*
     * 1. Test that default movement works as expected
     */
    @Test
    public void testDefaultMovement() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition position = new PathPosition(indexInPath, path);
        SlugEnemy slug = new SlugEnemy(position);

        slug.moveDownPath();
        assertEquals(2, position.getX().get());
        assertEquals(1, position.getY().get());

        slug.moveUpPath();
        slug.moveUpPath();
        assertEquals(1, position.getX().get());
        assertEquals(2, position.getY().get());

        slug.moveDownPath();
        assertEquals(1, position.getX().get());
        assertEquals(1, position.getY().get());

        int counter = 0;
        int x = position.getXCoord();
        int y = position.getYCoord();
        int xDown = position.getMoveDownX();
        int yDown = position.getMoveDownY();
        int xUp = position.getMoveUpX();
        int yUp = position.getMoveUpY();

        // test that the slug moves either up or down the path or doesn't move
        while (counter < 100) {
            slug.move();
            int slugX = position.getXCoord();
            int slugY = position.getYCoord();

            assertTrue(slugX == x || slugX == xDown || slugX == xUp);
            assertTrue(slugY == y || slugY == yDown || slugY == yUp);

            x = position.getXCoord();
            y = position.getYCoord();
            xDown = position.getMoveDownX();
            yDown = position.getMoveDownY();
            xUp = position.getMoveUpX();
            yUp = position.getMoveUpY();
            counter ++;
        }

        // test that the slug actually moves
        counter = 0;
        int movement_counter = 0;
        while (counter < 100) {
            slug.move();
            int slugX = slug.getX();
            int slugY = slug.getY();
            if (slugX != x || slugY != y) {
                movement_counter ++;
            }

            x = position.getXCoord();
            y = position.getYCoord(); 
            counter ++;
        }

        assertTrue(movement_counter > 0);
    }

    /*
     * 2. Test getters of slug including: attack, crit-chance, turn-chance,
     * battle radius and support radius
     */
    @Test
    public void testSlugStats() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition position = new PathPosition(indexInPath, path);
        SlugEnemy slug = new SlugEnemy(position);

        assertEquals(SLUG_HEALTH, slug.getHealth());
        assertEquals(SLUG_ATTACK, slug.attack(false));
        assertEquals(SLUG_CRIT, slug.getCrit());
        assertEquals(SLUG_TURN, slug.getTurn());
        assertEquals(SLUG_EXPERIENCE, slug.getExperience());
        assertEquals(SLUG_MAX_CRIT_DAMAGE, slug.getMaxCritDamage());
        assertEquals(SLUG_MIN_CRIT_DAMAGE, slug.getMinCritDamage());
        assertEquals(SLUG_MAX_STORED_CRITS, slug.getMaxStoredCrits());
        assertEquals(SLUG_MIN_STORED_CRITS, slug.getMinStoredCrits());

        assertEquals(SLUG_HEALTH - 10, slug.reduceHealth(10, null));
        assertEquals(SLUG_HEALTH - 15, slug.reduceHealth(5, null));
        assertEquals(SLUG_HEALTH - 18, slug.reduceHealth(3, null));
        assertEquals(SLUG_HEALTH - 20, slug.reduceHealth(2, null));

        int counter = 0;
        while (counter < 100) {
            assertFalse(slug.turn());
            assertEquals(SLUG_ATTACK, slug.attack(false));
            counter ++;
        }
    }   

    /*
     * 3. Test getters of vampire including: attack, crit-chance, turn-chance,
     * battle radius and support radius
     */
    @Test
    public void testVampireStats() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition position = new PathPosition(indexInPath, path);
        VampireEnemy vampire = new VampireEnemy(position, new ArrayList<>());

        assertEquals(VAMPIRE_HEALTH, vampire.getHealth());
        assertEquals(VAMPIRE_CRIT, vampire.getCrit());
        assertEquals(VAMPIRE_TURN, vampire.getTurn());
        assertEquals(VAMPIRE_EXPERIENCE, vampire.getExperience());

        assertEquals(VAMPIRE_MAX_CRIT_DAMAGE, vampire.getMaxCritDamage());
        assertEquals(VAMPIRE_MIN_CRIT_DAMAGE, vampire.getMinCritDamage());
        assertEquals(VAMPIRE_MAX_STORED_CRITS, vampire.getMaxStoredCrits());
        assertEquals(VAMPIRE_MIN_STORED_CRITS, vampire.getMinStoredCrits());

        assertEquals(VAMPIRE_HEALTH - 20, vampire.reduceHealth(20, null));

        int counter = 0;
        int nonShieldCrits = 0;
        int damage;

        while (counter < 2000) {
            damage = vampire.attack(false);
            assertFalse(vampire.turn());
            assertTrue(damage >= VAMPIRE_ATTACK && damage <= VAMPIRE_ATTACK + VAMPIRE_MAX_CRIT_DAMAGE);
            if (damage > VAMPIRE_ATTACK) {
                nonShieldCrits ++;
            }
        counter ++;
        }

        assertTrue(nonShieldCrits > 0);

        counter = 0;
        int shieldCrits = 0;

        while (counter < 2000) {
            damage = vampire.attack(true);
            assertTrue(damage >= VAMPIRE_ATTACK && damage <= VAMPIRE_ATTACK + VAMPIRE_MAX_CRIT_DAMAGE);
            if (damage > VAMPIRE_ATTACK) {
                shieldCrits ++;
            }
            counter ++;
        }
        assertTrue(shieldCrits > 0);
        assertTrue(shieldCrits < nonShieldCrits);
    }

    /*
     * 4. Test getters of zombie including: attack, crit-chance, turn-chance,
     * battle radius and support radius
     */
    @Test
    public void testZombieStats() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition position = new PathPosition(indexInPath, path);
        ZombieEnemy zombie = new ZombieEnemy(position);

        assertEquals(ZOMBIE_HEALTH, zombie.getHealth());
        assertEquals(ZOMBIE_ATTACK, zombie.attack(false));
        assertEquals(ZOMBIE_CRIT, zombie.getCrit());
        assertEquals(ZOMBIE_TURN, zombie.getTurn());
        assertEquals(ZOMBIE_EXPERIENCE, zombie.getExperience());

        assertEquals(ZOMBIE_MAX_CRIT_DAMAGE, zombie.getMaxCritDamage());
        assertEquals(ZOMBIE_MIN_CRIT_DAMAGE, zombie.getMinCritDamage());
        assertEquals(ZOMBIE_MAX_STORED_CRITS, zombie.getMaxStoredCrits());
        assertEquals(ZOMBIE_MIN_STORED_CRITS, zombie.getMinStoredCrits());

        assertEquals(ZOMBIE_HEALTH - 15, zombie.reduceHealth(15, null));

        int counter = 0;
        int turns = 0;

        while (counter < 1000) {
            if(zombie.turn()) {
                turns ++;
            }
            counter ++;
        }

        assertTrue(turns > 0);
    }

    /*
     * 5. Test slug battle and support radii
     */
    @ParameterizedTest
    @MethodSource("slugRadiiArguments")
    public void testSlugRadii(int x, int y, boolean battleAns, boolean supportAns) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(4, 2));
        path.add(new Pair<>(4, 3));
        path.add(new Pair<>(4, 4));
        path.add(new Pair<>(3, 4));
        path.add(new Pair<>(2, 4));
        path.add(new Pair<>(1, 4));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int enemyIndexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition enemyPosition = new PathPosition(enemyIndexInPath, path);
        SlugEnemy slug = new SlugEnemy(enemyPosition);
    
        assertTrue(slug.checkBattleRadius(x, y) == battleAns);
        assertTrue(slug.checkSupportRadius(x, y) == supportAns);
    }

    /*
     * 6. Test vampire battle and support radii
     */
    @ParameterizedTest
    @MethodSource("vampireRadiiArguments")
    public void testVampireRadii(int x, int y, boolean battleAns, boolean supportAns) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(4, 2));
        path.add(new Pair<>(4, 3));
        path.add(new Pair<>(4, 4));
        path.add(new Pair<>(3, 4));
        path.add(new Pair<>(2, 4));
        path.add(new Pair<>(1, 4));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int enemyIndexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition enemyPosition = new PathPosition(enemyIndexInPath, path);
        VampireEnemy vampire = new VampireEnemy(enemyPosition, new ArrayList<>());

        assertTrue(vampire.checkBattleRadius(x, y) == battleAns);
        assertTrue(vampire.checkSupportRadius(x, y) == supportAns);
    }

    /*
     * 7. Test zombie battle and support radii
     */
    @ParameterizedTest
    @MethodSource("zombieRadiiArguments")
    public void testZombieRadii(int x, int y, boolean battleAns, boolean supportAns) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(4, 2));
        path.add(new Pair<>(4, 3));
        path.add(new Pair<>(4, 4));
        path.add(new Pair<>(3, 4));
        path.add(new Pair<>(2, 4));
        path.add(new Pair<>(1, 4));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int enemyIndexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition enemyPosition = new PathPosition(enemyIndexInPath, path);

        SimpleIntegerProperty battleRadius = new SimpleIntegerProperty();
        SimpleIntegerProperty supportRadius = new SimpleIntegerProperty();

        battleRadius.set(1);
        supportRadius.set(1);

        ZombieEnemy zombie = new ZombieEnemy(enemyPosition);
        assertTrue(zombie.checkBattleRadius(x, y) == battleAns);
        assertTrue(zombie.checkSupportRadius(x, y) == supportAns);
    }

    /*
     * 8. Test zombie slower movement
     */
    @Test
    public void testZombieMovement() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));

        PathPosition position = new PathPosition(indexInPath, path);
        ZombieEnemy zombie = new ZombieEnemy(position);

        int x = position.getXCoord();
        int y = position.getYCoord();
        int xDown = position.getMoveDownX();
        int yDown = position.getMoveDownY();
        int xUp = position.getMoveUpX();
        int yUp = position.getMoveUpY();

        // test that the zombie only moves at most once every two turns
        int counter = 0;
        while (counter < 100) {
            int zombieX = zombie.getX();
            int zombieY = zombie.getY();
            zombie.move();
            assertEquals(x, zombieX);
            assertEquals(y, zombieY);

            zombie.move();
            zombieX = zombie.getX();
            zombieY = zombie.getY();
            assertTrue(zombieX == x || zombieX == xDown || zombieX == xUp);
            assertTrue(zombieY == y || zombieY == yDown || zombieY == yUp);

            x = position.getXCoord();
            y = position.getYCoord();
            xDown = position.getMoveDownX();
            yDown = position.getMoveDownY();
            xUp = position.getMoveUpX();
            yUp = position.getMoveUpY();
            counter ++;
        }
        
        // test that the zombie actually moves
        counter = 0;
        int movement_counter = 0;
        while (counter < 100) {
            zombie.move();
            int zombieX = zombie.getX();
            int zombieY = zombie.getY();
            if (zombieX != x || zombieY != y) {
                movement_counter ++;
            }

            x = position.getXCoord();
            y = position.getYCoord(); 
            counter ++;
        }

        assertTrue(movement_counter > 0);
    }

    /*
     * 9. Test vampire regular movement and campfire affected
     * with the following board
     *   01234
     * 0 -X---
     * 1 -V##-
     * 2 -#-#X
     * 3 -###-
     * 4 -----
     * 
     * Legend:
     * - = nothing
     * # = path tile
     * X = campfire
     * V = vampire
     */
    @Test
    public void testVampireMovement() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));
        List<Building> buildings = new ArrayList<>();

        PathPosition position = new PathPosition(indexInPath, path);
        VampireEnemy vampire = new VampireEnemy(position, buildings);

        int x = position.getXCoord();
        int y = position.getYCoord();
        int xDown = position.getMoveDownX();
        int yDown = position.getMoveDownY();
        int xUp = position.getMoveUpX();
        int yUp = position.getMoveUpY();
        int counter = 0;
        // test that the vampire moves either up or down the path or doesn't move
        // when there is no campfire
        while (counter < 100) {
            vampire.move();
            int vampireX = position.getXCoord();
            int vampireY = position.getYCoord();

            assertTrue(vampireX == x || vampireX == xDown || vampireX == xUp);
            assertTrue(vampireY == y || vampireY == yDown || vampireY == yUp);

            x = position.getXCoord();
            y = position.getYCoord();
            xDown = position.getMoveDownX();
            yDown = position.getMoveDownY();
            xUp = position.getMoveUpX();
            yUp = position.getMoveUpY();
            counter ++;
        }
        // test that the vampire actually moves when there is no campfire
        counter = 0;
        int movement_counter = 0;
        while (counter < 100) {
            vampire.move();
            int vampireX = vampire.getX();
            int vampireY = vampire.getY();
            if (vampireX != x || vampireY != y) {
                movement_counter ++;
            }
            x = position.getXCoord();
            y = position.getYCoord(); 
            counter ++;
        }
        assertTrue(movement_counter > 0);
    
        position = new PathPosition(indexInPath, path);
        vampire = new VampireEnemy(position, buildings);

        buildings.add(new CampfireBuilding(new SimpleIntegerProperty(4), new SimpleIntegerProperty(2)));
        buildings.add(new CampfireBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));

        // Vampire moves away from campfire at (1, 0)
        vampire.move();
        assertEquals(1, position.getXCoord());
        assertEquals(2, position.getYCoord());

        vampire.move();
        assertEquals(1, position.getXCoord());
        assertEquals(3, position.getYCoord());

        // Vampire will move between influece of the two campfires
        // causing it to alternate between (1, 3) and (2, 3)
        vampire.move();
        assertEquals(2, position.getXCoord());
        assertEquals(3, position.getYCoord());

        vampire.move();
        assertEquals(1, position.getXCoord());
        assertEquals(3, position.getYCoord());

        vampire.move();
        assertEquals(2, position.getXCoord());
        assertEquals(3, position.getYCoord());

        vampire.move();
        assertEquals(1, position.getXCoord());
        assertEquals(3, position.getYCoord());
    }

    /*
     * 10. Test vampire movement campfire affected with the following board
     *   01234
     * 0 X----
     * 1 -V##-
     * 2 -#-#-
     * 3 -###-
     * 4 -----
     * 
     * Legend:
     * - = nothing
     * # = path tile
     * X = campfire
     * V = vampire
     */
    @Test
    public void testVampireCampfireEquillibrum() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        List<Building> buildings = new ArrayList<>();
        buildings.add(new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        
        // check that vampire randomly chooses between equidistant path tiles
        int counter = 0;
        int upCounter = 0;
        int downCounter = 0;
        while (counter < 100) {
            int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));
            PathPosition position = new PathPosition(indexInPath, path);
            VampireEnemy vampire = new VampireEnemy(position, buildings);

            vampire.move();
            if (position.getXCoord() == 2 && position.getYCoord() == 1) {
                upCounter ++;
            } else if (position.getXCoord() == 1 && position.getYCoord() == 2) {
                downCounter ++;
            } else {
                assertTrue(false);
            }
            counter ++;
        }
        assertTrue(upCounter > 0);
        assertTrue(downCounter > 0);

        // check that given two paths with lower distance than the current path
        // the vampire doesn't move
        int indexInPath = path.indexOf(new Pair<Integer, Integer>(1, 1));
        PathPosition position = new PathPosition(indexInPath, path);
        VampireEnemy vampire = new VampireEnemy(position, buildings);

        vampire.move();
        vampire.move();
        vampire.move();
        vampire.move();
        
        counter = 0;
        while (counter < 100) {
            vampire.move();
            assertEquals(3, position.getXCoord());
            assertEquals(3, position.getYCoord());
            counter ++;
        }
    }

    @Test
    public void testString() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(0, 0));

        SlugEnemy newSlug = new SlugEnemy(new PathPosition(0, newList));
        assertEquals(newSlug.getType(), "slug");

        ZombieEnemy newZombie = new ZombieEnemy(new PathPosition(0, newList));
        assertEquals(newZombie.getType(), "zombie");

        VampireEnemy newVamp = new VampireEnemy(new PathPosition(0, newList), new ArrayList<>());
        assertEquals(newVamp.getType(), "vampire");
    }

    /*
     * Generate arguments for test 5
     */
    private static Stream<Arguments> slugRadiiArguments() {
        return Stream.of(
            Arguments.of(0, 0, true, true),
            Arguments.of(1, 0, true, true),
            Arguments.of(2, 0, true, true),
            Arguments.of(3, 0, false, false),
            Arguments.of(0, 1, true, true),
            Arguments.of(1, 1, true, true),
            Arguments.of(2, 1, true, true),
            Arguments.of(3, 1, true, true),
            Arguments.of(4, 1, false, false),
            Arguments.of(0, 2, true, true),
            Arguments.of(1, 2, true, true),
            Arguments.of(2, 2, true, true),            
            Arguments.of(3, 2, false, false),
            Arguments.of(2, 3, false, false),
            Arguments.of(2, 4, false, false)
        );
    }

    /*
     * Generate arguments for test 6
     */
    private static Stream<Arguments> vampireRadiiArguments() {
        return Stream.of(
            Arguments.of(1, 1, true, true),
            Arguments.of(4, 1, true, true),
            Arguments.of(5, 1, false, true),
            Arguments.of(6, 1, false, true),
            Arguments.of(7, 1, false, false),
            Arguments.of(1, 4, true, true),
            Arguments.of(1, 5, false, true),
            Arguments.of(1, 6, false, true),
            Arguments.of(1, 7, false, false),
            Arguments.of(4, 5, false, true),
            Arguments.of(5, 5, false, false),
            Arguments.of(3, 3, true, true)
        );
    }

    /*
     * Generate arguments for test 7
     */
    private static Stream<Arguments> zombieRadiiArguments() {
        return Stream.of(
            Arguments.of(1, 1, true, true),
            Arguments.of(4, 1, true, true),
            Arguments.of(5, 1, false, false),
            Arguments.of(1, 4, true, true),
            Arguments.of(1, 5, false, false),
            Arguments.of(3, 3, true, true),
            Arguments.of(4, 2, false, false)

        );
    }
}
