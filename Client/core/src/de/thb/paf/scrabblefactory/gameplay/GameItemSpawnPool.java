package de.thb.paf.scrabblefactory.gameplay;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

import de.thb.paf.scrabblefactory.models.components.ComponentType;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;
import de.thb.paf.scrabblefactory.models.entities.EntityType;
import de.thb.paf.scrabblefactory.models.entities.IEntity;
import de.thb.paf.scrabblefactory.settings.Settings;
import de.thb.paf.scrabblefactory.utils.Randomizer;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;

/**
 * Represents a self-managed game item object pool used to spawn game items of defined
 * entity type.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class GameItemSpawnPool {

    /**
     * The type of items to spawn
     */
    public final EntityType itemType;

    /**
     * The minimum time delay to spawn items in seconds
     */
    public final int minDelay;

    /**
     * The maximum time delay to spawn items in seconds
     */
    public final int maxDelay;

    /**
     * The maximum allowed count of items allowed in the object pool
     */
    public final int maxAllowedItemsCount;

    /**
     * The current countdown time before spawning the next item
     */
    private int spawnCountdownValue;

    /**
     * List of already spawned items
     */
    private List<IEntity> spawnedItems;

    /**
     * List of un-spawned items which are still spawnable
     */
    private List<IEntity> spawnableItems;

    /**
     * The sound to play when an item has been spawned
     */
    private Sound spawnSound;

    /**
     * Constructor.
     * @param itemType The type of items to spawn
     * @param minDelay The minimum time delay to spawn items in seconds
     * @param maxDelay The maximum time delay to spawn items in seconds
     */
    public GameItemSpawnPool(EntityType itemType, int minDelay, int maxDelay, int maxAllowedItemsCount) {
        this.itemType = itemType;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.maxAllowedItemsCount = maxAllowedItemsCount;
        this.spawnedItems = new ArrayList<>();
        this.spawnableItems = new ArrayList<>();

        this.spawnCountdownValue = Randomizer.nextRandomInt(this.minDelay, this.maxDelay);
        this.spawnSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/plop.mp3"));
    }

    /**
     * Tick down the countdown before spawning the next game item.
     */
     void tickDown() {
        if(--this.spawnCountdownValue <= 0) {
            this.spawnCountdownValue = Randomizer.nextRandomInt(this.minDelay, this.maxDelay);
            if(this.spawnableItems.size() > 0) {
                this.spawnItem();

            }
        } else {
            System.out.println("Next item will be spawned in ... " + this.spawnCountdownValue + " seconds");
        }
    }

    /**
     * Add a game item which can be spawned.
     * @param gameItem The game item allowed to spawn
     */
    void addGameItem(IEntity gameItem) {
        if(gameItem.isActive()) {
            this.spawnedItems.add(gameItem);
        } else {
            this.spawnableItems.add(gameItem);
        }
    }

    /**
     * Spawn a randomly selected game item from the object pool of spawnable items.
     */
    private void spawnItem() {
        int index = Randomizer.nextRandomInt(0, this.spawnableItems.size() - 1);
        IEntity gameItem = this.spawnableItems.get(index);
        this.spawnedItems.add(gameItem);
        this.spawnableItems.remove(gameItem);

        int xPosition = Randomizer.nextRandomInt(
                0, (int)(Settings.Game.VIRTUAL_WIDTH - gameItem.getSize().x / PPM));
        int yPosition = (int)(Settings.Game.VIRTUAL_HEIGHT + 10 * (gameItem.getSize().y / PPM));

        for(IComponent component : gameItem.getAllComponents(ComponentType.PHYS_COMPONENT)) {
            if(component instanceof RigidBodyPhysicsComponent) {
                ((RigidBodyPhysicsComponent)component).getBody().setTransform(
                        xPosition, yPosition, 0
                );
            }
        }

        this.spawnSound.play();
        gameItem.setActive(true);
    }
}
