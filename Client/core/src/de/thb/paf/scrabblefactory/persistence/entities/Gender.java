package de.thb.paf.scrabblefactory.persistence.entities;


/**
 * Represents mapping-class for the database entity 'Gender'.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class Gender implements IDBEntity {

    /**
     * The unique id
     */
    private int genderID;

    /**
     * The unique gender shortcut
     */
    private String shortcut;

    /**
     * The gender's description (full text)
     */
    private String description;

    /**
     * Default Constructor.
     */
    public Gender() {
        this.genderID = -1;
        this.shortcut = "";
        this.description = "";
    }

    /**
     * Constructor.
     * @param shortcut The unique gender shortcut
     * @param description The gender's description (full text)
     */
    public Gender(String shortcut, String description) {
        this.shortcut = shortcut;
        this.description = description;
    }

    /**
     * Get the gender's shortcut.
     * @return The gender's shortcut
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * Get the gender's description.
     * @return The gender's description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int getID() {
        return this.genderID;
    }

    @Override
    public void setID(int id) {
        this.genderID = id;
    }
}
