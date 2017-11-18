package de.thb.paf.scrabblefactory.models;

import java.util.List;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Interface that declares methods a dedicated game object must implement in order
 * to get used correctly by the scrabble factory game engine.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IGameObject {

    /**
     * Updates the entity based on the game's current render tick.
     * @param deltaTime The time passed between the last and the current frame in seconds
     */
    void update(float deltaTime);

    /**
     * Get all with the entity associated components.
     * @return List of associated components
     */
    List<IComponent> getAllComponents();

    /**
     * Get all with the entity associated components of certain type.
     * @param type The components' type
     * @return All found components of given type
     */
    List<IComponent> getAllComponents(ComponentType type);

    /**
     * Get specific with the entity associated component.
     * @param id The component's unique identifier
     * @return The found component
     */
    IComponent getComponent(int id);

    /**
     * Add a new component to the list of associated components.
     * @param component The component to add
     */
    void addComponent(IComponent component);

    /**
     * Add a list of new  components to the list of associated components.
     * @param components List of components to add
     */
    void addComponents(List<IComponent> components);

    /**
     * Removes all with the entity associated components.
     * @return The success status
     */
    boolean removeAllComponents();

    /**
     * Remove all components of a specific type from the entity's list of associated components.
     * @param type The components' type
     * @return The success status
     */
    boolean removeAllComponents(ComponentType type);

    /**
     * Remove a specific component component from the entity's list of associated components.
     * @param id The entity's unique identifier
     * @return The success status
     */
    boolean removeComponent(int id);

}
