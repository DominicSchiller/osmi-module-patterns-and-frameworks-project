package de.thb.paf.scrabblefactory.models.assets;

/**
 * Enumeration of all available asset targets to load from the game's assets folder.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum AssetTargetType {
    LEVEL("level");

    /**
     * Private Constructor
     * @param path The associated asset's root path
     */
    AssetTargetType(String path) {
        this.path = path;
    }

    /**
     * The associated asset's root path
     */
    public final String path;
}
