/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Constants;
import com.mygdx.game.IntVector2;
import com.mygdx.game.Skins;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.AllTools;

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
    private int saleIndex = 0;
    private boolean wasDefaultInSwitch = false;
    
    public InventoryShop(Constants.typeOfEntity toe) {
        this.typeOfEntity = toe;
        soldItem = new InventorySlot();
        soldItem.setBackground(Skins.invenotrySlotBck);
        soldItem.setTouchable(Touchable.enabled);
        soldItem.setName("soldSlot");
        soldItem.setMinObjectsForSplit(1000);
        buyItem = new InventorySlot();
        buyItem.setBackground(Skins.invenotrySlotBck);
        buyItem.setTouchable(Touchable.enabled);
        buyItem.setName("buySlot");
        buyItem.setMinObjectsForSplit(1000);
        
        itemsForSale = new InventorySlot[NUM_OF_ITEMSFORTRADE];
        itemsForTrade = new InventorySlot[NUM_OF_ITEMSFORTRADE];
        
        for (int i = 0; i < NUM_OF_ITEMSFORTRADE; i++) 
        {
            itemsForSale[i] = new InventorySlot();
            itemsForTrade[i] = new InventorySlot();
            
            SetItemForSaleAndTrade(itemsForSale[i],itemsForTrade[i]);//new InventorySlot();

            itemsForSale[i].setTouchable(Touchable.disabled);
            itemsForSale[i].setBackground(Skins.invenotrySlotBck);
            itemsForSale[i].setName(Integer.toString(i));
            
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
        if (soldItem != null)
        {
            if (soldItem.drop)
                return soldItem;
        }
        if (buyItem != null)
        {
            if (buyItem.drop)
                return buyItem;
        }

        
        return null;
    }

    public InventorySlot getDropInventorySlot(IntVector2 pos) {
        /*if (includesInSlot(pos, soldItem))
            return soldItem;
        else */
        // possible to put item only to bytItem slot
        if (includesInSlot(pos, buyItem))
            return buyItem;
        
        return null;
    }
    
    public void update(){
        buyItem.numOfItem -= itemsForTrade[saleIndex].numOfItem;
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
        if (soldItem == null || buyItem == null)
            return false;
        return ( soldItem.drag || buyItem.drag );
    }

    public void trade() {
        if (buyItem.isEmpty())
            soldItem.numOfItem = 0;   
        else if (soldItem.isEmpty())
        {
            for (int i = 0; i < itemsForTrade.length && soldItem.isEmpty() && !itemsForTrade[i].isEmpty(); i++) 
            {
                if ((itemsForTrade[i].getObject().id == buyItem.getObject().id) &&
                   (itemsForTrade[i].numOfItem <= buyItem.numOfItem))
                {
                    soldItem.setObject(itemsForSale[i]);
                    saleIndex = i;
                    //buyItem.numOfItem -= itemsForTrade[i].numOfItem;
                    return;
                }
            }
            soldItem.numOfItem = 0;
        }
    }
    

    private void SetItemForSaleAndTrade(InventorySlot saleSlot, InventorySlot tradeSlot) {
        if (typeOfEntity == Constants.typeOfEntity.villager)
        {
            int idx = (int)(Math.random() * 10);
            switch (idx) 
            {
                case 0: saleSlot.setObject(AllItems.stick);
                        saleSlot.numOfItem = (int)(Math.random() * 10) + 1;
                        tradeSlot.setObject(AllBlocks.coal);
                        tradeSlot.numOfItem = (int)(Math.random() * 10) + 1;
                        break;
                case 1: saleSlot.setObject(AllItems.bucket);
                        saleSlot.numOfItem = 1;
                        tradeSlot.setObject(AllBlocks.plank);
                        tradeSlot.numOfItem = (int)(Math.random() * 15) + 20;
                        break;
                case 2: saleSlot.setObject(AllItems.coalIngot);
                        saleSlot.numOfItem = 5;
                        tradeSlot.setObject(AllBlocks.iron);
                        tradeSlot.numOfItem = 5;
                        break;
                default:
                        if (wasDefaultInSwitch)
                            break;
                        int id = (int)((Math.random() * AllBlocks.blockList.size()));
                        if (id == 0) id = 1;
                        saleSlot.setObject(AllBlocks.getBlockById(id));
                        saleSlot.numOfItem = (int)((Math.random() * 3f + 1));
                        tradeSlot.setObject(AllItems.stick);
                        tradeSlot.numOfItem = (int)((Math.random() * 50f + 2));
                        wasDefaultInSwitch = true;
                    
            }        
        }
        else if (typeOfEntity == Constants.typeOfEntity.smith)
        {
            int idx = (int)(Math.random() * 10);
            switch (idx) 
            {
                case 0: saleSlot.setObject(AllTools.diamondSword);
                        saleSlot.numOfItem = 1;
                        tradeSlot.setObject(AllItems.diamondIngot);
                        tradeSlot.numOfItem = (int)(Math.random() * 15) + 20;
                        break;
                case 1: saleSlot.setObject(AllItems.ironArmor);
                        saleSlot.numOfItem = 1;
                        tradeSlot.setObject(AllItems.ironIngot);
                        tradeSlot.numOfItem = (int)(Math.random() * 15) + 20;
                        break;
                case 2: saleSlot.setObject(AllItems.diamondArmor);
                        saleSlot.numOfItem = 1;
                        tradeSlot.setObject(AllItems.diamondIngot);
                        tradeSlot.numOfItem = (int)(Math.random() * 15) + 20;;
                        break;
                default:
                        if (wasDefaultInSwitch)
                            break;
                        int id = (int)((Math.random() * AllTools.toolList.size()));
                        if (id == 0) id = 1;
                        saleSlot.setObject(AllTools.getToolById(id));
                        saleSlot.numOfItem = 1;
                        tradeSlot.setObject(AllItems.diamondIngot);
                        tradeSlot.numOfItem = (int)((Math.random() * 15f + 10));
                        wasDefaultInSwitch = true;
                    
            }        
        
        }
    }
}
