package de.thb.paf.scrabblefactory.models.entities;

/**
 * Represents a player entity.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class Player extends GameEntity {

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * Constructor
     * @param name The player's name
     */
    public Player(String name) {
    }

    /**
     * The player's name
     */
    private String name;

    /**
     * Get the player's name.
     * @return The player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the player's name.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

}