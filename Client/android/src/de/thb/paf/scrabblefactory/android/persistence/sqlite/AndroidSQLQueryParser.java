package de.thb.paf.scrabblefactory.android.persistence.sqlite;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic SQL query parser which parses certain types of SQL queries to
 * an Android-compliant SQLite equivalent.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

class AndroidSQLQueryParser {

    /**
     * Parse an SQL insert query string to an Android-compliant equivalent.
     * @param sql The SQL-query string to parse
     * @return The android-compliant SQL insert statement
     */
    static AndroidSQLInsertStatement parseSQLInsert(String sql) {
        int indexColumnKeysBegin = sql.indexOf('(');
        int indexColumnsEnd = sql.indexOf(')');
        int indexValuesBegin = sql.indexOf("values ", indexColumnsEnd) + 8;
        String[] columnKeys = sql.substring(indexColumnKeysBegin + 1, indexColumnsEnd).split(", ");
        String[] values = sql.substring(indexValuesBegin, sql.indexOf(')', indexValuesBegin)).split(", ");
        String tableName = sql.substring(sql.indexOf("insert into ") + 12, indexColumnKeysBegin);

        ContentValues contentValues = new ContentValues();
        for(int i=0; i<columnKeys.length; i++) {
            contentValues.put(columnKeys[i], values[i].replace("'", "").replace("(select", "select"));
        }

        return new AndroidSQLInsertStatement(tableName, contentValues);
    }

    /**
     * Parse an SQL select query string to an Android-compliant equivalent.
     * @param sql The SQL-query string to parse
     * @return The android-compliant SQL select statement
     */
    static AndroidSQLSelectStatement parseSQLSelect(String sql) {
        List<String> values = new ArrayList<>();
        List<String> whereConstraintKeys = new ArrayList<>();

        if(sql.contains("where")) {
            int whereBeginIndex = sql.indexOf(" where ") + 7;
            String rawWhereConstraints = sql.substring(sql.indexOf(" where ") + 7);
            String[] whereConstraints = rawWhereConstraints.split(" and ");

            sql = sql.substring(0, whereBeginIndex);

            for(String whereConstraint : whereConstraints) {
                String constraint = whereConstraint.replace(" = ", "=");
                String[] constraintParts = constraint.split("=");
                values.add(constraintParts[1].replace("'", ""));
                whereConstraintKeys.add(constraintParts[0]+"=?");
            }

            for(int i=0; i<whereConstraintKeys.size(); i++) {
                if(i != 0) {
                    sql += " and ";
                }

                sql += whereConstraintKeys.get(i);
            }
            sql = sql.replace(" = ", "=");
        }
        return new AndroidSQLSelectStatement(sql, values);
    }
}

/**
 * Represents SQL select statement which contains all information a
 * Android-compliant SQLite select statement must contain.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
class AndroidSQLSelectStatement {

    /**
     * The raw SQL select query string
     */
    final String sql;

    /**
     * List of WHERE selectors (ordered where constraint's values)
     */
    final String[] selectors;

    /**
     * Constructor.
     * @param sql The raw SQL select query string
     * @param selectors List of WHERE selectors (ordered where constraint's values)
     */
    AndroidSQLSelectStatement(String sql, List<String> selectors) {
        this.selectors = new String[selectors.size()];
        for(int i = 0; i< selectors.size(); i++) {
            this.selectors[i] = selectors.get(i);
        }
        this.sql = sql;
    }
}

/**
 * Represents SQL insert statement which contains all information a
 * Android-compliant SQLite insert statement must contain.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
class AndroidSQLInsertStatement {
    /**
     * The table's name to insert the selectors in
     */
    final String tableName;

    /**
     * The content selectors to insert (Pair of column name and value to put in)
     */
    final ContentValues contentValues;

    /**
     * Constructor.
     * @param tableName The table's name to insert the selectors in
     * @param contentValues The content selectors to insert (Pair of column name and value to put in)
     */
    AndroidSQLInsertStatement(String tableName, ContentValues contentValues) {
        this.tableName = tableName;
        this.contentValues = contentValues;
    }
}
