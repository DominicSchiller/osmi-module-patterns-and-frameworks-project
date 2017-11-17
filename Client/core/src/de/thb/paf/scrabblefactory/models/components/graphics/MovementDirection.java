package de.thb.paf.scrabblefactory.models.components.graphics;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of all available movement directions.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public enum MovementDirection {
    @SerializedName("up")
    UP,
    @SerializedName("down")
    DOWN,
    @SerializedName("left")
    LEFT,
    @SerializedName("right")
    RIGHT
}