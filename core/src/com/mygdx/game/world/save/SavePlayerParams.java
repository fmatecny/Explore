/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.save;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Fery
 */
public class SavePlayerParams {

    public Vector2 position;
    //private int health;
    
    public SaveInventoryParams saveInventoryParams;
    
    public SavePlayerParams() {
        saveInventoryParams = new SaveInventoryParams();
    }
    
}
