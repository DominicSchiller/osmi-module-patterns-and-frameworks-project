package de.thb.paf.scrabblefactory.models.assets;

/**
 * Enumerations of available fonts.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum FontAsset {
    PORKY("porkys"),
    SAN_FRANCISCO("san-francisco");

    /**
     * Private Constructor
     * @param fileName The font asset's file name
     */
    private FontAsset(String fileName) {
        this.fileName = fileName;
    }

    /**
     * The font asset's file name
     */
    public final String fileName;
}
