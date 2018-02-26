package de.thb.paf.scrabblefactory.gameplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.thb.paf.scrabblefactory.factories.EntityFactory;
import de.thb.paf.scrabblefactory.gameplay.timer.ICountdownListener;
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

        for(GameItemSpawnPool spawnPool : this.spawnPools) {
            for(int i = 0; i<spawnPool.maxAllowedItemsCount; i++) {
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
