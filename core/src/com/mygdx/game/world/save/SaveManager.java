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
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Player;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventorySlot;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Chest;
import com.mygdx.game.world.Map;
import java.util.ArrayList;

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
    
    public void saveGame(Double hours, Map map, Player player){
        Gdx.files.external(".explore/data").mkdirs();
        file = Gdx.files.external(".explore/data/" + MyGdxGame.playerName + "_" + MyGdxGame.worldName + ".json");
        
        file.writeString(getSaveParamsJson(hours, map, player), false);
        
        System.out.println("Save Done");
    }
    
    
    public void loadGame(Map map, Player player){
        file = Gdx.files.external(".explore/data/" + MyGdxGame.playerName + "_" + MyGdxGame.worldName + ".json");
        
        SaveParams saveParams = json.fromJson(SaveParams.class, file);
        
        //SaveMapParams[][] saveMapParams = new SaveMapParams[Constants.WIDTH_OF_MAP][Constants.HEIGHT_OF_MAP];
        SaveMapParams[][] saveMapParams = saveParams.saveMapParams;
        SavePlayerParams savePlayerParams = saveParams.savePlayerParams;
        ArrayList<SaveChestParams> saveChestsParams = saveParams.saveChestsParams;

        for (int i = 0; i < Constants.WIDTH_OF_MAP; i++) {
            for (int j = 0; j < Constants.HEIGHT_OF_MAP; j++) {
                //System.out.print(a[i][j]);
                if (saveMapParams[i][j].id != -1){
                    map.getBlockArray()[i][j] = new Block(GameScreen.allBlocks.getBlockById(saveMapParams[i][j].id));
                    map.getBlockArray()[i][j].blocked = saveMapParams[i][j].blocked;
                    map.getBlockArray()[i][j].textureRotation = saveMapParams[i][j].rotation;
                    if(saveMapParams[i][j].id == AllBlocks.torch.id)
                        map.addTorchToPos(new IntVector2(i, j));
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
                        player.getInventory().getInventoryBarHUD().inventoryBar[i].setObject(GameScreen.allBlocks.getBlockById(savePlayerParams.saveInventoryParams.saveInventoryBar[i].id));
                    }
                    else if (savePlayerParams.saveInventoryParams.saveInventoryBar[i].type == SaveInventorySlot.t.tool){
                        player.getInventory().getInventoryBarHUD().inventoryBar[i].setObject(GameScreen.allTools.getToolById(savePlayerParams.saveInventoryParams.saveInventoryBar[i].id));
                    }
                    else {
                        player.getInventory().getInventoryBarHUD().inventoryBar[i].setObject(GameScreen.allItems.getItemById(savePlayerParams.saveInventoryParams.saveInventoryBar[i].id));
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
                            player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].setObject(GameScreen.allBlocks.getBlockById(savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id));
                        }
                        else if (savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].type == SaveInventorySlot.t.tool){
                            player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].setObject(GameScreen.allTools.getToolById(savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id));
                        }
                        else {
                            player.getInventory().getInventoryPackage().inventoryPackageArray[x][y].setObject(GameScreen.allItems.getItemById(savePlayerParams.saveInventoryParams.saveInventoryPackage[x][y].id));
                        }
                    }
                }
            }
        }   
        
        if (saveChestsParams != null)
        {
            for (SaveChestParams saveChestParams : saveChestsParams) 
            {
                Chest chest = new Chest(saveChestParams.pos);

                for (int y = 0; y < Inventory.numOfRow; y++) {
                    for (int x = 0; x < Inventory.numOfCol; x++) {
                        getSlotFromJson(chest.chestPackage.inventoryPackageArray[x][y], saveChestParams.saveChestPackage[x][y]);
                    }
                }
                map.getChestList().add(chest);
            }
        }
    }
    
    public double getWorldTime(){
        file = Gdx.files.external(".explore/data/" + MyGdxGame.playerName + "_" + MyGdxGame.worldName + ".json");
        
        SaveParams saveParams = json.fromJson(SaveParams.class, file);
  
        return saveParams.hours;
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
    
    private ArrayList<SaveChestParams> getChestParams(ArrayList<Chest> chestList){
        ArrayList<SaveChestParams> saveChestsParams = new ArrayList<>();
        
        SaveChestParams saveChestParams = new SaveChestParams();
        
        for (Chest chest : chestList) 
        {
            saveChestParams.pos = chest.positon;
            
            for (int y = 0; y < Inventory.numOfRow; y++) {
                for (int x = 0; x < Inventory.numOfCol; x++) {
                    parseSlot(chest.chestPackage.inventoryPackageArray[x][y], saveChestParams.saveChestPackage[x][y]);   
                }
            }
            saveChestsParams.add(saveChestParams);

        }
        return saveChestsParams;
    }
    
    private String getSaveParamsJson(Double hours, Map map, Player player){
    
        SaveParams saveParams = new SaveParams();
        
        saveParams.saveMapParams = getMapParams(map);
        saveParams.savePlayerParams = getPlayerParams(player);
        saveParams.saveChestsParams = getChestParams(map.getChestList());
        saveParams.hours = hours;
        
        return json.toJson(saveParams);
    }
    
    private void parseSlot(InventorySlot inventorySlot, SaveInventorySlot saveSlot){
        if (inventorySlot.isEmpty())
        {
            saveSlot.id = -1;
            //savePlayerParams.saveInventoryParams.saveInventoryBar[i].amount = 0;
        }
        else{

            saveSlot.amount = inventorySlot.numOfItem;
            if (inventorySlot.isBlock()){
                saveSlot.id = inventorySlot.getBlock().id;
                saveSlot.type = SaveInventorySlot.t.block;}
            else if (inventorySlot.isTool()){
                saveSlot.id = inventorySlot.getTool().id;
                saveSlot.type = SaveInventorySlot.t.tool;}
            else{
                saveSlot.id = inventorySlot.getItem().id;
                saveSlot.type = SaveInventorySlot.t.item;}

        }
    }
    
    private void getSlotFromJson(InventorySlot inventorySlot, SaveInventorySlot saveSlot){
        if (saveSlot.id != -1)
        {
            inventorySlot.numOfItem = saveSlot.amount;
            if (saveSlot.type == SaveInventorySlot.t.block)
            {
                inventorySlot.setObject(GameScreen.allBlocks.getBlockById(saveSlot.id));
            }
            else if (saveSlot.type == SaveInventorySlot.t.tool){
                inventorySlot.setObject(GameScreen.allTools.getToolById(saveSlot.id));
            }
            else {
                inventorySlot.setObject(GameScreen.allItems.getItemById(saveSlot.id));
            }
        }
    }
    
    
    
}
