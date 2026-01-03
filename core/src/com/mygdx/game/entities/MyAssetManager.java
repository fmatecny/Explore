/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.Constants.typeOfArmor;
import com.mygdx.game.Constants.typeOfEntity;
import com.mygdx.game.Constants.typeOfDirection;
import com.mygdx.game.Constants.typeOfMovement;
import com.mygdx.game.world.AllTools.typeOfTools;

import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class MyAssetManager {

    public static MyAssetManager instance;
   // public static AssetManager manager;
    
    
    private ArrayMap<typeOfArmor, ArrayList<ArrayList<Animation<AtlasRegion>>>> playerAnimations;
    private ArrayMap<typeOfEntity, ArrayList<ArrayList<Animation<AtlasRegion>>>> entityAnimations;
    private ArrayMap<typeOfTools, ArrayList<ArrayList<Animation<AtlasRegion>>>> toolsAnimations;
    private ArrayMap<typeOfArmor, ArrayList<ArrayList<Animation<AtlasRegion>>>> playerArmAnimations;


    public MyAssetManager() {
        /*manager = new AssetManager();
        manager.load("block/rocky.png", Texture.class);
        manager.finishLoading();*/
        
        playerAnimations = new ArrayMap<typeOfArmor, ArrayList<ArrayList<Animation<AtlasRegion>>>>(typeOfArmor.values().length); 
        for (int i = 0; i < typeOfArmor.values().length; i++)
        	playerAnimations.put(typeOfArmor.values()[i], getAnimations("entities/player/" + typeOfArmor.values()[i].name()));
        
        entityAnimations = new ArrayMap<typeOfEntity, ArrayList<ArrayList<Animation<AtlasRegion>>>>(typeOfEntity.values().length);
        for	(int i  = 0; i < typeOfEntity.values().length; i++)
        	entityAnimations.put(typeOfEntity.values()[i], getAnimations("entities/" + typeOfEntity.values()[i].name() + "/"));
        
        toolsAnimations = new ArrayMap<typeOfTools, ArrayList<ArrayList<Animation<AtlasRegion>>>>(typeOfTools.values().length);
        for	(int i  = 0; i < typeOfTools.values().length; i++)
        	toolsAnimations.put(typeOfTools.values()[i], getAnimations("tools/" + typeOfTools.values()[i].name() + "/"));  
        
        playerArmAnimations = new ArrayMap<typeOfArmor, ArrayList<ArrayList<Animation<AtlasRegion>>>>(typeOfArmor.values().length);
        for	(int i  = 0; i < typeOfArmor.values().length; i++)
        	playerArmAnimations.put(typeOfArmor.values()[i], getAnimations("entities/player/" + typeOfArmor.values()[i].name() + "/Arm/"));
    }
    
        
    private ArrayList<ArrayList<Animation<AtlasRegion>>> getAnimations(String path){
        TextureAtlas[][] textureAtlas = loadTextures(path);
        
        ArrayList<ArrayList<Animation<AtlasRegion>>> animations = new ArrayList<>(typeOfDirection.values().length);
        ArrayList<Animation<AtlasRegion>> animationsOneDir = new ArrayList<>(typeOfMovement.values().length);
        for (int i = 0; i < typeOfDirection.values().length; i++) 
        {
            for (int j = 0; j < typeOfMovement.values().length; j++) 
            {
                if (path.contains("squirrel"))
                {
                    animationsOneDir.add(j, new Animation<>(0.08f, textureAtlas[i][j].getRegions()));  
                }
                else
                {
                    if (j == typeOfMovement.Stand.ordinal())
                        animationsOneDir.add(j, new Animation<>(0.1f, textureAtlas[i][j].getRegions()));
                    else
                        animationsOneDir.add(j, new Animation<>(0.03f, textureAtlas[i][j].getRegions())); 
                }
 
            }
            animations.add(new ArrayList<Animation<AtlasRegion>>(animationsOneDir));
            animationsOneDir.clear();
        }
        animationsOneDir = null;
        
        return animations;
    }
    
    private TextureAtlas[][] loadTextures(String path){        
        TextureAtlas[][] textureAtlas = new TextureAtlas[typeOfDirection.values().length][typeOfMovement.values().length];
        String direction;
        String movement;
        for (int i = 0; i < typeOfDirection.values().length; i++) 
        {
            //direction = "/" + typeOfDirection.values()[i].name();
            
            for (int j = 0; j < typeOfMovement.values().length; j++) 
            {            
            	direction = "/" + typeOfDirection.values()[i].name();
            	
                movement = "/" + typeOfMovement.values()[j].name();
                movement += "/" + typeOfMovement.values()[j].name().toLowerCase() + ".txt";
                
                try 
                {
                    textureAtlas[i][j] = new TextureAtlas(path + direction + movement);                
                } catch (Exception e) {
                    try 
                    {
                    	//System.err.println(e + "try to find other direction");
                        if (i == 0)
                            direction = "/" + typeOfDirection.values()[i+1].name();
                        else
                            direction = "/" + typeOfDirection.values()[i-1].name();
                        
                        textureAtlas[i][j] = new TextureAtlas(path + direction + movement);

                        for (AtlasRegion region : textureAtlas[i][j].getRegions()) 
                        {
                            region.flip(true, false);
                        }

                    } catch (Exception ex) {
                        //textureAtlas[i][j] = new TextureAtlas("entities/player/Default" + direction + movement);
                        textureAtlas[i][j] = new TextureAtlas();
                        textureAtlas[i][j].addRegion("Empty", new TextureRegion(new Texture(new Pixmap(0, 0, Pixmap.Format.RGB888))));
                        //System.err.println(ex);
                    }
                }
            }
        }
        return textureAtlas;
    }    
 
    
    public ArrayList<ArrayList<Animation<AtlasRegion>>> getPlayerAnimations(typeOfArmor type){
        return playerAnimations.get(type);
    }
    
    public ArrayList<ArrayList<Animation<AtlasRegion>>> getPlayerArmAnimations(typeOfArmor type){
        return playerArmAnimations.get(type);
    }
    
    public ArrayList<ArrayList<Animation<AtlasRegion>>> getEntityAnimations(typeOfEntity type){
        return entityAnimations.get(type);
    }
    
    public ArrayList<ArrayList<Animation<AtlasRegion>>> getToolsAnimations(typeOfTools type){
        return toolsAnimations.get(type);
    }
}
