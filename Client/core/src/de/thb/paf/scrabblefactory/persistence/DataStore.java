package de.thb.paf.scrabblefactory.persistence;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.persistence.entities.Gender;
import de.thb.paf.scrabblefactory.persistence.entities.IDBEntity;
import de.thb.paf.scrabblefactory.persistence.entities.Score;
import de.thb.paf.scrabblefactory.persistence.entities.User;
import de.thb.paf.scrabblefactory.persistence.entities.UserScore;
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

public class DataStore implements IUserCRUDOperations,
        IGenderCRUDOperations, IScoreCRUDOperations, IUserScoreCRUDOperations {

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
    public User createUser(User user, String password) {
        Gender gender = this.readGender(user.getGender().getShortcut());

        String insertQuery = SQLQuery.insertInto(DBInfo.Users.TABLE_NAME)
                .insertValue(DBInfo.Users.Columns.NAME, user.getName())
                .insertValue(DBInfo.Users.Columns.FIRST_NAME, user.getFirstname())
                .insertValue(DBInfo.Users.Columns.NICKNAME, user.getNickname())
                .insertValue(DBInfo.Users.Columns.PASSWORD, password)
                .insertValue(DBInfo.Users.Columns.DATE_OF_BIRTH, "" + (user.getDateOfBirth().getTime()))
                .insertValue(DBInfo.Users.Columns.GENDER_ID, "" + gender.getID())
                .create();
        user = (User)this.database.executeInsertOrUpdate(insertQuery, user);
        return user;
    }

    @Override
    public User readUser(int userID) {
        String selectQuery = SQLQuery.select("*")
                .from(DBInfo.Users.TABLE_NAME)
                .join(
                        DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.GENDER_ID,
                        DBInfo.Gender.TABLE_NAME, DBInfo.Gender.Columns.GENDER_ID
                )
                .where(DBInfo.Users.Columns.USER_ID, EQUAL_TO, "" + userID)
                .create();

        List<IDBEntity> readUsers = database.executeSelect(selectQuery, User.class);
        if(readUsers.size() > 0) {
            return (User)readUsers.get(0);
        }

        return null;
    }

    @Override
    public User readUser(String nickname, String password) {
        String selectQuery = SQLQuery.select("*")
                .from(DBInfo.Users.TABLE_NAME)
                .join(
                        DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.GENDER_ID,
                        DBInfo.Gender.TABLE_NAME, DBInfo.Gender.Columns.GENDER_ID
                )
                .where(DBInfo.Users.Columns.NICKNAME, EQUAL_TO, nickname)
                .where(DBInfo.Users.Columns.PASSWORD, EQUAL_TO, password)
                .create();

        List<IDBEntity> readUsers = database.executeSelect(selectQuery, User.class);
        if(readUsers.size() > 0) {
            return (User)readUsers.get(0);
        }

        return null;
    }

    @Override
    public List<User> readAllUsers() {
        String selectAllQuery = SQLQuery.select("*")
                .from(DBInfo.Users.TABLE_NAME)
                .join(
                        DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.GENDER_ID,
                        DBInfo.Gender.TABLE_NAME, DBInfo.Gender.Columns.GENDER_ID
                )
                .create();

        List<IDBEntity> readUsers = database.executeSelect(selectAllQuery, User.class);
        if(readUsers != null) {
            List<User> users = new ArrayList<>();
            for(IDBEntity entity : readUsers) {
                users.add((User)entity);
            }
            return users;
        }

        return null;
    }

    @Override
    public Gender createGender(Gender gender) {
        String insertQuery = SQLQuery.insertInto(DBInfo.Gender.TABLE_NAME)
                .insertValue(DBInfo.Gender.Columns.SHORTCUT, gender.getShortcut())
                .insertValue(DBInfo.Gender.Columns.DESCRIPTION, gender.getDescription())
                .create();

        gender = (Gender)this.database.executeInsertOrUpdate(insertQuery, gender);
        return gender;
    }

    @Override
    public Gender readGender(String shortcut) {
        String selectQuery = SQLQuery.select(DBInfo.Gender.Columns.ALL_COLUMNS)
                .from(DBInfo.Gender.TABLE_NAME)
                .where(DBInfo.Gender.Columns.SHORTCUT, EQUAL_TO, shortcut)
                .create();

        List<IDBEntity> readGenders = this.database.executeSelect(selectQuery, Gender.class);
        if(readGenders.size() > 0) {
            return (Gender)readGenders.get(0);
        }

        return null;
    }

    @Override
    public Score createScore(Score score) {
        String insertQuery = SQLQuery.insertInto(DBInfo.Scores.TABLE_NAME)
                .insertValue(DBInfo.Scores.Columns.SCORE, "" + score.getScore())
                .create();
        score = (Score)this.database.executeInsertOrUpdate(insertQuery, score);
        return score;
    }

    @Override
    public Score readScore(int scoreID) {
        String selectQuery = SQLQuery.select("*")
                .from(DBInfo.Scores.TABLE_NAME)
                .where(DBInfo.Scores.Columns.SCORE_ID, EQUAL_TO, "" + scoreID)
                .create();

        List<IDBEntity> readUsers = database.executeSelect(selectQuery, Score.class);
        if(readUsers.size() > 0) {
            return (Score) readUsers.get(0);
        }

        return null;
    }

    @Override
    public List<Score> readAllScores() {
        String selectAll = SQLQuery.select("*")
                .from(DBInfo.Scores.TABLE_NAME)
                .create();

        List<IDBEntity> readScores = database.executeSelect(selectAll, Score.class);
        if(readScores != null) {
            List<Score> scores = new ArrayList<>();
            for(IDBEntity entity : readScores) {
                scores.add((Score)entity);
            }
            return scores;
        }

        return null;
    }

    @Override
    public UserScore createUserScore(UserScore userScore) {

        Score score = this.readScore(userScore.getScore().getID());
        if(score == null) {
            score = this.createScore(userScore.getScore());
        }

        User user = userScore.getUser();
        if(user.getID() <= 0) {
            user = this.readUser(user.getNickname(), "");
        }

        String insertQuery = SQLQuery.insertInto(DBInfo.UserScores.TABLE_NAME)
                .insertValue(DBInfo.UserScores.Columns.USER_ID, "" + user.getID())
                .insertValue(DBInfo.UserScores.Columns.SCORE_ID, "" + score.getID())
                .insertValue(DBInfo.UserScores.Columns.CREATED_AT, "" + userScore.getCreatedAt().getTime())
                .insertValue(DBInfo.UserScores.Columns.IS_SYNCHRONIZED, "" + userScore.isSynchronized())
                .create();
        userScore = (UserScore)this.database.executeInsertOrUpdate(insertQuery, userScore);
        return userScore;
    }

    @Override
    public UserScore readUserScore(int userID, int scoreID) {
        String selectQuery = SQLQuery.select("*")
                .from(DBInfo.UserScores.TABLE_NAME)
                .join(
                        DBInfo.UserScores.TABLE_NAME, DBInfo.UserScores.Columns.USER_ID,
                        DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.USER_ID
                )
                .join(
                        DBInfo.UserScores.TABLE_NAME, DBInfo.UserScores.Columns.SCORE_ID,
                        DBInfo.Scores.TABLE_NAME, DBInfo.Scores.Columns.SCORE_ID
                )
                .where(DBInfo.UserScores.Columns.USER_ID, EQUAL_TO, "" + userID)
                .where(DBInfo.UserScores.Columns.SCORE_ID, EQUAL_TO, "" + scoreID)
                .create();

        List<IDBEntity> readUserScores = database.executeSelect(selectQuery, UserScore.class);
        if(readUserScores.size() > 0) {
            return (UserScore) readUserScores.get(0);
        }

        return null;
    }

    @Override
    public List<UserScore> readAllUserScores() {
        String selectAllQuery = SQLQuery.select("*")
                .from(DBInfo.UserScores.TABLE_NAME)
                .join(
                        DBInfo.UserScores.TABLE_NAME, DBInfo.UserScores.Columns.USER_ID,
                        DBInfo.Users.TABLE_NAME, DBInfo.Users.Columns.USER_ID
                )
                .join(
                        DBInfo.UserScores.TABLE_NAME, DBInfo.UserScores.Columns.SCORE_ID,
                        DBInfo.Scores.TABLE_NAME, DBInfo.Scores.Columns.SCORE_ID
                )
                .create();

        List<IDBEntity> readUserScores = database.executeSelect(selectAllQuery, UserScore.class);
        if(readUserScores != null) {
            List<UserScore> userScores = new ArrayList<>();
            for(IDBEntity entity : readUserScores) {
                userScores.add((UserScore)entity);
            }
            return userScores;
        }

        return null;
    }
}
