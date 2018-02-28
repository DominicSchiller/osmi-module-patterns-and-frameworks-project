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
import com.badlogic.gdx.math.Vector2;
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
import de.thb.paf.scrabblefactory.managers.PlayScreenRestoreManager;
import de.thb.paf.scrabblefactory.gameplay.ScrabbleChallengeWatchdog;
import de.thb.paf.scrabblefactory.gameplay.ScrabbleScoreCalculator;
import de.thb.paf.scrabblefactory.gameplay.timer.CountdownTimer;
import de.thb.paf.scrabblefactory.gameplay.timer.ICountdownListener;
import de.thb.paf.scrabblefactory.io.KeyboardInputProcessor;
import de.thb.paf.scrabblefactory.io.TouchInputProcessor;
import de.thb.paf.scrabblefactory.managers.GameEventManager;
import de.thb.paf.scrabblefactory.managers.GameObjectManager;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.managers.WorldPhysicsManager;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
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

    /**
     * The overlay displayed when a dialog window will pop up
     */
    private Image overlay;

    /**
     * The debug renderer visualizing Box2D bodies and the game's FPS rate
     */
    private VisualGameDebugger debugRenderer;

    /**
     * The virtual camera
     */
    private OrthographicCamera camera;

    /**
     * The level's challenge search word
     */
    private String searchWord;

    /**
     * The current game level
     */
    private ILevel level;

    /**
     * The single player HUD system
     */
    private HUDSystem hud;

    /**
     * The current player entity
     */
    private IEntity player;

    /**
     * The spawn center required to spawn cheese items
     */
    private GameItemSpawnCenter spawnCenter;

    /**
     * The watchdog observing if the challenge is won or not
     */
    private ScrabbleChallengeWatchdog challengeWatchdog;

    /**
     * The countdown timer counting down the level's defined time-frame
     */
    private CountdownTimer timer;

    /**
     * The screen's multiplexed input handler listening for touch and keyboard inputs
     */
    private InputMultiplexer inputHandler;

    /**
     * The level's background music
     */
    private Music backgroundMusic;

    /**
     * The sound played when the challenge is won
     */
    private Sound wonSound;

    /**
     * Status if the level was paused or not
     */
    private boolean isPauseRequested;

    private boolean isChallengeWon;

    /**
     * Default Constructor
     */
    public PlayScreen() {
        super(ScreenState.PLAY);
        this.isChallengeWon = false;
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
            this.searchWord = searchWords[randomIndex].toUpperCase();

            this.spawnCenter = new GameItemSpawnCenter(
                    this.searchWord,
                    new GameItemSpawnPool(EntityType.CHEESE, 2, 10, searchWord.length())
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
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/alrightlevel.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.25f);
            backgroundMusic.play();

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

            List<IEntity> cheeseItems =  gom.getGameEntity(EntityType.CHEESE);
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

        GameObjectManager.getInstance().dispose();

        this.spawnCenter.clear();
        this.hud.dispose();
        this.level.dispose();

        this.wonSound.dispose();
        this.backgroundMusic.dispose();
        this.stage.dispose();

        WorldPhysicsManager.getInstance().dispose();
        System.out.println();
    }

    @Override
    public void onCountdownStarted(long time) {
        this.triggerRemainingTimeUpdateEvent(time);
    }

    @Override
    public void onCountdownTick(long time) {
        this.triggerRemainingTimeUpdateEvent(time);
        this.isChallengeWon = this.challengeWatchdog.isChallengeWon();
        if(this.isChallengeWon) {
            this.stage.addActor(this.overlay);

            this.timer.stopTimer();
            this.backgroundMusic.stop();
            wonSound.play(1);

            int score = ScrabbleScoreCalculator.calculateScore(
                    this.searchWord, time);
            this.showChallengeResultDialog(score);
        }
    }

    @Override
    public void onCountdownFinished(long time) {
        if(!this.isChallengeWon) {
            this.showGameOverDialog();
        }
    }

    /**
     * Reset the overall level for a new round.
     */
    public void resetLevel() {
        if(this.isInitialized) {
            this.overlay.remove();
            this.isChallengeWon = false;

            String[] searchWords = ((BasicLevel)this.level).getWordPool();
            int randomIndex = Randomizer.nextRandomInt(0, searchWords.length - 1);
            this.searchWord = searchWords[randomIndex].toUpperCase();

            // reset player position
            PlayScreenRestoreManager restoreManager = PlayScreenRestoreManager.getInstance();
            for(IEntity player : GameObjectManager.getInstance().getGameEntity(EntityType.PLAYER)) {
                Vector2 playerPosition = restoreManager.getRestorePosition(player);
                for(IComponent component : player.getAllComponents(ComponentType.PHYS_COMPONENT)) {
                    if(component instanceof RigidBodyPhysicsComponent) {
                        ((RigidBodyPhysicsComponent) component).getBody().setTransform(
                                playerPosition.x,
                                playerPosition.y,
                                0
                        );
                    }
                }
            }

            //reset spawn center
            this.spawnCenter.reset(this.searchWord);
            this.spawnCenter.startSpawning();

            // init search word
            SearchWordHUD searchWordHUD = (SearchWordHUD) this.hud.getHUDComponent(HUDComponentType.SEARCH_WORD);
            if(searchWordHUD != null && this.level instanceof BasicLevel) {
                searchWordHUD.setSearchWord(searchWord);
            }

            timer = new CountdownTimer(this.level.getCountdown());
            timer.addCountdownListener(this);
            timer.addCountdownListener(this.spawnCenter);
            timer.start();
        }
    }

    /**
     * Setup all UI widgets required.
     */
    private void setupUIWidgets() {
        Texture pauseBtnTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/pause.png"));
        Texture pauseBtnPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/pausePressed.png"));
        pauseBtnTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        pauseBtnPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        this.overlay = new Image(new Texture(
                Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/overlay.png")
        ));

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

        this.stage.addActor(this.overlay);

        Gdx.app.postRunnable(() -> {
            render(Gdx.graphics.getDeltaTime());
            GameScreenManager gsm = GameScreenManager.getInstance();
            IGameScreen screen = gsm.getScreen(ScreenState.PAUSE);
            if(screen != null) {
                gsm.showScreen(screen);
            } else {
                gsm.showScreen(new PauseDialogScreen());
            }
        });
    }

    /**
     * Show the Won Score dialog.
     * @param score The score to display
     */
    private void showChallengeResultDialog(int score) {
        Gdx.app.postRunnable(() -> {
            render(Gdx.graphics.getDeltaTime());
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gdx.app.postRunnable(() -> {
            GameScreenManager gsm = GameScreenManager.getInstance();
            IGameScreen screen = gsm.getScreen(ScreenState.CHALLENGE_WON);
            if(screen != null) {
                ((ChallengeScoreDialogScreen)screen).setScore(score);
                gsm.showScreen(screen);
            } else {
                screen = new ChallengeScoreDialogScreen();
                ((ChallengeScoreDialogScreen)screen).setScore(score);
                gsm.showScreen(screen);
            }
        });
    }

    /**
     * Show the game over dialog.
     */
    private void showGameOverDialog() {
        Gdx.app.postRunnable(() -> {
            render(Gdx.graphics.getDeltaTime());
        });

        Gdx.app.postRunnable(() -> {
            render(Gdx.graphics.getDeltaTime());
            GameScreenManager gsm = GameScreenManager.getInstance();
            IGameScreen screen = gsm.getScreen(ScreenState.GAME_OVER);
            if(screen != null) {
                gsm.showScreen(screen);
            } else {
                gsm.showScreen(new GameOverDialogScreen());
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