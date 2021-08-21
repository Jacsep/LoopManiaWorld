package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.BasicEnemy;

public class HerosCastleBuilding extends Building {
    public HerosCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    @Override
    public String getType() {
        return "herosCastle";
    }

    public void specialAbility(Character character, List<BasicEnemy> enemyList) {}
}
