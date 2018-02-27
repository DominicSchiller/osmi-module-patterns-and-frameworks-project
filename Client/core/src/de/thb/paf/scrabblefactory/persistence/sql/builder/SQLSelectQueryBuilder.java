package de.thb.paf.scrabblefactory.persistence.sql.builder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SQL Query Builder dedicated to build SQL statements to select values from a selectedTables.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLSelectQueryBuilder implements ISQLQueryBuilder {

    /**
     * List of tables to select columns from
     */
    private List<SQLTableSelector> selectedTables;

    /**
     * List of columns to select
     */
    private List<SQLColumnSelector> selectedColumns;

    /**
     * List of WHERE constraints
     */
    private List<SQLWhereConstraint> whereConstraints;

    /**
     * List of JOIN constraints
     */
    private List<SQLJoinConstraint> joinConstraints;

    /**
     * Default Constructor.
     */
    public SQLSelectQueryBuilder() {
        this.selectedTables = new ArrayList<>();
        this.selectedColumns = new ArrayList<>();
        this.whereConstraints = new ArrayList<>();
        this.joinConstraints = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param columnNames List of column names to query
     */
    public SQLSelectQueryBuilder(String... columnNames) {
        this();
        this.select(columnNames);
    }

    /**
     * Constructor.
     * @param columnSelectors List of SQL column selectors to query
     */
    public SQLSelectQueryBuilder(SQLColumnSelector... columnSelectors) {
        this();
        this.select(columnSelectors);
    }

    /**
     * Apply column selections.
     * @param columnSelectors List of SQL column selectors to select
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder select(SQLColumnSelector... columnSelectors) {
        this.selectedColumns.addAll(Arrays.asList(columnSelectors));
        return this;
    }

    /**
     * Apply column selections.
     * @param columnNames List of column names to select
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder select(String... columnNames) {
        SQLColumnSelector[] columnSelectors = new SQLColumnSelector[columnNames.length];
        for(int i=0; i<columnNames.length; i++) {
            columnSelectors[i] = new SQLColumnSelector(columnNames[i]);
        }

        this.selectedColumns.addAll(Arrays.asList(columnSelectors));
        return this;
    }

    /**
     * Specify from which table to select the listed columns.
     * @param tableName The table's name from which to select the columns from
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder from(String tableName) {
        this.selectedTables.add(new SQLTableSelector(tableName));
        return this;
    }

    /**
     * Specify from which tables to select listed columns.
     * @param tableSelectors List of SQL tables selectors to apply
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder from(SQLTableSelector... tableSelectors) {
        this.selectedTables.addAll(Arrays.asList(tableSelectors));
        return this;
    }

    /**
     * Apply a WHERE constraint.
     * @param columnName The name of the column to compare
     * @param operator The relational operator (=comparing operator) to apply
     * @param value The value to compare the column's value for
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder where(String columnName, SQLRelationalOperator operator, String value) {
        this.whereConstraints.add(
                new SQLWhereConstraint(new SQLColumnSelector(columnName), value, operator)
        );
        return this;
    }

    /**
     * Apply a WHERE constraint.
     * @param columnSelector The SQL column selector to compare
     * @param operator The relational operator (=comparing operator) to apply
     * @param value The value to compare the column's value for
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder where(SQLColumnSelector columnSelector, SQLRelationalOperator operator, String value) {
        this.whereConstraints.add(
                new SQLWhereConstraint(columnSelector, value, operator)
        );
        return this;
    }

    /**
     * Apply a table join.
     * @param srcJoinRelation The table-column relation which contains the foreign key
     * @param destJoinRelation The table-column relation which contains the primary key as join parameter
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder join(
            SQLJoinKey srcJoinRelation,
            SQLJoinKey destJoinRelation
    ) {
        this.joinConstraints.add(new SQLJoinConstraint(srcJoinRelation, destJoinRelation));
        return this;
    }

    /**
     * Apply a table join.
     * @param srcTableName The name of table which should be extended
     * @param srcColumnName The name of the source value's column
     * @param destTableName The name of the table which should be joined
     * @param destColumnName The name of the destination value's column
     * @return The current builder instance
     */
    public SQLSelectQueryBuilder join(
            String srcTableName, String srcColumnName,
            String destTableName, String destColumnName
    ) {
        this.joinConstraints.add(new SQLJoinConstraint(
                new SQLJoinKey(srcTableName, srcColumnName),
                new SQLJoinKey(destTableName, destColumnName)
        ));
        return this;
    }


    @Override
    public String create() {
        StringBuilder stringStringBuilder = new StringBuilder();
        stringStringBuilder.append("select ");

        for(int i = 0; i<this.selectedColumns.size(); i++) {
            if(i>0) {
                stringStringBuilder.append(", ");
            }
            stringStringBuilder.append(this.selectedColumns.get(i).create());
        }

        stringStringBuilder.append(" from ");
        for(int i = 0; i<this.selectedTables.size(); i++) {
            if(i>0) {
                stringStringBuilder.append(", ");
            }
            stringStringBuilder.append(this.selectedTables.get(i).create());
        }

        for(SQLJoinConstraint innerJoinConstraint : this.joinConstraints) {
            stringStringBuilder.append(innerJoinConstraint.create());
        }

        if(this.whereConstraints.size() > 0) {
            stringStringBuilder.append(" where ");
            for(int i = 0; i<this.whereConstraints.size(); i++) {
                if(i>0) {
                    stringStringBuilder.append(" and ");
                }
                stringStringBuilder.append(this.whereConstraints.get(i).create());
            }
        }

        String query = stringStringBuilder.toString();
        return query;
    }

    /**
     * Abstract base class representing a key-alias pair which can be used for
     * SQL table and column definitions.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private static abstract class SQLSelectorAliasPair implements ISQLQueryBuilder {
        /**
         * The defined selector (here: the SQL table or column name)
         */
        String selector;

        /**
         * The used alias
         */
        String alias;

        /**
         * Constructor.
         * @param selector The defined selector (here: the SQL table or column name)
         * @param alias The used alias
         */
        SQLSelectorAliasPair(String selector, String alias) {
            this.selector = selector;
            this.alias = alias;
        }
    }

    /**
     * Represents a basic SQL column selector which defines a column including it's referenced table
     * defined by a table alias.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class SQLColumnSelector extends SQLSelectorAliasPair {

        /**
         * Constructor.
         * @param columnName The column's name which to select
         */
        public SQLColumnSelector(String columnName) {
            super(columnName, "");
        }

        /**
         * Constructor.
         * @param tableAlias The SQL table alias from which to select the column
         * @param columnName The column's name which to select
         */
        public SQLColumnSelector(String tableAlias, String columnName) {
            super(columnName, tableAlias);
        }

        @Override
        public String create() {
            return (this.alias.isEmpty() ? "" :this.alias + ".") +
                    this.selector;
        }
    }

    /**
     * Represents a basic SQL table selector which defines a table
     * including it's set alias name.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class SQLTableSelector extends SQLSelectorAliasPair {

        /**
         * Constructor.
         * @param tableName The table's name to select columns from
         */
        public SQLTableSelector(String tableName) {
            super(tableName, "");
        }

        /**
         * Constructor
         * @param tableAlias The table's alias name
         * @param tableName The table's name to select columns from
         */
        public SQLTableSelector(String tableAlias, String tableName) {
            super(tableName, tableAlias);
        }

        @Override
        public String create() {
            return this.selector +
                    (this.alias.isEmpty() ? "" : " " + this.alias);
        }
    }

    /**
     * Represents a basic SQL join key which is defines by a column name and
     * the name of it's corresponding table.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class SQLJoinKey extends SQLSelectorAliasPair {

        /**
         * Constructor.
         * @param tableName The table's name
         * @param columnName The table's column
         */
        public SQLJoinKey(String tableName, String columnName) {
            super(tableName, columnName);
        }

        /**
         * Get the table's name.
         * @return The table's name
         */
        public String getTableName() {
            return this.selector;
        }

        /**
         * Get the column's name.
         * @return The column's name
         */
        public String getColumnName() {
            return this.alias;
        }

        @Override
        public String create() {
            return this.selector + "." + this.alias;
        }
    }

    /**
     * Represents a basic SQL join constraint.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    public static class SQLJoinConstraint implements ISQLQueryBuilder {

        /**
         * The SQL join's source key: The table-column relation which to extend
         */
        public SQLJoinKey srcJoinKey;

        /**
         * The SQL join's destination key: The table-column relation which to join
         */
        public SQLJoinKey destJoinKey;

        /**
         * Constructor.
         * @param srcJoinKey The SQL join's source key: The table-column relation which to extend
         * @param destJoinKey The SQL join's destination key: The table-column relation which to join
         */
        public SQLJoinConstraint(SQLJoinKey srcJoinKey, SQLJoinKey destJoinKey) {
            this.srcJoinKey = srcJoinKey;
            this.destJoinKey = destJoinKey;
        }

        @Override
        public String create() {
            return " left join " + this.destJoinKey.getTableName() +
                    " on " + this.srcJoinKey.create() + " = " + this.destJoinKey.create();
        }
    }

    /**
     * Represents a basic SQL WHERE constraint.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private class SQLWhereConstraint implements ISQLQueryBuilder {
        /**
         * The SQL column selector to apply the WHERE constraint for
         */
        private SQLColumnSelector columnSelector;

        /**
         * The value to compare the specified column with
         */
        private String value;

        /**
         * The relational comparison operator to apply
         */
        private SQLRelationalOperator operator;

        /**
         * Constructor.
         * @param columnSelector The SQL column selector to apply the WHERE constraint for
         * @param value The value to compare the specified column with
         * @param operator The relational comparison operator to apply
         */
        SQLWhereConstraint(SQLColumnSelector columnSelector, String value, SQLRelationalOperator operator) {
            this.columnSelector = columnSelector;
            this.value = value;
            this.operator = operator;
        }

        @Override
        public String create() {
            return this.columnSelector.create() + " " + this.operator.string + " '" + this.value + "'";
        }
    }
}