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
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.physics.IPhysicsComponent;
import de.thb.paf.scrabblefactory.models.level.BasicLevel;
import de.thb.paf.scrabblefactory.models.level.ILevel;
import de.thb.paf.scrabblefactory.utils.ScrabbleFactoryClassLoader;

import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_COMPONENTS;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_JAVA_PACKAGE;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_NAME;

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

        JsonObject levelConfig = this.assetLoader.loadInitConfiguration(AssetTargetType.LEVEL, levelID);
        Gson gson = new GsonBuilder().create();

        // init level
        BasicLevel level = gson.fromJson(levelConfig, BasicLevel.class);
        GameObjectManager.getInstance().addGameObject(level);

        // init components
        List<IComponent> components = new ArrayList<>();
        JsonArray componentDefinitions = levelConfig.get(JSON_KEY_COMPONENTS).getAsJsonArray();

        for(int i=0; i<componentDefinitions.size(); i++) {
            JsonObject componentDef = componentDefinitions.get(i).getAsJsonObject();
            String componentName = componentDef.get(JSON_KEY_NAME).getAsString();
            String javaPackageName = componentDef.get(JSON_KEY_JAVA_PACKAGE).getAsString();
            Class<?> componentType = ScrabbleFactoryClassLoader.getClassForName(javaPackageName, componentName);

            IComponent component = null;
            if(IPhysicsComponent.class.isAssignableFrom(componentType)) {
                component = new PhysicsComponentFactory(this.assetLoader)
                        .getPhysComponent(componentType, componentDef, level);
            } else if(IGraphicsComponent.class.isAssignableFrom(componentType)) {
                component = new GraphicsComponentFactory(this.assetLoader)
                        .getGfxComponent(componentType, componentDef, level);
            }

            if(component != null) {
                components.add(component);
            }
        }

        level.addComponents(components);
        return level;
    }
}
