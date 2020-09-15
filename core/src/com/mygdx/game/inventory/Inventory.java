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
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Skins;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Block;
import static java.lang.Math.abs;
import jdk.vm.ci.meta.Constant;

/**
 *
 * @author Fery
 */
public class Inventory implements Disposable{
    
    public static int numOfCol = 9;
    public static int numOfRow = 3;
    public static int sizeOfSlot = 50;
    private final int space = 10;
    
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
    private boolean dragSlotInArmorSlots = false;
     
    private int dropPosOffsetY = 0;
    private int dropPosOffsetX = 0;
    IntVector2 realDropPos = new IntVector2();
    
    private Stage stageInventory;
    

    public Inventory() {
        
        stageInventory = new Stage(new FitViewport(MyGdxGame.width,MyGdxGame.height,new OrthographicCamera()),new SpriteBatch());
        
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
        inventoryBarHUD = new InventoryBar(numOfCol, Touchable.disabled);
         
        table.add(inventoryArmorSlots).left();
        table.add(inventoryAvatar).left();
        table.add(inventoryCraftingArea).right();
        //table.add().minWidth(2*50);
        table.row().padTop(space);
        table.add(inventoryPackage).colspan(3);
        table.row().padTop(space);
        table.add(inventoryBar).colspan(3);

        window.add(table).pad(20.0f);
        mock.add(window);//.colspan(2);//.expandY();
        
        //stageInventory.addActor(mock);

    }
    
    private boolean addObjectToInvenotryBar(Block block){
        if (block != null)
        {
            int i = getIdxOfBlockInInventoryBar(block);
            if (i != -1)
            {
                inventoryBarHUD.inventoryBar[i].setObject(block);
                inventoryBarHUD.inventoryBar[i].numOfItem++;
                inventoryBar.inventoryBar[i].setObject(block);
                inventoryBar.inventoryBar[i].numOfItem++;
                return true;
            }
            return false;
        }
        return true;
    }
    
    private boolean addObjectToInvenotryBar(Item item){
        if (item != null)
        {
            int i = getIdxOfItemInInventoryBar(item);
            if (i != -1)
            {
                int num = (int)(Math.random() * item.maxItemInBlock);
                if (num == 0) num = 1;
                
                inventoryBarHUD.inventoryBar[i].setObject(item);
                inventoryBarHUD.inventoryBar[i].numOfItem += num; 
                inventoryBar.inventoryBar[i].setObject(item);
                inventoryBar.inventoryBar[i].numOfItem += num;
                return true;
            }
            return false;
        }
        return true;
    }
    
    public boolean addObjectToInvenotry(Block block){
        if (block != null)
        {
            if (block.id == AllBlocks.door_down.id || block.id == AllBlocks.door_up.id)
            {
                //item.id = AllBlocks.door.id;
                block.texture = AllBlocks.door.texture;
            }
            
            if (block.id == AllBlocks.coal.id)
            {
                if (addObjectToInvenotryBar(AllItems.coalIngot))
                    return true;
                else
                    return inventoryPackage.addObject(AllItems.coalIngot);
            }
            if (block.id == AllBlocks.gold.id)
            {
                if (addObjectToInvenotryBar(AllItems.goldIngot))
                    return true;
                else
                    return inventoryPackage.addObject(AllItems.goldIngot);
            }
            if (block.id == AllBlocks.diamond.id)
            {
                if (addObjectToInvenotryBar(AllItems.diamondIngot))
                    return true;
                else
                    return inventoryPackage.addObject(AllItems.diamondIngot);
            }
            if (addObjectToInvenotryBar(block))
                return true;
            else
                return inventoryPackage.addObject(block);             
        }
        return true;
    }
    
    public int getIdxOfBlockInInventoryBar(Block block){
        
        Block slotItem;
        int emptySlot = -1;
        
        for (int i = numOfCol-1; i >= 0; i--) 
        {
            slotItem = inventoryBarHUD.inventoryBar[i].getBlock();
            if (inventoryBarHUD.inventoryBar[i].isEmpty())
                emptySlot = i;
            else if (slotItem != null)
            {
                if (block.stackable && slotItem.id == block.id)
                    return i;
            }
        }

        return emptySlot;
    }
    
    public int getIdxOfItemInInventoryBar(Item item){
        
        Item slotItem;
        int emptySlot = -1;
        
        for (int i = numOfCol-1; i >= 0; i--) 
        {
            slotItem = inventoryBarHUD.inventoryBar[i].getItem();
            if (inventoryBarHUD.inventoryBar[i].isEmpty())
                emptySlot = i;
            else if (slotItem != null)
            {
                if (slotItem.id == item.id)
                    return i;
            }
        }

        return emptySlot;
    }
    
    private void synchronizeHUDBar(){
        for (int i = 0; i < numOfCol; i++) {
            //inventoryBarHUD.inventoryBar[i] = inventoryBar.inventoryBar[i];
            inventoryBarHUD.inventoryBar[i].setObject(inventoryBar.inventoryBar[i]);
            //inventoryBarHUD.inventoryBar[i].numOfItem = inventoryBar.inventoryBar[i].numOfItem;
        }
    }
    
    private void synchronizeInventoryBar(){
        for (int i = 0; i < numOfCol; i++) {
            //inventoryBar.inventoryBar[i] = inventoryBarHUD.inventoryBar[i];
            inventoryBar.inventoryBar[i].setObject(inventoryBarHUD.inventoryBar[i]);
            //inventoryBar.inventoryBar[i].numOfItem = inventoryBarHUD.inventoryBar[i].numOfItem;
        }
    }
    
    //get dra invenotry slot AFTER drop item
    private InventorySlot getDragInventorySlotAfterDrop(){
        dragSlotInBar = false;
        dragSlotInPackage = false;
        dragSlotInCraftingArea = false;
        dragSlotInArmorSlots = false;
        
        InventorySlot dragSlot = inventoryBar.getDragInventorySlotAfterDrop();
        if (dragSlot != null){
            dragSlotInBar = true;
            return dragSlot;
        }
        
        dragSlot = inventoryPackage.getDragInventorySlotAfterDrop();
        if (dragSlot != null){
            dragSlotInPackage = true;
            return dragSlot;
        }
        
        dragSlot = inventoryCraftingArea.getDragInventorySlotAfterDrop();
        if (dragSlot != null){
            dragSlotInCraftingArea = true;
            return dragSlot;
        }
        
        dragSlot = inventoryArmorSlots.getDragInventorySlotAfterDrop();
        if (dragSlot != null){
            dragSlotInArmorSlots = true;
            return dragSlot;
        }
        
        return null;
    }
    
    private InventorySlot getDropInventorySlot(InventorySlot dragSlot){
        
        /*
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
        */
        
        // set offset
        if (dragSlotInBar)
        {
            dropPosOffsetX = 0;
            dropPosOffsetY = 0;
        }
        else if (dragSlotInPackage)
        {
            dropPosOffsetX = (int) inventoryPackage.getX();
            dropPosOffsetY = (int) inventoryPackage.getY();            
        }
        else if (dragSlotInCraftingArea)
        {
            dropPosOffsetX = (int) inventoryCraftingArea.getX();
            dropPosOffsetY = (int) inventoryCraftingArea.getY();  
        }
        else if (dragSlotInArmorSlots)
        {
            dropPosOffsetX = (int) inventoryArmorSlots.getX();
            dropPosOffsetY = (int) inventoryArmorSlots.getY();
        }
            
        IntVector2 dropPos = dragSlot.getDropPosition();
        //System.out.println(dropPos.X + "|||" + dropPos.Y);
        if (dropPos.X >= 0 && 
            dropPos.X <= Inventory.sizeOfSlot && 
            dropPos.Y >= 0 && 
            dropPos.Y <= Inventory.sizeOfSlot)
            return null;
        
        
        realDropPos.X = (int) (dragSlot.getX() + dropPos.X + dropPosOffsetX);
        realDropPos.Y = (int) (dragSlot.getY() + dropPos.Y + dropPosOffsetY);
        
        // from inventory bar to inventory bar
        if (inventoryBar.includes(realDropPos))
            return inventoryBar.getDropInventorySlot(realDropPos);

        // from inventory bar to invenotry package
        if (inventoryPackage.includes(realDropPos))
            return inventoryPackage.getDropInventorySlot(realDropPos);

        // from inventory bar to armor slots
        if (inventoryArmorSlots.includes(realDropPos))
            return inventoryArmorSlots.getDropInventorySlot(realDropPos);

        // from inventory bar to craftig area
        if (inventoryCraftingArea.includes(realDropPos))
            return inventoryCraftingArea.getDropInventorySlot(realDropPos);

        return null;
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

                setZIndex();
            
                InventorySlot dragSlot = getDragInventorySlotAfterDrop();
                
                if (dragSlot != null)
                {
                    InventorySlot dropSlot = getDropInventorySlot(dragSlot);
                    dragSlot.drag = false;
                    dragSlot.drop = false;
                    dragSlot.touchDown = false;
                
                    if (dropSlot != null)
                    { 
                        int n = dragSlot.numOfItem;
                        //Block item = dragSlot.getBlock();

                        if (dropSlot.isEmpty())
                        {
                            if (dragSlot.splitItems)
                            {
                                n = dragSlot.numOfItem/2;
                                dragSlot.numOfItem = n + dragSlot.numOfItem%2;
                                dropSlot.setObject(dragSlot, n);
                            }
                            else
                            {
                                dropSlot.setObject(dragSlot);
                                dragSlot.numOfItem = 0;
                                dragSlot.removeObject();
                            }
                            //dropSlot.numOfItem = n;
                            //dropSlot.setObject(dragSlot, n);
          
                        }
                        else if (dropSlot.hasObject(dragSlot))
                        {
                            if (dragSlot.splitItems)
                            {
                                n = dragSlot.numOfItem/2;
                                dragSlot.numOfItem = n + dragSlot.numOfItem%2;
                            }
                            else{
                                dragSlot.numOfItem = 0;
                                dragSlot.removeObject();
                            }
                            dropSlot.numOfItem += n;
                        }
                        
                        dragSlot.splitItems = false;
                        
                        if ("craftSlot".equals(dragSlot.getName()))
                            inventoryCraftingArea.updateCraft(n);

                        inventoryCraftingArea.craft();
                        
                            
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

    private void setZIndex() {
        //System.out.println(Inputs.instance.mouseX);
        
        if (inventoryBar.isDragInBar()){
            inventoryBar.setZIndex(10);
            inventoryPackage.setZIndex(0);
            inventoryCraftingArea.setZIndex(0);
            inventoryArmorSlots.setZIndex(0);
        }
        else if (inventoryPackage.isDragInPackage()){
            inventoryBar.setZIndex(0);
            inventoryPackage.setZIndex(10);
            inventoryCraftingArea.setZIndex(0);
            inventoryArmorSlots.setZIndex(0);
        }
        else if (inventoryCraftingArea.isDragInCraftingArea()){
            inventoryBar.setZIndex(0);
            inventoryPackage.setZIndex(0);
            inventoryCraftingArea.setZIndex(10);
            inventoryArmorSlots.setZIndex(0); 
        }
        else if (inventoryArmorSlots.isDragInArmorSlots()){
            inventoryBar.setZIndex(0);
            inventoryPackage.setZIndex(0);
            inventoryCraftingArea.setZIndex(0);
            inventoryArmorSlots.setZIndex(10);
        }
    }
    
    

    
    
}
