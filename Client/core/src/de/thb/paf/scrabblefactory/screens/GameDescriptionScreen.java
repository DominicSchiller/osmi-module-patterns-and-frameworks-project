package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import com.badlogic.gdx.utils.viewport.Viewport;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;

import static java.awt.Color.green;
import static java.awt.Color.red;

/**
 * Represents the Game description screen within description of game, how to play, further information
 *
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameDescriptionScreen extends GameScreen {

    /**
     * Declaration of world with and height in pixel
     */
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 320;

    /**
     * Declaration of Stage, Batch, Textures, Text
     * GlyphLayout is used to place the text
     */
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture backTexture;
    private Texture backPressTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private BitmapFont bitmapFont;
    private GlyphLayout layout = new GlyphLayout();
    private ScrabbleFactory game;

    private Viewport viewport;
    private Camera camera;

    /*
    * Default Constructor
    */
    public GameDescriptionScreen() {
        super(ScreenState.DESCRIPTION);
    }

    /*
    * Declaration of screen state
    */
    private ScreenState state = ScreenState.DESCRIPTION;


    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
    }

    @Override
    public void show() {

        /*
        * stage on description screen
         */
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        /*
        * background texture on the stage of description screen
        */
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        /*
        * sound of interactive elements
         */
        Sound tapsound = Gdx.audio.newSound(Gdx.files.internal("data/button click.mp3"));
        //backgroundmusic.getAssetManager().load("data/alright.mp3", Music.class);

        /*
        * text to describe the game, set on the batch
        */
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();

        /*
        * game play button (tap) on the stage
        */
        playTexture = new Texture(Gdx.files.internal("tapPlay.png"));
        playPressTexture = new Texture(Gdx.files.internal("tapPlayPressed.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        stage.addActor(play);
        play.setPosition(14 * WORLD_WIDTH / 16, 1 * WORLD_HEIGHT / 16, Align.center);

        /*
        * adds the play buttons listener an set it back to HomeScreen
        */
        play.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new PlayScreen());
            }
        });


        /*
        * back button (tap) on the stage
        */
        backTexture = new Texture(Gdx.files.internal("tapBack.png"));
        backPressTexture = new Texture(Gdx.files.internal("tapBackPressed.png"));
        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressTexture)));
        stage.addActor(back);
        back.setPosition(11 * WORLD_WIDTH / 16, 1 * WORLD_HEIGHT / 16, Align.center);

        /*
        * adds the back buttons listener an set it back to HomeScreen
        */
        back.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new HomeScreen());
            }
        });

    }

    public void draw (){
        batch.begin();
        String text = "This will be the description text";
        layout.setText(bitmapFont, text);
        bitmapFont.draw(batch, text, 80 * layout.width/480, layout.height *28);
        batch.end();
    }

    @Override
    public void render(float delta) {

        /*
        * default color of the description screen is red
         */
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch(state) {
            case DESCRIPTION: {
            }
            break;
            case PLAY: {
            }
            break;
            case MAIN_MENU: {
            }
            break;
        }

        /*
        * renders the stage
         */
        stage.act(delta);
        stage.draw();

        /*
        * renders everything out of render method
        * batch, text
         */
        draw();
    }

    @Override
    public void resize(int width, int height) {
        /*
        * calls the ViewPort Method for using the camera
         */
        stage.getViewport().update(width, height, true);
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
}
