package de.thb.paf.scrabblefactory.models.assets;

/**
 * Enumeration of all available asset types which can be loaded from the game's asset directory.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum AssetType {
    CONFIG("configurations"),
    TEXTURE("textures"),
    FONT("fonts");

    /**
     * Private Constructor
     * @param path The asset type's associated root path
     */
    AssetType(String path) {
        this.path = path;
    }

    /**
     * The asset type's associated root path
     */
    public final String path;
}
