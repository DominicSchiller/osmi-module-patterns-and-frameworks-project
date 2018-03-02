package de.thb.paf.scrabblefactory.managers;

/**
 * Interface that declares methods a dedicated game manager class must implement
 * in order to work correctly with respect of the game's resources and performance management.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

interface IGameManager {

    /**
     * Called when this manager should release all resources.
     */
    void dispose();
}
