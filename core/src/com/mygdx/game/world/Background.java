/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.MyGdxGame;
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
    
    public Background() {
        
        bgImg = new Texture(Gdx.files.internal("background/11_background.png"));
        bgClouds = new Texture(Gdx.files.internal("background/10_distant_clouds.png"));
        bgHugeClouds = new Texture(Gdx.files.internal("background/07_huge_clouds.png"));
        bgHill = new Texture(Gdx.files.internal("background/05_hill1.png"));
        bgBushes = new Texture(Gdx.files.internal("background/04_bushes.png"));
        
        
    }
    
    
    public void drawBackground(Batch batch){
        batch.draw(bgImg, GameScreen.camera.position.x-MyGdxGame.width/2, GameScreen.camera.position.y-MyGdxGame.height/2+0, MyGdxGame.width, MyGdxGame.height);
        batch.draw(bgClouds, GameScreen.camera.position.x-MyGdxGame.width/2, GameScreen.camera.position.y-MyGdxGame.height/2+90, MyGdxGame.width, MyGdxGame.height);
        batch.draw(bgHugeClouds, GameScreen.camera.position.x-MyGdxGame.width/2, GameScreen.camera.position.y-MyGdxGame.height/2+100, MyGdxGame.width, MyGdxGame.height);
        batch.draw(bgHill, GameScreen.camera.position.x-MyGdxGame.width/2, GameScreen.camera.position.y-MyGdxGame.height/2+100, MyGdxGame.width, MyGdxGame.height);
        batch.draw(bgBushes, GameScreen.camera.position.x-MyGdxGame.width/2, GameScreen.camera.position.y-MyGdxGame.height/2+70, MyGdxGame.width, MyGdxGame.height);
    
    }
    
    
    

}
