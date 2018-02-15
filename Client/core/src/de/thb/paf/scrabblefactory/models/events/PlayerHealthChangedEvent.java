package de.thb.paf.scrabblefactory.models.events;


/**
 * Game event dedicated to submit a player's health change.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class PlayerHealthChangedEvent extends GameEvent {

    /**
     * The changed health
     */
    private int health;

    /**
     * Default Constructor
     */
    public PlayerHealthChangedEvent() {
        super(GameEventType.PLAYER_HEALTH_CHANGED);
    }

    /**
     * Get the changed health.
     * @return The changed health
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Set the changed health.
     * @param health The changed health
     */
    public void setHealth(int health) {
        this.health = health;
    }

}
