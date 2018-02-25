package de.thb.paf.scrabblefactory.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import de.thb.paf.scrabblefactory.ScrabbleFactory;

/**
 * Launcher responsible for starting the Scrabble Factory game on an android based system.
 *
 * @author Dominic Schiller, Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 *
 * @see AndroidApplication
 */

public class AndroidLauncher extends AndroidApplication {

	/**
	 * Get the global android application instance
	 */
	private static AndroidLauncher instance;

	/**
	 * Get the global android application instance.
	 * @return The global android application instance.
	 */
	public static AndroidLauncher getInstance() {
		return instance;
	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidLauncher.instance = this;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(ScrabbleFactory.getInstance(), config);
	}
}