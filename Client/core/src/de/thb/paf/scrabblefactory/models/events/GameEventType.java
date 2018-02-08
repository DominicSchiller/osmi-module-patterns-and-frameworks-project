package de.thb.paf.scrabblefactory.models.events;


import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of available game events.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public enum GameEventType {
    @SerializedName("move")
    MOVE,
    @SerializedName("ground-contact")
    GROUND_CONTACT,
    @SerializedName("item-contact")
    ITEM_CONTACT
}