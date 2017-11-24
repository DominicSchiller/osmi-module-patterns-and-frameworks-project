package de.thb.paf.scrabblefactory.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.LayeredTexturesGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.SpriteAnimationGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.TextureLayer;
import de.thb.paf.scrabblefactory.utils.CloneComponentHelper;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;

import static de.thb.paf.scrabblefactory.settings.Constants.Files.LEVEL_ATLAS_NAME;
import static de.thb.paf.scrabblefactory.settings.Constants.Java.COMPONENTS_PACKAGE;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_NAME;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_TYPE;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

/**
 * Factory class dedicated to create and assemble new graphics component instances.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GraphicsComponentFactory {

    /**
     * The asset loader instance to load required files from the games asset's directory
     */
    private AssetLoader assetLoader;

    /**
     * Default Constructor
     */
    public GraphicsComponentFactory() {
        this.assetLoader = new AssetLoader();
    }

    /**
     * Constructor
     * @param assetLoader The asset loader instance to load required files from the games asset's directory
     */
    public GraphicsComponentFactory(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
    }

    /**
     * Get graphics component instance defined by component definition.
     * @param componentDef The component's JSON definition
     * @param parent The parent game object
     * @return The requested graphic component instance
     */
    public IComponent getGfxComponent(JsonObject componentDef, IGameObject parent) {
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

            this.initGfxComponent(component, parent);
            return component;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Initializes the content of the passed graphics components.
     * @param graphicsComponent The graphics component to setup
     * @param parent The parent game object
     */
    private void initGfxComponent(IComponent graphicsComponent, IGameObject parent) {
        if(graphicsComponent instanceof LayeredTexturesGraphicsComponent) {
            initLayeredGraphicsComponent(graphicsComponent, parent);
        } else if(graphicsComponent instanceof SpriteAnimationGraphicsComponent) {
            this.initSpriteAnimationGraphicsComponent(graphicsComponent, parent);
        }
    }

    /**
     * Initializes the content of a layered graphics component.
     * @param graphicsComponent The graphics component to assemble the content for
     * @param parent The parent game object
     * @see LayeredTexturesGraphicsComponent
     */
    private void initLayeredGraphicsComponent(IComponent graphicsComponent, IGameObject parent) {
        //TODO: atlas name generalisieren!!!! <--- LEVEL_ATLAS_NAME schon spezialisiert
        TextureAtlas textureAtlas = this.assetLoader.loadTextureAtlas(parent.getAssetTargetType(), LEVEL_ATLAS_NAME, parent.getID());

        List<TextureLayer> layers = new ArrayList<>();
        Collections.addAll(layers, ((LayeredTexturesGraphicsComponent) graphicsComponent).getStaticLayers());
        Collections.addAll(layers, ((LayeredTexturesGraphicsComponent)graphicsComponent).getMovableLayers());

        for(TextureLayer layer : layers) {
            Sprite texture = new Sprite(textureAtlas.findRegion(layer.textureName));

            texture.setSize(
                texture.getWidth() * VIRTUAL_SCALE,
                texture.getHeight() * VIRTUAL_SCALE
            );

            AlignmentHelper.setRelativeOnScreenPosition(
                    texture,
                    layer.alignment,
                    layer.margin
            );

            // assign the layer's texture field
            texture.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            layer.texture = texture;
        }
    }

    private void initSpriteAnimationGraphicsComponent(IComponent component, IGameObject parent) {
        // init texture atlases
        ((SpriteAnimationGraphicsComponent)component).setTextures(
                this.assetLoader.loadTextureAtlases(parent.getAssetTargetType(), parent.getID())
        );
    }
}
