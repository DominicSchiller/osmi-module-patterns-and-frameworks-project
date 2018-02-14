package de.thb.paf.scrabblefactory.models.events;

import de.thb.paf.scrabblefactory.models.IGameObject;

/**
 * Discard event dedicated to trigger a game object's discard action.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class DiscardEvent extends GameEvent {

    /**
     * The target which should be triggered
     */
    private IGameObject discardTarget;

    /**
     * Default Constructor
     */
    public DiscardEvent() {
        super(GameEventType.DISCARD);
    }

    /**
     * Get the event's target which should be triggered.
     * @return The event's target
     */
    public IGameObject getDiscardTarget() {
        return this.discardTarget;
    }

    /**
     * Set the event's target which should be triggered.
     * @param discardTarget The event's target
     */
    public void setDiscardTarget(IGameObject discardTarget) {
        this.discardTarget = discardTarget;
    }
}
