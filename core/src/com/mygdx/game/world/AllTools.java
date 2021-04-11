/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class AllTools {
    
    private enum id
    {
        bow, pickaxe, sword  
    }
    
    public static Tool bow; 
    public static Tool pickaxe; 
    public static Tool sword; 
    
    public static ArrayList<Tool> toolList;

    public AllTools() {

        toolList = new ArrayList<>();
        
        bow =  new Tool();
        bow.id = id.bow.ordinal();
        bow.texture = new Texture(Gdx.files.internal("tool/bow.png"));
        toolList.add(bow);
        
        pickaxe =  new Tool();
        pickaxe.id = id.pickaxe.ordinal();
        pickaxe.texture = new Texture(Gdx.files.internal("tool/pickaxe.png"));
        toolList.add(pickaxe);
        
        sword =  new Tool();
        sword.id = id.sword.ordinal();
        sword.texture = new Texture(Gdx.files.internal("tool/sword.png"));
        toolList.add(sword);
        
    }
    
    public Tool getToolById(int id){
        if (id < 0)
            return null;
        
        for (Tool tool : toolList) 
        {
            if (tool.id == id)
                return tool; 
        }
        
        return null;
    }
    
    
}
