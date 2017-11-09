package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Interface that declares methods a dedicated  graphics  component class must implement in order to get it's rendering triggered.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public interface IGraphicsComponent {

    /**
     * Renders the graphics component's content to the screen.
     * @param batch The global render batch to render the graphical content with
     */
    void render(Batch batch);

}