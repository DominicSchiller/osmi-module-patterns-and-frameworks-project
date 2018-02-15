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
        MILLISECONDS = milliseconds;
        isPaused = false;
        countdownListeners = new ArrayList<ICountdownListener>();
    }

    @Override
    public void run() {
        super.run();
        long passedMilliseconds = 0;

        notifyListeners(CountdownEvent.STARTED, (MILLISECONDS - passedMilliseconds));
        while(passedMilliseconds < MILLISECONDS && !isRestartRequested) {
            if(!isPaused) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                    passedMilliseconds += SLEEP_INTERVAL;
                    notifyListeners(CountdownEvent.TICKED, (MILLISECONDS - passedMilliseconds));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        notifyListeners(CountdownEvent.FINISHED, (MILLISECONDS - passedMilliseconds));

        // restart our self if requested
        if(isRestartRequested) {
            isRestartRequested = false;
            this.start();
        }
    }

    /**
     * Pauses the timer
     */
    public void pauseTimer() {
        isPaused = true;
    }

    /**
     * Resumes the timer
     */
    public void resumeTimer() {
        isPaused = false;
    }

    public void restart() {
        isRestartRequested = true;
    }

    /**
     * Registers a new listener to get notified.
     * @param listener The new listener to register
     */
    public void registerCountdownListener(ICountdownListener listener) {
        countdownListeners.add(listener);
    }

    /**
     * Removes a listener.
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
