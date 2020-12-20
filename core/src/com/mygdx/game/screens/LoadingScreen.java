/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Fery
 */
public class LoadingScreen extends ExploreMenuScreen{

    float progress = 0.0f;
    boolean passed = false;
    int renderIdx = 0;
    private ProgressBar progressBar;
    
    public static int worldID = 0;
    
    public LoadingScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
    }

    @Override
    public void show() {
        getStage().clear();
        Gdx.input.setInputProcessor(getStage());

        Skin skin = getSkin();
        
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        table.setDebug(true);
        getStage().addActor(table);
        
        progressBar = new ProgressBar(0.0f, 100, .1f, false, skin, "xp");
        progressBar.setValue(0.0f);
        table.add(progressBar).width(300.0f).height(50.0f).padBottom(10.0f);
    }
    
    @Override
    public void render(float f) {
        super.render(f);
        
        
        if (renderIdx == 1){
            getParent().createGameScreen();
            progress = 30;
            System.out.println(progress); 
            getParent().getGameScreen().createMap();
            progress = 40;
            System.out.println(progress);
            if (worldID == 0)
                progress = 100;
                
        }
        if (renderIdx < 3)
            renderIdx++;
        
        if (!passed && renderIdx == 1)
        { 
             //load game
            if (worldID > 0)
            {
            new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                           while(progress < 40)
                           {
                           try {
                                   Thread.sleep(500);
                           } catch (InterruptedException e) {
                                   e.printStackTrace();
                           }
                           }
                           
                           
                           
                           getParent().getGameScreen().loadGame();
                           progress = 99;
                           System.out.println(progress);
                           try {
                                   Thread.sleep(2000);
                           } catch (InterruptedException e) {
                                   e.printStackTrace();
                           }
                        progress = 100;
                        System.out.println(progress);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {

                                
                            }
                        });

                    }
                }
            ).start();
            }
            passed = true;
        }
        
        
        
        
        //progress += 0.1;
        System.out.println(progress + "a");
        progressBar.setValue(progress);
        
        if (progress >= 100.0f){
            progress = 0;
            passed = false;
            renderIdx = 0;
            getParent().changeScreen(MyGdxGame.GAME);
        }
    }
    
}
