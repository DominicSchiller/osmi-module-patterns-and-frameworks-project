package de.thb.paf.scrabblefactory.persistence.sqlite;


import java.util.List;

import de.thb.paf.scrabblefactory.persistence.entities.IDBEntity;

/**
 * Interface that declares methods a SQLite database instance must implement in order
 * to get used correctly by the scrabble factory game engine.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface ISQLiteDatabase {
    /**
     * Setup the database instance.
     */
    void setup();

    /**
     * Open the database to perform SQL queries.
     */
    void open();

    /**
     * Execute a 'Create', 'Alter' or 'Delete' SQL query.
     * @param sql The SQL query to execute
     */
    void executeDDL(String sql);

    /**
     * Execute a 'Insert' or 'Update' SQL query.
     * @param sql The SQL query to execute
     */
    IDBEntity executeInsertOrUpdate(String sql, IDBEntity entity);

    /**
     * Execute a 'Select' SQL query.
     * @param sql The SQL query to execute.
     * @param entityType The entity class type to retrieve as result
     * @return The entity result
     */
    List<IDBEntity> executeSelect(String sql, Class<?> entityType);

    /**
     * Close the database.
     */
    void close();
}
