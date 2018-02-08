package de.thb.paf.scrabblefactory.models.actions;


import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.graphics.SpriteAnimationGraphicsComponent;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.models.events.GroundContactEvent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

import static de.thb.paf.scrabblefactory.models.actions.MoveActionType.IDLE;
import static de.thb.paf.scrabblefactory.models.actions.MoveActionType.NONE;

/**
 * Represents a basic move action dedicated for a player's sprite animation graphics components.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class SpriteAnimationMovePlayerAction extends GameAction {

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
    public SpriteAnimationMovePlayerAction(SpriteAnimationGraphicsComponent parent) {
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

        String carryingSuffix = "";
        int carryingItemsCount = ((Player)this.parent.getParent()).getCheeseItems().size();
        if(carryingItemsCount > 0) {
            carryingSuffix = "_carrying";
        }

        switch(this.moveActionType) {
            case JUMP:
                this.parent.switchToAnimation("idle" + carryingSuffix);
                break;
            case JUMP_WALK:
                this.parent.switchToAnimation("walking" + carryingSuffix);
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
        int carryingItemsCount = ((Player)this.parent.getParent()).getCheeseItems().size();
        String carryingSuffix = "";

        if(carryingItemsCount > 0) {
            carryingSuffix = "_carrying";
        }

        switch(event.getMoveDirectionType()) {
            case LEFT:
                this.parent.setFlipped(true);
                break;
            case RIGHT:
                this.parent.setFlipped(false);
                break;
        }

        MoveActionType actionType;
        if(event.getMoveActionType() != NONE) {
            this.moveActionType  = event.getMoveActionType();
            actionType = this.moveActionType;
        } else {
            actionType = this.moveActionType;
        }

        switch(actionType) {
            case WALK:
                this.parent.setInfiniteLoop(true);
                this.parent.switchToAnimation("walking" + carryingSuffix);
                break;
            case JUMP:
            case JUMP_WALK:
                this.parent.setInfiniteLoop(false);
                this.parent.switchToAnimation("jumping" + carryingSuffix);
                break;
            case IDLE:
                this.parent.setInfiniteLoop(true);
                this.parent.switchToAnimation("idle" + carryingSuffix);
                break;
            default:
                // we ignore other move actions
                break;
        }
    }
}
