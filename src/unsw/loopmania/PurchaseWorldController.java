package unsw.loopmania;

import java.io.File;
import java.util.List;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.HealthPotion;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.ItemPrices;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;


public class PurchaseWorldController {
    @FXML
    GridPane mainGrid;
    @FXML
    GridPane sellGrid;
    @FXML
    GridPane purchaseGrid;
    @FXML
    Button purchaseButton;
    @FXML
    Button exitButton;
    Stage stage;

    private LoopManiaWorld world;
    private Timeline timeline;
    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image amourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image healthImage;
    private Image andurilImage;
    private Image doggieCoinImage;
    private Image treeStumpImage;
    private Image oneRingImage;

    int swordPurchaseCount = 0;
    int stakePurchaseCount = 0;
    int staffPurchaseCount = 0;
    int helmetPurchaseCount = 0;
    int armourPurchaseCount = 0;
    int healthPurchaseCount = 0;
    int shieldPurchaseCount = 0;
    int andurilPurchaseCount = 0;
    int doggieCoinPurchaseCount = 0;
    int treeStumpPurchaseCount = 0;
    int oneRingPurchaseCount = 0;

    int swordSellCount = 0;
    int stakeSellCount = 0;
    int staffSellCount = 0;
    int helmetSellCount = 0;
    int armourSellCount = 0;
    int healthSellCount = 0;
    int shieldSellCount = 0;
    int doggyCoinSellCount = 0;
    int oneRingSellCount = 0;
    int treeStumpSellCount = 0;
    int andurilSellCount = 0;

    int swordCost = 0;
    int stakeCost = 0;
    int staffCost = 0;
    int helmetCost = 0;
    int amourCost = 0;
    int healthCost = 0;
    int shieldCost = 0;
    int andurilCost = 0;
    int doggieCoinCost = 0;
    int treeStumpCost = 0;
    int oneRingCost = 0;

    int total = 0;
    TextField totalText = new TextField();
    Label errorText = new Label();
    Label errorText1 = new Label();
    Rectangle2D imagePart = null;
    int goldInHand = 0;
    int expenses = 0;
    int itemSellCount = 0;
    TextField gih;

    public PurchaseWorldController(LoopManiaWorld world, Stage stage, Timeline timeline) {
        this.world = world;
        this.stage = stage;
        this.timeline = timeline;
    }

    @FXML
    public void initialize() {
        imagePart = new Rectangle2D(0, 0, 32, 32);
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake_t.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff_t.png")).toURI().toString());
        amourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west_t.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump_t.png")).toURI().toString());
        oneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        healthImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());



        goldInHand = world.amountOfGold();
        initPurchaseItems();
        initSellItems();
        exitButton.setOnAction(E -> {
            //timeline.play();
            stage.getOnCloseRequest().handle(null);
            stage.close();            
        });
        //purchaseGrid.add(b, 1, 10);
        purchaseButton.setDisable(true);
        purchaseButton.setOnAction(E -> {
            addPurchaseItems();
            //timeline.play();
            stage.getOnCloseRequest().handle(null);
            stage.close();            
        }); 

        GridPane gp = new GridPane();
        gih = new TextField();
        gih.setText(String.valueOf(goldInHand));
        gih.setDisable(true);
        gp.add(new Label("Gold in hand: "), 0, 0);
        gp.add(gih, 1, 0);
        gih.setMinWidth(100);
        totalText.setMinWidth(100);
        gp.add(new Label("Expenses: "), 0, 1);
        gp.add(totalText, 1, 1);
        gp.add(errorText, 0, 2, 2, 1);
        gp.add(errorText1, 0, 3, 2, 1);
      
        mainGrid.add(gp, 0, 2, 2, 1);
        //purchaseGrid.add(purchaseButton, 0, 10);       
    }

    private void initSellItems() {
        List<Item> items = world.getUnequippedInventoryItems();
        int row = 0;
        int col = 0;
        for (Item i : items) {
            if (col == 4) {
                col =0;
                row++;
            }
            if (i.getType().equals("sword")) {
                HBox hb = new HBox();
                ImageView swordImageView = new ImageView(swordImage);
                swordImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        swordSellCount++;
                        goldInHand += ItemPrices.swordPrice;
                    } else {
                        swordSellCount--;
                        goldInHand -= ItemPrices.swordPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 || expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                hb.getChildren().add(swordImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;
            } else if (i.getType().equals("stake")) {
                ImageView stakeImageView = new ImageView(stakeImage);
                stakeImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        stakeSellCount++;
                        goldInHand += ItemPrices.stakePrice;
                    } else {
                        stakeSellCount--;
                        goldInHand -= ItemPrices.stakePrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 || expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(stakeImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;
        
            } else if (i.getType().equals("staff")) {
                ImageView staffImageView = new ImageView(staffImage);
                staffImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        staffSellCount++;
                        goldInHand += ItemPrices.staffPrice;
                    } else {
                        staffSellCount--;
                        goldInHand -= ItemPrices.staffPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 || expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
           });
                HBox hb = new HBox();
                hb.getChildren().add(staffImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;
        
            } else if (i.getType().equals("helmet")) {
                ImageView helmetImageView = new ImageView(helmetImage);
                helmetImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        helmetSellCount++;
                        goldInHand += ItemPrices.helmetPrice;
                    } else {
                        helmetSellCount--;
                        goldInHand -= ItemPrices.helmetPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                    staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 || expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(helmetImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;
                        
            } else if (i.getType().equals("healthPotion")) {
                ImageView healthImageView = new ImageView(healthImage);
                healthImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        healthSellCount++;
                        goldInHand += ItemPrices.healthPrice;
                    } else {
                        healthSellCount--;
                        goldInHand -= ItemPrices.healthPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                    staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                if (totalSoldItems > 0 || expenses > 0) {
                    purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
               });
                HBox hb = new HBox();
                hb.getChildren().add(healthImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;
                        
            } else if (i.getType().equals("armour")) {
                ImageView amourImageView = new ImageView(amourImage);
                amourImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        armourSellCount++;
                        goldInHand += ItemPrices.amourPrice;
                    } else {
                        armourSellCount--;
                        goldInHand -= ItemPrices.amourPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 || expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(amourImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;
                        
            } else if (i.getType().equals("shield")) {
                ImageView shieldImageView = new ImageView(shieldImage);
                shieldImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        shieldSellCount++;
                        goldInHand += ItemPrices.shieldPrice;
                    } else {
                        shieldSellCount--;
                        goldInHand -= ItemPrices.shieldPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 || expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(shieldImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;                       
            } else if (i.getType().equals("doggieCoin")) {
                ImageView doggieCoinImageView = new ImageView(doggieCoinImage);
                doggieCoinImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        doggyCoinSellCount++;
                        goldInHand += ItemPrices.doggieCoinPrice;
                    } else {
                        doggyCoinSellCount--;
                        goldInHand -= ItemPrices.doggieCoinPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 ||
                        expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(doggieCoinImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;                       
            } else if (i.getType().equals("anduril")) {
                ImageView andurilImageView = new ImageView(andurilImage);
                andurilImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        andurilSellCount++;
                        goldInHand += ItemPrices.andurilPrice;
                    } else {
                        andurilSellCount--;
                        goldInHand -= ItemPrices.andurilPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 ||
                        expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(andurilImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;                       
            } else if (i.getType().equals("treeStumps")) {
                ImageView treeStumpImageView = new ImageView(treeStumpImage);
                treeStumpImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        treeStumpSellCount++;
                        goldInHand += ItemPrices.treeStumpPrice;
                    } else {
                        treeStumpSellCount--;
                        goldInHand -= ItemPrices.treeStumpPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 ||
                        expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(treeStumpImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;                       
            } else if (i.getType().equals("theonering")) {
                ImageView oneRingImageView = new ImageView(oneRingImage);
                oneRingImageView.setViewport(imagePart);
                CheckBox c = new  CheckBox();
                c.selectedProperty().addListener(E -> {
                    if (c.isSelected()) {
                        oneRingSellCount++;
                        goldInHand += ItemPrices.oneRingPrice;
                    } else {
                        oneRingSellCount--;
                        goldInHand -= ItemPrices.oneRingPrice;
                    }
                    gih.setText(String.valueOf(goldInHand));
                    int totalSoldItems = swordSellCount + shieldSellCount + helmetSellCount + healthSellCount + armourSellCount + stakeSellCount + 
                        staffSellCount + doggyCoinSellCount + oneRingSellCount + treeStumpSellCount + andurilSellCount;
                    if (totalSoldItems > 0 ||
                        expenses > 0) {
                        purchaseButton.setDisable(false);
                    } else {
                        purchaseButton.setDisable(true);
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().add(oneRingImageView);
                hb.getChildren().add(c);
                sellGrid.add(hb, col, row);
                col++;                       
            }
        }       
    }

    private void initPurchaseItems() {
        Label emptyCol1 = new Label();
        emptyCol1.setMinWidth(5);
        Label emptyCol2 = new Label();
        emptyCol2.setMinWidth(5);
        errorText.setMaxWidth(800);
        errorText1.setMaxWidth(800);
        
        ImageView swordImageView = new ImageView(swordImage);
        swordImageView.setViewport(imagePart);
        TextField swordCount = new TextField();
        swordCount.setText("0");
        swordCount.setDisable(true);
        swordCount.setMaxWidth(30);
        Slider swordSlider = createSlider();
        swordSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + stakePurchaseCount + staffPurchaseCount + shieldPurchaseCount + armourPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount + helmetPurchaseCount + healthPurchaseCount;

            if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                } 
            }  
            swordPurchaseCount = i;
            swordCost = ItemPrices.swordPrice * i;
            swordSlider.setValue(i);
            swordCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });
        totalText.setText("0");
        totalText.setDisable(true);
        totalText.setMaxWidth(100);
        ImageView stakeImageView = new ImageView(stakeImage);
        stakeImageView.setViewport(imagePart);
        TextField stakeCount = new TextField();
        stakeCount.setText("0");
        stakeCount.setDisable(true);
        stakeCount.setMaxWidth(30);
        Slider stakeSlider = createSlider();
        stakeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + swordPurchaseCount + staffPurchaseCount + shieldPurchaseCount + armourPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount + helmetPurchaseCount + healthPurchaseCount;

            if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                }
            }		
            stakePurchaseCount = i;	
            stakeCost = ItemPrices.stakePrice * i;
            stakeSlider.setValue(i);
            stakeCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });

        ImageView staffImageView = new ImageView(staffImage);
        staffImageView.setViewport(imagePart);
        TextField staffCount = new TextField();
        staffCount.setText("0");
        staffCount.setDisable(true);
        staffCount.setMaxWidth(30);
        Slider staffSlider = createSlider();
        staffSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + swordPurchaseCount + stakePurchaseCount + shieldPurchaseCount + armourPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount + helmetPurchaseCount + healthPurchaseCount;
            if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                }
            }
            staffPurchaseCount = i;
            staffCost = ItemPrices.staffPrice * i;
            staffSlider.setValue(i);
            staffCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });

        ImageView amourImageView = new ImageView(amourImage);
        amourImageView.setViewport(imagePart);
        TextField amourCount = new TextField();
        amourCount.setDisable(true);
        amourCount.setText("0");
        amourCount.setMaxWidth(30);
        Slider amourSlider = createSlider();
        amourSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + swordPurchaseCount + stakePurchaseCount + staffPurchaseCount + shieldPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount + helmetPurchaseCount + healthPurchaseCount;
            if (world.getMode().equals("Berserker") && (helmetPurchaseCount > 0 || shieldPurchaseCount > 0 || i > 1)) {
                errorText.setText("Warning: You cannot purchase more than 1 piece of protective gear at a time in Berserker Mode");
                purchaseButton.setDisable(true);
            } else if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                //amourSlider.setValue(armourPurchaseCount);
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                }
            }      
            armourPurchaseCount = i;
            amourCost = ItemPrices.amourPrice * i;
            amourSlider.setValue(i);
            amourCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });

        ImageView shieldImageView = new ImageView(shieldImage);
        shieldImageView.setViewport(imagePart);
        TextField shieldCount = new TextField();
        shieldCount.setDisable(true);
        shieldCount.setMaxWidth(30);
        Slider shieldSlider = createSlider();
        shieldSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + swordPurchaseCount + stakePurchaseCount + staffPurchaseCount + armourPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount + helmetPurchaseCount + healthPurchaseCount;

 
            if (world.getMode().equals("Berserker") && (helmetPurchaseCount > 0 || armourPurchaseCount > 0 || i > 1)) {
                errorText.setText("Warning: You cannot purchase more than 1 piece of protective gear at a time in Berserker Mode");
                purchaseButton.setDisable(true);
            } else if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                }
            }
            shieldPurchaseCount = i;
            shieldCost = ItemPrices.shieldPrice * i;
            shieldSlider.setValue(i);
            shieldCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });
        shieldCount.setText("0");

        //= Helmet item
        ImageView helmetImageView = new ImageView(helmetImage);
        helmetImageView.setViewport(imagePart);
        TextField helmetCount = new TextField();
        helmetCount.setDisable(true);
        helmetCount.setText("0");
        helmetCount.setMaxWidth(30);
        Slider helmetSlider = createSlider();
        helmetSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + swordPurchaseCount + stakePurchaseCount + staffPurchaseCount + shieldPurchaseCount + healthPurchaseCount + armourPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount;


            if (world.getMode().equals("Berserker") && (shieldPurchaseCount > 0 || armourPurchaseCount > 0 || i > 1)) {
                errorText.setText("Warning: You cannot purchase more than 1 piece of protective gear at a time in Berserker Mode");
                purchaseButton.setDisable(true);
            } else if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                //helmetSlider.setValue(helmetPurchaseCount);
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                }
            }     
            helmetPurchaseCount = i;
            helmetCost = ItemPrices.helmetPrice * i;
            helmetSlider.setValue(i);
            helmetCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });
        helmetCount.setText("0");

        ImageView healthImageView = new ImageView(healthImage);
        healthImageView.setViewport(imagePart);
        TextField healthCount = new TextField();
        healthCount.setText("0");
        healthCount.setDisable(true);
        healthCount.setMaxWidth(30);
        Slider healthSlider = createSlider();
        healthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int i = (int) Math.round(newVal.doubleValue());
            int totalOrderedItems = i + swordPurchaseCount + stakePurchaseCount + staffPurchaseCount + shieldPurchaseCount + armourPurchaseCount +
                        andurilPurchaseCount + treeStumpPurchaseCount + oneRingPurchaseCount + helmetPurchaseCount;
            if (i > 1 && world.getMode().equals("Survival")) {
                errorText.setText("Warning: You cannot purchase more than 1 health potion in Survival Mode");
                purchaseButton.setDisable(true);
            } else if (totalOrderedItems > world.getNumberOfFreeSlotsInUnequippedInventory()) {
                errorText.setText("Warning: No sufficient slots in unequipped inventory."); 
                purchaseButton.setDisable(true);
            } else {
                expenses = swordCost + stakeCost + staffCost + amourCost + shieldCost + healthCost + helmetCost + andurilCost + oneRingCost + treeStumpCost + doggieCoinCost;
                if (expenses > goldInHand) {
                    errorText1.setText("Not enough fund to purchase (available gold: " + goldInHand + ")");
                    purchaseButton.setDisable(true);
                } else {
                    errorText1.setText(null);
                    errorText.setText(null);
                    purchaseButton.setDisable(false);
                }	
            }
            healthPurchaseCount = i;
            healthCost = ItemPrices.healthPrice * i;
            healthSlider.setValue(i);
            healthCount.setText(String.valueOf(i));
            totalText.setText(String.valueOf(expenses));
        });


        purchaseGrid.add(swordImageView, 0, 0);
        purchaseGrid.add(new Label("[" + ItemPrices.swordPrice + "] gold * "), 1, 0);
        purchaseGrid.add(swordCount, 2, 0);
        purchaseGrid.add(swordSlider, 3, 0);

        purchaseGrid.add(stakeImageView, 0, 1);
        purchaseGrid.add(new Label("[" + ItemPrices.stakePrice + "] gold * "), 1, 1);
        purchaseGrid.add(stakeCount, 2, 1);
        purchaseGrid.add(stakeSlider, 3, 1);

        purchaseGrid.add(staffImageView, 0, 2);
        purchaseGrid.add(new Label("[" + ItemPrices.staffPrice + "] gold * "), 1, 2);
        purchaseGrid.add(staffCount, 2, 2);
        purchaseGrid.add(staffSlider, 3, 2);

        purchaseGrid.add(amourImageView, 0, 3);
        purchaseGrid.add(new Label("[" + ItemPrices.amourPrice + "] gold * "), 1, 3);
        purchaseGrid.add(amourCount, 2, 3);
        purchaseGrid.add(amourSlider, 3, 3);

        purchaseGrid.add(shieldImageView, 0, 4);
        purchaseGrid.add(new Label("[" + ItemPrices.shieldPrice + "] gold * "), 1, 4);
        purchaseGrid.add(shieldCount, 2, 4);
        purchaseGrid.add(shieldSlider, 3, 4);

        purchaseGrid.add(helmetImageView, 0, 5);
        purchaseGrid.add(new Label("[" + ItemPrices.helmetPrice + "] gold * "), 1, 5);
        purchaseGrid.add(helmetCount, 2, 5);
        purchaseGrid.add(helmetSlider, 3, 5);

        purchaseGrid.add(healthImageView, 0, 6);
        purchaseGrid.add(new Label("[" + ItemPrices.healthPrice + "] gold * "), 1, 6);
        purchaseGrid.add(healthCount, 2, 6);
        purchaseGrid.add(healthSlider, 3, 6);     
    }


    private void addPurchaseItems() {
        if (swordPurchaseCount > 0) {
            world.purchase("sword", swordPurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (swordSellCount > 0) {
            world.sell("sword", swordSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (armourPurchaseCount > 0) {
            world.purchase("armour", armourPurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (armourSellCount > 0) {
            world.sell("armour", armourSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (stakePurchaseCount > 0) {
            world.purchase("stake", stakePurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (stakeSellCount > 0) {
            world.sell("stake", stakeSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (staffPurchaseCount > 0) {
            world.purchase("staff", staffPurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (staffSellCount > 0) {
            world.sell("staff", staffSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (shieldPurchaseCount > 0) {
            world.purchase("shield", shieldPurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (shieldSellCount > 0) {
            world.sell("shield", shieldSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (helmetPurchaseCount > 0) {
            world.purchase("helmet", helmetPurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (helmetSellCount > 0) {
            world.sell("helmet", helmetSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (healthPurchaseCount > 0) {
            world.purchase("healthPotion", healthPurchaseCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }
        if (healthSellCount > 0) {
            world.sell("healthPotion", healthSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (oneRingSellCount > 0) {
            world.sell("theonering", oneRingSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (andurilSellCount > 0) {
            world.sell("anduril", andurilSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (treeStumpSellCount > 0) {
            world.sell("treeStump", treeStumpSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        if (doggyCoinSellCount > 0) {
            world.sell("doggieCoin", doggyCoinSellCount);
            if (!world.isPurchased()) world.setIsPurchased(true);
        }

        int totalSum = swordCost + healthCost + amourCost + shieldCost + stakeCost + staffCost + helmetCost + oneRingCost + treeStumpCost + andurilCost;
        world.getGoldProperty().set(goldInHand - totalSum);
        world.setIsPurchased(true);
    }

    private Slider createSlider() {
        Slider s = new Slider();
        s.setShowTickMarks(true);
        s.setShowTickLabels(true);
        s.setMajorTickUnit(2);
        s.setMinorTickCount(0);
        s.setMin(0);
        s.setMax(10);
        s.valueProperty().addListener((obs, oldVal, newVal) ->
            s.setValue(Math.round(newVal.doubleValue()))
        );
        return s;        
    }
}
