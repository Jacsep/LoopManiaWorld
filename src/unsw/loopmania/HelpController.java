package unsw.loopmania;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class HelpController {
    private MenuSwitcher mainMenuSwitcher;
    private String helpContent = 
        "The game world contains a path composed of image tiles (see more details in this document) which forms a loop.\n\n" +
        "The Character automatically moves clockwise from position to position through this path, starting from the Hero's Castle.\n\n" +
        "The game world contains buildings (see buildings table below), enemies (see enemies table below), gold , health potions , and the Character . \n\n" +
        "You can see more information about gold and health potions in the items table below. Enemies will move around the path, " +
        "and their method of doing so depends on the enemy type.\n" +
        "It is important to note that in this document, the Human Player and Character are distinct:\n\n" +

        "The Character refers to the main Character within the game which the Human Player wishes to help win the game, represented " +
        "by a picture of a person . The Character completes many interactions such as moving around and fighting battles automatically, " +
        "without input from the Human Player.\n\n" +
        "The Human Player refers to the user playing the game application. The Human Player wishes to win the game by helping the Character " + 
        "complete all goals, and is able to help the Character win the game by creating buildings, equipping items, " +
        "purchasing and selling items, consuming health potions, and pausing the game (pausing makes it easier to drag and drop).\n\n" +

        "When the Character is attacked by an enemy, a battle is started involving nearby enemies and the Character, and either the Character " +
        "will defeat all enemies within this battle and win rewards, which can consist of cards, experience, gold, " +
        "and equipment (see items table below). Alternatively, the Character will be killed and the Human Player loses the game, and the game " +
        "ends. The battle is automatically played - the Human Player has no ability to perform any game interactions during a battle.\n" +
        "More precisely, when the Character moves within the battle radius of an enemy on the path (this differs by type of enemy), a battle " +
        "will commence. Those enemies for which the Character is within their support radius (support radius is distinct from battle radius, " +
        "and differs by type of enemy) will join the battle against the Character and its allies. Some enemies such as vampires have a larger support radius (see enemies table below).";                        
    @FXML
    GridPane helpGrid;
    @FXML
    Button exitButton;

    @FXML
    public void initialize() {
        Image gameImage = new Image((new File("src/images/game.png")).toURI().toString());
        ImageView gameImageView = new ImageView(gameImage);
        TextArea t = new TextArea();
        //t.setMaxHeight(400);
        //t.setMaxWidth(400);
        t.setWrapText(true);
        helpGrid.add(t, 0, 1);
        helpGrid.add(gameImageView, 0, 2);
        helpGrid.add(exitButton, 0, 0);
        t.setText(helpContent);

        
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        this.mainMenuSwitcher = mainMenuSwitcher;
    }  
    
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        mainMenuSwitcher.switchMenu();
    }    
}
