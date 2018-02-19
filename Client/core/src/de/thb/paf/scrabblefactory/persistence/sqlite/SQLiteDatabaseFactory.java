package de.thb.paf.scrabblefactory.persistence.sqlite;


import com.badlogic.gdx.Gdx;

/**
 * Database factory which will ensure creating the correct SQLite database as a function on
 * the current platform's operating system.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLiteDatabaseFactory {

    /**
     * The package name from the desktop's SQLite database manager
     */
    private static final String DESKTOP_PACKAGE_NAME = "de.thb.paf.scrabblefactory.desktop.persistence.sqlite.";

    /**
     * The class name from the desktop's SQLite database manager
     */
    private static final String DESKTOP_CLASS_NAME = "SQLiteDesktopDatabaseManager";

    /**
     * The game's database manager
     *
     */
    private static ISQLiteDatabaseManager databaseManager;

    /**
     * Get a SQLite database instance.
     * @param databaseURL The database's URL including the database's name
     * @param databaseVersion The database's version
     * @param onCreateQuery The SQL statement to execute when creating the database
     * @param onUpgradeQuery The SQL statement to execute when upgrading the database
     * @return The requested SQLite database
     */
    public static ISQLiteDatabase getDatabase(
            String databaseURL,
            int databaseVersion,
            String onCreateQuery,
            String onUpgradeQuery
    ) {
        // lazy loading: we just need to determine and createTable the database manager
        // once because the operating system won't change at runtime
        if(databaseManager == null) {
            switch(Gdx.app.getType()) {
                case Desktop:
                    try {
                        databaseManager = (ISQLiteDatabaseManager)Class.forName(DESKTOP_PACKAGE_NAME + DESKTOP_CLASS_NAME).newInstance();
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case Android:
                    break;
            }
        }

        return databaseManager.getNewDatabase(databaseURL, databaseVersion, onCreateQuery, onUpgradeQuery);
    }
}
