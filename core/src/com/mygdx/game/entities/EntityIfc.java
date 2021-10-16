/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Fery
 */
public interface EntityIfc {
   
    void defineBody(float x, float y);
    void updatePosition();
    void draw(SpriteBatch spriteBatch);
    void dispose();
  
}
