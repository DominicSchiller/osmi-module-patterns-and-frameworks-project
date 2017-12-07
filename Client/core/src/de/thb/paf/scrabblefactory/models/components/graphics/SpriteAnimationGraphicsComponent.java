package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.settings.Settings;

/**
 * Graphics component responsible for rendering sprite animations based on texture atlases.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class SpriteAnimationGraphicsComponent extends GameComponent implements IGraphicsComponent {


    /**
     * The default animation cycle time
     */
    private static int DEFAULT_ANIM_CYCLE_TIME = 30;

    /**
     * All texture atlases organized by animation key
     */
    private Map<String, TextureRegion[]> textures;

    /**
     * The animation looping through textures from a given texture atlas
     */
    private Animation<TextureRegion> animation;

    /**
     * The currently selected texture atlases key
     */
    private String selectedAtlasName;

    /**
     * Indicator whether the animation is infinitely looping or stop after the last frame
     */
    private boolean isInfiniteLoop;

    /**
     * The animation's frames-per-seconds rate
     */
    private int fps;

    /**
     * The elapsed time since the last rendering
     */
    private float elapsedTime;

    /**
     * Constructor
     * @param id The game component's unique id
     */
    public SpriteAnimationGraphicsComponent(Integer id) {
        super(id, ComponentType.GFX_COMPONENT);
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param parent The associated game entity holding this component
     */
    public SpriteAnimationGraphicsComponent(Integer id, IEntity parent) {
        this(id);
        this.setParent(parent);
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     */
    public SpriteAnimationGraphicsComponent(Integer id, IEntity parent, Map<String, TextureRegion[]> textures) {
        this(id, parent);
        this.textures = textures;
        this.selectedAtlasName = "";
        this.isInfiniteLoop = false;
        this.fps = DEFAULT_ANIM_CYCLE_TIME;
        this.elapsedTime = 0;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique identifier
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     */
    public SpriteAnimationGraphicsComponent(Integer id, IEntity parent, Map<String, TextureRegion[]> textures, String initialAtlasName) {
        this(id, parent, textures);
        this.selectedAtlasName = initialAtlasName;
        this.isInfiniteLoop = false;
        this.fps = DEFAULT_ANIM_CYCLE_TIME;
        this.elapsedTime = 0;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique identifier
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     * @param fps The animation's frames-per-seconds rate
     */
    public SpriteAnimationGraphicsComponent(Integer id, IEntity parent, Map<String, TextureRegion[]> textures, String initialAtlasName, int fps) {
        this(id, parent, textures, initialAtlasName);
        this.fps = fps;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     * @param fps The animation's frames-per-seconds rate
     * @param isInfiniteLoop Indicator whether the animation is infinitely looping or stop after the last frame
     */
    public SpriteAnimationGraphicsComponent(Integer id, IEntity parent, Map<String, TextureRegion[]> textures, String initialAtlasName, int fps, boolean isInfiniteLoop) {
        this(id, parent, textures, initialAtlasName, fps);
        this.isInfiniteLoop = isInfiniteLoop;
    }

    /**
     * Set all texture atlases organized by animation key
     * @param textures Map containing all texture atlases organized by animation key
     */
    public void setTextures(Map<String, TextureRegion[]> textures) {
        this.textures = textures;

        //set texture filters to each texture
        for(Object[] textureSet : this.textures.values()) {
            for(Object texture : textureSet) {
                ((TextureRegion)texture).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
            }
        }

        this.initAnimation();
    }

    /**
     * Set indicator whether to infinitely  loop the animation or not.
     * @param isInfiniteLoop The indicator whether the animation is infinitely looping or stop after the last frame
     */
    public void setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
    }

    /**
     * Set the animation's frames-per-seconds rate.
     * @param fps The animation's frames-per-seconds rate
     */
    public void setFps(int fps) {
        this.fps = fps;
        this.animation.setFrameDuration(fps);
    }

    /**
     * Switch the animation's texture atlas.
     * @param atlasName The name of the texture atlas to switch to
     */
    public void switchToAnimation(String atlasName) {
        this.elapsedTime = 0;
        this.selectedAtlasName = atlasName;
        this.initAnimation();
    }

    @Override
    public void update(float deltaTime) {
        this.elapsedTime += deltaTime;
    }

    @Override
    public void render(Batch batch) {
        batch.begin();

        Vector2 position = this.getParent().getPosition();
        TextureRegion texture = this.animation.getKeyFrame(this.elapsedTime, this.isInfiniteLoop);
        float width = texture.getRegionWidth()  * Settings.Game.VIRTUAL_SCALE;
        float height = texture.getRegionHeight() * Settings.Game.VIRTUAL_SCALE;
        batch.draw(
                texture,
                position.x,
                position.y,
                width,
                height

        );

        batch.end();
    }

    /**
     * Initializes the animation based on current settings
     */
    private void initAnimation() {
        this.animation = new Animation<>(
                1/(float)this.fps,
                textures.get(this.selectedAtlasName)
        );
    }

}