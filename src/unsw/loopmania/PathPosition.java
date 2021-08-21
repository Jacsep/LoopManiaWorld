package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * objects of this class represents the position in the path.
 * it holds the current position in the path and a reference to the orderedPath internally.
 * It also holds SimpleIntegerProperties for x and y coordinates, which we have getter methods for,
 *     so we can return them and attach ChangeListers, to decouple the frontend and backend.
 * The SimpleIntegerProperties are updated automatically when we move through the path.
 */
public class PathPosition{

    private int originalPositionInPath;
    private int currentPositionInPath;
    private List<Pair<Integer, Integer>> orderedPath;
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;

    /*
     * the integerproperty internal int is the x or y coordinate - since want to use this class to bind with node positions
     * currentPositionInPath is an index of orderedPath
     * usingValue0 is true if using value0 in path, false if using value1
     * orderedPath is the list of path coordinates in pairs
     */
    public PathPosition(int currentPositionInPath, List<Pair<Integer, Integer>> orderedPath){
        this.originalPositionInPath = currentPositionInPath;
        this.currentPositionInPath = currentPositionInPath;
        this.orderedPath = orderedPath;
        x = new SimpleIntegerProperty();
        y = new SimpleIntegerProperty();
        // update internal property
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * move forward through the path i.e. clockwise
     */
    public void moveDownPath(){
        currentPositionInPath = (currentPositionInPath + 1)%orderedPath.size();
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * move backwards through the path, i.e. anticlockwise
     */
    public void moveUpPath(){
        currentPositionInPath = (currentPositionInPath - 1 + orderedPath.size())%orderedPath.size();
        resetCoordinatesBasedOnPositionInPath();
    }

    public void changePosition(int position) {
        currentPositionInPath = position;
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * change the x and y SimpleIntegerProperties to reflect the current values of
     * the current position in the path, and the ordered path.
     */
    private void resetCoordinatesBasedOnPositionInPath(){
        x.set(orderedPath.get(currentPositionInPath).getValue0());
        y.set(orderedPath.get(currentPositionInPath).getValue1());
    }

    /*
     * get the X coordinate of a potential move down path
     * @return int containing X coordinate of next tile in path
     */
    public int getMoveDownX() {
        return orderedPath.get((currentPositionInPath + 1)%orderedPath.size()).getValue0();
    }

    /*
     * get the Y coordinate of a potential move down path
     * @return int containing Y coordinate of next tile in path
     */
    public int getMoveDownY() {
        return orderedPath.get((currentPositionInPath + 1)%orderedPath.size()).getValue1();
    }

    /*
     * get the X coordinate of a potential move up path
     * @return int containing X coordinate of previous tile in path
     */
    public int getMoveUpX() {
        return orderedPath.get((currentPositionInPath - 1 + orderedPath.size())%orderedPath.size()).getValue0();
    }

    /*
     * get the Y coordinate of a potential move up path
     * @ return int containing Y coordinate of previous tile in path
     */
    public int getMoveUpY() {
        return orderedPath.get((currentPositionInPath - 1 + orderedPath.size())%orderedPath.size()).getValue1();
    }

    /*
     * get the X coordinate of the current path tile
     * @return int containing current X coordinate
     */
    public int getXCoord() {
        return x.get();
    }

    /*
     * get the Y coordinate of the current path tile
     * @return int containing current Y coordinate
     */
    public int getYCoord() {
        return y.get();
    }

    /*
     * get the X coordinate of the current path tile
     * @return SimpleIntegerProperty containing current X coordinate
     */
    public SimpleIntegerProperty getX(){
        return x;
    }

    /*
     * get the Y coordinate of the current path tile
     * @return SimpleIntegerProperty containing current Y coordinate
     */
    public SimpleIntegerProperty getY(){
        return y;
    }

    /**
     * Set path position to the original position
     */
    public void resetPosition() {
        this.currentPositionInPath = originalPositionInPath;
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * Getter for position in path
     * @return int containing path position index
     */
    public int getCurrentPosition() {
        return this.currentPositionInPath;
    }
}
