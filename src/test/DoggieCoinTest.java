package test;


import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Inventory;
import unsw.loopmania.Items.DoggieCoin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoggieCoinTest {
    @Test
    public void TestInventoryFluctuatingPrice() {
        Inventory newInventory = new Inventory();
        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        int initialPrice = doggieCoin.returnValueOfCoin();
        newInventory.addUnequippedItem(doggieCoin);
        newInventory.fluctateValueOfDoggieCoin();
        assertTrue(initialPrice != doggieCoin.returnValueOfCoin());
    }

    @Test
    public void TestFluctuatingPrice() {
        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        int initialPrice = doggieCoin.returnValueOfCoin();
        doggieCoin.fluctuatePrice();
        assertTrue(initialPrice != doggieCoin.returnValueOfCoin());
    }

    @Test
    public void TestReturnType() {
        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertEquals(doggieCoin.getType(), "doggieCoin");
    }

}
