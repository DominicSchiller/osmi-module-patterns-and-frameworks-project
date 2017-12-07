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
    public Sprite texture;

    /**
     * The name of the associated texture file
     */
    public final String textureName;

    /**
     * The component's relative on screen alignment
     */
    public final Alignment alignment;

    /**
     * The component's relative on screen margins
     */
    public final int[] margin;

    /**
     * Flag whether to align the graphics relative to the parent's bounds
     */
    public final boolean isRelativeToParent;


    /**
     * Constructor
     * @param id The game component's unique id
     */
    public BasicGraphicsComponent(Integer id) {
        super(id, ComponentType.GFX_COMPONENT);

        this.textureName = "";
        this.alignment = Alignment.MIDDLE;
        this.margin = new int[0];
        this.isRelativeToParent = false;
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param texture The sprite image to render
     */
    public BasicGraphicsComponent(int id, Sprite texture) {
        this(id);
        this.texture = texture;
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param parent The associated game entity holding this component
     * @param texture The sprite image to render
     */
    public BasicGraphicsComponent(int id, IEntity parent, Sprite texture) {
        this(id);
        this.setParent(parent);
        this.texture = texture;
    }

    @Override
    public void update(float deltaTime) {
        // TODO implement here
    }

    @Override
    public void render(Batch batch) {
        this.texture.draw(batch);
    }

}