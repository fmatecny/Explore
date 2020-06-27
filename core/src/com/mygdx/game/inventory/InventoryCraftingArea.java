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
public class InventoryCraftingArea extends Table{
    
    private final int size = 3;
    
    public InventorySlot[][] craftingSlots;
    public InventorySlot craftedItem;

    public InventoryCraftingArea() {
        
        craftingSlots =  new InventorySlot[size][size];
        craftedItem = new InventorySlot();
        
        for (int y = 0; y < size; y++) 
        {
            for (int x = 0; x < size; x++) 
            {
                final InventorySlot invenotryItem =  new InventorySlot();
                //invenotryItem.setDebug(true);
                invenotryItem.setName(Integer.toString(x+y*size));
                invenotryItem.setTouchable(Touchable.enabled);
                invenotryItem.setBackground(Skins.skin.getDrawable("cell"));

                /*invenotryItem.addListener(new ClickListener() {
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
                });*/

                craftingSlots[x][y] = invenotryItem;
                
                if (x == 2 && (y == 0 || y == 2))
                    this.add(invenotryItem).size(Inventory.sizeOfSlot).colspan(2).left();
                else if (x == 2 && y == 1)
                    this.add(invenotryItem).size(Inventory.sizeOfSlot).colspan(1).left().padRight(Inventory.sizeOfSlot/2);
                else
                    this.add(invenotryItem).size(Inventory.sizeOfSlot);
                
                if (x == 2 && y == 1)
                {
                    final InventorySlot invenotryCraftedItem =  new InventorySlot();
                    //invenotryItem.setDebug(true);
                    invenotryCraftedItem.setName(Integer.toString(100));
                    invenotryCraftedItem.setTouchable(Touchable.enabled);
                    invenotryCraftedItem.setBackground(Skins.skin.getDrawable("cell"));

                    /*invenotryCraftedItem.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(invenotryCraftedItem.getName() + "   " + invenotryCraftedItem.getX() + " " + invenotryCraftedItem.getX());
                        invenotryCraftedItem.drag = false;
                    }
                    }); 

                    invenotryCraftedItem.addListener(new DragListener(){
                    @Override
                    public void drag(InputEvent event, float x, float y, int pointer) {
                        System.out.println(invenotryCraftedItem.getName() + " aa " + x + " aa " + y + getDeltaX());
                        invenotryCraftedItem.drag = true;
                    }
                    });*/
                    
                    craftedItem = invenotryCraftedItem;
                    this.add(craftedItem).size(Inventory.sizeOfSlot);
                    
                }  
            }
            this.row();
        }
    }
    
    public InventorySlot getDragInventorySlot(){
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (craftingSlots[i][j].drop)
                    return craftingSlots[i][j]; 
            }
        }
        
        if (craftedItem.drop)
            return craftedItem;
        
        return null;
    }
    
    
    
}
