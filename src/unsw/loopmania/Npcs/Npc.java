package unsw.loopmania.Npcs;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;

import unsw.loopmania.Helpers.Chance;

public abstract class Npc extends MovingEntity {
    /**
     * Constructor for an NPC
     * @param position - the position of the NPC on the map
     */
    public Npc(PathPosition position) {
        super(position);
    }

    /**
     * Move the npc character has a 50% chance to not move,
     * and 25% chance to move down and 25% chance to move up
     */
    public void move() {
        int directionChoice = Chance.intRangeChance(1, 100);
        if (directionChoice <= 25) {
            moveUpPath();
        } else if (directionChoice > 75){
            moveDownPath();
        }
    }
}
