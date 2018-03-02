package de.thb.paf.scrabblefactory.io;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import net.spookygames.gdx.nativefilechooser.NativeFileChooser;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserCallback;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.persistence.entities.SaveGame;

import static com.badlogic.gdx.Application.ApplicationType.Desktop;

/**
 * The save game handler allows to save and load save-game files.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SaveGameHandler {

    /**
     * The package name from the desktop's native file chooser
     */
    private static final String DESKTOP_PACKAGE_NAME = "net.spookygames.gdx.nativefilechooser.desktop.";

    /**
     * The package name from Android's native file chooser
     */
    private static final String ANDROID_PACKAGE_NAME = "net.spookygames.gdx.nativefilechooser.android.";

    /**
     * The class name from the desktop's native file chooser
     */
    private static final String DESKTOP_CLASS_NAME = "DesktopFileChooser";

    /**
     * The class name from Android's native file chooser
     */
    private static final String ANDROID_CLASS_NAME = "AndroidFileChooser";

    /**
     * A save-game file's default file name
     */
    private static final String DEFAULT_SAVE_GAME_FILENAME = "ScrabbleFactory";

    /**
     * A save-game file's default file ending
     */
    private static final String SAVE_GAME_FILE_ENDING = ".savegame";

    /**
     * The file chooser's configurations
     */
    private NativeFileChooserConfiguration fileChooserConfig;

    /**
     * The loaded save-game
     */
    private SaveGame loadedSaveGame;

    /**
     * Constructor.
     */
    public SaveGameHandler() {
        this.fileChooserConfig = null;
        this.loadedSaveGame = null;
    }

    /**
     * Save a new save-game file to local directory.
     * @param saveGame The save-game instance to write as JSON to file
     * @param isPrettyPrinting Setting if to write the JSON pretty formatted or not (Note: false = reduces the file size)
     */
    public void save(SaveGame saveGame, boolean isPrettyPrinting) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String createdAt = dateFormatter.format(new Date());

        String fileName = DEFAULT_SAVE_GAME_FILENAME + "_" + createdAt + SAVE_GAME_FILE_ENDING;
        FileHandle file = Gdx.app.getType() == Desktop ? Gdx.files.local(fileName) : Gdx.files.local("data/" + fileName);
        file.writeString(this.saveGameToJson(saveGame, isPrettyPrinting), false);
    }

    /**
     * Load a save-game file from disk.
     */
    public SaveGame load() {
                NativeFileChooser fileChooser = this.getFileChooser();
        if(fileChooser != null) {
            fileChooser.chooseFile(this.getFileChooserConfiguration(), new NativeFileChooserCallback() {
                @Override
                public void onFileChosen(FileHandle file) {
                    // Do stuff with file, yay!
                    String saveGameJSON = file.readString();
                    SaveGame saveGame = jsonToSaveGame(saveGameJSON);
                    loadedSaveGame = saveGame;
                }

                @Override
                public void onCancellation() {
                    // Warn user how rude it can be to cancel developer's effort
                    loadedSaveGame = null;
                }

                @Override
                public void onError(Exception exception) {
                    // Handle error (hint: use exception type)
                    loadedSaveGame = null;
                }
            });
        }

        return this.loadedSaveGame;
    }

    /**
     * Convert a save-game instance to it's JSON representation.
     * @param saveGame The save-game instance to convert
     * @param isPrettyPrinting Setting if to write the JSON pretty formatted or not
     * @return The save-game's JSON representation
     */
    private String saveGameToJson(SaveGame saveGame, boolean isPrettyPrinting) {
        Gson gson = isPrettyPrinting ?
                new GsonBuilder().setPrettyPrinting().create() : new Gson();

        return gson.toJson(saveGame);
    }

    /**
     * Convert a save-game's JSON representation to a concrete JAVA save-game instance.
     * @param saveGameJSON The JSON-String to parse and convert
     * @return The save-game JAVA instance
     */
    private SaveGame jsonToSaveGame(String saveGameJSON) {
        Gson gson = new Gson();
        SaveGame saveGame;
        try {
            saveGame = gson.fromJson(saveGameJSON, SaveGame.class);
        }
        catch(JsonSyntaxException e) {
//            e.printStackTrace();
            System.out.println("The loaded save-game file is malformed. Going to skip the import process...");
            saveGame = null;
        }
        return saveGame;
    }

    /**
     * Get a system's file chooser instance.
     * @return The requested file chooser
     */
    private NativeFileChooser getFileChooser() {
        NativeFileChooser fileChooser = null;
        try {
            switch(Gdx.app.getType()) {
                case Desktop:
                    fileChooser = (NativeFileChooser)
                            Class.forName(DESKTOP_PACKAGE_NAME + DESKTOP_CLASS_NAME)
                                    .newInstance();
                    break;
                case Android:
                    fileChooser = ScrabbleFactory.fileChooser;
                    break;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return fileChooser;
    }

    /**
     * Get a file chooser's configuration.
     * @return The file chooser configuration
     */
    private NativeFileChooserConfiguration getFileChooserConfiguration() {
        if(this.fileChooserConfig != null) {
            return this.fileChooserConfig;
        } else {
            String path = System.getProperty("user.home");
            NativeFileChooserConfiguration config = new NativeFileChooserConfiguration();
            config.directory = Gdx.app.getType() == Desktop ? Gdx.files.local("") : Gdx.files.local("data");
            config.mimeFilter = "";
            config.nameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(SAVE_GAME_FILE_ENDING);
                }
            };
            // Add a nice title
            config.title = "Please choose your Scrabble Factory save game file";
            this.fileChooserConfig = config;
            return this.fileChooserConfig;
        }
    }
}
