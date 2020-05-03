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
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.AllBlocks;
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
    
    private Stage stageInventory;
    

    public Inventory(SpriteBatch spriteBatch) {
        
        stageInventory = new Stage(new FitViewport(MyGdxGame.width,MyGdxGame.height,new OrthographicCamera()),spriteBatch);
        
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(Inputs.instance);
        multiplexer.addProcessor(stageInventory);
        
        Gdx.input.setInputProcessor(multiplexer);
        mock = new Table(skin);
        mock.setFillParent(true);
        mock.setDebug(true);
        mock.center().top();

        window = new Window("", skin);
        window.add(new Label("Inventory", skin)).left().padLeft(15.0f);
        window.row();
        table = new Table();
        table.setDebug(true);
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
        mock.add(window).colspan(2).expandY();
        
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
        
        InventorySlot dragSlot = inventoryBar.getDragInventorySlot();
        
        if (dragSlot != null){
            dragSlotInBar = true;
            return dragSlot;
        }
        
        dragSlot = inventoryPackage.getDragInventorySlot();
        if (dragSlot != null)
            dragSlotInPackage = true;
        
        
        return dragSlot;
    }
    
    private InventorySlot getDropInventorySlot(){
        InventorySlot dropSlot = inventoryBar.getDropInventorSlot();
        
        if (dropSlot != null)
            return dropSlot;

        
        if (dragSlotInBar)
        {
            int yPos = inventoryBar.getDragInventorySlot().getDropPosition().Y;
            int xPos = inventoryBar.getDragInventorySlot().getDropPosition().X;
    
            yPos -= (sizeOfSlot + 10);
            
            if (yPos < 0)
                return null;
            
            yPos /= sizeOfSlot;
            yPos = numOfRow - yPos - 1;
            
            if (xPos < 0)
                xPos -= sizeOfSlot;
            
            xPos /= sizeOfSlot;
            xPos += Integer.valueOf(inventoryBar.getDragInventorySlot().getName());
            
            if (yPos < 0 || yPos >= numOfRow || xPos < 0 || xPos >= numOfCol)
                return null;
            
            if (inventoryPackage.inventoryPackageArray[xPos][yPos].getItem() == null)
                return inventoryPackage.inventoryPackageArray[xPos][yPos];
            
        }
            
            
        dropSlot = inventoryPackage.getDropInventorySlot();

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
