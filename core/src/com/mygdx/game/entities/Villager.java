/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fery
 */
public class Villager extends Entity{
   
    //private int id = 1;
    private float WIDTH;
    private float HEIGHT;
    
    private enum typeOfMovement { stand, walk, run, jump, shot, hit, die};
    private typeOfMovement currentTOM = typeOfMovement.stand;
    private typeOfMovement lastTOM = typeOfMovement.stand;
   
    private TextureAtlas[] textureAtlas;
    private List<Animation<AtlasRegion>> animations;
    private float stateTime = 0;

    private boolean turned = false;
    
    private final float SCALE = 4f;
  
    private int speed = 2;
    private boolean fallDown = false;
    private double counterJump = 4;
    private boolean jump = false;
    private boolean isJumping = false;
       
    private long counter = 20;
    
    private Body b2body;
    
    private int timeMove = (int )(Math.random() * 20) + 5;
    private int timeToState = (int )(Math.random() * 100) + 100;
    
    private Vector2 followPosition = null;

    public Villager(int id) {
        super(id);
        
        textureAtlas = new TextureAtlas[typeOfMovement.values().length];
        textureAtlas[typeOfMovement.stand.ordinal()] = new TextureAtlas("player/Stand/stand.txt");
        textureAtlas[typeOfMovement.walk.ordinal()] = new TextureAtlas("player/Walk/walk.txt");
        textureAtlas[typeOfMovement.run.ordinal()] = new TextureAtlas("player/Run/run.txt");
        textureAtlas[typeOfMovement.jump.ordinal()] = new TextureAtlas("player/Jump/jump.txt");
        textureAtlas[typeOfMovement.shot.ordinal()] = new TextureAtlas("player/Shot/shot.txt");
        textureAtlas[typeOfMovement.hit.ordinal()] = new TextureAtlas("player/Hit/hit.txt");
        textureAtlas[typeOfMovement.die.ordinal()] = new TextureAtlas("player/Die/die.txt");
        
        createAnimations();
        defineBody(700.0f/GameScreen.PPM, 20);      
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
 
    @Override
    public void defineBody(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
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
        fdef.filter.maskBits = Constants.BLOCK_BIT;
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

    @Override
    public void updatePosition(){
        if (currentTOM == typeOfMovement.die)
            return;
        
        if (b2body.getLinearVelocity().x == 0)
            currentTOM = typeOfMovement.stand;
        
        speed = 2;
        /*if (Inputs.instance.run){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }*/

        if (followPosition != null)
        {
            if (followPosition.x-Block.size > b2body.getPosition().x && b2body.getLinearVelocity().x <= speed){
                b2body.applyLinearImpulse(new Vector2(0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = typeOfMovement.walk;
                turned = false;
            }

            if (followPosition.x+Block.size < b2body.getPosition().x && b2body.getLinearVelocity().x >= -speed){
                b2body.applyLinearImpulse(new Vector2(-0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = typeOfMovement.walk;
                turned = true;
            }
        }
        else
        {
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

    @Override
    public void draw(SpriteBatch spriteBatch){
        
        if (health <= 0)
            currentTOM = typeOfMovement.die;
        else
            counter++;
        
        if (currentTOM != typeOfMovement.jump)
        {
            //backwalk
            /*if ((turned && Inputs.instance.right) || (!turned && Inputs.instance.left))
                animations.get(currentTOM.ordinal()).setPlayMode(Animation.PlayMode.REVERSED );
            else*/
                animations.get(currentTOM.ordinal()).setPlayMode(Animation.PlayMode.NORMAL );
            
            stateTime += Gdx.graphics.getDeltaTime();
        }
        
        if (lastTOM != currentTOM)
            stateTime = 0;
        
        TextureRegion currentFrame;
        if (currentTOM == typeOfMovement.die)
            currentFrame = animations.get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
        else
            currentFrame = animations.get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
        currentFrame.flip(currentFrame.isFlipX() != turned, false);
        spriteBatch.draw(currentFrame, b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 7.0f/GameScreen.PPM)*2.0f, WIDTH, HEIGHT);
        
        lastTOM = currentTOM;
    
    }
    
    @Override
    public void dispose(){
        for (int i = 0; i < typeOfMovement.values().length; i++) 
        {
            textureAtlas[i].dispose();
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
        return counter < timeMove;
    }
    
    
    private boolean doRight(){
        
        if (counter > 2*timeMove + 2*timeToState)
            counter = 0;
        
        if (counter > 2*timeMove + timeToState)
            return false;
        
        if (counter > timeMove + timeToState)
            return true;

        return false;
    }
    
    public void setPosition(float x, float y){
        b2body.setTransform(x, y, 0);
    }
    
    void goToPosition(Vector2 position) {
        followPosition = position;
    }
}
