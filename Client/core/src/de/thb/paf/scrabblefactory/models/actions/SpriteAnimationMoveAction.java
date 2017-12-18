package de.thb.paf.scrabblefactory.models.actions;


import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.graphics.SpriteAnimationGraphicsComponent;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

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
     * Constructor
     * @param parent The sprite animation graphics component to control
     */
    public SpriteAnimationMoveAction(SpriteAnimationGraphicsComponent parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void update(Observable observable, Object o) {
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
     * @param event The move event to handle
     */
    private void handleMoveEvent(MoveEvent event) {
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
