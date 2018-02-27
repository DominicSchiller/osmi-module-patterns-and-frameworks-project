package de.thb.paf.scrabblefactory.managers;


import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

import de.thb.paf.scrabblefactory.models.entities.IEntity;

/**
 * Restore Manager dedicated to restore a play screen's level.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class PlayScreenRestoreManager {

    /**
     * The singleton PlayScreenRestoreManager instance
     */
    private static PlayScreenRestoreManager instance;

    /**
     * Hash-Map which stores all initial positions for each entity of the current level
     */
    private Map<IEntity, Vector2> restorePositions;

    /**
     * Private Constructor.
     */
    private PlayScreenRestoreManager() {
        this.restorePositions = new HashMap<>();
    }

    /**
     * Get the global PlayScreenRestoreManager instance.
     * @return The global PlayScreenRestoreManager instance
     */
    public static PlayScreenRestoreManager getInstance() {
        if(instance == null) {
            instance = new PlayScreenRestoreManager();
        }
        return instance;
    }

    /**
     * Add a restore position assigned with a specific entity.
     * @param entity The entity to store the restore position for
     * @param position The entity's restore position to store
     */
    public void addRestorePosition(IEntity entity, Vector2 position) {
        this.restorePositions.put(entity, position);
    }

    /**
     * Get a restore position for the requested entity.
     * @param entity The entity to get the restore position for
     * @return The restore position from the requested entity
     */
    public Vector2 getRestorePosition(IEntity entity) {
        return restorePositions.get(entity);
    }
}
