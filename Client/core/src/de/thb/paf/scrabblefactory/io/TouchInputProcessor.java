package de.thb.paf.scrabblefactory.io;


import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.actions.MoveActionType;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.models.events.DiscardEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;
import de.thb.paf.scrabblefactory.models.events.MoveToEvent;
import de.thb.paf.scrabblefactory.settings.Settings;

import static de.thb.paf.scrabblefactory.models.actions.MoveActionType.JUMP;
import static de.thb.paf.scrabblefactory.models.entities.EntityType.PLAYER;
import static de.thb.paf.scrabblefactory.models.events.GameEventType.DISCARD;
import static de.thb.paf.scrabblefactory.models.events.GameEventType.MOVE;
import static de.thb.paf.scrabblefactory.models.events.GameEventType.MOVE_TO;

/**
 * Represents a input processor dedicated to handle touch gesture inputs.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class TouchInputProcessor implements GestureDetector.GestureListener {

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        this.triggerMoveToEvent(
            this.calculateWorldCoordinates(x, y),
            MoveActionType.WALK
        );
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(velocityY < 0) {
            this.triggerMoveEvent(JUMP);
        } else {
            List<IGameObject> gameObjects = GameObjectManager.getInstance().getGameObject(PLAYER);
            Player player = (Player)gameObjects.get(0);
            if(player.getCheeseItems().size() > 0) {
                DiscardEvent discardEvent = (DiscardEvent)GameEventManager.getInstance().getGameEvent(DISCARD);
                discardEvent.setDiscardTarget(player);
                GameEventManager.getInstance().triggerEvent(DISCARD);
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    /**
     * Trigger a move event.
     * @param moveActionType The move action type to set with event
     */
    private void triggerMoveEvent(MoveActionType moveActionType) {
        GameEventManager gem = GameEventManager.getInstance();
        MoveEvent event = (MoveEvent) gem.getGameEvent(MOVE);
        event.setMoveActionType(moveActionType);

        gem.triggerEvent(MOVE);
    }

    /**
     * Trigger a move-to event.
     * @param targetPosition The event's target position to apply
     * @param moveActionType The move action type to set with event
     */
    private void triggerMoveToEvent(Vector2 targetPosition, MoveActionType moveActionType) {
        GameEventManager gem = GameEventManager.getInstance();
        MoveToEvent event = (MoveToEvent) gem.getGameEvent(MOVE_TO);
        event.setTargetPosition(targetPosition);
        event.setMoveActionType(moveActionType);

        gem.triggerEvent(MOVE_TO);
    }

    /**
     * Translate the received screen coordinates to the game world's virtual coordinates.
     * @param screenX The touch event's x-coordinate to translate
     * @param screenY The touch event's y-coordinate to translate
     * @return The translated coordinates
     */
    private Vector2 calculateWorldCoordinates(float screenX, float screenY) {
        return new Vector2(
                (Settings.Game.VIRTUAL_WIDTH * screenX) / Settings.App.DEVICE_SCREEN_WIDTH,
                (Settings.Game.VIRTUAL_HEIGHT* screenY) / Settings.App.DEVICE_SCREEN_HEIGHT
        );
    }
}
