/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.world;

import com.mygdx.game.IntVector2;
import com.mygdx.game.inventory.InventoryFurnace;

/**
 *
 * @author Fery
 */
public class Furnace {
    
    public IntVector2 positon;
    public InventoryFurnace inventoryFurnace; 
    
    public Furnace(int x, int y) {
        positon = new IntVector2(x, y);
        inventoryFurnace = new InventoryFurnace();
    }
    
    
    public Furnace(IntVector2 v) {
        this(v.X, v.Y);
    }
}
