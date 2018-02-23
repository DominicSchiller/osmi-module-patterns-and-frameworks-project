package de.thb.paf.scrabblefactory.persistence;


import de.thb.paf.scrabblefactory.persistence.entities.Gender;

/**
 * Defines supported CRUD-operations dedicated to the 'Gender' entity.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IGenderCRUDOperations {

    /**
     * Create a new gender entry on the database.
     * @param gender The gender to add to the database
     * @return The added gender extended by a unique id retrieved from the database
     */
    Gender createGender(Gender gender);

    /**
     * Read a gender entry from the database.
     * @param shortcut The gender's shortcut as unique identifier
     * @return The found gender entry
     */
    Gender readGender(String shortcut);
}
