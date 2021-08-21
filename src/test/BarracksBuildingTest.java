package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Character;
import javafx.beans.property.SimpleIntegerProperty;

public class BarracksBuildingTest {
    @Test
    public void testSpawnAllyWrongTile() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));
        Character newCharacter = new Character(new PathPosition(0, newList));
        BarracksBuilding newBarracks = new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        
        assertEquals(newCharacter.numOfAlliedSoldiers(), 0);

        newBarracks.specialAbility(newCharacter, new ArrayList<>());

        assertEquals(newCharacter.numOfAlliedSoldiers(), 0);
    }

    @Test
    public void testSpawnAllyFunctional() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));
        Character newCharacter = new Character(new PathPosition(0, newList));
        BarracksBuilding newBarracks = new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        
        assertEquals(newCharacter.numOfAlliedSoldiers(), 0);

        newBarracks.specialAbility(newCharacter, new ArrayList<>());

        assertEquals(newCharacter.numOfAlliedSoldiers(), 0);

        newCharacter.moveDownPath();

        newBarracks.specialAbility(newCharacter, new ArrayList<>());

        assertEquals(newCharacter.numOfAlliedSoldiers(), 1);
    }
    
    @Test
    public void testString() {
        BarracksBuilding newBarracks = new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));

        assertEquals(newBarracks.getType(), "barracks");
    }

}
