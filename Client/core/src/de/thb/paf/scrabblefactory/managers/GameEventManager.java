package de.thb.paf.scrabblefactory.managers;

import java.util.HashMap;
import java.util.Map;

import de.thb.paf.scrabblefactory.models.events.GameEventType;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;

/**
 * Manager class responsible to manage all registered game objects listening for certain events
 * and route those triggered events to the listening targets.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameEventManager implements IGameManager {

    /**
     * The singleton instance of the GameEventManager
     */
    private static GameEventManager instance;

    /**
     * List of all registered game events
     */
    private Map<GameEventType, IGameEvent> events;

    /**
     * static initializer: called when the class is loaded by the JVM
     */
    static {
        instance = new GameEventManager();
    }

    /**
     * Private singleton constructor.
     */
    private GameEventManager() {
        this.events = new HashMap<>();
    }

    /**
     * Get the global GameEventManager instance.
     * @return The global game event manager instance
     */
    public static GameEventManager getInstance() {
        return instance;
    }

    /**
     * Add a game event to the list of registered game events to handle.
     * @param gameEvent The game event instance to add
     */
    public void addGameEvent(IGameEvent gameEvent) {
        this.events.put(gameEvent.getEventType(), gameEvent);
    }

    /**
     * Get registered game event.
     * @param eventType The event type used access key
     * @return The found event type
     */
    public IGameEvent getGameEvent(GameEventType eventType) {
        return this.events.get(eventType);
    }

    /**
     * Trigger event selected by it's event type.
     * @param eventType The event type the event object is registered with
     */
    public void triggerEvent(GameEventType eventType) {
        IGameEvent gameEvent = this.events.get(eventType);
        // if we found a registered event, we're going to trigger it
        if(gameEvent != null) {
            gameEvent.invoke();
        }
    }

    @Override
    public void dispose() {
        // nothing to dispose here
    }
}
