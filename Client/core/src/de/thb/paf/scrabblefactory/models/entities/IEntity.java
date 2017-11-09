package de.thb.paf.scrabblefactory.models.entities;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Interface that declares methods a dedicated  game entity class must implement in order to work correctly as an entity class.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public interface IEntity {

    /**
     * Updates the entity based on the game's current render tick. 
     * @param deltaTime The time passed between the last and the current frame in seconds
     */
     void update(float deltaTime);

    /**
     * Get the entity's unique identifier.
     * @return The entity's unique identifier
     */
     int getID();

    /**
     * Get the entity's unique entity type
     *
     * @see EntityType
     * @return The entity's unique entity type
     */
     EntityType getType();

    /**
     * Get the entity's position.
     * @return The entity's position
     */
     Vector2 getPosition();

    /**
     * Get the entity's scaling.
     * @return The entity's scaling
     */
     Vector2 getScale();

    /**
     * Get the entity's rotation.
     * @return The entity's rotation
     */
     float getRotation();

    /**
     * Get the entity's termination state.
     * @return The entity's termination state
     */
     boolean isTerminated();

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
    public IComponent getComponent(int id);

    /**
     * Set the entity's position.
     * @param position The new position vector
     */
     void setPosition(Vector2 position);

    /**
     * Set the entity's scaling.
     * @param scale The new scale vector
     */
     void setScale(Vector2 scale);

    /**
     * Set the entity's rotation.
     * @param rotation The new rotation angle
     */
     void setRotation(float rotation);

    /**
     * Set the entity's termination state.
     * @param isTerminated The new termination state
     */
     void setTerminated(boolean isTerminated);

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