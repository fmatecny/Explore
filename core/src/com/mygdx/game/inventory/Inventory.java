/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Inputs;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Skins;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public class Inventory implements Disposable{
    
    public static int numOfCol = 9;
    public static int numOfRow = 3;
    public static int sizeOfSlot = 50;
    
    public Table mock,table;
    Window window;
    
    Skin skin = Skins.skin;
    
    InputMultiplexer multiplexer;
    
    private InventoryAvatar inventoryAvatar;
    private InventoryArmorSlots inventoryArmorSlots;
    private InventoryCraftingArea inventoryCraftingArea;
    private InventoryPackage inventoryPackage;
    private InventoryBar inventoryBar;
    private InventoryBar inventoryBarHUD;
    
    private boolean wasInventoryOpen = false;
    private boolean dragSlotInBar = false;
    private boolean dragSlotInPackage = false;
    private boolean dragSlotInCraftingArea = false;
    
    private Stage stageInventory;
    

    public Inventory(SpriteBatch spriteBatch) {
        
        stageInventory = new Stage(new FitViewport(MyGdxGame.width,MyGdxGame.height,new OrthographicCamera()),spriteBatch);
        
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(Inputs.instance);
        multiplexer.addProcessor(stageInventory);
        
        Gdx.input.setInputProcessor(multiplexer);
        mock = new Table(skin);
        mock.setFillParent(true);
        //mock.setDebug(true);
        mock.center().top().padTop(50);

        window = new Window("", skin);
        window.add(new Label("Inventory", skin)).left().padLeft(15.0f);
        window.row();
        table = new Table();
        //table.setDebug(true);
        table.setTouchable(Touchable.enabled);

        inventoryAvatar = new InventoryAvatar();
        inventoryArmorSlots = new InventoryArmorSlots();
        inventoryCraftingArea = new InventoryCraftingArea();
        inventoryPackage = new InventoryPackage(numOfCol, numOfRow);
        inventoryBar = new InventoryBar(numOfCol);
        inventoryBarHUD = new InventoryBar(numOfCol);
         
        table.add(inventoryArmorSlots).left();
        table.add(inventoryAvatar).left();
        table.add(inventoryCraftingArea).right();
        //table.add().minWidth(2*50);
        table.row().padTop(10);
        table.add(inventoryPackage).colspan(3);
        table.row().padTop(10);
        table.add(inventoryBar).colspan(3);

        window.add(table).pad(20.0f);
        mock.add(window);//.colspan(2);//.expandY();
        
        //stageInventory.addActor(mock);

    }
    
    private void addItemToInvenotryBar(Block item){
        if (item != null)
        {
            int i = getIdxOfItemInInventoryBar(item);
            if (i != -1)
            {
                inventoryBarHUD.inventoryBar[i].setItem(item);
                inventoryBarHUD.inventoryBar[i].numOfItem++;
                inventoryBar.inventoryBar[i].setItem(item);
                inventoryBar.inventoryBar[i].numOfItem++;
            }
        }
    }
    
    public void addItemToInvenotry(Block item){
        if (item != null){
            if (inventoryPackage.addItem(item) == false)
                addItemToInvenotryBar(item);
            
        }
    }
    
    public int getIdxOfItemInInventoryBar(Block item){
        
        Block slotItem;
        int emptySlot = -1;
        
        for (int i = numOfCol-1; i >= 0; i--) 
        {
            slotItem = inventoryBarHUD.inventoryBar[i].getItem();
            if (slotItem == null)
                emptySlot = i;
            else if (slotItem.id == item.id)
                return i;
        }

        return emptySlot;
    }
    
    private void synchronizeHUDBar(){
        for (int i = 0; i < numOfCol; i++) {
            inventoryBarHUD.inventoryBar[i].setItem(inventoryBar.inventoryBar[i].getItem());
            inventoryBarHUD.inventoryBar[i].numOfItem = inventoryBar.inventoryBar[i].numOfItem;
        }
    }
    
    private void synchronizeInventoryBar(){
        for (int i = 0; i < numOfCol; i++) {
            inventoryBar.inventoryBar[i].setItem(inventoryBarHUD.inventoryBar[i].getItem());
            inventoryBar.inventoryBar[i].numOfItem = inventoryBarHUD.inventoryBar[i].numOfItem;
        }
    }
    
    private InventorySlot getDragInventorySlot(){
        dragSlotInBar = false;
        dragSlotInPackage = false;
        dragSlotInCraftingArea = false;
        
        InventorySlot dragSlot = inventoryBar.getDragInventorySlot();
        
        if (dragSlot != null){
            dragSlotInBar = true;
            return dragSlot;
        }
        
        dragSlot = inventoryPackage.getDragInventorySlot();
        if (dragSlot != null){
            dragSlotInPackage = true;
            return dragSlot;
        }
        
        dragSlot = inventoryCraftingArea.getDragInventorySlot();
        if (dragSlot != null)
            dragSlotInCraftingArea = true;
        
        
        return dragSlot;
    }
    
    private InventorySlot getDropInventorySlot(){
        // from inventory bar to inventory bar
        InventorySlot dropSlot = inventoryBar.getDropInventorSlot();
        
        if (dropSlot != null)
            return dropSlot;

        // from inventory bar to invenotry package/craftig area/armor slots
        if (dragSlotInBar)
        {
            int yPos = inventoryBar.getDragInventorySlot().getDropPosition().Y;
            int xPos = inventoryBar.getDragInventorySlot().getDropPosition().X;
            System.err.println(xPos + "|" + yPos);
            
            /////////////// y position  ////////////////
            //0-50 - inventory bar
            //50-60 - space
            //60-210 - invetory package
            //210-220 - space
            //220-420 - armor slots
            //245-395 - crafting area
            //295-345 - crafting slot
            
            //////////// x position ///////////////////
            //0-450 - inventory
            //0-50 - armor slots
            //225-375 - crafting area
            //400 - 450 - crafting slot
            
            if (yPos < sizeOfSlot + 10 || yPos > 2*10 + sizeOfSlot*(numOfRow + 5))
                return null;
            
            // moving of item from right to left
            if (xPos < 0)
                xPos -= sizeOfSlot;
            
            // from inventory bar to invenotry package
            if (yPos <= sizeOfSlot + 10 + sizeOfSlot*numOfRow)
            {
                // get y zero pos of invenotry package
                yPos -= (sizeOfSlot + 10);

                // get y index of row (from bottom to top)
                yPos /= sizeOfSlot;
                // get y index of row (from top to bottom)
                yPos = numOfRow - yPos - 1;

                if (yPos >= numOfRow)
                    return null;

                xPos /= sizeOfSlot;
                xPos += Integer.valueOf(inventoryBar.getDragInventorySlot().getName());

                if (xPos < 0 || xPos >= numOfCol)
                    return null;

                if (inventoryPackage.inventoryPackageArray[xPos][yPos].getItem() == null)
                    return inventoryPackage.inventoryPackageArray[xPos][yPos];
            }
            // from inventory bar to craftig area/armor slots
            else if (yPos >= 2*10 + sizeOfSlot*(numOfRow+1))
            {
                xPos /= sizeOfSlot;
                xPos += Integer.valueOf(inventoryBar.getDragInventorySlot().getName());
                
                if (xPos < 0 || xPos >= numOfCol)
                    return null;
                
                // from inventory armor slots
                if (xPos == 0)
                {
                
                }
                // from inventory craftig slot
                else if (xPos == numOfCol)
                {
                
                }
                // from inventory craftig area
                else 
                {
                
                }
            }
            
        }
            
        // from inventory package to inventory package  
        dropSlot = inventoryPackage.getDropInventorySlot();
        // from inventory package to inventory bar
        if(dragSlotInPackage && dropSlot == null)
        {
            int yPos = inventoryPackage.getDragInventorySlot().getDropPosition().Y;
            int xPos = inventoryPackage.getDragInventorySlot().getDropPosition().X;
            
            int yIdx = Integer.valueOf(inventoryPackage.getDragInventorySlot().getName())/numOfCol;
            
            if (!(yPos < -((numOfRow-yIdx-1)*sizeOfSlot+10) && yPos > -((numOfRow-yIdx)*sizeOfSlot+10)))
                return null;
            
            if (xPos < 0)
                xPos -= sizeOfSlot;

            xPos /= sizeOfSlot;
            xPos += (Integer.valueOf(inventoryPackage.getDragInventorySlot().getName())%numOfCol);
            
            
            if (inventoryBar.inventoryBar[xPos].getItem() == null)
                return inventoryBar.inventoryBar[xPos];
        }
        
        
        return dropSlot;
    }
    
    public void draw(){
        Gdx.input.setInputProcessor(multiplexer);
        mock.setVisible(Inputs.instance.showInventory);

        if (!Inputs.instance.showInventory)
        {
            if(wasInventoryOpen){
                wasInventoryOpen = false;
                synchronizeHUDBar();
            }
            
            for (int i = 0; i < numOfCol; i++) {
                if (i == Inputs.instance.scrollIdx)
                    inventoryBarHUD.setActiveBckForSlot(i);
                else
                    inventoryBarHUD.setNonActiveBckForSlot(i);
            }
            inventoryBarHUD.setPosition(MyGdxGame.width/2,100);//(GameScreen.camera.position.x, GameScreen.camera.position.y - MyGdxGame.height/2 + 50);
            stageInventory.addActor(inventoryBarHUD);
        }
        else
        {
            if (wasInventoryOpen == false){
                wasInventoryOpen = true;
                synchronizeInventoryBar();
            }

            if (inventoryBar.isDragInBar()){
                inventoryPackage.setZIndex(0);
                inventoryBar.setZIndex(10);
            }
            else if (inventoryPackage.isDragInPackage()){
                inventoryPackage.setZIndex(10);
                inventoryBar.setZIndex(0);
            }

                InventorySlot dragSlot = getDragInventorySlot();
                InventorySlot dropSlot = getDropInventorySlot();
                
                if (dragSlot != null)
                {
                    dragSlot.drag = false;
                    dragSlot.drop = false;
                    dragSlot.touchDown = false;
                
                    if (dropSlot != null)
                    { 
                        int n = dragSlot.numOfItem;
                        Block item = dragSlot.getItem();


                        dragSlot.numOfItem = 0;
                        dragSlot.setItem(null);

                        dropSlot.numOfItem = n;
                        dropSlot.setItem(item);

                    }
                }
            //mock.setPosition(MyGdxGame.width/2- mock.getWidth()/2, MyGdxGame.height - mock.getHeight() + 100);
            stageInventory.addActor(mock);
        }

        stageInventory.draw();
    }

    public InventoryBar getInventoryBarHUD() {
        return inventoryBarHUD;
    }
    
    public Stage getStageInventory(){
        return stageInventory;
    }

    @Override
    public void dispose() {
        stageInventory.dispose();
    }
    
    

    
    
}
