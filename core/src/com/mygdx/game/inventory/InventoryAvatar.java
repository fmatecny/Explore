/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;
import com.mygdx.game.Inputs;
import com.mygdx.game.Skins;
import com.mygdx.game.entities.MyAssetManager;

/**
 *
 * @author Fery
 */
public class InventoryAvatar extends Table{
    
    private InventorySlot invenotryItem;
    private TextureRegion frame;
    private Animation<AtlasRegion> animation;
    private Constants.typeOfDirection direction = Constants.typeOfDirection.Right;
    private Constants.typeOfMovement currentTOM = Constants.typeOfMovement.Stand;
    private Constants.typeOfArmor typeOfArmor;
    private float stateTime = 0;

    public InventoryAvatar() {
            //this.setDebug(true);
            this.setName(Integer.toString(0));
            this.setTouchable(Touchable.enabled);
            this.setBackground(Skins.skin.getDrawable("cell"));
            this.add().width(3*Inventory.sizeOfSlot).height(4*Inventory.sizeOfSlot);

    }
    
    
    public void setAvatar(Constants.typeOfArmor typeOfArmor){
        //this.invenotryItem.setObject(item);
        //this.frame = frame;
        this.typeOfArmor = typeOfArmor;

        animation = MyAssetManager.instance.getPlayerAnimations(this.typeOfArmor).get(direction.ordinal()).get(currentTOM.ordinal());
    }

    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
        stateTime += Gdx.graphics.getDeltaTime();
        //System.out.println("SlotWidth = " + this.getWidth() + ", SlotHeight = " + this.getHeight());
        //System.out.println("FrameWidth = " + frame.getRegionWidth()+ ", FrameHeight = " + frame.getRegionHeight());
        //System.out.println("X = " + this.getX()+ ", Y = " + this.getY());
        //System.out.println("Mouse X = " + Inputs.instance.mouseX + ", Y = " + this.getParent().getX());
        
        if (Inputs.instance.mouseX-400 > this.getX()+this.getWidth()/2)
            direction = Constants.typeOfDirection.Right;
        else
            direction = Constants.typeOfDirection.Left;
        
        frame = animation.getKeyFrame(stateTime, true);
        float width = (frame.getRegionHeight()/frame.getRegionWidth())*this.getHeight()*2;
        
        batch.draw(frame, this.getX()+this.getWidth()/2 - width/2, this.getY()-100,  width, this.getHeight()*2);
    }
    
    
    
    
    
    
    
}
