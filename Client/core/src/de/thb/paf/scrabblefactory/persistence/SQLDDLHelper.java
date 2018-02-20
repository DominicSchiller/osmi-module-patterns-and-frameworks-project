package de.thb.paf.scrabblefactory.persistence;

import de.thb.paf.scrabblefactory.persistence.sql.builder.SQLQuery;

import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLTableColumnConstraintType.NOT_NULL;
import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLTableColumnConstraintType.PRIMARY_KEY;
import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLTableColumnConstraintType.UNIQUE;
import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLTableColumnDataType.INTEGER;
import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLTableColumnDataType.REAL;
import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLTableColumnDataType.TEXT;

/**
 * Static helper utility which offers the creation of massive SQL DDL queries.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SQLDDLHelper {

    /**
     * Create the DDL for creating the game's database structure.
     * @return The SQL query for creating the game's database structure
     */
    public static String getOnCreateDatabaseQuery() {
        String createUserTable = SQLQuery.createTable(DBInfo.Users.TABLE_NAME)
                .ifNotExisting()
                .withColumn(DBInfo.Users.Columns.USER_ID, INTEGER, UNIQUE, PRIMARY_KEY)
                .withColumn(DBInfo.Users.Columns.NAME, TEXT, NOT_NULL)
                .withColumn(DBInfo.Users.Columns.FIRST_NAME, TEXT, NOT_NULL)
                .withColumn(DBInfo.Users.Columns.NICKNAME, TEXT, UNIQUE, NOT_NULL)
                .withColumn(DBInfo.Users.Columns.DATE_OF_BIRTH, REAL)
                .withColumn(DBInfo.Users.Columns.GENDER_ID, INTEGER, NOT_NULL)
                .withForeignKey(DBInfo.Users.Columns.GENDER_ID, DBInfo.Gender.TABLE_NAME, DBInfo.Gender.Columns.GENDER_ID)
                .create() + "; ";

        String createGenderTable = SQLQuery.createTable(DBInfo.Gender.TABLE_NAME)
                .ifNotExisting()
                .withColumn(DBInfo.Gender.Columns.GENDER_ID, INTEGER, UNIQUE, PRIMARY_KEY)
                .withColumn(DBInfo.Gender.Columns.SHORTCUT, TEXT, UNIQUE)
                .withColumn(DBInfo.Gender.Columns.DESCRIPTION, TEXT)
                .withForeignKey(DBInfo.Users.Columns.GENDER_ID, DBInfo.Gender.TABLE_NAME, DBInfo.Gender.Columns.GENDER_ID)
                .create() + "; ";

        String createScoreTable = SQLQuery.createTable(DBInfo.Scores.TABLE_NAME)
                .ifNotExisting()
                .withColumn(DBInfo.Scores.Columns.SCORE_ID, INTEGER, UNIQUE, PRIMARY_KEY)
                .withColumn(DBInfo.Scores.Columns.SCORE, INTEGER)
                .create() + "; ";

        String createUserScoresTable = SQLQuery.createTable(DBInfo.UserScores.TABLE_NAME)
                .ifNotExisting()
                .withColumn(DBInfo.UserScores.Columns.USER_ID, INTEGER)
                .withColumn(DBInfo.UserScores.Columns.SCORE_ID, INTEGER)
                .withColumn(DBInfo.UserScores.Columns.CREATED_AT, REAL)
                .withColumn(DBInfo.UserScores.Columns.IS_SYNCHRONIZED, INTEGER)
                .withForeignKey(DBInfo.UserScores.Columns.USER_ID, DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.USER_ID)
                .withForeignKey(DBInfo.UserScores.Columns.SCORE_ID, DBInfo.Scores.TABLE_NAME, DBInfo.Scores.Columns.SCORE_ID)
                .create() + "; ";

        String createSaveGameTable = SQLQuery.createTable(DBInfo.Savegame.TABLE_NAME)
                .ifNotExisting()
                .withColumn(DBInfo.Savegame.Columns.SAVEGAME_ID, INTEGER, UNIQUE, PRIMARY_KEY)
                .withColumn(DBInfo.Savegame.Columns.USER_ID, INTEGER)
                .withColumn(DBInfo.Savegame.Columns.SNAPSHOT_DATA, TEXT)
                .withForeignKey(DBInfo.Savegame.Columns.USER_ID, DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.USER_ID)
                .create() + "; ";

        String insertMaleGender = SQLQuery.insertInto(DBInfo.Gender.TABLE_NAME)
                .insertValue(DBInfo.Gender.Columns.SHORTCUT, "m")
                .insertValue(DBInfo.Gender.Columns.DESCRIPTION, "male")
                .create() + "; ";

        String insertFemaleGender = SQLQuery.insertInto(DBInfo.Gender.TABLE_NAME)
                .insertValue(DBInfo.Gender.Columns.SHORTCUT, "f")
                .insertValue(DBInfo.Gender.Columns.DESCRIPTION, "female")
                .create();

        String query = createGenderTable + createUserTable + createScoreTable +
                createUserScoresTable + createSaveGameTable +
                insertMaleGender + insertFemaleGender;
        return query;
    }
}
