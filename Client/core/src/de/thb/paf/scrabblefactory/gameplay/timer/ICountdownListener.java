package de.thb.paf.scrabblefactory.gameplay.timer;

/**
 * This interface defines methods a countdown timer will call
 * back for registered countdown listeners.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface ICountdownListener {

    /**
     * Called when the timer has been just started.
     * @param time The remaining time
     */
    void onCountdownStarted(long time);

    /**
     * Called every countdown tick.
     * @param time The remaining time
     */
    void onCountdownTick(long time);

    /**
     * Called right after the timer has been finished counting down.
     * @param time The remaining time
     */
    void onCountdownFinished(long time);
}
