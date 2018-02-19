package de.thb.paf.scrabblefactory.persistence.sql.builder;


import de.thb.paf.scrabblefactory.persistence.sql.builder.SQLSelectQueryBuilder.SQLColumnSelector;

/**
 * SQL query offers static access for building all
 * main SQL statements and operations supported by this game.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLQuery {

    /**
     * Get a builder instance for creating a 'createTable table' SQL statement.
     * @param tableName The table's name to createTable
     * @return The builder instance for creating a 'createTable table' SQL statement
     */
    public static SQLCreateTableQueryBuilder createTable(String tableName) {
        return new SQLCreateTableQueryBuilder(tableName);
    }

    /**
     * Get a builder instance for creating a 'insert into' SQL statement.
     * @param tableName The table's name to insert values into
     * @return The builder instance for creating a 'insert into' SQL statement
     */
    public static SQLInsertQueryBuilder insertInto(String tableName) {
        return new SQLInsertQueryBuilder(tableName);
    }

    /**
     * Get a builder instance to create a 'select from' SQL statement.
     * @param columnNames List of column names to query
     * @return The builder instance to create a 'select from' SQL statement
     */
    public static SQLSelectQueryBuilder select(String... columnNames) {
        return new SQLSelectQueryBuilder(columnNames);
    }

    /**
     * Get a builder instance to create a 'select from' SQL statement.
     * @param columnSelectors List of SQL column selectors to query
     * @return The builder instance to create a 'select from' SQL statement
     */
    public static SQLSelectQueryBuilder select(SQLColumnSelector... columnSelectors) {
        return new SQLSelectQueryBuilder(columnSelectors);
    }

}
