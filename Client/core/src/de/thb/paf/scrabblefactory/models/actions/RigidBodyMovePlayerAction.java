package de.thb.paf.scrabblefactory.models.actions;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.GroundContactEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

import static de.thb.paf.scrabblefactory.models.actions.MoveActionType.IDLE;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

/**
 * Represents a basic move action dedicated for player's rigid body physics components.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class RigidBodyMovePlayerAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * The current move action type.
     */
    private MoveActionType moveActionType;

    /**
     * Semaphore access flag for securely switching the Box2D body
     */
    private boolean isSwitchingBody;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public RigidBodyMovePlayerAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = parent;
        this.moveActionType = IDLE;
        this.isSwitchingBody = false;
    }

    @Override
    public void update(Observable observable, Object o) {
        IGameEvent event = ((IGameEvent)observable);

        switch(event.getEventType()) {
            case MOVE:
                this.handleMoveEvent((MoveEvent)event);
                break;
            case GROUND_CONTACT:
                this.handleGroundContactEvent((GroundContactEvent)event);
                break;
            default:
                // do nothing here...
                break;
        }
    }

    /**
     * Handle a ground contact event.
     * @param event The triggered ground contact event to handle
     * @see GroundContactEvent
     */
    private void handleGroundContactEvent(GroundContactEvent event) {
        // if the event's contact is not equal the associated game object we will ignore this event,
        // because this event is not dedicated to us.
        if(this.parent.getParent() != event.getContact()) {
            return;
        }

        switch(this.moveActionType) {
            case JUMP:
                this.switchPhysicsBody("idle");
                break;
            case JUMP_WALK:
                this.switchPhysicsBody("walking");
                break;
            default:
                // we ignore other move actions
                break;
        }
    }

    /**
     * Handle a move event.
     * @param event The triggered move event to handle
     * @see MoveEvent
     */
    private void handleMoveEvent(MoveEvent event) {
        this.moveActionType = event.getMoveActionType();

        switch(event.getMoveDirectionType()) {
            case LEFT:
            case LEFT_UP:
            case LEFT_DOWN:
                this.parent.setFlipped(true);
                break;
            case RIGHT:
            case RIGHT_UP:
            case RIGHT_DOWN:
                this.parent.setFlipped(false);
                break;
        }

        Vector2 velocity = this.parent.getVelocity();
        switch(event.getMoveActionType()) {
            case WALK:
                this.switchPhysicsBody("walking");
                this.applyImpulse(velocity.x, 0, event.getMoveDirectionType().xSign, event.getMoveDirectionType().ySign);
                break;
            case JUMP:
                this.switchPhysicsBody("jumping");
                this.applyImpulse(0, velocity.y, event.getMoveDirectionType().xSign, event.getMoveDirectionType().ySign);
                break;
            case JUMP_WALK:
                this.switchPhysicsBody("jumping");
                this.applyImpulse(velocity.x, velocity.y, event.getMoveDirectionType().xSign, event.getMoveDirectionType().ySign);
                break;
            case IDLE:
                this.switchPhysicsBody("idle");
                this.resetVelocity();
                break;
        }
    }

    /**
     * Apply movement impulses to physical Box2D body.
     * @param xImpulse The movement impulse on x-axis
     * @param yImpulse The movement impulse on y-axis
     * @param xSign The movement direction sign on x-axis
     * @param ySign The movement direction sign on y-axis
     */
    private void applyImpulse(float xImpulse, float yImpulse, int xSign, int ySign) {
        Body body = this.parent.getBody();
        body.applyLinearImpulse(
                new Vector2(
                        xImpulse * xSign,
                        yImpulse * ySign
                ),
                body.getWorldCenter(),
                true
        );
    }

    /**
     * Reset all the Bodx2D body's velocity and movement impulses.
     */
    private void resetVelocity() {
        this.parent.getBody().setLinearVelocity(new Vector2(0, 0));
    }

    /**
     * Switches the Box2D body as a function of it's given key and flipped status.
     * @param bodyKey The body's key to load the body's Box2D shape definition from the physicsShapeCache
     */
    private void switchPhysicsBody(String bodyKey) {
        Body currentBody = this.parent.getBody();
        World world = currentBody.getWorld();

        if(!world.isLocked() && !this.isSwitchingBody) {
            this.isSwitchingBody = true;
            String flippedSuffixKey = this.parent.isFlipped() ? "_flipped" : "";
            float scale = VIRTUAL_SCALE * RESOLUTION.virtualScaleFactor;


            Body newBody = this.parent.getPhysicsShapeCache().createBody(
                    bodyKey + flippedSuffixKey,
                    world,
                    scale, scale
            );

            // set fixtures user data
            for(Fixture f : newBody.getFixtureList()) {
                f.setUserData(this.parent);
            }

            Vector2 position = currentBody.getPosition();
            newBody.setTransform(position.x, position.y, 0);

            currentBody.setActive(false);
            world.destroyBody(currentBody);
            this.parent.setBody(newBody);
            
            this.isSwitchingBody = false;
        }
    }
}
