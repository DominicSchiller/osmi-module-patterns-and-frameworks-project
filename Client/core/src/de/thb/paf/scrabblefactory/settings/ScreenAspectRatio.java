package de.thb.paf.scrabblefactory.settings;

/**
 * Enumeration of all supported screen aspect ratios.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum ScreenAspectRatio {

    SIXTEEN_TO_NINE(16f / 9f, "16:9"),
    SIXTEEN_TO_TEN(16f / 10f, "16:10"),
    FOUR_TO_THREE(4f / 3f, "4:3"),
    THREE_TO_TWO(3f / 2f, "3:2");

    /**
     * Constructor
     * @param value The aspect ratio's calculated float value
     * @param name The aspect ratio's description
     */
    ScreenAspectRatio(float value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * The aspect ratio's calculated quotient
     */
    public final float value;

    /**
     * The aspect ratio's name
     */
    public final String name;
}
