package de.thb.paf.scrabblefactory.persistence;

import java.util.List;

import de.thb.paf.scrabblefactory.persistence.entities.IDBEntity;
import de.thb.paf.scrabblefactory.persistence.entities.User;
import de.thb.paf.scrabblefactory.persistence.sql.builder.SQLQuery;
import de.thb.paf.scrabblefactory.persistence.sqlite.ISQLiteDatabase;
import de.thb.paf.scrabblefactory.persistence.sqlite.SQLiteDatabaseFactory;
import de.thb.paf.scrabblefactory.settings.Settings;

import static de.thb.paf.scrabblefactory.persistence.sql.builder.SQLRelationalOperator.*;

/**
 * The game's global data store used to persist data with.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class DataStore implements IUserCRUDOperations {

    /**
     * The singleton instance of the DataStore
     */
    private static DataStore instance;

    /**
     * The SQLite database reference
     */
    private ISQLiteDatabase database;

    /**
     * Default Constructor
     */
    private DataStore() {
        this.database = SQLiteDatabaseFactory.getDatabase(
                Settings.Database.DATABASE_NAME,
                Settings.Database.DATABASE_VERSION,
                SQLDDLHelper.getOnCreateDatabaseQuery(),
                null
        );
    }

    /**
     * Get the global DataStore instance.
     * @return The global data store instance
     */
    public static DataStore getInstance() {
        // lazy loading
        if(instance == null) {
            instance = new DataStore();
        }

        return instance;
    }

    @Override
    public User createUser(User user) {
        String selectGenderQuery = SQLQuery.select(DBInfo.Gender.Columns.GENDER_ID)
                .from(DBInfo.Gender.TABLE_NAME)
                .where(DBInfo.Gender.Columns.SHORTCUT, EQUAL_TO, user.getGender().getShortcut())
                .create();

        String insertUserQuery = SQLQuery.insertInto(DBInfo.Users.TABLE_NAME)
                .insertValue(DBInfo.Users.Columns.NAME, user.getName())
                .insertValue(DBInfo.Users.Columns.FIRST_NAME, user.getFirstName())
                .insertValue(DBInfo.Users.Columns.NICKNAME, user.getNickname())
                .insertValue(DBInfo.Users.Columns.DATE_OF_BIRTH, "" + (user.getDateOfBirth().getTime()))
                .insertValue(DBInfo.Users.Columns.GENDER_ID, selectGenderQuery)
                .create();
        user = (User)this.database.executeInsertOrUpdate(insertUserQuery, user);
        return user;
    }

    @Override
    public User readUser(String nickname, String password) {
        String selectUserQuery = SQLQuery.select("*")
                .from(DBInfo.Users.TABLE_NAME)
                .join(
                        DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.GENDER_ID,
                        DBInfo.Gender.TABLE_NAME, DBInfo.Gender.Columns.GENDER_ID
                )
                .where(DBInfo.Users.Columns.NICKNAME, EQUAL_TO, nickname)
                .create();

        List<IDBEntity> readUsers = database.executeSelect(selectUserQuery, User.class);
        if(readUsers.size() > 0) {
            return (User)readUsers.get(0);
        }
        return null;
    }
}
