package de.thb.paf.scrabblefactory.models.actions;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.DiscardEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;

/**
 * Cheese action dedicated to handle the discard a cheese item.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class DiscardCheeseAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public DiscardCheeseAction(RigidBodyPhysicsComponent parent) {
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
            Body body = this.parent.getBody();
            body.setActive(true);
            for(Fixture fixture : body.getFixtureList()) {
                fixture.setSensor(true);
            }
        }
    }
}
