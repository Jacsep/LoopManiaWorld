package unsw.loopmania.Helpers;

import java.lang.Math;

public class Distance {
    /*
     * Given the coordinates of two points calculate the euclidian distance between them
     * @param x1 - x coordinate of point 1
     * @param y1 - y coordinate of point 1
     * @param x2 - x coordinate of point 2
     * @param y2 - y coordinate of point 2
     * @return double containing euclidian distance between points
     */
    public static double calculate(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
