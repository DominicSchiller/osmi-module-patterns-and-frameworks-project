package de.thb.paf.scrabblefactory.models.actions;


import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.graphics.SpriteAnimationGraphicsComponent;
import de.thb.paf.scrabblefactory.models.events.GroundContactEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

import static de.thb.paf.scrabblefactory.models.actions.MoveActionType.IDLE;

/**
 * Represents a basic move action dedicated for sprite animation graphics components.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class SpriteAnimationMoveAction extends GameAction {

    /**
     * The sprite animation graphics component to control
     */
    private SpriteAnimationGraphicsComponent parent;

    /**
     * The current move action type
     */
    private MoveActionType moveActionType;

    /**
     * Constructor
     * @param parent The sprite animation graphics component to control
     */
    public SpriteAnimationMoveAction(SpriteAnimationGraphicsComponent parent) {
        super();
        this.parent = parent;
        this.moveActionType = IDLE;
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
                this.parent.switchToAnimation("idle");
                break;
            case JUMP_WALK:
                this.parent.switchToAnimation("walking");
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
                this.parent.setFlipped(true);
                break;
            case RIGHT:
                this.parent.setFlipped(false);
                break;
        }

        switch(event.getMoveActionType()) {
            case WALK:
                this.parent.setInfiniteLoop(true);
                this.parent.switchToAnimation("walking");
                break;
            case JUMP:
            case JUMP_WALK:
                this.parent.setInfiniteLoop(false);
                this.parent.switchToAnimation("jumping");
                break;
            default:
                this.parent.setInfiniteLoop(true);
                this.parent.switchToAnimation("idle");
                break;
        }
    }
}
