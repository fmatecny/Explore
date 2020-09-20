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
        stick, 
        coalIngot, ironIngot, goldIngot, diamondIngot,
        bucket, waterBucket
        
    }
    
    public static Item stick;
    public static Item coalIngot;
    public static Item ironIngot;
    public static Item goldIngot;
    public static Item diamondIngot;
    
    public static Item bucket;
    public static Item waterBucket;

    public AllItems() {
        
        stick =  new Item();
        stick.id = id.stick.ordinal();
        stick.texture = new Texture(Gdx.files.internal("item/stick.png"));
        stick.maxItemInBlock = 0;
        
        coalIngot =  new Item();
        coalIngot.id = id.coalIngot.ordinal();
        coalIngot.texture = new Texture(Gdx.files.internal("item/coalIngot.png"));
        coalIngot.maxItemInBlock = 5;
        
        ironIngot =  new Item();
        ironIngot.id = id.ironIngot.ordinal();
        ironIngot.texture = new Texture(Gdx.files.internal("item/ironIngot.png"));
        ironIngot.maxItemInBlock = 5;
        
        goldIngot =  new Item();
        goldIngot.id = id.goldIngot.ordinal();
        goldIngot.texture = new Texture(Gdx.files.internal("item/goldIngot.png"));
        goldIngot.maxItemInBlock = 4;
        
        diamondIngot =  new Item();
        diamondIngot.id = id.diamondIngot.ordinal();
        diamondIngot.texture = new Texture(Gdx.files.internal("item/diamondIngot.png"));
        diamondIngot.maxItemInBlock = 3;
        
        bucket =  new Item();
        bucket.id = id.bucket.ordinal();
        bucket.texture = new Texture(Gdx.files.internal("item/bucket.png"));
        bucket.maxItemInBlock = 0;
        
        bucket =  new Item();
        bucket.id = id.bucket.ordinal();
        bucket.texture = new Texture(Gdx.files.internal("item/bucket.png"));
        bucket.maxItemInBlock = 0;
        
        waterBucket =  new Item();
        waterBucket.id = id.waterBucket.ordinal();
        waterBucket.texture = new Texture(Gdx.files.internal("item/waterBucket.png"));
        waterBucket.maxItemInBlock = 0;
    }
    
    
    
}
