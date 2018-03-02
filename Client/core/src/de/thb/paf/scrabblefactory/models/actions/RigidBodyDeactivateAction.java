package de.thb.paf.scrabblefactory.models.actions;

import com.badlogic.gdx.physics.box2d.Body;

import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.ItemContactEvent;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;

/**
 * Represents a basic action dedicated to deactivate a rigid Box2D bodies.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class RigidBodyDeactivateAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public RigidBodyDeactivateAction(RigidBodyPhysicsComponent parent) {
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
            Body body = this.parent.getBody();
            body.setType(DynamicBody);
            body.setActive(false);
        }
    }
}
