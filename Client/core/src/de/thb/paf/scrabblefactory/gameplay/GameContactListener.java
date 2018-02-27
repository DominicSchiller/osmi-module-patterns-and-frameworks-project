package de.thb.paf.scrabblefactory.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.managers.WorldPhysicsManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.entities.Cheese;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.models.events.GroundContactEvent;
import de.thb.paf.scrabblefactory.models.events.ItemContactEvent;
import de.thb.paf.scrabblefactory.models.level.ILevel;

import static de.thb.paf.scrabblefactory.models.events.GameEventType.GROUND_CONTACT;
import static de.thb.paf.scrabblefactory.models.events.GameEventType.ITEM_CONTACT;


/**
 * Basic game contact listener dedicated to handle collisions between game objects.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        // if one of the collision objects does not have a fixture we cancel here
        if(fA == null || fB == null)
            return;

        // if one of the collision objects does not have any user data attached
        // to further define the collision object we cancel here also
        if(fA.getUserData() == null || fB.getUserData() == null)
            return;

        IGameObject goA = ((IComponent) fA.getUserData()).getParent();
        IGameObject goB = ((IComponent) fB.getUserData()).getParent();

        if(this.isGroundContact(goA, goB)) {
            this.triggerGroundContactEventAsync(goB);
        }
        else if(this.isItemContact(goA, goB) || this.isItemContact(goB, goA)) {
            this.triggerItemContactEventAsync(
                    (goA instanceof Player) ? goA : goB,
                    !(goB instanceof Player) ? goB : goA
            );
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        // if one of the collision objects does not have a fixture we cancel here
        if(fA == null || fB == null)
            return;

        // if one of the collision objects does not have any user data attached
        // to further define the collision object we cancel here also
        if(fA.getUserData() == null || fB.getUserData() == null)
            return;

        IGameObject goA = ((IComponent) fA.getUserData()).getParent();
        IGameObject goB = ((IComponent) fB.getUserData()).getParent();

        boolean isCheeseSensor =
                (goA instanceof Cheese && fA.isSensor()) || (goB instanceof Cheese && fB.isSensor());
        System.out.println("is cheese: " + isCheeseSensor);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     * Try to carry up caught and dismissed cheese item
     */
    public void tryToCarryUpCheeseItem() {
        World w = WorldPhysicsManager.getInstance().getPhysicalWorld();
        for(Contact contact : w.getContactList()) {
            Fixture fA = contact.getFixtureA();
            Fixture fB = contact.getFixtureB();

            // if one of the collision objects does not have a fixture we cancel here
            if(fA == null || fB == null)
                continue;

            // if one of the collision objects does not have any user data attached
            // to further define the collision object we cancel here also
            if(fA.getUserData() == null || fB.getUserData() == null)
                continue;

            IGameObject goA = ((IComponent) fA.getUserData()).getParent();
            IGameObject goB = ((IComponent) fB.getUserData()).getParent();

            boolean isPlayerAndCheese = (fA.isSensor() || fB.isSensor())
                    && (goA instanceof Player || goA instanceof Cheese)
                    && (goB instanceof Player || goB instanceof Cheese);

            if(isPlayerAndCheese) {
                Cheese cheese = goA instanceof Cheese ? (Cheese)goA : (Cheese)goB;
                Player player = goA instanceof Player ? (Player)goA : (Player)goB;

                this.triggerItemContactEventAsync(player, cheese);
                break;
            }
        }
    }

    /**
     * Verify if the collided object hit the game world's ground.
     * @param goA The first game object involved in the collision
     * @param goB The second game object involved in the collision
     * @return Status if ground contact happened
     */
    private boolean isGroundContact(IGameObject goA, IGameObject goB) {
        return (goA instanceof ILevel && goB instanceof Player)
                || (goA instanceof ILevel && goB instanceof Cheese);
    }

    /**
     * Verify if a player collided with an game item.
     * @param goA The first game object involved in the collision
     * @param goB The second game object involved in the collision
     * @return Status if ground contact happened
     */
    private boolean isItemContact(IGameObject goA, IGameObject goB) {
         boolean isItemContact = false;

        if(goA instanceof Player) {
            if(goB instanceof Cheese) {
                 if(!((Cheese)goB).isCaught()) {
                     isItemContact = true;
                 } else {

                 }
            }
        }

        return isItemContact;
    }

    /**
     * Asynchronously trigger a ground contact event.
     * @param contact The game object which hit the ground
     */
    private void triggerGroundContactEventAsync(IGameObject contact) {
        new Thread(() -> Gdx.app.postRunnable(() -> {
                ((GroundContactEvent) GameEventManager.getInstance()
                        .getGameEvent(GROUND_CONTACT)).setContact(contact);
                GameEventManager.getInstance().triggerEvent(GROUND_CONTACT);
            })
        ).start();
    }

    /**
     * Asynchronously trigger a item contact event.
     * @param contact The game object which hit the item
     * @param item The item which got hit by the contact
     */
    private void triggerItemContactEventAsync(IGameObject contact, IGameObject item) {
        new Thread(() -> Gdx.app.postRunnable(() -> {
                ItemContactEvent event = ((ItemContactEvent) GameEventManager.getInstance()
                        .getGameEvent(ITEM_CONTACT));
                event.setContact(contact);
                event.setItem(item);
                GameEventManager.getInstance().triggerEvent(ITEM_CONTACT);
            })
        ).start();
    }
}
