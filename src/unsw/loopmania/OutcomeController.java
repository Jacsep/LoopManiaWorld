package unsw.loopmania;

import java.io.File;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OutcomeController {
    @FXML
    TextArea information;

    @FXML
    Button exitButton;

    @FXML
    Button restartButton;

    Stage stage;
    Timeline timeline;

    private LoopManiaWorld world;
    public OutcomeController(LoopManiaWorld world, Stage stage, Timeline timeline) {
        this.world = world;
        this.stage = stage;
        this.timeline = timeline;
    }

    @FXML
    public void initialize() {
        exitButton.setOnAction(E -> {
            timeline.pause();
            stage.getOnCloseRequest().handle(null);
            stage.close();
        });
        restartButton.setOnAction(E -> {
            world.setIsRestart(true);
            stage.getOnCloseRequest().handle(null);
            stage.close();

        });
        
        String css = this.getClass().getResource("test.css").toExternalForm();
        information.getStylesheets().add(css);
        information.getStyleClass().add("centeredTextArea");
        information.getStyleClass().add("font14");
        information.setDisable(true);
        int status = world.getGameStatus();
        
        if (status == LoopManiaWorld.GAME_LOST) {
            information.setText("\n\nOops, You lost the game. Please try again.");
        } else if (status == LoopManiaWorld.GAME_WON_ACHIEVED_CYCLE_TARGET) {
            information.setText("\n\nCongratulations, You have achieved your target cycles.\n\nYou won the game.");
        }  else if (status == LoopManiaWorld.GAME_WON_ACHIEVED_GOLD_TARGET) {
            information.setText("\n\nCongratulations, You have achieved your targe gold.\n\nYou won the game.");
        }  else if (status == LoopManiaWorld.GAME_WON_DEFEATED_ENEMY) {
            information.setText("\n\nCongratulations, You have defeated all enemies.\n\nYou won the game.");
        } else {
            information.setText("\n\nCongratulations, You have achieved your targe experiences.\n\nYou won the game.");
        }
    }
}
