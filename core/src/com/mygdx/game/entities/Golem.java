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
public class Golem extends Entity{

    public Golem(int id, float x, float y) {
        super(id, x, y, "golem");
        //changeScale(6f);
    }

    @Override
    public void updatePosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

    
}
