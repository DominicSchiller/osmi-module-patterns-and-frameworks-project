package de.thb.paf.scrabblefactory.gameplay;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.thb.paf.scrabblefactory.factories.EntityFactory;
import de.thb.paf.scrabblefactory.gameplay.timer.ICountdownListener;
import de.thb.paf.scrabblefactory.managers.*;
import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.entities.Cheese;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;


/**
 * Represents a basic object pool center dedicated to manage multiple game item
 * spawn pools.
 * @see GameItemSpawnPool
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameItemSpawnCenter implements ICountdownListener {

    /**
     * The game's current scrabble search word
     */
    private String searchWord;

    /**
     * List of active game item spawn pools
     */
    private List<GameItemSpawnPool> spawnPools;

    /**
     * Status if this center is running
     */
    private boolean isRunning;

    /**
     * Private Default Constructor.
     */
    private GameItemSpawnCenter() {
        this.isRunning = false;
        this.spawnPools = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param searchWord The game's current scrabble search word
     * @param spawnPools List of game item spawn pools.
     */
    public GameItemSpawnCenter(String searchWord, GameItemSpawnPool... spawnPools) {
        this();
        this.searchWord = searchWord;
        this.spawnPools.addAll(Arrays.asList(spawnPools));
        this.initSpawnPools();
    }


    /**
     * Reset all current spawn pools based on updated search word.
     * @param searchWord The update search word
     */
    public void reset(String searchWord) {
        this.stopSpawning();

        this.searchWord = searchWord;
        for(GameItemSpawnPool spawnPool : this.spawnPools) {
            spawnPool.clear();
        }
        this.initSpawnPools();
    }

    /**
     * Start spawning associated spawn pools.
     */
    public void startSpawning() {
        this.isRunning = true;
    }

    /**
     * Stop spawning associated spawn pools.
     */
    public void stopSpawning() {
        this.isRunning = false;
    }

    @Override
    public void onCountdownStarted(long time) {}

    @Override
    public void onCountdownTick(long time) {
        if(this.isRunning) {
            for(GameItemSpawnPool spawnConfig : this.spawnPools) {
                spawnConfig.tickDown();
            }
        }
    }

    @Override
    public void onCountdownFinished(long time) {}

    /**
     * Clear all spawn pools from the spawn center.
     */
    public void clear() {
        for(GameItemSpawnPool spawnPool : this.spawnPools) {
            spawnPool.clear();
        }

        this.spawnPools.clear();
    }

    /**
     * Initialize all associated game item spawn pools by creating inactive game items
     * to spawn from.
     */
    private void initSpawnPools() {
        EntityFactory entityFactory = new EntityFactory();
        GameObjectManager gom = GameObjectManager.getInstance();
        de.thb.paf.scrabblefactory.managers.PlayScreenRestoreManager restoreManager = de.thb.paf.scrabblefactory.managers.PlayScreenRestoreManager.getInstance();

        for(GameItemSpawnPool spawnPool : this.spawnPools) {

            List<IEntity> gameItems = gom.getGameEntity(spawnPool.itemType);
            int remainingItems = spawnPool.maxAllowedItemsCount - gameItems.size();
            int minItems = remainingItems < 0 ? spawnPool.maxAllowedItemsCount : gameItems.size();
            for(int i=0; i<minItems; i++) {
                IEntity gameItem = gameItems.get(i);
                if(gameItem.getType() == EntityType.CHEESE) {
                    ((Cheese)gameItem).setLetter(this.searchWord.charAt(i));
                    ((Cheese)gameItem).setCarrier(null);
                    ((Cheese)gameItem).setCaught(false);
                    gameItem.setActive(false);
                    Vector2 restorePosition = restoreManager.getRestorePosition(gameItem);
                    if(restorePosition != null) {
                        for(IComponent component : gameItem.getAllComponents(ComponentType.PHYS_COMPONENT)) {
                            if(component instanceof RigidBodyPhysicsComponent) {
                                Body body = ((RigidBodyPhysicsComponent) component).getBody();
                                body.setType(BodyDef.BodyType.DynamicBody);
                                for(Fixture fixture : body.getFixtureList()) {
                                    fixture.setSensor(false);
                                }
                                body.setTransform(
                                        restorePosition.x,
                                        restorePosition.y,
                                        0
                                );
                            }
                        }
                    }
                }
                spawnPool.addGameItem(gameItem);
            }

            if(remainingItems > 0) {
                for(int i = gameItems.size(); i<remainingItems; i++) {
                    IEntity gameItem = entityFactory.getEntity(spawnPool.itemType, 1);
                    gameItem.setActive(false);
                    if(gameItem.getType() == EntityType.CHEESE) {
                        ((Cheese)gameItem).setLetter(this.searchWord.charAt(i));
                    }
                    spawnPool.addGameItem(gameItem);
                }
            }
        }
    }
}
