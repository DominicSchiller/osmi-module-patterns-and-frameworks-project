package de.thb.paf.scrabblefactory.persistence.entities;

/**
 * This interface defines methods a database entity mapping class must implement
 * in order to get used correctly.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IDBEntity {

    /**
     * Get the entity's id.
     * @return The entity's id
     */
    int getID();

    /**
     * Set the entity's id.
     * @param id The id to apply
     */
    void setID(int id);
}
