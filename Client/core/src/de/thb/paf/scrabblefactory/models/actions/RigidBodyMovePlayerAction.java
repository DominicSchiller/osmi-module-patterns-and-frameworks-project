package de.thb.paf.scrabblefactory.models.actions;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Observable;

import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.events.GameEventType;
import de.thb.paf.scrabblefactory.models.events.GroundContactEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;
import de.thb.paf.scrabblefactory.models.events.MoveToEvent;
import de.thb.paf.scrabblefactory.settings.Settings;

import static de.thb.paf.scrabblefactory.models.actions.MoveActionType.*;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

/**
 * Represents a basic move action dedicated for a player's rigid body physics components.
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
     * Status if the Box2D body already has ground contact
     */
    private boolean hasGroundContact;

    /**
     * The currently active async move action (= custom runnable)
     *
     */
    AsyncMoveAction asyncMoveAction;

    /**
     * The currently running thread executing the currently active async move action
     */
    Thread asyncMoveTask;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public RigidBodyMovePlayerAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = parent;
        this.moveActionType = IDLE;
        this.isSwitchingBody = false;
        this.hasGroundContact = false;
    }

    @Override
    public void update(Observable observable, Object o) {
        IGameEvent event = ((IGameEvent)observable);

        switch(event.getEventType()) {
            case MOVE:
                this.handleMoveEvent((MoveEvent)event);
                break;
            case MOVE_TO:
                this.handleMoveToEvent((MoveToEvent)event);
                break;
            case GROUND_CONTACT:
                if(!this.hasGroundContact)
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

        this.hasGroundContact = true;
    }

    /**
     * Handle a move event.
     * @param event The triggered move event to handle
     * @see MoveEvent
     */
    private void handleMoveEvent(MoveEvent event) {
        boolean isMoveToEvent = event instanceof MoveToEvent;
        this.moveActionType = event.getMoveActionType();
        Vector2 currentPosition = this.parent.getBody().getPosition();
        Vector2 bodySize = this.parent.getParent().getSize();
        boolean canApplyImpulse = false;

        switch(event.getMoveDirectionType()) {
            case LEFT:
            case LEFT_UP:
            case LEFT_DOWN:
                canApplyImpulse = currentPosition.x - 0.1 > 0
                        && currentPosition.y + 0.1 < (Settings.Game.VIRTUAL_HEIGHT - bodySize.y);
                this.parent.setFlipped(true);
                break;
            case RIGHT:
            case RIGHT_UP:
            case RIGHT_DOWN:
                canApplyImpulse = currentPosition.x + 0.1 < Settings.Game.VIRTUAL_WIDTH - bodySize.x
                        && currentPosition.y + 0.1 < Settings.Game.VIRTUAL_HEIGHT - bodySize.y;
                this.parent.setFlipped(false);
                break;
        }

        Vector2 velocity = this.parent.getVelocity();
        Vector2 impulse = new Vector2();
        switch(event.getMoveActionType()) {
            case WALK:
                this.switchPhysicsBody("walking");
                impulse.x = velocity.x;
                impulse.y = 0;
                break;
            case JUMP:
                this.switchPhysicsBody("jumping");
                this.parent.getBody().setTransform(
                        this.parent.getBody().getTransform().getPosition().x,
                        this.parent.getBody().getTransform().getPosition().y + velocity.y,
                        0
                );
                impulse.x = 0;
                impulse.y = velocity.y;
                this.hasGroundContact = false;
                break;
            case JUMP_WALK:
                this.switchPhysicsBody("jumping");
                impulse.x = velocity.x;
                impulse.y = velocity.y;
                break;
            case IDLE:
                this.switchPhysicsBody("idle");
                this.resetVelocity();
                impulse.x = 0;
                impulse.y = 0;
                break;
        }

        if(!isMoveToEvent && canApplyImpulse) {
            if(this.resetAsyncMoveTask()) {
                this.triggerAsyncMoveTask(
                    this.createAsyncMoveAction(impulse, event)
                );
            }
        }
    }

    /**
     * Handle a move to event.
     * @param event The triggered move to event to handle
     * @see MoveToEvent
     */
    private void handleMoveToEvent(MoveToEvent event) {
        Vector2 currentPosition = this.parent.getParent().getPosition();
        event.setMoveDirectionType(
                currentPosition.x > event.getTargetPosition().x ?
                        MoveDirectionType.LEFT : MoveDirectionType.RIGHT
        );

        if(event.getMoveActionType() == WALK) {
            if(this.resetAsyncMoveTask()) {
                this.handleMoveEvent(event);
                this.triggerAsyncMoveTask(
                        this.createAsyncMoveToAction(event)
                );
            }
        } else if(event.getMoveActionType() == JUMP) {
            this.applyImpulse(0, this.parent.getVelocity().y, event.getMoveDirectionType());
        }
    }

    /**
     * Apply movement impulses to physical Box2D body.
     * @param xImpulse The movement impulse on x-axis
     * @param yImpulse The movement impulse on y-axis
     * @param moveDirection The move direction to apply together with the impulses
     */
    private void applyImpulse(float xImpulse, float yImpulse, MoveDirectionType moveDirection) {
        Body body = this.parent.getBody();
        body.applyLinearImpulse(
                new Vector2(
                        xImpulse * moveDirection.xSign,
                        yImpulse * moveDirection.ySign
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

            if(newBody == null) {
                return;
            }

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

    /**
     * Triggers a new async move task.
     * @param asyncMoveRunnable The async move action to execute
     */
    private void triggerAsyncMoveTask(AsyncMoveAction asyncMoveRunnable) {
        this.asyncMoveAction = asyncMoveRunnable;
        this.asyncMoveTask = new Thread(this.asyncMoveAction);
        this.asyncMoveTask.start();
    }

    /**
     * Stop and reset the currently active async move action.
     * @return Status if the current move task has been stopped successfully
     */
    private boolean resetAsyncMoveTask() {
        if(this.asyncMoveAction != null) {
            this.asyncMoveAction.terminate();
            try {
                this.asyncMoveTask.join();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a new async move action.
     * @param impulse The move impulse to apply
     * @param event The move event to apply
     * @return The new async move action
     */
    private AsyncMoveAction createAsyncMoveAction(Vector2 impulse, MoveEvent event) {
        return new AsyncMoveAction() {
            @Override
            public void run() {
                applyImpulse(impulse.x, impulse.y, event.getMoveDirectionType());
                Vector2 bodySize = parent.getParent().getSize();

                boolean willInterrupt = false;
                while(this.isRunning() && !willInterrupt) {
                    Vector2 currentPosition = parent.getBody().getPosition();
                    switch(event.getMoveDirectionType()) {
                        case LEFT:
                            willInterrupt = !(currentPosition.x - 0.1 > 0
                                    && currentPosition.y + 0.1 < (Settings.Game.VIRTUAL_HEIGHT - bodySize.y));
                            break;
                        case RIGHT:
                            willInterrupt = !(currentPosition.x + 0.1 < Settings.Game.VIRTUAL_WIDTH - bodySize.x
                                    && currentPosition.y + 0.1 < Settings.Game.VIRTUAL_HEIGHT - bodySize.y);
                            break;
                    }
                }
                resetVelocity();
                this.terminate();
            }
        };
    }

    /**
     * Creates a new async move-to action.
     * @param event The move-to event to apply
     * @return The new async move action
     */
    private AsyncMoveAction createAsyncMoveToAction(MoveToEvent event) {
        return new AsyncMoveAction() {
            @Override
            public void run() {
                resetVelocity();
                applyImpulse(parent.getVelocity().x, 0, event.getMoveDirectionType());

                Vector2 bodySize = parent.getParent().getSize();
                Body body = parent.getBody();
                boolean isTargetPositionReached = false;
                while(this.isRunning() && !isTargetPositionReached) {
                    switch(event.getMoveDirectionType()) {
                        case LEFT:
                            isTargetPositionReached =
                                    (body.getTransform().getPosition().x - bodySize.x/2) <
                                            event.getTargetPosition().x;
                            break;
                        case RIGHT:
                            isTargetPositionReached =
                                    (body.getTransform().getPosition().x + bodySize.x/2) >
                                            event.getTargetPosition().x;
                            break;
                    }
                }

                GameEventManager gem = GameEventManager.getInstance();
                MoveEvent moveEvent = (MoveEvent) gem.getGameEvent(GameEventType.MOVE);
                moveEvent.setMoveDirectionType(MoveDirectionType.NONE);
                moveEvent.setMoveActionType(MoveActionType.IDLE);
                gem.triggerEvent(GameEventType.MOVE);

                this.terminate();
                resetVelocity();
            }
        };
    }
}

/**
 * Represents a custom runnable for executing move actions asynchronously.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */
class AsyncMoveAction implements Runnable {
    /**
     * Status if the runnable is still active
     */
    private volatile boolean isRunning = true;

    /**
     * Terminate the runnable.
     * (Set the running status to false)
     */
    public void terminate() {
        this.isRunning = false;
    }

    /**
     * Get the status if the runnable is still active.
     * @return The status indicating if the runnable is still active.
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void run() {

    }
}
