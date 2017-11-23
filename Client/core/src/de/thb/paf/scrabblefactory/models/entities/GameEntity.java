package de.thb.paf.scrabblefactory.models.entities;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Abstract representation of a game entity.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
abstract class GameEntity implements IEntity {

    /**
     * The game entity's unique identifier
     */
    private int id;

    /**
     * The game entity's position
     */
    private Vector2 position;

    /**
     * The game entity's size
     */
    private Vector2 size;

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
     * The game entity's asset target type
     */
    private AssetTargetType assetTargetType;

    /**
     * List of associated components
     */
    List<IComponent> components;

    /**
     * Default COnstructor
     */
    GameEntity() {
        this.components = new ArrayList<>();
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     * @see EntityType
     */
    GameEntity(int id, EntityType type) {
        this();
        this.id = id;
        this.type = type;
    }

    @Override
    public void update(float deltaTime) {
        // TODO implement here
        for(IComponent component : this.components) {
            component.update(deltaTime);
        }
    }

    @Override
    public void dispose() {
        //TODO: implement here...
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
    public Vector2 getSize() {
        return this.size;
    }

    @Override
    public AssetTargetType getAssetTargetType() {
        return this.assetTargetType;
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
    public List<IComponent> getAllComponents() {
        return this.components;
    }

    @Override
    public List<IComponent> getAllComponents(ComponentType type) {
        List<IComponent> filteredComponents = new ArrayList<>();
        for(IComponent component : this.components) {
            if(component.getType().equals(type)) {
                filteredComponents.add(component);
            }
        }

        return filteredComponents;
    }

    @Override
    public IComponent getComponent(int id) {
        // TODO implement here
        return null;
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
    public void setSize(Vector2 size) {
        this.size = size;
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

    @Override
    public void addComponent(IComponent component) {
        // TODO implement here
    }

    @Override
    public void addComponents(List<IComponent> components) {
        // TODO implement here
        this.components.addAll(components);
    }

    @Override
    public boolean removeAllComponents() {
        // TODO implement here
        return false;
    }

    @Override
    public boolean removeComponent(int id) {
        // TODO implement here
        return false;
    }

    @Override
    public boolean removeAllComponents(ComponentType type) {
        // TODO implement here
        return false;
    }

}
