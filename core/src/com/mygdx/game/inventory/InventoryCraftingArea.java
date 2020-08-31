/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Constants;
import com.mygdx.game.IntVector2;
import com.mygdx.game.Skins;
import com.mygdx.game.world.AllBlocks;

/**
 *
 * @author Fery
 */
public class InventoryCraftingArea extends InventoryPack{
    
    private final int size = 3;
    private int recipies = 0;
    
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
                invenotryItem.setName("craftingArea");//(Integer.toString(x+y*size));
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
                    invenotryCraftedItem.setName("craftSlot");
                    invenotryCraftedItem.setTouchable(Touchable.enabled);
                    invenotryCraftedItem.setBackground(Skins.skin.getDrawable("cell"));

                    craftedItem = invenotryCraftedItem;
                    craftedItem.setMinItemsForSplit(1000);
                    this.add(craftedItem).size(Inventory.sizeOfSlot);
                    
                }  
            }
            this.row();
        }
    }
    
    public InventorySlot getDragInventorySlotAfterDrop(){
        
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

    public InventorySlot getDropInventorySlot(IntVector2 pos){
        
        int x = (int)((pos.X-getX())/Inventory.sizeOfSlot);
        int y = (int)((pos.Y-getY())/Inventory.sizeOfSlot);
        //reverse idx
        y = size - (y+1);
        
        return isInRange(x, y) ? craftingSlots[x][y] : null;
    }
    
    private boolean isInRange(int x, int y){
    
        return x >= 0 && x < size &&
               y >=0 && y < size;
    }
    
    public boolean isDragInCraftingArea(){
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (craftingSlots[x][y].drag)
                    return true;   
            }
        }
        
        return craftedItem.drag;
    }

    public void craft() {
        
        recipies = getRecepies();
        int n;
        
        switch (recipies) 
        {
            case Constants.RECEPIE_PLANK:
                craftedItem.numOfItem = craftingSlots[1][1].numOfItem*4;
                craftedItem.setItem(AllBlocks.plank);
                //craftedItem.setMinItemsForSplit(8);
                break;
                
            case Constants.RECEPIE_HALF_PLANK:
                n = Math.min(craftingSlots[1][2].numOfItem, craftingSlots[2][2].numOfItem);
                craftedItem.numOfItem = n*2;
                craftedItem.setItem(AllBlocks.half_plank);
                //craftedItem.setMinItemsForSplit(8);
                break; 
                
            case Constants.RECEPIE_WOOD_STAIRS:
                n = Math.min(craftingSlots[0][0].numOfItem, craftingSlots[0][1].numOfItem);
                n = Math.min(n, craftingSlots[0][2].numOfItem);
                n = Math.min(n, craftingSlots[1][1].numOfItem);
                n = Math.min(n, craftingSlots[1][2].numOfItem);
                n = Math.min(n, craftingSlots[2][2].numOfItem);
                craftedItem.numOfItem = n*4;
                craftedItem.setItem(AllBlocks.wood_stairs);
                //craftedItem.setMinItemsForSplit(8);
                break; 
                
            case Constants.RECEPIE_WOOD_DOOR:
                n = Math.min(craftingSlots[0][0].numOfItem, craftingSlots[0][1].numOfItem);
                n = Math.min(n, craftingSlots[0][2].numOfItem);
                n = Math.min(n, craftingSlots[1][0].numOfItem);
                n = Math.min(n, craftingSlots[1][1].numOfItem);
                n = Math.min(n, craftingSlots[1][2].numOfItem);
                craftedItem.numOfItem = n;
                craftedItem.setItem(AllBlocks.door);
                //craftedItem.setMinItemsForSplit(8);
                break;     
            default:
                craftedItem.numOfItem = 0;
                craftedItem.setItem(null);
                //craftedItem.setMinItemsForSplit(2);
        }
     
    }

    public void updateCraft(int n) {
        
        switch (recipies) 
        {
            case Constants.RECEPIE_PLANK:
                craftingSlots[1][1].numOfItem -= n/4;
                break;
                
            case Constants.RECEPIE_HALF_PLANK:
                craftingSlots[1][2].numOfItem -= n/2;
                craftingSlots[2][2].numOfItem -= n/2;
                break;
                
            case Constants.RECEPIE_WOOD_STAIRS:
                craftingSlots[0][0].numOfItem -= n/4;
                craftingSlots[0][1].numOfItem -= n/4;
                craftingSlots[0][2].numOfItem -= n/4;
                craftingSlots[1][1].numOfItem -= n/4;
                craftingSlots[1][2].numOfItem -= n/4;
                craftingSlots[2][2].numOfItem -= n/4;
                break;
                
            case Constants.RECEPIE_WOOD_DOOR:
                craftingSlots[0][0].numOfItem -= n;
                craftingSlots[0][1].numOfItem -= n;
                craftingSlots[0][2].numOfItem -= n;
                craftingSlots[1][0].numOfItem -= n;
                craftingSlots[1][1].numOfItem -= n;
                craftingSlots[1][2].numOfItem -= n;
                break;    
                
                
            default:
                
        }
        
    }

    private int getRecepies() {
       
        if (
            craftingSlots[0][0].getItem() == null &&
            craftingSlots[0][1].getItem() == null &&
            craftingSlots[0][2].getItem() == null &&
            craftingSlots[1][0].getItem() == null &&
            craftingSlots[1][1].getItem() != null &&
            craftingSlots[1][2].getItem() == null &&
            craftingSlots[2][0].getItem() == null &&
            craftingSlots[2][1].getItem() == null &&
            craftingSlots[2][2].getItem() == null
            )
            {
            if (craftingSlots[1][1].getItem().id == AllBlocks.wood.id)
                return Constants.RECEPIE_PLANK;
            }
        
        if (
            craftingSlots[0][0].getItem() == null &&
            craftingSlots[0][1].getItem() == null &&
            craftingSlots[0][2].getItem() == null &&
            craftingSlots[1][0].getItem() == null &&
            craftingSlots[1][1].getItem() == null &&
            craftingSlots[1][2].getItem() != null &&
            craftingSlots[2][0].getItem() == null &&
            craftingSlots[2][1].getItem() == null &&
            craftingSlots[2][2].getItem() != null
            )
            {
            if (craftingSlots[1][2].getItem().id == AllBlocks.plank.id &&
                craftingSlots[2][2].getItem().id == AllBlocks.plank.id)
                return Constants.RECEPIE_HALF_PLANK;
            }
        
        if (
            craftingSlots[0][0].getItem() != null &&
            craftingSlots[0][1].getItem() != null &&
            craftingSlots[0][2].getItem() != null &&
            craftingSlots[1][0].getItem() == null &&
            craftingSlots[1][1].getItem() != null &&
            craftingSlots[1][2].getItem() != null &&
            craftingSlots[2][0].getItem() == null &&
            craftingSlots[2][1].getItem() == null &&
            craftingSlots[2][2].getItem() != null
            )
            {
            if (craftingSlots[0][0].getItem().id == AllBlocks.plank.id &&
                craftingSlots[0][1].getItem().id == AllBlocks.plank.id &&
                craftingSlots[0][2].getItem().id == AllBlocks.plank.id &&
                craftingSlots[1][1].getItem().id == AllBlocks.plank.id &&
                craftingSlots[1][2].getItem().id == AllBlocks.plank.id &&
                craftingSlots[2][2].getItem().id == AllBlocks.plank.id)
                return Constants.RECEPIE_WOOD_STAIRS;
            }
        
        if (
            craftingSlots[0][0].getItem() != null &&
            craftingSlots[0][1].getItem() != null &&
            craftingSlots[0][2].getItem() != null &&
            craftingSlots[1][0].getItem() != null &&
            craftingSlots[1][1].getItem() != null &&
            craftingSlots[1][2].getItem() != null &&
            craftingSlots[2][0].getItem() == null &&
            craftingSlots[2][1].getItem() == null &&
            craftingSlots[2][2].getItem() == null
            )
            {
            if (craftingSlots[0][0].getItem().id == AllBlocks.plank.id &&
                craftingSlots[0][1].getItem().id == AllBlocks.plank.id &&
                craftingSlots[0][2].getItem().id == AllBlocks.plank.id &&
                craftingSlots[1][0].getItem().id == AllBlocks.plank.id &&
                craftingSlots[1][1].getItem().id == AllBlocks.plank.id &&
                craftingSlots[1][2].getItem().id == AllBlocks.plank.id)
                return Constants.RECEPIE_WOOD_DOOR;
            }
        
        return -1;
    }
    
}
