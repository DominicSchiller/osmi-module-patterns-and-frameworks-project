package de.thb.paf.scrabblefactory.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.io.AssetLoader;
import de.thb.paf.scrabblefactory.managers.WorldPhysicsManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.RESOLUTION;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

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
    private static final int DEBUG_FONT_SIZE = 13;

    /**
     * The fps counter display
     */
    private BitmapFont fpsDisplay;

    /**
     * The physics debug renderer
     */
    private Box2DDebugRenderer physicsDebugRenderer;

    /**
     * Default Constructor
     */
    public VisualGameDebugger() {
        //init debug font
        int size = (int)(DEBUG_FONT_SIZE * VIRTUAL_PIXEL_DENSITY_MULTIPLIER);
        fpsDisplay = new AssetLoader().loadFont(
                FontAsset.OPEN_SANS,
                size,
                0,
                new Color(0xcc1331ff),
                Color.BLACK
        );
        fpsDisplay.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        float scaleFactor = RESOLUTION.virtualScaleFactor * 1/VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
        fpsDisplay.getData().setScale(scaleFactor);

        // init the debug renderer
        physicsDebugRenderer = new Box2DDebugRenderer();
        physicsDebugRenderer.SHAPE_AWAKE.r = 204/255f;
        physicsDebugRenderer.SHAPE_AWAKE.g = 19/255f;
        physicsDebugRenderer.SHAPE_AWAKE.b = 49/255f;
        physicsDebugRenderer.SHAPE_AWAKE.a = 1.0f;
    }

    /**
     * Renders all debug information to screen.
     * @param batch The game's global render batch
     */
    public void render(Batch batch) {
        Matrix4 projectionMatrix = batch.getProjectionMatrix();

        batch.begin();
        float x = 7;
        float y = (VIRTUAL_HEIGHT * PPM) - 35;

        fpsDisplay.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), x, y);
        batch.end();

//        Gdx.gl.glLineWidth(0.5f * GameInfo.VIRTUAL_SCREEN_MULTIPLYER);
        Gdx.gl.glLineWidth(3.0f);
        physicsDebugRenderer.render(
                WorldPhysicsManager.getInstance().getPhysicalWorld(),
                ScrabbleFactory.getInstance().batch.getProjectionMatrix());
    }
}
