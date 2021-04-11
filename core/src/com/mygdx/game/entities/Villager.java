/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Villager {
   
    private int id = 1;
    private enum typeOfMovement { stand, walk, run, jump};//, shot, hit, die };
    private typeOfMovement currentTOM = typeOfMovement.stand;
    private typeOfMovement lastTOM = typeOfMovement.stand;
   
    private TextureAtlas[] textureAtlas;
    private ArrayList<ArrayList<ArrayList<Sprite>>> sprites;
    private int idx = 0;

    private boolean turned = false;
    
    private final float SCALE = 4f;
    private final int downOffset = (int) (7.8f*SCALE);
    
    
    private int speed = 2;
    private boolean fallDown = false;
    private double counterJump = 4;
    private boolean jump = false;
    private boolean isJumping = false;
    
    
    private long counter = 170;
    
    private Body b2body;

    public Villager() {
        textureAtlas = new TextureAtlas[typeOfMovement.values().length];
        //System.out.println(typeOfMovement.stand.ordinal());
        textureAtlas[typeOfMovement.stand.ordinal()] = new TextureAtlas("player/Stand/stand.txt");
        textureAtlas[typeOfMovement.walk.ordinal()] = new TextureAtlas("player/Walk/walk.txt");
        textureAtlas[typeOfMovement.run.ordinal()] = new TextureAtlas("player/Run/run.txt");
        textureAtlas[typeOfMovement.jump.ordinal()] = new TextureAtlas("player/Jump/jump.txt");
        
        addSprites();
        defineVillager();
        
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
    public void defineVillager(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(700.0f/GameScreen.PPM, 20);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = GameScreen.world.createBody(bdef);
        b2body.setUserData(id);

        FixtureDef fdef = new FixtureDef();
        //fdef.friction = 1.0f;
        //fdef.restitution = 0.0f;
        //fdef.density = 5.0f;
        /*PolygonShape square = new PolygonShape();
        square.setAsBox(Block.size, Block.size);*/
        CircleShape square = new CircleShape();
        square.setRadius(Block.size/2.0f + 2.0f/GameScreen.PPM);
        //square.
        //fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        /*fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;*/

        fdef.shape = square;
        fdef.filter.categoryBits = Constants.VILLAGER_BIT;
        //fdef.filter.maskBits = Constants.BLOCK_BIT;
        b2body.createFixture(fdef);//.setUserData(this);
        

        /*EdgeShape head = new EdgeShape();
        head.set(new Vector2(-1, 2), new Vector2(3, 2));
        fdef.shape = head;
        fdef.isSensor = true;*/
        
        square.setRadius(Block.size/2.0f + 6.0f/GameScreen.PPM);
        square.setPosition(new Vector2(0, (Block.size/2.0f + 1.0f/GameScreen.PPM)*2.0f));
        b2body.createFixture(fdef);
        /*
        fdef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;*/

        //b2body.createFixture(fdef);//.setUserData(this);
        square.dispose();
    }

    public void updatePosition(){
        if (b2body.getLinearVelocity().x == 0)
            currentTOM = typeOfMovement.stand;
        
        speed = 2;
        /*if (Inputs.instance.run){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }*/

        if (doRight() && b2body.getLinearVelocity().x <= speed){
            b2body.applyLinearImpulse(new Vector2(0.2f, 0), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.walk;
            turned = false;
        }
        
        if (doLeft() && b2body.getLinearVelocity().x >= -speed){
            b2body.applyLinearImpulse(new Vector2(-0.2f, 0), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.walk;
            turned = true;
        }
        
        /*if (b2body.getLinearVelocity().y < 0 && isJumping )
            isFalling = true;
        
        if (b2body.getLinearVelocity().y == 0 && isFalling){
            isJumping = false;
            isFalling = false;
        }
        
        if (Inputs.instance.up && !isJumping && !isFalling){
            b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.jump;
            isJumping = true;
        }

        if (Inputs.instance.run){
            //speed *= 2;
            currentTOM = typeOfMovement.run;
        }*/
            
                

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
    
    
    public void draw(SpriteBatch spriteBatch){
        
        counter++;
        
        if (idx <= 0)
            idx = sprites.get(currentTOM.ordinal()).get(0).size()*getScaleOfMovement()-1;
        else if (idx >= sprites.get(currentTOM.ordinal()).get(0).size()*getScaleOfMovement() || lastTOM != currentTOM)
            idx = 0;
        
        Sprite sprite;
        if (turned){
            sprite = sprites.get(currentTOM.ordinal()).get(1).get(idx/getScaleOfMovement());           
        }
        else {sprite = sprites.get(currentTOM.ordinal()).get(0).get(idx/getScaleOfMovement());}
        
        sprite.setPosition(b2body.getPosition().x - Block.size*2, b2body.getPosition().y-(Block.size/2.0f + 7.0f/GameScreen.PPM)*2.0f);

        sprite.draw(spriteBatch);
        if (currentTOM != typeOfMovement.jump)
        {
            //backwalk
            if ((turned && doRight()) || (!turned && doLeft()))
                idx--;
            else
                idx++;
        }
        
        lastTOM = currentTOM;
    
    }
    
    public void dispose(){
        for (int i = 0; i < typeOfMovement.values().length; i++) {
            textureAtlas[i].dispose();
            sprites.get(i).get(0).clear();
            sprites.get(i).get(1).clear();
        }
    }
    
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
    }

    public int getSpeed() {
        return speed;
    }  
    
    
    private boolean doLeft(){
        
        if (counter < 180)
            return true;
    
        return false;
    }
    
    
    private boolean doRight(){
        
        if (counter > 800)
            counter = 0;
        
        if (counter > 480){
            return false;
        }
        
        if (counter > 300)
            return true;
        

            
        
        return false;
    }
            
    
}
