package test;

import org.junit.jupiter.api.Test;

import unsw.loopmania.Buildings.BuildingFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildingFactoryTest {
    @Test
    public void testBuildingFactory() {
        assertTrue(BuildingFactory.generateItem("barracks", 0, 0).getType().equals("barracks"));
        assertTrue(BuildingFactory.generateItem("campfire", 0, 0).getType().equals("campfire"));
        assertTrue(BuildingFactory.generateItem("tower", 0, 0).getType().equals("tower"));
        assertTrue(BuildingFactory.generateItem("trap", 0, 0).getType().equals("trap"));
        assertTrue(BuildingFactory.generateItem("vampireCastle", 0, 0).getType().equals("vampireCastle"));
        assertTrue(BuildingFactory.generateItem("village", 0, 0).getType().equals("village"));
        assertTrue(BuildingFactory.generateItem("zombiePit", 0, 0).getType().equals("zombiePit"));
    }
}
