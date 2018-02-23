package de.thb.paf.scrabblefactory.persistence.entities;


import java.util.Date;

/**
 * Represents mapping-class for the database entity 'User'.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class User implements IDBEntity {

    /**
     * The user's unique id
     */
    private int userID;

    /**
     * The user's name
     */
    private String name;

    /**
     * The user's first name
     */
    private String firstname;

    /**
     * The user's nickname
     */
    private String nickname;

    /**
     * The user's set password
     */
    private String password;

    /**
     * The user's date of birth
     */
    private Date dateOfBirth;

    /**
     * The user's gender
     */
    private Gender gender;

    /**
     * Default Constructor.
     */
    public User() {
        this.userID = -1;
        this.name = "";
        this.firstname = "";
        this.nickname = "";
        this.dateOfBirth = null;
        this.gender = null;
    }

    /**
     * Constructor.
     * @param name The user's name
     * @param firstname The user's first name
     * @param nickname The user's nickname
     * @param dateOfBirth The user's date of birth
     * @param gender The user's gender
     */
    public User(String name, String firstname, String nickname, Date dateOfBirth, Gender gender) {
        this.userID = -1;
        this.name = name;
        this.firstname = firstname;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    /**
     * Get the user's name.
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the user's first name.
     * @return The user's first name.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Get the user's nickname.
     * @return The user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the user's date of birth.
     * @return The user's date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Get the user's gender.
     * @return The user's gender
     */
    public Gender getGender() {
        return gender;
    }

    @Override
    public int getID() {
        return this.userID;
    }

    @Override
    public void setID(int id) {
        this.userID = id;
    }
}
