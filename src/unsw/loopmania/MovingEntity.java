package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    /**
     * object holding position in the path
     */
    private PathPosition position;

    /**
     * Create a moving entity which moves up and down the path in position
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position) {
        super();
        this.position = position;
    }

    public void changePosition(PathPosition position) {
        this.position = position;
    }

    public PathPosition returnPosition() {
        return this.position;
    }

    public int getPositionInPath() {
        return this.position.getCurrentPosition();
    }

    public void resetPosition() {
        this.position.resetPosition();
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    public SimpleIntegerProperty x() {
        return position.getX();
    }

    public SimpleIntegerProperty y() {
        return position.getY();
    }

    public int getX() {
        return position.getXCoord();
    }

    public int getY() {
        return position.getYCoord();
    }

    public int getMoveDownX() {
        return position.getMoveDownX();
    }

    public int getMoveDownY() {
        return position.getMoveDownY();
    }

    public int getMoveUpX() {
        return position.getMoveUpX();
    }

    public int getMoveUpY() {
        return position.getMoveUpY();
    }
}
