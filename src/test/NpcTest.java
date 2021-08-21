package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import unsw.loopmania.Npcs.Adventurer;
import unsw.loopmania.Npcs.Bandit;
import unsw.loopmania.Npcs.Bard;
import unsw.loopmania.Npcs.Dragon;
import unsw.loopmania.Npcs.Golem;
import unsw.loopmania.Npcs.MatthiasPierre;
import unsw.loopmania.Npcs.Mushroom;

import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.SlugEnemy;

public class NpcTest {
    /**
     * 1. Test the mushroom
     */
    @Test
    public void MushroomTestBasic() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        LoopManiaWorld world = new LoopManiaWorld(6, 6, path);
        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        world.setCharacter(character);
        character.changeHealth(-20);
    
        Mushroom mushroom = new Mushroom(new PathPosition(path.indexOf(new Pair<>(1, 1)), path), new ArrayList<BasicEnemy>());
        assertTrue(mushroom.checkRadius(character.getX(), character.getY()));

        assertTrue(mushroom.specialAbility(world));
        assertEquals(90, character.getHealth());

        character.changeHealth(20);
        assertEquals(110, character.getHealth());
        assertTrue(mushroom.getType().equals("mushroom"));
    }

    /**
     * 2. Test mushroom movement
     */
    @Test
    public void MushroomTestMovement() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));

        Mushroom mushroom = new Mushroom(new PathPosition(path.indexOf(new Pair<>(3, 1)), path), enemies);
    
        mushroom.move();
        assertEquals(3, mushroom.getX());
        assertEquals(2, mushroom.getY());

        mushroom.move();
        assertEquals(3, mushroom.getX());
        assertEquals(1, mushroom.getY());

        mushroom.move();
        assertEquals(2, mushroom.getX());
        assertEquals(1, mushroom.getY());
    }

    /**
     * 3. Test the bard
     */
    @Test
    public void BardTest() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        LoopManiaWorld world = new LoopManiaWorld(6, 6, path);
        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        world.setCharacter(character);
        character.changeHealth(-20);

        Bard bard = new Bard(new PathPosition(path.indexOf(new Pair<>(1, 3)), path));

        bard.specialAbility(world);
        assertEquals(85, character.getHealth());
        assertTrue(bard.getType().equals("bard"));
    }

    /**
     * 4. Test Matthias Pierre
     */
    @Test
    public void MatthiasPierreTest() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(4, 1));
        path.add(new Pair<>(5, 1));
        path.add(new Pair<>(5, 2));
        path.add(new Pair<>(5, 3));
        path.add(new Pair<>(4, 3));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        LoopManiaWorld world = new LoopManiaWorld(6, 6, path);
        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        world.setCharacter(character);
        world.setMatthiasPierre(new MatthiasPierre(new PathPosition(path.indexOf(new Pair<>(1, 3)), path)));
        world.addEnemy(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addEnemy(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path)));

        assertEquals(0, world.runBattles().size());

        character = new Character(new PathPosition(path.indexOf(new Pair<>(5, 1)), path));
        world.setCharacter(character);
        world.addEnemy(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(5, 1)), path)));
        world.addEnemy(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(5, 2)), path)));

        assertEquals(2, world.runBattles().size());
        assertTrue(world.getMatthiasPierre().getType().equals("matthiasPierre"));
    }

    @Test
    public void MatthiasPierreTestMovement() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        MatthiasPierre matthiasPierre = new MatthiasPierre(new PathPosition(path.indexOf(new Pair<>(3, 1)), path));
        
        matthiasPierre.move();
        assertEquals(3, matthiasPierre.getX());
        assertEquals(1, matthiasPierre.getY());

        matthiasPierre.move();
        assertEquals(3, matthiasPierre.getX());
        assertEquals(1, matthiasPierre.getY());

        matthiasPierre.move();
    }

    /**
     * Test the golem
     */
    @Test
    public void GolemTest() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        Golem golem = new Golem(new PathPosition(path.indexOf(new Pair<>(3, 1)), path));
        assertEquals(20, golem.attack());
        assertEquals(300, golem.reduceHealth(200));
        assertTrue(golem.getType().equals("golem"));
    }

    @Test
    public void GolemTestMovement() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        Golem golem = new Golem(new PathPosition(path.indexOf(new Pair<>(3, 1)), path));
        
        golem.move();
        assertEquals(3, golem.getX());
        assertEquals(1, golem.getY());

        golem.move();
        assertEquals(3, golem.getX());
        assertEquals(1, golem.getY());

        golem.move();
        assertEquals(3, golem.getX());
        assertEquals(1, golem.getY());

        golem.move();
    }

    /**
     * Test the adventurer
     */
    @Test
    public void AdventurerTest() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        Adventurer adventurer = new Adventurer(new PathPosition(path.indexOf(new Pair<>(3, 1)), path));
        assertEquals(20, adventurer.attack());
        assertEquals(30, adventurer.reduceHealth(30));
        assertTrue(adventurer.getType().equals("adventurer"));
    }

    /**
     * Test the bandit
     */
    @Test
    public void BanditTest() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        Bandit bandit = new Bandit(new PathPosition(path.indexOf(new Pair<>(3, 1)), path));
        assertEquals(10, bandit.attack());
        assertEquals(5, bandit.reduceHealth(30));
        assertTrue(bandit.getType().equals("bandit"));
    }

    /**
     * Test the dragon
     */
    @Test
    public void DragonTest() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        Dragon dragon = new Dragon(new PathPosition(path.indexOf(new Pair<>(3, 1)), path));
        assertEquals(20, dragon.attack());
        assertEquals(270, dragon.reduceHealth(30));
        assertTrue(dragon.getType().equals("dragon"));
    }
}
