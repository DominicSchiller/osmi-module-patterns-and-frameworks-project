package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Screen;

/**
 * Interface that declares methods a dedicated game screen class must implement
 * in order to work correctly as a dedicated screen within the game.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IGameScreen extends Screen {

    /**
     * Updates the screen based on the game's current render tick.
     * @param deltaTime The time passed between the last and the current frame in seconds
     */
    void update(float deltaTime);

    /**
     * Get the screen's state.
     * @return The screen's state
     */
    ScreenState getState();
}
