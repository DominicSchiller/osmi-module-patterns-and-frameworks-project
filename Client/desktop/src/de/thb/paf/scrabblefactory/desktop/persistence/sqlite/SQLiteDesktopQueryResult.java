package de.thb.paf.scrabblefactory.desktop.persistence.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteQueryResult;


/**
 * A concrete SQL query result dedicated to desktop systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLiteDesktopQueryResult implements ISQLiteQueryResult {

    /**
     * The JDBC-ResultSet containing a SQL query's result set
     */
    private ResultSet resultSet;

    /**
     * Constructor.
     * @param resultSet The JDBC-ResultSet containing a SQL query's result set
     */
    public SQLiteDesktopQueryResult(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public int getInt(String columnName) throws SQLException {
        return this.resultSet.getInt(columnName);
    }

    @Override
    public long getLong(String columnName) throws SQLException {
        return this.resultSet.getLong(columnName);
    }

    @Override
    public String getString(String columnName) throws SQLException {
        return this.resultSet.getString(columnName);
    }

    @Override
    public int getCount() {
        int count = -1;
        try {
            this.resultSet.last();
            count = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean hasNext() {
        try {
            return this.resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() {
        try {
            this.resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
