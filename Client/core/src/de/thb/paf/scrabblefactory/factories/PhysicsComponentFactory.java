package de.thb.paf.scrabblefactory.factories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.WorldPhysicsComponent;
import de.thb.paf.scrabblefactory.utils.CloneComponentHelper;

import static de.thb.paf.scrabblefactory.settings.Constants.Java.COMPONENTS_PACKAGE;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_NAME;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_TYPE;

/**
 * Factory class dedicated to create and assemble new physics component instances.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class PhysicsComponentFactory {

    /**
     * The asset loader instance to load required files from the games asset's directory
     */
    private AssetLoader assetLoader;

    /**
     * Default Constructor
     */
    public PhysicsComponentFactory() {
        this.assetLoader = new AssetLoader();
    }

    /**
     * Constructor
     * @param assetLoader The asset loader instance to load required files from the games asset's directory
     */
    public PhysicsComponentFactory(AssetLoader assetLoader) {

    }

    /**
     * Get physics component instance defined by component definition.
     * @param componentDef The component's JSON definition
     * @param parent The parent game object
     * @return The requested graphic component instance
     */
    public IComponent getPhysComponent(JsonObject componentDef, IGameObject parent) {
        Gson gson = new GsonBuilder().create();
        String componentName = componentDef.get(JSON_KEY_NAME).getAsString();
        String componentType = componentDef.get(JSON_KEY_TYPE).getAsString();

        try {
            Class<?> componentClassType = Class
                    .forName(COMPONENTS_PACKAGE + componentType + "." + componentName);

            //TODO: implement id calculation
            IComponent component = (IComponent) componentClassType.getDeclaredConstructor(Integer.class).newInstance(1);
            IComponent parsedComponent = gson.fromJson(componentDef, component.getClass());

            CloneComponentHelper.cloneFieldValues(component, parsedComponent);
            component.setParent(parent);

            this.initPhysComponent(component, parent);
            return component;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Initializes the content of the passed physics components.
     * @param physicsComponent The physics component to setup
     * @param parent The parent game object
     */
    private void initPhysComponent(IComponent physicsComponent, IGameObject parent) {
        if(physicsComponent instanceof WorldPhysicsComponent) {
            this.initWorldPhysicsComponent(physicsComponent);
        }
    }

    /**
     * Initializes the content of a world physics component.
     * @param physicsComponent The physics component to assemble the content for
     */
    private void initWorldPhysicsComponent(IComponent physicsComponent) {
        ((WorldPhysicsComponent) physicsComponent).reloadWorld();
    }
}
