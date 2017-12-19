package de.thb.paf.scrabblefactory.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.managers.WorldPhysicsManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.actions.IGameAction;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.components.physics.WorldPhysicsComponent;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.CloneComponentHelper;
import de.thb.paf.scrabblefactory.utils.ScrabbleFactoryClassLoader;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

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
        this.assetLoader = assetLoader;
    }

    /**
     * Get physics component instance defined by component definition.
     * @param componentDef The component's JSON definition
     * @param parent The parent game object
     * @return The requested graphic component instance
     */
    public IComponent getPhysComponent(Class<?> classType, JsonObject componentDef, IGameObject parent) {
        Gson gson = new GsonBuilder().create();

        //TODO: implement id calculation
        IComponent component = (IComponent) ScrabbleFactoryClassLoader.createInstance(classType, 1);
        IComponent parsedComponent = gson.fromJson(componentDef, component.getClass());

        CloneComponentHelper.cloneFieldValues(parsedComponent, component);
        component.setParent(parent);

        // initialize the component's special content
        this.initPhysComponent(component, parent);

        // initialize associated game actions
        this.initActions(componentDef, component);

        return component;
    }

    /**
     * Initializes the content of the passed physics components.
     * @param physicsComponent The physics component to setup
     * @param parent The parent game object
     */
    private void initPhysComponent(IComponent physicsComponent, IGameObject parent) {
        if(physicsComponent instanceof WorldPhysicsComponent) {
            this.initWorldPhysicsComponent((WorldPhysicsComponent) physicsComponent);
        } else if(physicsComponent instanceof RigidBodyPhysicsComponent) {
            this.initRigidBodyPhysicsComponent((RigidBodyPhysicsComponent) physicsComponent, parent);
        }
    }

    /**
     * Initializes the content of a world physics component.
     * @param physicsComponent The physics component to assemble the content for
     */
    private void initWorldPhysicsComponent(WorldPhysicsComponent physicsComponent) {
        WorldPhysicsManager worldPhysicsManager = WorldPhysicsManager.getInstance();
        physicsComponent.setWorld(worldPhysicsManager.getPhysicalWorld());
        physicsComponent.setSPS(Settings.App.FPS);
    }

    /**
     * Initializes the content of a rigid body physics component.
     * @param physicsComponent The physics component to setup
     * @param parent The parent game object
     */
    private void initRigidBodyPhysicsComponent(RigidBodyPhysicsComponent physicsComponent, IGameObject parent) {
        Vector2 position = parent.getPosition();
        float scale = VIRTUAL_SCALE * RESOLUTION.virtualScaleFactor;

        PhysicsShapeCache shapeCache = this.assetLoader.loadPhysicsConfiguration(
                parent.getAssetTargetType(),
                parent.getID());

        World world = WorldPhysicsManager.getInstance().getPhysicalWorld();
        Body body = shapeCache.createBody(
                physicsComponent.getActiveBodyName(),
                world,
                scale, scale);
//        body.setUserData(name);
        body.setTransform(position.x, position.y, 0);

        physicsComponent.setPhysicsShapeShapeCache(shapeCache);
        physicsComponent.setBody(body);
    }

    /**
     * Registers the component to listen for defined events.
     * @param component The component to register for event observing
     */
    private void initActions(JsonObject componentDef, IComponent component) {
        JsonArray actionDefs = componentDef.getAsJsonArray("actions");
        ActionFactory actionFactory = new ActionFactory();
        if(actionDefs != null) {
            for(JsonElement actionDef : actionDefs) {
                IGameAction action = actionFactory.getGameAction(actionDef.getAsJsonObject(), component);
                component.addAction(action);
            }
        }
    }
}
