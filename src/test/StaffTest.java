package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.SlugEnemy;
import unsw.loopmania.Items.Staff;

public class StaffTest {
    @Test
    public void testEmpty() {
        Staff newStaff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(newStaff.trance(new ArrayList<>()), null);
    }
    
    @Test
    public void testFunctional() {
        Staff newStaff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));

        List<BasicEnemy> newEnemy = new ArrayList<BasicEnemy>();
        newEnemy.add(new SlugEnemy(new PathPosition(0, newList)));
        newEnemy.add(new SlugEnemy(new PathPosition(1, newList)));

        BasicEnemy returnedEnemy = newStaff.trance(newEnemy);

        while (returnedEnemy == null) {
            returnedEnemy = newStaff.trance(newEnemy);
        }

        assertEquals(newEnemy.contains(returnedEnemy), true);
    }

    @Test
    public void testString() {
        Staff newStaff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        assertEquals(newStaff.getType(), "staff");
    }
}
