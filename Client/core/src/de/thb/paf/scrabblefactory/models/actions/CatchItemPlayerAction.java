package de.thb.paf.scrabblefactory.models.actions;


import com.badlogic.gdx.Gdx;

import java.util.Observable;

import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.entities.Cheese;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.models.events.ItemContactEvent;
import de.thb.paf.scrabblefactory.models.events.MoveEvent;

import static de.thb.paf.scrabblefactory.models.events.GameEventType.MOVE;

/**
 * Represents an action dedicated to handle a player's contact with an game item.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class CatchItemPlayerAction extends GameAction {

    /**
     * The rigid body physics component to control
     */
    private RigidBodyPhysicsComponent parent;

    /**
     * Constructor
     * @param parent The associated rigid body physics component
     */
    public CatchItemPlayerAction(RigidBodyPhysicsComponent parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void update(Observable observable, Object o) {
        IGameEvent event = ((IGameEvent) observable);

        switch(event.getEventType()) {
            case ITEM_CONTACT:
                this.handleItemContactEvent((ItemContactEvent)event);
                break;
            default:
                // we ignore other events
                break;
        }
    }

    /**
     * Handle a item contact event.
     * @param event The triggered item contact event
     * @see ItemContactEvent
     */
    private void handleItemContactEvent(ItemContactEvent event) {
        if(event.getContact() instanceof Player) {
            if(event.getItem() instanceof Cheese) {
                this.handleCheeseItem((Cheese)event.getItem());
            }
        }
    }

    /**
     * Handle the contact with an cheese item.
     * @param cheese The cheese item to process
     */
    private void handleCheeseItem(Cheese cheese) {
        Player player = (Player)this.parent.getParent();
        cheese.setCaught(true);
        player.addCheeseItem(cheese);
        this.triggerMoveEventAsync();
    }

    /**
     * Asynchronously trigger a non-defined move event.
     */
    private void triggerMoveEventAsync() {
        new Thread(() -> Gdx.app.postRunnable(() -> {
                MoveEvent event = (MoveEvent) GameEventManager.getInstance().getGameEvent(MOVE);
                event.setMoveActionType(MoveActionType.NONE);
                event.setMoveDirectionType(MoveDirectionType.NONE);
                GameEventManager.getInstance().triggerEvent(MOVE);
            })
        ).start();
    }
}
