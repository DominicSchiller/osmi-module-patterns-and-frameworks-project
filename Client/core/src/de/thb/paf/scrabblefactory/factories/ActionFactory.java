package de.thb.paf.scrabblefactory.factories;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import de.thb.paf.scrabblefactory.models.actions.IGameAction;
import de.thb.paf.scrabblefactory.models.components.IComponent;
import de.thb.paf.scrabblefactory.models.events.GameEventType;
import de.thb.paf.scrabblefactory.models.events.IGameEvent;
import de.thb.paf.scrabblefactory.utils.CloneComponentHelper;
import de.thb.paf.scrabblefactory.utils.ScrabbleFactoryClassLoader;

import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_JAVA_PACKAGE;
import static de.thb.paf.scrabblefactory.settings.Constants.Json.JSON_KEY_NAME;

/**
 * Factory class dedicated to create and assemble new game actions.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class ActionFactory {

    /**
     * Default Constructor
     */
    public ActionFactory() {}

    /**
     * Get the game action instance defined by it's JSON definition.
     * @param actionDef The action's definition JSON object
     * @param parent The component object which will be controllbed by the game action instance
     * @return The requested game action instance
     */
    public IGameAction getGameAction(JsonObject actionDef, IComponent parent) {
        Gson gson = new GsonBuilder().create();
        String actionName = actionDef.get(JSON_KEY_NAME).getAsString();
        String javaPackageName = actionDef.get(JSON_KEY_JAVA_PACKAGE).getAsString();

        IGameAction action = (IGameAction) ScrabbleFactoryClassLoader.createInstance(javaPackageName, actionName, parent);
        if(action != null) {
            IGameAction parsedAction = gson.fromJson(actionDef, action.getClass());
            CloneComponentHelper.cloneFieldValues(parsedAction, action);
            this.registerToEvents(action);
        }

        return action;
    }

    /**
     * Register the game action to associated game events.
     * @param action The game action instance to register with game events
     */
    public void registerToEvents(IGameAction action) {
        EventFactory eventFactory = new EventFactory();
        for(GameEventType eventType : action.getEventTypesToHandle()) {
            IGameEvent event = eventFactory.getGameEvent(eventType);
            event.addListener(action);
        }
    }
}
