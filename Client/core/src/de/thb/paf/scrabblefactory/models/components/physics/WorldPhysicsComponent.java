package de.thb.paf.scrabblefactory.models.components.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Represents a physical world where all (dynamic) physic simulation is happening.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class WorldPhysicsComponent extends AbstractPhysicsComponent {

    /**
     * The LibGDX Box2D world instance
     */
    private World world;

    /**
     * The active gravity vector
     */
    private Vector2 gravity;

    private float mAccumulator;

    private float sps;

    /**
     * Default Constructor
     * @param id The component's unique identifier
     */
    public WorldPhysicsComponent(Integer id) {
        super(id, PhysicsType.WORLD);
        mAccumulator = 0;
    }

    /**
     * Constructor
     * @param id The component's unique identifier
     * @param gravity The referenced Box2D world's gravity settings
     */
    public WorldPhysicsComponent(Integer id, Vector2 gravity) {
        this(id);
    }

    @Override
    public void update(float deltaTime) {
        this.stepWorld(deltaTime);
    }

    /**
     * Get the physical world's gravity.
     * @return the physical world's gravity vector
     */
    public Vector2 getWorldGravity() {
        return this.gravity;
    }

    /**
     * Set the physical world's gravity.
     * @param gravity The gravity to apply to the physical world
     */
    public void setWordGravity(Vector2 gravity) {
        this.world.setGravity(gravity);
    }

    /**
     * Set the physical world instance
     * @param world The physical world instance
     */
    public void setWorld(World world) {
        this.world = world;
        world.setGravity(this.gravity);
    }

    /**
     * Set the world's steps-per-seconds rate at which to update the physics
     * @param sps The world's steps-per-seconds rate
     */
    public void setSPS(float sps) {
        this.sps = 1/sps;
    }

    /**
     * Steps though the world
     * @link https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics
     * @param deltaTime The game's current deltaTime (calculated through each render loop cycle)
     */
    private void stepWorld(float deltaTime) {
        mAccumulator += Math.min(deltaTime, 0.25f);

        if(mAccumulator >= this.sps) {
            mAccumulator -= this.sps;

            this.world.step(this.sps, 6, 2);
        }
    }

}
