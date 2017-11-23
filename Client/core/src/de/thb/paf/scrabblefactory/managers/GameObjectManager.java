package de.thb.paf.scrabblefactory.managers;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;

/**
 * Manager class responsible managing all registered game objects.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameObjectManager implements IGameManager {

    /**
     * The singleton instance of the GameObjectManager
     */
    private static GameObjectManager instance;

    /**
     * List of all registered game objects
     */
    private List<IGameObject> gameObjects;

    /**
     * static initializer: called when the class is loaded by the JVM
     */
    static {
        instance = new GameObjectManager();
    }

    /**
     * Private singleton constructor.
     */
    private GameObjectManager() {
        this.gameObjects = new ArrayList<>();
    }

    /**
     * Get the global GameObjectManager instance.
     * @return The global game object instance
     */
    public static GameObjectManager getInstance() {
        return instance;
    }

    /**
     * Add game object to the managed list of registered game objects.
     * @param gameObject The game object to add
     */
    public void addGameObject(IGameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    /**
     * Remove game object from the managed list of registered game objects.
     * @param gameObject The game object to remove
     * @return The success state
     */
    public boolean removeGameObject(IGameObject gameObject) {
        return this.gameObjects.remove(gameObject);
    }

    @Override
    public void dispose() {
        for(IGameObject gameObject : this.gameObjects) {
            gameObject.dispose();
        }
    }
}
