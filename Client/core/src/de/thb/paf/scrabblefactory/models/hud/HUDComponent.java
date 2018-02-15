package de.thb.paf.scrabblefactory.models.hud;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.events.GameEventType;

/**
 * Abstract representation of a HUD component.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public abstract class HUDComponent implements IHUDComponent {

    /**
     * The parented HUD system
     */
    HUDSystem parentHUDSystem;

    /**
     * List of event types the action is registered to
     */
    private GameEventType[] eventsToHandle;

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
     * The HUD component's asset target type
     */
    private AssetTargetType assetTargetType;

    /**
     * The associated HUD type
     */

    private HUDComponentType type;

    /**
     * The HUD component's on screen alignment
     */
    public int[] margin;

    /**
     * The HUD component's relative on screen  margins
     */
    public Alignment alignment;

    /**
     * Default Constructor
     */
    HUDComponent() {
        this.assetTargetType = AssetTargetType.HUD;
        this.eventsToHandle = new GameEventType[0];
        this.components = new ArrayList<>();
    }

    @Override
    public HUDComponentType getHudComponentType() {
        return this.type;
    }

    @Override
    public void setEventTypesToHandle(GameEventType[] eventTypesToHandle) {
        this.eventsToHandle = eventTypesToHandle;
    }

    @Override
    public int getID() {
        return this.parentHUDSystem.getType().id;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getRotation() {
        return 0;
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
    public void setRotation(float rotation) {
        // ignore because a HUD does not need any rotation
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

    @Override
    public HUDSystem getHUDSystem() {
        return this.parentHUDSystem;
    }

    @Override
    public void setHUDSystem(HUDSystem hudSystem) {
        this.parentHUDSystem = hudSystem;
    }

    @Override
    public GameEventType[] getEventTypesToHandle() {
        return this.eventsToHandle;
    }
}
