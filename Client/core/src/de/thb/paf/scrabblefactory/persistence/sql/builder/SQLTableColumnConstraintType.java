package de.thb.paf.scrabblefactory.persistence.sql.builder;


/**
 * Enumeration of supported SQL constraints.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum SQLTableColumnConstraintType {
    UNIQUE("unique"),
    NOT_NULL("not null"),
    PRIMARY_KEY("primary key");

    /**
     * Private Constructor
     * @param string The key type's SQL string representation
     */
    SQLTableColumnConstraintType(String string) {
        this.string = string;
    }

    /**
     * The constraint type's SQL string representation
     */
    public final String string;
}
