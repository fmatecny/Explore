/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public class SavedGameItem extends Table{
    
    private TextButton btn;
    private Label worldNameLbl;
    private Label playerNameLbl;
    
    
    public SavedGameItem(final String playerName, final String worldName, final MyGdxGame parent) {
        
        this.setDebug(true);
        
        btn = new TextButton("Load World", Skins.skin);
        btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        MyGdxGame.playerName = playerName;
                        MyGdxGame.worldName = worldName;
                        System.out.println(MyGdxGame.playerName + "|"+ MyGdxGame.worldName);
                        parent.changeScreen(MyGdxGame.LOADING);

                }
            });
        playerNameLbl = new Label(playerName, Skins.skin);
        worldNameLbl = new Label(worldName, Skins.skin);
        
        
        Table tableLbl = new Table();
        tableLbl.add(playerNameLbl);
        tableLbl.row();
        tableLbl.add(worldNameLbl);
        
        this.add(tableLbl).width(372/2);
        this.add(btn).width(372/2);
        
        
    }

    public TextButton getBtn() {
        return btn;
    }

    
    
    
}
