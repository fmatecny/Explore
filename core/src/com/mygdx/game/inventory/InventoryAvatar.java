/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public class InventoryAvatar extends Table{

    public InventoryAvatar() {
        
            final InventorySlot invenotryItem =  new InventorySlot();
            //invenotryItem.setDebug(true);
            invenotryItem.setName(Integer.toString(0));
            invenotryItem.setTouchable(Touchable.enabled);
            invenotryItem.setBackground(Skins.skin.getDrawable("cell"));
            this.add(invenotryItem).width(3*50).height(4*50);
        
        
    }
    
    
    
}
