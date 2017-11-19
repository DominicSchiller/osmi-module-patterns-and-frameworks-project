package de.thb.paf.scrabblefactory.models.components.physics;

/**
 * Interface that declares methods a dedicated  physics  component class must implement in
 * order to get correctly used and identified.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IPhysicsComponent {

    /**
     * Get the associated physics type.
     * @return The associated physics type
     */
    PhysicsType getPhysicsType();
}
