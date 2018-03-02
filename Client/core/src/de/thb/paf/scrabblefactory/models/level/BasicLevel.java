package de.thb.paf.scrabblefactory.models.level;

/**
 * Represents a basic level.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class BasicLevel extends GameLevel {

    /**
     * The level's given word pool
     */
    private String[] wordPool;

    /**
     * Default Constructor
     */
    public BasicLevel() {
        super();
    }

    /**
     * Get the level's given word pool.
     * @return the level's given word pool
     */
    public String[] getWordPool() {
        return this.wordPool;
    }
}
