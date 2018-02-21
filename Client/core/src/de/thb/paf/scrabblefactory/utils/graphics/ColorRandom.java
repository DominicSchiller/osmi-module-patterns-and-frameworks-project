package de.thb.paf.scrabblefactory.utils.graphics;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Utility class help to randomly choose a color from a list of pre-defined colors.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class ColorRandom {

    /**
     * Map of HEX alpha values organized by it's alpha intensity
     * from 0 to 100 counted up in five steps
     */
    private static final Map<Integer, String> ALPHA_HEX_STRINGS;

    /**
     * List of colors to randomly choose from
     */
    private static final String[] COLORS;

    /**
     * The base random class
     */
    private Random random;

    /*
     * Static initializer
     */
    static {
        COLORS = new String[] {
                "1AAD00", // green
                "23B7E5", // blue
                "FF0000", // red
                "8E0058", // violet
                "4B0093", // purple
                "000000", // black
                "8B8D8E", // gray
                "FF00E7", // pink
                "00C181", // cyan
                "351500" // brown
        };

        ALPHA_HEX_STRINGS = new HashMap<Integer, String>() {{
            put(100, "FF");
            put(95, "F2");
            put(90, "E6");
            put(85, "D9");
            put(80, "CC");
            put(75, "BF");
            put(70, "B3");
            put(65, "A6");
            put(60, "99");
            put(55, "8C");
            put(50, "80");
            put(45, "73");
            put(40, "66");
            put(35, "59");
            put(30, "4D");
            put(25, "40");
            put(20, "33");
            put(15, "26");
            put(10, "1A");
            put(5, "0D");
            put(0, "00");
        }};
    }

    /**
     * Constructor
     */
    public ColorRandom() {
        this.random = new Random();
    }

    /**
     * Randomly choose a color from the defined list of available colors.
     * @param alpha The color's alpha value to set (optional)
     * @return The randomly chosen color
     */
    public Color getNextColor(int alpha) {
        int index = random.nextInt(COLORS.length);
        String hexString = COLORS[index] + ((alpha % 5 == 0) ?
                ALPHA_HEX_STRINGS.get(alpha) : ALPHA_HEX_STRINGS.get(100));
        long colorHex = Long.parseLong(hexString, 16);

        return new Color((int)colorHex);
    }
}
