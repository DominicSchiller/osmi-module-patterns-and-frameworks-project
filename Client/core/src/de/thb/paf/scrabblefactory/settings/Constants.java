package de.thb.paf.scrabblefactory.settings;

/**
 * Collection of global constants.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class Constants {

    /**
     * Collection of global file's related constants.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class Files {

        /**
         * The default name of a level's texture atlas file
         */
        public static final String LEVEL_ATLAS_NAME = "level";

        /**
         * Private Constructor
         */
        private Files() {
            // this is a static class
        }
    }

    /**
     * Private constructor
     */
    private Constants() {
        // this is a static class
    }

    /**
     * Collection of global Java related constants.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class Java {

        /**
         * The component's full java package path
         */
        public static final String COMPONENTS_PACKAGE = "de.thb.paf.scrabblefactory.models.components.";

        /**
         * Private Constructor
         */
        private Java() {
            // this is a static class
        }
    }

    /**
     * Collection of global Json related constants.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class Json {

        /**
         * Key to access the 'componentList' attribute from a JSON Object
         */
        public static final String JSON_KEY_COMPONENTS = "componentList";

        /**
         * Key to access the 'name' attribute from a JSON Object
         */
        public static final String JSON_KEY_NAME = "name";

        /**
         * Key to access the 'type' attribute from a JSON Object
         */
        public static final String JSON_KEY_TYPE = "type";

        /**
         * Private Constructor
         */
        private Json() {
            // this is a static class
        }
    }
}
