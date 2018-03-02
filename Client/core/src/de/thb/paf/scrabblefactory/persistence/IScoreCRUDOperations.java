package de.thb.paf.scrabblefactory.persistence;

import java.util.List;

import de.thb.paf.scrabblefactory.persistence.entities.Score;

/**
 * Defines supported CRUD-operations dedicated to the 'Score' entity.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IScoreCRUDOperations {

    /**
     * Create a new score entry on the database.
     * @param score The score to add to the database
     * @return The added score extended by a unique id retrieved from the database
     */
    Score createScore(Score score);

    /**
     * Read a specific score from the database
     * @param scoreID The score's unique id
     * @return The found score entry
     */
    Score readScore(int scoreID);

    /**
     * Read all available scores from the database.
     * @return List of found score entries
     */
    List<Score> readAllScores();
}
