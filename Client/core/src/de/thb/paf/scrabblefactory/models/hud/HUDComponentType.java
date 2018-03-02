package de.thb.paf.scrabblefactory.models.hud;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of all available HUD categories.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum HUDComponentType {
    @SerializedName("health")
    HEALTH_DISPLAY,
    @SerializedName("points")
    POINT_DISPLAY,
    @SerializedName("timer")
    TIMER_DISPLAY,
    @SerializedName("searchWord")
    SEARCH_WORD
}
