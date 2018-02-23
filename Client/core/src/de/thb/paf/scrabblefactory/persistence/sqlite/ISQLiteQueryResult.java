package de.thb.paf.scrabblefactory.persistence.sqlite;


import java.sql.SQLException;

/**
 * Interface which declares methods a concrete SQL result set must implement.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface ISQLiteQueryResult {

    /**
     * Get column value as Integer.
     * @param columnName The column's name to get the value for
     * @return The column's value
     * @throws SQLException
     */
    int getInt(String columnName) throws SQLException;

    /**
     * Get column value as Long.
     * @param columnName The column's name to get the value for
     * @return The column's value
     * @throws SQLException
     */
    long getLong(String columnName) throws SQLException;

    /**
     * Get column value as String.
     * @param columnName The column's name to get the value for
     * @return The column's value
     * @throws SQLException
     */
    String getString(String columnName) throws SQLException;

    /**
     * Get the amount of selected rows.
     * @return The amount of selected rows.
     */
    int getCount();

    /**
     * Get the status if the SQLite query result has more entries to iterate through.
     * @return The status if the SQLite query result has more entries to iterate through.
     */
    boolean hasNext();

    /**
     * Close the SQLite query result set.
     */
    void close();
}
