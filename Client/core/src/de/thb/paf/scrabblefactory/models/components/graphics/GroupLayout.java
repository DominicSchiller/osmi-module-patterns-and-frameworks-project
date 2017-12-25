package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.List;

import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;


/**
 * Layout container which relatively groups it's associated graphics components within it's bounds.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class GroupLayout extends Group implements IGameObject {

    /**
     * The layout container's default pivot point (here: bottom left corner)
     */
    private static Vector2 PIVOT_POINT = new Vector2(0, 0);

    /**
     * The associated parent game object.
     */
    private IGameObject parentGameObject;

    /**
     * Constructor
     * @param parent The associated parent game object
     */
    public GroupLayout(IGameObject parent) {
        super();
        this.parentGameObject = parent;
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void dispose() {}

    @Override
    public int getID() {
        return this.parentGameObject.getID();
    }

    @Override
    public Vector2 getPosition() {
        return PIVOT_POINT;
    }

    @Override
    public Vector2 getSize() {
        return this.parentGameObject.getSize();
    }

    @Override
    public AssetTargetType getAssetTargetType() {
        return this.parentGameObject.getAssetTargetType();
    }

    @Override
    public List<IComponent> getAllComponents() {
        return null;
    }

    @Override
    public List<IComponent> getAllComponents(ComponentType type) {
        return null;
    }

    @Override
    public IComponent getComponent(int id) {
        return null;
    }

    @Override
    public void setPosition(Vector2 position) {}

    @Override
    public void setSize(Vector2 size) {}

    @Override
    public void addComponent(IComponent component) {}

    @Override
    public void addComponents(List<IComponent> components) {}

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
