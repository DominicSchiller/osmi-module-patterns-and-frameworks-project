package de.thb.paf.scrabblefactory.models.events;


import de.thb.paf.scrabblefactory.models.IGameObject;

/**
 * Game event dedicated to submit a game entity's collision contact with the game world's ground.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GroundContactEvent extends GameEvent {

    private IGameObject contact;

    /**
     * Default Constructor
     */
    public GroundContactEvent() {
        super(GameEventType.GROUND_CONTACT);
        this.contact = null;
    }

    /**
     * Set the in the collision involved contact.
     * @param contact The involved contact
     */
    public void setContact(IGameObject contact) {
        this.contact = contact;
    }

    /**
     * Get the in the collision involved contact.
     * @return The involved contact
     */
    public IGameObject getContact() {
        return this.contact;
    }
}
