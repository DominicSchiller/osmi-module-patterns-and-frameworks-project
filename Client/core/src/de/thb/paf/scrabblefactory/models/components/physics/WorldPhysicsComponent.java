package de.thb.paf.scrabblefactory.models.components.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import de.thb.paf.scrabblefactory.settings.Settings;

/**
 * Represents a physical world where all (dynamic) physic simulation is happening.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class WorldPhysicsComponent extends AbstractPhysicsComponent {

    /**
     * The default gravity which will be applied to new Box2D world instances
     */
    private static final Vector2 DEFAULT_GRAVITY = new Vector2(0, 12);

    /**
     * The LibGDX Box2D world instance
     */
    private World world;

    /**
     * The active gravity vector
     */
    private Vector2 gravity;

    /**
     * Default Constructor
     * @param id The component's unique identifier
     */
    public WorldPhysicsComponent(Integer id) {
        super(id, PhysicsType.WORLD);
    }

    /**
     * Constructor
     * @param id The component's unique identifier
     * @param gravity The referenced Box2D world's gravity settings
     */
    public WorldPhysicsComponent(Integer id, Vector2 gravity) {
        this(id);
        this.setWordGravity(gravity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.world.step(1 / Settings.App.FPS, 6, 2);
    }

    /**
     * Get the referenced Box2D physical world.
     * @return The referenced Box2D physical world.
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Set the physical world's gravity.
     * @param gravity The gravity to apply to the physical world
     */
    public void setWordGravity(Vector2 gravity) {
        this.world.setGravity(gravity);
    }

    /**
     * Re-initializes the physical Box2D world based on currently set gravity vector.
     */
    public void reloadWorld() {
        Vector2 gravity = this.gravity == null ? DEFAULT_GRAVITY : this.gravity;
        this.world = new World(gravity, false);
    }

}
