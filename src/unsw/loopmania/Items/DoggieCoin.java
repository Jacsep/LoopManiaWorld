package unsw.loopmania.Items;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class DoggieCoin extends Item {
    private int value;

    public DoggieCoin(SimpleIntegerProperty x,  SimpleIntegerProperty y) {
        super(x, y);
        fluctuatePrice();
    }

    public void fluctuatePrice() {
        Random rand = new Random();
        this.value = rand.nextInt(500);
    }

    public int returnValueOfCoin() {
        return this.value;
    }

    @Override
    public String getType() {
        return "doggieCoin";
    }
    
}
