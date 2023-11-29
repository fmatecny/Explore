/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Tool;

/**
 *
 * @author Fery
 */
public class InventorySlot extends Table{
    
    private boolean isOver = false;
    public int numOfItem;
    private Block block = null;
    private Item item = null;
    private Tool tool = null;
    private Texture texture;

    private BitmapFont font;
    public boolean drag = false;
    public boolean drop = false;
    public boolean touchDown = false;
    public boolean splitItems = false;
    private IntVector2 dropPos = new IntVector2();
    
    private int minItemsForSplit = 2;
    

    public InventorySlot() {        
        font = new BitmapFont();
        numOfItem = 0;  
        
        addListener(new DragListener(){
        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            if (touchDown)
                drag = true;
        }
        });


        addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                isOver = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                isOver = false;
                //System.err.println("exit" + numOfItem + toActor.toString());
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println(getName() + "   " + getX() + "m" + x + "," + y);
                if (numOfItem > 0 && Inputs.instance.showInventory && button == Input.Buttons.LEFT){
                    splitItems = false;
                    touchDown = true;}
                else if (numOfItem >= minItemsForSplit && Inputs.instance.showInventory && button == Input.Buttons.RIGHT){
                    if (touchDown == false)
                        splitItems = true;
                    drag = true;//drag function doeas not call for right button
                    touchDown = true;
                }  

                return touchDown;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(getName() + "   " + getX()+ "mmm" + x + "," + y);
                if (touchDown)
                {
                    drop = true;
                    dropPos.setXY((int) x, (int) y);
                }      
            }
        });
        

        
    }

    private void checkObject(){
        if (numOfItem <= 0)
            removeObject();     
    }
    
    public IntVector2 getDropPosition(){
        return dropPos;
    }

    public boolean isEmpty(){
        return (numOfItem <= 0 || (block == null && item == null && tool == null) || texture == null);
    }

    public boolean isBlock(){
        return (numOfItem > 0 && block != null && texture != null);
    }
    
    public boolean isTool(){
        return (numOfItem > 0 && tool != null && texture != null);
    }
    
    public boolean isItem(){
        return (numOfItem > 0 && item != null && texture != null);
    }
    
    public Block getBlock() {
        return isEmpty() ? null : block;
    }

    public Item getItem() {
        return isEmpty() ? null : item;
    }
    
    public Tool getTool() {
        return isEmpty() ? null : tool;
    }
    
    public InventoryObject getObject(){
        if (this.isBlock()) return this.block;
        if (this.isTool()) return this.tool;
        if (this.isItem()) return this.item;
        
        return null;
    }
    
    public String getObjectInfo(){
        if (isBlock())
            return block.info + "\n\nHardness = " + block.hardness;
        else if (isTool())
            return tool.info + "\n\nDamage = " + tool.damage;
        else if (isItem())
            return item.info;
        else
            return "";
    }
    
    public void setObject(InventorySlot slot, int n){
        this.numOfItem = n;
        
        if (slot.block != null)
        {
            setObject(slot.block);
        }
        else if(slot.item != null)
        {
            setObject(slot.item);
        }
        else if(slot.tool != null)
        {
            setObject(slot.tool);
        }
        else{
            removeObject();
        }
    }
    
    public void setObject(InventorySlot slot) {
        setObject(slot, slot.numOfItem);
    }
    
    public void setObject(Block block) {
        if (block != null)
        {
            this.block = block;
            this.texture = block.texture;
            this.block.textureRotation = 0;
            this.item = null;
            this.tool = null;
        }
    }
    
    public void setObject(Item item) {
        if (item != null)
        {
            this.item = item;
            this.texture = item.texture;
            this.block = null;
            this.tool = null;
        }  
    }
    
    public void setObject(Tool tool) {
        if (tool != null)
        {
            this.tool = tool;
            this.texture = tool.texture;
            this.block = null;
            this.item = null;
        }  
    }

    public void removeObject() {
        this.numOfItem = 0;
        this.block = null;
        this.item = null;
        this.tool = null;
        this.texture = null;
    }
    
    public void setMinObjectsForSplit(int minItemsForSplit) {
        this.minItemsForSplit = minItemsForSplit;
    }

    public boolean hasObject(InventorySlot dragSlot) {
        if (this.block != null && dragSlot.getBlock() != null)
        {
            if (this.block.id == dragSlot.getBlock().id)
                return true;
        }
        
        if (this.item != null && dragSlot.getItem() != null)
        {
            if (this.item.id == dragSlot.getItem().id)
                return true;
        }
        
        if (this.tool != null && dragSlot.getTool() != null)
        {
            if (this.tool.id == dragSlot.getTool().id)
                return true;
        }
        
        return false;
    }
    
    public boolean isObjectStackable(){
        return block != null ? block.stackable : !isTool();
    }

    public boolean isOver() {
        return isOver;
    }
    
    
    
    
    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        
        checkObject();
        
        if (isEmpty() == false)
        {
            if (touchDown)
            {
                if (splitItems)
                { 
                    batch.draw(texture, 
                            getX()+10, getY()+10,  
                            getWidth()-20, getHeight()-20);
                    font.draw(batch, Integer.toString(numOfItem/2 + numOfItem%2), getX()+30, getY()+20);
                    this.setZIndex(50);
                    batch.draw(texture, 
                            Inputs.instance.mouseX-400, 560-Inputs.instance.mouseY,  
                            getWidth()-15, getHeight()-15);
                    font.draw(batch, Integer.toString(numOfItem/2), Inputs.instance.mouseX-375, 570-Inputs.instance.mouseY);
                }
                else{
                    this.setZIndex(50);
                    batch.draw(texture, 
                            Inputs.instance.mouseX-400, 560-Inputs.instance.mouseY,  
                            getWidth()-15, getHeight()-15);
                    font.draw(batch, Integer.toString(numOfItem), Inputs.instance.mouseX-375, 570-Inputs.instance.mouseY);
                }  
            }
            else
            {
                this.setZIndex(1);
                batch.draw(texture, 
                        getX()+10, getY()+10,  
                        getWidth()-20, getHeight()-20);
                font.draw(batch, Integer.toString(numOfItem), getX()+30, getY()+20);
            }
        }
    } 
}
