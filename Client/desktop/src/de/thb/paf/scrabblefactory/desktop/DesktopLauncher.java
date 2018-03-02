package de.thb.paf.scrabblefactory.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.settings.Settings;

/**
 * Launcher responsible for starting the Scrabble Factory game on a conventional desktop system.
 *
 * @author Dominic Schiller, Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 *
 * @see LwjglApplication
 */
public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Vector2 screenResolution = determineScreenResolution();

		config.title = Settings.App.APP_TITLE;
		config.foregroundFPS = Settings.App.FPS;
		config.backgroundFPS = Settings.App.FPS;

		config.useHDPI = true;
		config.width = (int)screenResolution.x;
		config.height = (int)screenResolution.y;

		new LwjglApplication(ScrabbleFactory.getInstance(), config);
	}

	/**
	 * Determines the device's screen resolution.
	 * @return The screen resolution
	 */
	private static Vector2 determineScreenResolution() {
		Vector2 resolution;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		resolution = new Vector2(
				(float)screenSize.getWidth(),
				(float)screenSize.getHeight()
		);

		return resolution;
	}
}
