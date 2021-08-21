package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import unsw.loopmania.Cards.BuildingCard;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.UtilityCard;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Items.Equippable;
import unsw.loopmania.Items.Inventory;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Npcs.FriendlyCombatNpc;
import unsw.loopmania.Npcs.NeutralCombatNpc;
import unsw.loopmania.Buildings.Building;
/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM,
    ITEM_EQUIPPED
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;
    @FXML
    private HBox myHbox;
    @FXML
    private GridPane exitGrid;

    @FXML
    private GridPane titleGrid;

    @FXML
    private TextArea gameTitle;

    @FXML
    private Button exitButton;

    @FXML 
    private GridPane musicGrid;

    @FXML
    private Button musicButton;
    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;
    @FXML
    private GridPane mainGrid;

    @FXML
    private VBox itemSection;
    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;
    @FXML
    private Separator myseparator;
    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;
    private Map<Integer, Node> equippedImages;

    @FXML
    private GridPane unequippedInventory;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    boolean isOutcomePopup = false;
    Stage purchasePopUpStage = null;
    Stage outcomePopupStage = null;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    // map containing the images of most entities keyed using the object's getType method
    Map<String, Image> images;

    private Image vampireCastleCardImage;
    private Image heroCastleBuildingImage;
    private Image basicEnemyImage;
    private Image swordImage;
    private Image basicBuildingImage;
    private Image goldImage;
    private Image healthImage;
    private Image brilliant_blue_newImage;
    private Image xpImage;
    private Image alliedSlot;
    private Image alliedSoldierImage;
    private Image stakeImage;
	private Image staffImage;		
    private Image helmetImage;
    private Image armourImage;
    private Image shieldImage;
    private Image andurilImage;
    private Image doggieCoinImage;
    private Image treeStumpImage;
    private Image oneRingImage;

   GridPane alliedView;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    private StaticEntity currentlyDraggedEntity;
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    private TextArea itemCardName;
    private Label itemCardTitle;
    private Label goldInfo;
    private Label healthInfo;
    private Label alliedInfo;
    private Label xpInfo;
    private Stage primaryStage;
/*
https://stackoverflow.com/questions/47931041/javafx-popup-window-launches-but-controller-doesnt
*/
    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        equippedImages = new HashMap<Integer, Node>();
        equippedImages.put(Inventory.HEAD_SLOT, null);
        equippedImages.put(Inventory.WEAPON_SLOT, null);
        equippedImages.put(Inventory.BODY_SLOT, null);
        equippedImages.put(Inventory.SHIELD_SLOT, null);

        entityImages = new ArrayList<>(initialEntities);

        loadImages();
        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        heroCastleBuildingImage = new Image((new File("src/images/heros_castle.png")).toURI().toString());
        basicEnemyImage = new Image((new File("src/images/slug.png")).toURI().toString());
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        goldImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        alliedSoldierImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());
        healthImage = new Image((new File("src/images/heart.png")).toURI().toString());
        brilliant_blue_newImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        xpImage = new Image((new File("src/images/xp.png")).toURI().toString());
        alliedSlot = new Image((new File("src/images/allied_slot_nt.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());
        oneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        basicBuildingImage = new Image((new File("src/images/vampire_castle_placed.png")).toURI().toString());

 
        currentlyDraggedEntity = null;
        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    public Map<Integer, Node> returnEquippedImages() {
        return this.equippedImages;
    }

    public GridPane returnEquippedItems() {
        return this.equippedItems;
    }
    
    public MenuSwitcher returnMainMenuSwitcher() {
        return this.mainMenuSwitcher;
    }

    @FXML
    public void initialize() {
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot_white.png")).toURI().toString());
        world.getGoldProperty().set(0);

        // ROW 0 - COL 0
        gameTitle = new TextArea();
        gameTitle.getStyleClass().add("centeredTextArea");
        gameTitle.setMaxHeight(10);
        gameTitle.setMaxWidth(253);
        gameTitle.setNodeOrientation(NodeOrientation.INHERIT);
        gameTitle.setText("GOAL: " + world.goalNum() + " " + world.goalType());
        gameTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 8));

        mainGrid.add(gameTitle, 0, 0);
        // ROW 0 - COL 1
        exitButton.setText("Exit");
        musicButton.setText("Toggle Music");
        exitButton.setMinWidth(60);
        musicButton.setMinWidth(60);
        exitButton.setTextAlignment(TextAlignment.CENTER);
        musicButton.setTextAlignment(TextAlignment.CENTER);
        musicButton.setFocusTraversable(false);
        exitGrid = new GridPane();
        exitGrid.setAlignment(Pos.CENTER_RIGHT);
        exitGrid.add(exitButton, 1, 0);
        exitGrid.add(musicButton, 0, 0);
        exitButton.setAlignment(Pos.CENTER);
        mainGrid.add(exitGrid, 1, 0);

        // ROW 1 - COL 0
        mainGrid.add(squares, 0, 1);
        // ROW 1 - COL 1
        GridPane g = new GridPane();
        g.add(createItemCardInfo(), 0, 0);
        g.add(createSeparator(), 0, 1);
        g.add(createPlayerInfo(), 0, 2);
        g.add(createSeparator(), 0, 3);
        g.add(equippedItems, 0, 4);
        g.add(createSeparator(), 0, 5);
        g.add(unequippedInventory, 0,6);

        mainGrid.add(g, 1, 1);
        // ROW 2 - COL 0
        mainGrid.add(cards, 0, 2);
        // ROW 2 - COL 1
        mainGrid.add(createAlliedView(), 1, 2);

        // initialise equipped inventory
        Image helmetImage = new Image((new File("src/images/helmet_slot.png")).toURI().toString());
        Image armourImage = new Image((new File("src/images/armour_slot.png")).toURI().toString());
        Image shieldImage = new Image((new File("src/images/shield_unequipped.png")).toURI().toString());
        Image swordImage = new Image((new File("src/images/sword_unequipped.png")).toURI().toString());
        ImageView emptySlotView1 = new ImageView(helmetImage);
        ImageView emptySlotView2 = new ImageView(swordImage);
        ImageView emptySlotView3 = new ImageView(armourImage);
        ImageView emptySlotView4 = new ImageView(shieldImage);
        equippedItems.add(emptySlotView1, 1, 0);
        equippedItems.add(emptySlotView2, 0, 1);
        equippedItems.add(emptySlotView3, 1, 1);
        equippedItems.add(emptySlotView4, 2, 1);
        // initialise ground
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        } 
        // initialise path
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        } 
        // initialise cards
        for (int x=0; x<8; x++){
            //ImageView groundView = new ImageView(pathTilesImage);
            //groundView.setViewport(imagePart);
            ImageView emptySlotView = new ImageView(inventorySlotImage);
            
            emptySlotView.setViewport(imagePart);
            cards.add(emptySlotView, x, 0);
            //cards.add(groundView, x, 0);
        }
        // initialise unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }
        // Add hero castle building
        ImageView heroCastleBuildingViewImage = new ImageView(heroCastleBuildingImage);
        heroCastleBuildingViewImage.setViewport(imagePart);
        squares.add(heroCastleBuildingViewImage, world.getCharacter().getX(), world.getCharacter().getY());

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);
        
    }
    private Label createSeparator() {
        Label l = new Label();
        l.setMinHeight(5);
        return l;
    }
    private GridPane createAlliedView(){
        alliedView = new GridPane();
        initAlliedView();
        return alliedView;
    }

    private void initAlliedView() {
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        Image inventorySlotImage = new Image((new File("src/images/allied_slot_nt.png")).toURI().toString());
        alliedView.getChildren().removeAll();
        for (int i = 0; i < 4; i++) {
            ImageView emptySlotView = new ImageView(inventorySlotImage);            
            emptySlotView.setViewport(imagePart);
            alliedView.add(emptySlotView, i, 0);
        }
    }

    private GridPane createPlayerInfo() {
        Label empty = new Label();
        empty.setMinWidth(5);
        GridPane g2 = new GridPane();
        g2.getStyleClass().add("game-grid-cell");
        g2.getStyleClass().add("first-row");
        g2.setAlignment(Pos.CENTER_LEFT);
        
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        ImageView goldImageView = new ImageView(goldImage);            
        goldImageView.setViewport(imagePart);
        ImageView healthImageView = new ImageView(healthImage);            
        healthImageView.setViewport(imagePart);
        ImageView xpImageView = new ImageView(xpImage);
        xpImageView.setViewport(imagePart);
        
        goldInfo = new Label();
        goldInfo.textProperty().bindBidirectional(world.getGoldProperty(), new NumberStringConverter());
        g2.add(goldImageView, 0, 0);
        g2.add(goldInfo, 2, 0);
        healthInfo = new Label();
        healthInfo.textProperty().bindBidirectional(world.getHealthProperty(), new NumberStringConverter());;
        g2.add(empty, 1, 0, 1, 4);
        g2.add(healthImageView, 0, 2);
        g2.add(healthInfo, 2, 2);
        Label loopTitle = new Label();
        loopTitle.setText("LOOP");
        Label loopCount = new Label();
        loopCount.textProperty().bindBidirectional(world.getCycleNumber(), new NumberStringConverter());
        g2.add(loopTitle, 0, 4);
        g2.add(loopCount, 2, 4);
        xpInfo = new Label();
        xpInfo.textProperty().bindBidirectional(world.getExpProperty(), new NumberStringConverter());
        g2.add((xpImageView), 0, 3);
        g2.add(xpInfo, 2, 3);

        return g2;
    }

    private GridPane createItemCardInfo() {
        Label empty = new Label();
        empty.setMaxHeight(2);
        empty.setMinHeight(1);

        GridPane g = new GridPane();
        g.setAlignment(Pos.CENTER);
        g.getStyleClass().add("game-grid-cell");
        g.getStyleClass().add("first-row");
        itemCardTitle = new Label();
        itemCardTitle.setText("ITEM/CARD NAME");
        itemCardTitle.setAlignment(Pos.CENTER);
        itemCardName = new TextArea();
        
        itemCardName.setMaxWidth(130);
        itemCardName.setMaxHeight(103);

        g.add(itemCardTitle, 0, 0);

        GridPane g1 = new GridPane();
        g1.getStyleClass().add("game-grid-cell");
        g1.getStyleClass().add("first-row");
        g1.setAlignment(Pos.CENTER);

        g1.add(g, 0, 0);
        g1.add(itemCardName, 0, 1);
        return g1;
    }

    public void setMode(String mode) {
        world.setMode(mode);
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            if (world.getMatthiasPierre() == null) {
                onLoad(world.createMatthiasPierre());
            }
            if (world.isPurchased()) {
                refreshUnequippedInventory();
            }
            showAlliedSolders();
            for (FriendlyCombatNpc friend : world.runTickMoves()) {
                onLoad(friend);
            }
            List<BasicEnemy> defeatedEnemies = world.runBattles();
            showAlliedSolders();

            if (world.getHealthProperty().intValue() <= 0) {
                terminate();
                world.setGameStatus(LoopManiaWorld.GAME_LOST);
                popupOutcome();
            }
            
            if (world.checkWin()) {
                timeline.stop();
				popupOutcome();
            }

            if (defeatedEnemies != null) {
                for (BasicEnemy enemy : defeatedEnemies){
                    reactToEnemyDefeat(enemy);
                }
            }
    
            world.enemiesVsNpcs();
            List<NeutralCombatNpc> defeatedNeutrals = world.characterVsNpcs();
            if (defeatedNeutrals == null) {
                terminate();
                popupOutcome();
            } else {
                for (NeutralCombatNpc neutral : defeatedNeutrals) {
                    reactToNeutralDefeat(neutral);
                }
            }
            List<BasicEnemy> newEnemies = world.possiblySpawnSlugs();
            for (BasicEnemy newEnemy: newEnemies){
                onLoad(newEnemy);
            }
            
            //printThreadingNotes("HANDLED TIMER");
            if (world.completedCycle()) {
                List<MovingEntity> spawnList = world.possiblySpawnEndOfCycle();
                for (MovingEntity newEntity: spawnList){
					onLoad(newEntity);
				}
            }            
            if (world.isCharacterAtHeroCastleBuilding()) {
                popUpLaunch();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        timeline.stop();
    }

    public void terminate(){
        for (int key : equippedImages.keySet()) {
            equippedItems.getChildren().remove(equippedImages.get(key));
        }
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a vampire card from the world, and pair it with an image in the GUI
     */
    private void loadCard(Card card) {
        onLoad(card);
    }

    /**
     * load a sword from the world, and pair it with an image in the GUI
     */
    private void loadItem(Item item){
        onLoad(item);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        world.increaseGoldExperience(enemy);
        Item item = world.getRewardItem(enemy);
        Card card = world.getRewardCard();
        if (item != null) {
            loadItem(item);
        }
        if (card != null) {
            loadCard(card);
        }
    }

    /**
     * run GUI events after a neutral npc is defeated, such as spawning items/experience/gold
     * @param neutral defeated neutral for which we should react to the death of
     */
    private void reactToNeutralDefeat(NeutralCombatNpc neutral){
        // react to character defeating a neutral npc
        world.increaseGoldExperience(neutral);
        Item item = world.getRewardItem(neutral);
        if (item != null) {
            loadItem(item);
        }
    }

    /**
     * load a card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param card
     */
    private void onLoad(Card card) {
        String type = card.getType();
        String description;
        String name;

        if (type.equals("vampireCastleCard")) {
            description = "Will spawn 1-3\nvampires every\n5 loops";
            name = "Vampire Castle";
        } else if (type.equals("zombiePitCard")) {
            description = "Will spawn 1-2\nzombies every loop";
            name = "Zombie Pit";
        } else if (type.equals("villageCard")) {
            description = "Will regenerate 20\nhealth when passed";
            name = "Village";
        } else if (type.equals("trapCard")) {
            description = "Traps the enemy";
            name = "Trap";
        } else if (type.equals("towerCard")) {
            description = "This tower will\nhelp combat\nnearby enemies";
            name = "Tower";
        } else if (type.equals("campfireCard")) {
            description = "Character will deal\ndouble damage in its\nvicinity";
            name = "Camp Fire";
        } else if (type.equals("barracksCard")) {
            description = "Recruit a soldier\nto help you";
            name = "Barrack Card";
        } else if (type.equals("adventurerGuildCard")) {
            description = "Upgrades an\nexisting village";
            name = "Adventurer's Guild";
        } else if (type.equals("pokerBallCard")) {
            description = "Permanently entrances\nan enemy";
            name = "PokerBall";
        } else if (type.equals("clearEnemiesCard")) {
            description = "Clear all enemies";
            name = "Clear Enemies";    
        } else {
            description = "";
            name = "";
        }       

        Image image = images.get(type);
        ImageView view = new CardImageView(image, description, name);
        view.setFocusTraversable(true);
        view.setOnMouseClicked(e -> {
            itemCardName.setText(((CardImageView) view).getDescription());
            itemCardTitle.setText(((CardImageView) view).getName());
         });
        view.setOnMouseReleased(e -> {
            itemCardName.setText(((CardImageView) view).getDescription());
            itemCardTitle.setText(((CardImageView) view).getName());
        });

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares, card);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an item into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param item
     */
    private void onLoad(Item item) {
        String type = item.getType();
        String description = null;
        String name = null;

        if (type.equals("healthPotion")) {
            description = "Increases health by 40\n\nMhm... Tasty";
            name = "Health Potion";
        } else if (type.equals("helmet")) {
            description = "Reduces damage\ntaken\n\nI love helmets! - Skater Bro";
            name = "Helmet";
        } else if (type.equals("stake")) {
            description = "Deals\nadditional damage\nto vampires\n\nThere are lives at stake...";
            name = "Stake";
        } else if (type.equals("staff")) {
            description = "Chance to inflict\ntrance on attack\n\nWhat a needlessly large rod...";
            name = "Staff";
        } else if (type.equals("armour")) {
            description = "Reduces damage\ntaken\n\nWhat's he gonna do? Stab me? - Deceased armourless victim";
            name = "Armour";
        } else if (type.equals("sword")) {
            description = "Deals\nadditional damage\n\nIt will KEAL - Doug";
            name = "Sword";
        } else if (type.equals("shield")) {
            description = "Reduces damage\ntaken\n\nThe best offence is a good defence";
            name = "Shield";
        } else if (type.equals("anduril")) {
            description = "Deals significant\ndamage to bosses\n\nSomething about a ring...";
            name = "Anduril";
        } else if (type.equals("doggieCoin")) {
            description = "Sell high\n\n[Memesis]69:42 Elon planted crops in that land and the same year reaped a hundredfold, because the LORD blessed him";
            name = "Doggie Coin";
        } else if (type.equals("treeStump")) {
            description = "Reduces damage\nsignificantly\nagainst bosses\n\n#teamtrees";
            name = "Tree Stump";
        } else if (type.equals("theonering")) {
            description = "Revives character\nupon death\n\nMy precious...";
            name = "The One Ring";
        } else {
            description = "";
            name = "";
        }

        Image image = images.get(type);
        ImageView view  = new ItemImageView(image, description, name);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems, item);
        addEntity(item, view);
        view.setFocusTraversable(true);
        view.setOnMouseClicked(e -> {
            itemCardName.setText(((ItemImageView) view).getDescription());
            itemCardTitle.setText(((ItemImageView) view).getName());
         });
        unequippedInventory.getChildren().add(view);
    }

    private void onLoadEquipped(Item item) {
        String type = item.getType();
        Image image = images.get(type);
        ImageView view = new ImageView(image);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM_EQUIPPED, equippedItems, unequippedInventory, item);
        addEntity(item, view);
        equippedItems.getChildren().add(view);
    }

    /**
     * load a moving entity into the GUI
     * @param 
     */
    private void onLoad(MovingEntity entity) {
        ImageView view = new ImageView(images.get(entity.getType()));
        addEntity(entity, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building
     */
    private void onLoad(Building building){
        ImageView view = new ImageView(images.get(building.getType()));
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != targetGridPane && db.hasImage()){  

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType){
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if (((Card )currentlyDraggedEntity).validPlacement(x, y, world.getPath())) {
                                    if (currentlyDraggedEntity instanceof BuildingCard) {
                                        Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                        onLoad(newBuilding);
                                    } else {
                                        UtilityCard utilityCard = (UtilityCard )currentlyDraggedEntity;
                                        utilityCard.specialAbility(nodeX, nodeY, world.getCharacter());
                                        utilityCard.destroy();
                                        world.removeCard(utilityCard);
                                    }
                                } else {
                                    onLoad((Card )currentlyDraggedEntity);
                                }

                                break;
                            case ITEM:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(
                                    currentlyDraggedEntity instanceof Equippable &&
                                    ((Equippable )currentlyDraggedEntity).validEquipCoords(x, y) &&
                                    world.isEquipmentSlotFree(((Equippable )currentlyDraggedEntity).getSlot())
                                ) {
                                    Equippable equippableItem = ((Equippable )currentlyDraggedEntity);
                                    equippableItem.destroy();
                                    equippableItem.resurrect();
                                    equipItem(equippableItem);
                                    onLoadEquipped(equippableItem);
                                    //targetGridPane.add(image, x, y, 1, 1);
                                    //equippedImages.put(equippableItem.getSlot(), image);


                                } else if (
                                    currentlyDraggedEntity instanceof Equippable &&
                                    ((Equippable )currentlyDraggedEntity).validEquipCoords(x, y) &&
                                    !world.isEquipmentSlotFree(((Equippable )currentlyDraggedEntity).getSlot())
                                ) {
                                    Equippable equippableItem = ((Equippable )currentlyDraggedEntity);
                                    //targetGridPane.getChildren().remove(equippedImages.get(equippableItem.getSlot()));
                                    Equippable equippedItem = world.getEquippedItemBySlot(equippableItem.getSlot());

                                    // remove already equipped item
                                    equippedItem.destroy();
                                    equippedItem.resurrect();
                                    world.unequipItem(((Equippable )currentlyDraggedEntity).getSlot());
                                    onLoad(equippedItem);
                                    
                                    // equip the item being dragged into the slot
                                    equipItem((Equippable )currentlyDraggedEntity);
                                    equippableItem.destroy();
                                    equippableItem.resurrect();
                                    onLoadEquipped(equippableItem);

                                    //targetGridPane.add(image, x, y, 1, 1);
                                    //equippedImages.put(equippableItem.getSlot(), image);
                                } else {
                                    onLoad((Item )currentlyDraggedEntity);
                                }
                                break;
                            case ITEM_EQUIPPED:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Item item = (Item )currentlyDraggedEntity;
                                item.destroy();
                                item.resurrect();
                                world.unequipItem(((Equippable)item).getSlot());
                                onLoad(item);

                                break;

                            default:
                                break;
                        }
                        
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedEntity = null;
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        // printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                } 
                event.setDropCompleted(false);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                   draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void equipItem(Equippable item) {
        try {
            world.addEquippedItem(item);
        } catch (Exception e) {
            System.out.println(e);
            onLoad((Item )item);
        }   
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane, StaticEntity entity){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedEntity = entity;
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case CARD:
                        draggedEntity.setImage(view.getImage());
                        break;
                    case ITEM:
                        draggedEntity.setImage(view.getImage());
                        break;
                    case ITEM_EQUIPPED:
                        draggedEntity.setImage(view.getImage());
                        break;
                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                //if(event.getGestureSource() != n && event.getDragboard().hasImage()){
                                    //n.setOpacity(0.7);
                                //}
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you could program the game so if the new highlight location is invalid the highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                timeline.play();
                isPaused = false;
            }
            else{
                isPaused = true;
                pause();
            }
            break;
        case H:
            world.useHealthPotion();        
		default:
            break;
        }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        popUpExit();
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    /*private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }*/

    /*
     * Load all entity images into the world controller
     */
    public void loadImages() {
        images = new HashMap<String, Image>();
        images.put("armour", new Image((new File("src/images/armour.png")).toURI().toString()));
        images.put("anduril", new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString()));
        images.put("adventurer", new Image((new File("src/images/adventurer.png")).toURI().toString()));
        images.put("adventurerGuildCard", new Image((new File("src/images/AdventurerGuild.png")).toURI().toString()));
        images.put("bandit", new Image((new File("src/images/bandit.png")).toURI().toString()));
        images.put("bard", new Image((new File("src/images/bard.png")).toURI().toString()));
        images.put("barracksCard", new Image((new File("src/images/barracks_card.png")).toURI().toString()));
        images.put("barracks", new Image((new File("src/images/barracks.png")).toURI().toString()));
        images.put("campfireCard", new Image((new File("src/images/campfire_card.png")).toURI().toString()));
        images.put("campfire", new Image((new File("src/images/campfire.png")).toURI().toString()));
        images.put("clearEnemiesCard", new Image((new File("src/images/Clear.png")).toURI().toString()));
        images.put("dragon", new Image((new File("src/images/dragon.png")).toURI().toString()));
        images.put("doggie", new Image((new File("src/images/doggie.png")).toURI().toString()));
        images.put("doggieCoin", new Image((new File("src/images/doggiecoin.png")).toURI().toString()));
        images.put("elanMuske", new Image((new File("src/images/ElanMuske.png")).toURI().toString()));
        images.put("sword", new Image((new File("src/images/basic_sword.png")).toURI().toString()));
        images.put("healthPotion", new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString()));
        images.put("alliedSoldier", new Image((new File("deep_elf_master_archer.png")).toURI().toString()));
        images.put("gold", new Image((new File("src/images/gold_pile.png")).toURI().toString()));
        images.put("golem", new Image((new File("src/images/golem.png")).toURI().toString()));
        images.put("helmet", new Image((new File("src/images/helmet.png")).toURI().toString()));
        images.put("matthiasPierre", new Image((new File("src/images/MatthiasPierre.png")).toURI().toString()));
        images.put("mushroom", new Image((new File("src/images/mushroom.png")).toURI().toString()));
        images.put("pokerBallCard", new Image((new File("src/images/PokerBall.png")).toURI().toString()));
        images.put("shield", new Image((new File("src/images/shield.png")).toURI().toString()));
        images.put("slug", new Image((new File("src/images/slug.png")).toURI().toString()));
        images.put("staff", new Image((new File("src/images/staff.png")).toURI().toString()));
        images.put("stake", new Image((new File("src/images/stake.png")).toURI().toString()));
        images.put("theOneRing", new Image((new File("src/images/the_one_ring.png")).toURI().toString()));
        images.put("towerCard", new Image((new File("src/images/tower_card.png")).toURI().toString()));
        images.put("tower", new Image((new File("src/images/tower.png")).toURI().toString()));
        images.put("trapCard", new Image((new File("src/images/trap_card.png")).toURI().toString()));
        images.put("trap", new Image((new File("src/images/trap.png")).toURI().toString()));
        images.put("treeStump", new Image((new File("src/images/tree_stump.png")).toURI().toString()));
        images.put("vampireCastle", new Image((new File("src/images/vampire_castle_placed.png")).toURI().toString()));
        images.put("vampireCastleCard", new Image((new File("src/images/vampire_castle_card.png")).toURI().toString()));
        images.put("vampire", new Image((new File("src/images/vampire.png")).toURI().toString()));
        images.put("villageCard", new Image((new File("src/images/village_card.png")).toURI().toString()));
        images.put("village", new Image((new File("src/images/village.png")).toURI().toString()));
        images.put("zombiePitCard", new Image((new File("src/images/zombie_pit_card.png")).toURI().toString()));
        images.put("zombiePit", new Image((new File("src/images/zombie_pit.png")).toURI().toString()));
        images.put("zombie", new Image((new File("src/images/zombie.png")).toURI().toString()));
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    private void popupOutcome() {
        Parent root;

        try {
            if (purchasePopUpStage != null) {
                try {
                    purchasePopUpStage.close();
                } catch (Exception e) {}
            }
            isOutcomePopup = true;
            pause();
            outcomePopupStage = new Stage();
            outcomePopupStage.setHeight(400);
            outcomePopupStage.setWidth(600);
            

            OutcomeController controller = new OutcomeController(world, outcomePopupStage, timeline);
            FXMLLoader purchaseLoader = new FXMLLoader(getClass().getResource("outcome.fxml"));
            purchaseLoader.setController(controller);
            root = purchaseLoader.load();
            outcomePopupStage.setTitle("Game Over");
            outcomePopupStage.setResizable(false);
            outcomePopupStage.setScene(new Scene(root, 250, 100));
            outcomePopupStage.initModality(Modality.WINDOW_MODAL);  
            outcomePopupStage.initOwner(primaryStage);
            outcomePopupStage.show();
            outcomePopupStage.setOnCloseRequest(windowEvent -> {
                if (world.isRestartGame()) {
                    restart();
                };
            });


        } catch (Exception e) {e.printStackTrace();}
    }
    private void popUpLaunch(){
        Parent root;
    
        if (!isOutcomePopup) {
            purchasePopUpStage = new Stage();
            purchasePopUpStage.setHeight(600);
            try {
                pause();
                PurchaseWorldController controller = new PurchaseWorldController(world, purchasePopUpStage, timeline);
                FXMLLoader purchaseLoader = new FXMLLoader(getClass().getResource("purchase.fxml"));
                purchaseLoader.setController(controller);
                root = purchaseLoader.load();
                purchasePopUpStage.setTitle("Purchase Items");
                purchasePopUpStage.setResizable(false);
                purchasePopUpStage.setScene(new Scene(root));
                purchasePopUpStage.initModality(Modality.WINDOW_MODAL);    // popup
                purchasePopUpStage.initOwner(primaryStage);
                purchasePopUpStage.setOnCloseRequest(E -> {
                    isPaused = false;
                    timeline.play();
                });
                purchasePopUpStage.show();
                
                //timeline.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void popUpExit(){
        Stage popUpStage = new Stage();
        Parent root;
    
        try {
            pause();
            boolean wasPlaying;
            if (mediaPlayer != null) {
                wasPlaying = mediaPlayer.getStatus().equals(Status.PLAYING);
                mediaPlayer.pause();
            } else {
                wasPlaying = false;
            }
            ExitMenuController controller = new ExitMenuController(popUpStage, this);
            FXMLLoader exitLoader = new FXMLLoader(getClass().getResource("ExitMenuView.fxml"));
            exitLoader.setController(controller);
            root = exitLoader.load();
            popUpStage.setResizable(false);
            popUpStage.setScene(new Scene(root));
            popUpStage.initModality(Modality.WINDOW_MODAL);    // popup
            popUpStage.initOwner(primaryStage);
            popUpStage.setOnCloseRequest(E -> {
                timeline.play();
                if (wasPlaying) {
                    mediaPlayer.play();
                }
            } );
            popUpStage.show();
            
            //timeline.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //timeline.play();
    }

    private void refreshUnequippedInventory() {
        Image inventorySlotImage = new Image((new File("src/images/empty_slot_white.png")).toURI().toString());
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }
        world.resetUnequippedCoords();
        List<Item> unequippedInventoryItems = world.getUnequippedInventoryItems();
        for (int i = 0; i < unequippedInventoryItems.size(); i++) {
            Item e = unequippedInventoryItems.get(i);
            onLoad(e);
        }
    }    

    private void showAlliedSolders() {
        int size = 0;
        if (world.getCharacter() != null) {
            size = world.getCharacter().getListOfAllies().size();
        }
        if (size > 0) {
            Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
            Image inventorySlotImage = new Image((new File("src/images/allied_slot_nt.png")).toURI().toString());
            alliedView.getChildren().removeAll();
            alliedView.setCache(false);
            alliedView.setCacheShape(false);
    
            for (int i = 0; i < 4; i++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);            
                emptySlotView.setViewport(imagePart);
                alliedView.add(emptySlotView, i, 0);
            }        

            // add


            for (int i = 0; i < size; i++) {
                ImageView  view = new ImageView(alliedSoldierImage);
                view.setViewport(imagePart);
                alliedView.add(view, i, 0);
            }
        }
    }

    MediaPlayer mediaPlayer;
    public void playBackgroundMusic() {
        String musicFile = "src/unsw/loopmania/BackgroundMusic.mp3";
        Media backgroundMusic = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.play();
    }

    @FXML
    private void toggleMusic() {
        if (mediaPlayer.getStatus().equals(Status.PLAYING)) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    private void restart() {
        isOutcomePopup = false;
        isPaused = false;
        world.resetWorld();
        for (int key : equippedImages.keySet()) {
            equippedItems.getChildren().remove(equippedImages.get(key));
        }    
        initAlliedView();
        timeline.play(); 
    }

    public void exitGameAndReset() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        world.resetWorld();
        showAlliedSolders();
        for (int key : equippedImages.keySet()) {
            equippedItems.getChildren().remove(equippedImages.get(key));
        }
        pause();
        returnMainMenuSwitcher().switchMenu();
    }

    public void saveAndReset() {
        mediaPlayer.pause();
        world.saveWorld();

        world.resetWorld();
        showAlliedSolders();
        for (int key : equippedImages.keySet()) {
            equippedItems.getChildren().remove(equippedImages.get(key));
        }
        pause();
        returnMainMenuSwitcher().switchMenu();
    }

    public void loadWorld() throws JSONException, FileNotFoundException {
        world.loadWorld();
        showAlliedSolders();
        refreshUnequippedInventory();
        for (Card card : world.getCards()) {
            onLoad(card);
        }
        for (Map.Entry<Integer, Equippable> entry : world.inventory.returnEquippedItems().entrySet()) {
            Equippable equipped = entry.getValue();
            if (equipped != null) {
                onLoadEquipped(entry.getValue());
            } 
        }
        for (Building building : world.getBuildings()) {
            onLoad(building);
        }
        for (BasicEnemy enemy : world.getEnemies()) {
            onLoad(enemy);
        }
    }
}
