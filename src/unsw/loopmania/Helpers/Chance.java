package unsw.loopmania.Helpers;

import java.util.Random;

public class Chance {
    /**
     * Pseudorandomly output true and false at the rate determined by percentage
     * @param percentage - the percentage chance of obtaining a true
     * @return true percentage% of the time and false otherwise
     */
    public static boolean binomialChance(int percentage) {
        if ((new Random()).nextInt(99) + 1 <= percentage) {
            return true;
        }
        return false;
    }

    /**
     * Generate a pseudo random integer within a specified range
     * @param min - the upper limit (inclusive)
     * @param max - the lower limit (inclusive)
     * @return the random integer
     */
    public static int intRangeChance(int min, int max) {
        return (new Random()).nextInt(max - min + 1) + min;
    }
    
}
