/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Inputs;
import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Fery
 */
public class PauseScreen extends ExploreMenuScreen{

    public PauseScreen(MyGdxGame myGdxGame){
            super(myGdxGame);
    }
    
    
    @Override
    public void show() {
        getStage().clear();
        Gdx.input.setInputProcessor(getStage());
        Inputs.instance.pause = false;
        
        // Create a table that fills the screen. Everything else will go inside
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        //table.setDebug(true);
        getStage().addActor(table);

        // temporary until we have asset manager in
        Skin skin = getSkin();
        
        // return back to menu
        final TextButton backToGameButton = new TextButton("Back To Game", skin);
        backToGameButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.GAME);
                }
        });
        
        // preferences
        final TextButton preferencesButton = new TextButton("Preferences", skin);
        preferencesButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.SETTINGS);
                }
        });
        
        // return back to menu
        final TextButton backButton = new TextButton("Back To Menu", skin);
        backButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.MENU);
                }
        });

        Label titleLabel = new Label( "Explore", skin, "title" );
        Label pauseLabel = new Label( "Pause", skin, "bold" );

        table.add(titleLabel).padBottom(30);
        table.row().pad(10, 0, 0, 0);
        table.add(pauseLabel).fillX().uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(backToGameButton).fillX().uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(preferencesButton).fillX().uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(backButton).fillX().uniformX();
    }
    
}
