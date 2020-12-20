/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class LoadGameScreen extends ExploreMenuScreen{

    private ArrayList<SavedGameItem> savedGameList;
    
    
    public LoadGameScreen(MyGdxGame myGdxGame){
            super(myGdxGame);
    }

    @Override
    public void show() {
        getStage().clear();
        Gdx.input.setInputProcessor(getStage());

        Skin skin = getSkin();
        
        ScrollPane scrollPane;
        
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        //table.setDebug(true);
        getStage().addActor(table);
        
        
        Table scrolTable = new Table(skin);
        //scrolTable.setDebug(true);
        
        savedGameList = new ArrayList<>();
        
        loadSavedGameList();

        for (SavedGameItem savedGameItem : savedGameList) 
        {
            scrolTable.add(savedGameItem).fillX().uniformX();
            scrolTable.row().pad(10, 0, 10, 0);
        }

        scrollPane = new ScrollPane(scrolTable, skin);
        scrollPane.setFadeScrollBars(false);
        
        
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
        table.add(scrollPane).height(300).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().uniformX();
    }  

    private void loadSavedGameList() {
        
        FileHandle dirHandle;
        dirHandle = Gdx.files.internal("data");
        String s;
        String player;
        String world;
        int i = 0;
        
        for (FileHandle entry: dirHandle.list()) {
            
            s = entry.nameWithoutExtension();
            player = s.split(Constants.SPLIT_CHAR)[0];
            world = s.split(Constants.SPLIT_CHAR)[1];
            System.out.println(player + "|" + world);
            
            savedGameList.add(new SavedGameItem(player, world, getParent()));
            savedGameList.get(i).getBtn().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        LoadingScreen.worldID = 1;
                        getParent().changeScreen(MyGdxGame.LOADING);

                }
            });
            i++;
        }
        
        
        

    }
}
