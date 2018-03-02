package de.thb.paf.scrabblefactory.android.persistence.sqlite;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteDatabase;
import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteDatabaseManager;


/**
 * The SQLite database manager dedicated to Android systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLiteAndroidDatabaseManager implements ISQLiteDatabaseManager {

    /**
     * List of created and active SQLite databases.
     */
    private List<SQLiteAndroidDatabase> appDatabases;

    /**
     * Default Constructor
     */
    public SQLiteAndroidDatabaseManager() {
        this.appDatabases = new ArrayList<>();
    }

    @Override
    public ISQLiteDatabase getNewDatabase(
            String databaseURL,
            int databaseVersion,
            String onCreateQuery,
            String onUpgradeQuery)
    {
        SQLiteAndroidDatabase database = new SQLiteAndroidDatabase(databaseURL, databaseVersion,
                onCreateQuery, onUpgradeQuery
        );
        database.setup();

        this.appDatabases.add(database);
        return database;
    }
}
