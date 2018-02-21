package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.settings.Settings;

/**
 * Represents the Highscore list screen within ranking og highscores
 *
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameHighscoreScreen extends GameScreen {

    /*
    * Default Constructor
    */
    public GameHighscoreScreen() {
        super(ScreenState.HIGHSCORE);
    }

    /*
    * Declaration of screen state
    */
    private ScreenState state = ScreenState.HIGHSCORE;

    /**
     * Declaration of world with and height in pixel
     */
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 320;

    /**
     * Declaration of Stage, Batch, Textures, Text, Table
     */
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture backTexture, backPressTexture;
    private Texture playTexture, playPressTexture;
    private Label label, nameLabel;
    private TextField nameText;
    private Table table;
    private Sound tapsound;
    private BitmapFont bitmapFont;
    private GlyphLayout layout;

    @Override
    public void update(float deltaTime) {

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
        backgroundTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/backgrounds/background.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        /* reads highscores out of file*/
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        drawScores();

        /*
        * sound of interactive elements
         */
        tapsound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));

        /*
        * This method sets all buttons and their listeners
        */
        setUpButtons();

        int Help_Guides = 2;
        int row_height = Gdx.graphics.getWidth() /20 ;
        int col_width = Gdx.graphics.getWidth() / 20;
        addBackgroundGuide(Help_Guides);

        /*
        * Definition of different Label Styles
         */
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont standardFont = new BitmapFont();
        labelStyle.font = standardFont;
        labelStyle.fontColor = Color.GOLDENROD;

        Label.LabelStyle labelStyleDark = new Label.LabelStyle();
        labelStyleDark.font = standardFont;
        labelStyleDark.fontColor = Color.FIREBRICK;

        /*
        * Definition of Table Labels (Highscore Title, Name, Score, Date)
        */

        Label label = new Label("Highscore Liste",labelStyle);
        label.setSize(Gdx.graphics.getWidth(),row_height);
        label.setPosition(Gdx.graphics.getWidth()-col_width*15,Gdx.graphics.getHeight()-row_height*2);
        label.setAlignment(Align.bottomLeft);
        stage.addActor(label);

        Label nameLabel = new Label ("Name",labelStyleDark);
        //nameText = new TextField("vvv",skin);
        nameLabel.setSize(Gdx.graphics.getWidth(),row_height);
        nameLabel.setPosition(Gdx.graphics.getWidth()-col_width*15,Gdx.graphics.getHeight()-row_height*3);
        nameLabel.setAlignment(Align.bottomLeft);
        stage.addActor(nameLabel);

        Label scoreLabel = new Label("Punkte",labelStyleDark);
        //nameText = new TextArea("xxx", skin);
        scoreLabel.setSize(Gdx.graphics.getWidth(),row_height);
        scoreLabel.setPosition(Gdx.graphics.getWidth()-col_width*8,Gdx.graphics.getHeight()-row_height*3);
        scoreLabel.setAlignment(Align.bottomLeft);
        stage.addActor(scoreLabel);

        Label dateLabel = new Label("Datum",labelStyleDark);
        //nameText = new TextField("vvv",skin);
        dateLabel.setSize(Gdx.graphics.getWidth(),row_height);
        dateLabel.setPosition(Gdx.graphics.getWidth()-col_width*3,Gdx.graphics.getHeight()-row_height*3);
        dateLabel.setAlignment(Align.bottomLeft);
        stage.addActor(dateLabel);

        Label tableLabel = new Label("Tabelle",labelStyleDark);
        tableLabel.setSize(Gdx.graphics.getWidth(),row_height);
        tableLabel.setPosition(Gdx.graphics.getWidth()-col_width*15,Gdx.graphics.getHeight()-row_height*4);
        tableLabel.setAlignment(Align.bottomLeft);
        stage.addActor(tableLabel);

        /*
        * Dummy data get started with highscore table
         */
        String[][] data = new String[5][3];
        data[0] = new String[]{"Melanie","120"," 15.12.2017"};
        data[1] = new String[]{"Dominic","118", "05.10.2017"};
        data[2] = new String[]{"Falk","87", "17.12.2017"};
        data[3] = new String[]{"Adrian","76", "13.11.2017"};
        data[4] = new String[]{"Sofia","83", "05.10.2017"};

        /*
        * This method includes the table
        */
        table = new Table();
        table.setFillParent(true);

        table.add(tableLabel);
        table.add(nameText).width(100);
        table.row();
        table.add(tableLabel);
        table.add(nameText).width(100);
        stage.addActor(table);

    }


    @Override
    public void render(float delta) {

        /*
        * renders the stage
         */
        stage.act(delta);
        stage.draw();

        drawScores();

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

    private void setUpButtons(){

        /*
        * back button (tap) on the stage
        */
        backTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapBack.png"));
        backPressTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapBackPressed.png"));
        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressTexture)));
        stage.addActor(back);
        back.setPosition(11 * WORLD_WIDTH / 16, 2 * WORLD_HEIGHT / 32, Align.center);

        /*
        * adds the back buttons listener an set it back to MainMenuScreen
        */
        back.addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                tapsound.play();
                tapsound.dispose();
                GameScreenManager.getInstance().setScreen(new MainMenuScreen());
            }
        });

        /*
        * game play button (tap) on the stage
        */
        playTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapPlay.png"));
        playPressTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/tapPlayPressed.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        stage.addActor(play);
        play.setPosition(14 * WORLD_WIDTH / 16, 2 * WORLD_HEIGHT / 32, Align.center);

                /*
        * adds the play buttons listener an set it back to game
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
    }

    private void drawTable(){

    }

    private void drawScores(){
        /*
        * This method is able to draw score on the batch
         */
            batch.begin();
            bitmapFont = new BitmapFont(Gdx.files.internal("fonts/arial26-small.fnt"));
            FileHandle file = Gdx.files.internal("files/highscoredata-min.json");

            String text = file.readString();
            layout = new GlyphLayout();
            layout.setText(bitmapFont, text);
            bitmapFont.draw(batch, text, 5*layout.width/100, layout.height +970);
            batch.end();

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
