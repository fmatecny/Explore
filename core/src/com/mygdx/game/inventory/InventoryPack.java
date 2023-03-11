/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.IntVector2;

/**
 *
 * @author Fery
 */
public abstract class InventoryPack extends Table{

    public boolean includes(IntVector2 pos){
    
        return (pos.X >= getX() && 
                pos.X <= getX() + getWidth() &&
                pos.Y >= getY() && 
                pos.Y <= getY() + getHeight());
    }
    
}
