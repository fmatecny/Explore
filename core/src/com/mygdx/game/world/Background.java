/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;

/**
 *
 * @author Fery
 */
public class Background {

    private Texture bgImg;
    private Texture bgClouds;
    private Texture bgHugeClouds;
    private Texture bgHill;
    private Texture bgBushes;
    
    private float x,y;
    
    private float bgClouds_speedOffset = 0.97f;
    private float bgHugeClouds_speedOffset = 0.95f;
    private float bgHill_speedOffset = 0.91f;
    private float bgBushes_speedOffset = 0.87f;
    
    private float bg_x;
    private int k;
    
    //minimal distance between edge of screen and edge of background
    //it means minimal distance of background OUT of screen
    //background will be drawing minimal n meter(s) out of screen
    private int MIN_GAP = 1;
    
    public Background() {
        
        bgImg = new Texture(Gdx.files.internal("background/11_background.png"));
        bgClouds = new Texture(Gdx.files.internal("background/10_distant_clouds.png"));
        bgHugeClouds = new Texture(Gdx.files.internal("background/07_huge_clouds1.png"));
        bgHill = new Texture(Gdx.files.internal("background/05_hill2.png"));
        bgBushes = new Texture(Gdx.files.internal("background/04_bushes.png"));
        
        
    }
    
    
    public void drawBackground(Batch batch){
        x = GameScreen.camera.position.x-Constants.W_IN_M/2.0f;
        y = GameScreen.camera.position.y-Constants.H_IN_M/2.0f;
        
        //System.err.println( GameScreen.camera.position.x);
        //default background
        batch.draw(bgImg, x, y, Constants.W_IN_M, Constants.H_IN_M);
        
        //############## CLOUDS #################
        bg_x = x*bgClouds_speedOffset;
        k = (int) (x/Constants.W_IN_M - bg_x/Constants.W_IN_M);
        
        //min gap on right side
        if ((bg_x + Constants.W_IN_M*(k+2)) - (GameScreen.camera.position.x + Constants.W_IN_M/2) < MIN_GAP)
            k++;
        
        //min gap on left side
        if (x - (bg_x + Constants.W_IN_M*k) < MIN_GAP)
            batch.draw(bgClouds,    bg_x + Constants.W_IN_M*(k-1),    y+0.9f,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgClouds,        bg_x + Constants.W_IN_M*k,        y+0.9f,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgClouds,        bg_x + Constants.W_IN_M*(k+1),    y+0.9f,   Constants.W_IN_M, Constants.H_IN_M);
        
        //############## HUGE CLOUDS #################
        bg_x = x*bgHugeClouds_speedOffset;
        k = (int) (x/Constants.W_IN_M - bg_x/Constants.W_IN_M);
        
        //min gap on right side
        if ((bg_x + Constants.W_IN_M*(k+2)) - (GameScreen.camera.position.x + Constants.W_IN_M/2) < MIN_GAP)
            k++;
        
        //min gap on left side
        if (x - (bg_x + Constants.W_IN_M*k) < MIN_GAP)
            batch.draw(bgHugeClouds,    bg_x + Constants.W_IN_M*(k-1),    y+1,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHugeClouds,        bg_x + Constants.W_IN_M*k,        y+1,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHugeClouds,        bg_x + Constants.W_IN_M*(k+1),    y+1,   Constants.W_IN_M, Constants.H_IN_M);
        
        //############## HILL #################
        bg_x = x*bgHill_speedOffset;
        k = (int) (x/Constants.W_IN_M - bg_x/Constants.W_IN_M);
        
        //min gap on right side
        if ((bg_x + Constants.W_IN_M*(k+2)) - (GameScreen.camera.position.x + Constants.W_IN_M/2) < MIN_GAP)
            k++;
        
        //min gap on left side
        if (x - (bg_x + Constants.W_IN_M*k) < MIN_GAP)
            batch.draw(bgHill,    bg_x + Constants.W_IN_M*(k-1),    y+1,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHill,        bg_x + Constants.W_IN_M*k,        y+1,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHill,        bg_x + Constants.W_IN_M*(k+1),    y+1,   Constants.W_IN_M, Constants.H_IN_M);       
        

        //############## BUSHES #################
        bg_x = x*bgBushes_speedOffset;
        k = (int) (x/Constants.W_IN_M - bg_x/Constants.W_IN_M);
        
        //min gap on right side
        if ((bg_x + Constants.W_IN_M*(k+2)) - (GameScreen.camera.position.x + Constants.W_IN_M/2) < MIN_GAP)
            k++;
        
        //min gap on left side
        if (x - (bg_x + Constants.W_IN_M*k) < MIN_GAP)
            batch.draw(bgBushes,    bg_x + Constants.W_IN_M*(k-1),    y+0.7f,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgBushes,        bg_x + Constants.W_IN_M*k,        y+0.7f,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgBushes,        bg_x + Constants.W_IN_M*(k+1),    y+0.7f,   Constants.W_IN_M, Constants.H_IN_M);  
    }
}
