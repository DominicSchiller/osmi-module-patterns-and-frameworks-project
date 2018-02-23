package de.thb.paf.scrabblefactory.persistence;


/**
 * Global database information containing all database definitions
 * i.e. tables and it's columns.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

class DBInfo {

    /**
     * Definition of the database table "USERS"
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    static class Users {
        /**
         * The user table's name
         */
        static final String TABLE_NAME = "USERS";

        /**
         * Column definitions from database table "USERS"
         *
         * @author Dominic Schiller - Technische Hochschule Brandenburg
         * @version 1.0
         * @since 1.0
         */
        static class Columns {
            static final String ALL_COLUMNS = "*";
            static final String USER_ID = "userID";
            static final String NAME = "name";
            static final String FIRST_NAME = "firstname";
            static final String NICKNAME = "nickname";
            static final String DATE_OF_BIRTH = "dateOfBirth";
            static final String GENDER_ID = "genderID";
        }
    }

    /**
     * Definition of the database table "GENDER"
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    static class Gender {
        /**
         * The gender table's name
         */
        static final String TABLE_NAME = "GENDER";

        /**
         * Column definitions from database table "GENDER"
         *
         * @author Dominic Schiller - Technische Hochschule Brandenburg
         * @version 1.0
         * @since 1.0
         */
        static class Columns {
            static final String ALL_COLUMNS = "*";
            static final String GENDER_ID = "genderID";
            static final String SHORTCUT = "shortcut";
            static final String DESCRIPTION = "description";
        }
    }

    /**
     * Definition of the database table "SCORES"
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    static class Scores {
        /**
         * The scores table's name
         */
        static final String TABLE_NAME = "SCORES";

        /**
         * Column definitions from database table "SCORES"
         *
         * @author Dominic Schiller - Technische Hochschule Brandenburg
         * @version 1.0
         * @since 1.0
         */
        static class Columns {
            static final String ALL_COLUMNS = "*";
            static final String SCORE_ID = "scoreID";
            static final String SCORE = "score";
        }
    }

    /**
     * Definition of the database table "USER_SCORES"
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    static class UserScores {
        /**
         * The user scores table's name
         */
        static final String TABLE_NAME = "USER_SCORES";

        /**
         * Column definitions from database table "USER_SCORES"
         *
         * @author Dominic Schiller - Technische Hochschule Brandenburg
         * @version 1.0
         * @since 1.0
         */
        static class Columns {
            static final String ALL_COLUMNS = "*";
            static final String USER_ID = "userID";
            static final String SCORE_ID = "scoreID";
            static final String CREATED_AT = "createdAt";
            static final String IS_SYNCHRONIZED = "isSynchronized";
        }
    }

    /**
     * Definition of the database table "SAVEGAMES"
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    static class Savegame {
        /**
         * The savegame table's name
         */
        static final String TABLE_NAME = "SAVEGAMES";

        /**
         * Column definitions from database table "SAVEGAMES"
         *
         * @author Dominic Schiller - Technische Hochschule Brandenburg
         * @version 1.0
         * @since 1.0
         */
        static class Columns {
            static final String ALL_COLUMNS = "*";
            static final String SAVEGAME_ID = "saveGameID";
            static final String USER_ID = "userID";
            static final String SNAPSHOT_DATA = "snapshotData";
        }
    }
}
