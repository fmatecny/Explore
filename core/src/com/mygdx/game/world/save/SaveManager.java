/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Player;
import static com.mygdx.game.screens.GameScreen.allBlocks;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Map;

/**
 *
 * @author Fery
 */
public class SaveManager {

    private FileHandle file;
    
    
    Json json;
    
    
    public SaveManager() {
        json = new Json();
        json.setOutputType(OutputType.minimal);
        
    }
    
    public void saveGame(Map map, Player player){
        Gdx.files.external(".explore/data").mkdirs();
        file = Gdx.files.external(".explore/data/" + MyGdxGame.playerName + "_" + MyGdxGame.worldName + ".json");
        
        //getMapParamsJson(map);
        
        
        
        file.writeString(getMapParamsJson(map), false);
    
    }
    
    
    public void loadGame(Map map){
        file = Gdx.files.external(".explore/data/" + MyGdxGame.playerName + "_" + MyGdxGame.worldName + ".json");
        
        //SaveMapParams[][] saveMapParams = new SaveMapParams[Constants.WIDTH_OF_MAP][Constants.HEIGHT_OF_MAP];
        SaveMapParams[][] saveMapParams = json.fromJson(SaveMapParams[][].class, file);
    
        for (int i = 0; i < Constants.WIDTH_OF_MAP; i++) {
            for (int j = 0; j < Constants.HEIGHT_OF_MAP; j++) {
                //System.out.print(a[i][j]);
                if (saveMapParams[i][j].id != -1){
                    map.getBlockArray()[i][j] = new Block(allBlocks.getBlockById(saveMapParams[i][j].id));
                    map.getBlockArray()[i][j].blocked = saveMapParams[i][j].blocked;
                    map.getBlockArray()[i][j].textureRotation = saveMapParams[i][j].rotation;
                }
                else
                    map.getBlockArray()[i][j] = null;
            }
            //System.out.println("");
        }
        
    }
    
    
    
    public String getMapParamsJson(Map map){
        
        SaveMapParams[][] saveMapParams = new SaveMapParams[Constants.WIDTH_OF_MAP][Constants.HEIGHT_OF_MAP];
        
        for (int i = 0; i < Constants.WIDTH_OF_MAP; i++) {
            for (int j = 0; j < Constants.HEIGHT_OF_MAP; j++) {
                saveMapParams[i][j] = new SaveMapParams();
                if (map.getBlockArray()[i][j] != null){
                    saveMapParams[i][j].id = map.getBlockArray()[i][j].id;
                    saveMapParams[i][j].blocked = map.getBlockArray()[i][j].blocked;
                    saveMapParams[i][j].rotation = map.getBlockArray()[i][j].textureRotation;
                }
                else{
                    saveMapParams[i][j].id = -1;
                    saveMapParams[i][j].blocked = false;
                    saveMapParams[i][j].rotation = 0;
                }
            }
        }
        
        return json.toJson(saveMapParams);
    }
    
    
    
    
}
