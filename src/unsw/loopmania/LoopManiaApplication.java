package unsw.loopmania;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;
    private Parent gameRoot;
    private MainMenuController mainMenuController;
    private FXMLLoader gameLoader;
    private LoopManiaWorldControllerLoader loopManiaLoader;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game

        // load the main menu
        mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load help 
        HelpController helpController = new HelpController();
        FXMLLoader helpLoader =  new FXMLLoader(getClass().getResource("HelpMenuView.fxml"));
        helpLoader.setController(helpController);
        Parent helpRoot = helpLoader.load();

        // load mode select
        ModeSelectController modeSelectController = new ModeSelectController();
        FXMLLoader modeLoader = new FXMLLoader(getClass().getResource("ModeSelectView.fxml"));
        modeLoader.setController(modeSelectController);
        Parent modeRoot = modeLoader.load();

        // load custom world select
        CustomWorldController customModeController = new CustomWorldController();
        FXMLLoader customLoader = new FXMLLoader(getClass().getResource("CustomWorldView.fxml"));
        customLoader.setController(customModeController);
        Parent customRoot = customLoader.load();

        // load path select
        PathSelectController pathSelectController = new PathSelectController();
        FXMLLoader pathLoader = new FXMLLoader(getClass().getResource("PathSelectView.fxml"));
        pathLoader.setController(pathSelectController);
        Parent pathRoot = pathLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        String css = this.getClass().getResource("test.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        /*mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);
        try {
            mainMenuController.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }});*/

        modeSelectController.setGameSwitcher(() -> {
            try {
                loadWorld(primaryStage, scene, mainMenuRoot, "world_with_twists_and_turns.json");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.setMode(modeSelectController.getMode());
            mainController.startTimer();
            mainController.playBackgroundMusic();
        });

        modeSelectController.setCustomSwitcher(() -> {
            switchToRoot(scene, customRoot, primaryStage);
        });

        customModeController.setPathSwitcher(() -> {
            pathSelectController.initialize(customModeController.getWidth(), customModeController.getLength(), customModeController.getStartingGold(), customModeController.getGoalType(), customModeController.getGoalNum());
            switchToRoot(scene, pathRoot, primaryStage);
        });

        pathSelectController.setGameSwitcher(() -> {
            pathSelectController.writeInitialToJSON(customModeController.getWidth(), customModeController.getLength(), customModeController.getStartingGold(), customModeController.getGoalType(), customModeController.getGoalNum());
            try {
                loadWorld(primaryStage, scene, mainMenuRoot, "loadFile.json");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.setMode(modeSelectController.getMode());
            mainController.startTimer();
            mainController.playBackgroundMusic();
        });

        mainMenuController.setHelpSwitcher(() -> {
            switchToRoot(scene, helpRoot, primaryStage);
        });

        mainMenuController.setModeSwitcher(() -> {switchToRoot(scene, modeRoot, primaryStage);});
        mainMenuController.setLoadSwitcher(() -> {
            try {
                JSONObject json = new JSONObject(new JSONTokener(new FileReader("saveFile.json")));
                loadWorld(primaryStage, scene, mainMenuRoot, json.getString("fileName"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            switchToRoot(scene, gameRoot, primaryStage);
            try {
                mainController.loadWorld();
            } catch (JSONException | FileNotFoundException e) {
                e.printStackTrace();
            }
            mainController.startTimer();
            mainController.pause();
            mainController.playBackgroundMusic();
        });

        helpController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        
        // deploy the main onto the stage
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadWorld(Stage primaryStage, Scene scene, Parent mainMenuRoot, String world) throws IOException {
        loopManiaLoader = new LoopManiaWorldControllerLoader(world);
        mainController = loopManiaLoader.loadController();
        mainController.setPrimaryStage(primaryStage);
        gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        gameRoot = gameLoader.load();
        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);
        try {
            mainMenuController.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }});
        gameRoot.requestFocus();
    }

    /*@Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }*/

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
