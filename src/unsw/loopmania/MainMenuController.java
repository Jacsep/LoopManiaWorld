package unsw.loopmania;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher modeSwitcher;
    private MenuSwitcher helpSwitcher;
    private MenuSwitcher loadSwitcher;

    @FXML
    private Button loadGameButton;

    public void initialize() throws IOException {
        Path path = Paths.get("saveFile.json");
        boolean exists = Files.exists(path);
        if (exists) {
            loadGameButton.setDisable(false);
        } else {
            loadGameButton.setDisable(true);
        }
    }

    public void setModeSwitcher(MenuSwitcher modeSwitcher){
        this.modeSwitcher = modeSwitcher;
    }

    public void setHelpSwitcher(MenuSwitcher helpSwitcher) {
        this.helpSwitcher = helpSwitcher;
    }

    public void setLoadSwitcher(MenuSwitcher loadSwitcher) {
        this.loadSwitcher = loadSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToMode() throws IOException {
        modeSwitcher.switchMenu();
    }

    @FXML
    private void loadGame() throws IOException {
        loadSwitcher.switchMenu();
    }

    @FXML
    private void switchToHelp() throws IOException {
        helpSwitcher.switchMenu();
    }    
}
