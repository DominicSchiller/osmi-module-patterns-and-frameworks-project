package de.thb.paf.scrabblefactory.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.thb.paf.scrabblefactory.models.assets.AssetFileType;
import de.thb.paf.scrabblefactory.models.assets.AssetTargetType;
import de.thb.paf.scrabblefactory.models.assets.AssetType;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.settings.Settings;

import static de.thb.paf.scrabblefactory.models.assets.AssetFileType.TEXTURE_ATLAS;

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
     * The asset's init configuration file name
     */
    private static final String INIT_CONFIG_FILE_NAME = "init";

    /**
     * The asset's physics configuration file name
     */
    private static final String PHY_CONFIG_FILE_NAME = "physics";

    /**
     * Default character set a bitmap font will be assigned with
     */
    private static final String DEFAULT_FONT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?.-+*/=ß\"()ÄÖÜäöü";

    /**
     * Loads JSON init configuration file for a given asset target.
     * @param assetTargetType The asset's target type
     * @param assetID The asset's unique identifier
     * @return The asset's JSON configuration
     */
    public JsonObject loadInitConfiguration(AssetTargetType assetTargetType, int assetID) {
        String configFilePath = AssetType.CONFIG.path + "/" + assetTargetType.path + "/" + assetID + "/" + INIT_CONFIG_FILE_NAME + AssetFileType.JSON.fileEnding;
        FileHandle fileHandle = this.getFileHandle(configFilePath);

        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(fileHandle.readString()).getAsJsonObject();
    }

    /**
     * Loads parsed XML physics configuration file for a given asset target
     * @param assetTargetType The asset's target type
     * @param assetID The asset's unique identifier
     * @return The asset's parsed XML physics configuration
     */
    public PhysicsShapeCache loadPhysicsConfiguration(AssetTargetType assetTargetType, int assetID) {
        String configFilePath = AssetType.CONFIG.path + "/" + assetTargetType.path + "/" + assetID + "/" + PHY_CONFIG_FILE_NAME + AssetFileType.XML.fileEnding;
        FileHandle fileHandle = this.getFileHandle(configFilePath);
        return new PhysicsShapeCache(fileHandle);
    }

    /**
     * Loads a all texture atlases from asset target's directory.
     * @param assetTargetType The asset's target type
     * @param atlasNames List of atlas names to load
     * @param assetID The asset's unique identifier (optional parameter)
     * @return Map of all loaded texture atlases organized by it's atlas name
     */
    public Map<String, TextureRegion[]> loadTextureAtlases(AssetTargetType assetTargetType, String[] atlasNames, int... assetID) {
        String atlasesRootPath = AssetType.TEXTURE.path + "/" + Settings.Game.RESOLUTION.name + "/" + assetTargetType.path;
        if(assetID.length > 0) {
            atlasesRootPath += "/" + assetID[0] + "/";
        }

        HashMap<String, TextureRegion[]> textures = new HashMap<>();
        if(atlasNames.length > 0) {
            for(String atlasName: atlasNames) {
                String filePath;
                if(!atlasName.contains(TEXTURE_ATLAS.fileEnding)) {
                    filePath = atlasesRootPath + atlasName + TEXTURE_ATLAS.fileEnding;
                } else {
                    filePath = atlasesRootPath + atlasName;
                }

                textures.put(
                        atlasName,
                        new TextureAtlas(this.getFileHandle(filePath)).getRegions().toArray()
                );
            }
        }

        return textures;
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
        atlasPath += "/" + atlasName + TEXTURE_ATLAS.fileEnding;

        TextureAtlas textureAtlas = new TextureAtlas(this.getFileHandle(atlasPath));
        return textureAtlas;
    }

    /**
     * Loads a specific Font with given properties
     * @param font The font to load
     * @param fontSize The font size to set
     * @param borderWidth The font's character's outline width
     * @param fillColor The font's fill color
     * @param borderColor The font's outline color
     * @param chars A custom set of characters that can be written with this font (optional parameter)
     * @return The loaded font
     */
    public BitmapFont loadFont(FontAsset font, int fontSize, int borderWidth, Color fillColor, Color borderColor, String... chars) {
        String fontPath = AssetType.FONT.path + "/" + font.fileName + AssetFileType.TRUE_TYPE_FONT.fileEnding;

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(this.getFileHandle(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        if(chars.length > 0) {
            fontParameter.characters = Arrays.toString(chars);
        } else {
            fontParameter.characters = DEFAULT_FONT_CHARS;
        }

        fontParameter.size = fontSize;
        fontParameter.borderWidth = borderWidth;
        fontParameter.color = fillColor;
        fontParameter.borderColor = borderColor;

        BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();

        return bitmapFont;
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
