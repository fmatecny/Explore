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
public class LoadGameScreen extends ExploreMenuScreen{

    public LoadGameScreen(MyGdxGame myGdxGame){
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
        //table.setDebug(true);
        getStage().addActor(table);
        
        // create world
        final TextButton createButton = new TextButton("Load World", skin);
        createButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.GAME);

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
        
        Label saveLabel = new Label( "Saved games", skin, "bold" );
        Label titleLabel = new Label( "Explore", skin, "title" );

        table.add(titleLabel).padBottom(30);
        table.row().pad(10, 0, 10, 0);        
        table.add(saveLabel).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(createButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().uniformX();
    }  
}
