package de.thb.paf.scrabblefactory.models.actions;


/**
 * Enumeration of supported move directions.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public enum MoveDirectionType {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1),
    LEFT_UP(-1, 1),
    RIGHT_UP(1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(1, -1);

    /**
     * Constructor
     * @param xSign The positive/negative sign along the x-axis
     * @param ySign The positive/negative sign along the y-axis
     */
    MoveDirectionType(int xSign, int ySign) {
        this.xSign = xSign;
        this.ySign = ySign;
    }

    /**
     * The positive/negative sign along the x-axis
     */
    public final int xSign;

    /**
     * The positive/negative sign along the y-axis
     */
    public final int ySign;
}
