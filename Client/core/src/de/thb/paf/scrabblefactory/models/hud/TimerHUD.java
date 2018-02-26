package de.thb.paf.scrabblefactory.models.hud;


import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.components.graphics.FontGraphicsComponent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.RemainingTimeUpdateEvent;

/**
 * Represents a HUD container dedicated to display a level's remaining time.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class TimerHUD extends HUDComponent {

    /**
     * The time to display in milliseconds
     */
    private long time;

    /**
     * Constructor
     */
    public TimerHUD() {
        super();
    }

    /**
     * Get the displayed time in milliseconds.
     * @return the displayed time
     */
    public long getTime() {
        return this.time;
    }

    /**
     * Set the displayed time.
     * @param time The displayed time in milliseconds
     */
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public void update(Observable observable, Object arg) {
        IGameEvent event = ((IGameEvent) observable);

        switch(event.getEventType()) {
            case REMAINING_TIME_UPDATE:
                this.time = ((RemainingTimeUpdateEvent)event).getTime();
                this.updateFontComponents();
                break;
        }
    }

    /**
     * Update the associated font graphics components for displaying the current remaining
     * minutes and seconds.
     */
    private void updateFontComponents() {
        short totalTime = (short)(this.time / 1000);
        String minutes = String.format("%02d", totalTime / 60);
        String seconds = String.format("%02d", totalTime % 60);

        for(IComponent component : this.getAllComponents(ComponentType.GFX_COMPONENT)) {
            if(component instanceof FontGraphicsComponent) {
                FontGraphicsComponent fontGraphicsComponent = (FontGraphicsComponent)component;
                if(fontGraphicsComponent.alignment == Alignment.CENTER_LEFT) {
                    fontGraphicsComponent.text = minutes;
                } else {
                    fontGraphicsComponent.text = seconds;
                }
            }
        }
    }
}
