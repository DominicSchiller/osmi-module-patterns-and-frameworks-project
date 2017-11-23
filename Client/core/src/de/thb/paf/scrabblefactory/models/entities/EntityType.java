package de.thb.paf.scrabblefactory.models.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of available game's entity types.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public enum EntityType {
    @SerializedName("player")
    PLAYER,
    @SerializedName("cheese")
    CHEESE
}