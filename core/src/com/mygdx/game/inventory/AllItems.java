/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.mygdx.game.inventory.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Fery
 */
public class AllItems {
    
    private enum id
    {
        stick
    }
    
    public static Item stick;

    public AllItems() {
        
        stick =  new Item();
        stick.id = id.stick.ordinal();
        stick.texture = new Texture(Gdx.files.internal("item/stick.png"));
        
        
        
        
    }
    
    
    
}
