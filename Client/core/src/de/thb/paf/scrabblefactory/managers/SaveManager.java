package de.thb.paf.scrabblefactory.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Saves the game data
 *
 * @author Melanie Steiner - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class SaveManager {

    public ObjectMap<String, Object> data = new ObjectMap<String, Object>();

    //saves the data as local json-file
    private FileHandle file = Gdx.files.local("bin/scores.json");

    //method loads the highscore json-file
    private SaveManager getSave(){
        SaveManager save = new SaveManager();
        if(file.exists()){Json json = new Json();
      //  if(encoded)save = json.fromJson(SaveManager.class, Base64Coder.decodeString(file.readString()));
      //  else save = json.fromJson(SaveManager.class,file.readString());
        }
        return save;
    }

    //method saves highscores in the json-file
    public void saveToFile(){
        SaveManager save = new SaveManager();
        Json json = new Json();  json.setOutputType(JsonWriter.OutputType.json);
     //   if(encoded) file.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
     //   else file.writeString(json.prettyPrint(save), false);
    }

    //method loads the actual highscore value
    public <T> T loadDataValue(String key, Class type){
        SaveManager save = new SaveManager();
        if(save.data.containsKey(key))return (T) save.data.get(key);
        else return null;
    }

    //mehtod saves the actual highscore data value
    public void saveDataValue(String key, Object object){
        SaveManager save = new SaveManager();
        save.data.put(key, object);
        saveToFile();
    }

    //mehtod prepares local highscore
    private void prepareLocalScores() {
    //    SaveManager=new SaveManager(true);
    //    if(saveManager.loadDataValue("Score1", int.class)==null){
    //        for(int i=1;i<=10;i++){
    //            saveManager.saveDataValue("Score"+i, 0);
    //        }
    //    }
    }
}
