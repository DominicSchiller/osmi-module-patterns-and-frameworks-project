package de.thb.paf.scrabblefactory.gameplay;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thb.paf.scrabblefactory.gameplay.sort.CheeseComparator;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
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
            searchWordBuilder.append(cheese.getLetter());
        }

        return searchWordBuilder.toString().equals(this.searchWord);
    }
}
