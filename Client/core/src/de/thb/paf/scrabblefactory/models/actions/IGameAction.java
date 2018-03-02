package de.thb.paf.scrabblefactory.models.actions;

import java.util.Observer;

import de.thb.paf.scrabblefactory.models.events.GameEventType;


/**
 * Interface that declares methods a dedicated game action must implement in order
 * to get used correctly by the scrabble factory game engine.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public interface IGameAction extends Observer {

    /**
     * Get all the event types the action is registered to handle.
     * @return List of event types the action is registered to
     */
    GameEventType[] getEventTypesToHandle();

    /**
     * Set all the event types the action is registered to handle.
     * @param eventTypesToHandle List of event types the action is registered to
     */
    void setEventTypesToHandle(GameEventType[] eventTypesToHandle);
}
