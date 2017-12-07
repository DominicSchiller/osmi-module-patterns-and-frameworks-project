package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;

/**
 * Represents the home screen where all screen navigation options for player will be presented.
 *
 * @author Dominic Schiller, Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class HomeScreen extends GameScreen {

    /**
     * Declaration of world with and height in pixel
     */
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 320;

    /**
     * Declaration of stage and textures
     */
    private Stage stage;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private Texture startPressTexture,startTexture;
    private Texture descriptionTexture,descriptionPressTexture;

    /**
     * Default Constructor
     */
    public HomeScreen() {
        super(ScreenState.MAIN_MENU);
    }


    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
    }

    @Override
    public void show() {

        /*
        * stage on home screen
         */
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        /*
        * background texture on the stage
         */
        backgroundTexture = new Texture(Gdx.files.internal("home-background.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        /*
        * title on the stage adds actor title
         */
        titleTexture = new Texture(Gdx.files.internal("scrabblefactory-title.png"));
        Image title = new Image(titleTexture);

        title.setPosition(WORLD_WIDTH /2, 2 * WORLD_HEIGHT / 4,    Align.center);

        stage.addActor(title);

        /*
        * Background music & sound of interacitve elements
         */
        //backgroundmusic.getAssetManager().load("data/alright.mp3", Music.class);
        Music backgroundmusic = Gdx.audio.newMusic(Gdx.files.internal("data/alright.mp3"));
        backgroundmusic.setLooping(true);
        backgroundmusic.play();

        Sound tapsound = Gdx.audio.newSound(Gdx.files.internal("data/button click.mp3"));

        /*
        * start button (tap) on the stage adds actor start
        * */
        startTexture = new Texture(Gdx.files.internal("tap.png"));
        startPressTexture = new Texture(Gdx.files.internal("tapPressed.png"));

        ImageButton start = new ImageButton(new TextureRegionDrawable(new TextureRegion(startTexture)), new TextureRegionDrawable(new TextureRegion(startPressTexture)));

        stage.addActor(start);
        start.setPosition(WORLD_WIDTH / 2, 8 * WORLD_HEIGHT / 16, Align.center);

        /*
        * adds the buttons listener an set it to the PlayScreen
         */
        start.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                backgroundmusic.stop();
                backgroundmusic.dispose();
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new PlayScreen());
            }
        });

        /*
        * game description button (tap) on the stage adds actor description (back button)
        * */
        descriptionTexture = new Texture(Gdx.files.internal("tap.png"));
        descriptionPressTexture = new Texture(Gdx.files.internal("tapPressed.png"));

        ImageButton description = new ImageButton(new TextureRegionDrawable(new TextureRegion(descriptionTexture)), new TextureRegionDrawable(new TextureRegion(descriptionPressTexture)));

        stage.addActor(description);
        description.setPosition(WORLD_WIDTH / 2, 5 * WORLD_HEIGHT / 16, Align.center);

        /*
        * adds the description buttons listener an set it to the GameDescriptionScreen
         */
        description.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                backgroundmusic.stop();
                backgroundmusic.dispose();
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new GameDescriptionScreen());
            }
        });

    }

    @Override
    public void render(float delta) {

        /*
        * default background color
         */
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
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
}
