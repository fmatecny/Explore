/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

/**
 *
 * @author Fery
 */
public final class Constants {

    public static final int WIDTH_OF_MAP = 1000;
    public static final int HEIGHT_OF_MAP = 100;
    public static final int HEIGHT_OF_SKY = 30;
    public static final int SIZE_OF_CHUNK = 8;
    public static final float PPM = 100.0f;
    public static final float W_IN_M = MyGdxGame.width/PPM;
    public static final float H_IN_M = MyGdxGame.height/PPM;
    
    public static final String SPLIT_CHAR = "_";
    
    public static final int PLAYER_LEFT_BIT = 8;
    public static final int PLAYER_RIGHT_BIT = 2;
    public static final int BLOCK_BIT = 4;
    public static final int UNBLOCK_BIT = 16;
    
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
    public static final int RECEPIE_PICKAXE = 11;
    public static final int RECEPIE_STONE_STAIRS = 12;
    public static final int RECEPIE_HALF_STONE = 13;
    
    public static final int DAY_IN_SECONDS = 600;
    public static final float HOUR_IN_SECONDS = DAY_IN_SECONDS/24;
    public static final int SUNRISE_HOUR = 4;
    public static final int SUNRISE_DURATION = 4;
    public static final int SUNSET_HOUR = 18;
    public static final int SUNSET_DURATION = 4;
    public static final float ALPHA_MIN = 0.3f;
    public static final float ALPHA_MAX = 1.0f;
    
    
    private Constants() {
    }
}
