/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.IntVector2;
import com.mygdx.game.Skins;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public class InventoryFurnace extends InventoryPack{

    public InventorySlot lighterSlot;
    public InventorySlot oreSlot;
    public InventorySlot ingotSlot;
    private ProgressBar progressBarMelting;
    private ProgressBar progressBarBurning;
    
    
    public InventoryFurnace()
    {
        lighterSlot = new InventorySlot();
        lighterSlot.setBackground(Skins.invenotrySlotBck);
        lighterSlot.setTouchable(Touchable.enabled);
        lighterSlot.setName("lighterSlot");
        //lighterSlot.setMinObjectsForSplit(1000);
        
        oreSlot = new InventorySlot();
        oreSlot.setBackground(Skins.invenotrySlotBck);
        oreSlot.setTouchable(Touchable.enabled);
        oreSlot.setName("oreSlot");
        //oreSlot.setMinObjectsForSplit(1000);
        
        ingotSlot = new InventorySlot();
        ingotSlot.setBackground(Skins.invenotrySlotBck);
        ingotSlot.setTouchable(Touchable.enabled);
        ingotSlot.setName("ingotSlot");
        ingotSlot.setMinObjectsForSplit(1000);

        progressBarMelting = new ProgressBar(-20.0f, 100, .1f, false, Skins.skin, "default-horizontal");
        progressBarMelting.getStyle().background.setMinHeight(20);
        progressBarMelting.getStyle().knobBefore.setMinHeight(20);
        progressBarMelting.setValue(0.0f);  
        
        progressBarBurning = new ProgressBar(-20.0f, 100, .1f, false, Skins.skin, "default-horizontal");
        progressBarBurning.getStyle().background.setMinHeight(20);
        progressBarBurning.getStyle().knobBefore.setMinHeight(20);
        progressBarBurning.setValue(0.0f);  
        
        this.setDebug(true);
        this.setSkin(Skins.skin);
        this.add(oreSlot).left().size(Inventory.sizeOfSlot);
        this.row();
        this.add(progressBarMelting).width(Inventory.sizeOfSlot*2).height(Inventory.sizeOfSlot);
        this.add(">").width(Inventory.sizeOfSlot/3);
        this.add(ingotSlot).size(Inventory.sizeOfSlot).left();
        this.row();
        this.add(lighterSlot).left().size(Inventory.sizeOfSlot);
        this.row();
        this.add(progressBarBurning).width(Inventory.sizeOfSlot).height(Inventory.sizeOfSlot).left();
    }

    boolean isDragInFurnace() {
        if (lighterSlot == null || oreSlot == null || ingotSlot == null)
            return false;
        return ( lighterSlot.drag || oreSlot.drag || ingotSlot.drag );
    }

    InventorySlot getDragInventorySlotAfterDrop() {
        if (lighterSlot != null)
        {
            if (lighterSlot.drop)
                return lighterSlot;
        }
        if (oreSlot != null)
        {
            if (oreSlot.drop)
                return oreSlot;
        }
        if (ingotSlot != null)
        {
            if (ingotSlot.drop)
                return ingotSlot;
        }
        
        return null;
    }

    InventorySlot getDropInventorySlot(IntVector2 pos) {
        if (includesInSlot(pos, lighterSlot))
            return lighterSlot;
        else if(includesInSlot(pos, oreSlot))
            return oreSlot;
        
        return null;
    }
    
    public boolean includesInSlot(IntVector2 pos, InventorySlot slot){
        /*System.out.println("IncludesInSlot: posX = " + pos.X +
                                        ", posY = " + pos.Y +
                                        ", getX = " + slot.getX() +
                                        ", getY = " + slot.getY() +
                                        ", getW = " + slot.getWidth() +
                                        ", getH = " + slot.getHeight());*/
        return (pos.X >= getX() + slot.getX() && 
                pos.X <= getX() + slot.getX() + slot.getWidth() &&
                pos.Y >= getY() + slot.getY() && 
                pos.Y <= getY() + slot.getY() + slot.getHeight());
    }
    
    private boolean isSmeltPossible(){
  
        if (oreSlot.getBlock().id == AllBlocks.sand.id && lighterSlot.getItem().id == AllItems.coalIngot.id)
        {
            if (ingotSlot.isEmpty())
                return true;
            else if (ingotSlot.getBlock() != null)
                return ingotSlot.getBlock().id == AllBlocks.window.id;
        }
        return false;
    }

    
    
    public void update(){
        if (lighterSlot.isItem() && oreSlot.isBlock())
        {
            if (isSmeltPossible())
            {
                if (progressBarBurning.isAnimating() == false && progressBarMelting.getValue() == 0)
                {
                    progressBarBurning.setAnimateDuration(32);
                    progressBarBurning.setValue(100);
                }
                else if (progressBarBurning.isAnimating() == false && progressBarBurning.getValue() == 100)
                {
                    progressBarBurning.setAnimateDuration(0);
                    progressBarBurning.setValue(0);
                    lighterSlot.numOfItem--;    
                }
                
                if (progressBarMelting.isAnimating() == false && progressBarMelting.getValue() == 0)
                {
                    System.err.println("set 100");
                    progressBarMelting.setAnimateDuration(10);
                    progressBarMelting.setValue(100);
                    System.err.println("set 100 exit");
                }    
                else if (progressBarMelting.isAnimating() == false && progressBarMelting.getValue() == 100)
                {
                    System.err.println("set 0");
                    progressBarMelting.setAnimateDuration(0);
                    progressBarMelting.setValue(0);
                    if (ingotSlot.isEmpty())
                    {
                        ingotSlot.numOfItem = 1;
                        ingotSlot.setObject(new Block(AllBlocks.window));
                    }
                    else
                    {
                        ingotSlot.numOfItem++;
                    }
                    oreSlot.numOfItem--;
                    System.err.println("set 0 exit");
                }
                return;
            }
        }
        progressBarBurning.setAnimateDuration(0);
        progressBarBurning.setValue(0);
        progressBarMelting.setAnimateDuration(0);
        progressBarMelting.setValue(0);
    }   
}
