package de.thb.paf.scrabblefactory.models.hud;

import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.actions.IGameAction;

/**
 * Interface that declares methods a dedicated HUD  class must implement
 * in order to work correctly as a hud canvas container.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public interface IHUDComponent extends IGameObject, IGameAction {

    /**
     * Get the parent HUD system.
     * @return The parented HUD system
     */
    HUDSystem getHUDSystem();

    /**
     * Set the parent HUD system.
     * @param hudSystem The parent HUD system.
     */
    void setHUDSystem(HUDSystem hudSystem);


    /**
     * Get the HUD's associated display type.
     * @return The HUD's display type
     */
    HUDComponentType getHudComponentType();
}
