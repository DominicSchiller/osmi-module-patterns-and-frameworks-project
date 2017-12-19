package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.factories.EntityFactory;
import de.thb.paf.scrabblefactory.factories.HUDSystemFactory;
import de.thb.paf.scrabblefactory.factories.LevelFactory;
import de.thb.paf.scrabblefactory.io.UserInputProcessor;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.models.hud.HUDSystem;
import de.thb.paf.scrabblefactory.models.hud.HUDSystemType;
import de.thb.paf.scrabblefactory.models.hud.IHUDComponent;
import de.thb.paf.scrabblefactory.models.level.ILevel;
import de.thb.paf.scrabblefactory.utils.debug.VisualGameDebugger;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Represents the play screen where all single and multi-player levels take place.
 *
 * @author Dominic Schiller, Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class PlayScreen extends GameScreen {

    private UserInputProcessor userInputProcessor;
    private VisualGameDebugger debugRenderer;
    private OrthographicCamera camera;
    private ILevel level;
    private HUDSystem hud;
    private IEntity player;
    private Music levelmusic;
    private Sound tapsound;
    private Texture pauseTexture, pausePressTexture;
    private Texture stopTexture, stopPressTexture;
    private Texture playTexture, playPressTexture;

    private GlyphLayout layout = new GlyphLayout();
    private Stage stage;
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 320;

    /**
     * Default Constructor
     */
    public PlayScreen() {
          super(ScreenState.PLAY);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(
                false,
                VIRTUAL_WIDTH,
                VIRTUAL_HEIGHT);

        ScrabbleFactory.getInstance().batch.setProjectionMatrix(
                this.camera.combined
        );

        ScrabbleFactory.getInstance().textBatch.setProjectionMatrix(
                this.camera.combined.cpy().scl(1/PPM)
        );
    }

    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
        if(this.isInitialized) {
            this.level.update(deltaTime);
            this.player.update(deltaTime);
        }
    }

    @Override
    public void show() {
        this.level = new LevelFactory().getLevel(1);
        this.hud = new HUDSystemFactory().getHUDSystem(HUDSystemType.SINGLE_PLAYER_HUD);
        this.player = new EntityFactory().getEntity(EntityType.PLAYER, 1);
        this.debugRenderer = new VisualGameDebugger();

        /**
         * Level sound and music
         */
        levelmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/alrightlevel.mp3"));
        levelmusic.setLooping(true);
        levelmusic.play();
        tapsound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));

        /**
         * All navigation elements on the stage
         */
//        this.stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
//        Gdx.input.setInputProcessor(stage);
//        setUpButtons();

        this.userInputProcessor = new UserInputProcessor();
        this.isInitialized = true;
    }

    @Override
    public void render(float delta) {
        // just render the content if initialized so far
        if(this.isInitialized) {
            // update the screen first before rendering it's content
            this.update(delta);

            Gdx.gl.glClearColor(1/255f, 8/255f, 15/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            Batch batch = ScrabbleFactory.getInstance().batch;
            Batch textBatch = ScrabbleFactory.getInstance().textBatch;

            //TODO Implement more elegant method to render components
            List<IComponent> components = this.level.getAllComponents(ComponentType.GFX_COMPONENT);
            for(IComponent component : components) {
                ((IGraphicsComponent) component).render(batch);
            }

            components = this.player.getAllComponents(ComponentType.GFX_COMPONENT);
            for(IComponent component : components) {
                ((IGraphicsComponent) component).render(batch);
            }

            // render HUD components
            List<IHUDComponent> hudComponents = this.hud.getHUDComponents();
            for(IGameObject hudComponent: hudComponents) {
                components = hudComponent.getAllComponents(ComponentType.GFX_COMPONENT);
                for(IComponent component : components) {
                    ((IGraphicsComponent) component).render(batch);
                }
            }

//            stage.act(delta);
//            stage.draw();

            this.debugRenderer.render(textBatch);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
//        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        /*
        * change default background color to grey
        */
        Gdx.gl.glClearColor(87, 87, 87, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    private void setUpButtons(){

        pauseTexture = new Texture(Gdx.files.internal("images/buttons/tapPause.png"));
        pausePressTexture = new Texture(Gdx.files.internal("images/buttons/tapPausePressed.png"));

        ImageButton pause = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)), new TextureRegionDrawable(new TextureRegion(pausePressTexture)));

        pause.setPosition(14 * WORLD_WIDTH / 16, 2 * WORLD_HEIGHT / 32, Align.center);
        this.stage.addActor(pause);

        stopTexture = new Texture(Gdx.files.internal("images/buttons/tapStop.png"));
        stopPressTexture = new Texture(Gdx.files.internal("images/buttons/tapStopPressed.png"));

        ImageButton stop = new ImageButton(new TextureRegionDrawable(new TextureRegion(stopTexture)), new TextureRegionDrawable(new TextureRegion(stopPressTexture)));

        stop.setPosition(11 * WORLD_WIDTH / 16, 2 * WORLD_HEIGHT / 32, Align.center);
        this.stage.addActor(stop);

        /*
        * adds the buttons listener an set it to the HomeScreen
         */
        stop.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                levelmusic.stop();
                levelmusic.dispose();
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new HomeScreen());
            }
        });

        /*
        * adds the buttons listener an set it to show dialog
         */
        pause.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                levelmusic.stop();
                levelmusic.dispose();
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new GameDialogScreen());
            }
        });

    }
}
