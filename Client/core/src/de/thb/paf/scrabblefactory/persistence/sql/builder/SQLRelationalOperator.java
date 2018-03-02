package de.thb.paf.scrabblefactory.persistence.sql.builder;


/**
 * Enumeration of supported SQL relational comparison operators.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum SQLRelationalOperator {
    EQUAL_TO("="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    NOT_EQUAL_TO("!="),
    GREATER_THAN_OR_EQUAL_TO(">="),
    LESS_THAN_OR_EQUAL_TO("<=");

    SQLRelationalOperator(String string) {
        this.string = string;
    }

    public final String string;
}
