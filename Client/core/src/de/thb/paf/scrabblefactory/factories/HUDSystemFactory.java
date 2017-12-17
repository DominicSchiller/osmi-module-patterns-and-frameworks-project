package de.thb.paf.scrabblefactory.factories;

import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.hud.HUDSystem;
import de.thb.paf.scrabblefactory.models.hud.HUDSystemType;
import de.thb.paf.scrabblefactory.models.hud.HealthHUD;
import de.thb.paf.scrabblefactory.models.hud.IHUDComponent;
import de.thb.paf.scrabblefactory.utils.ScrabbleFactoryClassLoader;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;

import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_COMPONENTS;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_JAVA_PACKAGE;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_NAME;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_TYPE;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Factory class dedicated to create and assemble HUD systems.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class HUDSystemFactory {

    /**
     * The asset loader instance to load required files from the games asset's directory
     */
    private AssetLoader assetLoader;

    /**
     * Get HUD system instance defined by it's unique type.
     * @param hudSystemType The HUD system type
     * @return The requested HUD system instance
     */
    public HUDSystem getHUDSystem(HUDSystemType hudSystemType) {
        if(this.assetLoader == null) {
            this.assetLoader = new AssetLoader();
        }

        HUDSystem hudSystem = new HUDSystem(hudSystemType);

        JsonObject hudSystemConfig = this.assetLoader.loadInitConfiguration(AssetTargetType.HUD, hudSystemType.id);
        Gson gson = new GsonBuilder().create();

        JsonArray hudComponentConfigs = hudSystemConfig.get(JSON_KEY_COMPONENTS).getAsJsonArray();
        for(JsonElement hudComponentConfig : hudComponentConfigs) {
            String hudComponentType = hudComponentConfig.getAsJsonObject().get(JSON_KEY_TYPE).getAsString();
            IHUDComponent hudComponent = this.initHUDComponent(gson, hudComponentType, hudComponentConfig.getAsJsonObject());

            if(hudComponent != null) {
                hudComponent.setHUDSystem(hudSystem);
                this.initAssociatedComponents(hudComponent, hudComponentConfig.getAsJsonObject());
                hudSystem.addHUDComponent(hudComponent);
            }
        }

        return hudSystem;
    }

    private IHUDComponent initHUDComponent(Gson gson, String hudComponentType, JsonObject hudComponentConfig) {

        IHUDComponent hudComponent;
        switch(hudComponentType) {
            case "health":
                hudComponent = this.initHealthHUDComponent(gson, hudComponentConfig.getAsJsonObject());
                break;
            default:
                hudComponent = null;
                // we do nothing here
                break;
        }

        return hudComponent;
    }

    private IHUDComponent initHealthHUDComponent(Gson gson, JsonObject hudComponentConfig) {
        HealthHUD hudComponent = gson.fromJson(hudComponentConfig, HealthHUD.class);
        Vector2 position = AlignmentHelper.getRelativePosition(
                new Vector2 (
                        hudComponent.getSize().x / PPM,
                        hudComponent.getSize().y / PPM
                ),
                new Vector2(VIRTUAL_WIDTH, VIRTUAL_HEIGHT),
                hudComponent.alignment,
                hudComponent.margin
        );
        hudComponent.setPosition(position);
        return hudComponent;
    }

    private void initAssociatedComponents(IHUDComponent hudComponent, JsonObject hudComponentConfig) {
        JsonArray associatedComponentConfigs = hudComponentConfig.getAsJsonObject()
                .get(JSON_KEY_COMPONENTS).getAsJsonArray();

        for(JsonElement componentConfig: associatedComponentConfigs) {
            String componentName = componentConfig.getAsJsonObject().get(JSON_KEY_NAME).getAsString();
            String javaPackageName = componentConfig.getAsJsonObject().get(JSON_KEY_JAVA_PACKAGE).getAsString();
            Class<?> componentType = ScrabbleFactoryClassLoader.getClassForName(javaPackageName, componentName);

            IComponent component = null;
            if(IGraphicsComponent.class.isAssignableFrom(componentType)) {
                System.out.println(componentType.getName());
                component = new GraphicsComponentFactory(this.assetLoader)
                        .getGfxComponent(componentType, componentConfig.getAsJsonObject(), hudComponent);
            }

            if(component != null) {
                hudComponent.addComponent(component);
            }
        }
    }
}
