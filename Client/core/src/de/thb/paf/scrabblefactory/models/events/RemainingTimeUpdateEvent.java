package de.thb.paf.scrabblefactory.models.events;


/**
 * Game event dedicated to submit a level's remaining time update.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class RemainingTimeUpdateEvent extends GameEvent {

    /**
     * The remaining time
     */
    private long time;

    /**
     * Default Constructor
     */
    public RemainingTimeUpdateEvent() {
        super(GameEventType.REMAINING_TIME_UPDATE);
    }

    /**
     * Get the remaining time.
     * @return The remaining time
     */
    public long getTime() {
        return this.time;
    }

    /**
     * Set the remaining time.
     * @param time The remaining time
     */
    public void setTime(long time) {
        this.time = time;
    }
}
