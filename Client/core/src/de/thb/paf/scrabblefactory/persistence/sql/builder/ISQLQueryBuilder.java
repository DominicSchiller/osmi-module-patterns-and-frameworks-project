package de.thb.paf.scrabblefactory.persistence.sql.builder;


/**
 * Interface that declares methods a SQLite query builder instance must implement in order
 * to get used correctly by the scrabble factory game engine.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface ISQLQueryBuilder {

    /**
     * Create SQL query string.
     * @return The SQL query string
     */
    String create();
}
