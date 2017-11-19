package de.thb.paf.scrabblefactory.models.components.physics;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;

/**
 * Abstract representation of a physics component a concrete physics component
 * must inherit from.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

abstract class AbstractPhysicsComponent extends GameComponent implements IPhysicsComponent {

    /**
     * The component's associated physics type
     */
    private PhysicsType physicsType;

    /**
     * Constructor
     * @param id The component's unique identifier
     * @param physicsType The component's associated physics type
     */
    AbstractPhysicsComponent(int id, PhysicsType physicsType) {
        super(id, ComponentType.PHYS_COMPONENT);
        this.physicsType = physicsType;
    }

    @Override
    public PhysicsType getPhysicsType() {
        return this.physicsType;
    }
}
