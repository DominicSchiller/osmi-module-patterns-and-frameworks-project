package de.thb.paf.scrabblefactory.models.components;

import de.thb.paf.scrabblefactory.models.entities.IEntity;

/**
 * Interface that declares methods a dedicated  game component class must implement in order to work correctly as an ordinary game component .
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public interface IComponent {

    /**
     * Updates the component  based on the game's current render tick.
     * @param deltaTime The time passed between the last and the current frame in seconds
     */
    void update(float deltaTime);

    /**
     * Get the component's unique identifier.
     * @return The component's unique identifier
     */
    int getID();

    /**
     * Get the component's type.
     * @return The component's type
     */
    ComponentType getType();

    /**
     * Set the game entity the component belongs to.
     * @param entity The associated game entity
     */
    void setParent(IEntity entity);

    /**
     * Set the component's type.
     * @param type The component's type
     */
    void setType(ComponentType type);

}