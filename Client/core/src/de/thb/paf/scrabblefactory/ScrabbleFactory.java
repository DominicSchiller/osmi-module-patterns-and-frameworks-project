package de.thb.paf.scrabblefactory;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.screens.PlayScreen;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.debug.SettingsDebugger;

/**
 * The global Scrabble Factory game class.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class ScrabbleFactory extends Game {

	/**
	 * The global sprite batch to render assets with
	 */
	public SpriteBatch batch;

	/**
	 * The global sprite batch to render font typefaces with
	 */
	public SpriteBatch textBatch;

	/**
	 * The singleton instance of ScrabbleFactory
	 */
	private static final ScrabbleFactory instance;

	/**
	 * static initializer: called when the class is loaded by the JVM
	 */
	static {
		instance = new ScrabbleFactory();
	}

	/**
	 * Private singleton constructor
	 */
	private ScrabbleFactory() {
		// singleton instance
	}

	/**
	 * Get the global ScrabbleFactory game instance.
	 * @return The global game instance
	 */
	public static ScrabbleFactory getInstance() {
		return instance;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		textBatch = new SpriteBatch();

		// TODO: code to remove later on - just for dev demo purpose
		GameScreenManager.getInstance().setScreen(new PlayScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		// update global settings first
		Settings.update();
		SettingsDebugger.printSettings();
		// then notify the active set screen to resize
		super.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

}
