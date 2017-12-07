package de.thb.paf.scrabblefactory.screens;


/**
 * Abstract representation of a game screen.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public abstract class GameScreen implements IGameScreen {

    private final ScreenState state;

    /**
     * Default Constructor
     */
    GameScreen(ScreenState state) {
        this.state = state;
    }


    @Override
    public ScreenState getState() {
        return this.state;
    }
}
