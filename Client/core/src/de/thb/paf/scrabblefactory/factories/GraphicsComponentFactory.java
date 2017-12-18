package de.thb.paf.scrabblefactory.factories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.models.IGameObject;

import de.thb.paf.scrabblefactory.models.actions.IGameAction;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.components.graphics.BasicGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.FontGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.LayeredTexturesGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.SpriteAnimationGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.TextureLayer;
import de.thb.paf.scrabblefactory.utils.CloneComponentHelper;
import de.thb.paf.scrabblefactory.utils.ScrabbleFactoryClassLoader;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;

import static de.thb.paf.scrabblefactory.settings.Constants.Files.HUD_ATLAS_NAME;
import static de.thb.paf.scrabblefactory.settings.Constants.Files.LEVEL_ATLAS_NAME;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
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
    public IComponent getGfxComponent(Class<?> classType, JsonObject componentDef, IGameObject parent) {
        Gson gson = new GsonBuilder().create();

        //TODO: implement id calculation
        IComponent component = (IComponent) ScrabbleFactoryClassLoader.createInstance(classType, 1);
        IComponent parsedComponent = gson.fromJson(componentDef, component.getClass());

        CloneComponentHelper.cloneFieldValues(parsedComponent, component);
        component.setParent(parent);

        // initialize the component's special content
        this.initGfxComponent(component, parent);

        // initialize associated game actions
        this.initActions(componentDef, component);

        return component;
    }

    /**
     * Initializes the content of the passed graphics components.
     * @param graphicsComponent The graphics component to setup
     * @param parent The parent game object
     */
    private void initGfxComponent(IComponent graphicsComponent, IGameObject parent) {
        if(graphicsComponent instanceof BasicGraphicsComponent) {
            initBasicGraphicsComponent(graphicsComponent, parent);
        } else if(graphicsComponent instanceof LayeredTexturesGraphicsComponent) {
            initLayeredGraphicsComponent(graphicsComponent, parent);
        } else if(graphicsComponent instanceof SpriteAnimationGraphicsComponent) {
            this.initSpriteAnimationGraphicsComponent(graphicsComponent, parent);
        } else if(graphicsComponent instanceof FontGraphicsComponent) {
            initFontGraphicsComponent(graphicsComponent, parent);
        }
    }

    /**
     * Initializes the content of a base sprite graphics component.
     * @param graphicsComponent The graphics component to assemble the content for
     * @param parent The parent game object
     * @see BasicGraphicsComponent
     */
    private void initBasicGraphicsComponent(IComponent graphicsComponent, IGameObject parent) {
        TextureAtlas textureAtlas;
        switch(parent.getAssetTargetType()) {
            case HUD:
                textureAtlas = this.assetLoader.loadTextureAtlas(parent.getAssetTargetType(), HUD_ATLAS_NAME, parent.getID());
                break;
            default:
                textureAtlas = null;
                break;
        }

        if(textureAtlas != null) {
            BasicGraphicsComponent basicGraphicsComponent = (BasicGraphicsComponent)graphicsComponent;
            basicGraphicsComponent.texture = this.initSprite(
                    textureAtlas,
                    basicGraphicsComponent.textureName,
                    basicGraphicsComponent.alignment,
                    basicGraphicsComponent.margin
            );

            if(basicGraphicsComponent.isRelativeToParent) {
                // calculates the relative position in the parent canvas and apply the padding
                Vector2 relativePosition = AlignmentHelper.getRelativePosition(
                        new Vector2(
                                basicGraphicsComponent.texture.getWidth(),
                                basicGraphicsComponent.texture.getHeight()
                        ),
                        new Vector2(
                                parent.getSize().x / PPM,
                                parent.getSize().y / PPM
                        ),
                        basicGraphicsComponent.alignment,
                        basicGraphicsComponent.margin
                );

                basicGraphicsComponent.texture.setPosition(
                      parent.getPosition().x + relativePosition.x,
                        parent.getPosition().y + relativePosition.y
                );
            }
        }
    }

    /**
     * Initializes the content of a font graphics component.
     * @param graphicsComponent The graphics component to assemble the content for
     * @param parent The parent game object
     * @see FontGraphicsComponent
     */
    private void initFontGraphicsComponent(IComponent graphicsComponent, IGameObject parent) {
        FontGraphicsComponent fontGraphicsComponent = (FontGraphicsComponent)graphicsComponent;

        BitmapFont font = new AssetLoader().loadFont(
                fontGraphicsComponent.fontAsset,
                (int)(fontGraphicsComponent.fontSize * VIRTUAL_PIXEL_DENSITY_MULTIPLIER),
                (int)(fontGraphicsComponent.borderWidth * VIRTUAL_PIXEL_DENSITY_MULTIPLIER),
                Color.WHITE,
                Color.BLACK
        );

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        float scaleFactor = RESOLUTION.virtualScaleFactor * 1/VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
        font.getData().setScale(scaleFactor);
        fontGraphicsComponent.font = font;

        if(fontGraphicsComponent.isRelativeToParent) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, "x");

            // calculates the relative position in the parent canvas and apply the padding
            Vector2 relativePosition = AlignmentHelper.getRelativePosition(
                    new Vector2(
                            glyphLayout.width / PPM,
                            (glyphLayout.height) / PPM
                    ),
                    new Vector2(
                            parent.getSize().x / PPM,
                            parent.getSize().y / PPM
                    ),
                    fontGraphicsComponent.alignment,
                    fontGraphicsComponent.margin
            );

            fontGraphicsComponent.position = new Vector2(
                    parent.getPosition().x + relativePosition.x,
                    parent.getPosition().y + relativePosition.y
            );
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

    /**
     * Initializes the content of a sprite animation graphics component.
     * @param graphicsComponent The graphics component to assemble the content for
     * @param parent The parent game object
     * @see SpriteAnimationGraphicsComponent
     */
    private void initSpriteAnimationGraphicsComponent(IComponent graphicsComponent, IGameObject parent) {
        // init texture atlases
        SpriteAnimationGraphicsComponent animationGraphicsComponent = (SpriteAnimationGraphicsComponent)graphicsComponent;
        animationGraphicsComponent.setTextures(
                this.assetLoader.loadTextureAtlases(
                        parent.getAssetTargetType(),
                        animationGraphicsComponent.atlasNames,
                        parent.getID()
                )
        );
    }

    /**
     * Initialize a sprite texture
     * @param textureAtlas The texture atlas to load the texture from
     * @param textureName The texture's name to load
     * @param alignment The sprite's relative on screen position
     * @param margin The sprite's relative on screen margin
     * @return The initialized sprite instance
     */
    private Sprite initSprite(TextureAtlas textureAtlas, String textureName, Alignment alignment, int[] margin) {
        Sprite texture = new Sprite(textureAtlas.findRegion(textureName));

        texture.setSize(
                texture.getWidth() * VIRTUAL_SCALE,
                texture.getHeight() * VIRTUAL_SCALE
        );

        AlignmentHelper.setRelativeOnScreenPosition(
                texture,
                alignment,
                margin
        );

        // assign the layer's texture field
        texture.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }

    /**
     * Registers the component to listen for defined events.
     * @param component The component to register for event observing
     */
    private void initActions(JsonObject componentDef, IComponent component) {
        JsonArray actionDefs = componentDef.getAsJsonArray("actions");
        ActionFactory actionFactory = new ActionFactory();
        if(actionDefs != null) {
            System.out.println();
            for(JsonElement actionDef : actionDefs) {
                IGameAction action = actionFactory.getGameAction(actionDef.getAsJsonObject(), component);
                component.addAction(action);
            }
        }
    }
}
