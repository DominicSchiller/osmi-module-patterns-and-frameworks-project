package de.thb.paf.scrabblefactory.models.hud;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of all available hud systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum HUDSystemType {

    @SerializedName("single-player")
    SINGLE_PLAYER_HUD(1);

    /**
     * Constructor
     * @param id
     */
    HUDSystemType(int id) {
        this.id = id;
    }

    /**
     * The hud systems unique identifier
     */
    public final int id;
}
