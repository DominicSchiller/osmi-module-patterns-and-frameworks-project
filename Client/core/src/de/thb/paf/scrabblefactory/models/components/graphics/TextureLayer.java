package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a  basic texture layer  which can be used by the LayerdTexturesGraphicsComponent.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 * @see LayeredTexturesGraphicsComponent
 */
public class TextureLayer {

    /**
     * The texture to render
     */
    public final Sprite texture;

    /**
     * The z-index representing the order in the layer-stack
     */
    public final int zindex;

    /**
     * Constructor
     * @param texture The texture to render
     * @param zIndex The texture's z-order on the render stack
     */
    public TextureLayer(Sprite texture, int zIndex) {
        this.texture = texture;
        this.zindex = zIndex;
    }

}