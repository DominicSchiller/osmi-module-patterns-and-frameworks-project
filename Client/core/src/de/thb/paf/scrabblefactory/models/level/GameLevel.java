package de.thb.paf.scrabblefactory.models.level;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Abstract representation of a level.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

abstract class GameLevel implements ILevel {

    /**
     * The pivot point position (default: left bottom corner)
     */
    private Vector2 position;

    /**
     * The level's on screen dimensions
     */
    private Vector2 size;

    /**
     * List of associated components
     */
    List<IComponent> components;

    /**
     * The level's unique identifier
     */
    private int id;

    /**
     * The level's title
     */
    private String title;

    /**
     * The level's given countdown
     */
    private int countdown;

    /**
     * The level's assetTargetType
     */
    private AssetTargetType assetTargetType;

    /**
     * Default Constructor
     */
    GameLevel() {
        this.components = new ArrayList<IComponent>();
    }

    @Override
    public void update(float deltaTime) {
        //TODO: implement here...
        for(IComponent component : this.components) {
            component.update(deltaTime);
        }
    }

    @Override
    public int getID() {
        return this.id;
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
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getCountdown() {
        return this.countdown;
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
        return null;
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
    public void addComponent(IComponent component) {
        this.components.add(component);
    }

    @Override
    public void addComponents(List<IComponent> components) {
        this.components.addAll(components);
    }

    @Override
    public boolean removeAllComponents() {
        return false;
    }

    @Override
    public boolean removeAllComponents(ComponentType type) {
        return false;
    }

    @Override
    public boolean removeComponent(int id) {
        return false;
    }
}
