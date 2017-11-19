package de.thb.paf.scrabblefactory.factories;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.level.BasicLevel;
import de.thb.paf.scrabblefactory.models.level.ILevel;

import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_COMPONENTS;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_TYPE;

/**
 * Factory class dedicated to create and assemble new level instances.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class LevelFactory {

    /**
     * The asset loader instance to load required files from the games asset's directory
     */
    private AssetLoader assetLoader;

    /**
     * Get level instance defined by it's unique identifier.
     * @param levelID The level's unique identifier to load it's content for
     * @return The requested level instance
     */
    public ILevel getLevel(int levelID) {
        if(this.assetLoader == null) {
            this.assetLoader = new AssetLoader();
        }

        JsonObject levelConfig = this.assetLoader.loadLevelConfiguration(AssetTargetType.LEVEL, levelID);
        Gson gson = new GsonBuilder().create();

        // init level
        BasicLevel level = gson.fromJson(levelConfig, BasicLevel.class);

        // init components
        List<IComponent> components = new ArrayList<>();
        JsonArray componentDefinitions = levelConfig.get(JSON_KEY_COMPONENTS).getAsJsonArray();
        for(int i=0; i<componentDefinitions.size(); i++) {

            JsonObject componentDef = componentDefinitions.get(i).getAsJsonObject();
            String componentType = componentDef.get(JSON_KEY_TYPE).getAsString();

            IComponent component = null;
            switch(componentType) {
                case "graphics":
                    component = new GraphicsComponentFactory(this.assetLoader)
                            .getGfxComponent(componentDef, level);
                    break;
                case "physics":
                     component = new PhysicsComponentFactory(this.assetLoader)
                            .getPhysComponent(componentDef, level);
                    break;
            }

            if(component != null) {
                components.add(component);
            }
        }

        level.addComponents(components);
        GameObjectManager.getInstance().addGameObject(level);
        return level;
    }
}
