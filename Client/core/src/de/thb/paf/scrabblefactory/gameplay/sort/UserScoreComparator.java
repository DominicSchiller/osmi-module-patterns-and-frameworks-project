package de.thb.paf.scrabblefactory.gameplay.sort;


import java.util.Comparator;

import de.thb.paf.scrabblefactory.persistence.entities.UserScore;

/**
 * Basic comparator to sort UserScores in descending order.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class UserScoreComparator implements Comparator<UserScore> {


    @Override
    public int compare(UserScore userScore1, UserScore userScore2) {
        return userScore1.getScore().getScore()
                > userScore2.getScore().getScore() ? -1 : 1;
    }
}
