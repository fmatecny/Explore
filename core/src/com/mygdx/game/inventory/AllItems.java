/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class AllItems {
    
    private enum id
    {
        stick, glass,
        coalIngot, ironIngot, goldIngot, diamondIngot,
        bucket, waterBucket,
        ironArmor, diamondArmor,
        
        redApple, greenApple, blackMushroom, amanitaMushroom
        
    }
    
    public static Item stick;
    public static Item glass;
    public static Item coalIngot;
    public static Item ironIngot;
    public static Item goldIngot;
    public static Item diamondIngot;
    
    public static Item bucket;
    public static Item waterBucket;
    
    public static Item ironArmor;
    public static Item diamondArmor;

    public static Item redApple;
    public static Item greenApple;
    public static Item blackMushroom, amanitaMushroom;
    
    
    public static ArrayList<Item> itemList;
    
    public AllItems() {
        
        itemList = new ArrayList<Item>();
        
        stick = new Item();
        stick.id = id.stick.ordinal();
        stick.info = "Stick";
        stick.texture = new Texture(Gdx.files.internal("item/stick.png"));
        stick.maxItemInBlock = 0;
        itemList.add(stick);
        
        glass = new Item();
        glass.id = id.stick.ordinal();
        glass.info = "Glass";
        glass.texture = new Texture(Gdx.files.internal("item/glass.png"));
        glass.maxItemInBlock = 0;
        itemList.add(glass);
        
        coalIngot = new Item();
        coalIngot.id = id.coalIngot.ordinal();
        coalIngot.info = "Coal";
        coalIngot.texture = new Texture(Gdx.files.internal("item/coalIngot.png"));
        coalIngot.maxItemInBlock = 5;
        itemList.add(coalIngot);
        
        ironIngot = new Item();
        ironIngot.id = id.ironIngot.ordinal();
        ironIngot.info = "Iron";
        ironIngot.texture = new Texture(Gdx.files.internal("item/ironIngot.png"));
        ironIngot.maxItemInBlock = 5;
        itemList.add(ironIngot);
        
        goldIngot = new Item();
        goldIngot.id = id.goldIngot.ordinal();
        goldIngot.info = "Gold";
        goldIngot.texture = new Texture(Gdx.files.internal("item/goldIngot.png"));
        goldIngot.maxItemInBlock = 4;
        itemList.add(goldIngot);
        
        diamondIngot = new Item();
        diamondIngot.id = id.diamondIngot.ordinal();
        diamondIngot.info = "Diamond";
        diamondIngot.texture = new Texture(Gdx.files.internal("item/diamondIngot.png"));
        diamondIngot.maxItemInBlock = 3;
        itemList.add(diamondIngot);
        
        bucket = new Item();
        bucket.id = id.bucket.ordinal();
        bucket.info = "Bucket";
        bucket.texture = new Texture(Gdx.files.internal("item/bucket.png"));
        bucket.maxItemInBlock = 0;
        itemList.add(bucket);
        
        waterBucket = new Item();
        waterBucket.id = id.waterBucket.ordinal();
        waterBucket.info = "Bucket of water";
        waterBucket.texture = new Texture(Gdx.files.internal("item/waterBucket.png"));
        waterBucket.maxItemInBlock = 0;
        itemList.add(waterBucket);
        
        ironArmor = new Item();
        ironArmor.id = id.ironArmor.ordinal();
        ironArmor.info = "Iron armor";
        ironArmor.texture = new Texture(Gdx.files.internal("item/IronArmorBody.png"));
        ironArmor.maxItemInBlock = 0;
        itemList.add(ironArmor);
        
        diamondArmor = new Item();
        diamondArmor.id = id.diamondArmor.ordinal();
        diamondArmor.info = "Diamond armor";
        diamondArmor.texture = new Texture(Gdx.files.internal("item/GuardArmor.png"));
        diamondArmor.maxItemInBlock = 0;
        itemList.add(diamondArmor);
        
        redApple = new Item();
        redApple.id = id.redApple.ordinal();
        redApple.info = "Red Apple";
        redApple.texture = new Texture(Gdx.files.internal("item/redApple.png"));
        redApple.maxItemInBlock = 0;
        redApple.isEatable = true;
        redApple.satiety = 5;
        itemList.add(redApple);
        
        greenApple = new Item();
        greenApple.id = id.greenApple.ordinal();
        greenApple.info = "Green Apple";
        greenApple.texture = new Texture(Gdx.files.internal("item/greenApple.png"));
        greenApple.maxItemInBlock = 0;
        greenApple.isEatable = true;
        greenApple.satiety = 5;
        itemList.add(greenApple);
        
        blackMushroom = new Item();
        blackMushroom.id = id.blackMushroom.ordinal();
        blackMushroom.info = "Black Mushroom";
        blackMushroom.texture = new Texture(Gdx.files.internal("item/blackMushroom.png"));
        blackMushroom.maxItemInBlock = 0;
        blackMushroom.isEatable = true;
        blackMushroom.satiety = 5;
        itemList.add(blackMushroom);
        
        amanitaMushroom = new Item();
        amanitaMushroom.id = id.amanitaMushroom.ordinal();
        amanitaMushroom.info = "Amanita Mushroom";
        amanitaMushroom.texture = new Texture(Gdx.files.internal("item/amanitaMushroom.png"));
        amanitaMushroom.maxItemInBlock = 0;
        amanitaMushroom.isEatable = true;
        amanitaMushroom.satiety = -25;
        itemList.add(amanitaMushroom);
    }
    
    public static Item getItemById(int id){
        if (id < 0)
            return null;
        
        for (Item block : itemList) 
        {
            if (block.id == id)
                return block; 
        }
        
        return null;
    }
}
