/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.save;

import com.mygdx.game.IntVector2;
import com.mygdx.game.inventory.Inventory;

/**
 *
 * @author Fery
 */
public class SaveChestParams {
    
    public IntVector2 pos;
    public SaveInventorySlot[][] saveChestPackage;

    public SaveChestParams() {
        saveChestPackage = new SaveInventorySlot[Inventory.numOfCol][Inventory.numOfRow];
        for (int x = 0; x < Inventory.numOfCol; x++) {
            for (int y = 0; y < Inventory.numOfRow; y++) {
                saveChestPackage[x][y] = new SaveInventorySlot();
            }
        }
    }
    
    
    
}
