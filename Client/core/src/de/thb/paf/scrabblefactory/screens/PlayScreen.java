package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Represents the play screen where all single and multi-player levels take place.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class PlayScreen extends GameScreen {

    /**
     * Default Constructor
     */
    public PlayScreen() {
        super(ScreenState.PLAY);
    }

    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
    }

    @Override
    public void show() {
        // TODO: Implement here...
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // TODO: Implement here...
    }

    @Override
    public void resize(int width, int height) {
        // TODO: Implement here...
    }

    @Override
    public void pause() {
        // TODO: Implement here...
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
