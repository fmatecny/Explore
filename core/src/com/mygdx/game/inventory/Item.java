/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.mygdx.game.Constants;

/**
 *
 * @author Fery
 */
public class Item extends InventoryObject{
       
    public int maxItemInBlock = 0;
    
    public Item(Item i) {
        this.id = i.id;
        this.texture = i.texture;
        this.maxItemInBlock = i.maxItemInBlock;
    }
    
    public Item() {
    }
    
}
