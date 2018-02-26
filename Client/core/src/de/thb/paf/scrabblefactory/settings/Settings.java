package de.thb.paf.scrabblefactory.settings;

/**
 * Global Settings containing collection of particular settings classes.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

import com.badlogic.gdx.Gdx;

import de.thb.paf.scrabblefactory.utils.graphics.DeviceDimensionsHelper;

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
     * Private constructor
     */
    private Settings() {
        // this is a plain static class
    }

    /**
     * Update variable settings.
     */
    public static void update() {
        Settings.App.update();
        Settings.Game.update();
    }

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
        public static int DEVICE_SCREEN_WIDTH;

        /**
         * The device's current screen height
         */
        public static int DEVICE_SCREEN_HEIGHT;

        /**
         * The frames per seconds count:
         * the frequency at which all consecutive graphics are updated and displayed on the animated display.
         */
        public static final int FPS = 60;

        static {
            update();
        }

        /**
         * Private constructor
         */
        private App() {
            // this is a plain static class
        }

        /**
         * Update variable app settings.
         */
        public static void update() {
            DEVICE_SCREEN_WIDTH = Gdx.graphics.getWidth();
            DEVICE_SCREEN_HEIGHT = Gdx.graphics.getHeight();
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
         * The virtual 'points per meter' factor required for the Box2D physics calculation
         */
        public static final float PPM = 32f;

        /**
         * The virtual screen width (where all graphical assets are based on)
         */
        public static final int VIRTUAL_WIDTH = (int) (480 / PPM);

        /**
         * The virtual screen width (where all graphical assets are based on)
         */
        public static final int VIRTUAL_HEIGHT = (int) (320 / PPM);

        /**
         * The device's screen aspect ratio
         */
        public static ScreenAspectRatio ASPECT_RATIO;

        /**
         * The device's screen resolution
         */
        public static ScreenResolution RESOLUTION;

        /**
         * The virtual scale factor to adjust texture's scaling
         */
        public static float VIRTUAL_SCALE;

        /**
         * The virtual pixel density multiplier
         */
        public static float VIRTUAL_PIXEL_DENSITY_MULTIPLIER;

        static {
            update();
        }

        /**
         * Private constructor
         */
        private Game() {
            // this is a plain static class
        }

        /**
         * Update variable game settings.
         */
        public static void update() {
            ASPECT_RATIO = DeviceDimensionsHelper.calculateAspectRatio(DEVICE_SCREEN_WIDTH, DEVICE_SCREEN_HEIGHT);
            RESOLUTION = DeviceDimensionsHelper.calculateScreenResolution(DEVICE_SCREEN_WIDTH);
            VIRTUAL_SCALE = DeviceDimensionsHelper.calculateVirtualScale();
            VIRTUAL_PIXEL_DENSITY_MULTIPLIER = DeviceDimensionsHelper.calculateScreenMultiplier();
        }
    }

    /**
     * Settings section embracing all database relevant settings.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static final class Database {
        /**
         * The game's main database name
         */
        public static final String DATABASE_NAME = "ScrabbleFactory.sqlite";

        /**
         * The game's main database version
         */
        public static final int DATABASE_VERSION = 1;
    }

    /**
     * Settings section embracing all debug relevant settings.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static final class Debug {

        /**
         * Status if enable the game's overall debug mode
         */
        public static final boolean isDebugModeEnabled = true;
    }
}
