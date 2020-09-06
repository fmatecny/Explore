/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.mygdx.game.inventory.InventoryObject;

/**
 *
 * @author Fery
 */
public class Tool extends InventoryObject{
    

    public Tool(Tool t) {
        this.id = t.id;
        this.texture = t.texture;
    }

    public Tool() {
    } 
}
