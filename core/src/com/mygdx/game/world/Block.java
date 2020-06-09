/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;

/**
 *
 * @author Fery
 */
public class Block extends Rectangle{
    public static final int size_in_pixels = 40;
    public static final float size = size_in_pixels/GameScreen.PPM;
 
        
    public int id;
    public int hardness;
    public boolean blocked;
    public Texture texture;
    public boolean isRotationAllowed;
    public int textureRotation;

    private Body body = null;
    

    public Block(Block b) {
        this.id = b.id;
        this.hardness = b.hardness;
        this.blocked = b.blocked;
        this.texture = b.texture;
        this.textureRotation = b.textureRotation;
        this.isRotationAllowed = b.isRotationAllowed;
        
    }

    public Block() {
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    
}
