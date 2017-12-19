package de.thb.paf.scrabblefactory.utils.graphics;

import de.thb.paf.scrabblefactory.settings.ScreenAspectRatio;
import de.thb.paf.scrabblefactory.settings.ScreenResolution;

import static de.thb.paf.scrabblefactory.settings.ScreenAspectRatio.*;
import static de.thb.paf.scrabblefactory.settings.ScreenResolution.*;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.*;
import static de.thb.paf.scrabblefactory.settings.Settings.App.*;

/**
 * Utility class helping to calculate screen related attributes.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class DeviceDimensionsHelper {

    /**
     * Private Constructor
     */
    private DeviceDimensionsHelper() {
        // this is a static utils class
    }

    /**
     * Calculates the device's screen aspect ratio.
     * @param width The current screen's width
     * @param height The current screen's height
     * @return The calculated aspect ratio
     */
    public static ScreenAspectRatio calculateAspectRatio(int width, int height) {
        float ratio = width / (float)height;

        ScreenAspectRatio screenAspectRatio;
        if(ratio >= SIXTEEN_TO_NINE.value) {
            screenAspectRatio = SIXTEEN_TO_NINE;
        } else if(ratio >= SIXTEEN_TO_TEN.value && ratio < SIXTEEN_TO_NINE.value) {
            screenAspectRatio = SIXTEEN_TO_TEN;
        } else if(ratio >= THREE_TO_TWO.value && ratio < SIXTEEN_TO_TEN.value) {
            screenAspectRatio = THREE_TO_TWO;
        } else {
            screenAspectRatio = FOUR_TO_THREE;
        }

        return screenAspectRatio;
    }

    /**
     * Calculates the device's screen density based on the screen's landscaped minWidth.
     * @param width The screen's minWidth
     * @return The screen density type
     */
    public static ScreenResolution calculateScreenResolution(int width) {
        ScreenResolution resolution;

        if(width >= DCI_4K.minWidth) {
            resolution = DCI_4K;
        } else if(width > HD2.minWidth) {
            resolution = UHD;
        } else if(width > HD.minWidth) {
            resolution = HD2;
        } else if(width > SD.minWidth) {
            resolution = HD;
        } else {
            resolution = SD;
        }

        return resolution;
    }

    /**
     * Calculates the scale factor required to scale all textures correctly.
     * @return The calculated scale factor
     */
    public static float calculateVirtualScale() {
        float scaleFactor = 0;
        float deviceAspectScale = 1;

        switch(ASPECT_RATIO) {
            case SIXTEEN_TO_NINE:
            case SIXTEEN_TO_TEN:
            case THREE_TO_TWO:
                deviceAspectScale = DEVICE_SCREEN_WIDTH / (float)RESOLUTION.maxWidth;
                scaleFactor = VIRTUAL_WIDTH / (DEVICE_SCREEN_WIDTH / PPM);
                scaleFactor *= deviceAspectScale;
                break;
            case FOUR_TO_THREE:
                deviceAspectScale = DEVICE_SCREEN_HEIGHT / (float)RESOLUTION.maxHeight;
                scaleFactor = VIRTUAL_WIDTH / (DEVICE_SCREEN_HEIGHT/ PPM);
                scaleFactor *= deviceAspectScale;
                break;
        }

        return scaleFactor / PPM;
    }

    /**
     * Calculates the virtual pixel density multiplier required to fix some resolution issues.
     * @return The calculated pixel density multiplier
     */
    public static float calculateScreenMultiplier() {
        float multiplier = 1;

        switch(ASPECT_RATIO) {
            case SIXTEEN_TO_NINE:
            case SIXTEEN_TO_TEN:
            case THREE_TO_TWO:
                multiplier = (float) Math.floor((DEVICE_SCREEN_WIDTH / PPM) / (float)VIRTUAL_WIDTH);
                break;
            case FOUR_TO_THREE:
                multiplier = (float) Math.floor((DEVICE_SCREEN_HEIGHT / PPM) / (float)VIRTUAL_WIDTH);
                break;
        }

        return multiplier;
    }
}
