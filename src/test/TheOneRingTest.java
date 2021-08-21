package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Character;

public class TheOneRingTest {
    @Test
    public void testFunctional() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        TheOneRing newRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        Character newCharacter = new Character(new PathPosition(0, newList));

        newCharacter.changeHealth(-100);

        assertEquals(newCharacter.getHealth(), 0);

        newRing.revive(newCharacter);

        assertEquals(newCharacter.getHealth(), 100);

    }

    @Test
    public void testString() {
        TheOneRing newRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(newRing.getType(), "theOneRing");
    }
}
