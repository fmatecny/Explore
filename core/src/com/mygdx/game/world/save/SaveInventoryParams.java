/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.save;

import com.mygdx.game.inventory.Inventory;

/**
 *
 * @author Fery
 */
public class SaveInventoryParams {
    
    public SaveInventorySlot[] saveInventoryBar;
    public SaveInventorySlot[][] saveInventoryPackage;
    
    public SaveInventoryParams() {
        saveInventoryBar = new SaveInventorySlot[Inventory.numOfCol];
        saveInventoryPackage = new SaveInventorySlot[Inventory.numOfCol][Inventory.numOfRow];
        
        for (int x = 0; x < Inventory.numOfCol; x++) 
        {
            saveInventoryBar[x] = new SaveInventorySlot();
            
            for (int y = 0; y < Inventory.numOfRow; y++) 
            {
                saveInventoryPackage[x][y] = new SaveInventorySlot();
            }
        }
        
    } 
}

