/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Fery
 */
public class MyAssetManager {

    public static MyAssetManager instance;
    public static AssetManager manager;
    
    
    private ArrayMap<typeOfArmor, ArrayList> playerAnimations;
    private ArrayMap villagerAnimations;
    private ArrayMap goblinAnimations;
    
    private enum typeOfDirection { Left, Right };
    private enum typeOfMovement { Stand, Walk, Run, Jump, Slash, Hit, Die };
    private enum typeOfArmor { basic, leather, iron, diamond };
    private enum typeOfEntity{ player, vilager, smith, golem };
    
    protected TextureAtlas[][] textureAtlas;

    public MyAssetManager() {
        manager = new AssetManager();
        manager.load("block/rocky.png", Texture.class);
        manager.finishLoading();
        
        playerAnimations = new ArrayMap(typeOfArmor.values().length);
        playerAnimations.put(typeOfArmor.basic, getAnimations("/player/basic"));
        /*playerAnimations.put(typeOfArmor.basic, getAnimations("/player/basic"));
        playerAnimations.put(typeOfArmor.basic, getAnimations("/player/basic"));
        playerAnimations.put(typeOfArmor.basic, getAnimations("/player/basic"));*/

    }
    
        
    private ArrayList getAnimations(String path){
        loadTextures(path);
        
        ArrayList animations = new ArrayList<>(typeOfDirection.values().length);
        ArrayList animationsOneDir = new ArrayList<>(typeOfMovement.values().length);
        for (int i = 0; i < typeOfDirection.values().length; i++) 
        {
            for (int j = 0; j < typeOfMovement.values().length; j++) 
            { 
                if (j == typeOfMovement.Stand.ordinal())
                    animationsOneDir.add(j, new Animation<>(0.1f, textureAtlas[i][j].getRegions()));
                else
                    animationsOneDir.add(j, new Animation<>(0.03f, textureAtlas[i][j].getRegions()));  
            }
            animations.add(new ArrayList<Animation<AtlasRegion>>(animationsOneDir));
            animationsOneDir.clear();
        }
        animationsOneDir = null;
        
        return animations;
    }
    
    
    private void loadTextures(String path){        
        textureAtlas = new TextureAtlas[typeOfDirection.values().length][typeOfMovement.values().length];
        String direction;
        String movement;
        for (int i = 0; i < typeOfDirection.values().length; i++) 
        {
            direction = "/" + typeOfDirection.values()[i].name();
            
            for (int j = 0; j < typeOfMovement.values().length; j++) 
            {            
                movement = "/" + typeOfMovement.values()[j].name();
                movement += "/" + typeOfMovement.values()[j].name().toLowerCase() + ".txt";
                
                try 
                {
                    textureAtlas[i][j] = new TextureAtlas("entities" + path + direction + movement);                
                } catch (Exception e) {
                    try 
                    {
                        if (i == 0)
                            direction = "/" + typeOfDirection.values()[i+1].name();
                        else
                            direction = "/" + typeOfDirection.values()[i-1].name();
                        
                        textureAtlas[i][j] = new TextureAtlas("entities" + path + direction + movement);

                        for (AtlasRegion region : textureAtlas[i][j].getRegions()) 
                        {
                            region.flip(true, false);
                        }

                    } catch (Exception ex) {
                        //textureAtlas[i][j] = new TextureAtlas("entities/player/Default" + direction + movement);
                        textureAtlas[i][j] = new TextureAtlas();
                        textureAtlas[i][j].addRegion("Empty", new TextureRegion(new Texture(new Pixmap(0, 0, Pixmap.Format.RGB888))));
                        System.err.println(ex);
                    }
                }
            }
        }
    }
 
    
    public ArrayList getPlayerAnimations(typeOfArmor type){
        return playerAnimations.get(type);
    }
}
