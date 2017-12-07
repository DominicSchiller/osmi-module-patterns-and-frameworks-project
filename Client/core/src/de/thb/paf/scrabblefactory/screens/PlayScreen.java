package de.thb.paf.scrabblefactory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.List;

import de.thb.paf.scrabblefactory.ScrabbleFactory;
import de.thb.paf.scrabblefactory.factories.EntityFactory;
import de.thb.paf.scrabblefactory.factories.HUDSystemFactory;
import de.thb.paf.scrabblefactory.models.IGameObject;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.graphics.IGraphicsComponent;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.models.hud.HUDSystem;
import de.thb.paf.scrabblefactory.models.hud.HUDSystemType;
import de.thb.paf.scrabblefactory.models.hud.IHUDComponent;
import de.thb.paf.scrabblefactory.models.level.ILevel;
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
    private HUDSystem hud;
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

        ScrabbleFactory.getInstance().batch.setProjectionMatrix(
                this.camera.combined
        );

        ScrabbleFactory.getInstance().textBatch.setProjectionMatrix(
                this.camera.combined.cpy().scl(1/PPM)
        );
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
        this.hud = new HUDSystemFactory().getHUDSystem(HUDSystemType.SINGLE_PLAYER_HUD);
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

        Batch batch = ScrabbleFactory.getInstance().batch;
        Batch textBatch = ScrabbleFactory.getInstance().textBatch;

        //TODO Implement more elegant method to render components
        List<IComponent> components = this.level.getAllComponents(ComponentType.GFX_COMPONENT);
        for(IComponent component : components) {
            ((IGraphicsComponent) component).render(batch);
        }

        components = this.player.getAllComponents(ComponentType.GFX_COMPONENT);
        for(IComponent component : components) {
            ((IGraphicsComponent) component).render(batch);
        }

        // render HUD components
        List<IHUDComponent> hudComponents = this.hud.getHUDComponents();
        for(IGameObject hudComponent: hudComponents) {
            components = hudComponent.getAllComponents(ComponentType.GFX_COMPONENT);
            for(IComponent component : components) {
                ((IGraphicsComponent) component).render(batch);
            }
        }

        this.debugRenderer.render(textBatch);
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
