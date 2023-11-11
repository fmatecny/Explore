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
    
    public enum typeOfTools
    {
        //bow, 
        stoneAxe, ironAxe, diamondAxe, 
        woodPickaxe, stonePickaxe, ironPickaxe, 
        stoneSword, diamondSword  
    }
    
    public static Tool bow; 
    public static Tool stoneAxe;
    public static Tool ironAxe;
    public static Tool diamondAxe;
    public static Tool woodPickaxe;
    public static Tool stonePickaxe;
    public static Tool ironPickaxe;
    public static Tool stoneSword;
    public static Tool diamondSword;
    
    public static ArrayList<Tool> toolList;

    public AllTools() {

        toolList = new ArrayList<>();
        
        /*bow =  new Tool();
        bow.id = typeOfTools.bow.ordinal();
        bow.texture = new Texture(Gdx.files.internal("tools/bow.png"));
        toolList.add(bow);*/
        
        //-------------------------------------AXE---------------------------------------
        stoneAxe =  new Tool();
        stoneAxe.id = typeOfTools.stoneAxe.ordinal();
        stoneAxe.info = "Stone axe";
        stoneAxe.texture = new Texture(Gdx.files.internal("tools/stoneAxe/stoneAxe.png"));
        stoneAxe.damage = 2;
        toolList.add(stoneAxe);
        
        ironAxe =  new Tool();
        ironAxe.id = typeOfTools.ironAxe.ordinal();
        ironAxe.info = "Iron axe";
        ironAxe.texture = new Texture(Gdx.files.internal("tools/ironAxe/ironAxe.png"));
        ironAxe.damage = 4;
        toolList.add(ironAxe);
        
        diamondAxe =  new Tool();
        diamondAxe.id = typeOfTools.diamondAxe.ordinal();
        diamondAxe.info = "Diamond axe";
        diamondAxe.texture = new Texture(Gdx.files.internal("tools/diamondAxe/diamondAxe.png"));
        diamondAxe.damage = 6;
        toolList.add(diamondAxe);
        
        //-------------------------------------PICKAXE---------------------------------------
        woodPickaxe =  new Tool();
        woodPickaxe.id = typeOfTools.woodPickaxe.ordinal();
        woodPickaxe.info = "Wood pickaxe";
        woodPickaxe.texture = new Texture(Gdx.files.internal("tools/woodPickaxe/woodPickaxe.png"));
        woodPickaxe.damage = 2;
        toolList.add(woodPickaxe);
        
        stonePickaxe =  new Tool();
        stonePickaxe.id = typeOfTools.stonePickaxe.ordinal();
        stonePickaxe.info = "Stone pickaxe";
        stonePickaxe.texture = new Texture(Gdx.files.internal("tools/stonePickaxe/stonePickaxe.png"));
        stonePickaxe.damage = 4;
        toolList.add(stonePickaxe);
        
        ironPickaxe =  new Tool();
        ironPickaxe.id = typeOfTools.ironPickaxe.ordinal();
        ironPickaxe.info = "Iron pickaxe";
        ironPickaxe.texture = new Texture(Gdx.files.internal("tools/ironPickaxe/ironPickaxe.png"));
        ironPickaxe.damage = 6;
        toolList.add(ironPickaxe);
        
        //-------------------------------------SWORD----------------------------------------------
        stoneSword =  new Tool();
        stoneSword.id = typeOfTools.stoneSword.ordinal();
        stoneSword.info = "Stone sword";
        stoneSword.texture = new Texture(Gdx.files.internal("tools/stoneSword/stoneSword.png"));
        stoneSword.damage = 4;
        toolList.add(stoneSword);
        
        diamondSword =  new Tool();
        diamondSword.id = typeOfTools.diamondSword.ordinal();
        diamondSword.info = "Diamond sword";
        diamondSword.texture = new Texture(Gdx.files.internal("tools/diamondSword/diamondSword.png"));
        diamondSword.damage = 6;
        toolList.add(diamondSword);
        
    }
    
    public static Tool getToolById(int id){
        if (id < 0)
            return null;
        
        for (Tool tool : toolList) 
        {
            if (tool.id == id)
            {
                //System.err.println("GetToolById: Id " + id + " found");
                return tool; 
            }
        }
        System.err.println("GetToolById: Id " + id + " not found!");
        return null;
    }
    
    
}
