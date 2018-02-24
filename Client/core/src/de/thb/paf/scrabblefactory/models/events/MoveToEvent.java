package de.thb.paf.scrabblefactory.models.events;


import com.badlogic.gdx.math.Vector2;

/**
 * Special move event dedicated to submit a game object's
 * movement change with a defined target position.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class MoveToEvent extends MoveEvent {

    /**
     * The target position to move to
     */
    private Vector2 targetPosition;

    /**
     * Constructor.
     */
    public MoveToEvent() {
        super(GameEventType.MOVE_TO);
        this.targetPosition = new Vector2();
        this.resetTargetPosition();
    }

    /**
     * Get the target position.
     * @return The target position
     */
    public Vector2 getTargetPosition() {
        return this.targetPosition;
    }

    /**
     * Set the target position.
     * @param worldX The position's x-coordinate
     * @param worldY The position's y-coordinate
     */
    public void setTargetPosition(float worldX, float worldY) {
        this.targetPosition.x = worldX;
        this.targetPosition.y = worldY;
    }

    /**
     * Set the target position.
     * @param targetPosition The target position vector
     */
    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition.x = targetPosition.x;
        this.targetPosition.y = targetPosition.y;
    }

    /**
     * Reset the associated target position.
     */
    void resetTargetPosition() {
        this.targetPosition.x = -1;
        this.targetPosition.y = -1;
    }

}
