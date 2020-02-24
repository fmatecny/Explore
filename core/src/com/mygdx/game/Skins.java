/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 *
 * @author Fery
 */
public class Skins {
    
    public static Skins instance;
    public static Skin skin;
    public static SpriteDrawable invenotryActiveSlotBck, invenotrySlotBck;

    public Skins() {
        
        
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        
        ProgressBar.ProgressBarStyle style = skin.get("xp", ProgressBar.ProgressBarStyle.class);
        skin.getTiledDrawable("xp-bg").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("xp-bg");
        skin.getTiledDrawable("xp").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("xp");
        
        style = skin.get("hunger", ProgressBar.ProgressBarStyle.class);
        skin.getTiledDrawable("meat").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("meat");
        skin.getTiledDrawable("meat-bg").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("meat-bg");
        
        style = skin.get("health", ProgressBar.ProgressBarStyle.class);
        skin.getTiledDrawable("heart-bg").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("heart-bg");
        skin.getTiledDrawable("heart").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("heart");
        
        style = skin.get("armor", ProgressBar.ProgressBarStyle.class);
        skin.getTiledDrawable("armor-bg").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("armor-bg");
        skin.getTiledDrawable("armor").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("armor");
        
        
        invenotryActiveSlotBck = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("inventar/barActiveBck.png"))));
        invenotrySlotBck = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("inventar/bar.png"))));
        
    }
}
