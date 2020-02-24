/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.mygdx.game.inventory.Inventory;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Map;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Player {

    private Inventory inventory;
    private HUD hud;
    
    private enum typeOfMovement { stand, walk, run, jump};//, shot, hit, die };
    private typeOfMovement currentTOM = typeOfMovement.stand;
    private typeOfMovement lastTOM = typeOfMovement.stand;
   
    private TextureAtlas[] textureAtlas;
    private ArrayList<ArrayList<ArrayList<Sprite>>> sprites;
    private int idx = 0;

    private boolean turned = false;
    private Vector2 position;
    
    private final float SCALE = 4f;
    private final int LDoffsetX = 14*(int)SCALE;
    private final int RDoffsetX = 25*(int)SCALE;
    private final int downOffset = (int) (7.8f*SCALE);
    
    
    private int speed = 2;
    private boolean fallDown = false;
    private double counterJump = 4;
    private boolean jump = false;
    private boolean isJumping = false;

    public Player(Stage stage) {
        textureAtlas = new TextureAtlas[typeOfMovement.values().length];
        //System.out.println(typeOfMovement.stand.ordinal());
        textureAtlas[typeOfMovement.stand.ordinal()] = new TextureAtlas("player/Stand/stand.txt");
        textureAtlas[typeOfMovement.walk.ordinal()] = new TextureAtlas("player/Walk/walk.txt");
        textureAtlas[typeOfMovement.run.ordinal()] = new TextureAtlas("player/Run/run.txt");
        textureAtlas[typeOfMovement.jump.ordinal()] = new TextureAtlas("player/Jump/jump.txt");
        
        addSprites();
        position =  new Vector2(450, MyGdxGame.height);
        
        inventory = new Inventory(stage);
        hud = new HUD();
        
    }

    private void addSprites() {
        
        sprites = new ArrayList<ArrayList<ArrayList<Sprite>>>();
        
        for (int i = 0; i < typeOfMovement.values().length; i++) 
        { 
            ArrayList<ArrayList<Sprite>> spriteArrArr = new ArrayList<ArrayList<Sprite>>();
            Array<AtlasRegion> regions = textureAtlas[i].getRegions();
            ArrayList<Sprite> spriteArr = new ArrayList<Sprite>();
            ArrayList<Sprite> spriteArrTurned = new ArrayList<Sprite>();
            
            for (AtlasRegion region : regions) 
            {
                Sprite sprite = textureAtlas[i].createSprite(region.name);

                float width = sprite.getWidth();
                float height = sprite.getHeight();

                sprite.setSize(((Block.size*SCALE)/height)*width, Block.size*SCALE);
                sprite.setOrigin(0, 0);

                spriteArr.add(sprite);

                Sprite spriteTurned = new Sprite(sprite);
                spriteTurned.flip(true, false);
                spriteArrTurned.add(spriteTurned);

            }
            spriteArrArr.add(spriteArr);
            spriteArrArr.add(spriteArrTurned);
            sprites.add(spriteArrArr);
        }

        
    }
    
    public void updatePosition(Map map, IntVector2 cam){
        currentTOM = typeOfMovement.stand;
        speed = 2;
       if (Inputs.instance.run){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }
        
        if ((isJumping && counterJump > 5) || (!isJumping))
            checkGround(map);
        


        if (Inputs.instance.left){
            checkLeftCollission(map);
            //turned = true;
            //position.x -= speed;
            currentTOM = typeOfMovement.walk;
        }
        
        if (Inputs.instance.right){
            checkRightCollission(map);
            //turned = false;
            //position.x += speed;
            currentTOM = typeOfMovement.walk;
        }

        if (Inputs.instance.run){
            //speed *= 2;
            currentTOM = typeOfMovement.run;
        }

        if (Inputs.instance.up && !isJumping){
            jump = true;
            isJumping = true;
        }
        
        if (isJumping)
            currentTOM = typeOfMovement.jump;
        
        if (jump){
            if (counterJump < 6.8){
                counterJump += 0.1;}

            if (counterJump >= 6.8)
                jump = false;

            position.y = position.y - (int) ((Math.sin(counterJump) + Math.cos(counterJump)) * 6);
            //System.out.println(counterJump + "aaa" + position.y);
        }
        else if(!fallDown){
            counterJump = 4;
            isJumping = false;
            //System.out.println( "1 " + position.y);
            if (position.y%Block.size > Block.size/2)
                position.y = position.y + Block.size - position.y%Block.size;
            else{
                position.y = position.y - position.y%Block.size;
            }
            //System.out.println("2 "+ position.y + "\n");
        }
        
        if (Inputs.instance.mouseX < position.x+70-(cam.X-MyGdxGame.width/2))
            turned = true;
        
        if (Inputs.instance.mouseX > position.x+89-(cam.X-MyGdxGame.width/2))
            turned = false;
            
                

    }
    
    private void checkGround(Map map){
        fallDown = false;
        boolean RD = false;
        boolean LD = false;
        if (map.getBlock((int) position.x+RDoffsetX, (int) position.y-speed) == null){
            RD = true;
        }
        else if (map.getBlock((int) position.x+RDoffsetX, (int) position.y-speed).blocked == false)
        {
            RD = true;
        }
   
        if (map.getBlock((int) position.x+LDoffsetX, (int) position.y-speed) == null){
            LD = true;
        }
        else if (map.getBlock((int) position.x+LDoffsetX, (int) position.y-speed).blocked == false)
        {
            LD = true;
        }
        
        
        if (RD && LD){
            fallDown = true;
            if (!jump) position.y-=4;
        }
        else{
            jump = false;
        } 
    }
    
    private void checkRightCollission(Map map){
        if (map.getBlock((int) position.x+RDoffsetX+speed, (int) position.y) == null){
            position.x+=speed;
        }
        else if (map.getBlock((int) position.x+RDoffsetX+speed, (int) position.y).blocked == false)
        {
            position.x+=speed;
        }
    }
    
    private void checkLeftCollission(Map map){
        if (map.getBlock((int) position.x+LDoffsetX+speed, (int) position.y) == null){
            position.x-=speed;
        }
        else if (map.getBlock((int) position.x+LDoffsetX+speed, (int) position.y).blocked == false)
        {
            position.x-=speed;
        }
    }
    
    private int getScaleOfMovement(){
        
        switch (currentTOM) 
        {
            case stand: return 6;
            case walk:  return 2;
            case run:  return 2;
            case jump:  return 2;
            
            default:    throw new AssertionError();
        }
    
    }
    
    
    public void draw(Stage stage){
        
        if (idx <= 0)
            idx = sprites.get(currentTOM.ordinal()).get(0).size()*getScaleOfMovement()-1;
        else if (idx >= sprites.get(currentTOM.ordinal()).get(0).size()*getScaleOfMovement() || lastTOM != currentTOM)
            idx = 0;
        
        Sprite sprite;
        if (turned){
            sprite = sprites.get(currentTOM.ordinal()).get(1).get(idx/getScaleOfMovement());           
        }
        else {sprite = sprites.get(currentTOM.ordinal()).get(0).get(idx/getScaleOfMovement());}
        
        sprite.setPosition(position.x, position.y-downOffset);

        sprite.draw(stage.getBatch());
        if (currentTOM != typeOfMovement.jump)
        {
            //backwalk
            if ((turned && Inputs.instance.right) || (!turned && Inputs.instance.left))
                idx--;
            else
                idx++;
        }
        
        lastTOM = currentTOM;
        
        inventory.draw(stage);
    
    }
    
    public void dispose(){
        for (int i = 0; i < typeOfMovement.values().length; i++) {
            textureAtlas[i].dispose();
            sprites.get(i).get(0).clear();
            sprites.get(i).get(1).clear();
        }
    }
    
    
    public int getX(){
        return (int) position.x;
    }
    
    public int getY(){
        return (int) position.y;
    }

    public int getSpeed() {
        return speed;
    }

    public Inventory getInventory() {
        return inventory;
    }
    


}
