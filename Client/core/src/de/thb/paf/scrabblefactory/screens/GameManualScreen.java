package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

/**
 * Represents the game's manual screen.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameManualScreen extends GameScreen {

    /**
     * The ui elements canvas holder
     */
    private Stage stage;

    /**
     * The button pressed audio resource
     */
    private Sound buttonPressedSound;

    /**
     * The game's manual text
     */
    private String manualText;

    /*
    * Default Constructor
    */
    public GameManualScreen() {
        super(ScreenState.GAME_MANUAL);

        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        this.loadManualText();
        float scaling = this.initBackgroundScene();
        this.setupSounds();
        this.setUpButtons(scaling);
        this.setupManualTextDisplay();
    }

    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
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
        // TODO: Implement here...
    }

    /**
     * Load the game's manual text.
     */
    private void loadManualText() {
        FileHandle manualTextFile = Gdx.files.internal("files/gamedescription.txt");
        this.manualText = manualTextFile.readString();
    }

    /**
     * Initialize and setup all sound resources.
     */
    private void setupSounds() {
        this.buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));
    }

    /**
     * Initialize and setup the screen's background.
     */
    private float initBackgroundScene() {
        Image background = new Image(new Texture(
                Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/background.png")
        ));

        float backgroundScaling = (Settings.App.DEVICE_SCREEN_WIDTH / background.getWidth());
        background.setScale(backgroundScaling);
        Vector2 backgroundPosition = AlignmentHelper.getRelativePosition(
                new Vector2(background.getWidth() * backgroundScaling, background.getHeight() * backgroundScaling),
                new Vector2(Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT),
                Alignment.MIDDLE,
                new int[] {0, 0, 0 ,0}
        );
        background.setPosition(backgroundPosition.x, backgroundPosition.y);

        this.stage.addActor(background);

        return backgroundScaling;
    }

    /**
     * Initialize and setup the screen's navigation buttons.
     * @param scale The button texture's scale constraint
     */
    private void setUpButtons(float scale){
        // resolution scale multiplier based on VIRTUAL width, height = 480 x 320
        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;

        ActorGestureListener gestureListener = new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                onButtonPressed(event.getListenerActor());
            }
        };

        Texture playTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapPlay.png"));
        Texture playPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapPlayPressed.png"));
        ImageButton playButton = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("play")
                .alignment(Alignment.BOTTOM_RIGHT)
                .margins(0, 15 * multiplier, 5 * multiplier, 0)
                .imageButtonTextures(playTexture, playPressedTexture)
                .actorGestureListener(gestureListener)
                .create();

        Texture backTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapBack.png"));
        Texture backPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapBackPressed.png"));
        ImageButton backButton = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("back")
                .alignment(Alignment.BOTTOM_RIGHT)
                .margins(0, (int)((25 * multiplier) + playButton.getWidth()), 5 * multiplier, 0)
                .imageButtonTextures(backTexture, backPressedTexture)
                .actorGestureListener(gestureListener)
                .create();

        this.stage.addActor(playButton);
        this.stage.addActor(backButton);
    }

    /**
     * Initialize and setup the display to display the game's manual text.
     */
    private void setupManualTextDisplay() {
        // resolution scale multiplier based on VIRTUAL width, height = 480 x 320
        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;

        int scaledWidth = (int)(Settings.App.DEVICE_SCREEN_WIDTH * 0.95f);
        int scaledHeight = (int)(Settings.App.DEVICE_SCREEN_HEIGHT * 0.75f);

        Label label = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title(this.manualText)
                .size(scaledWidth, scaledHeight)
                .alignment(Alignment.TOP_CENTER)
                .margins(0, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 10 * multiplier, Color.BLACK)
                .create();
        this.stage.addActor(label);
    }

    /**
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        buttonPressedSound.play();
        buttonPressedSound.dispose();

        switch(sender.getName()) {
            case "play":
                GameScreenManager.getInstance().setScreen(new PlayScreen());
                break;
            case "back":
                GameScreenManager.getInstance().setScreen(new MainMenuScreen());
                break;
        }
    }
}
