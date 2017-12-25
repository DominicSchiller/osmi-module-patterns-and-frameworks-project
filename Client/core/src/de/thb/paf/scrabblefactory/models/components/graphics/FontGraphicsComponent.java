package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.models.assets.FontAsset;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.utils.graphics.AlignmentHelper;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_SCALE;

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
     * The bitmap font printed with pre-rendered with specific text content to a sprite image
     */
    public Sprite fontSprite;

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

        if(this.text.length() > 1) {
            Batch textBatch = ScrabbleFactory.getInstance().textBatch;
            textBatch.begin();
            this.font.draw(
                    textBatch,
                    this.text,
                    this.position.x * PPM,
                    (this.position.y * PPM) + this.font.getCapHeight()
            );
            textBatch.end();
        } else {
            batch.begin();
            this.fontSprite.draw(batch);
            batch.end();
        }
    }

    @Override
    public void update(float deltaTime) {
        if(this.isRelativeToParent) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, "x");

            Vector2 glyphSize = new Vector2(
                    glyphLayout.width / PPM,
                    glyphLayout.height / PPM
            );

            Vector2 parentSize = new Vector2(
                    this.getParent().getSize().x / PPM,
                    this.getParent().getSize().y / PPM
            );

            // calculates the relative position in the parent canvas and apply the padding
            Vector2 relativePosition = AlignmentHelper.getRelativePosition(
                    glyphSize ,
                    parentSize,
                    this.alignment,
                    this.margin
            );

            this.position = new Vector2(
                    relativePosition.x / 2,
                    relativePosition.y / 2
            );
        }

        if(!(this.getParent() instanceof GroupLayout)) {
            this.fontSprite.setRotation(this.getParent().getRotation());
        }

        this.fontSprite.setPosition(
                this.position.x,
                this.position.y
        );
    }

    /**
     * Set font to new one and update the pre-rendered sprite.
     * @param font The new font to (pre-)render
     */
    public void setFont(BitmapFont font) {
        this.font = font;

        BitmapFont.Glyph glyph = this.font.getData().getGlyph('A');
        int srcX = glyph.srcX + this.font.getRegion().getRegionX();
        int srcY = glyph.srcY+ this.font.getRegion().getRegionY();
        this.fontSprite = new Sprite(this.font.getRegion().getTexture(), srcX, srcY, glyph.width, glyph.height);
        this.fontSprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.fontSprite.setSize(this.fontSprite.getWidth() * VIRTUAL_SCALE, this.fontSprite.getHeight() * VIRTUAL_SCALE);
        this.fontSprite.setOrigin(fontSprite.getWidth()/2, fontSprite.getHeight()/2);
        this.fontSprite.setPosition(
                this.position.x,
                this.position.y
        );
    }
}
