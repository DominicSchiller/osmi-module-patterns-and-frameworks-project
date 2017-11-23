package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.List;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.factories.EntityFactory;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.models.level.ILevel;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.factories.LevelFactory;
import de.thb.paf.scrabblefactory.utils.debug.VisualGameDebugger;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_HEIGHT;
import static de.thb.paf.scrabblefactory.settings.Settings.Game.VIRTUAL_WIDTH;

/**
 * Represents the play screen where all single and multi-player levels take place.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class PlayScreen extends GameScreen {

    private VisualGameDebugger debugRenderer;
    private OrthographicCamera camera;
    private ILevel level;
    private IEntity player;

    /**
     * Default Constructor
     */
    public PlayScreen() {
        super(ScreenState.PLAY);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(
                false,
                VIRTUAL_WIDTH,
                VIRTUAL_HEIGHT);
        ScrabbleFactory.getInstance().batch.setProjectionMatrix(this.camera.combined);
    }

    @Override
    public void update(float deltaTime) {
        // TODO: Implement here...
        this.level.update(deltaTime);
        this.player.update(deltaTime);
    }

    @Override
    public void show() {

        this.level = new LevelFactory().getLevel(1);
        this.player = new EntityFactory().getEntity(EntityType.PLAYER, 1);
        this.debugRenderer = new VisualGameDebugger();

        // TODO: Implement here...
    }

    @Override
    public void render(float delta) {

        // update the screen first before rendering it's content
        this.update(delta);

        Gdx.gl.glClearColor(1/255f, 8/255f, 15/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // TODO: Implement here...
        ScrabbleFactory.getInstance().batch.begin();

        //TODO Implement more elegant method
        List<IComponent> components = this.level.getAllComponents(ComponentType.GFX_COMPONENT);
        for(IComponent component : components) {
            ((IGraphicsComponent) component).render(ScrabbleFactory.getInstance().batch);
        }

        components = this.player.getAllComponents(ComponentType.GFX_COMPONENT);
        for(IComponent component : components) {
            ((IGraphicsComponent) component).render(ScrabbleFactory.getInstance().batch);
        }

        ScrabbleFactory.getInstance().batch.end();

        this.debugRenderer.render(
                ScrabbleFactory.getInstance().batch,
//                this.camera.combined.cpy().scl(PPM)
                this.camera.combined
        );
    }

    @Override
    public void resize(int width, int height) {
        // TODO: Implement here...
    }

    @Override
    public void pause() {
        // TODO: Implement here...
    }

    @Override
    public void resume() {
        // TODO: Implement here...
    }

    @Override
    public void hide() {
        // TODO: Implement here...
    }

    @Override
    public void dispose() {
        // TODO: Implement here...
    }
}
