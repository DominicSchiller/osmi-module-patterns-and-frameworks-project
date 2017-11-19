package de.thb.paf.scrabblefactory.managers;

import java.util.HashMap;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.screens.IGameScreen;
import de.thb.paf.scrabblefactory.screens.ScreenState;

/**
 * Manager class responsible handling the overall screen management.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class GameScreenManager implements IGameManager {

    /**
     * Collection of active screens
     */
    private final HashMap<ScreenState, IGameScreen> screens;

    /**
     * The singleton instance of the GameScreenManager
     */
    private static final GameScreenManager instance;

    /**
     * static initializer: called when the class is loaded by the JVM
     */
    static {
        instance = new GameScreenManager();
    }

    /**
     * Get the global GameScreenManager instance.
     * @return The global game screen manager instance
     */
    public static GameScreenManager getInstance() {
        return instance;
    }

    /**
     * Private singleton constructor
     */
    private GameScreenManager() {
        this.screens = new HashMap<ScreenState, IGameScreen>();
    }

    /**
     * Set next screen to be active.
     * @param screen The screen to set as active
     */
    public void setScreen(IGameScreen screen) {
        ScrabbleFactory.getInstance().setScreen(screen);
    }

    @Override
    public void dispose() {
        // going to dispose all referenced (active) screens
        for(IGameScreen screen : this.screens.values()) {
            screen.dispose();
        }
    }
}

