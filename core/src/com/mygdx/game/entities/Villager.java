/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public class Villager extends Entity{
        
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
        super(id, x, y, "villager");    
    }

    @Override
    public void updatePosition(){
        if (currentTOM == typeOfMovement.Die)
            return;
        
        if (b2body.getLinearVelocity().x == 0)
            currentTOM = typeOfMovement.Stand;
        
        speed = 2;
        /*if (Inputs.instance.run){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }*/

        if (followPosition != null)
        {
            if (followPosition.x-Block.size > b2body.getPosition().x && b2body.getLinearVelocity().x <= speed){
                b2body.applyLinearImpulse(new Vector2(0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = typeOfMovement.Walk;
                direction = typeOfDirection.Right;
            }

            if (followPosition.x+Block.size < b2body.getPosition().x && b2body.getLinearVelocity().x >= -speed){
                b2body.applyLinearImpulse(new Vector2(-0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = typeOfMovement.Walk;
                direction = typeOfDirection.Left;
            }
        }
        else
        {
            if (doRight() && b2body.getLinearVelocity().x <= speed){
                b2body.applyLinearImpulse(new Vector2(0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = typeOfMovement.Walk;
                direction = typeOfDirection.Right;
            }

            if (doLeft() && b2body.getLinearVelocity().x >= -speed){
                b2body.applyLinearImpulse(new Vector2(-0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = typeOfMovement.Walk;
                direction = typeOfDirection.Left;
            }
        }
    }
    
    
    @Override
    public void draw(SpriteBatch spriteBatch){
        if (health > 0)
            counter++;
        
        super.draw(spriteBatch);
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
    
    void goToPosition(Vector2 position) {
        followPosition = position;
    }
}
