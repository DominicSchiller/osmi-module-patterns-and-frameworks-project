package de.thb.paf.scrabblefactory.settings;

/**
 * Global Settings containing collection of particular settings classes.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

import com.badlogic.gdx.Gdx;

import de.thb.paf.scrabblefactory.utils.ui.DeviceDimensionsHelper;

import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_WIDTH;

/**
 * Global settings class which embraces all configuration and settings properties used in the game.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public final class Settings {

    /**
     * Settings section embracing all application relevant settings.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static final class App {

        /**
         * The application's title
         */
        public static final String APP_TITLE = "Scrabble Factory";

        /**
         * The devices current screen width
         */
        public static final int DEVICE_SCREEN_WIDTH = Gdx.graphics.getWidth();

        /**
         * The device's current screen height
         */
        public static int DEVICE_SCREEN_HEIGHT = Gdx.graphics.getHeight();

        /**
         * The frames per seconds count:
         * the frequency at which all consecutive graphics are updated and displayed on the animated display.
         */
        public static final int FPS = 60;

        /**
         * Private constructor
         */
        private App() {
            // this is a plain static class
        }
    }

    /**
     * Settings section embracing all game relevant settings.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static final class Game {
        /**
         * The virtual screen width (where all graphical assets are based on)
         */
        public static final int VIRTUAL_WIDTH = 480;

        /**
         * The virtual screen width (where all graphical assets are based on)
         */
        public static final int VIRTUAL_HEIGHT = 320;

        /**
         * The virtual 'points per meter' factor required for the Box2D physics calculation
         */
        public static final float PPM = 32f;

        /**
         * The device's screen aspect ratio
         */
        public static ScreenAspectRatio ASPECT_RATIO = DeviceDimensionsHelper.calculateAspectRatio(DEVICE_SCREEN_WIDTH, DEVICE_SCREEN_HEIGHT);

        /**
         * The device's screen resolution
         */
        public static ScreenResolution RESOLUTION = DeviceDimensionsHelper.calculateScreenResolution(DEVICE_SCREEN_WIDTH);

        /**
         * The virtual scale factor to adjust texture's scaling
         */
        public static float VIRTUAL_SCALE = DeviceDimensionsHelper.calculateVirtualScale();

        /**
         * Private constructor
         */
        private Game() {
            // this is a plain static class
        }
    }

    /**
     * Private constructor
     */
    private Settings() {
        // this is a plain static class
    }

}
