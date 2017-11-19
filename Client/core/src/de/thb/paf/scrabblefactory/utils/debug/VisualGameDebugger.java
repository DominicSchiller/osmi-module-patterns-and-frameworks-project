package de.thb.paf.scrabblefactory.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.IPhysicsComponent;
import de.thb.paf.scrabblefactory.models.components.physics.PhysicsType;
import de.thb.paf.scrabblefactory.models.components.physics.WorldPhysicsComponent;
import de.thb.paf.scrabblefactory.settings.Settings;

/**
 * Utility class helping visualizing FPS and physical Box2D elements.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class VisualGameDebugger {

    /**
     * The debugger's default font size
     */
    private static final short DEBUG_FONT_SIZE = 12;

    /**
     * The fps counter display
     */
    private BitmapFont fpsDisplay;

    /**
     * The physics debug renderer
     */
    private Box2DDebugRenderer physicsDebugRenderer;

    /**
     * The physical Box2D world to render
     */
    private World physicalWorld;

    /**
     * Default Constructor
     */
    public VisualGameDebugger() {
        //init debug font
        fpsDisplay = new AssetLoader().loadFont(
                FontAsset.SAN_FRANCISCO,
                (int)(DEBUG_FONT_SIZE * Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER),
                0,
                new Color(0xcc1331ff),
                Color.BLACK
        );
        fpsDisplay.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fpsDisplay.getData().setScale(Settings.Game.VIRTUAL_SCALE, Settings.Game.VIRTUAL_SCALE);

        // init the debug renderer
        physicsDebugRenderer = new Box2DDebugRenderer();
        physicsDebugRenderer.SHAPE_AWAKE.r = 204/255f;
        physicsDebugRenderer.SHAPE_AWAKE.g = 19/255f;
        physicsDebugRenderer.SHAPE_AWAKE.b = 49/255f;
        physicsDebugRenderer.SHAPE_AWAKE.a = 1.0f;

        // retrieve physical world
        List<IComponent> physicsComponents =  GameObjectManager.getInstance()
                .getComponentsByType(ComponentType.PHYS_COMPONENT);
        for(IComponent physicsComponent : physicsComponents) {
            if(((IPhysicsComponent)physicsComponent).getPhysicsType().equals(PhysicsType.WORLD)) {
                this.physicalWorld = ((WorldPhysicsComponent)physicsComponent).getWorld();
            }
        }

    }

    /**
     * Renders all debug information to screen.
     * @param batch The game's global render batch
     * @param cameraProjectionMatrix The current camera's projection-view matrix
     */
    public void render(Batch batch, Matrix4 cameraProjectionMatrix) {
        batch.begin();
        fpsDisplay.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 5, Settings.Game.VIRTUAL_HEIGHT - 12);
        batch.end();

//        Gdx.gl.glLineWidth(0.5f * GameInfo.VIRTUAL_SCREEN_MULTIPLYER);
        Gdx.gl.glLineWidth(3.0f);
        physicsDebugRenderer.render(this.physicalWorld, cameraProjectionMatrix);
    }
}
