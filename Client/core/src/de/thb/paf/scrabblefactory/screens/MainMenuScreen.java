package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

/**
 * Represents the home screen where all screen navigation options for player will be presented.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class MainMenuScreen extends GameScreen {

    /**
     * The ui elements canvas holder
     */
    private Stage stage;

    /**
     * The screen's background music resource
     */
    private Music backgroundMusic;

    /**
     * The button pressed audio resource
     */
    private Sound buttonPressedSound;

    /**
     * Default Constructor
     */
    public MainMenuScreen() {
        super(ScreenState.MAIN_MENU);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.initBackgroundScene();
        this.setupUIWidgets();
        this.setupSounds();
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
        Gdx.gl.glClearColor(1/255f, 8/255f, 15/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO: Implement here...
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

    }

    /**
     * Initialize and setup the screen's background.
     */
    private void initBackgroundScene() {
        Image background = new Image(new Texture(
                Gdx.files.internal("images/backgrounds/home-background.png")
        ));

        Image titleImage = new Image(new Texture(
                Gdx.files.internal("images/backgrounds/scrabblefactory-title.png")
        ));
        Vector2 titlePosition = AlignmentHelper.getRelativePosition(
                new Vector2(titleImage.getWidth(), titleImage.getHeight()),
                new Vector2(Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT),
                Alignment.TOP_CENTER,
                new int[] {100, 0, 0 ,0}
        );
        titleImage.setPosition(titlePosition.x, titlePosition.y);

        this.stage.addActor(background);
        this.stage.addActor(titleImage);
    }

    /**
     * Setup all UI widgets required to represent the main menu.
     */
    private void setupUIWidgets() {
        Texture buttonDefaultTexture = new Texture(Gdx.files.internal("images/buttons/tap.png"));
        Texture buttonPressedTexture = new Texture(Gdx.files.internal("images/buttons/tapPressed.png"));

        this.addLabelImageButtonGroup("Play", "play", 200, buttonDefaultTexture, buttonPressedTexture);
        this.addLabelImageButtonGroup("Highscores", "highScore", 260, buttonDefaultTexture, buttonPressedTexture);
        this.addLabelImageButtonGroup("Game Description", "description", 320, buttonDefaultTexture, buttonPressedTexture);
    }

    /**
     * Initialize and setup all sound resources.
     */
    private void setupSounds() {
        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/alright.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    /**
     * Create and add a new label-image button group to the stage.
     * @param labelTitle The label's title
     * @param identifier The image button's identifier
     * @param marginTop The image button's top margin
     * @param buttonTextures The button textures to apply
     */
    private void addLabelImageButtonGroup(String labelTitle, String identifier, int marginTop, Texture... buttonTextures) {
        ImageButton imaegButton = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier(identifier)
                .alignment(Alignment.TOP_CENTER)
                .margins(marginTop, 0, 0, 0)
                .imageButtonTextures(buttonTextures)
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

        Label label = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title(labelTitle)
                .identifier(identifier)
                .alignment(Alignment.TOP_CENTER)
                .margins(marginTop, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 28, Color.WHITE)
                .clickListener(new ClickListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        super.touchUp(event, x, y, pointer, button);
                        onButtonPressed(event.getListenerActor());
                    }
                })
                .create();

        label.setPosition(
            imaegButton.getX() + 75,
                imaegButton.getY()
        );

        this.stage.addActor(imaegButton);
        this.stage.addActor(label);
    }

    /**
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        backgroundMusic.stop();
        backgroundMusic.dispose();
        buttonPressedSound.play();
        buttonPressedSound.dispose();

        switch(sender.getName()) {
            case "play":
                GameScreenManager.getInstance().setScreen(new PlayScreen());
                break;
            case "highScore":
                GameScreenManager.getInstance().setScreen(new GameHighscoreScreen());
                break;
            case "description":
                GameScreenManager.getInstance().setScreen(new GameDescriptionScreen());
                break;
        }
    }
}
