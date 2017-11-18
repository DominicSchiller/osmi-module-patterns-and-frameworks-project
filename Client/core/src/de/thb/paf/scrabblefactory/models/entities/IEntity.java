package de.thb.paf.scrabblefactory.models.entities;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Interface that declares methods a dedicated  game entity class must implement in order to work correctly as an entity class.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public interface IEntity extends IGameObject{

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

}