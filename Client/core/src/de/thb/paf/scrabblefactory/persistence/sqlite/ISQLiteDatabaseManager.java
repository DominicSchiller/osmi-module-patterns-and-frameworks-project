package de.thb.paf.scrabblefactory.persistence.sqlite;


/**
 * Interface that declares methods a SQLite database manager instance must implement in order
 * to get used correctly by the scrabble factory game engine.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface ISQLiteDatabaseManager {

    /**
     * Get a new database instance.
     * @param databaseURL The database's URL including the database's name
     * @param databaseVersion The database's version
     * @param onCreateQuery The SQL statement to execute when creating the database
     * @param onUpgradeQuery The SQL statement to execute when upgrading the database
     * @return
     */
    ISQLiteDatabase getNewDatabase(
            String databaseURL,
            int databaseVersion,
            String onCreateQuery,
            String onUpgradeQuery
    );
}
