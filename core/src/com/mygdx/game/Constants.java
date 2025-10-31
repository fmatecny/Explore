/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author Fery
 */
public final class Constants {

    public static final int WIDTH_OF_MAP = 700;
    public static final int HEIGHT_OF_MAP = 150;
    public static final int HEIGHT_OF_SKY = 30;
    public static final int SIZE_OF_CHUNK = 16;
    public static final float PPM = 100.0f;
    //TODO can be changed during runtime -> cant be conctant
    public static final float W_IN_M = MyGdxGame.width/PPM;
    public static final float H_IN_M = MyGdxGame.height/PPM;
    public static final float ENTITY_SCREEN_OFFSET = 1.0f;
    
    public static final String SPLIT_CHAR = "_";
    
    public static final int PLAYER_LEFT_BIT = 8;
    public static final int PLAYER_RIGHT_BIT = 2;
    public static final int BLOCK_BIT = 4;
    public static final int UNBLOCK_BIT = 16;
    //public static final int VILLAGER_BIT = 32;
    public static final int PLAYER_BIT = 32;
    public static final int ENTITY_BIT = 64;
    public static final int ENTITY_LEFT_BIT = 128;
    public static final int ENTITY_RIGHT_BIT = 256;
    //public static final int GOLEM_BIT = 128;
    
    public static final int RECEPIE_PLANK = 1;
    public static final int RECEPIE_HALF_PLANK = 2;
    public static final int RECEPIE_WOOD_STAIRS = 3;
    public static final int RECEPIE_WOOD_DOOR = 4;
    public static final int RECEPIE_STICK = 5;
    public static final int RECEPIE_TORCH = 6;
    public static final int RECEPIE_LADDER = 7;
    public static final int RECEPIE_BUCKET = 8;
    public static final int RECEPIE_CHEST = 9;
    public static final int RECEPIE_FURNACE = 10;
    public static final int RECEPIE_STONE_STAIRS = 11;
    public static final int RECEPIE_HALF_STONE = 12;
    public static final int RECEPIE_WINDOW = 13;
    
    public static final int RECEPIE_STONE_AXE = 100;
    public static final int RECEPIE_IRON_AXE = 101;
    public static final int RECEPIE_DIAMOND_AXE = 102;
    
    public static final int RECEPIE_WOOD_PICKAXE = 103;
    public static final int RECEPIE_STONE_PICKAXE = 104;
    public static final int RECEPIE_IRON_PICKAXE = 105;
   // public static final int RECEPIE_DIAMOND_PICKAXE = 106;
    
   // public static final int RECEPIE_WOOD_SWORD = 107;
    public static final int RECEPIE_STONE_SWORD = 108;
   // public static final int RECEPIE_IRON_SWORD = 109;
    public static final int RECEPIE_DIAMOND_SWORD = 110;
    
    public static final int DAY_IN_SECONDS = 600;
    public static final float HOUR_IN_SECONDS = DAY_IN_SECONDS/24;
    public static final int SUNRISE_HOUR = 4;
    public static final int SUNRISE_DURATION = 4;
    public static final int SUNSET_HOUR = 18;
    public static final int SUNSET_DURATION = 4;
    public static final float ALPHA_MIN = 0.3f;
    public static final float ALPHA_MAX = 1.0f;
    
    public static enum typeOfDirection { Left, Right };
    public static enum typeOfMovement { Stand, Walk, Run, Jump, Slash, Hit, Die };
    public static enum typeOfArmor { Default, Leather, Iron, Diamond };
    public static enum typeOfEntity{ villager, girl, smith, golem, skeleton, knight, king };
    
    private Constants() {
    }
    
   /* public int GetCategoryBit(typeOfEntity toe){
        
        switch (toe) 
        {
            case villager:  return VILLAGER_BIT;
            case girl:      return VILLAGER_BIT;
            case smith:     return VILLAGER_BIT;
            case golem:     return GOLEM_BIT;
                
            default:
                throw new AssertionError();
        }
    }*/
}
