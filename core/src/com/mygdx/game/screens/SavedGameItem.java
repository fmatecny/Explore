/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public class SavedGameItem extends Table{
    
    private TextButton loadBtn;
    private TextButton deleteBtn;
    private Label worldNameLbl;
    private Label playerNameLbl;
    
    
    public SavedGameItem(final String playerName, final String worldName, final MyGdxGame parent) {
        
        //this.setDebug(true);
        
        loadBtn = new TextButton("Load", Skins.skin);
        deleteBtn = new TextButton("Delete", Skins.skin);
        
        playerNameLbl = new Label(playerName, Skins.skin);
        worldNameLbl = new Label(worldName, Skins.skin);
        
        float width = loadBtn.getWidth()/2;
        float height = loadBtn.getHeight()*0.7f;
        
        this.add(playerNameLbl).width(width).height(height);
        this.add(loadBtn).width(width).height(height);
        this.row();
        this.add(worldNameLbl).width(width).height(height);
        this.add(deleteBtn).width(width).height(height);
        
    }

    public TextButton getLoadBtn() {
        return loadBtn;
    }

    public TextButton getDeleteBtn() {
        return deleteBtn;
    }
     
}
