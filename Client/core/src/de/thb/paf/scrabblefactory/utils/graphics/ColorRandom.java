package de.thb.paf.scrabblefactory.utils.graphics;


import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Utility class 
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class ColorRandomizer {

    private static final float DEFAULT_ALPHA = 1.0f;

    private static final Map<Integer, String> ALPHA_HEX_STRINGS;

    private static final String[] COLORS;

    private Random random;

    static {
        COLORS = new String[] {
                "1AAD00FF", // green
                "23B7E5FF", // blue
                "FF0000FF", // red
                "8E0058FF", // violet
                "4B0093FF", // purple
                "000000FF", // black
                "8B8D8EFF", // gray
                "FF00E7FF", // pink
                "00C181FF", // cyan
                "351500FF" // brown
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

    public ColorRandomizer() {
        this.random = new Random();
    }

    public Color getNextColor(int alpha) {
        int index = random.nextInt(COLORS.length);
        String hexString = COLORS[index] + ((alpha % 5 == 0) ? ALPHA_HEX_STRINGS.get(alpha) : "");
        long colorHex = Long.parseLong(hexString, 16);

        return new Color((int)colorHex);
    }
}
