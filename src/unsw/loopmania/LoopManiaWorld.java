package unsw.loopmania;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Iterator;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.BuildingFactory;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Buildings.TrapBuilding;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Buildings.ZombiePitBuilding;
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
import unsw.loopmania.Enemies.DoggieEnemy;
import unsw.loopmania.Enemies.ElanMuskeEnemy;
import unsw.loopmania.Enemies.EnemyFactory;
import unsw.loopmania.Enemies.SlugEnemy;
import unsw.loopmania.Enemies.VampireEnemy;
import unsw.loopmania.Enemies.ZombieEnemy;

import unsw.loopmania.Helpers.Chance;
import unsw.loopmania.Helpers.Distance;
import unsw.loopmania.Helpers.WorldHelpers;
import unsw.loopmania.Items.Anduril;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.DoggieCoin;
import unsw.loopmania.Items.Equippable;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.ItemFactory;
import unsw.loopmania.Items.Inventory;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Items.TreeStump;
import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Npcs.Adventurer;
import unsw.loopmania.Npcs.Bandit;
import unsw.loopmania.Npcs.Bard;
import unsw.loopmania.Npcs.CombatNpc;
import unsw.loopmania.Npcs.Dragon;
import unsw.loopmania.Npcs.FriendlyCombatNpc;
import unsw.loopmania.Npcs.Golem;
import unsw.loopmania.Npcs.MatthiasPierre;
import unsw.loopmania.Npcs.Mushroom;
import unsw.loopmania.Npcs.NeutralCombatNpc;
import unsw.loopmania.Npcs.UtilityNpc;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    private final int MIN_CARD_OVERFLOW_GOLD = 30;
    private final int MAX_CARD_OVERFLOW_GOLD = 50;
    private final int MIN_CARD_OVERFLOW_EXPERIENCE = 20;
    private final int MAX_CARD_OVERFLOW_EXPERIENCE = 20;

    private final int MIN_ENEMY_KILL_GOLD = 30;
    private final int MAX_ENEMY_KILL_GOLD = 50;

    private final int ENEMY_KILL_ITEM_CHANCE = 30;
    private final int ENEMY_KILL_CARD_CHANCE = 20;

    private final int STARTING_GOLD = 0;

    private final int ELAN_EXP_THRESHOLD = 10000;

    public static final int GAME_WON_ACHIEVED_EXPERIENCE_TARGET = 1;
    public static final int GAME_WON_ACHIEVED_GOLD_TARGET = 2;
    public static final int GAME_WON_ACHIEVED_CYCLE_TARGET = 3;
    public static final int GAME_WON_DEFEATED_ENEMY = 4;
    public static final int GAME_LOST = 5;

    // width and height of the world in GridPane cells
    private int width;
    private int height;

    private int startingX;
    private int startingY;

    // list of x,y coordinate pairs in the order by which moving entities traverse them
    private List<Pair<Integer, Integer>> orderedPath;

    // Only contains four slots (head - 0, weapon - 1, body - 2, shield - 3)
    Inventory inventory;
    //private Equippable[] equippedItems = {null, null, null, null};
    //private List<Item> unequippedInventoryItems;

    private String mode;

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    // Non-item entities
    private Character character;

    private List<BasicEnemy> enemies;
    private List<Card> cardEntities;
    private List<Building> buildingEntities;
    private List<UtilityNpc> utilityNpcs;
    private List<FriendlyCombatNpc> friendlyNpcs;
    private List<NeutralCombatNpc> neutralNpcs;

    private MatthiasPierre matthiasPierre;

    private JSONObject goals;

    private IntegerProperty amountOfGold;
    private IntegerProperty amountOfExperience;
    private IntegerProperty cycleNumber;

    private boolean isPurchased;
    private boolean isRestart = false;
    private int gameStatus = 0;

    private String fileName;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        this.orderedPath = orderedPath;

        this.enemies = new ArrayList<>();
        this.cardEntities = new ArrayList<>();
        this.buildingEntities = new ArrayList<>();

        this.utilityNpcs = new ArrayList<>();
        this.friendlyNpcs = new ArrayList<>();
        this.neutralNpcs = new ArrayList<>();
        this.matthiasPierre = null;

        this.inventory = new Inventory();
        //this.unequippedInventoryItems = new ArrayList<>();

        this.amountOfExperience = new SimpleIntegerProperty(0);
        this.amountOfGold = new SimpleIntegerProperty(0);
        this.cycleNumber = new SimpleIntegerProperty(0);
    }

    /**
     * Reset the state of the world to the beginning
     * However the frontend still needs to be updated
     */
    public void resetWorld() {
        isRestart = false;
        for (Building building : buildingEntities) {
            building.destroy();
        }
        buildingEntities = new ArrayList<Building>();
        for (Card card : cardEntities) {
            card.destroy();
        }
        cardEntities = new ArrayList<Card>();
        for (BasicEnemy enemy : enemies) {
            enemy.destroy();
        }
        enemies = new ArrayList<BasicEnemy>();
        for (UtilityNpc utilityNpc : utilityNpcs) {
            utilityNpc.destroy();
        }
        utilityNpcs = new ArrayList<UtilityNpc>();
        for (FriendlyCombatNpc friend : friendlyNpcs) {
            friend.destroy();
        }
        friendlyNpcs = new ArrayList<FriendlyCombatNpc>();
        for (NeutralCombatNpc neutral : neutralNpcs) {
            neutral.destroy();
        }
        neutralNpcs = new ArrayList<NeutralCombatNpc>();
        if (matthiasPierre != null) {
            matthiasPierre.destroy();
            matthiasPierre = null;
        }
        
        amountOfExperience.set(0);
        amountOfGold.set(STARTING_GOLD);
        cycleNumber.set(0);
    
        character.changeHealth(100);
        character.resetPosition();
        character.resetAlliedSoldiers();
        inventory.clear();
    }

    /**
     * Set the mode of the game
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Get the mode of the game
     * @return
     */
    public String getMode() {
        return this.mode;
    }

    public void setString(String fileName) {
        this.fileName = fileName;
    }

    /**
     * getter for gold integer property for frontend
     * @return IntegerProperty containing gold
     */
    public IntegerProperty getGoldProperty() {
        return amountOfGold;
    }

    /**
     * getter for exp integer property for frontend
     * @return IntegerProperty containing experience
     */
    public IntegerProperty getExpProperty() {
        return amountOfExperience;
    }

    /**
     * getter for health integer property for frontend
     * @return IntegerProperty containing health
     */
    public IntegerProperty getHealthProperty() {
        return character.getHealthProperty();
    }

    /**
     * getter for gold 
     * @return integer containing gold
     */
    public int amountOfGold() {
        return amountOfGold.get();
    }

    /**
     * getter for experience
     * @return integer containing amount of experience
     */
    public int amountOfExperience() {
        return amountOfExperience.get();
    }

    /**
     * setter of experience property for tests
     * @param experience - the experience amount
     */
    public void setExperience(int experience) {
        amountOfExperience.set(experience);
    }

    /**
     * getter for world width
     * @return integer containing width
     */
    public int getWidth() {
        return width;
    }

    /**
     * getter for height
     * @return integer containing height
     */
    public int getHeight() {
        return height;
    }

	/**
     * getter for cycle number property
     * @return integer property for cycle
     */
    public IntegerProperty getCycleNumber() {
        return cycleNumber;
    }

    /**
     * setter of cycle number property for tests
     * @param cycle - the cycle number
     */
    public void setCycle(int cycle) {
        cycleNumber.set(cycle);
    }

    /**
     * getter for path
     * @return ordered list of ordered pairs containing path coordinates
     */
    public List<Pair<Integer, Integer>> getPath() {
        return orderedPath;
    }

    /**
     * Setter for goals
     * @param obj - json object containing goals
     */
    public void setGoals(JSONObject obj) {
        this.goals = obj;
    }

    /**
     * Getter for goals
     * @return json object containing goals
     */
    public JSONObject getGoals() {
        return this.goals;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
        startingX = character.getX();
        startingY = character.getY();
    }

    /*
     * getter for character
     * @return character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Increase the character's maximum health (and
     * increase current health by the same amount)
     * @param health - amount to increase health by
     */
    public void increaseCharacterHealth(int health) {
        character.increaseMaxHealth(health);
    }

    /**
     * Heal the character by a certain amount
     * @param health - amount to heal character by
     */
    public void healCharacter(int health) {
        character.changeHealth(health);
    }

    /*
     * add building entity to world for testing purposes
     * @param building - building to be added
     */
    public void addBuilding(Building building) {
        buildingEntities.add(building);
    }

    /*
     * add enemy to world for testing purposes
     * @param enemy - basic enemy to add
     */
    public void addEnemy(BasicEnemy enemy) {
        enemies.add(enemy);
    }

    /*
     * getter for enemies for testing purposes
     * @return list containing all enemy objects in world
     */
    public List<BasicEnemy> getEnemies() {
        return enemies;
    }

    /*
     * get the utility npcs for testing
     * @return the list of utility npcs
     */
    public List<UtilityNpc> getUtilityNpcs() {
        return utilityNpcs;
    }

    /*
     * add a utility npc to world for testing purposes
     * @param npc - the utility npe to add to the world
     */
    public void addUtilityNpc(UtilityNpc npc) {
        utilityNpcs.add(npc);
    }

    /*
     * add a friendly npc to world for testing purposes
     * @param npc - the friendly npe to add to the world
     */
    public void addFriendly(FriendlyCombatNpc npc) {
        friendlyNpcs.add(npc);
    }

    /*
     * get the friendly npcs for testing
     * @return the list of friendly npcs
     */
    public List<FriendlyCombatNpc> getFriendlyNpcs() {
        return friendlyNpcs;
    }

    /*
     * add a neutral npc to world for testing purposes
     * @param npc - the neutral npe to add to the world
     */
    public void addNeutral(NeutralCombatNpc npc) {
        neutralNpcs.add(npc);
    }

    /*
     * get the neutral npcs for testing
     * @return the list of neutral npcs
     */
    public List<NeutralCombatNpc> getNeutralNpcs() {
        return neutralNpcs;
    }

    /**
     * Spawn the one and only Matthias Pierre to a random path position
     * @return the entity which was created
     */
    public MatthiasPierre createMatthiasPierre() {
        matthiasPierre = new MatthiasPierre(new PathPosition(randomPathIndex(), orderedPath));
        return matthiasPierre;
    }

    /**
     * Set matthias pierre for testing purposes
     * @param matthiasPierre - matthias
     */
    public void setMatthiasPierre(MatthiasPierre matthiasPierre) {
        this.matthiasPierre = matthiasPierre;
    }

    /**
     * Getter for Matthias Pierre
     * @return Matthias Pierre object
     */
    public MatthiasPierre getMatthiasPierre() {
        return matthiasPierre;
    }

    /**
     * Get the index of a random path tile
     * @return the index
     */
    private int randomPathIndex() {
        return orderedPath.indexOf(orderedPath.get(Chance.intRangeChance(0, orderedPath.size() - 1)));
    }
    
    public List<Card> getCards() {
        return cardEntities;
    }

    public List<Building> getBuildings() {
        return buildingEntities;
    }

    /**
     * Adds an equpped item to the equipped list
     * @pre the item slot is empty
     * @param item - the item to be equipped
     */
    public void addEquippedItem(Equippable item) {
        inventory.addEquippedItem(item);
    }

    /**
     * Remove an equipped item by slot number and change character's statistics accordingly
     * @param slot
     * @return the item which has been unequipped
     */
    public Item unequipItem(int slot) {
        return inventory.unequipItem(slot);
    }

    /**
     * Check if equipment slot is free
     * @param slot - equipment slot number
     * @return boolean containing result
     */
    public boolean isEquipmentSlotFree(int slot) {
        return inventory.isEquipmentSlotFree(slot);
    }

    /**
     * Get the item at specfied slot
     * @param slot - the slot number to be accessed
     * @return null if slot not equipped or invalid slot, the item otherwise
     */
    public Equippable getEquippedItemBySlot(int slot) {
        return inventory.getEquippedItemBySlot(slot);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnSlugs(){
        // Spawn slugs
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            SlugEnemy newSlug = new SlugEnemy(new PathPosition(indexInPath, orderedPath));
            enemies.add(newSlug);
            spawningEnemies.add(newSlug);
        }
        return spawningEnemies;
    }

    /**
     * Potentially spawn enemies at the end of a cycle
     * @return list of spawning enemies
     */
	 public List<MovingEntity> possiblySpawnEndOfCycle() {
        List<MovingEntity> spawningEntities = new ArrayList<MovingEntity>();
        for (Building building : buildingEntities) {
            if (building instanceof ZombiePitBuilding) {
                ZombiePitBuilding zombPit = (ZombiePitBuilding) building;
                List<Integer> possibleIndexes = getPotentialSpots(zombPit);
                // Spawn a random amount of zombies between 0-2
                Random rand = new Random();
                zombPit.specialAbility(character, enemies);
                int numToSpawn = zombPit.getNumZombiesSpawn();
                // Choose random spots from possibleIndexes
                for (int i = 0; i <= numToSpawn; i++) {
                    ZombieEnemy newZombie = new ZombieEnemy(new PathPosition(possibleIndexes.get(rand.nextInt(possibleIndexes.size())), orderedPath));
                    enemies.add(newZombie);
                    spawningEntities.add(newZombie);
                }
            } else if (building instanceof VampireCastleBuilding) {
                VampireCastleBuilding vampCastle = (VampireCastleBuilding) building;
                if ((cycleNumber.get() - vampCastle.getRoundSpawned()) % 5 == 0) {
                    vampCastle.specialAbility(character, enemies);
                    // Get a random number of vampires to spawn
                    int numToSpawn = vampCastle.getNumVampiresSpawn();

                    List<Integer> possibleIndexes = getPotentialSpots(building);

                    // Choose random spots to spawn the vampires
                    Random rand = new Random();
                    for (int i = 0; i <= numToSpawn; i++) {
                        VampireEnemy newVamp = new VampireEnemy(new PathPosition(possibleIndexes.get(rand.nextInt(possibleIndexes.size())), orderedPath), buildingEntities);
                        enemies.add(newVamp);
                        spawningEntities.add(newVamp);
                    }
                }
                
            }
        }
        if (cycleNumber.get() % 20  == 0) {
            DoggieEnemy dog = new DoggieEnemy(new PathPosition(randomPathIndex(), orderedPath));
            spawningEntities.add(dog);
            enemies.add(dog);
        }
        if (cycleNumber.get() % 40 == 0 && amountOfExperience.get() >= ELAN_EXP_THRESHOLD) {
            ElanMuskeEnemy elan = new ElanMuskeEnemy(new PathPosition(randomPathIndex(), orderedPath));
            spawningEntities.add(elan);
            enemies.add(elan);
        }
        if (Chance.binomialChance(2)) {
            Dragon dragon = new Dragon(new PathPosition(randomPathIndex(), orderedPath));
            spawningEntities.add(dragon);
            neutralNpcs.add(dragon);
        }
        int npcSelector = Chance.intRangeChance(0, 2);
        switch (npcSelector) {
            case 0:
                // mushroom spawns at a random location on the path
                Mushroom mushroom = new Mushroom(new PathPosition(randomPathIndex(), orderedPath), enemies);
                spawningEntities.add(mushroom);
                utilityNpcs.add(mushroom);
                break;
            case 1:
                // bard spawns at the character's location
                Bard bard = new Bard(new PathPosition(character.getPositionInPath(), orderedPath));
                spawningEntities.add(bard);
                utilityNpcs.add(bard);
                break;
            case 2:
                // 1 - 3 bandits all spawn at the same random location on the path
                int nBandits = Chance.intRangeChance(1, 3);
                int counter = 0;
                int index = randomPathIndex();
                while (counter < nBandits) {
                    Bandit bandit = new Bandit(new PathPosition(index, orderedPath));
                    spawningEntities.add(bandit);
                    neutralNpcs.add(bandit);
                    counter ++;
                }
                break;
        } 

        return spawningEntities;
    }

    /**
     * Increase gold and experience by the correct amount given an entity
     * @param entity - the defeated entity
     */
    public void increaseGoldExperience(MovingEntity entity) {
        if (entity instanceof BasicEnemy) {
            amountOfGold.set(amountOfGold.get() + Chance.intRangeChance(MIN_ENEMY_KILL_GOLD, MAX_ENEMY_KILL_GOLD));
            amountOfExperience.set(amountOfExperience.get() + ((BasicEnemy )entity).getExperience());
        } else if (entity instanceof NeutralCombatNpc) {
            amountOfGold.set(amountOfGold.get() + ((NeutralCombatNpc )entity).getGold());
            amountOfExperience.set(amountOfExperience.get() + ((NeutralCombatNpc )entity).getExperience());
        }
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    public void killEnemy(BasicEnemy enemy){   
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * kill a friendly npc
     * @param friend - the friendly npc to be killed
     */
    public void killFriendly(FriendlyCombatNpc friend) {
        friend.destroy();
        friendlyNpcs.remove(friend);
    }

    /**
     * kill a friendly npc
     * @param friend - the friendly npc to be killed
     */
    public void killNeutral(NeutralCombatNpc neutral) {
        neutral.destroy();
        neutralNpcs.remove(neutral);
    }

    /**
     * Randomly select a card as a reward
     * @return a card or null chosen by chance
     */
    public Card getRewardCard() {
        if (Chance.binomialChance(ENEMY_KILL_CARD_CHANCE)) {
            return loadCard();
        }
        return null;
    }

    /**
     * Randomly select an item as a reward
     * @param entity - the defeated entity
     * @return an item or null chosen by chance
     */
    public Item getRewardItem(MovingEntity entity) {
        if ((entity instanceof BasicEnemy || entity instanceof NeutralCombatNpc) && Chance.binomialChance(ENEMY_KILL_ITEM_CHANCE)) {
            return addUnequippedItem();
        }
        return null;
    }

    /**
     * Use health potion
     * If no health potion in inventory do nothing (change health by 0)
     */
    public void useHealthPotion() {
        character.changeHealth(inventory.useHealthPotion());
    }

    /**
     * Check whether the character has reached the win conditions
     * @return
     */
    public boolean checkWin() {
        Iterator<String> keys = goals.keys();
        String typeOfGoal = "";
        int desiredQuantity = 0;
        while(keys.hasNext()) {
            String key = keys.next();
            if (key.equals("goal")) {
                typeOfGoal = goals.getString(key);
            } else {
                desiredQuantity = goals.getInt(key);
            } 
        }
        if (typeOfGoal.equals("experience")) {
            if (amountOfExperience.get() >= desiredQuantity) {
                gameStatus = LoopManiaWorld.GAME_WON_ACHIEVED_EXPERIENCE_TARGET;
                return true;
            }
        }
        if (typeOfGoal.equals("gold")) {
            if (amountOfGold.get() >= desiredQuantity) {
                gameStatus = LoopManiaWorld.GAME_WON_ACHIEVED_GOLD_TARGET;
                return true;
            }
        }
        if (typeOfGoal.equals("cycles")) {
             if (cycleNumber.get() >= desiredQuantity) {
                gameStatus = LoopManiaWorld.GAME_WON_ACHIEVED_CYCLE_TARGET;
                return true;
            }
        }
        return false;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int status) {
        this.gameStatus = status;
    }
    
    public String goalType() {
        Iterator<String> keys = goals.keys();
        String typeOfGoal = "";
        while(keys.hasNext()) {
            String key = keys.next();
            if (key.equals("goal")) {
                typeOfGoal = goals.getString(key);
            } 
        }
        return typeOfGoal;
    }

    public int goalNum() {
        Iterator<String> keys = goals.keys();
        int desiredQuantity = 0;
        while(keys.hasNext()) {
            String key = keys.next();
            if (!key.equals("goal")) {
                desiredQuantity = goals.getInt(key);
            } 
        }
        return desiredQuantity;
    }
    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        // Get all the entities involved in the battles
        boolean isCampfire = WorldHelpers.inCampfireRadius(character, buildingEntities);
        boolean isShield = (inventory.getEquippedItemBySlot(Inventory.SHIELD_SLOT) != null);
        Weapon weapon = (Weapon )inventory.getEquippedItemBySlot(Inventory.WEAPON_SLOT);

        List<AlliedSoldier> alliedSoldiers = character.getListOfAllies();
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        List<BasicEnemy> supportingEnemies = new ArrayList<BasicEnemy>();
        List<BasicEnemy> trancedEnemies = new ArrayList<BasicEnemy>();
        List<TowerBuilding> towers = WorldHelpers.getTowers(character, buildingEntities);
        List<FriendlyCombatNpc> friendlys = WorldHelpers.getFriendlys(character, friendlyNpcs);

        BasicEnemy targetEnemy = WorldHelpers.getMainEnemy(character.getX(), character.getY(), matthiasPierre, enemies);
        // Add all the supporting enemies of the target enemy
        if (targetEnemy != null) {
            supportingEnemies.addAll(WorldHelpers.getSupportingEnemies(character.getX(), character.getY(), matthiasPierre, targetEnemy, enemies));
        }
        outer:
        while (targetEnemy != null) {
            if (!character.getIsStunned()) {
                // Character attacks
                // Apply character damage
                targetEnemy.reduceHealth(inventory.modifyOutgoingDamage(character.getBaseDamage(), isCampfire), weapon);
                // If trance is successful then move enemy to character then if no more enemies
                if (targetEnemy.getHealth() <= 0) {
                    defeatedEnemies.add(targetEnemy);
                    killEnemy(targetEnemy);
                    if (supportingEnemies.size() == 0) {
                        break;
                    } else {
                        targetEnemy = supportingEnemies.remove(0);
                    }
                } else if (inventory.trance()) {
                    targetEnemy.changePosition(character.returnPosition());
                    targetEnemy.randomTrancedRounds();
                    trancedEnemies.add(targetEnemy);
                    if (supportingEnemies.size() == 0) {
                        break;
                    } else {
                        targetEnemy = supportingEnemies.remove(0);
                    }
                }
            } else {
                character.setStunned(false);
            }
            // allied soldiers all attack
            for (AlliedSoldier soldier : alliedSoldiers) {
                targetEnemy.reduceHealth(soldier.getBaseDamage(), null);
                if (targetEnemy.getHealth() <= 0) {
                    defeatedEnemies.add(targetEnemy);
                    killEnemy(targetEnemy);
                    if (supportingEnemies.size() == 0) {
                        break outer;
                    } else {
                        targetEnemy = supportingEnemies.remove(0);
                    }
                }
            }
            // friendly npcs all attack
            for (FriendlyCombatNpc friend : friendlys) {
                targetEnemy.reduceHealth(friend.attack(), null);
                if (targetEnemy.getHealth() <= 0) {
                    defeatedEnemies.add(targetEnemy);
                    killEnemy(targetEnemy);
                    if (supportingEnemies.size() == 0) {
                        break outer;
                    } else {
                        targetEnemy = supportingEnemies.remove(0);
                    }
                }
            }
            // tranced enemies attack
            for (BasicEnemy enemy : trancedEnemies) {
                targetEnemy.reduceHealth(enemy.attack(false), null);
                if (targetEnemy.getHealth() <= 0) {
                    defeatedEnemies.add(targetEnemy);
                    killEnemy(targetEnemy);
                    if (supportingEnemies.size() == 0) {
                        break outer;
                    } else {
                        targetEnemy = supportingEnemies.remove(0);
                    }
                }
            }
            // all towers attack
            for (TowerBuilding tower : towers) {
                targetEnemy.reduceHealth(tower.getAttack(), null);
                if (targetEnemy.getHealth() <= 0) {
                    defeatedEnemies.add(targetEnemy);
                    killEnemy(targetEnemy);
                    if (supportingEnemies.size() == 0) {
                        break outer;
                    } else {
                        targetEnemy = supportingEnemies.remove(0);
                    }
                }
            }
            // enemies attack back
            List<BasicEnemy> turned = new ArrayList<>();
            List<BasicEnemy> allAttackingEnemies = new ArrayList<>();
            allAttackingEnemies.add(targetEnemy);
            allAttackingEnemies.addAll(supportingEnemies);
            for (BasicEnemy enemy : allAttackingEnemies) {
                if (alliedSoldiers.size() > 0) {
                    AlliedSoldier targetSoldier = alliedSoldiers.get(0);
                    targetSoldier.takeDamage(-enemy.attack(false));
                    if (targetSoldier.getHealth() <= 0) {
                        alliedSoldiers.remove(targetSoldier);
                    } else if (targetEnemy.turn()) {
                        supportingEnemies.add(WorldHelpers.turnAlliedSoldier(character, orderedPath, alliedSoldiers, enemies));
                    }
                } else if (friendlys.size() > 0) {
                    FriendlyCombatNpc targetFriend = friendlys.get(0);
                    if (targetFriend.reduceHealth(enemy.attack(false)) <= 0) {
                        killFriendly(targetFriend);
                        friendlys.remove(targetFriend);
                    }
                } else if (trancedEnemies.size() > 0) {
                    BasicEnemy targetTranced = trancedEnemies.get(0);
                    targetTranced.reduceHealth(enemy.attack(false), null);
                    if (targetTranced.getHealth() <= 0) {
                        trancedEnemies.remove(targetTranced);
                        defeatedEnemies.add(targetTranced);
                    }
                } else {
                    character.changeHealth(-inventory.modifyIncomingDamage(enemy.attack(isShield), enemy));
                    if (character.getHealth() <= 0) {
                        if (inventory.getOneRingEquipped(character)) {
                        } else if (mode != null && mode.equals("Confusing")) {
                            for (Item item : inventory.getUnequippedItems()) {
                                if (item instanceof Anduril) {
                                    Anduril newAnduril = (Anduril) item;
                                    if (newAnduril.additionalItem().equals("TheOneRing") && newAnduril.numOfCharges() > 0) {
                                        newAnduril.reduceCharges();
                                        character.changeHealth(100);
                                        break;
                                    }
                                } else if (item instanceof TreeStump) {
                                    TreeStump newTreeStump = (TreeStump) item;
                                    if (newTreeStump.additionalItem().equals("TheOneRing") && newTreeStump.numOfCharges() > 0) {
                                        newTreeStump.reduceCharges();
                                        character.changeHealth(100);
                                        break;
                                    }
                                }
                            }
                        } else {
                            // resetWorld();
                            return null;
                        }
                    } else {
                        character.setStunned(enemy.stun());
                    }
                }
                if (enemy instanceof ElanMuskeEnemy) {
                    ((ElanMuskeEnemy )enemy).healEnemies(allAttackingEnemies);
                }
            }
            // add all turned allied soldiers to list of supporting enemies to avoid concurrent modification
            supportingEnemies.addAll(turned);

            List<BasicEnemy> removedTranced = new ArrayList<BasicEnemy>();
            for (BasicEnemy enemy : trancedEnemies) {
                enemy.decrementTrancedRounds();
                if (enemy.trancedRoundsRemaning() == 0) {
                    removedTranced.add(enemy);
                    supportingEnemies.add(enemy);
                }
            }
            trancedEnemies.removeAll(removedTranced);
        }
        // Kill all tranced enemies after the battle
        for (BasicEnemy enemy: trancedEnemies) {
            killEnemy(enemy);  
        }
        defeatedEnemies.addAll(trancedEnemies);
        return defeatedEnemies;
    }

    /**
     * Every enemy attacks the closest combat npc if one is in range
     */
    public void enemiesVsNpcs() {
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        // enemies attack npcs one at a time
        outer:
        for (BasicEnemy enemy : enemies) {
            CombatNpc targetNpc = getClosestCombatNpc(enemy);
            // fight until either the enemy or npc dies
            while (targetNpc != null) {
                if (targetNpc.reduceHealth(enemy.attack(false)) <= 0) {
                    if (targetNpc instanceof FriendlyCombatNpc) { 
                        killFriendly((FriendlyCombatNpc )targetNpc);
                    }
                    else {
                        killNeutral((NeutralCombatNpc )targetNpc);
                    }
                    continue outer;
                }
                if (enemy.reduceHealth(targetNpc.attack(), null) <= 0) {
                    defeatedEnemies.add(enemy);
                    continue outer;
                }
            }
        }
        for (BasicEnemy enemy : defeatedEnemies) {
            killEnemy(enemy);
        }
    }

    public List<NeutralCombatNpc> characterVsNpcs() {
        List<NeutralCombatNpc> defeatedNeutrals = new ArrayList<NeutralCombatNpc>();

        boolean isCampfire = WorldHelpers.inCampfireRadius(character, buildingEntities);
        List<AlliedSoldier> alliedSoldiers = character.getListOfAllies();
        List<TowerBuilding> towers = WorldHelpers.getTowers(character, buildingEntities);
        List<FriendlyCombatNpc> friendlys = WorldHelpers.getFriendlys(character, friendlyNpcs);

        outer:
        for (NeutralCombatNpc neutral : neutralNpcs) {
            while (neutral.checkRadius(character.getX(), character.getY())) {
                if (neutral.reduceHealth(inventory.modifyOutgoingDamage(character.getBaseDamage(), isCampfire)) <= 0 || inventory.trance()) {
                    defeatedNeutrals.add(neutral);
                    continue outer;
                }
                for (AlliedSoldier soldier : alliedSoldiers) {
                    if (neutral.reduceHealth(soldier.getBaseDamage()) <= 0) {
                        defeatedNeutrals.add(neutral);
                        continue outer;
                    }
                }
                for (FriendlyCombatNpc friendly : friendlys) {
                    if (neutral.reduceHealth(friendly.attack()) <= 0) {
                        defeatedNeutrals.add(neutral);
                        continue outer;
                    }
                }
                for (TowerBuilding tower : towers) {
                    if (neutral.reduceHealth(tower.getAttack()) <= 0) {
                        defeatedNeutrals.add(neutral);
                        continue outer;
                    }
                }
                if (alliedSoldiers.size() > 0) {
                    AlliedSoldier targetSoldier = alliedSoldiers.get(0);
                    targetSoldier.takeDamage(-neutral.attack());
                    if (targetSoldier.getHealth() <= 0) {
                        alliedSoldiers.remove(targetSoldier);
                    }
                } else if (friendlys.size() > 0) {
                    FriendlyCombatNpc targetFriendly = friendlys.get(0);
                    if (targetFriendly.reduceHealth(neutral.attack()) <= 0) {
                        friendlys.remove(targetFriendly);
                        killFriendly(targetFriendly);
                    }
                } else {
                    character.changeHealth(-inventory.modifyIncomingDamage(neutral.attack(), null));
                    if (character.getHealth() <= 0) {
                        if (!inventory.getOneRingEquipped(character)) {
                            resetWorld();
                            return null;
                        }
                    }
                }
            }
        }
        for (NeutralCombatNpc neutral : defeatedNeutrals) {
            killNeutral(neutral);
        }
        return defeatedNeutrals;
    }

    private CombatNpc getClosestCombatNpc(BasicEnemy enemy) {
        CombatNpc result = null;
        int lowestDistance = -1;
        for (FriendlyCombatNpc friend : friendlyNpcs) {
            if (
                enemy.checkBattleRadius(friend.getX(), friend.getY()) &&
                (Distance.calculate(enemy.getX(), enemy.getY(), friend.getX(), friend.getY()) < lowestDistance || lowestDistance == -1)
            ) {
                result = (CombatNpc )friend;
            }
        }
        for (NeutralCombatNpc neutral : neutralNpcs) {
            if (
                enemy.checkBattleRadius(neutral.getX(), neutral.getY()) &&
                (Distance.calculate(enemy.getX(), enemy.getY(), neutral.getX(), neutral.getY()) < lowestDistance || lowestDistance == -1)
            ) {
                result = (CombatNpc )neutral;
            }
        }
        return result;
    }

    /**
     * spawn a random card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard(){
        Card card = null;
        // If card inventory is full
        if (cardEntities.size() == 8) {
            removeCard(0);
            amountOfGold.set(amountOfGold.get() + Chance.intRangeChance(MIN_CARD_OVERFLOW_GOLD, MAX_CARD_OVERFLOW_GOLD));
            amountOfExperience.set(amountOfExperience.get() + Chance.intRangeChance(MIN_CARD_OVERFLOW_EXPERIENCE, MAX_CARD_OVERFLOW_EXPERIENCE));
            // Give it a 50 50 chance of getting an item drop
            if (Chance.binomialChance(50)) {
                addUnequippedItem();
            }
        }
        SimpleIntegerProperty cardX = new SimpleIntegerProperty(cardEntities.size());
        SimpleIntegerProperty cardY = new SimpleIntegerProperty(0);

        switch (Chance.intRangeChance(0, 9)) {
            case 0:
                card = new VampireCastleCard(cardX, cardY);
                break;
            case 1:
                card = new ZombiePitCard(cardX, cardY);
                break;
            case 2:
                card = new TowerCard(cardX, cardY);
                break;
            case 3:
                card = new VillageCard(cardX, cardY);
                break;
            case 4:
                card = new BarracksCard(cardX, cardY);
                break;
            case 5:
                card = new TrapCard(cardX, cardY);
                break;
            case 6:
                card = new CampfireCard(cardX, cardY);
                break;
            case 7:
                card = new AdventurerGuildCard(cardX, cardY, buildingEntities, enemies);
                break;
            case 8:
                card = new PokerBallCard(cardX, cardY, buildingEntities, this);
                break;
            default:
                card = new ClearEnemiesCard(cardX, cardY, buildingEntities, enemies);
        }
        cardEntities.add(card);
        return card;
    }

    /**
     * Getter for the number of cards
     * @return integer containing number of cards
     */
    public int numOfCards() {
        return this.cardEntities.size();
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * spawn a random item in the world and return it
     * @return an item to be spawned in the controller as a JavaFX node
     */
    public Item addUnequippedItem(){
        return inventory.addUnequippedItem(amountOfGold, amountOfExperience);
    }

    /**
     * Add an item object to the unequipped inventory
     * @pre there is sufficient room in the unequipped inventory
     * @param item - the item to be added
     */
    public void addUnequippedItem(Item item) {
        inventory.addUnequippedItem(item);
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Item item){
        inventory.removeUnequippedInventoryItem(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Item getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        return inventory.getUnequippedInventoryItemEntityByCoordinates(x, y);
    }

    public List<Item> getUnequippedInventoryItems() {
        return inventory.getUnequippedItems();
    } 

    /**
     * Getter for number of unequipped items
     * @return int containing result
     */
    public int numUnequippedItems() {
        return inventory.numUnequippedItems();
    }

    /**
     * Getter for number of free slots in unequipped inventory
     * @return int containing result
     */
    public int getNumberOfFreeSlotsInUnequippedInventory() {
        return unequippedInventoryHeight * unequippedInventoryWidth - inventory.numUnequippedItems();
    }

    public boolean completedCycle() {
        if (character.getX() == startingX && character.getY() == startingY) {
            cycleNumber.set(cycleNumber.get() + 1);
            return true;
        }
        return false;
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public List<FriendlyCombatNpc> runTickMoves(){
        inventory.setMode(mode);
        character.moveDownPath();
        moveBasicEnemies();
        List<TrapBuilding> usedTraps = new ArrayList<>();
        List<FriendlyCombatNpc> spawningAllies = new ArrayList<FriendlyCombatNpc>();
        // Applys the buildings that have effects
        for (Building building : buildingEntities) {
            if (building instanceof BarracksBuilding) {
                BarracksBuilding newBarracks = (BarracksBuilding) building;
                newBarracks.specialAbility(character, enemies);
            }
            if (building instanceof VillageBuilding) {
                VillageBuilding newVillage = (VillageBuilding) building;
                newVillage.specialAbility(character, enemies);
                // upgraded village also spawns an adventurer when passing through
                if (newVillage.hasAdventurerGuild() && newVillage.getX() == character.getX() && newVillage.getY() == character.getY()) {
                    Pair<Integer, Integer> coords = new Pair<Integer, Integer>(character.getX(), character.getY());
                    spawningAllies.add(new Adventurer(new PathPosition(orderedPath.indexOf(coords), orderedPath)));
                }

            }
            if (building instanceof TrapBuilding) {
                TrapBuilding newTrap = (TrapBuilding) building;
                newTrap.specialAbility(character, enemies);
                if (newTrap.getExists() == false) {
                    usedTraps.add(newTrap);            
                }
            }
        }
        for (TrapBuilding trap : usedTraps) {
            trap.destroy();
            buildingEntities.remove(trap);
        }
        // golem has 0.1% chance to spawn at the character's location every move
        if (Chance.binomialChance(1) && Chance.binomialChance(10)) {
            Pair<Integer, Integer> coords = new Pair<Integer, Integer>(character.getX(), character.getY());
            spawningAllies.add(new Golem(new PathPosition(orderedPath.indexOf(coords), orderedPath)));
        }
        // destroy unlucky utility npcs which enemies are able to attack
        destroyUtilityNpcs();
        // apply utility npc effects
        List<UtilityNpc> usedUpUtility = new ArrayList<UtilityNpc>();
        for (UtilityNpc utilityNpc : utilityNpcs) {
            if (utilityNpc.checkRadius(character.getX(), character.getY())) {
                if (utilityNpc.specialAbility(this)) {
                    usedUpUtility.add(utilityNpc);
                }
            }
        }
        for (UtilityNpc usedUtility : usedUpUtility) {
            utilityNpcs.remove(usedUtility);
            usedUtility.destroy();
        }
        // move npcs
        moveNpcs();
        // Vary doggie coin value
        inventory.fluctateValueOfDoggieCoin();
        friendlyNpcs.addAll(spawningAllies);
        return spawningAllies;
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (BasicEnemy e: enemies){
            e.move();
        }
    }

    private void moveNpcs() {
        if (matthiasPierre != null) {
            matthiasPierre.move();
        }
        for (UtilityNpc utilityNpc : utilityNpcs) {
            utilityNpc.move();
        }
        for (FriendlyCombatNpc friend : friendlyNpcs) {
            friend.move();
        }
        for (NeutralCombatNpc neutral : neutralNpcs) {
            neutral.move();
        }
    }

    /**
     * Destroy utility npcs if they are destructible and 
     * are within an enemy's attack range
     * @param x
     */
    private void destroyUtilityNpcs() {
        List<UtilityNpc> killedNpcs = new ArrayList<UtilityNpc>();
        for (UtilityNpc utilityNpc : utilityNpcs) {
            if (utilityNpc.getDestroyable()) {
                for (BasicEnemy enemy : enemies) {
                    if (enemy.checkBattleRadius(utilityNpc.getX(), utilityNpc.getY())) {
                        utilityNpc.destroy();
                        killedNpcs.add(utilityNpc);
                    }
                }
            }
        }
        for (UtilityNpc deadNpc : killedNpcs) {
            utilityNpcs.remove(deadNpc);
        }
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition(){
        Random rand = new Random();
        int choice = rand.nextInt(2); 
        if (orderedPath.size() == 1) {
            return null;
        } else if ((choice == 0) && (enemies.size() < 2)) {
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * remove a card by its x, y coordinates
     * @pre the coordinate is for a building card
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = getCardByCoordinates(cardNodeX, cardNodeY);
        Building newBuilding = null;

        if (card != null && card instanceof BuildingCard) {
            // now spawn building
            newBuilding = ((BuildingCard )card).createBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            if (newBuilding instanceof VampireCastleBuilding) {
                VampireCastleBuilding newVampire = (VampireCastleBuilding) newBuilding;
                newVampire.setRoundSpawned(cycleNumber.get());
            }
            buildingEntities.add(newBuilding);

            // destroy the card
            card.destroy();
            cardEntities.remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
        }

        return newBuilding;
    }

    /**
     * Remove a card from world 
     * @param card - the card to be removed
     */
    public void removeCard(Card card) {
        cardEntities.remove(card);
    }

    /**
     * Helper for above function which gets card by its coordinates
     * @param x - x coordinate of the card
     * @param y - y coordinate of the card
     * @return - the card object with these coordinates
     */
    public Card getCardByCoordinates(int x, int y) {
        Card result = null;
        for (Card c: cardEntities){
            if ((c.getX() == x) && (c.getY() == y)){
                result = c;
                break;
            }
        }
        return result;
    }
    /**
     * Getter for number of buildings
     * @return integer containing number of buildings
     */
    public int numOfBuildings() {
        return this.buildingEntities.size();
    }

    // Using the ordered list and determining the possible spawn radius of zombie/vampire building
    // Return a list of the potential spots that these enemies can be spawned
    // Function return value is passed through to the spawnZombies/spawnVampires functions in their respective classes
    public List<Integer> getPotentialSpots(Object object) {
        int radius = 0;
        int xChar = 0;
        int yChar = 0;
        if (object instanceof ZombiePitBuilding) {
            radius = 4;
            ZombiePitBuilding building = (ZombiePitBuilding) object;
            xChar = building.getX();
            yChar = building.getY();
        }
        if (object instanceof VampireCastleBuilding) {
            radius = 5;
            VampireCastleBuilding building = (VampireCastleBuilding) object;
            xChar = building.getX();
            yChar = building.getY();
        }

        List<Integer> potentialPathSpots = new ArrayList<Integer>();
        for (int i = 0; i < orderedPath.size(); i++) {
            Pair<Integer, Integer> coordPair = orderedPath.get(i);
            int xCoord = coordPair.getValue0();
            int yCoord = coordPair.getValue1();
            if (Math.sqrt(Math.pow(xChar - xCoord, 2) + Math.pow(yChar - yCoord, 2)) <= radius) {
                if (character.getX() == xCoord && character.getY() == yCoord) {
                    continue;
                } else {
                    potentialPathSpots.add(i);
                }
            }
        }
        return potentialPathSpots;
    }

    /**
     * Check if character is at the starting position
     * @return boolean containing result
     */
    public boolean isCharacterAtHeroCastleBuilding() {
        boolean result = false;
        if (character.getX() == startingX && character.getY() == startingY) {
            result = true;
        }
        return result;
    }

//- For purchasing
    public void purchase(String type, int quantity) {
        Item item;
        for (int counter = 0; counter < quantity; counter++) {
            if ((item = ItemFactory.generateItem(type, 0, 0)) != null) {
                inventory.addUnequippedItem(item);
            }
        }
    }

    public void sell(String type, int quantity) {
        List<Item> removeItems = new ArrayList<Item>();
        for (int j = 0; j < inventory.getUnequippedItems().size(); j++) {
            if (inventory.getUnequippedItems().get(j).getType().equals(type)) {
                removeItems.add(inventory.getUnequippedItems().get(j));
                quantity--;
                if (quantity == 0) {
                    break;
                }
            }
        }
        inventory.getUnequippedItems().removeAll(removeItems);
    }

    /**
     * Check if any item has been purchased recently
     * @return boolean containing result
     */
    public boolean isPurchased() {
        return isPurchased;
    }

    public boolean isRestartGame() {
        return isRestart;
    }

    public void setIsRestart(boolean val) {
        isRestart = val;
    }

    public void resetUnequippedCoords() {
        inventory.resetUnequippedCoords();
    }

    /**
     * Setter for purchase check
     * @param val - change the status of recent purchase
     */
    public void setIsPurchased(boolean val) {
        isPurchased = val;
    }

    public void saveWorld() {
        FileWriter file = null;
        JSONObject newObj = new JSONObject();
        newObj.put("fileName", fileName);
        newObj.put("Mode", mode);
        newObj.put("Gold", amountOfGold());
        newObj.put("Experience", amountOfExperience());
        newObj.put("Cycles", cycleNumber.get());
        newObj.put("Health", character.getHealth());
        newObj.put("Position", character.returnPosition().getCurrentPosition());
        newObj.put("AlliedSoldiers", character.getListOfAllies().size());

        JSONArray spawnedEnemies = new JSONArray();
        for (BasicEnemy enemy : enemies) {
            JSONObject enemyObj = new JSONObject();
            enemyObj.put("Type", enemy.getType());
            enemyObj.put("Health", enemy.getHealth());
            enemyObj.put("Position", enemy.returnPosition().getCurrentPosition());
            spawnedEnemies.put(enemyObj);
        }
        newObj.put("Enemies", spawnedEnemies);

        JSONArray spawnedBuildings = new JSONArray();
        for (Building building : buildingEntities) {
            JSONObject buildingObj = new JSONObject();
            buildingObj.put("Type", building.getType());
            buildingObj.put("x", building.getX());
            buildingObj.put("y", building.getY());
            if (building instanceof VampireCastleBuilding) {
                VampireCastleBuilding vamp = (VampireCastleBuilding) building;
                buildingObj.put("roundSpawned", vamp.getRoundSpawned());
            }
            spawnedBuildings.put(buildingObj);
        }
        newObj.put("Buildings", spawnedBuildings);

        JSONArray savedCards = new JSONArray();
        for (Card card : cardEntities) {
            JSONObject cardObj = new JSONObject();
            cardObj.put("Type", card.getType());
            cardObj.put("x", card.getX());
            cardObj.put("y", card.getY());
            savedCards.put(cardObj);
        }

        newObj.put("Cards", savedCards);

        JSONArray equippedItems = new JSONArray();
        for (Map.Entry<Integer, Equippable> entry : inventory.returnEquippedItems().entrySet()) {
            Equippable item = entry.getValue();
            if (item == null) {
                continue;
            }
            JSONObject equippedObj = new JSONObject();
            equippedObj.put("Type", item.getType());
            equippedObj.put("x", item.getEquippedX());
            equippedObj.put("y", item.getEquippedY());
            equippedItems.put(equippedObj);
        }

        newObj.put("EquippedItems", equippedItems);

        JSONArray unequippedItems = new JSONArray();
        for (Item item : inventory.getUnequippedItems()) {
            JSONObject unequippedObj = new JSONObject();
            unequippedObj.put("Type", item.getType());
            unequippedObj.put("x", item.getX());
            unequippedObj.put("y", item.getY());
            unequippedItems.put(unequippedObj);
        }

        newObj.put("UnequippedItems", unequippedItems);

        try {
            file = new FileWriter("saveFile.json");
            file.write(newObj.toString());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadWorld() throws JSONException, FileNotFoundException {
        JSONObject json = new JSONObject(new JSONTokener(new FileReader("saveFile.json")));
        amountOfGold.set(json.getInt("Gold"));
        amountOfExperience.set(json.getInt("Experience"));
        cycleNumber.set(json.getInt("Cycles"));
        mode = json.getString("Mode");
        character.changeHealth(json.getInt("Health") - character.getHealth());
        character.returnPosition().changePosition(json.getInt("Position"));

        for (int i = 0; i < json.getInt("AlliedSoldiers"); i++) {
            character.addAlliedSoldier();
        }

        JSONArray equippedItems = json.getJSONArray("EquippedItems");
        for (int i = 0; i < equippedItems.length(); i++) {
            JSONObject item = equippedItems.getJSONObject(i);
            String type = item.getString("Type");
            inventory.addEquippedItem((Equippable)ItemFactory.generateItem(type, item.getInt("x"), item.getInt("y")));
        }

        JSONArray unequippedItems = json.getJSONArray("UnequippedItems");
        for (int i = 0; i < unequippedItems.length(); i++) {
            JSONObject item = unequippedItems.getJSONObject(i);
            String type = item.getString("Type");
            inventory.addUnequippedItem(ItemFactory.generateItem(type, item.getInt("x"), item.getInt("y")));
        }
        JSONArray cards = json.getJSONArray("Cards");
        for (int i = 0; i < cards.length(); i++) {
            JSONObject card = cards.getJSONObject(i);
            String type = card.getString("Type");
            cardEntities.add(CardFactory.generateItem(type, card.getInt("x"), card.getInt("y")));

        }

        JSONArray buildings = json.getJSONArray("Buildings");
        for (int i = 0; i < buildings.length(); i++) {
            JSONObject building = buildings.getJSONObject(i);
            String type = building.getString("Type");
            buildingEntities.add(BuildingFactory.generateItem(type, building.getInt("x"), building.getInt("y")));
        }

        JSONArray enemiesArray = json.getJSONArray("Enemies");
        for (int i = 0; i < enemiesArray.length(); i++) {
            JSONObject enemyObj = enemiesArray.getJSONObject(i);
            String type = enemyObj.getString("Type");
            enemies.add(EnemyFactory.generateEnemy(type, enemyObj.getInt("Health"), new PathPosition(enemyObj.getInt("Position"), orderedPath), buildingEntities));
        }
    }
}