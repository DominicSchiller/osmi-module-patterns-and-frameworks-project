package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.thb.paf.scrabblefactory.io.SaveGameHandler;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.persistence.DataStore;
import de.thb.paf.scrabblefactory.persistence.entities.SaveGame;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

/**
 * Represents the game's landing screen where to choose between login or creating a new account.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class LandingScreen extends GameScreen {

    /**
     * The buttons' on-click sound
     */
    private Sound buttonPressedSound;

    /**
     * Default Constructor
     */
    public LandingScreen() {
        super(ScreenState.LANDING);
        this.stage = new Stage();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        this.stage.addActor(DEFAULT_BACKGROUND);
        this.setupUIWidgets();
        this.setupSounds();
        this.isInitialized = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1/255f, 8/255f, 15/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.buttonPressedSound.dispose();
    }

    /**
     * Verify if the user can directly login into the game.
     * @return Status if direct login is available
     */
    private boolean isDirectLogin() {
        DataStore ds = DataStore.getInstance();
        boolean isDirectLogin = ds.readAllUsers().size() > 0;
        return isDirectLogin;
    }

    /**
     * Setup all UI widgets required to represent a login form.
     */
    private void setupUIWidgets() {
        boolean isDirectLogin = this.isDirectLogin();
        float scaling = Settings.App.DEVICE_SCREEN_WIDTH/(float)Settings.Game.RESOLUTION.maxWidth;
        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;

        if(isDirectLogin) {
            Button loginButton = (TextButton)new UIWidgetBuilder(UIWidgetType.TEXT_BUTTON)
                    .identifier("login")
                    .title("Login")
                    .size(DEFAULT_WIDGET_WIDTH, DEFAULT_INPUT_HEIGHT * 2)
                    .alignment(Alignment.TOP_CENTER)
                    .margins((int)(75* Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER), 0, 0, 0)
                    .clickListener(
                            new ClickListener() {
                                @Override
                                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                    super.touchUp(event, x, y, pointer, button);
                                    onButtonPressed(event.getListenerActor());
                                }
                            }
                    )
                    .create();

            this.stage.addActor(loginButton);

            Texture exportTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/export.png"));
            Texture exportPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/exportPressed.png"));
            exportTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
            exportPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

            ImageButton exportBtn = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                    .identifier("export")
                    .size((int)(exportTexture.getWidth() * scaling), (int)(exportTexture.getHeight() * scaling))
                    .alignment(Alignment.BOTTOM_RIGHT)
                    .margins(
                            0,
                            (30 * multiplier) + (int)(exportTexture.getWidth() * scaling),
                            (15 * multiplier),
                            0
                    )
                    .imageButtonTextures(exportTexture, exportPressedTexture)
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
            this.stage.addActor(exportBtn);
        }

        Alignment alignment = isDirectLogin ? Alignment.TOP_CENTER : Alignment.MIDDLE;
        int padding = isDirectLogin ?
                (int)(120* Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER) + DEFAULT_INPUT_HEIGHT
                : (int)(-25* Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER);
        Button createAccountButton = (TextButton)new UIWidgetBuilder(UIWidgetType.TEXT_BUTTON)
                .identifier("register")
                .title("Create New Account")
                .size(DEFAULT_WIDGET_WIDTH, DEFAULT_INPUT_HEIGHT * 2)
                .alignment(alignment)
                .margins(padding, 0, 0, 0)
                .clickListener(
                        new ClickListener() {
                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                super.touchUp(event, x, y, pointer, button);
                                onButtonPressed(event.getListenerActor());
                            }
                        }
                )
                .create();
        this.stage.addActor(createAccountButton);

        Texture importTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/import.png"));
        Texture importPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/importPressed.png"));
        importTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        importPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        ImageButton importBtn = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("import")
                .size((int)(importTexture.getWidth() * scaling), (int)(importTexture.getHeight() * scaling))
                .alignment(Alignment.BOTTOM_RIGHT)
                .margins(
                        0,
                        (20 * multiplier),
                        (15 * multiplier),
                        0
                )
                .imageButtonTextures(importTexture, importPressedTexture)
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

        this.stage.addActor(importBtn);
    }

    /**
     * Initialize and setup all sound resources.
     */
    private void setupSounds() {
        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));
//        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/alright.mp3"));
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();
    }

    /**
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        this.buttonPressedSound.play();

        switch(sender.getName()) {
            case "login":
                this.goToScreen(ScreenState.LOGIN);
                break;
            case "register":
                this.goToScreen(ScreenState.REGISTER_ACCOUNT);
                break;
            case "import":
                this.triggerImport();
                break;
            case "export":
                this.triggerExport();
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
                case LOGIN:
                    screen = new LoginScreen();
                    break;
                case REGISTER_ACCOUNT:
                    screen = new RegisterAccountScreen();
                    break;
            }
        }

        gsm.showScreen(screen);
    }

    /**
     * Trigger the import save-game dialog.
     */
    private void triggerImport() {
        SaveGame saveGame = new SaveGameHandler().load();
        if(saveGame != null) {
            DataStore dataStore = DataStore.getInstance();
            dataStore.restoreDatabaseFromSaveGame(saveGame);

            // reset the UI widget stage before reloading
            for(Actor actor : this.stage.getActors()) {
                actor.remove();
            }
            this.show();
        }
    }

    /**
     * Try to export a save-game.
     */
    private void triggerExport() {
        DataStore dataStore = DataStore.getInstance();
        SaveGame saveGame = new SaveGame(
                dataStore.readAllUsers(),
                dataStore.readAllScores(),
                dataStore.readAllUserScores()
        );
        new SaveGameHandler().save(saveGame, true);
    }
}
