/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public class InventoryArmorSlots extends Table{

    public InventorySlot[] inventoryArmorSlots;
    
    private final int numOfSlots = 4;
    
    
    public InventoryArmorSlots() {
        
        inventoryArmorSlots =  new InventorySlot[numOfSlots];
        
        for (int i = 0; i < numOfSlots; i++) 
        {
            final InventorySlot invenotryItem =  new InventorySlot();
            //invenotryItem.setDebug(true);
            invenotryItem.setName(Integer.toString(i));
            invenotryItem.setTouchable(Touchable.enabled);
            invenotryItem.setBackground(Skins.skin.getDrawable("cell"));

            invenotryItem.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(invenotryItem.getName() + "   " + invenotryItem.getX() + " " + invenotryItem.getX());
                invenotryItem.drag = false;
            }
            }); 

            invenotryItem.addListener(new DragListener(){
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                System.out.println(invenotryItem.getName() + " aa " + x + " aa " + y + getDeltaX());
                invenotryItem.drag = true;
            }
            });

            inventoryArmorSlots[i] = invenotryItem;
            this.add(invenotryItem).size(50);
            this.row();
        }
        
        
        
        
    }
    
    
    
}
