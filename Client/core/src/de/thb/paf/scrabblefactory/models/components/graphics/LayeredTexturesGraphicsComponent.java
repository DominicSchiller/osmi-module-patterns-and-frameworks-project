package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Graphics component responsible for rendering layered textures.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class LayeredTexturesGraphicsComponent extends GameComponent implements IGraphicsComponent {

    /**
     * List of static textures to render stacked
     */
    private TextureLayer[] staticLayers;

    /**
     * List of movable textures to render stacked
     */
    private MovableTextureLayer[] movableLayers;

    /**
     * Constructor
     * @param id The game component's unique id
     */
    public LayeredTexturesGraphicsComponent(Integer id) {
        super(id, ComponentType.GFX_COMPONENT);
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     */
    public LayeredTexturesGraphicsComponent(int id, ComponentType type, IEntity parent) {
        super(id, type, parent);
    }

    @Override
    public void update(float deltaTime) {
        // we just update all movable layers
        for(MovableTextureLayer layer : this.movableLayers) {
            // just update movable sprites
            AlignmentHelper.updatePositionByAutoMovement(
                    layer.texture,
                    layer.direction,
                    layer.speed,
                    layer.isInfiniteLoop
            );
        }
        // TODO implement here
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        for(TextureLayer layer : this.staticLayers) {
            layer.texture.draw(batch);
        }
        for(TextureLayer layer : this.movableLayers) {
            layer.texture.draw(batch);
            // draw a "ghost" copy of the moving sprite
            // to ensure a seamless auto loop transition
            batch.draw(
                    layer.texture,
                    layer.texture.getX() + layer.texture.getWidth() + VIRTUAL_WIDTH - layer.texture.getWidth(),
                    layer.texture.getY(), layer.texture.getWidth(), layer.texture.getHeight());


        }
        batch.end();
    }

    /**
     * Get all associated static layers.
     * @return List of associated static layers.
     */
    public TextureLayer[] getStaticLayers() {
        return this.staticLayers;
    }

    /**
     * Get all associated movable layers.
     * @return List of associated movable layers.
     */
    public MovableTextureLayer[] getMovableLayers() {
        return this.movableLayers;
    }
}
