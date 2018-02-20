package de.thb.paf.scrabblefactory;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.thb.paf.scrabblefactory.managers.GameScreenManager;
import de.thb.paf.scrabblefactory.persistence.DataStore;
import de.thb.paf.scrabblefactory.persistence.entities.Gender;
import de.thb.paf.scrabblefactory.persistence.entities.User;
import de.thb.paf.scrabblefactory.screens.HomeScreen;
import de.thb.paf.scrabblefactory.screens.LoginScreen;
import de.thb.paf.scrabblefactory.screens.RegisterAccountScreen;
import de.thb.paf.scrabblefactory.settings.Settings;
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

		GameScreenManager.getInstance().setScreen(new RegisterAccountScreen());

		DataStore ds = DataStore.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1948");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User usr = new User("Maus", "Mickey",
				"Mickey", date, new Gender("m", "male"));
		ds.createUser(usr);
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
