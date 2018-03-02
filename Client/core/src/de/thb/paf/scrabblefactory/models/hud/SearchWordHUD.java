package de.thb.paf.scrabblefactory.models.hud;


import java.util.Observable;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.FontGraphicsComponent;

/**
 * Represents a HUD container dedicated to display a level's search word.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SearchWordHUD extends HUDComponent {

    /**
     * The search word to display
     */
    private String searchWord;

    /**
     * Constructor
     */
    public SearchWordHUD() {
        super();
    }

    /**
     * Get the displayed search word.
     * @return The displayed search word
     */
    public String getSearchWord() {
        return this.searchWord;
    }

    /**
     * Set the displayed search word.
     * @param searchWord The search word to display
     */
    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
        this.updateFontComponents();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Update all associated font graphics components for displaying the
     * current set search word.
     */
    private void updateFontComponents() {
        for(IComponent component : this.getAllComponents(ComponentType.GFX_COMPONENT)) {
            if (component instanceof FontGraphicsComponent) {
                ((FontGraphicsComponent) component).text = this.searchWord;
            }
        }
    }
}
