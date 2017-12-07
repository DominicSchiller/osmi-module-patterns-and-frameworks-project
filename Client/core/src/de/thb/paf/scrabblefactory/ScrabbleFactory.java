package de.thb.paf.scrabblefactory;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.screens.HomeScreen;
import de.thb.paf.scrabblefactory.utils.debug.SettingsDebugger;

/**
 * The global Scrabble Factory game class.
 *
 * @author Dominic Schiller, Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class ScrabbleFactory extends Game {

	/**
	 * The global sprite batch to render assets with
	 */
	public SpriteBatch batch;

	/**
	 * The singleton instance of ScrabbleFactory
	 */
	private static final ScrabbleFactory instance;

	/**
	 * The asset manager loads the assets
	 */
	private final AssetManager assetManager = new AssetManager();

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

		SettingsDebugger.printSettings();
		GameScreenManager.getInstance().setScreen(new HomeScreen());
	}

	/**
	 * Get the AssetManager instance.
	 */
	public AssetManager getAssetManager(){
		return assetManager;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
