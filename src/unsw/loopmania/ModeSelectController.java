package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ModeSelectController {
    
    @FXML
    private Button startButton;

    @FXML
    private RadioButton survivalButton;

    @FXML
    private ToggleGroup modeSelect;

    private MenuSwitcher gameSwitcher;
    private MenuSwitcher customSwitcher;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    public void setCustomSwitcher(MenuSwitcher customSwitcher) {
        this.customSwitcher = customSwitcher;
    }

    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    @FXML
    public void switchToCustom() throws IOException {
        customSwitcher.switchMenu();
    }

    @FXML
    public String getMode() {
        RadioButton selectedRadioButton = (RadioButton) modeSelect.getSelectedToggle();
        return selectedRadioButton.getText();
    }

}
