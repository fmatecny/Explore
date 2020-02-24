/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Player;

/**
 *
 * @author Fery
 */
public class DebugHUD {

    private BitmapFont font;
    private String dbgStr = "";
    
    public DebugHUD() {
        font = new BitmapFont();
        
    }
    
    public void draw(Player player, Batch batch, IntVector2 cam){
        createDebugInfo(player, cam);
        
        font.draw(batch, dbgStr, cam.X - MyGdxGame.width/2, cam.Y + MyGdxGame.height/2);
        dbgStr = "";
    
    }
    
    private void createDebugInfo(Player player, IntVector2 cam){
        addPlayerInfo(player, cam);
        addCamInfo(cam);
        addMouseInfo();

    }
    
    private void addPlayerInfo(Player player, IntVector2 cam){
        dbgStr += "Player position X: " + player.getX() + ", Y: " + player.getY() + "\n";
        dbgStr += "RealCoordinate X: " + ((player.getX()+70)-(cam.X-MyGdxGame.width/2)) + "\n";
    }

    private void addCamInfo(IntVector2 cam) {
        dbgStr += "CamX: " + cam.X + ", CamY: " + cam.Y + "\n";
    }

    private void addMouseInfo() {
        dbgStr += "MouseX: " + Inputs.instance.mouseX + ", MouseY: " + Inputs.instance.mouseY + "\n";
    }
    
    
}
