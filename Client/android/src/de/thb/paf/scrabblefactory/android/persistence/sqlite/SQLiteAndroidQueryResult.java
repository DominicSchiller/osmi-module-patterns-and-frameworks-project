package de.thb.paf.scrabblefactory.android.persistence.sqlite;

import android.database.Cursor;

import java.sql.SQLException;

import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteQueryResult;


/**
 * A concrete SQL query result dedicated to Android systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLiteAndroidQueryResult implements ISQLiteQueryResult {

    /**
     * The Android-Cursor containing a SQL query's result set
     */
    private Cursor cursor;

    /**
     * Constructor.
     * @param cursor The Android-Cursor containing a SQL query's result set
     */
    public SQLiteAndroidQueryResult(Cursor cursor) {
        this.cursor = cursor;
        this.cursor.moveToFirst();
    }

    @Override
    public int getInt(String columnName) throws SQLException {
        int index = this.cursor.getColumnIndex(columnName);
        int value = index > -1 ? this.cursor.getInt(index): -1;
        return value;
    }

    @Override
    public long getLong(String columnName) throws SQLException {
        int index = this.cursor.getColumnIndex(columnName);
        long value = index > -1 ? this.cursor.getLong(index) : -1;
        return value;
    }

    @Override
    public String getString(String columnName) throws SQLException {
        int index = this.cursor.getColumnIndex(columnName);
        String value = index > -1 ? this.cursor.getString(index) : "";
        return value;
    }

    @Override
    public int getCount() {
        return this.cursor.getCount();
    }

    @Override
    public boolean hasNext() {
        return this.cursor.moveToNext();
    }

    @Override
    public void close() {
        this.cursor.close();
    }
}
