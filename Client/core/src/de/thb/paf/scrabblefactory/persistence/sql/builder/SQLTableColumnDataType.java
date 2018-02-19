package de.thb.paf.scrabblefactory.persistence.sql.builder;


/**
 * Enumeration of supported SQL data types.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum SQLTableColumnDataType {
    INTEGER("integer"),
    TEXT("text"),
    REAL("real");

    /**
     * Private Constructor
     * @param string The data type's SQL string representation
     */
    SQLTableColumnDataType(String string) {
        this.string = string;
    }

    /**
     * The data type's SQL string representation
     */
    public final String string;
}
