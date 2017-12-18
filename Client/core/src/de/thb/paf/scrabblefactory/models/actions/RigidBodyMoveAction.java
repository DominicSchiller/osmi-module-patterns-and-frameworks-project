package de.thb.paf.scrabblefactory.models.actions;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

/**
 * Represents a basic move action dedicated for rigid body physics components.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class RigidBodyMoveAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public RigidBodyMoveAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = (RigidBodyPhysicsComponent)parent;
    }

    @Override
    public void update(Observable observable, Object o) {
        this.resetVelocity();
        IGameEvent event = ((IGameEvent)observable);

        switch(event.getEventType()) {
            case MOVE:
                this.handleMoveEvent((MoveEvent) event);
                break;
            default:
                // do nothing here...
                break;
        }
    }

    /**
     * Handle the move event.
     * @param event The current move event to handle
     */
    private void handleMoveEvent(MoveEvent event) {
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
    public void applyImpulse(float xImpulse, float yImpulse, int xSign, int ySign) {
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
    public void resetVelocity() {
        this.parent.getBody().setLinearVelocity(new Vector2(0, 0));
    }

    /**
     * Switches the Box2D body as a function of it's given key and flipped status.
     * @param bodyKey The body's key to load the body's Box2D shape definition from the physicsShapeCache
     */
    public void switchPhysicsBody(String bodyKey) {
        Body currentBody = this.parent.getBody();
        String flippedSuffixKey = this.parent.isFlipped() ? "_flipped" : "";
        float scale = VIRTUAL_SCALE * RESOLUTION.virtualScaleFactor;
        World world = currentBody.getWorld();

        Body newBody = this.parent.getPhysicsShapeCache().createBody(
                bodyKey + flippedSuffixKey,
                world,
                scale, scale
        );

        Vector2 position = currentBody.getPosition();
        newBody.setTransform(position.x, position.y, 0);

        world.destroyBody(currentBody);
        this.parent.setBody(newBody);
    }
}
