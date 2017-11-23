package de.thb.paf.scrabblefactory.utils.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.components.graphics.MovementDirection;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Utility class helping to align textures on screen.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class AlignmentHelper {

    /**
     * Private Constructor
     */
    private AlignmentHelper() {
        // this is a static class
    }

    /**
     * Calculates the relative on screen position based on given alignment, object's size and margin properties.
     * @param texture The texture to set the relative position for
     * @param alignment The object's alignment target
     * @param margins The object's outer margins
     */
    public static void setRelativeOnScreenPosition(Sprite texture, Alignment alignment, int[] margins) {
        float x, y;

        switch(alignment) {
            case CENTER_LEFT:
                x = 0;
                y = VIRTUAL_HEIGHT / 2 - texture.getHeight() / 2;
                break;
            case CENTER_RIGHT:
                x = VIRTUAL_WIDTH - texture.getWidth();
                y = VIRTUAL_HEIGHT / 2 - texture.getHeight() / 2;
                break;
            case TOP_CENTER:
                x = VIRTUAL_WIDTH / 2 - texture.getWidth() / 2;
                y = VIRTUAL_HEIGHT - texture.getHeight();
                break;
            case TOP_LEFT:
                x = 0;
                y = VIRTUAL_HEIGHT - texture.getHeight();
                break;
            case TOP_RIGHT:
                x = VIRTUAL_WIDTH - texture.getWidth();
                y = VIRTUAL_HEIGHT - texture.getHeight();
                break;
            case BOTTOM_CENTER:
                x = VIRTUAL_WIDTH / 2 - texture.getWidth() / 2;
                y = 0;
                break;
            case BOTTOM_LEFT:
                x = 0;
                y = 0;
                break;
            case BOTTOM_RIGHT:
                x = VIRTUAL_WIDTH - texture.getWidth();
                y = 0;
                break;
            case MIDDLE:
            default:
                x = VIRTUAL_WIDTH / 2 - texture.getWidth() / 2;
                y = VIRTUAL_HEIGHT / 2 - texture.getHeight() / 2;
                break;
        }

        // apply margins
        x += margins[3] != 0 ? margins[3] / PPM : 0;
        x -= margins[1] != 0 ? margins[1] / PPM : 0;
        y -= margins[0] != 0 ? margins[0] / PPM : 0;
        y += margins[2] != 0 ? margins[2] / PPM : 0;

        texture.setPosition(x, y);
    }

    /**
     * Update texture's on screen position by it's movement definition.
     * @param texture The texture to update
     * @param movementDirection The object's movement position
     * @param speed The object's movement speed
     * @param isAutoLoop Flag whether the movement is auto-looped or not
     */
    public static void updatePositionByAutoMovement(Sprite texture, MovementDirection movementDirection, float speed, boolean isAutoLoop) {

        Vector2 updatedPosition = new Vector2();
        speed = speed/10f / PPM;
        switch(movementDirection) {
            case LEFT:
                float viewPortWidth = VIRTUAL_WIDTH;

                if(texture.getX() < (viewPortWidth * -1) + ((viewPortWidth - texture.getWidth()))) {
                    if(isAutoLoop) {
                        // reset to initial position
                        texture.setX(texture.getX() + viewPortWidth - speed); // - ((viewPortWidth - sprite.getWidth()))
                    } else {
                        // no auto loop so the sprite stops here
                    }
                } else {
                    // set movement to the left
                    texture.setX(texture.getX() - speed);
                }
                break;
        }
    }
}
