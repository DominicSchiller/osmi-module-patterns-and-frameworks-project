package de.thb.paf.scrabblefactory.models.data;

/**
 * Represents the Highscore List Model
 *
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class Highscore {

        private String name = "Dummy Text Highscores";
        private int score = 0;
        private int date = 1;

        public Highscore(String name, int date, int score){
            this.name = name;
            this.date = date;
            this.score = score;
        }

        public String getHighscoreInput(){
            return this.name + " / " + this.score + " / " + this.date;
        }

        public String getName(){
            return this.name;
        }

        public int getDate(){
        return this.date;
        }

        public int getScore(){
            return this.score;
        }

    }
