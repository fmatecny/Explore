/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Fery
 */
public class Block extends Rectangle{
    public static int size = 40;
 
        
    public int id;
    public int hardness;
    public boolean blocked;
    public Texture texture;
    public boolean isRotationAllowed;
    public int textureRotation;

    

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

    
}
