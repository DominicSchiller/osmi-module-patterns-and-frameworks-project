package de.thb.paf.scrabblefactory.models.actions;


import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.ItemContactEvent;

/**
 * Represents a basic action dedicated to deactivate Box2D bodies.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class RigidBodyDisableAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Default Constructor
     */
    public RigidBodyDisableAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void update(Observable observable, Object o) {
        IGameEvent event = ((IGameEvent) observable);

        switch(event.getEventType()) {
            case ITEM_CONTACT:
                this.handleItemContactEvent((ItemContactEvent)event);
                break;
            default:
                // we ignore other events
                break;
        }
    }

    /**
     * Handle a item contact event.
     * @param event The triggered item contact event
     * @see ItemContactEvent
     */
    private void handleItemContactEvent(ItemContactEvent event) {
        if(event.getItem() == this.parent.getParent()) {
            this.parent.getBody().setActive(false);
        }
    }
}
