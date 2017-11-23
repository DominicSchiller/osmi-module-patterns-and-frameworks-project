package de.thb.paf.scrabblefactory.utils.debug;

import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_WIDTH;
import static de.thb.paf.scrabblefactory.settings.Settings.App.FPS;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.ASPECT_RATIO;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Utility class helping printing out the current setting's attributes when needed.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SettingsDebugger {

    /**
     * Private Constructor
     */
    private SettingsDebugger() {
        // static class
    }

    /**
     * Prints current game config properties to standard output channel.
     */
    public static void printSettings() {

//        if(!IS_VISUAL_DEBUGGING_ENABLED) {
//            return;
//        }

        System.out.println("--------------------------------------------");
        System.out.println("VIRTUAL WIDTH:\t" + VIRTUAL_WIDTH + "px");
        System.out.println("VIRTUAL HEIGHT:\t" + VIRTUAL_HEIGHT + "px");
        System.out.println("FPS:\t" + FPS);
        System.out.println("PPM:\t" + PPM);
        System.out.println("VIRTUAL PIXEL DENSITY MULTIPLIER:\t" + "x" + VIRTUAL_PIXEL_DENSITY_MULTIPLIER);
        System.out.println("--------------------------------------------");
        System.out.println("SCREEN WIDTH:\t" + DEVICE_SCREEN_WIDTH + "px");
        System.out.println("SCREEN HEIGHT:\t" + DEVICE_SCREEN_HEIGHT + "px");
        System.out.println("ASPECT RATIO:\t" + ASPECT_RATIO.name);
        System.out.println("SCREEN RESOLUTION:\t" + RESOLUTION);
        System.out.println("SCALE FACTOR:\t" + VIRTUAL_SCALE);

        System.out.println("--------------------------------------------");
    }
}
