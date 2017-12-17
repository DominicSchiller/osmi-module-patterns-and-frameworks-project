package de.thb.paf.scrabblefactory.models.events;

import java.util.Observer;

/**
 * Interface that declares methods a dedicated game event must implement.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IGameEvent {

    /**
     * Get the associated game event type.
     * @return The associated game event type.
     */
    GameEventType getEventType();

    /**
     * Add observer to the list of listeners.
     * @param observer The observer to add
     */
    void addListener(Observer observer);

    /**
     * Remove observer from the list of listeners.
     * @param observer The observer to remove
     */
    void removeListener(Observer observer);

    /**
     * Invoke the game event and notifies all listeners.
     */
    void invoke();
}
