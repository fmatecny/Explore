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
    private Table scrolTable;
    
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
     
        scrolTable = new Table(skin);
        //scrolTable.setDebug(true);
        
        savedGameList = new ArrayList<>();
        loadSavedGameList();
        updateTable();

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
        dirHandle = Gdx.files.external(".explore/data");
        String s;
        int i = 0;
        
        for (FileHandle entry: dirHandle.list()) {
            
            s = entry.nameWithoutExtension();
            if (s.length() > 1)
            {
                final String player = s.split(Constants.SPLIT_CHAR)[0];
                final String world = s.split(Constants.SPLIT_CHAR)[1];
                final FileHandle file = entry;
                //System.out.println(player + "|" + world);
                savedGameList.add(new SavedGameItem(player, world, getParent()));
                
                savedGameList.get(i).getLoadBtn().addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        MyGdxGame.playerName = player;
                        MyGdxGame.worldName = world;
                        LoadingScreen.worldID = 1;
                        System.out.println("Loading game");
                        System.out.println("Player name: " + MyGdxGame.playerName);
                        System.out.println("World name: " + MyGdxGame.worldName);
                        getParent().changeScreen(MyGdxGame.LOADING);
                    }
                });
                
                savedGameList.get(i).getDeleteBtn().addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        System.out.println(player + "|delete|" + world + Gdx.files.getExternalStoragePath());

                        file.delete();
                        savedGameList.clear();
                        loadSavedGameList();
                        updateTable();
                    }
                });
                
                i++;
            }
        }
    }
    
    private void updateTable(){
        scrolTable.clear();
        for (SavedGameItem savedGameItem : savedGameList) 
        {
            scrolTable.add(savedGameItem).fillX().uniformX();
            scrolTable.row().pad(20, 0, 0, 0);
        }
    }
    
}
