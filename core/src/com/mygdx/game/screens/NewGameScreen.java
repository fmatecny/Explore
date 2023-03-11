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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Fery
 */
public class NewGameScreen extends ExploreMenuScreen{

    private TextField nameField;
    private TextField worldField;

    public NewGameScreen(MyGdxGame myGdxGame){
        super(myGdxGame);
    }
    
    @Override
    public void show() {
        getStage().clear();
        Gdx.input.setInputProcessor(getStage());
        
        // temporary until we have asset manager in
        Skin skin = getSkin();

        // create world
        final TextButton createButton = new TextButton("Create World", skin);
        createButton.setDisabled(true);
        createButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    System.out.println(nameField.getText());
                    System.out.println(worldField.getText());
                    MyGdxGame.playerName = nameField.getText();
                    MyGdxGame.worldName = worldField.getText();
                    LoadingScreen.worldID = 0;
                    getParent().changeScreen(MyGdxGame.LOADING);
            }
        });
        
        nameField = new TextField("", skin);
        worldField = new TextField("", skin);
        
        nameField.setTextFieldFilter(new TextFieldFilter() {
            // Accepts all Characters except '_' 
            @Override
            public  boolean acceptChar(TextField textField, char c) {
                    return c != '_';
                }
        });
        nameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                if ( nameField.getText().length() > 0 &&
                    worldField.getText().length() > 0)
                    createButton.setDisabled(false);
                else
                    createButton.setDisabled(true);
            }
        });
        
        
        worldField.setTextFieldFilter(new TextFieldFilter() {
            // Accepts all Characters except '_' 
            @Override
            public  boolean acceptChar(TextField textField, char c) {
                    return c != '_';
                }
        });

        worldField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                if ( nameField.getText().length() > 0 &&
                    worldField.getText().length() > 0)
                    createButton.setDisabled(false);
                else
                    createButton.setDisabled(true);
            }
        });
        
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        //table.setDebug(true);
        getStage().addActor(table);
        

        
        // return back to menu
        final TextButton backButton = new TextButton("Back To Menu", skin);
        backButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.MENU);
                }
        });
        
        Label newGameLabel = new Label( "New Game", skin, "bold" );
        Label nameLabel = new Label( "Name:", skin );
        Label worldLabel = new Label( "World:", skin );
        Label titleLabel = new Label( "Explore", skin, "title" );

        table.add(titleLabel).padBottom(30).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(newGameLabel).colspan(2).fillX().uniformX();
        table.row().pad(20,0,10,0);
        table.add(nameLabel).fillX().uniformX();
        table.add(nameField).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(worldLabel).fillX().uniformX();
        table.add(worldField).fillX().uniformX();
        table.row().pad(30,0,10,0);
        table.add(createButton).colspan(2).fillX().uniformX();
        table.row().pad(30,0,10,0);
        table.add(backButton).colspan(2).fillX().uniformX();
        
    }
  
}
