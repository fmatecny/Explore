/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.Constants;
import com.mygdx.game.IntVector2;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public class InventoryArmorSlots extends InventoryPack{

    public InventorySlot[] inventoryArmorSlots;
    
    private final int numOfSlots = 1; //4;
    
    
    public InventoryArmorSlots() {
        
        inventoryArmorSlots =  new InventorySlot[numOfSlots];
        
        for (int i = 0; i < numOfSlots; i++) 
        {
            final InventorySlot invenotryItem =  new InventorySlot();
            //invenotryItem.setDebug(true);
            invenotryItem.setName("ArmorSlot" + Integer.toString(i));
            invenotryItem.setTouchable(Touchable.enabled);
            invenotryItem.setBackground(Skins.skin.getDrawable("cell"));

            invenotryItem.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println(invenotryItem.getName() + "   " + invenotryItem.getX() + " " + invenotryItem.getX());
                invenotryItem.drag = false;
            }
            }); 

            invenotryItem.addListener(new DragListener(){
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                //System.out.println(invenotryItem.getName() + " aa " + x + " aa " + y + getDeltaX());
                invenotryItem.drag = true;
            }
            });

            inventoryArmorSlots[i] = invenotryItem;
            this.add(invenotryItem).size(Inventory.sizeOfSlot);
            this.row();
        }  
    }
    
    public InventorySlot getDragInventorySlotAfterDrop(){
        for (int i = 0; i < numOfSlots; i++) {
            if (inventoryArmorSlots[i].drop)
                return inventoryArmorSlots[i];
        }
        
        return null;
    }
    
    public InventorySlot getDropInventorySlot(IntVector2 pos){
        
        int y = (int)((pos.Y-getY())/Inventory.sizeOfSlot);
        //reverse idx
        y = numOfSlots - (y+1);
        
        return isInRange(y) ? inventoryArmorSlots[y] : null;

    }
    
    private boolean isInRange(int i){
    
        return i >= 0 && i < numOfSlots;
    }
    
    public boolean isDragInArmorSlots(){
        for (int y = 0; y < numOfSlots; y++) {
            if (inventoryArmorSlots[y].drag)
                return true;   
        }
        
        return false;
    }
    
    public Constants.typeOfArmor getTypeOfArmor(){
        if (inventoryArmorSlots[0].isItem())
        {
            if (inventoryArmorSlots[0].getItem().id == AllItems.ironArmor.id)
                return Constants.typeOfArmor.Iron;
            else if (inventoryArmorSlots[0].getItem().id == AllItems.diamondArmor.id)
                return Constants.typeOfArmor.Diamond;
        }
        
        return Constants.typeOfArmor.Default;
    }
}

