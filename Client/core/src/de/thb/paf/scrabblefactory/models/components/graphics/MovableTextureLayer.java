package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.*;

/**
 * Represents a advanced movable texture layer  which can be used by the LayerdTexturesGraphicsComponent.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 * @see TextureLayer
 * @see LayeredTexturesGraphicsComponent
 */
public class MovableTextureLayer extends TextureLayer {

    /**
     * Indicator whether to loop the movement animation in the case the texture has passed the screen's borders or not
     */
    public final boolean isInfiniteLoop;

    /**
     * The movement speed
     */
    public final float speed;

    /**
     * The movement direction
     */
    public MovementDirection direction;

    /**
     * Constructor
     * @param texture The texture to render
     * @param zIndex The texture's z-order on the render stack
     * @param speed The layer's movement speed
     * @param isInfiniteLoop Indicator whether to loop the movement animation in the case the texture has passed the screen's borders or not
     */
    public MovableTextureLayer(Sprite texture, int zIndex, float speed, boolean isInfiniteLoop) {
        super(texture, zIndex);
        this.speed = speed;
        this.isInfiniteLoop = isInfiniteLoop;
    }

}