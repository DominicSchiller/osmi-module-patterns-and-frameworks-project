package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
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

import de.thb.paf.scrabblefactory.managers.GameScreenManager;

/**
 * Represents the Game dialog screen showing communication of each dialog
 *
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameDialogScreen extends GameScreen {


        /*
        * Default Constructor
        */
        public GameDialogScreen() {
            super(ScreenState.DESCRIPTION);
        }

        /*
        * Declaration of screen state
        */
        private ScreenState state = ScreenState.DESCRIPTION;

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
        private Sound tapsound;
        private Texture backgroundTexture;
        private Texture playAgainTexture, playAgainPressTexture;
        private BitmapFont bitmapFont;
        private GlyphLayout layout;

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
            backgroundTexture = new Texture(Gdx.files.internal("images/backgrounds/background.png"));
            Image background = new Image(backgroundTexture);
            stage.addActor(background);

        /*
        * sound of interactive elements
         */
            tapsound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/button_click.mp3"));

        /*
        * text to describe the game, set on the batch
        */
            batch = new SpriteBatch();
            bitmapFont = new BitmapFont();
            drawText();

        /*
        *This method sets all buttons and their listeners
         */
            setUpButtons();
        }

        @Override
        public void render(float delta) {

        /*
        * default color of the description screen is violet
         */
            Gdx.gl.glClearColor(1, 0, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        /*
        * renders the stage
         */
            stage.act(delta);
            stage.draw();

        /*
        * renders text out of draw text method
         */
            drawText();
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

        private void setUpButtons(){

        /*
        * game play again button (tap) on the stage
        */
            playAgainTexture = new Texture(Gdx.files.internal("images/buttons/tapForward.png"));
            playAgainPressTexture = new Texture(Gdx.files.internal("images/buttons/tapForwardPressed.png"));
            ImageButton playAgain = new ImageButton(new TextureRegionDrawable(new TextureRegion(playAgainTexture)), new TextureRegionDrawable(new TextureRegion(playAgainPressTexture)));
            stage.addActor(playAgain);
            playAgain.setPosition(8 * WORLD_WIDTH / 16, 18 * WORLD_HEIGHT / 32, Align.center);

        /*
        * adds the play again buttons listener an set it back to MainMenuScreen
        */
            playAgain.addListener(new ActorGestureListener() {

                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    super.tap(event, x, y, count, button);
                    tapsound.play();
                    tapsound.dispose();
                    GameScreenManager.getInstance().setScreen(new PlayScreen());
                }
            });
        }

        public void drawText (){
        /*
        * This method is able to draw text on the batch
         */
            batch.begin();
            String text = "Mäusepause - Gleich geht es weiter!";
            layout = new GlyphLayout();
            layout.setText(bitmapFont, text);
            bitmapFont.draw(batch, text, 80 * layout.width/480, layout.height *28);
            batch.end();
      }
 }


