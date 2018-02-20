package de.thb.paf.scrabblefactory.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.thb.paf.scrabblefactory.gameplay.timer.CountdownTimer;
import de.thb.paf.scrabblefactory.gameplay.timer.ICountdownListener;
import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.graphics.Alignment;
import de.thb.paf.scrabblefactory.persistence.DataStore;
import de.thb.paf.scrabblefactory.persistence.entities.User;
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
     * The ui elements canvas holder
     */
    private Stage stage;

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
        Gdx.input.setInputProcessor(stage);

        this.setupUIWidgets();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void show() {
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

    }

    /**
     * Setup all UI widgets required to represent a login form.
     */
    private void setupUIWidgets() {
        this.loginBtn = (TextButton)new UIWidgetBuilder(UIWidgetType.TEXT_BUTTON)
                .title("Login")
                .alignment(Alignment.MIDDLE)
                .clickListener(
                        new ClickListener() {
                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                super.touchUp(event, x, y, pointer, button);
                                if(login()) {
                                    gotoHomeScreen();
                                } else {
                                    showLoginError();
                                }
                            }
                        }
                )
                .create();

        this.nicknameInputField = (TextField)new UIWidgetBuilder(UIWidgetType.TEXT_FIELD)
                .title("Please insert your nickname...")
                .size(500, 75)
                .alignment(Alignment.TOP_CENTER)
                .margins(300, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 28, Color.BLACK)
                .clickListener(
                        new ClickListener() {
                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                super.touchUp(event, x, y, pointer, button);
                                nicknameInputField.setText("");
                            }
                        }
                )
                .create();

        this.passwordInputField = (TextField)new UIWidgetBuilder(UIWidgetType.TEXT_FIELD)
                .title("")
                .size(500, 75)
                .alignment(Alignment.TOP_CENTER)
                .margins(500, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 28, Color.BLACK)
                .clickListener(
                        new ClickListener() {
                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                super.touchUp(event, x, y, pointer, button);
                                if(nicknameInputField.getText().equals("")) {
                                    nicknameInputField.setText("Please insert your nickname...");
                                }
                            }
                        }
                )
                .create();
        this.passwordInputField.setPasswordMode(true);
        this.passwordInputField.setPasswordCharacter('*');

        this.loginErrorLabel = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("Ihr Konto wurde nicht gefunden.\nBitte überprüfen Sie Ihre eingaben.")
                .size(500, 50)
                .alignment(Alignment.TOP_CENTER)
                .margins(650, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 28, Color.RED)
                .create();

        Label userNameLabel = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("Nickname")
                .size(500, 50)
                .alignment(Alignment.TOP_CENTER)
                .margins(250, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 28, Color.WHITE)
                .create();

        Label passwordLabel = (Label)new UIWidgetBuilder(UIWidgetType.TEXT_LABEL)
                .title("Password")
                .size(500, 50)
                .alignment(Alignment.TOP_CENTER)
                .margins(450, 0, 0, 0)
                .font(FontAsset.OPEN_SANS, 28, Color.WHITE)
                .create();

        this.stage.addActor(userNameLabel);
        this.stage.addActor(passwordLabel);
        this.stage.addActor(this.nicknameInputField);
        this.stage.addActor(this.passwordInputField);
        this.stage.addActor(this.loginBtn);
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
        DataStore dataStore = DataStore.getInstance();
        User user = dataStore.readUser(
                this.nicknameInputField.getText(),
                this.passwordInputField.getMessageText()
        );

        return user != null;
    }

    /**
     * Shows a error hint for a failed login attempt.
     */
    private void showLoginError() {
        CountdownTimer displayErrorTimer;displayErrorTimer = new CountdownTimer(3000);
        displayErrorTimer.registerCountdownListener(this);
        displayErrorTimer.start();
    }

    /**
     * Navigate to the game's home screen.
     */
    private void gotoHomeScreen() {
        GameScreenManager.getInstance().setScreen(new HomeScreen());
    }
}
