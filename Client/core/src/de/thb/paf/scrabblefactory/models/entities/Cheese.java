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
     * Quality characteristic whether the cheese is rotten or not
     */
    private boolean isRotten;

    /**
     * The cheese's associated letter
     */
    private char letter;


    /**
     * Default Constructor
     */
    public Cheese() {
        super();
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     */
    public Cheese(int id, EntityType type) {
        super(id, type);
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     * @param letter The cheese's associated letter
     */
    public Cheese(int id, EntityType type, char letter) {
        this(id, type);
        this.letter = letter;
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     * @param letter The cheese's associated letter
     * @param isRotten Quality characteristic whether the cheese is rotten or not
     */
    public Cheese(int id, EntityType type, char letter, boolean isRotten) {
        this(id, type, letter);
        this.isRotten = isRotten;
    }

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