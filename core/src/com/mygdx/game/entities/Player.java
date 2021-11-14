/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.inventory.Inventory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;
import com.mygdx.game.Inputs;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fery
 */
public class Player {

    private Inventory inventory;
    private HUD hud;
    private float health = 100.0f;
    
    private enum typeOfMovement { stand, walk, run, jump, shot, hit, die};
    private typeOfMovement currentTOM = typeOfMovement.stand;
    private typeOfMovement lastTOM = typeOfMovement.stand;
   
    private TextureAtlas[] textureAtlas;
    private List<Animation<AtlasRegion>> animations;
    private float stateTime = 0;

    private boolean turned = false;
    
    private final float SCALE = 4f;
    private float WIDTH;
    private float HEIGHT;
    
    private float speed = 0.4f;//2/GameScreen.PPM;
    private float powerOfImpuls = 0.2f;

    private boolean isFalling = false;
    private boolean isJumping = false;
    
    public Body b2body;
    private Vector2 position =new Vector2();
    
    private Vector3 v3 = new Vector3();
    private boolean isShotFinished = true;

    public Player() {
        textureAtlas = new TextureAtlas[typeOfMovement.values().length];
        textureAtlas[typeOfMovement.stand.ordinal()] = new TextureAtlas("player/Stand/stand.txt");
        textureAtlas[typeOfMovement.walk.ordinal()] = new TextureAtlas("player/Walk/walk.txt");
        textureAtlas[typeOfMovement.run.ordinal()] = new TextureAtlas("player/Run/run.txt");
        textureAtlas[typeOfMovement.jump.ordinal()] = new TextureAtlas("player/Jump/jump.txt");
        textureAtlas[typeOfMovement.shot.ordinal()] = new TextureAtlas("player/Shot/shot.txt");
        textureAtlas[typeOfMovement.hit.ordinal()] = new TextureAtlas("player/Hit/hit.txt");
        textureAtlas[typeOfMovement.die.ordinal()] = new TextureAtlas("player/Die/die.txt");
        
        createAnimations();
        definePlayer();

        inventory = new Inventory();
        hud = new HUD();
    }

    private void createAnimations() {
        
        animations = new ArrayList<>(typeOfMovement.values().length);
        
        for (int i = 0; i < typeOfMovement.values().length; i++) 
        { 
            if (i == typeOfMovement.stand.ordinal())
                animations.add(i, new Animation<>(0.1f, textureAtlas[i].getRegions()));
            else
                animations.add(i, new Animation<>(0.03f, textureAtlas[i].getRegions()));  
        }

        float width = animations.get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).getKeyFrame(0).getRegionHeight();
        WIDTH = ((Block.size*SCALE)/height)*width;
        HEIGHT = Block.size*SCALE;
    }


    private void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(450.0f/GameScreen.PPM, 20);
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
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
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
        right.set(new Vector2(0.15f, -0.1f), new Vector2(0.23f, -0.1f));
        //right.set(new Vector2(0.23f, -0.15f), new Vector2(0.23f, 0.5f));
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
        
        if (currentTOM == typeOfMovement.die)
            return;
        
        if (b2body.getLinearVelocity().x == 0 && isShotFinished)
            currentTOM = typeOfMovement.stand;
        
        speed = 2;
        powerOfImpuls = 0.2f;
        
        if (MyContactListener.swim){
            speed /=4;
            powerOfImpuls = 0.03f;
            if (Inputs.instance.run)
                currentTOM = typeOfMovement.stand;      
        }
        
        if (Inputs.instance.run && currentTOM != typeOfMovement.stand){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }
        
        if (Inputs.instance.mouseLeft && isShotFinished)
        {
            currentTOM = typeOfMovement.shot;
            isShotFinished = false;
        }
        else if (Inputs.instance.mouseLeft == false && isShotFinished == false)
            isShotFinished = true;
        
        if (Inputs.instance.right && b2body.getLinearVelocity().x <= speed && MyContactListener.blockOnRight==0){
            b2body.applyLinearImpulse(new Vector2(powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.walk;
        }
        
        if (Inputs.instance.left && b2body.getLinearVelocity().x >= -speed && MyContactListener.blockOnLeft==0){
            b2body.applyLinearImpulse(new Vector2(-powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = typeOfMovement.walk;
        }

        /*if (Inputs.instance.up && b2body.getLinearVelocity().y <= speed/5){
            b2body.applyLinearImpulse(new Vector2(0, powerOfImpuls), b2body.getWorldCenter(), true);
        }
        else{
            //if playerOnLadder
        b2body.setGravityScale(0);
        Vector2 vel = b2body.getLinearVelocity();
        vel.y = 0f;
        b2body.setLinearVelocity(vel);
        }*/
        
        if (Inputs.instance.down && MyContactListener.swim){
            b2body.applyLinearImpulse(new Vector2(0, -powerOfImpuls), b2body.getWorldCenter(), true);
        }
        
        
        if (b2body.getLinearVelocity().y < 0 && isJumping )
            isFalling = true;
        
        if (b2body.getLinearVelocity().y == 0 && isFalling){
            isJumping = false;
            isFalling = false;
        }
        
        if (Inputs.instance.jump && !isJumping && (!isFalling || MyContactListener.swim)){
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
    
    /*private int getScaleOfMovement(){
        
        switch (currentTOM) 
        {
            case stand: return 6;
            case walk:  return 2;
            case run:  return 2;
            case jump:  return 2;
            
            default:    throw new AssertionError();
        }
    
    }*/
    
    
    public void update(OrthographicCamera camera){
        //updatePosition(camera);
        health -= 10;
        hud.setHealth(health);
        if (health <= 0)
            currentTOM = typeOfMovement.die;
    }
    
    public void draw(SpriteBatch spriteBatch){
        //hud.draw();
        if (currentTOM != typeOfMovement.jump)
        {
            //backwalk
            if ((turned && Inputs.instance.right) || (!turned && Inputs.instance.left))
                animations.get(currentTOM.ordinal()).setPlayMode(Animation.PlayMode.REVERSED );
            else
                animations.get(currentTOM.ordinal()).setPlayMode(Animation.PlayMode.NORMAL );
            
            stateTime += Gdx.graphics.getDeltaTime();
        }
        
        if (lastTOM != currentTOM)
            stateTime = 0;
        
        TextureRegion currentFrame;
        if (currentTOM == typeOfMovement.die || currentTOM == typeOfMovement.shot)
            currentFrame = animations.get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
        else
            currentFrame = animations.get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
        currentFrame.flip(currentFrame.isFlipX() != turned, false);
        spriteBatch.draw(currentFrame, b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 7.0f/GameScreen.PPM)*2.0f, WIDTH, HEIGHT);
        
        if (currentTOM == typeOfMovement.shot && animations.get(currentTOM.ordinal()).isAnimationFinished(stateTime))
            currentTOM = typeOfMovement.stand;
        
        lastTOM = currentTOM;
        
        
    }
    
    public void dispose(){
        for (int i = 0; i < typeOfMovement.values().length; i++) 
        {
            textureAtlas[i].dispose();
        }
        inventory.dispose();
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
    
    public HUD getHud() {
        return hud;
    }
    
    public boolean IsAlive() {
        return health > 0;
    }
}
