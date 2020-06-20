/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.inventory.Inventory;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Inputs;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.GameScreen;
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
    
    private final float SCALE = 4f;    
    
    private float speed = 0.4f;//2/GameScreen.PPM;
    private float powerOfImpuls = 0.2f;

    private boolean isFalling = false;
    private boolean isJumping = false;
    
    public Body b2body;
    
    private Vector3 v3 = new Vector3();

    public Player(Stage stage, SpriteBatch spriteBatch) {
        textureAtlas = new TextureAtlas[typeOfMovement.values().length];
        //System.out.println(typeOfMovement.stand.ordinal());
        textureAtlas[typeOfMovement.stand.ordinal()] = new TextureAtlas("player/Stand/stand.txt");
        textureAtlas[typeOfMovement.walk.ordinal()] = new TextureAtlas("player/Walk/walk.txt");
        textureAtlas[typeOfMovement.run.ordinal()] = new TextureAtlas("player/Run/run.txt");
        textureAtlas[typeOfMovement.jump.ordinal()] = new TextureAtlas("player/Jump/jump.txt");
        
        addSprites();
        definePlayer();
        inventory = new Inventory(spriteBatch);
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


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(250.0f/GameScreen.PPM, 40);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = GameScreen.world.createBody(bdef);
        b2body.setFixedRotation(true);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 5.0f;
        //fdef.restitution = 0.0f;
        fdef.density = 0.5f;
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
        b2body.createFixture(fdef);//.setUserData(this);
        
        square.setRadius(Block.size/2.0f + 4.0f/GameScreen.PPM);
        square.setPosition(new Vector2(0, (Block.size/2.0f + 1.0f/GameScreen.PPM)*2.0f));
        b2body.createFixture(fdef);
        
        EdgeShape right = new EdgeShape();
        //right.set(new Vector2(0.15f, -0.1f), new Vector2(0.23f, -0.1f));
        right.set(new Vector2(0.23f, -0.15f), new Vector2(0.23f, 0.5f));
        fdef.filter.categoryBits = Constants.PLAYER_RIGHT_BIT;
        fdef.shape = right;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        
        EdgeShape left = new EdgeShape();
        left.set(new Vector2(-0.15f, -0.1f), new Vector2(-0.23f, -0.1f));
        fdef.filter.categoryBits = Constants.PLAYER_LEFT_BIT;
        fdef.shape = left;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        
        square.dispose();
        right.dispose();
        left.dispose();
    }

    
    public void updatePosition(OrthographicCamera camera){
        
        if (b2body.getLinearVelocity().x == 0)
            currentTOM = typeOfMovement.stand;
        
        speed = 2;
        powerOfImpuls = 0.2f;
        
        if (MyContactListener.swim){
            speed /=4;
            powerOfImpuls = 0.05f;
        }
        
        if (Inputs.instance.run && currentTOM != typeOfMovement.stand){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }

        if (Inputs.instance.right && b2body.getLinearVelocity().x <= speed && MyContactListener.blockOnRight==0){
            b2body.applyLinearImpulse(new Vector2(powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.walk;
        }
        
        if (Inputs.instance.left && b2body.getLinearVelocity().x >= -speed && MyContactListener.blockOnLeft==0){
            b2body.applyLinearImpulse(new Vector2(-powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.walk;
        }
        
        if (b2body.getLinearVelocity().y < 0 && isJumping )
            isFalling = true;
        
        if (b2body.getLinearVelocity().y == 0 && isFalling){
            isJumping = false;
            isFalling = false;
        }
        
        if (Inputs.instance.up && !isJumping && !isFalling){
            b2body.applyLinearImpulse(new Vector2(0, 0.6f), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.jump;
            isJumping = true;
        }

        if (Inputs.instance.run && currentTOM != typeOfMovement.stand){
            //speed *= 2;
            currentTOM = typeOfMovement.run;
        }
        
        camera.unproject(v3.set(Inputs.instance.mouseX, Inputs.instance.mouseY, 0f));
        
        //System.out.println(v3.x + "|" + b2body.getPosition().x);
        if (v3.x < b2body.getPosition().x)
            turned = true;
        
        if (v3.x > b2body.getPosition().x)
            turned = false;
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
        
        if (idx <= 0)
            idx = sprites.get(currentTOM.ordinal()).get(0).size()*getScaleOfMovement()-1;
        else if (idx >= sprites.get(currentTOM.ordinal()).get(0).size()*getScaleOfMovement() || lastTOM != currentTOM)
            idx = 0;
        
        Sprite sprite;
        if (turned){
            sprite = sprites.get(currentTOM.ordinal()).get(1).get(idx/getScaleOfMovement());           
        }
        else {sprite = sprites.get(currentTOM.ordinal()).get(0).get(idx/getScaleOfMovement());}
        
        
        sprite.setPosition(b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 7.0f/GameScreen.PPM)*2.0f);

        sprite.draw(spriteBatch);
        if (currentTOM != typeOfMovement.jump)
        {
            //backwalk
            if ((turned && Inputs.instance.right) || (!turned && Inputs.instance.left))
                idx--;
            else
                idx++;
        }
        
        lastTOM = currentTOM;
        
        
        //System.out.println(b2body.getLinearVelocity().y);
        //inventory.draw();
    
    }
    
    public void dispose(){
        for (int i = 0; i < typeOfMovement.values().length; i++) 
        {
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

    public float getSpeed() {
        return speed;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
