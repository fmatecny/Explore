/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public class Villager extends Entity{
    
    private boolean turned = false;
    
    private int speed = 2;
    private boolean fallDown = false;
    private double counterJump = 4;
    private boolean jump = false;
    private boolean isJumping = false;
       
    private long counter = 20;
    
    private int timeMove = (int )(Math.random() * 20) + 5;
    private int timeToState = (int )(Math.random() * 100) + 100;
    
    private Vector2 followPosition = null;

    public Villager(int id, float x, float y) {
        super(id, x, y, "player");    
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
        spriteBatch.draw(currentFrame, b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 11.0f/GameScreen.PPM)*2.0f, WIDTH, HEIGHT);
        
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
    
    @Override
    public void setPosition(float x, float y){
        b2body.setTransform(x, y, 0);
    }
    
    void goToPosition(Vector2 position) {
        followPosition = position;
    }
}
