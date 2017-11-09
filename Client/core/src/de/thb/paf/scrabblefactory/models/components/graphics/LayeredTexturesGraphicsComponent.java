package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.List;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.models.entities.IEntity;

/**
 * Graphics component responsible for rendering layered textures.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class LayeredTexturesGraphicsComponent extends GameComponent implements IGraphicsComponent {

    /**
     * List of textures to render stacked
     */
    private List<TextureLayer> layers;

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param layers The list of layered textures to render
     */
    public LayeredTexturesGraphicsComponent(int id, ComponentType type, List<TextureLayer> layers) {
        super(id, type);
        this.layers = layers;
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     * @param layers The list of layered textures to render
     */
    public LayeredTexturesGraphicsComponent(int id, ComponentType type, IEntity parent, List<TextureLayer> layers) {
        super(id, type, parent);
        this.layers = layers;
    }

    @Override
    public void update(float deltaTime) {
        // TODO implement here
    }

    @Override
    public void render(Batch batch) {
        // TODO implement here
    }

}