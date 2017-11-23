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
     * The player's name
     */
    private String name;

    /**
     * Default Constructor
     */
    public Player() {
        super();
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     */
    public Player(int id, EntityType type) {
        super(id, type);
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     * @param name The player's name
     */
    public Player(int id, EntityType type, String name) {
        this(id, type);
        this.name = name;
    }

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