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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.persistence.entities.Score;
import de.thb.paf.scrabblefactory.persistence.entities.User;
import de.thb.paf.scrabblefactory.persistence.entities.UserScore;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

/**
 * Represents the highscore screen ranking reached highscores from top to bottom.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameHighScoreScreen extends GameScreen {


    private Stage stage;

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
        this.stage = new Stage();

        this.userScores = new ArrayList<>();
        this.loadHighScoreList();

        this.initBackgroundScene();
        this.setupSounds();
        this.setUpButtons();
        this.setupHighScoreTable();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
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

    }

    /**
     * Load the high score list from the database.
     */
    private void loadHighScoreList() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("22.02.2018 12:53:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.userScores.add(
                new UserScore(
                        new User("Schiller", "Dominic", "Dom", null, null),
                        new Score(1262425725),
                        date
                )
        );

        this.userScores.add(
                new UserScore(
                        new User("Steiner", "Melanie", "Melli", null, null),
                        new Score(1262425725),
                        date
                )
        );

        this.userScores.add(
                new UserScore(
                        new User("Atkins", "Scott", "Scotti", null, null),
                        new Score(1262425725),
                        date
                )
        );

        this.userScores.add(
                new UserScore(
                        new User("Kenobi", "Obi Wan", "Obi-Wan", null, null),
                        new Score(1262425725),
                        date
                )
        );
    }

    /**
     * Initialize and Setup the high score list table.
     */
    private void setupHighScoreTable() {
        Table table = new Table();
        table.setSize(Settings.App.DEVICE_SCREEN_WIDTH, 1000);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss");
        int width = (int)(100 * Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER);
        int extendedWith = (int)(140 * Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER);

        table.add(this.createLabel(FontAsset.PORKY, "NICKNAME"))
                .width(width);
        table.add(this.createLabel(FontAsset.PORKY, "SCORE"))
                .width(width);
        table.add(this.createLabel(FontAsset.PORKY, "CREATED AT"))
                .width(width);
        table.row();
        table.row();

        for(UserScore userScore : this.userScores) {
            String createdAt = dateFormatter.format(userScore.getCreatedAt());

            table.add(this.createLabel(FontAsset.OPEN_SANS, userScore.getUser().getFirstname()))
                    .width(width);
            table.add(this.createLabel(FontAsset.OPEN_SANS, new Integer(userScore.getScore().getScore()).toString()))
                    .width(width);
            table.add(this.createLabel(FontAsset.OPEN_SANS, createdAt + "Uhr"))
                    .width(extendedWith);
            table.row();
        }

        Vector2 tablePosition = AlignmentHelper.getRelativePosition(
                new Vector2(table.getWidth(), table.getHeight()),
                new Vector2(Settings.App.DEVICE_SCREEN_WIDTH, Settings.App.DEVICE_SCREEN_HEIGHT),
                Alignment.TOP_CENTER,
                new int[] {0, 0, 0 ,0}
        );
        table.setPosition(tablePosition.x, tablePosition.y);

        this.stage.addActor(table);
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
        buttonPressedSound.dispose();

        switch (sender.getName()) {
            case "play":
                GameScreenManager.getInstance().showScreen(new PlayScreen());
                break;
            case "back":
                GameScreenManager.getInstance().showScreen(new MainMenuScreen());
                break;
        }
    }

    public void addBackgroundGuide(int columns){

//        Texture texture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/dialog.png"));
//        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
//
//        TextureRegion textureRegion = new TextureRegion(texture);
//        textureRegion.setRegion(0,0,texture.getWidth()*columns,texture.getWidth()*columns);
//        Image background = new Image(textureRegion);
//        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getWidth());
//        background.setSize((Gdx.graphics.getWidth()/100)*80,(Gdx.graphics.getWidth()/100)*80);
//        background.setPosition( 20,20);
//        stage.addActor(background);
    }
}
