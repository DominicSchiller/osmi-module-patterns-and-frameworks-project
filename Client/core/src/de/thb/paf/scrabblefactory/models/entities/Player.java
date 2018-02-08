package de.thb.paf.scrabblefactory.models.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player entity.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class Player extends GameEntity {

    /**
     * List of carrying cheese items
     */
    private List<Cheese> cheeseItems;

    /**
     * The player's name
     */
    private String name;

    /**
     * Default Constructor
     */
    public Player() {
        super();
        this.cheeseItems = new ArrayList<>();
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     */
    public Player(int id, EntityType type) {
        super(id, type);
        this.cheeseItems = new ArrayList<>();
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

    /**
     * Get the list of carrying cheese items.
     * @return The list of carrying cheese items
     */
    public List<Cheese> getCheeseItems() {
        return this.cheeseItems;
    }

    /**
     * Add a cheese item to the list of carrying cheese items.
     * @param cheese The cheese item to add
     */
    public void addCheeseItem(Cheese cheese) {
        if(!this.cheeseItems.contains(cheese)) {
            cheese.setCarrier(this);
            this.cheeseItems.add(cheese);
        }
    }

    /**
     * Remove a cheese item from the list of carrying cheese items.
     * @param cheese The cheese item to remove
     * @return The remove success status
     */
    public boolean removeCheeseItem(Cheese cheese) {
        cheese.setCarrier(null);
        return this.cheeseItems.remove(cheese);
    }
}