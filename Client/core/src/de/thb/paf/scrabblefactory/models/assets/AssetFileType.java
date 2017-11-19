package de.thb.paf.scrabblefactory.models.assets;

/**
 * Enumeration of all supported asset file types.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum AssetFileType {
    JSON(".json"),
    TEXTURE_ATLAS(".atlas"),
    TRUE_TYPE_FONT(".ttf");

    /**
     * Private Constructor
     * @param fileEnding The associated file ending
     */
    AssetFileType(String fileEnding) {
        this.fileEnding = fileEnding;
    }

    /**
     * The associated file ending
     */
    public final String fileEnding;
}
