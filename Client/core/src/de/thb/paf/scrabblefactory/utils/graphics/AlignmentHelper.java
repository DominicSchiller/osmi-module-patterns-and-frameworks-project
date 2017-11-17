package de.thb.paf.scrabblefactory.utils.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.components.graphics.MovementDirection;
import de.thb.paf.scrabblefactory.settings.Settings;

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
                y = Settings.Game.VIRTUAL_HEIGHT / 2 - texture.getHeight() / 2;
                break;
            case CENTER_RIGHT:
                x = Settings.Game.VIRTUAL_WIDTH - texture.getWidth();
                y = Settings.Game.VIRTUAL_HEIGHT / 2 - texture.getHeight() / 2;
                break;
            case TOP_CENTER:
                x = Settings.Game.VIRTUAL_WIDTH / 2 - texture.getWidth() / 2;
                y = Settings.Game.VIRTUAL_HEIGHT - texture.getHeight();
                break;
            case TOP_LEFT:
                x = 0;
                y = Settings.Game.VIRTUAL_HEIGHT - texture.getHeight();
                break;
            case TOP_RIGHT:
                x = Settings.Game.VIRTUAL_WIDTH - texture.getWidth();
                y = Settings.Game.VIRTUAL_HEIGHT - texture.getHeight();
                break;
            case BOTTOM_CENTER:
                x = Settings.Game.VIRTUAL_WIDTH / 2 - texture.getWidth() / 2;
                y = 0;
                break;
            case BOTTOM_LEFT:
                x = 0;
                y = 0;
                break;
            case BOTTOM_RIGHT:
                x = Settings.Game.VIRTUAL_WIDTH - texture.getWidth();
                y = 0;
                break;
            case MIDDLE:
            default:
                x = Settings.Game.VIRTUAL_WIDTH / 2 - texture.getWidth() / 2;
                y = Settings.Game.VIRTUAL_HEIGHT / 2 - texture.getHeight() / 2;
                break;
        }

        // apply margins
        x += margins[3];
        x -= margins[1];
        y -= margins[0];
        y += margins[2];

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
        speed = speed/10f;
        switch(movementDirection) {
            case LEFT:
                float viewPortWidth = Settings.Game.VIRTUAL_WIDTH;

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
