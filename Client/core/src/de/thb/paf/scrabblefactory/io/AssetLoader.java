package de.thb.paf.scrabblefactory.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.thb.paf.scrabblefactory.models.assets.AssetFileType;
import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.assets.AssetType;
import de.thb.paf.scrabblefactory.settings.Settings;

/**
 * Input Reader responsible for loading all supported types of assets from the app's global
 * asset directory.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class AssetLoader {

    /**
     * The asset's default configuration file name
     */
    private static final String CONFIG_FILE_NAME = "init";

    /**
     * Loads JSON configuration file for a given asset target.
     * @param assetTargetType The asset's target type
     * @param assetID The asset's unique identifier
     * @return The asset's JSON configuration
     */
    public JsonObject loadLevelConfiguration(AssetTargetType assetTargetType, int assetID) {
        String configFilePath = AssetType.CONFIG.path + "/" + assetTargetType.path + "/" + assetID + "/" + CONFIG_FILE_NAME + AssetFileType.JSON.fileEnding;
        FileHandle fileHandle = this.getFileHandle(configFilePath);

        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(fileHandle.readString()).getAsJsonObject();
    }

    /**
     * Loads a specific texture atlas file for a given asset target.
     * @param assetTargetType The asset's target type
     * @param atlasName The atlas file's name
     * @param assetID The asset's unique identifier (optional parameter)
     * @return The loaded texture atlas
     */
    public TextureAtlas loadTextureAtlas(AssetTargetType assetTargetType, String atlasName, int... assetID) {
        String atlasPath = AssetType.TEXTURE.path + "/" + Settings.Game.RESOLUTION.name + "/" + assetTargetType.path;
        if(assetID.length > 0) {
            atlasPath += "/" + assetID[0];
        }
        atlasPath += "/" + atlasName + AssetFileType.TEXTURE_ATLAS.fileEnding;

        TextureAtlas textureAtlas = new TextureAtlas(this.getFileHandle(atlasPath));
        return textureAtlas;
    }

    /**
     * Creates a file handle for a specific resource's fileEnding
     * @param path The fileEnding to a specific resource
     * @return The created file handle
     */
    private FileHandle getFileHandle(String path) {
        //TODO: implement exeption handling
        return Gdx.files.internal(path);
    }
}
