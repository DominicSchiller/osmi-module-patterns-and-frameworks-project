package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.settings.Settings;

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
    private Texture highscoreTexture,highscorePressTexture;
    private Music backgroundmusic;
    private Sound tapsound;
    private GlyphLayout layout = new GlyphLayout();
    private SpriteBatch batch;
    private BitmapFont bitmapFont;

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
        * Background music & sound of interacitve elements
         */
        backgroundmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/alright.mp3"));
        backgroundmusic.setLooping(true);
        backgroundmusic.play();

        tapsound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));

        /*
        * background texture on the stage
         */
        backgroundTexture = new Texture(Gdx.files.internal("images/backgrounds/home-background.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        /*
        * title on the stage adds actor title
         */
        titleTexture = new Texture(Gdx.files.internal("images/backgrounds/scrabblefactory-title.png"));
        Image title = new Image(titleTexture);

        title.setPosition(WORLD_WIDTH /2, 2 * WORLD_HEIGHT / 4, Align.center);

        stage.addActor(title);

        /*
        *This Method sets all buttons and their listeners
        */
        setUpButtons();


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

    private void setUpButtons() {



    /*
    * game highscore button (tap) on the stage adds actor highscore (button)
    */
    highscoreTexture = new Texture(Gdx.files.internal("images/buttons/tap.png"));
    highscorePressTexture = new Texture(Gdx.files.internal("images/buttons/tapPressed.png"));

    ImageButton highscore = new ImageButton(new TextureRegionDrawable(new TextureRegion(highscoreTexture)), new TextureRegionDrawable(new TextureRegion(highscorePressTexture)));

    stage.addActor(highscore);
    highscore.setPosition(WORLD_WIDTH / 2, 10 * WORLD_HEIGHT / 32, Align.center);

    /*
    * adds the highscores buttons listener an set it to the GameHighscoreScreen
    */
    highscore.addListener(new ActorGestureListener() {

    @Override
        public void tap(InputEvent event, float x, float y, int count, int button) {
            super.tap(event, x, y, count, button);
            backgroundmusic.stop();
            backgroundmusic.dispose();
            tapsound.play();
            tapsound.dispose();
            GameScreenManager.getInstance().setScreen(new GameHighscoreScreen());
        }
    });

        /*
        * start button (tap) on the stage adds actor start
        * */
        startTexture = new Texture(Gdx.files.internal("images/buttons/tap.png"));
        startPressTexture = new Texture(Gdx.files.internal("images/buttons/tapPressed.png"));

        ImageButton start = new ImageButton(new TextureRegionDrawable(new TextureRegion(startTexture)), new TextureRegionDrawable(new TextureRegion(startPressTexture)));

        stage.addActor(start);
        start.setPosition(WORLD_WIDTH / 2, 18 * WORLD_HEIGHT / 32, Align.center);

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
                stage = new Stage(new FitViewport(Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT));
                GameScreenManager.getInstance().setScreen(new PlayScreen());
            }
        });

        /*
        * game description button (tap) on the stage adds actor description (back button)
        * */
        descriptionTexture = new Texture(Gdx.files.internal("images/buttons/tap.png"));
        descriptionPressTexture = new Texture(Gdx.files.internal("images/buttons/tapPressed.png"));

        ImageButton description = new ImageButton(new TextureRegionDrawable(new TextureRegion(descriptionTexture)), new TextureRegionDrawable(new TextureRegion(descriptionPressTexture)));

        stage.addActor(description);
        description.setPosition(WORLD_WIDTH / 2, 14 * WORLD_HEIGHT / 32, Align.center);

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
}
