package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public abstract class Item extends StaticEntity{
    /**
     * Constructor for a basic item
     * @param x -  x coordinate of item in inventory
     * @param y -  y coordinate of item in inventory
     */
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
    }
}
