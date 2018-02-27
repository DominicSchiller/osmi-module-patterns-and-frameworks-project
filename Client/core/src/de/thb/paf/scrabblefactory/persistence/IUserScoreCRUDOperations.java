package de.thb.paf.scrabblefactory.persistence;


import java.util.List;

import de.thb.paf.scrabblefactory.persistence.entities.UserScore;

/**
 * Defines supported CRUD-operations dedicated to the 'UserScore' entity.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IUserScoreCRUDOperations {

    /**
     * Create a new user-score entry to the database.
     * @param userScore The user-score to add
     * @return The added user-score
     */
    UserScore createUserScore(UserScore userScore);

    /**
     * Read a specific user-score entry from the database
     * @param userID The user-score's associated user's id
     * @param scoreID The user-score's associated score's id
     * @return The found user-score database entry
     */
    UserScore readUserScore(int userID, int scoreID);

    /**
     * Read all user-score entries from the database
     * @return List of found user-score entries
     */
    List<UserScore> readAllUserScores();
}
