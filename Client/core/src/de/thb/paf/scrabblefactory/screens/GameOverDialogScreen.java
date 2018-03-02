package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_WIDTH;

/**
 * Represents the game over dialog screen.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameOverDialogScreen extends GameScreen {

    /**
     * The screen's dialog background image
     */
    private Image dialogBackground;

    /**
     * Default Constructor
     */
    public GameOverDialogScreen() {
        super(ScreenState.GAME_OVER);
        this.stage = new Stage(
                new ExtendViewport(DEVICE_SCREEN_WIDTH, DEVICE_SCREEN_HEIGHT, this.camera),
                this.batch
        );
    }

    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
    }

    @Override
    public void show() {
        this.applyProjectionMatrix();
        Gdx.input.setInputProcessor(stage);

        if(!this.isInitialized) {
            float scaling = this.initBackgroundScene();
            this.setupWidgets(scaling);
            this.isInitialized = true;
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        // TODO: Implement here...
    }

    @Override
    public void hide() {
        // TODO: Implement here...
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    /**
     * Initialize and setup the screen's background.
     */
    private float initBackgroundScene() {
        float scaling = (Settings.App.DEVICE_SCREEN_WIDTH / (float)Settings.Game.RESOLUTION.maxWidth);

        Texture dialogTexture = new Texture(
                Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/dialog_bg.png")
        );
        dialogTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        this.dialogBackground = new Image(dialogTexture);
        this.dialogBackground.setScale(scaling);

        Vector2 dialogPosition = AlignmentHelper.getRelativePosition(
                new Vector2(this.dialogBackground.getWidth() * scaling, this.dialogBackground.getHeight() * scaling),
                new Vector2(Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT),
                Alignment.MIDDLE,
                new int[] {0, 0, 0, 0}
        );
        this.dialogBackground.setPosition(dialogPosition.x, dialogPosition.y);

        this.stage.addActor(this.dialogBackground);
        return scaling;
    }

    /**
     * Setup all UI widgets required to represent the main menu.
     */
    private void setupWidgets(float scaling) {
        Texture stopTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/finish.png"));
        Texture stopPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/finishPressed.png"));
        stopTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        stopPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        Texture retryTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/retry.png"));
        Texture retryPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/retryPressed.png"));
        retryTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        retryPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;

        Label headline = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("GAME OVER")
                .alignment(Alignment.MIDDLE)
                .margins(
                        0, 0,
                        (int)((this.dialogBackground.getHeight() * scaling)/2) - (26 * multiplier),
                        0
                )
                .font(FontAsset.PORKY, 28 * multiplier, Color.BLACK)
                .create();

        Label description = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("Your time is up.\nDo you want to try the challenge again?")
                .alignment(Alignment.MIDDLE)
                .size(
                        (int)(this.dialogBackground.getWidth() * scaling) - (20 * multiplier),
                        50 * multiplier
                )
                .margins(
                        0, 0,
                        (int)((this.dialogBackground.getHeight() * scaling)/2)
                                - (28 * multiplier) - (int)headline.getHeight(),
                        10 * multiplier
                )
                .font(FontAsset.OPEN_SANS, 12 * multiplier, Color.BLACK)
                .create();

        description.setAlignment(Align.center);

        ImageButton stopGameBtn = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("stopGame")
                .alignment(Alignment.MIDDLE)
                .margins(
                        (int)((this.dialogBackground.getHeight() * scaling)/2)
                                - stopTexture.getHeight() - (10 * multiplier),
                        (int)((this.dialogBackground.getWidth() * scaling)/2)
                                - (stopTexture.getWidth()/2) - (50 * multiplier),
                        0, 0)
                .imageButtonTextures(stopTexture, stopPressedTexture)
                .actorGestureListener(
                        new ActorGestureListener() {
                            @Override
                            public void tap(InputEvent event, float x, float y, int count, int button) {
                                super.tap(event, x, y, count, button);
                                onButtonPressed(event.getListenerActor());
                            }
                        }
                )
                .create();

        ImageButton retryGameBtn = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("retryGame")
                .alignment(Alignment.MIDDLE)
                .margins(
                        (int)((this.dialogBackground.getHeight() * scaling)/2)
                                - retryTexture.getHeight() - (10 * multiplier),
                        0, 0,
                        (int)((this.dialogBackground.getWidth() * scaling)/2)
                                - (stopTexture.getWidth()/2) - (50 * multiplier))
                .imageButtonTextures(retryTexture, retryPressedTexture)
                .actorGestureListener(
                        new ActorGestureListener() {
                            @Override
                            public void tap(InputEvent event, float x, float y, int count, int button) {
                                super.tap(event, x, y, count, button);
                                onButtonPressed(event.getListenerActor());
                            }
                        }
                )
                .create();

        this.stage.addActor(headline);
        this.stage.addActor(description);
        this.stage.addActor(stopGameBtn);
        this.stage.addActor(retryGameBtn);
    }

    /**
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        switch(sender.getName()) {
            case "retryGame":
                Gdx.app.postRunnable(() -> {
                    GameScreenManager gsm = GameScreenManager.getInstance();
                    IGameScreen screen = gsm.getScreen(ScreenState.PLAY);
                    if(screen != null) {
                        ((PlayScreen)screen).resetLevel();
                        gsm.showLastScreen();
                    }
                });
                break;
            case "stopGame":
                Gdx.app.postRunnable(() -> {
                    this.goToScreen(ScreenState.MAIN_MENU);
                    GameScreenManager gsm = GameScreenManager.getInstance();
                    gsm.clearHistory();
                });
                break;
        }
    }

    /**
     * Navigate to a specific screen.
     * @param screenState The screen's state to navigate to
     */
    private void goToScreen(ScreenState screenState) {
        GameScreenManager gsm = GameScreenManager.getInstance();
        IGameScreen screen = gsm.getScreen(screenState);

        if(screen == null) {
            switch(screenState) {
                case MAIN_MENU:
                    screen = new MainMenuScreen();
                    break;
            }
        }
        gsm.showScreen(screen);
    }
}