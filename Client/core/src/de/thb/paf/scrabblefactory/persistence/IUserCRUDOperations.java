package de.thb.paf.scrabblefactory.persistence;


import java.util.List;

import de.thb.paf.scrabblefactory.persistence.entities.User;

/**
 * Defines supported CRUD-operations dedicated to the 'User' entity.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IUserCRUDOperations {

    /**
     * Create a new user entry on the database.
     * @param user The user to add to the database
     * @param password The user's password to store
     * @return The added user extended by a unique id retrieved from the database
     */
    User createUser(User user, String password);

    /**
     * Read a specific user entry from the database.
     * @param userID The user's unique id
     * @return The found user database entry
     */
    User readUser(int userID);

    /**
     * Read a specific user entry from the database.
     * @param nickname The user's nickname
     * @param password The user's password
     * @return The found user
     */
    User readUser(String nickname, String password);

    /**
     * Read all available users from the database.
     * @return List of found users
     */
    List<User> readAllUsers();
}
