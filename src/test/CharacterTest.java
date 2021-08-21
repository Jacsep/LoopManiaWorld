package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;

public class CharacterTest {
    @Test
    public void testAddAlliedSoldier() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        assertEquals(newCharacter.numOfAlliedSoldiers(), 0);

        newCharacter.addAlliedSoldier();

        assertEquals(newCharacter.numOfAlliedSoldiers(), 1);
        
        newCharacter.addAlliedSoldier();

        assertEquals(newCharacter.numOfAlliedSoldiers(), 2);

        List<AlliedSoldier> returned = newCharacter.getListOfAllies();
        returned.remove(0);

        assertEquals(newCharacter.numOfAlliedSoldiers(), 1);
    }

    @Test
    public void testDestroyAlliedSoldier() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        assertEquals(newCharacter.numOfAlliedSoldiers(), 0);

        newCharacter.addAlliedSoldier();
        newCharacter.addAlliedSoldier();

        assertEquals(newCharacter.numOfAlliedSoldiers(), 2);

        List<AlliedSoldier> allyList = newCharacter.getListOfAllies();
        AlliedSoldier firstSoldier = allyList.get(0);

        newCharacter.destroySoldier(firstSoldier);

        assertEquals(newCharacter.numOfAlliedSoldiers(), 1);
    }

    @Test
    public void testChangeHealth() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        assertEquals(newCharacter.getHealth(), 100);

        newCharacter.changeHealth(20);

        assertEquals(newCharacter.getHealth(), 100);

        newCharacter.changeHealth(-40);

        assertEquals(newCharacter.getHealth(), 60);
    }

    /*
    @Test
    public void testChangeBaseDamage() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        assertEquals(newCharacter.getBaseDamage(), 5);

        newCharacter.changeBaseDamage(-10);

        assertEquals(newCharacter.getBaseDamage(), 0);

        newCharacter.changeBaseDamage(25);

        assertEquals(newCharacter.getBaseDamage(), 25);
    }

    @Test
    public void testChangeDefenceScalar() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        assertEquals(newCharacter.getDefenceScalar(), 0);

        newCharacter.changeDefenceScalar(-10);

        assertEquals(newCharacter.getDefenceScalar(), 0);

        newCharacter.changeDefenceScalar(25);

        assertEquals(newCharacter.getDefenceScalar(), 25);
    }

    @Test
    public void testChangeDefenceRatio() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        assertEquals(newCharacter.getDefenceRatio(), 1);

        newCharacter.changeDefenceRatio(2);

        assertEquals(newCharacter.getDefenceRatio(), 1);

        newCharacter.changeDefenceRatio(0.5);

        assertEquals(newCharacter.getDefenceRatio(), 0.5);
    }

    @Test
    public void testTranced() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        
        Character newCharacter = new Character(new PathPosition(0, newList));

        newCharacter.addTranced(new SlugEnemy(new PathPosition(0, newList)));
        assertEquals(newCharacter.returnTranced().size(), 1);

        newCharacter.cleanTranced();

        assertEquals(newCharacter.returnTranced().size(), 0);
    }
    */
}
