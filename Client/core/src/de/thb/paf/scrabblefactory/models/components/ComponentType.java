package de.thb.paf.scrabblefactory.models.components;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of available component types.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public enum ComponentType {
    @SerializedName("graphics")
    GFX_COMPONENT,
    @SerializedName("physics")
    PHYS_COMPONENT
}
