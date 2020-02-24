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
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Fery
 */
public class ExitScreen extends ExploreMenuScreen{

    public ExitScreen(MyGdxGame myGdxGame){
        super(myGdxGame);
    }

    @Override
    public void show() {
        getStage().clear();
        Gdx.input.setInputProcessor(getStage());
        
        // temporary until we have asset manager in
        Skin skin = getSkin();
        
        
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        //table.setDebug(true);
        getStage().addActor(table);
        
        // return to main screen button
        final TextButton noButton = new TextButton("No", skin);
        noButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.MENU);

                }
        });
        
        // exit game
        final TextButton yesButton = new TextButton("Yes", skin);
        yesButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                        Gdx.app.exit();
                }
        });
        
        Label exitLabel = new Label( "Are you sure you\n want to quit Explore?", skin );            
        Label titleLabel = new Label( "Explore", skin, "title" );

        table.add(titleLabel).padBottom(30).colspan(2);
        table.row().pad(10, 0, 10, 0); 
        exitLabel.setAlignment(Align.center);
        table.add(exitLabel).colspan(2).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(noButton).width(titleLabel.getWidth()/2);
        table.add(yesButton).width(titleLabel.getWidth()/2);

    }
 
}
