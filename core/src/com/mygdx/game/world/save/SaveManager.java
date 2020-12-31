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
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventorySlot;
import static com.mygdx.game.screens.GameScreen.allBlocks;
import static com.mygdx.game.screens.GameScreen.allItems;
import static com.mygdx.game.screens.GameScreen.allTools;
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
        
        file.writeString(getSaveParamsJson(map, player), false);
        
        System.out.println("Save Done");
    }
    
    
    public void loadGame(Map map, Player player){
        file = Gdx.files.external(".explore/data/" + MyGdxGame.playerName + "_" + MyGdxGame.worldName + ".json");
        
        SaveParams saveParams = json.fromJson(SaveParams.class, file);
        
        //SaveMapParams[][] saveMapParams = new SaveMapParams[Constants.WIDTH_OF_MAP][Constants.HEIGHT_OF_MAP];
        SaveMapParams[][] saveMapParams = saveParams.saveMapParams;
        SavePlayerParams savePlayerParams = saveParams.savePlayerParams;
    
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
        
        if (savePlayerParams != null)
        {
            player.b2body.setTransform(savePlayerParams.position, 0);
            for (int i = 0; i < Inventory.numOfCol; i++) 
            {
                if (savePlayerParams.saveInventoryParams.saveInventoryBar[i].id != -1)
                {
                    player.getInventory().getInventoryBarHUD().inventoryBar[i].numOfItem = savePlayerParams.saveInventoryParams.saveInventoryBar[i].amount;
                    
                    if (savePlayerParams.saveInventoryParams.saveInventoryBar[i].type == SaveInventorySlot.t.block)
                    {
                        player.getInventory().getInventoryBarHUD().inventoryBar[i].setObject(allBlocks.getBlockById(savePlayerParams.saveInventoryParams.saveInventoryBar[i].id));
                    }
                    else if (savePlayerParams.saveInventoryParams.saveInventoryBar[i].type == SaveInventorySlot.t.tool){
                        player.getInventory().getInventoryBarHUD().inventoryBar[i].setObject(allTools.getToolById(savePlayerParams.saveInventoryParams.saveInventoryBar[i].id));
                    }
                    else {
                        player.getInventory().getInventoryBarHUD().inventoryBar[i].setObject(allItems.getItemById(savePlayerParams.saveInventoryParams.saveInventoryBar[i].id));
                    }
                    
                }
            }  
            
            
            
            for (int x = 0; x < Inventory.numOfCol; x++) 
            {
                for (int y = 0; y < Inventory.numOfRow; y++) 
                {
                    if (savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id != -1)
                    {
                        player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].numOfItem = savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].amount;
                        if (savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].type == SaveInventorySlot.t.block)
                        {
                            player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].setObject(allBlocks.getBlockById(savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id));
                        }
                        else if (savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].type == SaveInventorySlot.t.tool){
                            player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].setObject(allTools.getToolById(savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id));
                        }
                        else {
                            player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].setObject(allItems.getItemById(savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id));
                        }
                    }
                }
            }
            
            
            
        }    
    }
    
    
    
    public SaveMapParams[][] getMapParams(Map map){
        
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
        
        return saveMapParams;
    }
    
    
    private SavePlayerParams getPlayerParams(Player player){
        SavePlayerParams savePlayerParams = new SavePlayerParams();
        
        savePlayerParams.position = player.b2body.getPosition();
        
        InventorySlot inventorySlot;
        
        for (int i = 0; i < Inventory.numOfCol; i++) 
        {
            inventorySlot = player.getInventory().getInventoryBarHUD().inventoryBar[i];
            if (inventorySlot.isEmpty())
            {
                savePlayerParams.saveInventoryParams.saveInventoryBar[i].id = -1;
                //savePlayerParams.saveInventoryParams.saveInventoryBar[i].amount = 0;
            }
            else{
                savePlayerParams.saveInventoryParams.saveInventoryBar[i].amount = inventorySlot.numOfItem;
                if (inventorySlot.isBlock()){
                    savePlayerParams.saveInventoryParams.saveInventoryBar[i].id = inventorySlot.getBlock().id;
                    savePlayerParams.saveInventoryParams.saveInventoryBar[i].type = SaveInventorySlot.t.block;}
                else if (inventorySlot.isTool()){
                    savePlayerParams.saveInventoryParams.saveInventoryBar[i].id = inventorySlot.getTool().id;
                    savePlayerParams.saveInventoryParams.saveInventoryBar[i].type = SaveInventorySlot.t.tool;}
                else{
                    savePlayerParams.saveInventoryParams.saveInventoryBar[i].id = inventorySlot.getItem().id;
                    savePlayerParams.saveInventoryParams.saveInventoryBar[i].type = SaveInventorySlot.t.item;}
            
            }    
            
        }
        
        for (int y = 0; y < Inventory.numOfRow; y++) {
            for (int x = 0; x < Inventory.numOfCol; x++) {
                inventorySlot = player.getInventory().getInventoryPackage().inventoryPackageArray[x][y];
                if (inventorySlot.isEmpty())
                {
                    savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id = -1;
                    //savePlayerParams.saveInventoryParams.saveInventoryBar[i].amount = 0;
                }
                else{
                    
                    savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].amount = inventorySlot.numOfItem;
                    if (inventorySlot.isBlock()){
                        savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id = inventorySlot.getBlock().id;
                        savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].type = SaveInventorySlot.t.block;}
                    else if (inventorySlot.isTool()){
                        savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id = inventorySlot.getTool().id;
                        savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].type = SaveInventorySlot.t.tool;}
                    else{
                        savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id = inventorySlot.getItem().id;
                        savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].type = SaveInventorySlot.t.item;}

                }  
            } 
        }
        
        
        return savePlayerParams;  
    }
    
    
    private String getSaveParamsJson(Map map, Player player){
    
        SaveParams saveParams = new SaveParams();
        
        saveParams.saveMapParams = getMapParams(map);
        saveParams.savePlayerParams = getPlayerParams(player);
        
        return json.toJson(saveParams);
    }
    
    
}
