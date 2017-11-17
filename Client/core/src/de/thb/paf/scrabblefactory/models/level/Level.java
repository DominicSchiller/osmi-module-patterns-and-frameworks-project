package de.thb.paf.scrabblefactory.models.level;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Represents a basic Level.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class Level implements ILevel {

    private int id;

    private String title;

    private int countdown;

    private String[] wordPool;

    private List<IComponent> components;

    /**
     * Default Constructor
     */
    public Level() {
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
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getCountdown() {
        return this.countdown;
    }

    @Override
    public String[] getWordPool() {
        return this.wordPool;
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
