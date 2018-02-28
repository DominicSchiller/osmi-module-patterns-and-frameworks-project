package de.thb.paf.scrabblefactory.gameplay;


import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thb.paf.scrabblefactory.gameplay.sort.CheeseComparator;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.entities.Cheese;
import de.thb.paf.scrabblefactory.models.entities.EntityType;

/**
 * A basic watchdog dedicated to determine if the player has mastered the
 * scrabble challenge.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class ScrabbleChallengeWatchdog {

    /**
     * The goal search word
     */
    private String searchWord;

    /**
     * Constructor.
     * @param searchWord The goal search word
     */
    public ScrabbleChallengeWatchdog(String searchWord) {
        this.searchWord = searchWord;
    }

    /**
     * Determine if the player has mastered the scrabble challenge.
     * @return Status if the player has won
     */
    public boolean isChallengeWon() {
        GameObjectManager gom = GameObjectManager.getInstance();
        List<Cheese> cheeseItems = new ArrayList<>();
        for(IGameObject gameObject : gom.getGameEntity(EntityType.CHEESE)) {
            if(gameObject instanceof Cheese && ((Cheese) gameObject).isActive()) {
                cheeseItems.add((Cheese)gameObject);
            }
        }

        Collections.sort(cheeseItems, new CheeseComparator());

        StringBuilder searchWordBuilder = new StringBuilder();
        for(Cheese cheese : cheeseItems) {
            BodyDef.BodyType bodyType = BodyDef.BodyType.StaticBody;
            for(IComponent component : cheese.getAllComponents(ComponentType.PHYS_COMPONENT)) {
                if(component instanceof RigidBodyPhysicsComponent) {
                    bodyType = ((RigidBodyPhysicsComponent) component).getBody().getType();
                }
            }
            // just verify a cheese item if it is not caught by the player
            if(cheese.getCarrier() == null && bodyType == BodyDef.BodyType.KinematicBody)
                searchWordBuilder.append(cheese.getLetter());
        }

        return searchWordBuilder.toString().equals(this.searchWord);
    }
}
