package de.thb.paf.scrabblefactory.desktop.persistence.sqlite;


import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteDatabase;
import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteDatabaseManager;

/**
 * The SQLite database manager dedicated to desktop systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLiteDesktopDatabaseManager implements ISQLiteDatabaseManager {

    /**
     * List of created and active SQLite databases.
     */
    private List<SQLiteDesktopDatabase> desktopDatabases;

    /**
     * Default Constructor
     */
    public SQLiteDesktopDatabaseManager() {
        this.desktopDatabases = new ArrayList<SQLiteDesktopDatabase>();
    }

    @Override
    public ISQLiteDatabase getNewDatabase(
            String databaseURL,
            int databaseVersion,
            String onCreateQuery,
            String onUpgradeQuery
    ) {
        SQLiteDesktopDatabase database = new SQLiteDesktopDatabase(
                databaseURL, databaseVersion, onCreateQuery, onUpgradeQuery
        );
        database.setup();

        this.desktopDatabases.add(database);
        return database;
    }
}
