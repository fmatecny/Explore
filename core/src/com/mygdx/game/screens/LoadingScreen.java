/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Label progressLabel;
    private String progressString;
    
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
        table.center();
        table.left();
        table.padLeft(100);
        table.setDebug(true);
        getStage().addActor(table);
        
        progressBar = new ProgressBar(0.0f, 100, .1f, false, skin, "xp");
        progressBar.setValue(0.0f);
        table.add(progressBar).width(400).height(50);
        table.row().padTop(20);
        progressLabel = new Label( "", skin );
        table.add(progressLabel);
    }
    
    @Override
    public void render(float f) {
        super.render(f);
        /*try {
                Thread.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }*/
        
        
        
        switch (renderIdx) {
            case 0: progress = 10;
                    progressString = "Loading...";
                    break;
            
            case 2: getParent().createGameScreen();
                    progress = 30;
                    progressString = "Creating blocks";
                    break;
                    
            case 4: getParent().getGameScreen().createAllBlocks();
                    progress = 50;
                    progressString = "Creating game screen";
                    break;
                    
            case 6: getParent().getGameScreen().createAllItems();
                    progress = 60;
                    break;
                    
            case 8: getParent().getGameScreen().createEntities();
                    progress = 70;
                    break;     
                    
            case 10: getParent().getGameScreen().createMap();
                    progress = 80;
                    break;
                    
            case 12: if (worldID == 0)
                        progress = 99;
                    break;
                    
            case 14: if (worldID > 0){
                        getParent().getGameScreen().loadGame();
                        progress = 99;
                    }
                    break;  
                    
            case 16: progress = 100;
                    break; 
                    
                    
            default:
                break;
        }
       
        progressLabel.setText(progressString);
        
        if (progress < 100)
            renderIdx++;
        

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
