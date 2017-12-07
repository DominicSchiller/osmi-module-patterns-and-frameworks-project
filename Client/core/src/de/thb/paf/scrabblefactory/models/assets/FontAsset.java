package de.thb.paf.scrabblefactory.models.assets;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of all available fonts.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum FontAsset {
    @SerializedName("porkys")
    PORKY("porkys"),
    @SerializedName("open-sans")
    OPEN_SANS("open-sans");

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
