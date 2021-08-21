package unsw.loopmania;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardImageView extends ImageView {
    String description;
    String itemName;

    public CardImageView(Image m, String description, String name) {
        super(m);
        this.description = description;
        this.itemName = name;
    }
    public String getDescription() {
        return description;
    }

    public String getName() {
        return itemName;
    }
}