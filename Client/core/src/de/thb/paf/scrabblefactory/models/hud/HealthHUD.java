package de.thb.paf.scrabblefactory.models.hud;

import java.util.Observable;

import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.PlayerHealthChangedEvent;

/**
 * Represents a HUD container dedicated to display a player's health.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class HealthHUD extends HUDComponent {

    /**
     * The current health
     */
    private int health;

    /**
     * Constructor
     */
    public HealthHUD() {
        super();
    }

    /**
     * Get the currently displayed health.
     * @return the displayed health
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Set the health to display.
     * @param health The updated health to display
     */
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void update(Observable observable, Object arg) {
        IGameEvent event = ((IGameEvent) observable);

        switch(event.getEventType()) {
            case PLAYER_HEALTH_CHANGED:
                this.setHealth(((PlayerHealthChangedEvent)event).getHealth());
                break;
        }
    }
}
