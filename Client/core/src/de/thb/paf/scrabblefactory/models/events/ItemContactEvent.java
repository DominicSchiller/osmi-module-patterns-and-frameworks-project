package de.thb.paf.scrabblefactory.models.events;


import de.thb.paf.scrabblefactory.models.IGameObject;

/**
 * Game event dedicated to submit a game entity's collision contact with a game item.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class ItemContactEvent extends GameEvent {

    /**
     * The entity which has contacted the game item
     */
    private IGameObject contact;

    /**
     * The item which got hit
     */
    private IGameObject item;

    /**
     * Default Constructor
     */
    public ItemContactEvent() {
        super(GameEventType.ITEM_CONTACT);
    }

    /**
     * Get the in the collision involved contact.
     * @return The in the collision involved contact.
     */
    public IGameObject getContact() {
        return this.contact;
    }

    /**
     * Set the in the collision involved contact.
     * @param contact The in the collision involved contact to apply
     */
    public void setContact(IGameObject contact) {
        this.contact = contact;
    }

    /**
     * Get the in the collision involved item.
     * @return The in the collision involved item
     */
    public IGameObject getItem() {
        return this.item;
    }

    /**
     * Set the in the collision involved item.
     * @param item The in the collision involved item to apply
     */
    public void setItem(IGameObject item) {
        this.item = item;
    }
}
