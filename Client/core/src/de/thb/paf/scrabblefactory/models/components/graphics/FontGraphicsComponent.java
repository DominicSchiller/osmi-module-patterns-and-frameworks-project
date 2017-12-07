package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;

/**
 * Graphics component responsible for rendering text fonts.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class FontGraphicsComponent extends GameComponent implements IGraphicsComponent {

    //TODO: make properties private

    /**
     * The bitmap font for rendering text messages
     */
    public BitmapFont font;

    /**
     * The text message to render via the font
     */
    public String text;

    /**
     * The font's font size
     */
    public int fontSize;

    /**
     * The font's border with
     */
    public int borderWidth;

    /**
     * The associated font asset
     */
    public FontAsset fontAsset;

    /**
     * The font's fill color
     */
    public String fillColor;

    /**
     * The font's border color
     */
    public String borderColor;

    /**
     * The component's absolute on screen position
     */
    public Vector2 position;

    /**
     * The font's relative on screen alignment
     */
    public Alignment alignment;

    /**
     * The font's relative on screen margin
     */
    public int[] margin;

    /**
     * Flag whether to align the graphics relative to the parent's bounds
     */
    public boolean isRelativeToParent;

    /**
     * Constructor
     * @param id The component's unique identifier
     */
    public FontGraphicsComponent(Integer id) {
        super(id, ComponentType.GFX_COMPONENT);
    }

    @Override
    public void render(Batch batch) {
        Batch textBatch = ScrabbleFactory.getInstance().textBatch;
        textBatch.begin();

        this.font.draw(
                textBatch,
                this.text,
                this.position.x * PPM,
                (this.position.y * PPM) + this.font.getCapHeight()
        );
        textBatch.end();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
