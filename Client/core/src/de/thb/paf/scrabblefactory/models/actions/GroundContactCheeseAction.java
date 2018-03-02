package de.thb.paf.scrabblefactory.models.actions;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.GroundContactEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

/**
 * Cheese action dedicated to handle collision contact with the game world's ground.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GroundContactCheeseAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public GroundContactCheeseAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void update(Observable observable, Object o) {
        IGameEvent event = ((IGameEvent) observable);

        switch(event.getEventType()) {
            case GROUND_CONTACT:
                this.handleGroundContactEvent((GroundContactEvent)event);
                break;
            default:
                // we ignore other events
                break;
        }
    }

    /**
     * Handle a received ground contact event.
     * @param event The received ground contact event to handle.
     */
    private void handleGroundContactEvent(GroundContactEvent event) {
        if(event.getContact() == this.parent.getParent()) {
            Body body = this.parent.getBody();
            body.setType(BodyDef.BodyType.KinematicBody);
            body.setFixedRotation(true);
            body.setLinearVelocity(0, 0);
            body.applyForceToCenter(0,0, true);
            body.applyLinearImpulse(0, 0, 0, 0, true);
            body.setTransform(body.getTransform().getPosition().x, ((float)90 * VIRTUAL_SCALE) , 0);
        }
    }
}
