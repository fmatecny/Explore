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
public class InventoryBar extends Table{

    public InventorySlot[] inventoryBar;

    public InventoryBar(int numOfCol) {
        inventoryBar =  new InventorySlot[numOfCol];
        
        for (int i = 0; i < numOfCol; i++) 
        {
            final InventorySlot invenotryItem =  new InventorySlot();
            //invenotryItem.setDebug(true);
            invenotryItem.setName(Integer.toString(i));
            invenotryItem.setTouchable(Touchable.enabled);
            invenotryItem.setBackground(Skins.invenotrySlotBck);//Skins.skin.getDrawable("cell"));
            inventoryBar[i] = invenotryItem;
            this.add(invenotryItem).size(Inventory.sizeOfSlot);
        }
        
    }
    
    public InventorySlot getInventorySlot(int index){
        return inventoryBar[index];  
    }
    
    public void setActiveBckForSlot(int i){
        getInventorySlot(i).setBackground(Skins.invenotryActiveSlotBck);
    }
    
    public void setNonActiveBckForSlot(int i){
        getInventorySlot(i).setBackground(Skins.invenotrySlotBck);
    }
    
    public void setNonActiveBackground(){
        setBackground(Skins.invenotryActiveSlotBck);
    }
    
    public InventorySlot getDragInventorySlot(){
        for (int i = 0; i < Inventory.numOfCol; i++) {
            if (inventoryBar[i].drop)
                return inventoryBar[i];
        }
        
        return null;
    }
    
    public boolean isDragInBar(){
        for (int i = 0; i < Inventory.numOfCol; i++) {
            if (inventoryBar[i].drag)
                return true;
        }
        
        return false;
    }
    
    public InventorySlot getDropInventorSlot(){
        int x = -1;
        int yPos = -1;
        for (int i = 0; i < Inventory.numOfCol; i++) 
        {
            if (inventoryBar[i].drop)
            {
                int xPos = inventoryBar[i].getDropPosition().X;
                yPos = inventoryBar[i].getDropPosition().Y;
                if (xPos < 0)
                    xPos -= Inventory.sizeOfSlot;
                
                x = (xPos/Inventory.sizeOfSlot) + i;
            }
                
        }
        if (x >= 0 && x < Inventory.numOfCol && 
            yPos >= 0 && yPos < Inventory.sizeOfSlot && 
            inventoryBar[x].getItem() == null)
            return inventoryBar[x];
        
        return null;
    }
    
}
