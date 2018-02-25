package de.thb.paf.scrabblefactory.factories;

import com.badlogic.gdx.math.Vector2;
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
import de.thb.paf.scrabblefactory.models.entities.Cheese;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.models.entities.Player;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.ScrabbleFactoryClassLoader;

import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_COMPONENTS;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_JAVA_PACKAGE;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_NAME;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;

/**
 * Factory class dedicated to create and assemble new entity instances.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class EntityFactory {

    /**
     * The asset loader instance to load required files from the games asset's directory
     */
    private AssetLoader assetLoader;

    /**
     * Get the entity instance defined by it's entity type and unique identifier.
     * @param entityType The entity's type
     * @param entityID The entity's unique identifier
     * @return The requested entity instance
     */
    public IEntity getEntity(EntityType entityType, int entityID) {
        if(this.assetLoader == null) {
            this.assetLoader = new AssetLoader();
        }

        AssetTargetType assetTargetType;
        Class entityClass;
        switch(entityType) {
            case PLAYER:
                assetTargetType = AssetTargetType.CHARACTER;
                entityClass = Player.class;
                break;
            case CHEESE:
                assetTargetType = AssetTargetType.ITEM;
                entityClass = Cheese.class;
                break;
            default:
                //TODO: remove this case
                assetTargetType = AssetTargetType.CHARACTER;
                entityClass = Player.class;
                break;
        }
        JsonObject entityConfig = this.assetLoader.loadInitConfiguration(assetTargetType, entityID);
        Gson gson = new GsonBuilder().create();

        // init entity
        IEntity entity = (IEntity) gson.fromJson(entityConfig, entityClass);
        entity.setScale(
                new Vector2(
                        Settings.Game.VIRTUAL_SCALE,
                        Settings.Game.VIRTUAL_SCALE
                )
        );
        // update position based on current PPM
        Vector2 position = entity.getPosition();
        position.x *= 1/PPM;
        position.y *= 1/PPM;
        entity.setPosition(position);

        // init components
        List<IComponent> components = new ArrayList<>();
        JsonArray componentDefinitions = entityConfig.get(JSON_KEY_COMPONENTS).getAsJsonArray();

        for(int i=0; i<componentDefinitions.size(); i++) {
            JsonObject componentDef = componentDefinitions.get(i).getAsJsonObject();
            String componentName = componentDef.get(JSON_KEY_NAME).getAsString();
            String javaPackageName = componentDef.get(JSON_KEY_JAVA_PACKAGE).getAsString();
            Class<?> componentType = ScrabbleFactoryClassLoader.getClassForName(javaPackageName, componentName);

            IComponent component = null;
            if(IPhysicsComponent.class.isAssignableFrom(componentType)) {
                component = new PhysicsComponentFactory(this.assetLoader)
                        .getPhysComponent(componentType, componentDef, entity);
            } else if(IGraphicsComponent.class.isAssignableFrom(componentType)) {
                component = new GraphicsComponentFactory(this.assetLoader)
                        .getGfxComponent(componentType, componentDef, entity);
            }

            if(component != null) {
                components.add(component);
            }
        }

        entity.setActive(true);
        entity.addComponents(components);
        GameObjectManager.getInstance().addGameObject(entity);
        return entity;
    }
}
