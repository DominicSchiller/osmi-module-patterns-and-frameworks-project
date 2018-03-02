package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thb.paf.scrabblefactory.gameplay.sort.UserScoreComparator;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.persistence.DataStore;
import de.thb.paf.scrabblefactory.persistence.entities.UserScore;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.App.DEVICE_SCREEN_WIDTH;

/**
 * Represents the high-score screen ranking reached high-scores from top to bottom.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameHighScoreScreen extends GameScreen {

    /**
     * The button pressed audio resource
     */
    private Sound buttonPressedSound;

    /**
     * List of available user high scores
     */
    List<UserScore> userScores;

    /*
    * Default Constructor
    */
    public GameHighScoreScreen() {
        super(ScreenState.HIGH_SCORES);
        this.stage = new Stage(
                new ExtendViewport(DEVICE_SCREEN_WIDTH, DEVICE_SCREEN_HEIGHT, this.camera),
                this.batch
        );

        this.userScores = new ArrayList<>();

        this.initBackgroundScene();
        this.setupSounds();
        this.setUpButtons();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void show() {
        this.applyProjectionMatrix();
        Gdx.input.setInputProcessor(this.stage);

        for(Actor actor : this.stage.getActors()) {
            actor.remove();
        }

        this.loadHighScoreList();
        this.initBackgroundScene();
        this.setUpButtons();
        this.setupHighScoreTable();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
     * Load the high score list from the database.
     */
    private void loadHighScoreList() {
        this.userScores.clear();
        List<UserScore> userScores = DataStore.getInstance().readAllUserScores();
        if(userScores != null) {
            this.userScores.addAll(userScores);
            Collections.sort(this.userScores, new UserScoreComparator());
        }
    }

    /**
     * Initialize and Setup the high score list table.
     */
    private void setupHighScoreTable() {
        Table table = new Table();
        table.setSize(Settings.App.DEVICE_SCREEN_WIDTH * 0.9f, Settings.App.DEVICE_SCREEN_HEIGHT * 0.75f);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss");
        int width = (int)(80 * Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER);
        int extendedWith = (int)(140 * Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER);

        this.createTableCell(table, width, "RANK", FontAsset.PORKY, Align.center);
        this.createTableCell(table, width, "NICKNAME", FontAsset.PORKY, Align.left);
        this.createTableCell(table, width, "SCORE", FontAsset.PORKY, Align.center);
        this.createTableCell(table, extendedWith, "CREATED AT", FontAsset.PORKY, Align.left);
        table.row();
        table.row();

        for(int i=0; i<this.userScores.size(); i++) {
            UserScore userScore = this.userScores.get(i);
            String createdAt = dateFormatter.format(userScore.getCreatedAt());

            this.createTableCell(table, width, "#" + (i+1), FontAsset.OPEN_SANS, Align.center);
            this.createTableCell(table, width, userScore.getUser().getNickname(), FontAsset.OPEN_SANS, Align.left);
            this.createTableCell(table, width, new Integer(userScore.getScore().getScore()).toString(), FontAsset.OPEN_SANS, Align.center);
            this.createTableCell(table, extendedWith, createdAt + "Uhr", FontAsset.OPEN_SANS, Align.left);
            table.row();
        }

        Vector2 tablePosition = AlignmentHelper.getRelativePosition(
                new Vector2(table.getWidth(), table.getHeight()),
                new Vector2(Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT),
                Alignment.TOP_CENTER,
                new int[] {0, 0, 0 ,0}
        );
        table.setPosition(tablePosition.x, tablePosition.y);

        Skin skin = new Skin(Gdx.files.internal("ui/glassy-ui.json"));
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setOverscroll(true, true);
        scrollPane.setBounds(0, 100 * (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER, Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT * 0.9f);
        table.setFillParent(true);

        this.stage.addActor(scrollPane);
    }

    /**
     * Create a table cell and add it to the specified table.
     * @param table The table to add the cell to
     * @param width The cell's width to apply
     * @param text The cell's text to insert
     * @param font The cell text's font
     * @param align The cell's and text's alignment
     */
    private void createTableCell(Table table, int width, String text, FontAsset font, int align) {
        Label label = this.createLabel(font, text);
        label.setAlignment(align);
        Cell c = table.add(label).width(width);
        c.align(align);
    }

    /**
     * Initialize and setup a label for the high score list table.
     * @param font The label's font to apply
     * @param text The label's text to apply
     * @return The created label
     */
    private Label createLabel(FontAsset font, String text) {
        return (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title(text)
                .font(font, (int)(12 * Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER), Color.BLACK)
                .create();
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

        // resolution scale multiplier based on VIRTUAL width, height = 480 x 320
        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;
        Label titleLabel = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("HIGHSCORES")
                .alignment(Alignment.TOP_LEFT)
                .margins(0, 0, 0, 10 * multiplier)
                .font(FontAsset.PORKY, 28 * multiplier, new Color((int)Long.parseLong("23B7E5FF", 16)))
                .create();



        this.stage.addActor(background);
        this.stage.addActor(titleLabel);

        return backgroundScaling;
    }

    /**
     * Initialize and setup the screen's navigation buttons.
     */
    private void setUpButtons(){
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
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        buttonPressedSound.play();

        switch (sender.getName()) {
            case "play":
                this.goToScreen(ScreenState.PLAY);
                break;
            case "back":
                this.goToScreen(ScreenState.MAIN_MENU);
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
                case PLAY:
                    screen = new PlayScreen();
                    break;
                case MAIN_MENU:
                    screen = new MainMenuScreen();
                    break;
            }
        }
        gsm.showScreen(screen);
    }
}
