package de.thb.paf.scrabblefactory.models.components.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.codeandweb.physicseditor.PhysicsShapeCache;

/**
 * Represents a rigid body assembling all static or dynamic body characteristic like
 * friction, density etc. in relation to real world physics.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class RigidBodyPhysicsComponent extends AbstractPhysicsComponent {

    /**
     * List of body keys which can be used to load a body
     */
    private String[] bodyKeys;

    /**
     * The active body's name
     */
    private String activeBodyName;

    /**
     * The Box2D physics shape cache to create bodies from
     */
    private PhysicsShapeCache physicsShapeCache;

    /**
     * The LibGDX Box2D body instance
     */
    private Body body;

    /**
     * Constructor
     * @param id The component's unique identifier
     */
    public RigidBodyPhysicsComponent(Integer id) {
        super(id, PhysicsType.RIGID_BODY);
    }

    /**
     * Constructor
     * @param id The component's unique identifier
     * @param body The LibGDX Box2D body instance
     */
    public RigidBodyPhysicsComponent(Integer id, Body body) {
        super(id, PhysicsType.RIGID_BODY);
        this.body = body;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.getParent().setPosition(this.body.getPosition());
    }

    /**
     * Get the active body's name.
     * @return The active body's name
     */
    public String getActiveBodyName() {
        return this.activeBodyName;
    }

    /**
     * Get the Box2D physics shape cache containing all body definitions
     * @return The Box2D physics shape cache
     */
    public PhysicsShapeCache getPhysicsShapeCache() {
        return this.physicsShapeCache;
    }

    /**
     * Set the active body's name.
     * @param bodyName The active body's name
     */
    public void setActiveBodyName(String bodyName) {
        this.activeBodyName = bodyName;
    }
    /**
     * The Box2D physics shape cache containing all body definitions
     * @param physicsShapeShapeCache The Box2D physics shape cache
     */
    public void setPhysicsShapeShapeCache(PhysicsShapeCache physicsShapeShapeCache) {
        this.physicsShapeCache = physicsShapeShapeCache;
    }

    /**
     * Set the LibGDX Box2D body instance.
     * @param body The LibGDX Box2D body instance
     */
    public void setBody(Body body) {
        this.body = body;
    }


}
