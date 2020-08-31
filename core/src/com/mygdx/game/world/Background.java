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
        
        //System.err.println(x*0.87 + "aa" + GameScreen.camera.position.x);
        
        batch.draw(bgImg, x, y, Constants.W_IN_M, Constants.H_IN_M);
        
        batch.draw(bgClouds,        x*0.97f,                  y+0.9f,    Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHugeClouds,    x*0.95f,                  y+1,       Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHill,          x*0.91f,                  y+1,       Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgBushes,        x*0.87f,                  y+0.7f,    Constants.W_IN_M, Constants.H_IN_M);
        
        //TODO 
        batch.draw(bgClouds,        x*0.97f+Constants.W_IN_M, y+0.9f,   Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHugeClouds,    x*0.95f+Constants.W_IN_M, y+1,      Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgHill,          x*0.91f+Constants.W_IN_M, y+1,      Constants.W_IN_M, Constants.H_IN_M);
        batch.draw(bgBushes,        x*0.87f+Constants.W_IN_M, y+0.7f,   Constants.W_IN_M, Constants.H_IN_M);    
    }
    
    
    

}
