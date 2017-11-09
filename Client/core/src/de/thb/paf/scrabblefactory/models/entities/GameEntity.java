package de.thb.paf.scrabblefactory.models.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Abstract representation of a game entity.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
abstract class GameEntity implements IEntity {

    /**
     * Default constructor
     */
    GameEntity() {
    }

    /**
     * The game entity's unique identifier
     */
    private int id;

    /**
     * The game entity's position
     */
    private Vector2 position;

    /**
     * The game entity's scaling
     */
    private Vector2 scale;

    /**
     * The game entity's rotation
     */
    private float rotation;

    /**
     * The game entity's indicator whether it can be destroyed and removed or not
     */
    private boolean isTerminated;

    /**
     * The game entity's unique type
     */
    private EntityType type;

    /**
     * Constructor
     * @see EntityType
     * @param id The entity's unique identifier
     * @param type The entity's type
     */
    GameEntity(int id, EntityType type) {
        // TODO implement here
    }

    @Override
    public void update(float deltaTime) {
        // TODO implement here
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public EntityType getType() {
        return this.type;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public Vector2 getScale() {
        return this.scale;
    }

    @Override
    public float getRotation() {
        return this.rotation;
    }

    @Override
    public boolean isTerminated() {
        return this.isTerminated;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void setTerminated(boolean isTerminated) {
        this.isTerminated = isTerminated;
    }

}