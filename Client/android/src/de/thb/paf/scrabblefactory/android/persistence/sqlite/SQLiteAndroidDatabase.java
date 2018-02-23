package de.thb.paf.scrabblefactory.android.persistence.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.android.AndroidLauncher;
import de.thb.paf.scrabblefactory.persistence.entities.IDBEntity;
import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteDatabase;
import de.thb.paf.scrabblefactory.persistence.sqlite.SQLiteORMapper;


/**
 * Represents a SQLite database for android systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLiteAndroidDatabase implements ISQLiteDatabase {

    /**
     * The database's URL including the database's name
     */
    private String databaseURL;

    /**
     * The database's version
     */
    private int databaseVersion;

    /**
     * The SQL statement to execute when creating the database
     */
    private String onCreateQuery;

    /**
     * The SQL statement to execute when upgrading the database
     */
    private String onUpgradeQuery;

    /**
     * The Android SQLite database instance
     */
    private SQLiteDatabase database;

    /**
     * Constructor
     * @param databaseURL The database's URL including the database's name
     * @param databaseVersion The database's version
     * @param onCreateQuery The SQL statement to execute when creating the database
     * @param onUpgradeQuery The SQL statement to execute when upgrading the database
     */
    public SQLiteAndroidDatabase(
            String databaseURL,
            int databaseVersion,
            String onCreateQuery,
            String onUpgradeQuery
    ) {
        this.databaseURL = databaseURL;
        this.databaseVersion = databaseVersion;
        this.onCreateQuery = onCreateQuery;
        this.onUpgradeQuery = onUpgradeQuery;
    }

    @Override
    public void setup() {
        this.open();

        if(this.database.getVersion() == 0) {
            this.database.setVersion(this.databaseVersion);
            this.executeDDL(onCreateQuery);
        }

        this.close();
    }

    @Override
    public void open() {
        Context context = AndroidLauncher.getInstance().getApplicationContext();
        // initializes local SQLIte database based on the global application context (= app bundle)
        this.database = context.openOrCreateDatabase(
                this.databaseURL,
                Context.MODE_PRIVATE,
                null
        );
    }

    @Override
    public void executeDDL(String sql) {
        String[] queries = sql.split(";");
        for(String query : queries) {
            this.database.execSQL(query);
        }
    }

    @Override
    public IDBEntity executeInsertOrUpdate(String sql, IDBEntity entity) {
        AndroidSQLInsertStatement statement = AndroidSQLQueryParser.parseSQLInsert(sql);
        this.open();

        try {
            int id = (int)this.database.insert(statement.tableName,
                    null,
                    statement.contentValues
            );
            entity.setID(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        this.close();

        return entity;
    }

    @Override
    public List<IDBEntity> executeSelect(String sql, Class<?> entityType) {
        this.open();

        List<IDBEntity> selectedEntities = new ArrayList<>();
        AndroidSQLSelectStatement statement = AndroidSQLQueryParser.parseSQLSelect(sql);
        SQLiteAndroidQueryResult queryResult = new SQLiteAndroidQueryResult(
                this.database.rawQuery(statement.sql, statement.selectors)
        );

        if(queryResult.getCount() > 0) {
            do {
                Object entityInstance = SQLiteORMapper.createInstanceFromClassType(entityType);
                SQLiteORMapper.applyResultSet(queryResult, entityInstance);
                selectedEntities.add((IDBEntity)entityInstance);
            } while(queryResult.hasNext());
        }
        queryResult.close();
        this.close();

        return selectedEntities;
    }

    @Override
    public void close() {
        this.database.close();
    }
}
