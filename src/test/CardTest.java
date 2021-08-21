package test;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Cards.AdventurerGuildCard;
import unsw.loopmania.Cards.BarracksCard;
import unsw.loopmania.Cards.BuildingCard;
import unsw.loopmania.Cards.CampfireCard;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.CardFactory;
import unsw.loopmania.Cards.ClearEnemiesCard;
import unsw.loopmania.Cards.PokerBallCard;
import unsw.loopmania.Cards.TowerCard;
import unsw.loopmania.Cards.TrapCard;
import unsw.loopmania.Cards.VampireCastleCard;
import unsw.loopmania.Cards.VillageCard;
import unsw.loopmania.Cards.ZombiePitCard;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.SlugEnemy;



public class CardTest {
    /*
     * 1. Test valid placements for card which can be placed adjacent to path
     */
    @ParameterizedTest
    @MethodSource("adjacentValidArguments")
    public void adjacentPlacementValid(int x, int y, List<Pair<Integer, Integer>> path) {
        Card adjacentCard = new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(adjacentCard.validPlacement(x, y, path));
    }

    /*
     * 2. Test invalid placements for card which can be placed adjacent to path
     */
    @ParameterizedTest
    @MethodSource("adjacentInvalidArguments")
    public void adjacentPlacementInvalid(int x, int y, List<Pair<Integer, Integer>> path) {
        Card adjacentCard = new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertFalse(adjacentCard.validPlacement(x, y, path));
    }

    /*
     * 3. Test valid placements for card which can be placed on path
     */
    @ParameterizedTest
    @MethodSource("pathValidArguments")
    public void pathPlacementValid(int x, int y, List<Pair<Integer, Integer>> path) {
        Card adjacentCard = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(adjacentCard.validPlacement(x, y, path));
    }

    /*
     * 4. Test invalid placements for card which can be placed on path
     */
    @ParameterizedTest
    @MethodSource("pathInvalidArguments")
    public void pathPlacementInvalid(int x, int y, List<Pair<Integer, Integer>> path) {
        Card adjacentCard = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertFalse(adjacentCard.validPlacement(x, y, path));
    }

    /*
     * 5. Test valid placements for card which can be placed on non-path
     */
    @ParameterizedTest
    @MethodSource("nonPathValidArguments")
    public void nonPathPlacementValid(int x, int y, List<Pair<Integer, Integer>> path) {
        Card adjacentCard = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(adjacentCard.validPlacement(x, y, path));
    }

    /*
     * 6. Test invalid placements for card which can be placed on path
     */
    @ParameterizedTest
    @MethodSource("nonPathInvalidArguments")
    public void nonPathPlacementInvalid(int x, int y, List<Pair<Integer, Integer>> path) {
        Card adjacentCard = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertFalse(adjacentCard.validPlacement(x, y, path));
    }

    /*
     * 7. Test barracks card to barracks conversion
     */
    @Test
    public void testBarracksCard() {
        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(barracksCard.getType().equals("barracksCard"));

        Building barracks = ((BuildingCard )barracksCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(barracks.getType().equals("barracks"));
    }

    /*
     * 8. Test campfire card to campfire conversion
     */
    @Test
    public void testCampfireCard() {
        Card campfireCard = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(campfireCard.getType().equals("campfireCard"));

        Building campfire = ((BuildingCard )campfireCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(campfire.getType().equals("campfire"));
    }

    /*
     * 9. Test tower card to tower conversion
     */
    @Test
    public void testTowerCard() {
        Card towerCard = new TowerCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(towerCard.getType().equals("towerCard"));

        Building tower = ((BuildingCard )towerCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(tower.getType().equals("tower"));
    }

    /*
     * 10. Test trap card to trap conversion
     */
    @Test
    public void testTrapCard() {
        Card trapCard = new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(trapCard.getType().equals("trapCard"));

        Building trap = ((BuildingCard )trapCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty());
        assertTrue(trap.getType().equals("trap"));
    }

    /*
     * 11. Test vampire castle card to vampire castle conversion
     */
    @Test
    public void testVampireCastleCard() {
        Card vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(vampireCastleCard.getType().equals("vampireCastleCard"));

        Building vampireCastle = ((BuildingCard )vampireCastleCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(vampireCastle.getType().equals("vampireCastle"));
    }

    /*
     * 12. Test village card to village conversion
     */
    @Test
    public void testVillageCard() {
        Card villageCard = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(villageCard.getType().equals("villageCard"));

        Building village = ((BuildingCard )villageCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(village.getType().equals("village"));
    }

    /*
     * 13. Test zombie pit card to zombie pit conversion
     */
    @Test
    public void testZombiePitCard() {
        Card zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));
        assertTrue(zombiePitCard.getType().equals("zombiePitCard"));

        Building zombiePit = ((BuildingCard )zombiePitCard).createBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(zombiePit.getType().equals("zombiePit"));
    }

    @Test
    public void testVillageUpgradeCard() {
        List<Building> buildings = new ArrayList<Building>();
        VillageBuilding village1 = new VillageBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        VillageBuilding village2 = new VillageBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(0));
        buildings.add(village1);
        buildings.add(village2);
        buildings.add(new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)));
        buildings.add(new CampfireBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));
        AdventurerGuildCard guildCard = new AdventurerGuildCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), buildings, null);

        assertTrue(guildCard.validPlacement(0, 0, null));
        assertFalse(guildCard.validPlacement(1, 0, null));
        assertFalse(guildCard.validPlacement(0, 1, null));
        assertFalse(guildCard.validPlacement(1, 3, null));

        guildCard.specialAbility(0, 0, null);
        assertTrue(village1.hasAdventurerGuild());
        assertFalse(village2.hasAdventurerGuild());
        guildCard.specialAbility(0,1, null);
        assertTrue(village1.hasAdventurerGuild());
        assertFalse(village2.hasAdventurerGuild());
        guildCard.specialAbility(1,0, null);
        assertTrue(village1.hasAdventurerGuild());
        assertFalse(village2.hasAdventurerGuild());
        guildCard.specialAbility(1, 3, null);
        assertTrue(village1.hasAdventurerGuild());
        assertFalse(village2.hasAdventurerGuild());
        guildCard.specialAbility(3, 0, null);
        assertTrue(village1.hasAdventurerGuild());
        assertTrue(village2.hasAdventurerGuild());
    }

    @Test
    public void testPokerBall() {
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
        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        world.addEnemy(enemy1);
        enemies.add(enemy1);
        world.addEnemy(enemy2);
        enemies.add(enemy2);

        PokerBallCard pokerBall = new PokerBallCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), null, world);

        assertTrue(pokerBall.validPlacement(1, 1, null));
        assertTrue(pokerBall.validPlacement(1, 2, null));
        assertFalse(pokerBall.validPlacement(1, 3, null));
        assertFalse(pokerBall.validPlacement(3, 1, null));
        assertFalse(pokerBall.validPlacement(3, 3, null));

        pokerBall.specialAbility(1, 1, character);
        assertEquals(1, character.getCaughtEnemies().size());
        pokerBall.specialAbility(1, 1, character);
        assertEquals(1, character.getCaughtEnemies().size());
        pokerBall.specialAbility(0, 1, character);
        assertEquals(1, character.getCaughtEnemies().size());
        pokerBall.specialAbility(1, 2, character);
        assertEquals(2, character.getCaughtEnemies().size());
    }

    @Test
    public void testClearCard() {
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
        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        enemies.add(enemy1);
        enemies.add(enemy2);

        ClearEnemiesCard clearCard = new ClearEnemiesCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), null, enemies);

        assertTrue(clearCard.validPlacement(1, 1, null));
        assertTrue(clearCard.validPlacement(1, 2, null));

        clearCard.specialAbility(1, 1, null);
        assertEquals(0, enemies.size());
    }

    @Test
    public void testCardFactory() {
        assertTrue(CardFactory.generateItem("barracksCard", 0, 0).getType().equals("barracksCard"));
        assertTrue(CardFactory.generateItem("campfireCard", 0, 0).getType().equals("campfireCard"));
        assertTrue(CardFactory.generateItem("towerCard", 0, 0).getType().equals("towerCard"));
        assertTrue(CardFactory.generateItem("trapCard", 0, 0).getType().equals("trapCard"));
        assertTrue(CardFactory.generateItem("vampireCastleCard", 0, 0).getType().equals("vampireCastleCard"));
        assertTrue(CardFactory.generateItem("villageCard", 0, 0).getType().equals("villageCard"));
        assertTrue(CardFactory.generateItem("zombiePitCard", 0, 0).getType().equals("zombiePitCard"));
    }


    /*
     * Cards are tested on the following boards:
     *
     * Test board 1 (path1)
     *   0123
     * 0 ----
     * 1 -#--
     * 2 ----
     * 3 ----
     *
     * Test board 2 (path2)
     *   012345
     * 0 ------
     * 1 -###--
     * 2 -#-#--
     * 3 -###--
     * 4 ------
     * 5 ------
     */

    /*
     * Generate arguments for test 1
     */
    private static Stream<Arguments> adjacentValidArguments() {
        List<Pair<Integer, Integer>> path1 = new ArrayList<>();
        path1.add(new Pair<>(1, 1));

        List<Pair<Integer, Integer>> path2 = new ArrayList<>();
        path2.add(new Pair<>(1, 1));
        path2.add(new Pair<>(2, 1));
        path2.add(new Pair<>(3, 1));
        path2.add(new Pair<>(3, 2));
        path2.add(new Pair<>(3, 3));
        path2.add(new Pair<>(2, 3));
        path2.add(new Pair<>(1, 3));
        path2.add(new Pair<>(1, 2));

        return Stream.of(
            Arguments.of(0, 1, path1),
            Arguments.of(1, 0, path1),
            Arguments.of(2, 1, path1),
            Arguments.of(1, 2, path1),
            Arguments.of(0, 1, path2),
            Arguments.of(0, 2, path2),
            Arguments.of(0, 3, path2),
            Arguments.of(1, 0, path2),
            Arguments.of(2, 0, path2),
            Arguments.of(3, 0, path2),
            Arguments.of(4, 1, path2),
            Arguments.of(4, 2, path2),
            Arguments.of(4, 3, path2),
            Arguments.of(1, 4, path2),
            Arguments.of(2, 4, path2),
            Arguments.of(3, 4, path2),
            Arguments.of(2, 2, path2)
        );
    }

    /*
     * Generate arguments for test 2
     */
    private static Stream<Arguments> adjacentInvalidArguments() {
        List<Pair<Integer, Integer>> path1 = new ArrayList<>();
        path1.add(new Pair<>(1, 1));

        List<Pair<Integer, Integer>> path2 = new ArrayList<>();
        path2.add(new Pair<>(1, 1));
        path2.add(new Pair<>(2, 1));
        path2.add(new Pair<>(3, 1));
        path2.add(new Pair<>(3, 2));
        path2.add(new Pair<>(3, 3));
        path2.add(new Pair<>(2, 3));
        path2.add(new Pair<>(1, 3));
        path2.add(new Pair<>(1, 2));
    
        return Stream.of(
            Arguments.of(0, 0, path1),
            Arguments.of(2, 2, path1),
            Arguments.of(3, 3, path1),
            Arguments.of(3, 2, path1),
            Arguments.of(3, 1, path1),
            Arguments.of(2, 3, path1),
            Arguments.of(1, 3, path1),
            Arguments.of(1, 1, path1),
            Arguments.of(0, 0, path2),
            Arguments.of(0, 4, path2),
            Arguments.of(4, 0, path2),
            Arguments.of(4, 4, path2),
            Arguments.of(5, 0, path2),
            Arguments.of(5, 2, path2),
            Arguments.of(3, 5, path2),
            Arguments.of(5, 5, path2),
            Arguments.of(1, 1, path2),
            Arguments.of(2, 1, path2),
            Arguments.of(3, 1, path2),
            Arguments.of(3, 2, path2),
            Arguments.of(3, 3, path2),
            Arguments.of(2, 3, path2),
            Arguments.of(1, 3, path2),
            Arguments.of(1, 2, path2),
            Arguments.of(-1, -1, path2),
            Arguments.of(10, 10, path2),
            Arguments.of(-3, 2, path2),
            Arguments.of(3, -4, path2),
            Arguments.of(10, 3, path2),
            Arguments.of(2, 9, path2)
        );
    }

    /*
     * Generate arguments for test 3
     */
    private static Stream<Arguments> pathValidArguments() {
        List<Pair<Integer, Integer>> path1 = new ArrayList<>();
        path1.add(new Pair<>(1, 1));

        List<Pair<Integer, Integer>> path2 = new ArrayList<>();
        path2.add(new Pair<>(1, 1));
        path2.add(new Pair<>(2, 1));
        path2.add(new Pair<>(3, 1));
        path2.add(new Pair<>(3, 2));
        path2.add(new Pair<>(3, 3));
        path2.add(new Pair<>(2, 3));
        path2.add(new Pair<>(1, 3));
        path2.add(new Pair<>(1, 2));

        return Stream.of(
            Arguments.of(1, 1, path1),
            Arguments.of(1, 1, path2),
            Arguments.of(2, 1, path2),
            Arguments.of(3, 1, path2),
            Arguments.of(3, 2, path2),
            Arguments.of(3, 3, path2),
            Arguments.of(2, 3, path2),
            Arguments.of(1, 3, path2),
            Arguments.of(1, 2, path2)
        );
    }

    /*
     * Generate arguments for test 4
     */
    private static Stream<Arguments> pathInvalidArguments() {
        List<Pair<Integer, Integer>> path1 = new ArrayList<>();
        path1.add(new Pair<>(1, 1));

        List<Pair<Integer, Integer>> path2 = new ArrayList<>();
        path2.add(new Pair<>(1, 1));
        path2.add(new Pair<>(2, 1));
        path2.add(new Pair<>(3, 1));
        path2.add(new Pair<>(3, 2));
        path2.add(new Pair<>(3, 3));
        path2.add(new Pair<>(2, 3));
        path2.add(new Pair<>(1, 3));
        path2.add(new Pair<>(1, 2));

        return Stream.of(
            Arguments.of(0, 0, path1),
            Arguments.of(0, 1, path1),
            Arguments.of(1, 0, path1),
            Arguments.of(1, 2, path1),
            Arguments.of(1, 3, path1),
            Arguments.of(2, 1, path1),
            Arguments.of(2, 2, path1),
            Arguments.of(2, 3, path1),
            Arguments.of(3, 1, path1),
            Arguments.of(3, 2, path1),
            Arguments.of(3, 3, path1),
            Arguments.of(0, 0, path2),
            Arguments.of(0, 1, path2),
            Arguments.of(0, 2, path2),
            Arguments.of(0, 3, path2),
            Arguments.of(0, 4, path2),
            Arguments.of(1, 0, path2),
            Arguments.of(1, 4, path2),
            Arguments.of(2, 0, path2),
            Arguments.of(2, 2, path2),
            Arguments.of(2, 4, path2),
            Arguments.of(3, 0, path2),
            Arguments.of(3, 4, path2),
            Arguments.of(4, 0, path2),
            Arguments.of(4, 1, path2),
            Arguments.of(4, 2, path2),
            Arguments.of(4, 3, path2),
            Arguments.of(4, 4, path2),
            Arguments.of(5, 0, path2),
            Arguments.of(5, 2, path2),
            Arguments.of(3, 5, path2),
            Arguments.of(5, 5, path2),
            Arguments.of(10, 10, path2),
            Arguments.of(-5, -5, path2),
            Arguments.of(10, 4, path2),
            Arguments.of(4, 10, path2)
        );
    }

    /*
     * Generate arguments for test 5
     */
    private static Stream<Arguments> nonPathValidArguments() {
        List<Pair<Integer, Integer>> path1 = new ArrayList<>();
        path1.add(new Pair<>(1, 1));

        List<Pair<Integer, Integer>> path2 = new ArrayList<>();
        path2.add(new Pair<>(1, 1));
        path2.add(new Pair<>(2, 1));
        path2.add(new Pair<>(3, 1));
        path2.add(new Pair<>(3, 2));
        path2.add(new Pair<>(3, 3));
        path2.add(new Pair<>(2, 3));
        path2.add(new Pair<>(1, 3));
        path2.add(new Pair<>(1, 2));

        return Stream.of(
            Arguments.of(0, 0, path1),
            Arguments.of(0, 1, path1),
            Arguments.of(1, 0, path1),
            Arguments.of(1, 2, path1),
            Arguments.of(1, 3, path1),
            Arguments.of(2, 1, path1),
            Arguments.of(2, 2, path1),
            Arguments.of(2, 3, path1),
            Arguments.of(3, 1, path1),
            Arguments.of(3, 2, path1),
            Arguments.of(3, 3, path1),
            Arguments.of(0, 0, path2),
            Arguments.of(0, 1, path2),
            Arguments.of(0, 2, path2),
            Arguments.of(0, 3, path2),
            Arguments.of(0, 4, path2),
            Arguments.of(1, 0, path2),
            Arguments.of(1, 4, path2),
            Arguments.of(2, 0, path2),
            Arguments.of(2, 2, path2),
            Arguments.of(2, 4, path2),
            Arguments.of(3, 0, path2),
            Arguments.of(3, 4, path2),
            Arguments.of(4, 0, path2),
            Arguments.of(4, 1, path2),
            Arguments.of(4, 2, path2),
            Arguments.of(4, 3, path2),
            Arguments.of(4, 4, path2),
            Arguments.of(5, 0, path2),
            Arguments.of(5, 2, path2),
            Arguments.of(3, 5, path2),
            Arguments.of(5, 5, path2)
        );
    }

    /*
     * Generate arguments for test 6
     */
    private static Stream<Arguments> nonPathInvalidArguments() {
        List<Pair<Integer, Integer>> path1 = new ArrayList<>();
        path1.add(new Pair<>(1, 1));

        List<Pair<Integer, Integer>> path2 = new ArrayList<>();
        path2.add(new Pair<>(1, 1));
        path2.add(new Pair<>(2, 1));
        path2.add(new Pair<>(3, 1));
        path2.add(new Pair<>(3, 2));
        path2.add(new Pair<>(3, 3));
        path2.add(new Pair<>(2, 3));
        path2.add(new Pair<>(1, 3));
        path2.add(new Pair<>(1, 2));

        return Stream.of(
            Arguments.of(1, 1, path1),
            Arguments.of(1, 1, path2),
            Arguments.of(2, 1, path2),
            Arguments.of(3, 1, path2),
            Arguments.of(3, 2, path2),
            Arguments.of(3, 3, path2),
            Arguments.of(2, 3, path2),
            Arguments.of(1, 3, path2),
            Arguments.of(1, 2, path2)
        );
    }

}
