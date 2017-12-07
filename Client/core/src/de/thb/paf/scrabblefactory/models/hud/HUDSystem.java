package de.thb.paf.scrabblefactory.models.hud;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete HUD systems as a collection of it's associated HUD components.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class HUDSystem {

    /**
     * The HUD systems's type
     */
    private HUDSystemType type;

    /**
     * List of HUD components the HUD system is made up of
     */
    private List<IHUDComponent> hudComponents;

    /**
     * Constructor
     * @param type The HUD systems's type
     */
    public HUDSystem(HUDSystemType type) {
        this.type = type;
        this.hudComponents = new ArrayList<>();
    }

    /**
     * Updates the complete HUD system based  on the game's current render tick.
     * @param deltaTime he time passed between the last and the current frame in seconds
     */
    public void update(float deltaTime) {
        //TODO: implement here
    }

    /**
     * Get the associated HUD system type.
     * @return The HUD system type
     */
    public HUDSystemType getType() {
        return this.type;
    }

    public void addHUDComponent(IHUDComponent hudComponent) {
        this.hudComponents.add(hudComponent);
    }

    public List<IHUDComponent> getHUDComponents() {
        return this.hudComponents;
    }
}
