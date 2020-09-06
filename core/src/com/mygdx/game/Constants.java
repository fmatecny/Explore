/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.mygdx.game.screens.GameScreen;

/**
 *
 * @author Fery
 */
public final class Constants {

    public static final int WIDTH_OF_MAP = 500;
    public static final int HEIGHT_OF_MAP = 100;
    public static final int HEIGHT_OF_SKY = 30;
    public static final int SIZE_OF_CHUNK = 8;
    public static final float PPM = 100.0f;
    public static final float W_IN_M = MyGdxGame.width/PPM;
    public static final float H_IN_M = MyGdxGame.height/PPM;
    
    public static final int PLAYER_LEFT_BIT = 8;
    public static final int PLAYER_RIGHT_BIT = 2;
    public static final int BLOCK_BIT = 4;
    
    public static final int RECEPIE_PLANK = 1;
    public static final int RECEPIE_HALF_PLANK = 2;
    public static final int RECEPIE_WOOD_STAIRS = 3;
    public static final int RECEPIE_WOOD_DOOR = 4;
    public static final int RECEPIE_STICK = 5;

    private Constants() {
    }
}
