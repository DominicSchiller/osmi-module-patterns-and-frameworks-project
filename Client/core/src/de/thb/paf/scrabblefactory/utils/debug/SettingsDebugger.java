package de.thb.paf.scrabblefactory.utils.debug;

import de.thb.paf.scrabblefactory.settings.Settings;

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
        System.out.println("VIRTUAL WIDTH:\t" + Settings.Game.VIRTUAL_WIDTH + "px");
        System.out.println("VIRTUAL HEIGHT:\t" + Settings.Game.VIRTUAL_HEIGHT + "px");
        System.out.println("FPS:\t" + Settings.App.FPS);
        System.out.println("PPM:\t" + Settings.Game.PPM);
//        System.out.println("VIRTUAL MULTIPLYER:\t" + "x" + VIRTUAL_SCREEN_MULTIPLYER);
        System.out.println("--------------------------------------------");
        System.out.println("SCREEN WIDTH:\t" + Settings.App.DEVICE_SCREEN_WIDTH + "px");
        System.out.println("SCREEN HEIGHT:\t" + Settings.App.DEVICE_SCREEN_HEIGHT + "px");
        System.out.println("ASPECT RATIO:\t" + Settings.Game.ASPECT_RATIO.name);
        System.out.println("SCREEN RESOLUTION:\t" + Settings.Game.RESOLUTION);
//        System.out.println("SCALE FACTOR:\t" + SCALE_FACTOR);

        System.out.println("--------------------------------------------");
    }
}
