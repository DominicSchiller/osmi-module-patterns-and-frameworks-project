package de.thb.paf.scrabblefactory.persistence.sql.builder;


import java.util.ArrayList;
import java.util.List;

/**
 * SQL Query Builder dedicated to build SQL statements to createTable new tables.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLCreateTableQueryBuilder implements ISQLQueryBuilder {

    /**
     * The table name
     */
    private String tableName;

    /**
     * List of table columns
     */
    private List<SQLTableColumnBuilder> tableColumns;

    /**
     * List of foreign keys
     */
    private List<SQLForeignKeyBuilder> foreignKeys;

    /**
     * Status if only createTable the table if not already existing
     */
    private boolean isCreateOnlyIfNotExisting;

    /**
     * Constructor
     * @param tableName The table's name to createTable
     */
    public SQLCreateTableQueryBuilder(String tableName) {
        this.tableName = tableName;
        this.tableColumns = new ArrayList<>();
        this.foreignKeys = new ArrayList<>();
        this.isCreateOnlyIfNotExisting = false;
    }

    /**
     * Set the status to only createTable the table if not already existing
     * @return The current builder instance
     */
    public SQLCreateTableQueryBuilder ifNotExisting() {
        this.isCreateOnlyIfNotExisting = true;
        return this;
    }

    /**
     * Define a table column to createTable.
     * @param columnName The table column's name
     * @param dataType The table column's data type
     * @param constraints The table columns's constraints
     * @return The current builder instance
     */
    public SQLCreateTableQueryBuilder withColumn(String columnName, SQLTableColumnDataType dataType, SQLTableColumnConstraintType... constraints) {
        this.tableColumns.add(
                new SQLTableColumnBuilder(columnName, dataType, constraints)
        );

        return this;
    }

    /**
     * Define a primary key.
     * @param columnName The column name which will be a foreign key
     * @param referencingTable The referencing table
     * @param referencingColumn The referencing table's column
     * @return The current builder instance
     */
    public SQLCreateTableQueryBuilder withForeignKey(String columnName, String referencingTable, String referencingColumn) {
        this.foreignKeys.add(new SQLForeignKeyBuilder(
                columnName, referencingTable, referencingColumn
        ));
        return this;
    }

    @Override
    public String create() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "create table " +
                        (this.isCreateOnlyIfNotExisting ? "if not exists " : "") +
                        this.tableName + "("
        );

        for(int i = 0; i<this.tableColumns.size(); i++) {
            SQLTableColumnBuilder columnBuilder = this.tableColumns.get(i);
            stringBuilder.append(columnBuilder.create());
            if(i != tableColumns.size() - 1) {
                stringBuilder.append(", ");
            } else {
                for(SQLForeignKeyBuilder foreignKeyBuilder : this.foreignKeys) {
                    stringBuilder.append(", " + foreignKeyBuilder.create());
                }
                stringBuilder.append(")");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * SQL Query Builder dedicated to build SQL statements for defining table columns.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private class SQLTableColumnBuilder implements ISQLQueryBuilder {
        /**
         * The table column's name
         */
        private String name;

        /**
         * The table column's data type
         */
        private SQLTableColumnDataType dataType;

        /**
         * The table columns's key type
         */
        private SQLTableColumnConstraintType[] constraints;

        /**
         * Constructor
         * @param name The table column's name
         * @param dataType The table column's data type
         * @param constraints The table columns's constraints
         */
        SQLTableColumnBuilder(String name, SQLTableColumnDataType dataType, SQLTableColumnConstraintType... constraints) {
            this.name = name;
            this.dataType = dataType;
            this.constraints = constraints;
        }

        @Override
        public String create() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.name + " " + this.dataType.string);

            for(SQLTableColumnConstraintType constraint : this.constraints) {
                stringBuilder.append(" " + constraint.string);
            }

            return  stringBuilder.toString();
        }
    }

    /**
     * SQL Query Builder dedicated to build SQL statements for defining foreign keys.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private class SQLForeignKeyBuilder implements ISQLQueryBuilder {

        /**
         * The column name which will be a foreign key
         */
        private String columnName;

        /**
         * The referencing table
         */
        private String referencingTable;

        /**
         * The referencing table's addressed column
         */
        private String referencingColumn;

        /**
         * Constructor
         * @param columnName The column name which will be a foreign key
         * @param referencingTable The referencing table
         * @param referencingColumn The referencing table's column
         */
        SQLForeignKeyBuilder(String columnName, String referencingTable, String referencingColumn) {
            this.columnName = columnName;
            this.referencingTable = referencingTable;
            this.referencingColumn = referencingColumn;
        }

        @Override
        public String create() {
            return "FOREIGN KEY(" + this.columnName +
                    ") REFERENCES " + this.referencingTable
                    + "(" + this.referencingColumn + ")";
        }
    }
}
