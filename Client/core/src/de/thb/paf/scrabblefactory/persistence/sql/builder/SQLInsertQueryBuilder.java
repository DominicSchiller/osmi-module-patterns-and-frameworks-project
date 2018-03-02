package de.thb.paf.scrabblefactory.persistence.sql.builder;


import java.util.ArrayList;
import java.util.List;

/**
 * SQL Query Builder dedicated to build SQL statements to insert values into tables.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLInsertQueryBuilder implements ISQLQueryBuilder {

    /**
     * The table name
     */
    private String tableName;

    /**
     * List of columnName-value pairs
     */
    private List<SQLColumnValuePair> columnValuePairs;

    /**
     * Constructor
     * @param tableName The table's name to insert values
     */
    public SQLInsertQueryBuilder(String tableName) {
        this.tableName = tableName;
        this.columnValuePairs = new ArrayList<>();
    }

    /**
     * Insert a column's value.
     * @param columnName The name of the table column to set
     * @param value The column's value
     * @return The current builder instance
     */
    public SQLInsertQueryBuilder insertValue(String columnName, String value) {
        this.columnValuePairs.add(new SQLColumnValuePair(columnName, value));
        return this;
    }

    @Override
    public String create() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder valuesStringBuilder = new StringBuilder();

        stringBuilder.append("insert into " + tableName + "(");
        valuesStringBuilder.append(" values (");

        int index = 0;
        // append column names
        for(SQLColumnValuePair columnValuePair : this.columnValuePairs) {
            if(index != 0) {
                stringBuilder.append(", ");
                valuesStringBuilder.append(", ");
            }
            stringBuilder.append(columnValuePair.columnName);
            if(columnValuePair.value.startsWith("select")) {
                valuesStringBuilder.append("(" + columnValuePair.value + ")");
            } else {
                valuesStringBuilder.append("'" + columnValuePair.value + "'");
            }


            index++;
        }

        stringBuilder.append(")");
        valuesStringBuilder.append(")");

        String query = stringBuilder.toString() + valuesStringBuilder.toString();
        return query;
    }

    /**
     * Represents a basic SQL columnName-value pair.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private class SQLColumnValuePair {

        /**
         * The name of the table column to set
         */
        private String columnName;

        /**
         * The columnName's value
         */
        private String value;

        /**
         * Constructor
         * @param columnName The name of the table column to set
         * @param value The columnName's value
         */
        SQLColumnValuePair(String columnName, String value) {
            this.columnName = columnName;
            this.value = value;
        }
    }
}