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
        stick, coalIngot, goldIngot, diamondIngot
    }
    
    public static Item stick;
    public static Item coalIngot;
    public static Item goldIngot;
    public static Item diamondIngot;

    public AllItems() {
        
        stick =  new Item();
        stick.id = id.stick.ordinal();
        stick.texture = new Texture(Gdx.files.internal("item/stick.png"));
        stick.maxItemInBlock = 0;
        
        coalIngot =  new Item();
        coalIngot.id = id.coalIngot.ordinal();
        coalIngot.texture = new Texture(Gdx.files.internal("item/coalIngot.png"));
        coalIngot.maxItemInBlock = 5;
        
        goldIngot =  new Item();
        goldIngot.id = id.goldIngot.ordinal();
        goldIngot.texture = new Texture(Gdx.files.internal("item/goldIngot.png"));
        goldIngot.maxItemInBlock = 4;
        
        diamondIngot =  new Item();
        diamondIngot.id = id.diamondIngot.ordinal();
        diamondIngot.texture = new Texture(Gdx.files.internal("item/diamondIngot.png"));
        diamondIngot.maxItemInBlock = 3;
        
        
        
    }
    
    
    
}
