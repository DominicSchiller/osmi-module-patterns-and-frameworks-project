package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.List;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.factories.EntityFactory;
import de.thb.paf.scrabblefactory.factories.HUDSystemFactory;
import de.thb.paf.scrabblefactory.factories.LevelFactory;
import de.thb.paf.scrabblefactory.gameplay.GameItemSpawnCenter;
import de.thb.paf.scrabblefactory.gameplay.GameItemSpawnPool;
import de.thb.paf.scrabblefactory.gameplay.ScrabbleChallengeWatchdog;
import de.thb.paf.scrabblefactory.gameplay.timer.CountdownTimer;
import de.thb.paf.scrabblefactory.gameplay.timer.ICountdownListener;
import de.thb.paf.scrabblefactory.io.KeyboardInputProcessor;
import de.thb.paf.scrabblefactory.io.TouchInputProcessor;
import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.models.events.RemainingTimeUpdateEvent;
import de.thb.paf.scrabblefactory.models.hud.HUDComponentType;
import de.thb.paf.scrabblefactory.models.hud.HUDSystem;
import de.thb.paf.scrabblefactory.models.hud.HUDSystemType;
import de.thb.paf.scrabblefactory.models.hud.IHUDComponent;
import de.thb.paf.scrabblefactory.models.hud.SearchWordHUD;
import de.thb.paf.scrabblefactory.models.level.BasicLevel;
import de.thb.paf.scrabblefactory.models.level.ILevel;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.Randomizer;
import de.thb.paf.scrabblefactory.utils.debug.VisualGameDebugger;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

import static de.thb.paf.scrabblefactory.models.events.GameEventType.REMAINING_TIME_UPDATE;
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
public class PlayScreen extends GameScreen implements ICountdownListener {

    private Image overlay;
    private VisualGameDebugger debugRenderer;
    private OrthographicCamera camera;
    private ILevel level;
    private HUDSystem hud;
    private IEntity player;
    private Music levelmusic;

    private GameItemSpawnCenter spawnCenter;
    private ScrabbleChallengeWatchdog challengeWatchdog;
    private CountdownTimer timer;
    private Sound wonSound;

    private Stage stage;
    private InputMultiplexer inputHandler;
    private boolean isPauseRequested;

    /**
     * Default Constructor
     */
    public PlayScreen() {
        super(ScreenState.PLAY);
        this.isPauseRequested = false;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        ScrabbleFactory.getInstance().batch.setProjectionMatrix(
            this.camera.combined
        );

        ScrabbleFactory.getInstance().textBatch.setProjectionMatrix(
            this.camera.combined.cpy().scl(1/PPM)
        );

        this.stage = new Stage(new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, this.camera));
        // setup input processors
        this.inputHandler = new InputMultiplexer();
        this.inputHandler.addProcessor(new KeyboardInputProcessor());
        this.inputHandler.addProcessor(new GestureDetector(new TouchInputProcessor()));
        this.inputHandler.addProcessor(this.stage);
    }

    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
        if(this.isInitialized) {
            this.level.update(deltaTime);
            GameObjectManager.getInstance().updateGameObjects(deltaTime);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.inputHandler);

        if(!this.isInitialized) {
            if(Settings.Debug.isDebugModeEnabled) {
                this.debugRenderer = new VisualGameDebugger();
            }

            this.setupUIWidgets();

            this.level = new LevelFactory().getLevel(1);
            this.hud = new HUDSystemFactory().getHUDSystem(HUDSystemType.SINGLE_PLAYER_HUD);
            this.player = new EntityFactory().getEntity(EntityType.PLAYER, 1);

            String[] searchWords = ((BasicLevel)this.level).getWordPool();
            int randomIndex = Randomizer.nextRandomInt(0, searchWords.length - 1);
            String searchWord = searchWords[randomIndex].toUpperCase();

            this.spawnCenter = new GameItemSpawnCenter(
                    searchWord,
                    new GameItemSpawnPool(EntityType.CHEESE, 5, 10, searchWord.length())
            );

            // init search word
            SearchWordHUD searchWordHUD = (SearchWordHUD) this.hud.getHUDComponent(HUDComponentType.SEARCH_WORD);
            if(searchWordHUD != null && this.level instanceof BasicLevel) {
                searchWordHUD.setSearchWord(searchWord);
            }

            timer = new CountdownTimer(this.level.getCountdown());
            timer.addCountdownListener(this);
            timer.addCountdownListener(this.spawnCenter);
            timer.start();

            /**
             * Level sound and music
             */
            levelmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/alrightlevel.mp3"));
            levelmusic.setLooping(true);
            levelmusic.setVolume(0.25f);
            levelmusic.play();

            this.wonSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/tada.mp3"));

            this.spawnCenter.startSpawning();
            this.challengeWatchdog = new ScrabbleChallengeWatchdog(searchWord);

            this.isInitialized = true;
        }
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

            GameObjectManager gom = GameObjectManager.getInstance();
            components = this.player.getAllComponents(ComponentType.GFX_COMPONENT);
            for(IComponent component : components) {
                ((IGraphicsComponent) component).render(batch);
            }

            List<IGameObject> cheeseItems =  gom.getGameEntity(EntityType.CHEESE);
            for(IGameObject cheese : cheeseItems) {
                if(((IEntity)cheese).isActive()) {
                    components = cheese.getAllComponents(ComponentType.GFX_COMPONENT);
                    for(IComponent component : components) {
                        ((IGraphicsComponent) component).render(batch);
                    }
                }
            }

            // render HUD components
            List<IHUDComponent> hudComponents = this.hud.getHUDComponents();
            for(IGameObject hudComponent: hudComponents) {
                components = hudComponent.getAllComponents(ComponentType.GFX_COMPONENT);
                for(IComponent component : components) {
                    ((IGraphicsComponent) component).render(batch);
                }
            }

            if(Settings.Debug.isDebugModeEnabled) {
                this.debugRenderer.render(textBatch);
            }

            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        if(this.isPauseRequested) {
            this.timer.pauseTimer();
        }
    }

    @Override
    public void resume() {
        if(this.overlay != null) {
            this.overlay.remove();
        }
        this.isPauseRequested = false;
        this.timer.resumeTimer();
    }

    @Override
    public void hide() {
        // TODO: Implement here...
    }

    @Override
    public void dispose() {
        // TODO: Implement here...
    }

    @Override
    public void onCountdownStarted(long time) {
        System.out.println("Game started");
        this.triggerRemainingTimeUpdateEvent(time);
    }

    @Override
    public void onCountdownTick(long time) {
        this.triggerRemainingTimeUpdateEvent(time);
        if(this.challengeWatchdog.isChallengeWon()) {
            System.out.println("The Game is Won!!!");
            this.timer.stopTimer();
            this.levelmusic.stop();
            wonSound.play(1);

            this.showChallengeResultDialog();
        }
    }

    @Override
    public void onCountdownFinished(long time) {
        System.out.println("Game over");
    }

    /**
     * Setup all UI widgets required.
     */
    private void setupUIWidgets() {
        Texture pauseBtnTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/pause.png"));
        Texture pauseBtnPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/pausePressed.png"));
        pauseBtnTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        pauseBtnPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
        int padding = (1 * multiplier);
        ImageButton pauseBtn = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("pause")
                .useVirtualScale(true)
                .alignment(Alignment.TOP_LEFT)
                .size(pauseBtnTexture.getWidth(), pauseBtnTexture.getHeight())
                .margins(padding, 0, 0, padding)
                .imageButtonTextures(pauseBtnTexture, pauseBtnPressedTexture)
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

        this.stage.addActor(pauseBtn);
    }

    /**
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        switch(sender.getName()) {
            case "pause":
                this.showPauseDialog();
                break;
        }
    }

    /**
     * Show the pause dialog.
     */
    private void showPauseDialog() {
        this.isPauseRequested = true;
        this.pause();

        this.overlay = new Image(new Texture(
                Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/overlay.png")
        ));
        this.stage.addActor(this.overlay);

        Gdx.app.postRunnable(() -> {
            render(Gdx.graphics.getDeltaTime());
            GameScreenManager gsm = GameScreenManager.getInstance();
            IGameScreen screen = gsm.getScreen(ScreenState.PAUSE_DIALOG);
            if(screen != null) {
                gsm.showScreen(screen);
            } else {
                gsm.showScreen(new PauseDialogScreen());
            }
        });
    }

    private void showChallengeResultDialog() {
        this.overlay = new Image(new Texture(
                Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/overlay.png")
        ));
        this.stage.addActor(this.overlay);

        Gdx.app.postRunnable(() -> {
            render(Gdx.graphics.getDeltaTime());
            GameScreenManager gsm = GameScreenManager.getInstance();
            IGameScreen screen = gsm.getScreen(ScreenState.PAUSE_DIALOG);
            if(screen != null) {
                gsm.showScreen(screen);
            } else {
                gsm.showScreen(new ChallengeScoreDialogScreen());
            }
        });
    }

    /**
     * Trigger a remaining time update event to update the timer HUD.
     * @param time The remaining time in milliseconds
     */
    private void triggerRemainingTimeUpdateEvent(long time) {
        GameEventManager gm = GameEventManager.getInstance();
        RemainingTimeUpdateEvent event = (RemainingTimeUpdateEvent) gm.getGameEvent(REMAINING_TIME_UPDATE);
        event.setTime(time);
        gm.triggerEvent(REMAINING_TIME_UPDATE);
    }
}