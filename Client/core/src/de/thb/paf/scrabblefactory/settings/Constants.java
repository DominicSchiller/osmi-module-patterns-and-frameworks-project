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
     * Private constructor
     */
    private Constants() {
        // this is a static class
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
         * Key to access the 'type' attribute from a JSON Object
         */
        public static final String JSON_KEY_JAVA_PACKAGE = "javaPackage";

        /**
         * Private Constructor
         */
        private Json() {
            // this is a static class
        }
    }
}
