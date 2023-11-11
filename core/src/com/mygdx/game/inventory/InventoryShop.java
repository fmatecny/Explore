/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Constants;
import com.mygdx.game.IntVector2;
import com.mygdx.game.Skins;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.AllTools;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class InventoryShop extends InventoryPack{

    private InventorySlot[] itemsForSale;
    private InventorySlot[] itemsForTrade;
    private Constants.typeOfEntity typeOfEntity;
    public InventorySlot soldItem;
    public InventorySlot buyItem;
    private final short NUM_OF_ITEMSFORTRADE = 3;
    
    public InventoryShop(Constants.typeOfEntity toe) {
        this.typeOfEntity = toe;
        soldItem = new InventorySlot();
        soldItem.setBackground(Skins.invenotrySlotBck);
        soldItem.setTouchable(Touchable.enabled);
        buyItem = new InventorySlot();
        buyItem.setBackground(Skins.invenotrySlotBck);
        buyItem.setTouchable(Touchable.enabled);
        
        itemsForSale = new InventorySlot[NUM_OF_ITEMSFORTRADE];
        itemsForTrade = new InventorySlot[NUM_OF_ITEMSFORTRADE];
        
        for (int i = 0; i < NUM_OF_ITEMSFORTRADE; i++) 
        {
            itemsForSale[i] = GetItemForSale();//new InventorySlot();
            //itemsForSale[i].setObject(AllItems.ironArmor);
            //itemsForSale[i].numOfItem = 1;
            itemsForSale[i].setTouchable(Touchable.disabled);
            itemsForSale[i].setBackground(Skins.invenotrySlotBck);
            itemsForSale[i].setName(Integer.toString(i));
            
            itemsForTrade[i] = new InventorySlot();
            itemsForTrade[i].setObject(AllItems.stick);
            itemsForTrade[i].numOfItem = 10;
            itemsForTrade[i].setTouchable(Touchable.disabled);
            itemsForTrade[i].setBackground(Skins.invenotrySlotBck);
            itemsForTrade[i].setName(Integer.toString(i));
        }
        //this.setDebug(true);
        this.setSkin(Skins.skin);
        this.add(itemsForTrade[0]);
        this.add(">").center().padLeft(Inventory.sizeOfSlot/2).padRight(Inventory.sizeOfSlot/2);
        this.add(itemsForSale[0]).left();
        this.row();
        this.add(itemsForTrade[1]);
        this.add(">").center();
        this.add(itemsForSale[1]).padRight(Inventory.sizeOfSlot*2);
        this.add(buyItem).size(Inventory.sizeOfSlot);
        this.add(">").center().padLeft(Inventory.sizeOfSlot/2).padRight(Inventory.sizeOfSlot/2);
        this.add(soldItem).size(Inventory.sizeOfSlot);
        this.row();
        this.add(itemsForTrade[2]);
        this.add(">").center();
        this.add(itemsForSale[2]).left();
    }

    public InventorySlot getDragInventorySlotAfterDrop() {
        if (soldItem.drop)
            return soldItem;
        else if (buyItem.drop)
            return buyItem;
        
        return null;
    }

    public InventorySlot getDropInventorySlot(IntVector2 pos) {
        if (includesInSlot(pos, soldItem))
            return soldItem;
        else if (includesInSlot(pos, buyItem))
            return buyItem;
        
        return null;
    }
    
    public boolean includesInSlot(IntVector2 pos, InventorySlot slot){
        System.out.println("IncludesInSlot: posX = " + pos.X +
                                        ", posY = " + pos.Y +
                                        ", getX = " + slot.getX() +
                                        ", getY = " + slot.getY() +
                                        ", getW = " + slot.getWidth() +
                                        ", getH = " + slot.getHeight());
        return (pos.X >= getX() + slot.getX() && 
                pos.X <= getX() + slot.getX() + slot.getWidth() &&
                pos.Y >= getY() + slot.getY() && 
                pos.Y <= getY() + slot.getY() + slot.getHeight());
    }

    public boolean isDragInShop() {
        return ( soldItem.drag || buyItem.drag );
    }

    public void trade() {
        if (buyItem.isItem())
        {
            for (int i = 0; i < itemsForTrade.length && soldItem.isEmpty(); i++) 
            {
                if ((itemsForTrade[i].getItem().id == buyItem.getItem().id) &&
                   (itemsForTrade[i].numOfItem <= buyItem.numOfItem))
                {
                    soldItem.setObject(itemsForSale[i]);
                    buyItem.numOfItem -= itemsForTrade[i].numOfItem;
                }
            }
        }
    }
    
    private InventorySlot GetItemForSale()
    {
        InventorySlot slot = new InventorySlot();
        int id;
        //if (typeOfEntity == Constants.typeOfEntity.villager)
        //{
            // tools
            if ((Math.random() * 10) > 5)
            {
                id = (int)((Math.random() * AllTools.toolList.size()));
                if (id == 0) id = 1;
                slot.setObject(AllTools.getToolById(id));
                slot.numOfItem = 1;
            }
            else //items
            {
                id = (int)((Math.random() * AllTools.toolList.size()));
                if (id == 0) id = 1;
                slot.setObject(AllItems.getItemById(id));
                slot.numOfItem = (int)((Math.random()*5)+1);
            }
        
        //}

        
        
        
        return slot;
    }
   
}
