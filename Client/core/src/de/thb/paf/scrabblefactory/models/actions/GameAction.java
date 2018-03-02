package de.thb.paf.scrabblefactory.models.actions;

import de.thb.paf.scrabblefactory.models.events.GameEventType;

/**
 * Abstract representation of an game action.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public abstract class GameAction implements IGameAction {

    /**
     * List of event types the action is registered to
     */
    private GameEventType[] eventsToHandle;

    /**
     * Default Constructor
     */
    public GameAction() {
        this.eventsToHandle = new GameEventType[0];
    }

    @Override
    public GameEventType[] getEventTypesToHandle() {
        return this.eventsToHandle;
    }

    @Override
    public void setEventTypesToHandle(GameEventType[] eventTypesToHandle) {
        this.eventsToHandle = eventTypesToHandle;
    }
}
