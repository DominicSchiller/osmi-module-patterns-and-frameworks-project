package de.thb.paf.scrabblefactory.models.entities;

/**
 * Represents a cheese entity.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class Cheese extends GameEntity {

    /**
     * Default constructor
     */
    public Cheese() {
    }

    /**
     * Constructor
     * @param letter The cheese's associated letter
     */
    public Cheese(char letter) {
        this.letter = letter;
    }

    /**
     * Constructor
     * @param letter The cheese's associated letter
     * @param isRotten Quality characteristic whether the cheese is rotten or not
     */
    public Cheese(char letter, boolean isRotten) {
        this(letter);
        this.isRotten = isRotten;
    }

    /**
     * Quality characteristic whether the cheese is rotten or not
     */
    private boolean isRotten;

    /**
     * The cheese's associated letter
     */
    private char letter;

    /**
     * Get the quality characteristic whether the cheese is rotten or not.
     * @return The rotten status
     */
    public boolean isRotten() {
        return this.isRotten;
    }

    /**
     * Set the quality characteristic whether the cheese is rotten or not.
     * @param isRotten The new rotten status
     */
    public void setRotten(boolean isRotten) {
        this.isRotten = isRotten;
    }

    /**
     * Get the cheese's associated letter.
     * @return The cheese's associated letter
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Get the cheese's associated letter.
     * @param letter The new letter to associate
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

}