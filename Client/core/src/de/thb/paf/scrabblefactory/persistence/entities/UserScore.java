package de.thb.paf.scrabblefactory.persistence.entities;


import java.util.Date;

/**
 * Represents mapping-class for the database entity 'UserScore'.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class UserScore implements IDBEntity {

    /**
     * The associated user entity
     */
    private User user;

    /**
     * The associated score entity
     */
    private Score score;

    /**
     * The user-score's creation date
     */
    private Date createdAt;

    /**
     * Default Constructor.
     */
    public UserScore() {}

    /**
     * Constructor.
     * @param user The user
     * @param score The user's score
     * @param createdAt The user score's creation date
     */
    public UserScore(User user, Score score, Date createdAt) {
        this.user = user;
        this.score = score;
        this.createdAt = createdAt;
    }

    /**
     * Get the associated user entity.
     * @return The associated user entity
     */
    public User getUser() {
        return user;
    }

    /**
     * Get the associated score entity.
     * @return The associated score entity
     */
    public Score getScore() {
        return score;
    }

    /**
     * Get the user-score's creation date.
     * @return The user-score's creation date
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int getID() {
        return -1;
    }

    @Override
    public void setID(int id) {
        // this entity does not have any id
    }
}
