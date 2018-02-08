package de.thb.paf.scrabblefactory.models.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.components.physics.RigidBodyPhysicsComponent;

import static de.thb.paf.scrabblefactory.settings.Settings.Game.PPM;

/**
 * Represents a cheese entity.
 * 
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */
public class Cheese extends GameEntity {

    /**
     * The carrying player
     */
    private Player carrier;

    /**
     * Quality characteristic whether the cheese is rotten or not
     */
    private boolean isRotten;

    /**
     * The cheese's associated letter
     */
    private char letter;


    /**
     * Default Constructor
     */
    public Cheese() {
        super();
        this.carrier = null;
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     */
    public Cheese(int id, EntityType type) {
        super(id, type);
        this.carrier = null;
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     * @param letter The cheese's associated letter
     */
    public Cheese(int id, EntityType type, char letter) {
        this(id, type);
        this.letter = letter;
    }

    /**
     * Constructor
     * @param id The entity's unique identifier
     * @param type The entity's type
     * @param letter The cheese's associated letter
     * @param isRotten Quality characteristic whether the cheese is rotten or not
     */
    public Cheese(int id, EntityType type, char letter, boolean isRotten) {
        this(id, type, letter);
        this.isRotten = isRotten;
    }

    /**
     * Get the quality characteristic whether the cheese is rotten or not.
     * @return The rotten status
     */
    public boolean isRotten() {
        return this.isRotten;
    }

    /**
     * Set the quality characteristic whether the cheese is rotten or not.
     * @param isRotten The new rotten status
     */
    public void setRotten(boolean isRotten) {
        this.isRotten = isRotten;
    }

    /**
     * Get the cheese's associated letter.
     * @return The cheese's associated letter
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Get the cheese's associated letter.
     * @param letter The new letter to associate
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Set the carrying player.
     * @param carrier The carrying player
     */
    public void setCarrier(Player carrier) {
        this.carrier = carrier;
    }

    /**
     * Get the carrying player.
     * @return The carrying player.
     */
    public Player getCarrier() {
        return this.carrier;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // check if we're carried by a player and update our position relative to our carrier
        if(this.carrier != null) {
            for(IComponent component : this.components) {
                if(component instanceof RigidBodyPhysicsComponent) {
                    Body body = ((RigidBodyPhysicsComponent)component).getBody();
                    Vector2 carrierPosition = this.carrier.getPosition();
                    Vector2 carrierSize = this.carrier.getSize();
                    float verticalOffset = ((this.carrier.getCheeseItems().indexOf(this) + 1) * (this.getSize().x / PPM)) - (7 / PPM);
                    float horizontalOffset = 15 / PPM;
                    body.setTransform(
                            carrierPosition.x + (carrier.getSize().x / PPM) - horizontalOffset,
                            carrierPosition.y + (carrierSize.y / PPM) + verticalOffset,
                            0
                    );
                }
            }
        }
    }
}