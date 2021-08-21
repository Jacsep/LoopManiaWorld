package unsw.loopmania;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class ExitMenuController {
    @FXML 
    private Button exitGameButton;

    @FXML 
    private Button saveAndExit;

    private LoopManiaWorldController controller;
    private Stage stage;

    public ExitMenuController(Stage stage, LoopManiaWorldController controller) {
        this.controller = controller;
        this.stage = stage;
    }

    @FXML
    private void exitGame() {
        controller.exitGameAndReset();
        stage.close();
    }

    @FXML
    private void saveGameAndExit() {
        controller.saveAndReset();
        stage.close();
    }
}
