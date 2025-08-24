/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Skins;

/**
 *
 * @author Ferino
 */
public class InventoryObjectInfo extends Table{

    private BitmapFont font;
    private InventoryObject object;
    private String info = "";
    
    public InventoryObjectInfo() {
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        this.setVisible(true);
        this.add().width(200).height(100);
        this.setBackground(Skins.skin.getDrawable("cell"));
        this.pack();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
        /*batch.draw(texture, 
                   Inputs.instance.mouseX-400, 560-Inputs.instance.mouseY,  
                   getWidth()-15, getHeight()-15);*/
        //if (object != null)
        font.draw(batch, info, getX()+10, getY()+this.getHeight()-10); 
    }

    public void setInfo(String info) {
        this.info = info;
        this.setVisible(this.info != "");
    }
}
