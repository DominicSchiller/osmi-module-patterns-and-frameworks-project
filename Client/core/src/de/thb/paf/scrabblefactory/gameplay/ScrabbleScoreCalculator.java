package de.thb.paf.scrabblefactory.gameplay;


import java.util.HashMap;
import java.util.Map;

/**
 * Static class calculating scrabble scores.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class ScrabbleScoreCalculator {

    /**
     * Hash-Map listing scrabble characters and it's corresponding scrabble points
     */
    private static Map<Character, Integer> scrabbleScoreTable = new HashMap<Character, Integer>() {{
        put('A', 5);
        put('B', 2);
        put('C', 2);
        put('D', 4);
        put('E', 15);
        put('F', 2);
        put('G', 3);
        put('H', 4);
        put('I', 6);
        put('J', 1);
        put('K', 2);
        put('L', 3);
        put('M', 4);
        put('N', 9);
        put('O', 3);
        put('P', 1);
        put('Q', 1);
        put('R', 6);
        put('S', 7);
        put('T', 6);
        put('U', 6);
        put('V', 1);
        put('W', 1);
        put('X', 1);
        put('Y', 1);
        put('Z', 1);
    }};

    /**
     * Private Contructor
     */
    private ScrabbleScoreCalculator() {
        // this is a raw static class
    }

    /**
     * Calculate a scrabble score.
     * @param searchWord The solved search word
     * @param remainingTime The remaining time
     * @return The calculated scrabble score
     */
    public static int calculateScore(String searchWord, long remainingTime) {
        int score = 1;

        int lengthMultiplier = searchWord.length();
        for(char c : searchWord.toUpperCase().toCharArray()) {
            int charPoints = scrabbleScoreTable.get(c);
            charPoints *= lengthMultiplier;
            score += charPoints;
        }

        int remainingTimeMultiplier = (int)((remainingTime / 1000) / 10);
        score *= remainingTimeMultiplier;
        return score;
    }
}
