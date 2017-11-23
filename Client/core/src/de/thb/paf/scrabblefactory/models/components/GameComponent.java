package de.thb.paf.scrabblefactory.models.components;

import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.entities.IEntity;

/**
 * Abstract representation of a game component.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public abstract class GameComponent implements IComponent {

    /**
     * The component's unique id
     */
    private int id;

    @Override
    public IGameObject getParent() {
        return this.parent;
    }

    /**
     * The component's unique type
     */
    private ComponentType type;

    /**
     * The associated game object holding this component
     */
    private IGameObject parent;

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     */
    public GameComponent(int id, ComponentType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     */
    public GameComponent(int id, ComponentType type, IEntity parent) {
        this.id = id;
        this.type = type;
        this.parent = parent;
    }

    @Override
    public void update(float deltaTime) {
        // TODO implement here
    }

    @Override
    public void dispose() {
        // TODO: implement here....
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public ComponentType getType() {
        return this.type;
    }

    @Override
    public void setParent(IGameObject parent) {
        this.parent = parent;
    }

    @Override
    public void setType(ComponentType type) {
        this.type = type;
    }

}