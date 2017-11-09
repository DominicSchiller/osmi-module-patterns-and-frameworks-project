package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.models.entities.IEntity;

/**
 * Graphics component responsible for rendering static textures.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class BasicGraphicsComponent extends GameComponent implements IGraphicsComponent {

    /**
     * The sprite image to render
     */
    private Sprite texture;

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param texture The sprite image to render
     */
    public BasicGraphicsComponent(int id, ComponentType type, Sprite texture) {
        super(id, type);
        this.texture = texture;
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     * @param texture The sprite image to render
     */
    public BasicGraphicsComponent(int id, ComponentType type, IEntity parent, Sprite texture) {
        super(id, type, parent);
        this.texture = texture;
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