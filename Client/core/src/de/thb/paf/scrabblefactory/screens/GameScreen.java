package de.thb.paf.scrabblefactory.screens;


/**
 * Abstract representation of a game screen.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public abstract class GameScreen implements IGameScreen {

    /**
     * The screen's state type
     */
    private final ScreenState state;

    /**
     * State indicating whether the screen is set as active or not
     */
    boolean isActive;

    /**
     * State indicating whether the screen's components have been initialized or not
     */
    boolean isInitialized;

    /**
     * Default Constructor
     */
    GameScreen(ScreenState state) {
        this.state = state;
    }

    @Override
    public void resize(int width, int height) {
        if(this.isActive && !this.isInitialized) {
            this.show();
        } else {
            this.isActive = true;
        }
    }

    @Override
    public ScreenState getState() {
        return this.state;
    }
}
