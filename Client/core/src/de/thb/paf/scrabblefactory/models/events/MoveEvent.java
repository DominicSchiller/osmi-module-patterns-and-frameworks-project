package de.thb.paf.scrabblefactory.models.events;

import java.util.Observer;

import de.thb.paf.scrabblefactory.models.actions.MoveActionType;
import de.thb.paf.scrabblefactory.models.actions.MoveDirectionType;

/**
 * Move event dedicated to submit a game object's movement changes.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class MoveEvent extends GameEvent {

    /**
     * The current move action type
     */
    private MoveActionType moveActionType;

    /**
     * The current move direction type
     */
    private MoveDirectionType moveDirectionType;

    /**
     * Default Constructor
     */
    public MoveEvent() {
        super(GameEventType.MOVE);
        this.moveDirectionType = MoveDirectionType.RIGHT;
        this.moveActionType = MoveActionType.IDLE;
    }

    /**
     * Constructor
     * @param observers List of observers to register
     */
    public MoveEvent(Observer... observers) {
        super(GameEventType.MOVE);

        for(Observer observer : observers) {
            this.addListener(observer);
        }
    }

    @Override
    public void invoke() {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Get the current move action type.
     * @return The current move action type
     */
    public MoveActionType getMoveActionType() {
        return this.moveActionType;
    }

    /**
     * Get the current move direction type.
     * @return The current move direction type
     */
    public MoveDirectionType getMoveDirectionType() {
        return this.moveDirectionType;
    }

    /**
     * Set the current move direction type.
     * @param moveDirectionType The updated move direction
     */
    public void setMoveDirectionType(MoveDirectionType moveDirectionType) {
        this.moveDirectionType = moveDirectionType;
    }

    /**
     * Set the current move action type.
     * @param moveActionType The updated move action
     */
    public void setMoveActionType(MoveActionType moveActionType) {
        this.moveActionType = moveActionType;
    }
}
