/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 * @author Fery
 */
public abstract class Entity {
    
    private float health;
    private Body b2body;

    public Entity() {
        
    }
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
    }
}


