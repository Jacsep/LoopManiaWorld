package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CustomWorldController {
    @FXML
    private TextField widthInput;
    
    @FXML
    private TextField lengthInput;

    @FXML
    private TextField goldInput;

    @FXML
    private TextField goalAmount;

    @FXML
    private Button startGameButton;

    @FXML
    private ToggleGroup pickGoal;
    
    private MenuSwitcher pathSwitcher;

    @FXML
    private void initialize() {
        startGameButton.setDisable(true);

        widthInput.textProperty().addListener((obs, oldText, newText) -> {
            if (Integer.valueOf(newText) > 8) {
                startGameButton.setDisable(true);
            } else if (checkAllInputs()) {
                startGameButton.setDisable(false);
            } else {
                startGameButton.setDisable(true);
            }
        });

        lengthInput.textProperty().addListener((obs, oldText, newText) -> {
            if (Integer.valueOf(newText) > 15) {
                startGameButton.setDisable(true);
            } else if (checkAllInputs()) {
                startGameButton.setDisable(false);
            } else {
                startGameButton.setDisable(true);
            }
        });

        goldInput.textProperty().addListener((obs, oldText, newText) -> {
            if (checkAllInputs()) {
                startGameButton.setDisable(false);
            } else {
                startGameButton.setDisable(true);
            }
        });

        goalAmount.textProperty().addListener((obs, oldText, newText) -> {
            if (checkAllInputs()) {
                startGameButton.setDisable(false);
            } else {
                startGameButton.setDisable(true);
            }
        }); 
    }

    public void setPathSwitcher(MenuSwitcher pathSwitcher) {
        this.pathSwitcher = pathSwitcher;
    }

    @FXML
    private void switchToPathSelect() throws IOException {
        pathSwitcher.switchMenu();
    }

    @FXML
    public int getWidth() {
        return Integer.valueOf(widthInput.getText());
    }

    @FXML
    public int getLength() {
        return Integer.valueOf(lengthInput.getText());
    }

    @FXML
    public int getStartingGold() {
        return Integer.valueOf(goldInput.getText());
    }

    @FXML
    public int getGoalNum() {
        return Integer.valueOf(goalAmount.getText());
    }

    @FXML
    public String getGoalType() {
        RadioButton selectedRadioButton = (RadioButton) pickGoal.getSelectedToggle();
        return selectedRadioButton.getText();
    }

    public boolean checkAllInputs() {
        if ((widthInput.getText().trim().isEmpty() || lengthInput.getText().trim().isEmpty() || goldInput.getText().trim().isEmpty() || goalAmount.getText().trim().isEmpty())) {
            return false;
        }
        return true;
    }
}
