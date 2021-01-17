/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.mygdx.game.IntVector2;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryPackage;

/**
 *
 * @author Fery
 */
public class Chest {

    public IntVector2 positon;
    public InventoryPackage chestPackage;
    
    public Chest(int x, int y) {
        positon = new IntVector2(x, y);
        chestPackage = new InventoryPackage(Inventory.numOfCol, Inventory.numOfRow);
    }
    
    public Chest(IntVector2 v) {
        this(v.X, v.Y);
    }

}
