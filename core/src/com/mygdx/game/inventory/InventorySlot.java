/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public class InventorySlot extends Table{
    
    public int numOfItem;
    private BitmapFont font;
    private Block item = null;
    
    public boolean drag = false;
    public boolean drop = false;
    public boolean touchDown = false;
    private IntVector2 dropPos = new IntVector2();
    

    public InventorySlot() {        
        font = new BitmapFont();
        numOfItem = 0;  
        
        addListener(new DragListener(){
        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            System.out.println(getName() + " aa " + x + " aa " + y );
            if (touchDown)
                drag = true;
        }
        });


        addListener(new ClickListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println(getName() + "   " + getX() + "m" + x + "," + y);
            if (numOfItem > 0 && Inputs.instance.showInventory)
                touchDown = true;

            return touchDown;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println(getName() + "   " + getX()+ "m" + x + "," + y);
            if (touchDown)
            {
                drop = true;
                dropPos.setXY((int) x, (int) y);
            }
                
        }
        });
        
    }
    
    
    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        
        checkItem();
        
        if (isEmpty() == false)
        {
            if (touchDown)
            {
                this.setZIndex(50);
                batch.draw(item.texture, 
                Inputs.instance.mouseX-400, 600-Inputs.instance.mouseY,  
                getWidth()-15, getHeight()-15);
                font.draw(batch, Integer.toString(numOfItem), Inputs.instance.mouseX-375, 610-Inputs.instance.mouseY);
            }
            else 
            {
                    this.setZIndex(1);
                batch.draw(item.texture, 
                        getX()+10, getY()+10,  
                        getWidth()-20, getHeight()-20);
                font.draw(batch, Integer.toString(numOfItem), getX()+30, getY()+20);

            }
        }
        

        

    }
    
    
    private void checkItem(){
        if (numOfItem <= 0)
            item = null;
    }
    
    public IntVector2 getDropPosition(){
        return dropPos;
    }

    public boolean isEmpty(){
        return (numOfItem <= 0 || item == null);
    }

    public Block getItem() {
        return item;
    }

    public void setItem(Block item) {
        this.item = item;
    }

    
    
}
