package de.thb.paf.scrabblefactory.models.components.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.GameComponent;
import de.thb.paf.scrabblefactory.models.components.IComponent;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;

/**
 * Graphics component responsible for rendering a group layout container.
 * @see GroupLayout
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class GroupedGraphicsComponent extends GameComponent implements IGraphicsComponent {

    /**
     * The render group responsible for grouping all textures into a render container
     */
    private GroupLayout groupLayout;

    /**
     * List of associated graphics components
     */
    private List<IGraphicsComponent> components;

    /**
     * Constructor
     * @param id The game component's unique id
     */
    public GroupedGraphicsComponent(Integer id) {
        super(id, ComponentType.GFX_COMPONENT);
        this.components = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime) {

        this.groupLayout.setRotation(this.getParent().getRotation());

        Vector2 position = this.getParent().getPosition();
        this.groupLayout.setPosition(
                position.x,
                position.y
        );

        for(IGraphicsComponent component : this.components) {
            ((IComponent)component).update(deltaTime);
        }

    }

    @Override
    public void render(Batch batch) {
        this.groupLayout.draw(batch, 1.0f);
    }

    /**
     * Get the group layout responsible to render all associated graphics components.
     * @return The group layout responsible to render all associated graphics components
     */
    public GroupLayout getGroupLayout() {

        if(this.groupLayout == null) {
            this.initGroupLayout();
        }

        return this.groupLayout;
    }

    /**
     * Set the list of associated graphics components.
     * @param components the list of graphics components to associate.
     */
    public void setGraphicsComponents(List<IGraphicsComponent> components) {
        this.components = components;
    }

    /**
     * Initialize the group layout required to render all associated graphics components.
     */
    private void initGroupLayout() {

        this.groupLayout = new GroupLayout(this.getParent());

        final Actor groupedCanvas = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                for(IGraphicsComponent component : components) {
                    component.render(batch);
                }
            }
        };

        Vector2 size = this.getParent().getSize();
        groupedCanvas.setBounds(0, 0, size.x / PPM, size.y / PPM);
        this.groupLayout.addActor(groupedCanvas);
    }
}
