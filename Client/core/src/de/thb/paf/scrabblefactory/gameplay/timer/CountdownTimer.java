package de.thb.paf.scrabblefactory.gameplay.timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the countdown timer for handling a game level's time constraints.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg, Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class CountdownTimer extends Thread {

    /**
     * Default time interval of 1 second used for each countdown tick
     */
    private static final short SLEEP_INTERVAL = 1000;

    /**
     * The set time to countdown from
     */
    private final long MILLISECONDS;

    /**
     * Status indicating if the countdown timer has been stopped
     */
    private boolean isStopped;

    /**
     * Status indicating if the timer has been paused or not
     */
    private boolean isPaused;

    /**
     * Status indicating if a restart of the timer was requested or not
     */
    private boolean isRestartRequested;

    /**
     * List of registered listeners which will be notified for certain events
     */
    private List<ICountdownListener> countdownListeners;

    /**
     * Constructor
     * @param milliseconds The time interval in milliseconds the timer is count down from
     */
    public CountdownTimer(long milliseconds) {
        this.MILLISECONDS = milliseconds;
        this.isPaused = false;
        this.isStopped = false;
        this.countdownListeners = new ArrayList<ICountdownListener>();
    }

    @Override
    public void run() {
        super.run();
        long passedMilliseconds = 0;

        notifyListeners(CountdownEvent.STARTED, (this.MILLISECONDS - passedMilliseconds));
        while(passedMilliseconds < this.MILLISECONDS && !this.isRestartRequested && !this.isStopped) {
            if(!this.isPaused) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                    passedMilliseconds += SLEEP_INTERVAL;
                    notifyListeners(CountdownEvent.TICKED, (this.MILLISECONDS - passedMilliseconds));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        notifyListeners(CountdownEvent.FINISHED, (this.MILLISECONDS - passedMilliseconds));

        // restart our self if requested
        if(this.isRestartRequested) {
            this.isRestartRequested = false;
            this.start();
        }
    }

    /**
     * Stop the timer.
     */
    public void stopTimer() {
        this.isStopped = true;
    }

    /**
     * Pause the timer.
     */
    public void pauseTimer() {
        isPaused = true;
    }

    /**
     * Resume the timer.
     */
    public void resumeTimer() {
        isPaused = false;
    }

    /**
     * Restart the timer.
     */
    public void restartTimer() {
        isRestartRequested = true;
    }

    /**
     * Register a new listener to get notified.
     * @param listener The new listener to register
     */
    public void addCountdownListener(ICountdownListener listener) {
        countdownListeners.add(listener);
    }

    /**
     * Remove a listener.
     * @param listener The listener which will be removed from the list of listeners
     */
    public boolean removeCountdownListener(ICountdownListener listener) {
        return countdownListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners.
     * @param event The fired countdown event
     * @param time The current countdown time
     */
    private void notifyListeners(CountdownEvent event, long time) {
        for(ICountdownListener listener : countdownListeners) {
            switch(event) {
                case STARTED:
                    listener.onCountdownStarted(time);
                    break;
                case TICKED:
                    listener.onCountdownTick(time);
                    break;
                case FINISHED:
                    listener.onCountdownFinished(time);
                    break;
            }
        }
    }
}
