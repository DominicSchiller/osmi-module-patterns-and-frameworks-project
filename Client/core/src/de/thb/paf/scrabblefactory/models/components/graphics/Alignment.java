package de.thb.paf.scrabblefactory.models.components.graphics;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of all supported screen alignment options.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum Alignment {
    @SerializedName("center center")
    MIDDLE,

    @SerializedName("center left")
    CENTER_LEFT,

    @SerializedName("center right")
    CENTER_RIGHT,

    @SerializedName("top left")
    TOP_LEFT,

    @SerializedName("top right")
    TOP_RIGHT,

    @SerializedName("top center")
    TOP_CENTER,

    @SerializedName("bottom left")
    BOTTOM_LEFT,

    @SerializedName("bottom right")
    BOTTOM_RIGHT,

    @SerializedName("bottom center")
    BOTTOM_CENTER,
}
