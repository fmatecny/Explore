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
public abstract class Entity implements EntityIfc{
    
    protected int id;
    
    protected float health = 100.0f;
    protected Body b2body;

    public Entity(int id) {
        this.id = id;
    }
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
    }
}


