package de.thb.paf.scrabblefactory.models.level;

import de.thb.paf.scrabblefactory.models.IGameObject;

/**
 * Interface that declares methods a dedicated level  class must implement
 * in order to work correctly as a level representation.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface ILevel extends IGameObject {

    /**
     * Get the level's unique identifier.
     * @return The level's unique identifier
     */
    int getID();

    /**
     * Get the level's title.
     * @return The level's title.
     */
    String getTitle();

    /**
     * Get the level's given countdown.
     * @return The level's given countdown
     */
    int getCountdown();

}
