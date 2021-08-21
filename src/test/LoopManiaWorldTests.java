package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;
import org.json.JSONObject;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Buildings.TrapBuilding;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Buildings.ZombiePitBuilding;
import unsw.loopmania.Cards.BuildingCard;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.DoggieEnemy;
import unsw.loopmania.Enemies.ElanMuskeEnemy;
import unsw.loopmania.Enemies.SlugEnemy;
import unsw.loopmania.Enemies.VampireEnemy;
import unsw.loopmania.Enemies.ZombieEnemy;
import unsw.loopmania.Helpers.WorldHelpers;
import unsw.loopmania.Items.Anduril;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.HealthPotion;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Inventory;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Items.TreeStump;
import unsw.loopmania.Npcs.Adventurer;
import unsw.loopmania.Npcs.Bard;
import unsw.loopmania.Npcs.Bandit;
import unsw.loopmania.Npcs.Dragon;
import unsw.loopmania.Npcs.Golem;
import unsw.loopmania.Npcs.Mushroom;

public class LoopManiaWorldTests {
    @Test
    public void testResetAndMovement() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<>(1, 1));
        newList.add(new Pair<>(2, 1));
        newList.add(new Pair<>(3, 1));
        newList.add(new Pair<>(3, 2));
        newList.add(new Pair<>(3, 3));
        newList.add(new Pair<>(2, 3));
        newList.add(new Pair<>(1, 3));
        newList.add(new Pair<>(1, 2));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        newWorld.setCharacter(new Character(new PathPosition(0, newList)));
        newWorld.addBuilding(new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        newWorld.addEnemy(new SlugEnemy(new PathPosition(0, newList)));
        newWorld.addEnemy(new ZombieEnemy(new PathPosition(0, newList)));
        newWorld.addUnequippedItem(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        newWorld.addFriendly(new Golem(new PathPosition(0, newList)));
        newWorld.addNeutral(new Dragon(new PathPosition(0, newList)));
        newWorld.addUtilityNpc(new Mushroom(new PathPosition(0, newList), newWorld.getEnemies()));
        newWorld.addUtilityNpc(new Bard(new PathPosition(0, newList)));
        newWorld.getGoals();
        newWorld.getMatthiasPierre();
        newWorld.getNumberOfFreeSlotsInUnequippedInventory();
        newWorld.setIsPurchased(true);
        newWorld.isPurchased();

        newWorld.createMatthiasPierre();
        newWorld.loadCard();
        newWorld.runTickMoves();
        newWorld.resetWorld();

        assertEquals(0, newWorld.getCards().size());
        assertEquals(0, newWorld.getBuildings().size());
    }

    @Test 
    public void testSaveLoad() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        Character character = new Character(new PathPosition(0, newList));
        character.addAlliedSoldier();
        newWorld.setCharacter(character);
        newWorld.addBuilding(new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        newWorld.addBuilding(new VampireCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        newWorld.addEnemy(new SlugEnemy(new PathPosition(0, newList)));
        newWorld.addUnequippedItem(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        newWorld.addFriendly(new Golem(new PathPosition(0, newList)));
        newWorld.addNeutral(new Dragon(new PathPosition(0, newList)));
        newWorld.addEquippedItem(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        newWorld.getGoals();
        newWorld.getMatthiasPierre();
        newWorld.setMode("Confusing");
        newWorld.loadCard();
        newWorld.saveWorld();
        try {
            newWorld.loadWorld();
        } catch (FileNotFoundException e){
            assertTrue(false);
        } 
    }

    @Test
    public void tickUpgradedVillage() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<>(1, 1));
        newList.add(new Pair<>(2, 1));
        newList.add(new Pair<>(3, 1));
        newList.add(new Pair<>(3, 2));
        newList.add(new Pair<>(3, 3));
        newList.add(new Pair<>(2, 3));
        newList.add(new Pair<>(1, 3));
        newList.add(new Pair<>(1, 2));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        Character character = new Character(new PathPosition(0, newList));
        newWorld.setCharacter(character);
        newWorld.createMatthiasPierre();
        VillageBuilding village = new VillageBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(3));
        village.upgrade();
        newWorld.addBuilding(village);

        int counter = 0;
        while (counter < 1000) {
            newWorld.runTickMoves();
            counter ++;
        }

        assertTrue(newWorld.getFriendlyNpcs().size() > 0 && newWorld.getFriendlyNpcs().size() < 500);
    }

    @Test
    public void testPurchase() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        newWorld.purchase("sword", 1);
        newWorld.purchase("stake", 2);
        newWorld.purchase("staff", 1);
        newWorld.purchase("armour", 1);
        newWorld.purchase("shield", 2);
        newWorld.purchase("helmet", 1);
        newWorld.purchase("healthPotion", 1);
        newWorld.purchase("hokuspokus", 4);
        assertEquals(9, newWorld.numUnequippedItems());
    }

    @Test
    public void testRewardSplit() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<>(1, 1));
        newList.add(new Pair<>(2, 1));
        newList.add(new Pair<>(3, 1));
        newList.add(new Pair<>(3, 2));
        newList.add(new Pair<>(3, 3));
        newList.add(new Pair<>(2, 3));
        newList.add(new Pair<>(1, 3));
        newList.add(new Pair<>(1, 2));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        int counter = 0;
        while (counter < 100) {
            newWorld.getRewardItem(new Dragon(new PathPosition(0, newList)));
            counter ++;
        }
        newWorld.increaseGoldExperience(new Dragon(new PathPosition(0, newList)));
        newWorld.increaseGoldExperience(new Bard(new PathPosition(0, newList)));
        newWorld.getRewardItem(new Dragon(new PathPosition(0, newList)));
    }

    /**
     * Test spawning basic enemy
     */
    @Test
    public void testSpawnEnemies() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        
        newWorld.setCharacter(new Character(new PathPosition(0, newList)));

        for (int i = 0; i < 10; i++) {
          List<BasicEnemy> returnedList = newWorld.possiblySpawnSlugs();
            assertTrue(returnedList.size() < 2);
        }
    }

    @Test
    public void testSpawnNoValidSpots() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);

        newWorld.setCharacter(new Character(new PathPosition(0, newList)));
        for (int i = 0; i < 10; i++) {
            List<BasicEnemy> returnedList = newWorld.possiblySpawnSlugs();
              assertTrue(returnedList.size() == 0);
        }

    }

    @Test
    public void setMode() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());
        newWorld.setMode("Berserker");
        assertTrue(newWorld.getMode().equals("Berserker"));
    }

    /**
     * Basic battle test
     */
    @Test
    public void testRunBattles() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        Character myCharacter = new Character(new PathPosition(0, newList));
        newWorld.setCharacter(myCharacter);

        /*List<BasicEnemy> org = newWorld.possiblySpawnEnemies();
        List<BasicEnemy> after = newWorld.runBattles();

        assertTrue(after.size() <= org.size());*/
        SlugEnemy newSlug = new SlugEnemy(new PathPosition(1, newList));
        newWorld.addEnemy(newSlug);
        List<BasicEnemy> after = newWorld.runBattles();

        assertEquals(after.size(), 1);
        assertTrue(after.contains(newSlug));
    }

    /**
     * Test loading card
     */
    @Test
    public void testLoadCard() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());
        
        assertEquals(newWorld.numOfCards(), 0);
        newWorld.loadCard();
        assertEquals(newWorld.numOfCards(), 1);
    }

    /**
     * test adding unequipped item
     */
    @Test
    public void testAddUneqippedItem() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());
        
        assertEquals(newWorld.numUnequippedItems(), 0);

        newWorld.addUnequippedItem();

        assertEquals(newWorld.numUnequippedItems(), 1);
    }

    /**
     * Test for add unequipped item when inventory is full
     */
    @Test
    public void testUseHealthPotion() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        Character newCharacter = new Character(new PathPosition(0, newList));
        newWorld.setCharacter(newCharacter);

        newCharacter.changeHealth(-50);
        assertEquals(newCharacter.getHealth(), 50);

        Item returnedItem = newWorld.addUnequippedItem();

        while (!(returnedItem instanceof HealthPotion)) {
            returnedItem = newWorld.addUnequippedItem();
        }

        newWorld.useHealthPotion();
        assertEquals(newCharacter.getHealth(), 90);
    }

    @Test
    public void testGoals() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "experience");
        goalJSON.put("quantity", 123456);
        newWorld.setGoals(goalJSON);
        
        assertTrue(newWorld.goalType().equals("experience"));
        assertEquals(newWorld.goalNum(), 123456);
    }

    @Test
    public void testCompletedCycle() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        Character newCharacter = new Character(new PathPosition(0, newList));
        newWorld.setCharacter(newCharacter);
        assertEquals(newWorld.getCycleNumber().get(), 0);

        newCharacter.moveDownPath();
        assertTrue(newWorld.completedCycle() == false);

        newCharacter.moveDownPath();
        assertTrue(newWorld.completedCycle() == true);
        assertEquals(newWorld.getCycleNumber().get(), 1);
    }

    @Test
    public void testCharacter() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        Character newCharacter = new Character(new PathPosition(0, newList));
        newWorld.setCharacter(newCharacter);
        assertTrue(newWorld.getCharacter().equals(newCharacter));
    }

    @Test
    public void testAddUneqippedItemFullInventory() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());

        for (int i = 0; i < 16; i++) {
            newWorld.addUnequippedItem();
        }
        
        assertEquals(newWorld.numUnequippedItems(), 16);
        assertEquals(newWorld.amountOfGold(), 0);
        assertEquals(newWorld.amountOfExperience(), 0);

        newWorld.addUnequippedItem();

        assertEquals(newWorld.numUnequippedItems(), 16);
        assertTrue(newWorld.amountOfGold() >= 30);
        assertTrue(newWorld.amountOfExperience() >= 20);
    }

    /**
     * Test remove unequipped inventory by coordinates
     */
    @Test
    public void removeUnequipped() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());
        
        assertEquals(newWorld.numUnequippedItems(), 0);

        Item item1 = newWorld.addUnequippedItem();
        Item item2 = newWorld.addUnequippedItem();

        assertEquals(newWorld.numUnequippedItems(), 2);

        newWorld.removeUnequippedInventoryItem(item2);

        assertEquals(newWorld.numUnequippedItems(), 1);

        Item returnItem = newWorld.getUnequippedInventoryItemEntityByCoordinates(0, 0);

        assertTrue(returnItem.equals(item1));
    }

    /**
     * Test move tick
     */
    @Test
    public void testRunTickMoves() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);

        Character newCharacter = new Character(new PathPosition(0, newList));
        newWorld.setCharacter(newCharacter);
        assertEquals(newCharacter.getX(), 1);
        assertEquals(newCharacter.getY(), 0);

        newWorld.runTickMoves();

        assertEquals(newCharacter.getX(), 1);
        assertEquals(newCharacter.getY(), 1);
    }

    /**
     * Test that card gives the correct building
     */
    @Test
    public void testConvertedCardToBuilding() {
        LoopManiaWorld newWorld = new LoopManiaWorld(120, 120, new ArrayList<>());
        int counter = 0;
        while (counter < 100) {
            newWorld.loadCard();
            int numCards = newWorld.numOfCards();
            int numBuildings = newWorld.numOfBuildings();
            if (newWorld.getCardByCoordinates(newWorld.numOfCards() - 1, 0) instanceof BuildingCard) {
                newWorld.convertCardToBuildingByCoordinates(newWorld.numOfCards() - 1, 0, 1, counter);
                assertEquals(numCards - 1, newWorld.numOfCards());
                assertEquals(numBuildings + 1, newWorld.numOfBuildings());
            }
            counter ++;
        }
        
        newWorld.convertCardToBuildingByCoordinates(0, 0, 1, counter);
        counter = 0;
        while (counter < 100) {
            newWorld.loadCard();
            assertTrue(newWorld.numOfCards() <= 8);
            counter ++;
        }
        newWorld.convertCardToBuildingByCoordinates(0, 0, 1, counter);
        newWorld.removeCard(newWorld.getCardByCoordinates(0, 0));
        newWorld.getCardByCoordinates(0, 4);


        
    }

    @Test
    public void testKillEnemy() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        ZombieEnemy newEnemy = new ZombieEnemy(new PathPosition(0, newList));
        newWorld.addEnemy(newEnemy);

        assertEquals(newWorld.getEnemies().size(), 1);
        newWorld.killEnemy(newEnemy);
        newWorld.increaseGoldExperience(newEnemy);;

        assertTrue(newWorld.amountOfExperience() != 0);
        assertTrue(newWorld.amountOfGold() != 0);

        assertEquals(newWorld.getEnemies().size(), 0);

    }

    /**
     * Test that presence of campfire is correctly determined
     */
    @Test
    public void testInCampfireRadius() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        List<Building> buildings = new ArrayList<Building>();
        buildings.add(new VampireCastleBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0)));
        buildings.add(new CampfireBuilding(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5)));
        buildings.add(new VampireCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3)));

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        assertTrue(WorldHelpers.inCampfireRadius(character, buildings));

        character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        assertFalse(WorldHelpers.inCampfireRadius(character, buildings));
    }

    /**
     * Test that all towers are fetched
     */
    @Test
    public void testGetTowers() {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));

        List<Building> buildings = new ArrayList<Building>();

        buildings.add(new TowerBuilding(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5)));
        buildings.add(new TowerBuilding(new SimpleIntegerProperty(4), new SimpleIntegerProperty(5)));
        buildings.add(new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(4)));

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        assertEquals(2, WorldHelpers.getTowers(character, buildings).size());

        character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 3)), path));
        assertEquals(1, WorldHelpers.getTowers(character, buildings).size());

        character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        assertEquals(0, WorldHelpers.getTowers(character, buildings).size());
    }

    /**
     * Test that correct enemy is selected
     */
    @Test
    public void testGetMainEnemy() {
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

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        enemies.add(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
        BasicEnemy enemy = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        enemies.add(enemy);
        enemies.add(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path)));

        assertTrue(WorldHelpers.getMainEnemy(character.getX(), character.getY(), null, enemies) == enemy);
    }

    /**
     * Test that correct supporting enemies are added
     */
    @Test
    public void testGetSupportingEnemies() {
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

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        enemies.add(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
        enemies.add(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path)));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        enemies.add(enemy3);

        BasicEnemy mainEnemy = WorldHelpers.getMainEnemy(character.getX(), character.getY(), null, enemies);
        List<BasicEnemy> supportingEnemies = WorldHelpers.getSupportingEnemies(character.getX(), character.getY(), null, mainEnemy, enemies);
        assertEquals(1, supportingEnemies.size());
        assertTrue(supportingEnemies.get(0) == enemy3);
    }

    /**
     * Run battles against no enemies
     */
    @Test
    public void testRunBattlesNothing(){
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

        assertEquals(0, world.runBattles().size());

        assertEquals(0, world.getEnemies().size());
        assertEquals(100, character.getHealth());
    }

    /**
     * Run basic battles
     */
    @Test
    public void testRunBattlesBasic(){
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

        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(2, world.runBattles().size());

        assertEquals(1, world.getEnemies().size());
        assertEquals(50, character.getHealth());
    }

    /**
     * Test battle with ally
     */
    @Test
    public void testRunBattlesAllies(){
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
        character.addAlliedSoldier();

        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(2, world.runBattles().size());

        assertEquals(1, world.getEnemies().size());
        assertEquals(100, character.getHealth());
        assertEquals(20, character.getListOfAllies().get(0).getHealth());
    }

    @Test
    public void testRunBattlesFriendlys(){
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
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));

        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(2, world.runBattles().size());
    }

    @Test
    public void testRunBattlesFriendlys2(){
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
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));

        BasicEnemy enemy1 = new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(3, world.runBattles().size());
    }

    @Test
    public void testRunNeutralVChar(){
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

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);
        assertTrue(enemy1.checkRadius(character.getX(), character.getY()));

        assertEquals(1, world.characterVsNpcs().size());
        assertEquals(40, character.getHealth());
    }

    @Test
    public void testRunNeutralVChar3(){
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

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);

        assertTrue(world.characterVsNpcs() == null);
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testRunNeutralVChar2(){
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
        world.addUnequippedItem(new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);

        assertEquals(2, world.characterVsNpcs().size());
        assertEquals(80, character.getHealth());
    }

    @Test
    public void testRunBattlesFriendlys3(){
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
        world.addFriendly(new Adventurer(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));

        BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path), null);
        BasicEnemy enemy2 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path), null);
        BasicEnemy enemy3 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), null);
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);
        world.runBattles();
    }

    @Test
    public void testRunNeutralVChar4(){
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
        character.addAlliedSoldier();

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy3 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);
        world.addNeutral(enemy3);

        assertEquals(3, world.characterVsNpcs().size());
        assertEquals(40, character.getHealth());
    }

    @Test
    public void testRunNeutralVChar5(){
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
        world.addEquippedItem(new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy3 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);
        world.addNeutral(enemy3);

        assertEquals(3, world.characterVsNpcs().size());
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testRunNeutralVChar6(){
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
        world.addEquippedItem(new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        character.addAlliedSoldier();

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy3 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);
        world.addNeutral(enemy3);

        assertEquals(3, world.characterVsNpcs().size());
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testRunNeutralVChar7(){
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
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));

        Dragon enemy1 = new Dragon(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Dragon enemy2 = new Dragon(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Dragon enemy3 = new Dragon(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));

        world.addNeutral(enemy1);
        world.addNeutral(enemy2);
        world.addNeutral(enemy3);

        assertTrue(world.characterVsNpcs() == null);
    }

    @Test
    public void testRunNeutralVChar8(){
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
        world.addBuilding(new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        world.addBuilding(new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        world.addBuilding(new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        

        Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy2 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        Bandit enemy3 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));


        world.addNeutral(enemy1);
        world.addNeutral(enemy2);
        world.addNeutral(enemy3);

        assertEquals(3, world.characterVsNpcs().size());
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testRunNeutralVChar9(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        
        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            world.addEquippedItem(new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
            
            Bandit enemy1 = new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.addNeutral(enemy1);
            world.characterVsNpcs();
            counter ++;
        }

    }

    @Test
    public void enemiesVNpcs1() {
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

        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
    
        world.addEnemy(new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
        world.addEnemy(new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));

        world.enemiesVsNpcs();

        assertEquals(0, world.getEnemies().size());
    }

    @Test
    public void enemiesVNpcs2() {
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

        world.addNeutral(new Dragon(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
    
        world.addEnemy(new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
        world.addEnemy(new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));

        world.enemiesVsNpcs();

        assertEquals(0, world.getEnemies().size());
    }

    @Test
    public void enemiesVNpcs3() {
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

        world.addNeutral(new Dragon(new PathPosition(path.indexOf(new Pair<>(2, 1)), path)));
        world.addNeutral(new Dragon(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addNeutral(new Bandit(new PathPosition(path.indexOf(new Pair<>(1, 2)), path)));

        int counter = 0;
        while (counter < 100) {
            world.addEnemy(new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
            counter ++;
        }
        world.enemiesVsNpcs();
        assertEquals(0, world.getNeutralNpcs().size());
    }

    @Test
    public void enemiesVNpcs4() {
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

        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 2)), path)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 3)), path)));
        
        int counter = 0;
        while (counter < 100) {
            world.addEnemy(new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
            counter ++;
        }
        world.enemiesVsNpcs();
        assertEquals(0, world.getNeutralNpcs().size());
    }

    @Test
    public void enemiesVNpcs5() {
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

        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
        
        int counter = 0;
        while (counter < 100) {
            world.addEnemy(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
            counter ++;
        }
        world.enemiesVsNpcs();
        assertEquals(1, world.getFriendlyNpcs().size());
    }

    @Test
    public void enemiesVNpcs6() {
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

        world.addNeutral(new Dragon(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
        world.addNeutral(new Dragon(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
        
        int counter = 0;
        while (counter < 100) {
            world.addEnemy(new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path)));
            counter ++;
        }
        world.enemiesVsNpcs();
        assertEquals(1, world.getNeutralNpcs().size());
    }

    @Test
    public void testRunBattlesDogBoss(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
    
            BasicEnemy enemy1 = new DoggieEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.addEnemy(enemy1);
            world.runBattles();
            counter ++;
        }
    }

    @Test
    public void testRunBattlesTeslaBoss(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            world.addFriendly(new Adventurer(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
            world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
    
            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path), null);
            BasicEnemy enemy2 = new ElanMuskeEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            BasicEnemy enemy3 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), null);
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);
            world.addEnemy(enemy3);
            world.runBattles();
            counter ++;
        }
    }

    @Test
    public void testRunBattlesStumpConfusing(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            world.setMode("Confusing");
            world.addEquippedItem(new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
            TreeStump stump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            stump.setConfusingModeItem();
            world.addUnequippedItem(stump);;

            world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
    
            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path), null);
            BasicEnemy enemy2 = new ElanMuskeEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            BasicEnemy enemy3 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), null);
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);
            world.addEnemy(enemy3);
            world.runBattles();
            counter ++;
        }
    }

    @Test
    public void testRunBattlesAndurilConfusing(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            world.setMode("Confusing");
            Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            anduril.setConfusingModeItem();
            world.addUnequippedItem(anduril);

            world.addFriendly(new Golem(new PathPosition(path.indexOf(new Pair<>(1, 1)), path)));
    
            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path), null);
            BasicEnemy enemy2 = new ElanMuskeEnemy(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            BasicEnemy enemy3 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), null);
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);
            world.addEnemy(enemy3);
            world.runBattles();
            counter ++;
        }
    }

    /**
     * Test battle with 2 allies
     */
    @Test
    public void testRunBattles2Allies(){
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
        character.addAlliedSoldier();
        character.addAlliedSoldier();

        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(2, world.runBattles().size());

        assertEquals(1, world.getEnemies().size());
        assertEquals(100, character.getHealth());
        assertEquals(30, character.getListOfAllies().get(0).getHealth());
        assertEquals(35, character.getListOfAllies().get(1).getHealth());
    }

    /**
     * Test soldier ability to take final kill
     */
    @Test
    public void testRunBattlesAllyKill(){
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
        character.addAlliedSoldier();
        character.addAlliedSoldier();

        BasicEnemy enemy = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy);

        assertEquals(1, world.runBattles().size());

        assertEquals(0, world.getEnemies().size());
        assertEquals(100, character.getHealth());
        assertEquals(35, character.getListOfAllies().get(0).getHealth());
        assertEquals(35, character.getListOfAllies().get(1).getHealth());
    }

    /**
     * Test the one ring coverage test
     */
    @Test
    public void testRunBattlesRing(){
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
        world.addUnequippedItem(new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));

        BasicEnemy enemy = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
        world.addEnemy(enemy);

        world.runBattles();
    }

    /**
     * Test the one ring coverage test with supporting enemy
     */
    @Test
    public void testRunBattlesRing2(){
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
        world.addUnequippedItem(new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));

        BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
        BasicEnemy enemy2 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
        BasicEnemy enemy3 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
        BasicEnemy enemy4 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
        
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);
        world.addEnemy(enemy4);

        world.runBattles();
    }

    /**
     * Test battle with vampire and stake
     */
    @Test
    public void testRunBattlesStakeVampire(){
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
        world.addEquippedItem(new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1))) ;
        character.addAlliedSoldier();

        BasicEnemy enemy = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
        world.addEnemy(enemy);

        assertEquals(1, world.runBattles().size());

        assertEquals(0, world.getEnemies().size());
        assertEquals(100, character.getHealth());
        assertEquals(35, character.getListOfAllies().get(0).getHealth());
    }

    /**
     * Test vampire battle with campfire and stake
     */
    @Test
    public void testRunBattlesStakeVampireCampfire(){
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

        List<Building> buildings = new ArrayList<>();
        Building campfire = new CampfireBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));
        buildings.add(campfire);
    
        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        world.addBuilding(campfire);
        world.setCharacter(character);
        world.addEquippedItem(new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1))) ;

        BasicEnemy enemy = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
        world.addEnemy(enemy);

        assertEquals(1, world.runBattles().size());

        assertEquals(0, world.getEnemies().size());
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testCheckWinExperience() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "experience");
        goalJSON.put("quantity", 10);
        newWorld.setGoals(goalJSON);
        
        assertTrue(newWorld.checkWin() == false);

        newWorld.killEnemy(new ZombieEnemy(new PathPosition(0, newList)));
        newWorld.increaseGoldExperience(new ZombieEnemy(new PathPosition(0, newList)));
        assertTrue(newWorld.checkWin() == true);
    }

    @Test
    public void testCheckWinGold() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "gold");
        goalJSON.put("quantity", 10);
        newWorld.setGoals(goalJSON);
        
        assertTrue(newWorld.checkWin() == false);

        newWorld.killEnemy(new ZombieEnemy(new PathPosition(0, newList)));
        newWorld.increaseGoldExperience(new ZombieEnemy(new PathPosition(0, newList)));
        assertTrue(newWorld.checkWin() == true);
    }

    @Test
    public void testCheckWinCycle() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 1));
        newList.add(new Pair<Integer, Integer>(1, 2));

        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        newWorld.setCharacter(new Character(new PathPosition(0, newList)));
        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "cycles");
        goalJSON.put("quantity", 1);
        newWorld.setGoals(goalJSON);
        
        assertTrue(newWorld.checkWin() == false);

        newWorld.getCharacter().moveDownPath();
        newWorld.getCharacter().moveDownPath();
        newWorld.completedCycle();
        assertTrue(newWorld.checkWin() == true);
    }

    @Test
    public void testGoalType() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());
        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "cycles");
        goalJSON.put("quantity", 1);
        newWorld.setGoals(goalJSON);
        assertTrue(newWorld.goalType().equals("cycles"));
    }

    @Test
    public void testGoalNum() {
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, new ArrayList<>());
        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "cycles");
        goalJSON.put("quantity", 1);
        newWorld.setGoals(goalJSON);
        assertEquals(newWorld.goalNum(), 1);
    }

    /**
     * Test zombie turn chance
     */
    @Test
    public void testRunBattlesZombies(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        int turns = 0;

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.addUtilityNpc(new Mushroom(new PathPosition(path.indexOf(new Pair<>(1, 1)), path), null));
            world.setCharacter(character);
            world.addUnequippedItem();
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            character.addAlliedSoldier();

            BasicEnemy enemy1 = new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
            BasicEnemy enemy2 = new ZombieEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);

            List<BasicEnemy> returnedList = world.runBattles();
            if (returnedList != null && returnedList.size() == 3) {
                turns ++;
            }
            counter ++;
        }
        // check for basic case where turning occurs
        assertTrue(turns > 0);
    }

    /**
     * Coverage test for crits
     */
    @Test
    public void testRunBattlesVampire(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            character.addAlliedSoldier();

            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), new ArrayList<>());
            world.addEnemy(enemy1);

            counter ++;
        }
        // check for basic case where turning occurs
    }

    /**
     * Test staff and trance
     */
    @Test
    public void testRunBattlesStaff(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        int trances = 0;

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            world.addEquippedItem(new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)));
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            character.addAlliedSoldier();

            BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
            BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
            BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
            BasicEnemy enemy4 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);
            world.addEnemy(enemy3);
            world.addEnemy(enemy4);

            assertEquals(4, world.runBattles().size());
            if(character.getListOfAllies().get(0).getHealth() == 30) {
                trances ++;
            }
            counter ++;
        }
        assertTrue(trances > 0);
    }

    /**
     * Test for staff 2
     */
    @Test
    public void testRunBattlesStaff2(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            world.setCharacter(character);
            Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            world.addEquippedItem(armour);
            world.addEquippedItem(new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)));
            world.addEquippedItem(new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
            world.addEquippedItem(new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
            world.getUnequippedInventoryItems();
            assertTrue(world.getEquippedItemBySlot(armour.getSlot()) == armour);

            BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
            BasicEnemy enemy2 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path), new ArrayList<>());
            BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
            BasicEnemy enemy4 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path), new ArrayList<>());
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);
            world.addEnemy(enemy3);
            world.addEnemy(enemy4);
            world.runBattles();
            counter ++;
        }
    }

    /**
     * Test for vampire crits
     */
    @Test
    public void testRunBattlesVampires(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        int deaths = 0;
        List<Building> buildings = new ArrayList<Building>();

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            character.addAlliedSoldier();
            character.addAlliedSoldier();

            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
            world.addEnemy(enemy1);

            assertEquals(1, world.runBattles().size());
            if (character.numOfAlliedSoldiers() == 3) {
                deaths ++;
            }
            counter ++;
        }
        // check for basic case where turning occurs
        assertTrue(deaths > 0);
    }

    /**
     * Coverage test for multiple vampires
     */
    @Test
    public void testRunBattlesMoreVampires(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        List<Building> buildings = new ArrayList<Building>();

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);

            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
            BasicEnemy enemy2 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);

            counter ++;
        }
    }

    /**
     * Coverage test for multiple vampires with shield
     */
    @Test
    public void testRunBattlesMoreVampiresShield(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        path.add(new Pair<>(1, 1));
        path.add(new Pair<>(2, 1));
        path.add(new Pair<>(3, 1));
        path.add(new Pair<>(3, 2));
        path.add(new Pair<>(3, 3));
        path.add(new Pair<>(2, 3));
        path.add(new Pair<>(1, 3));
        path.add(new Pair<>(1, 2));
        int counter = 0;
        List<Building> buildings = new ArrayList<Building>();

        while (counter < 1000) {
            LoopManiaWorld world = new LoopManiaWorld(6, 6, path);

            Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
            world.setCharacter(character);
            world.addEquippedItem(new Shield(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)));

            BasicEnemy enemy1 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
            BasicEnemy enemy2 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
            BasicEnemy enemy3 = new VampireEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path), buildings);
            world.addEnemy(enemy1);
            world.addEnemy(enemy2);
            world.addEnemy(enemy3);

            counter ++;
        }
    }

    /**
     * Test running battle with campfire
     */
    @Test
    public void testRunBattlesCampfire(){
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

        Building campfire = new CampfireBuilding(new SimpleIntegerProperty(1),new SimpleIntegerProperty(0));
        world.addBuilding(campfire);

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        world.setCharacter(character);

        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(2, world.runBattles().size());

        assertEquals(1, world.getEnemies().size());
        assertEquals(80, character.getHealth());
    }

    /**
     * Test towers in battle
     */
    @Test
    public void testRunBattlesTower(){
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

        Building tower = new TowerBuilding(new SimpleIntegerProperty(1),new SimpleIntegerProperty(0));
        world.addBuilding(tower);
        tower = new TowerBuilding(new SimpleIntegerProperty(1),new SimpleIntegerProperty(0));
        world.addBuilding(tower);
        tower = new TowerBuilding(new SimpleIntegerProperty(1),new SimpleIntegerProperty(0));
        world.addBuilding(tower);

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(1, 1)), path));
        world.setCharacter(character);

        BasicEnemy enemy1 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 3)), path));
        BasicEnemy enemy2 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(1, 2)), path));
        BasicEnemy enemy3 = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(2, 1)), path));
        world.addEnemy(enemy1);
        world.addEnemy(enemy2);
        world.addEnemy(enemy3);

        assertEquals(2, world.runBattles().size());

        assertEquals(1, world.getEnemies().size());
        assertEquals(95, character.getHealth());
    }

    /**
     * Check building spawning
     */
    @Test 
    public void testPotentialSpots() {
        List<Pair<Integer, Integer>> newList = new ArrayList<Pair<Integer, Integer>>();
        newList.add(new Pair<Integer, Integer>(1, 0));
        newList.add(new Pair<Integer, Integer>(1, 1));
        Character newCharacter = new Character(new PathPosition(0, newList));
        LoopManiaWorld newWorld = new LoopManiaWorld(5, 5, newList);
        newWorld.setCharacter(newCharacter);

        VampireCastleBuilding newVamp = new VampireCastleBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(0));

        List<Integer> returnedList = newWorld.getPotentialSpots(newVamp);

        assertEquals(returnedList.size(), 1);
    }

    /**
     * Coverage test for rewards
     */
    @Test
    public void testItemRewards() {
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
        int counter = 0;
        int rings = 0;
        while (counter < 3000) {
            Item item = world.getRewardItem(new SlugEnemy(null));
            counter ++;

            if (item != null && item.getType().equals("theOneRing")) {
                rings ++;
            }
        }
        assertTrue(rings > 0);
    }

    /**
     * Coverage test for rewards
     */
    @Test
    public void testCardRewards() {
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
        int counter = 0;
        while (counter < 3000) {
            world.getRewardCard();
            counter ++;
        }
    }

    /**
     * Check the is at hero castle building method
     */
    @Test
    public void testCharacterStartingPosition() {
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

        assertTrue(world.isCharacterAtHeroCastleBuilding());
        world.runTickMoves();

        assertFalse(world.isCharacterAtHeroCastleBuilding());
    }

    /**
     * Coverage test for tickmoves
     */
    @Test
    public void testTickMoves() {
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

        Character character = new Character(new PathPosition(path.indexOf(new Pair<>(2, 3)), path));
        world.setCharacter(character);
        
        BasicEnemy enemy = new SlugEnemy(new PathPosition(path.indexOf(new Pair<>(3, 2)), path));
        world.addEnemy(enemy);
    
        world.addBuilding(new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        world.addBuilding(new VillageBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)));
        world.addBuilding(new TrapBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(1)));
        world.addBuilding(new TrapBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(2)));
        world.addBuilding(new TrapBuilding(new SimpleIntegerProperty(3), new SimpleIntegerProperty(3)));
        
        
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();
    }

    /**
     * Coverage test for possibly spawn end of cycle
     */
    @Test
    public void testBuildingSpawn() {
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
        world.possiblySpawnEndOfCycle();
        world.addBuilding(new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        world.addBuilding(new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        world.addBuilding(new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));

        world.possiblySpawnEndOfCycle();
        world.runTickMoves();
        world.possiblySpawnEndOfCycle();
    }

    @Test
    public void testNpcSpawn() {
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
        world.setCycle(1);
        int counter = 0;
        while (counter < 1000) {
            world.possiblySpawnEndOfCycle();
            counter ++;
        }
        assertTrue(world.getUtilityNpcs().size() > 0);
        assertTrue(world.getNeutralNpcs().size() > 0);
    }

    @Test
    public void testSpawnBosses() {
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
        world.setCycle(40);
        world.setExperience(9000);
        assertTrue(world.possiblySpawnEndOfCycle().size() > 1);
        world.setCycle(40);
        world.setExperience(10000);
        assertTrue(world.possiblySpawnEndOfCycle().size() > 2);
    }

    /**
     * Coverage tests for getters
     */
    @Test
    public void testGetters () {
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
        assertEquals(100, world.getHealthProperty().get());
        assertEquals(0, world.getGoldProperty().get());
        assertEquals(0, world.getExpProperty().get());
        assertEquals(6, world.getHeight());
        assertEquals(8, world.getPath().size());
    }

    /**
     * Test for equipping items
     */
    @Test
    public void testEquippedSlots() {
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

        assertTrue(world.isEquipmentSlotFree(3));
        assertFalse(world.isEquipmentSlotFree(-1));

        world.addEquippedItem(new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));
        world.addEquippedItem(new Shield(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)));
        
        assertFalse(world.isEquipmentSlotFree(3));
        assertFalse(world.isEquipmentSlotFree(0));

        assertTrue(world.isEquipmentSlotFree(1));
        assertTrue(world.isEquipmentSlotFree(2));
    }

    /**
     * Test for unequipping items
     */
    @Test
    public void testUnEquip() {
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

        world.addEquippedItem(new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));
        world.addEquippedItem(new Shield(new SimpleIntegerProperty(2), new SimpleIntegerProperty(1)));
        world.addEquippedItem(new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        world.addEquippedItem(new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        
        world.unequipItem(0);
        assertTrue(world.isEquipmentSlotFree(0));
        world.unequipItem(1);
        assertTrue(world.isEquipmentSlotFree(1));
        world.unequipItem(2);
        assertTrue(world.isEquipmentSlotFree(2));
        world.unequipItem(3);
        assertTrue(world.isEquipmentSlotFree(3));
    }
}
