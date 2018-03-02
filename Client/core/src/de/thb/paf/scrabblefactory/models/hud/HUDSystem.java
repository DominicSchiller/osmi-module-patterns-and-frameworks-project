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

    /**
     * Add HUD component to the HUD system.
     * @param hudComponent The HUD component to add
     */
    public void addHUDComponent(IHUDComponent hudComponent) {
        this.hudComponents.add(hudComponent);
    }

    /**
     * Get all associated HUD components.
     * @return List of associated HUD components
     */
    public List<IHUDComponent> getHUDComponents() {
        return this.hudComponents;
    }

    /**
     * Get HUD component defined by it's type.
     * @param type The requested HUD component's type
     * @return The found HUD component
     */
    public IHUDComponent getHUDComponent(HUDComponentType type) {
        IHUDComponent foundComponent = null;
        for(IHUDComponent hudComponent : this.hudComponents) {
            if(hudComponent.getHudComponentType() == type) {
                foundComponent = hudComponent;
                break;
            }
        }

        return foundComponent;
    }

    /**
     * Dispose the HUD system.
     */
    public void dispose() {
        for(IHUDComponent hudComponent : this.hudComponents) {
            hudComponent.dispose();
        }

        this.hudComponents.clear();
    }
}
