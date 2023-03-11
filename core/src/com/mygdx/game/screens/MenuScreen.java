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
import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Fery
 */
public class MenuScreen extends ExploreMenuScreen{
    
    public MenuScreen(MyGdxGame myGdxGame){
        super(myGdxGame);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        //table.setDebug(true);
        getStage().addActor(table);
        
        Skin skin = getSkin();
        
        TextButton newGame = new TextButton("New Game", skin);
        TextButton loadGame = new TextButton("Load Game", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);
        
        Label titleLabel = new Label( "Explore", skin, "title" );

        table.add(titleLabel).padBottom(60);
        table.row().pad(10, 0, 10, 0);
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(loadGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferences).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exit).fillX().uniformX().padBottom(30);

        Gdx.input.setInputProcessor(getStage());
        
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getParent().changeScreen(MyGdxGame.NEWGAME);
            }
        });
        
        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getParent().changeScreen(MyGdxGame.LOADGAME);
            }
        });
        
        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getParent().changeScreen(MyGdxGame.SETTINGS);
            }
        });
        
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getParent().changeScreen(MyGdxGame.EXIT);
            }
        });
    } 
}
