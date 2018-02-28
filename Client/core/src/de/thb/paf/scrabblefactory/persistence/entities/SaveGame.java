package de.thb.paf.scrabblefactory.persistence.entities;


import java.util.List;

/**
 * Your description here...
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SaveGame {

    User[] users;
    Score[] scores;
    UserScore[] userScores;

    public SaveGame(List<User> users, List<Score> scores, List<UserScore> userScores) {
        this.users = users != null ?
                users.toArray(new User[users.size()]) : new User[0];
        this.scores = scores != null ?
                scores.toArray(new Score[scores.size()]) : new Score[0];
        this.userScores = userScores != null ?
                userScores.toArray(new UserScore[userScores.size()]) : new UserScore[0];
    }

    public User[] getUsers() {
        return this.users;
    }

    public Score[] getScores() {
        return this.scores;
    }

    public UserScore[] getUserScores() {
        return this.userScores;
    }
}
