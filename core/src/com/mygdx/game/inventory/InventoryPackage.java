/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.IntVector2;
import com.mygdx.game.Skins;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public class InventoryPackage extends InventoryPack{

    
    public InventorySlot[][] inventoryPackageArray;
    
    public InventoryPackage(int numOfCol, int numOfRow) {
        
        inventoryPackageArray = new InventorySlot[numOfCol][numOfRow];
        
        for(int y = 0; y < numOfRow; y++) 
        {
            for (int x = 0; x < numOfCol; x++) 
            {
                
                final InventorySlot invenotryItem =  new InventorySlot();
                //invenotryItem.setDebug(true);
                invenotryItem.setName(Integer.toString(x+y*numOfCol));
                invenotryItem.setTouchable(Touchable.enabled);
                invenotryItem.setBackground(Skins.invenotrySlotBck);

                inventoryPackageArray[x][y] = invenotryItem;
                this.add(invenotryItem).size(Inventory.sizeOfSlot);
            }     
            this.row();
        }
    }
    
    public InventorySlot getDragInventorySlotAfterDrop(){
        for (int y = 0; y < Inventory.numOfRow; y++) {
            for (int x = 0; x < Inventory.numOfCol; x++) {
                if (inventoryPackageArray[x][y].drop)
                    return inventoryPackageArray[x][y];   
            }
        }
        return null;
    
    }
    
    public InventorySlot getDropInventorySlot(){
        
        int xIdx = -1;
        int yIdx = -1;
        
        for (int y = 0; y < Inventory.numOfRow; y++) {
            for (int x = 0; x < Inventory.numOfCol; x++) {
                if (inventoryPackageArray[x][y].drop)
                {
                    int dropPosX = inventoryPackageArray[x][y].getDropPosition().X;
                    int dropPosY = inventoryPackageArray[x][y].getDropPosition().Y;
                    
                    if (dropPosX < 0)
                        dropPosX -= Inventory.sizeOfSlot;
                    
                    xIdx = (dropPosX/Inventory.sizeOfSlot) + x;
                    
                    if (dropPosY < 0)
                        dropPosY -= Inventory.sizeOfSlot;
                    
                    yIdx = y-(dropPosY/Inventory.sizeOfSlot);
                    
                    if (xIdx >= 0 && xIdx < Inventory.numOfCol && 
                        yIdx >= 0 && yIdx < Inventory.numOfRow && 
                        inventoryPackageArray[xIdx][yIdx].getItem() == null)
                        return inventoryPackageArray[xIdx][yIdx];
                }         
            } 
        }
        return null;
    }
   
    public InventorySlot getDropInventorySlot(IntVector2 pos){
        
        int x = (int)((pos.X-getX())/Inventory.sizeOfSlot);
        int y = (int)((pos.Y-getY())/Inventory.sizeOfSlot);
        //reverse idx
        y = Inventory.numOfRow - (y+1);
        
        return isInRange(x, y) ? inventoryPackageArray[x][y] : null;

    }
    
    public boolean isDragInPackage(){
        for (int y = 0; y < Inventory.numOfRow; y++) {
            for (int x = 0; x < Inventory.numOfCol; x++) {
                if (inventoryPackageArray[x][y].drag)
                    return true;   
            }
        }
        return false;
    }
    
    public boolean addItem(Block item){
        IntVector2 index = getSlotIdx(item);
        if (index != null){
            inventoryPackageArray[index.X][index.Y].setItem(item);
            inventoryPackageArray[index.X][index.Y].numOfItem++;
            return true;
        } 
        return false;
    }
    
    public IntVector2 getSlotIdx(Block item){
        for (int y = 0; y < Inventory.numOfRow; y++) {
            for (int x = 0; x < Inventory.numOfCol; x++) {
                if (inventoryPackageArray[x][y].getItem() != null)
                    if (inventoryPackageArray[x][y].getItem().id == item.id)
                        return (new IntVector2(x , y));   
            }
        }
    
        return null;
    }
    
    private boolean isInRange(int x, int y){
        return (x >= 0 && x < Inventory.numOfCol && y >= 0 && y < Inventory.numOfRow);
    
    }
}
