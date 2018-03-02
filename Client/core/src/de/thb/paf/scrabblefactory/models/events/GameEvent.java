package de.thb.paf.scrabblefactory.models.events;

import java.util.Observable;
import java.util.Observer;

/**
 * Abstract representation of a game event.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

abstract class GameEvent extends Observable implements IGameEvent {

    /**
     * The associated game event type
     */
    private GameEventType eventType;

    /**
     * Constructor.
     * @param eventType The associated game event type
     */
    GameEvent(GameEventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public void invoke() {
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public GameEventType getEventType() {
        return eventType;
    }

    @Override
    public void addListener(Observer observer) {
        this.addObserver(observer);
    }

    @Override
    public void removeListener(Observer observer) {
        this.deleteObserver(observer);
    }
}
