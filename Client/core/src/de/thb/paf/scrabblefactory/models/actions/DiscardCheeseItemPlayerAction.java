package de.thb.paf.scrabblefactory.models.actions;


import com.badlogic.gdx.Gdx;

import java.util.Observable;

import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.entities.Cheese;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.models.events.DiscardEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;

import static de.thb.paf.scrabblefactory.models.events.GameEventType.DISCARD;

/**
 * Player action dedicated to handle the discard of cheese items.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class DiscardCheeseItemPlayerAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public DiscardCheeseItemPlayerAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void update(Observable observable, Object o) {
        IGameEvent event = ((IGameEvent) observable);

        switch(event.getEventType()) {
            case DISCARD:
                this.handleDiscardEvent((DiscardEvent)event);
                break;
            default:
                // we ignore other events
                break;
        }
    }

    /**
     * Handle a received discard event.
     * @param event The triggered discard event to process
     */
    private void handleDiscardEvent(DiscardEvent event) {
        if(event.getDiscardTarget() == this.parent.getParent()) {
            Player player = ((Player)this.parent.getParent());
            Cheese cheese = player.getCheeseItems().get(player.getCheeseItems().size() - 1);
            this.triggerDiscardEventAsync(cheese);
            player.removeCheeseItem(cheese);
        }
    }

    /**
     * Asynchronously trigger a discard event.
     * @param target The event's target
     */
    private void triggerDiscardEventAsync(Cheese target) {
        new Thread(() -> Gdx.app.postRunnable(() -> {
            DiscardEvent event = (DiscardEvent) GameEventManager.getInstance().getGameEvent(DISCARD);
            event.setDiscardTarget(target);
            GameEventManager.getInstance().triggerEvent(DISCARD);
        })).start();
    }
}
