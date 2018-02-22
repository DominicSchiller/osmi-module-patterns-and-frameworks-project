package de.thb.paf.scrabblefactory.persistence.entities;


/**
 * Represents mapping-class for the database entity 'Score'.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class Score implements IDBEntity {

    /**
     * The unique id
     */
    private int id;

    /**
     * The reached score
     */
    private int score;

    /**
     * Default Constructor.
     */
    public Score() {}

    /**
     * Constructor.
     * @param score The reached score
     */
    public Score(int score) {
        this.score = score;
    }

    /**
     * Get the reached score
     * @return The reached score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int id) {

    }


}
