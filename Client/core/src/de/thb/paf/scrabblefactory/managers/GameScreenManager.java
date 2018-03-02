package de.thb.paf.scrabblefactory.managers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
     * The singleton instance of the GameScreenManager
     */
    private static final GameScreenManager instance;

    /**
     * Hash map of active screens
     */
    private final HashMap<ScreenState, IGameScreen> screens;

    /**
     * History of already navigated screens
     */
    private List<ScreenState> screenHistory;

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
        this.screens = new HashMap<>();
        this.screenHistory = new LinkedList<>();
    }

    /**
     * Set next screen to be active.
     * @param screen The screen to set as active
     */
    public void showScreen(IGameScreen screen) {
        this.screens.put(screen.getState(), screen);
        this.screenHistory.add(screen.getState());
        ScrabbleFactory.getInstance().setScreen(screen);
    }

    /**
     * Get a specific screen.
     * @param screenState The screen's state to identify the requested screen
     */
    public IGameScreen getScreen(ScreenState screenState) {
        IGameScreen screen = this.screens.get(screenState);
        return screen;
    }

    /**
     * Navigate to the last screen.
     * @return Status if there was a last screen to navigate to
     */
    public boolean showLastScreen() {
        if(this.screenHistory.size() <= 1) {
            return false;
        }
        IGameScreen lastScreen = this.screens.get(
                this.screenHistory.get(this.screenHistory.size() - 2)
        );

        ScrabbleFactory.getInstance().setScreen(lastScreen);
        lastScreen.resume();
        this.screenHistory.remove(this.screenHistory.size()-1);

        return true;
    }

    /**
     * Clear the screen history.
     */
    public void clearHistory() {
        this.screenHistory.clear();
    }

    /**
     * Dismiss a stored screen.
     * @param screenState The identifier from the screen to dismiss
     * @return The dismiss success state
     */
    public boolean dismissScreen(ScreenState screenState) {
        IGameScreen screen = this.screens.remove(screenState);
        if(screen != null) {
            screen.dispose();
            return true;
        }

        return false;
    }

    @Override
    public void dispose() {
        // going to dispose all referenced (active) screens
        for(IGameScreen screen : this.screens.values()) {
            screen.dispose();
        }
    }
}

