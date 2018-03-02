package de.thb.paf.scrabblefactory;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

/**
 * Abstract base class required by all ScrabbleFactories's unit tests.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public abstract class ScrabbleFactoryTest {

    /**
     * The headless umbrella application backend required to run all unit tests with
     */
    private static Application mApplication;

    /**
     * Initializes the headless umbrella mApplication backend before running any unit tests
     */
    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        mApplication = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });

        // Use Mockito to mock the OpenGL methods since we are running headless
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    /**
     * Cleans up and shuts down the umbrella mApplication backend since we're done.
     */
    @AfterClass
    public static void cleanUp() {
        // Exit the mApplication first
        mApplication.exit();
        mApplication = null;
    }

}