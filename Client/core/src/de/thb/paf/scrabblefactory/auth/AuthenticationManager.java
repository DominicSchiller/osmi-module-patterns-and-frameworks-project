package de.thb.paf.scrabblefactory.auth;


import de.thb.paf.scrabblefactory.persistence.DataStore;
import de.thb.paf.scrabblefactory.persistence.entities.User;

/**
 * Represents a basic authentication manager which handles login and logout processed as
 * well as a simple user management.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class AuthenticationManager {

    /**
     * The singleton instance of authentication manager
     */
    private static AuthenticationManager instance;

    /**
     * The currently logged-in user
     */
    private User currentUser;

    /**
     * Private Singleton Constructor
     */
    private AuthenticationManager() {

    }

    /**
     * Get the global authentication manager.
     * @return The global authentication manager instance
     */
    public static AuthenticationManager getInstance() {
        if(instance == null) {
            instance = new AuthenticationManager();
        }

        return instance;
    }

    /**
     * Login with user credentials.
     * @param nickname The user's nickname
     * @param password The user's password
     * @return The success status of the login attempt
     */
    public boolean login(String nickname, String password) {
        DataStore dataStore = DataStore.getInstance();
        User user = dataStore.readUser(nickname, password);

        if(user != null) {
            this.currentUser = user;
        }

        return user != null;
    }

    /**
     * Logout the current user.
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Get the current logged-in user.
     * @return The currently logged-in user.
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
}
