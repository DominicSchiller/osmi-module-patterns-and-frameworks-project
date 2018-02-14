package de.thb.paf.scrabblefactory.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.List;

import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.models.events.DiscardEvent;
import de.thb.paf.scrabblefactory.models.actions.MoveActionType;
import de.thb.paf.scrabblefactory.models.actions.MoveDirectionType;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

import static com.badlogic.gdx.Input.Keys.*;
import static de.thb.paf.scrabblefactory.models.entities.EntityType.PLAYER;
import static de.thb.paf.scrabblefactory.models.events.GameEventType.DISCARD;
import static de.thb.paf.scrabblefactory.models.events.GameEventType.MOVE;


/**
 * Represents the global input processor dedicated to handle
 * all the user's touch or keyboard input.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class UserInputProcessor implements InputProcessor {

    /**
     * Default Constructor
     */
    public UserInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case LEFT:
            case RIGHT:
            case UP:
            case SPACE:
            case DOWN:
                MoveEvent moveEvent = (MoveEvent)GameEventManager.getInstance().getGameEvent(MOVE);
                if(moveEvent != null) {
                    this.applyMoveAction(keycode, moveEvent, this.applyMoveDirection(keycode, moveEvent));
                    GameEventManager.getInstance().triggerEvent(MOVE);
                }
                break;
            case X:
                //TODO: handle the player entity
                List<IGameObject> gameObjects = GameObjectManager.getInstance().getGameObject(PLAYER);
                Player player = (Player)gameObjects.get(0);
                if(player.getCheeseItems().size() > 0) {
                    DiscardEvent discardEvent = (DiscardEvent)GameEventManager.getInstance().getGameEvent(DISCARD);
                    discardEvent.setDiscardTarget(player);
                    GameEventManager.getInstance().triggerEvent(DISCARD);
                }
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(LEFT)) {
            return false;
        }

        switch(keycode) {
            case LEFT:
            case RIGHT:
                MoveEvent event = (MoveEvent)GameEventManager.getInstance().getGameEvent(MOVE);
                if(event != null) {
                    event.setMoveActionType(MoveActionType.IDLE);
                    GameEventManager.getInstance().triggerEvent(MOVE);
                }
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    /**
     * Determine and apply move direction for given pressed key code and current move event.
     * @param keyCode The key code to verify for additional pressed keys
     * @return The status whether there are multiple movement keys pressed or not
     */
    private boolean applyMoveDirection(int keyCode, MoveEvent event) {
        boolean isisMultiDirection = false;

        switch(keyCode) {
            case LEFT:
                if(Gdx.input.isKeyPressed(UP)) {
                    event.setMoveDirectionType(MoveDirectionType.LEFT_UP);
                    isisMultiDirection = true;
                } else if(Gdx.input.isKeyPressed(DOWN)) {
                    event.setMoveDirectionType(MoveDirectionType.LEFT_DOWN);
                    isisMultiDirection = true;
                } else {
                    event.setMoveDirectionType(MoveDirectionType.LEFT);
                }
                break;
            case RIGHT:
                if(Gdx.input.isKeyPressed(UP)) {
                    event.setMoveDirectionType(MoveDirectionType.RIGHT_UP);
                    isisMultiDirection = true;
                } else if(Gdx.input.isKeyPressed(DOWN)) {
                    event.setMoveDirectionType(MoveDirectionType.RIGHT_DOWN);
                    isisMultiDirection = true;
                } else {
                    event.setMoveDirectionType(MoveDirectionType.RIGHT);
                }
                break;
            case UP:
            case SPACE:
                if(Gdx.input.isKeyPressed(LEFT)) {
                    event.setMoveDirectionType(MoveDirectionType.LEFT_UP);
                    isisMultiDirection = true;
                } else if(Gdx.input.isKeyPressed(RIGHT)) {
                    event.setMoveDirectionType(MoveDirectionType.RIGHT_UP);
                    isisMultiDirection = true;
                } else {
                    event.setMoveDirectionType(MoveDirectionType.UP);
                }
                break;
            case DOWN:
                if(Gdx.input.isKeyPressed(LEFT)) {
                    event.setMoveDirectionType(MoveDirectionType.LEFT_DOWN);
                    isisMultiDirection = true;
                } else if(Gdx.input.isKeyPressed(RIGHT)) {
                    event.setMoveDirectionType(MoveDirectionType.RIGHT_DOWN);
                    isisMultiDirection = true;
                } else {
                    event.setMoveDirectionType(MoveDirectionType.DOWN);
                }
                break;
        }

        return isisMultiDirection;
    }

    /**
     * Determine and apply the move action based on currently pressed key and move event.
     * @param keyCode The key code of pressed key
     * @param event The current move event
     * @param isMultipleKeysPressed The status whether there are multiple movement keys pressed or not
     */
    private void applyMoveAction(int keyCode, MoveEvent event, boolean isMultipleKeysPressed) {
        if(isMultipleKeysPressed) {
            event.setMoveActionType(MoveActionType.JUMP_WALK);
        } else {
            switch(keyCode) {
                case LEFT:
                case RIGHT:
                    event.setMoveActionType(MoveActionType.WALK);
                    break;
                case UP:
                case SPACE:
                case DOWN:
                    event.setMoveActionType(MoveActionType.JUMP);
                    break;
            }
        }
    }
}
