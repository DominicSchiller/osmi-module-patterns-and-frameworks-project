package de.thb.paf.scrabblefactory.utils;


import java.util.Random;

/**
 * Basic random value calculator.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class Randomizer {

    /**
     * The random calculator
     */
    private static Random random;

    static {
        random = new Random();
    }

    /**
     * Calculate a random number between a min and max value.
     * @param min The min bound
     * @param max The max bound
     * @return The calculated random number
     */
    public static int nextRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
