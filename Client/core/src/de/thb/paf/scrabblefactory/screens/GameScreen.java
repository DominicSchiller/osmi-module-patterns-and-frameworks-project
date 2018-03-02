package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;

import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_WIDTH;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Abstract representation of a game screen.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public abstract class GameScreen implements IGameScreen {

    /**
     * A game screen's default font size
     */
    static int DEFAULT_FONT_SIZE;

    /**
     * A game screen's default label widget's height
     */
    static int DEFAULT_LABEL_HEIGHT;

    /**
     * A game screen's default input widget's height
     */
    static int DEFAULT_INPUT_HEIGHT;

    /**
     * A game screen's default widget's width
     */
    static int DEFAULT_WIDGET_WIDTH;

    /**
     * A game screen's default label background image
     */
    static Image DEFAULT_BACKGROUND;

    /**
     * The screen's state type
     */
    private final ScreenState state;

    /**
     * The screen's stage displaying Scene2D UI widgets
     */
    Stage stage;

    /**
     * The virtual camera
     */
    OrthographicCamera camera;

    /**
     * The OpenGL render batch
     */
    SpriteBatch batch;

    /**
     * State indicating whether the screen is set as active or not
     */
    boolean isActive;

    /**
     * State indicating whether the screen's components have been initialized or not
     */
    boolean isInitialized;

    // static inizializer
    static {
        float multiplier = Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
        DEFAULT_FONT_SIZE = (int)(12 * multiplier);
        DEFAULT_LABEL_HEIGHT = (int)(15 * multiplier);
        DEFAULT_INPUT_HEIGHT = (int)(25 * multiplier);
        DEFAULT_WIDGET_WIDTH = (int)(DEVICE_SCREEN_WIDTH * 0.6);

        DEFAULT_BACKGROUND = new Image(new Texture(
                Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name +"/backgrounds/background.png")
        ));
        Vector2 backgroundPosition = AlignmentHelper.getRelativePosition(
                new Vector2(DEFAULT_BACKGROUND.getWidth(), DEFAULT_BACKGROUND.getHeight()),
                new Vector2(DEVICE_SCREEN_WIDTH, DEVICE_SCREEN_HEIGHT),
                Alignment.TOP_CENTER,
                new int[] {0, 0, 0 ,0});
        DEFAULT_BACKGROUND.setPosition(backgroundPosition.x, backgroundPosition.y);
    }

    /**
     * Default Constructor
     */
    GameScreen(ScreenState state) {
        this.state = state;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, DEVICE_SCREEN_WIDTH, DEVICE_SCREEN_HEIGHT);
        this.batch = ScrabbleFactory.getInstance().batch;
        this.batch.setProjectionMatrix(this.camera.combined);
    }

    /**
     * Apply the current' camera's projection matrix to the global
     * LibGDX OpenGL render batch.
     */
    void applyProjectionMatrix() {
        this.batch.setProjectionMatrix(this.camera.combined);
    }


    @Override
    public void resize(int width, int height) {
        if(this.isActive && !this.isInitialized) {
            this.show();
        } else {
            this.isActive = true;
        }
    }

    @Override
    public ScreenState getState() {
        return this.state;
    }
}
