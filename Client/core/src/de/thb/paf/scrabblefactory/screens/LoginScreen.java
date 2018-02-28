package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import de.thb.paf.scrabblefactory.auth.AuthenticationManager;
import de.thb.paf.scrabblefactory.auth.PasswordHashGenerator;
import de.thb.paf.scrabblefactory.gameplay.timer.CountdownTimer;
import de.thb.paf.scrabblefactory.gameplay.timer.ICountdownListener;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.persistence.DataStore;
import de.thb.paf.scrabblefactory.persistence.entities.User;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetBuilder;
import de.thb.paf.scrabblefactory.utils.graphics.widgets.UIWidgetType;

/**
 * Represents the login screen where a player login and retrieve his data.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class LoginScreen extends GameScreen implements ICountdownListener {

    /**
     * The login button to trigger the login process
     */
    private TextButton loginBtn;

    /**
     * The text input field where to enter the nickname
     */
    private TextField nicknameInputField;

    /**
     * The text input field where to enter the password
     */
    private TextField passwordInputField;

    /**
     * The label used to display a login error
     */
    private Label loginErrorLabel;

    /**
     * Default Constructor
     */
    public LoginScreen() {
        super(ScreenState.LOGIN);
        this.stage = new Stage();

        this.stage.addActor(DEFAULT_BACKGROUND);
        this.setupUIWidgets();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        this.clearInputs();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1/255f, 8/255f, 15/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act(delta);
        this.stage.draw();
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
    }

    @Override
    public void onCountdownStarted(long time) {
        this.loginBtn.remove();
        this.stage.addActor(this.loginErrorLabel);
    }

    @Override
    public void onCountdownTick(long time) {

    }

    @Override
    public void onCountdownFinished(long time) {
        this.loginErrorLabel.remove();
        this.stage.addActor(this.loginBtn);
    }

    /**
     * Try to login with the current entered login credentials.
     * @return The login success status
     */
    private boolean login() {
        return AuthenticationManager.getInstance().login(
            this.nicknameInputField.getText(),
            PasswordHashGenerator.md5(this.passwordInputField.getText())
        );
    }

    /**
     * Shows a error hint for a failed login attempt.
     */
    private void showLoginError() {
        CountdownTimer displayErrorTimer = new CountdownTimer(3000);
        displayErrorTimer.addCountdownListener(this);
        displayErrorTimer.start();
    }

    /**
     * Navigate to the game's home screen.
     */
    private void gotoHomeScreen() {
        GameScreenManager gsm = GameScreenManager.getInstance();
        gsm.showScreen(new MainMenuScreen());
        gsm.clearHistory();
        gsm.dismissScreen(ScreenState.LOGIN);
    }

    /**
     * Setup all UI widgets required to represent a login form.
     */
    private void setupUIWidgets() {
        float scaling = Settings.App.DEVICE_SCREEN_WIDTH/(float)Settings.Game.RESOLUTION.maxWidth;
        int multiplier = (int)Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER;

        this.nicknameInputField = this.addLabelInputGroup("Nickname", 40);
        this.passwordInputField = this.addLabelInputGroup("Password", 90);
        this.passwordInputField.setPasswordMode(true);
        this.passwordInputField.setPasswordCharacter('*');

        this.loginBtn = (TextButton)new UIWidgetBuilder(UIWidgetType.TEXT_BUTTON)
                .identifier("login")
                .title("Login")
                .size(DEFAULT_WIDGET_WIDTH, DEFAULT_INPUT_HEIGHT)
                .alignment(Alignment.TOP_CENTER)
                .margins((int)(135* Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER), 0, 0, 0)
                .clickListener(
                        new ClickListener() {
                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                super.touchUp(event, x, y, pointer, button);
                                onButtonPressed(event.getListenerActor());
                            }
                        }
                )
                .create();

        this.loginErrorLabel = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("Ihr Konto wurde nicht gefunden.\nBitte überprüfen Sie Ihre eingaben.")
                .size(DEFAULT_WIDGET_WIDTH, DEFAULT_LABEL_HEIGHT)
                .alignment(Alignment.TOP_CENTER)
                .margins((int)(135* Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER), 0, 0, 0)
                .font(FontAsset.OPEN_SANS, DEFAULT_FONT_SIZE, Color.RED)
                .create();
        this.loginErrorLabel.setAlignment(Align.center);

        this.stage.addActor(this.loginBtn);

        // setup the back Button
        Texture backTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/back.png"));
        Texture backPressedTexture = new Texture(Gdx.files.internal("images/" + Settings.Game.RESOLUTION.name + "/buttons/backPressed.png"));
        backTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        backPressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        ImageButton backBtn = (ImageButton)new UIWidgetBuilder(UIWidgetType.IMAGE_BUTTON)
                .identifier("back")
                .size((int)(backTexture.getWidth() * scaling), (int)(backTexture.getHeight() * scaling))
                .alignment(Alignment.TOP_LEFT)
                .margins((15 * multiplier), 0, 0, (20 * multiplier)
                )
                .imageButtonTextures(backTexture, backPressedTexture)
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
        this.stage.addActor(backBtn);
    }

    /**
     * Setup a UI label-input widget group.
     * @param labelName The label's name to display
     * @param yPos The y-on-screen position of the widget group
     * @return The created input field instance
     */
    private TextField addLabelInputGroup(String labelName, int yPos) {
        int topPosition = (int)(yPos*Settings.Game.VIRTUAL_PIXEL_DENSITY_MULTIPLIER);
        Label label = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title(labelName)
                .size(DEFAULT_WIDGET_WIDTH, DEFAULT_LABEL_HEIGHT)
                .alignment(Alignment.TOP_CENTER)
                .margins(topPosition- DEFAULT_LABEL_HEIGHT, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, DEFAULT_FONT_SIZE, Color.BLACK)
                .create();

        TextField textField = (TextField)new UIWidgetBuilder(UIWidgetType.TEXT_FIELD)
                .size(DEFAULT_WIDGET_WIDTH, DEFAULT_INPUT_HEIGHT)
                .alignment(Alignment.TOP_CENTER)
                .margins(topPosition, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, DEFAULT_FONT_SIZE, Color.BLACK)
                .create();

        this.stage.addActor(label);
        this.stage.addActor(textField);

        return textField;
    }

    /**
     * Clear all input fields.
     */
    private void clearInputs() {
        this.nicknameInputField.setText("");
        this.passwordInputField.setText("");
    }

    /**
     * Handle a button pressed event.
     * @param sender The triggered button
     */
    private void onButtonPressed(Actor sender) {
        switch(sender.getName()) {
            case "login":
                if(login()) {
                    gotoHomeScreen();
                } else {
                    showLoginError();
                }
                break;
            case "back":
                GameScreenManager gsm = GameScreenManager.getInstance();
                gsm.showScreen(new LandingScreen());
                gsm.clearHistory();
                gsm.dismissScreen(ScreenState.LOGIN);
                break;
        }
    }
}
