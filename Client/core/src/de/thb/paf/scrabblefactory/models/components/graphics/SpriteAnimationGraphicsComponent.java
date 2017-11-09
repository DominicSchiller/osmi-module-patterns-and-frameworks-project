package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Map;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.models.entities.IEntity;

import static com.badlogic.gdx.graphics.g2d.TextureAtlas.*;

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
    private static float DEFAULT_ANIM_CYCLE_TIME;

    /**
     * All texture atlases organized by animation key
     */
    private Map<String, AtlasRegion[]> textures;

    /**
     * The animation looping through textures from a given texture atlas
     */
    private Animation<AtlasRegion> animation;

    /**
     * The currently selected texture atlases key
     */
    private String selectedAtlasName;

    /**
     * Indicator whether the animation is infinitely looping or stop after the last frame
     */
    private boolean isInfiniteLoop;

    /**
     * The set cycle time after which the animation moves to the next image frame from the selected texture atlas
     */
    private float animationCycleTime;

    /**
     * The elapsed time since the last rendering
     */
    private float elapsedTime;

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     */
    public SpriteAnimationGraphicsComponent(int id, ComponentType type, Map<String, AtlasRegion[]> textures, String initialAtlasName) {
        super(id, type);
        this.textures = textures;
        this.selectedAtlasName = initialAtlasName;
        this.isInfiniteLoop = false;
        this.animationCycleTime = DEFAULT_ANIM_CYCLE_TIME;
        this.elapsedTime = 0;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     */
    public SpriteAnimationGraphicsComponent(int id, ComponentType type, IEntity parent, Map<String, AtlasRegion[]> textures, String initialAtlasName) {
        super(id, type, parent);
        this.textures = textures;
        this.selectedAtlasName = initialAtlasName;
        this.isInfiniteLoop = false;
        this.animationCycleTime = DEFAULT_ANIM_CYCLE_TIME;
        this.elapsedTime = 0;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     * @param animationCycleTime The set cycle time after which the animation moves to the next image frame from the selected texture atlas
     */
    public SpriteAnimationGraphicsComponent(int id, ComponentType type, Map<String, AtlasRegion[]> textures, String initialAtlasName, float animationCycleTime) {
        this(id, type, textures, initialAtlasName);
        this.animationCycleTime = animationCycleTime;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     * @param animationCycleTime The set cycle time after which the animation moves to the next image frame from the selected texture atlas
     */
    public SpriteAnimationGraphicsComponent(int id, ComponentType type, IEntity parent, Map<String, AtlasRegion[]> textures, String initialAtlasName, float animationCycleTime) {
        this(id, type, parent, textures, initialAtlasName);
        this.animationCycleTime = animationCycleTime;
        initAnimation();
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     * @param animationCycleTime The set cycle time after which the animation moves to the next image frame from the selected texture atlas
     * @param isInfiniteLoop Indicator whether the animation is infinitely looping or stop after the last frame
     */
    public SpriteAnimationGraphicsComponent(int id, ComponentType type, Map<String, AtlasRegion[]> textures, String initialAtlasName, float animationCycleTime, boolean isInfiniteLoop) {
        this(id, type, textures, initialAtlasName, animationCycleTime);
        this.isInfiniteLoop = isInfiniteLoop;
    }

    /**
     * Constructor
     * @param id The game component's unique id
     * @param type The game component's type
     * @param parent The associated game entity holding this component
     * @param textures All texture atlases organized by animation key
     * @param initialAtlasName The name of the texture atlas which should be initially set
     * @param animationCycleTime The set cycle time after which the animation moves to the next image frame from the selected texture atlas
     * @param isInfiniteLoop Indicator whether the animation is infinitely looping or stop after the last frame
     */
    public SpriteAnimationGraphicsComponent(int id, ComponentType type, IEntity parent, Map<String, AtlasRegion[]> textures, String initialAtlasName, float animationCycleTime, boolean isInfiniteLoop) {
        this(id, type, parent, textures, initialAtlasName, animationCycleTime);
        this.isInfiniteLoop = isInfiniteLoop;
    }

    /**
     * Set indicator whether to infinitely  loop the animation or not.
     * @param isInfiniteLoop The indicator whether the animation is infinitely looping or stop after the last frame
     */
    public void setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
    }

    /**
     * Set the animation's frame-by-frame cycle time.
     * @param animationCycleTime The cycle time after which the animation moves to the next image frame from the selected texture atlas
     */
    public void setAnimationCycleTime(float animationCycleTime) {
        this.animationCycleTime = animationCycleTime;
        this.animation = new Animation<AtlasRegion>(this.animationCycleTime, textures.get(this.selectedAtlasName));
    }

    /**
     * Switch the animation's texture atlas.
     * @param atlasName The name of the texture atlas to switch to
     */
    public void switchToAnimation(String atlasName) {
        // TODO implement here
        this.elapsedTime = 0;
        this.animation = new Animation<AtlasRegion>(this.animationCycleTime, textures.get(this.selectedAtlasName));
    }

    @Override
    public void update(float deltaTime) {
        // TODO implement here
    }

    @Override
    public void render(Batch batch) {
        // TODO implement here
    }

    private void initAnimation() {
        this.animation = new Animation<AtlasRegion>(
                this.animationCycleTime,
                textures.get(this.selectedAtlasName)
        );
    }

}