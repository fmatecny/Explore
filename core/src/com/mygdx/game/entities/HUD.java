/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public class HUD {
    
    private ProgressBar healthBar;
    private ProgressBar hungerBar;
    private Table mock;
    private Stage stagePlayerHud;
    
    private float hungryTime = 0;
    private float maxHungryTime = 5;
    private float multiplyHunger = 1;
    private float noFoodTime = 0;
    private float maxNoFoodTime = 100;

    public HUD() {
        
        stagePlayerHud = new Stage(new FitViewport(MyGdxGame.width,MyGdxGame.height,new OrthographicCamera()),new SpriteBatch());
        
        mock = new Table(Skins.skin);
        mock.setFillParent(true);
        mock.setDebug(true);
        mock.center().bottom().padBottom(MyGdxGame.height*0.15f);

        healthBar = new ProgressBar(0.0f, 100, .1f, false, Skins.skin, "health");
        healthBar.setValue(100.0f);
        mock.add(healthBar).width(180.0f).height(18.0f);

        hungerBar = new ProgressBar(0.0f, 100, .1f, false, Skins.skin, "hunger");
        hungerBar.setValue(0.0f);
        mock.add(hungerBar).width(180.0f).height(18.0f).padLeft(15.0f);
/*
        mock.row();
        Stack stack = new Stack();
        ProgressBar xpBar = new ProgressBar(0.0f, 100, .1f, false, Skins.skin, "xp");
        xpBar.setValue(50.0f);
        stack.add(xpBar);
        Label label = new Label("25", Skins.skin, "xp");
        label.setAlignment(Align.center);
        stack.add(label);
        mock.add(stack).width(378.0f).height(9.0f).colspan(2).padTop(20.0f).padBottom(50.0f);
    
    
    
        mock.row();
        armorBar = new ProgressBar(0.0f, 100, .1f, false, Skins.skin, "armor");
        armorBar.setValue(10.0f);
        armorBar.setSize(180, 18);
        armorBar.setPosition(0, 400);
        mock.add(armorBar).width(180.0f).height(18.0f).padBottom(10.0f);*/
        stagePlayerHud.addActor(mock);
    }
    
    
    public void draw(){
        noFoodTime += (Gdx.graphics.getDeltaTime() * multiplyHunger);
        
        if (noFoodTime >= maxNoFoodTime)
        {
            noFoodTime = 0;
            decreaseHunger(5);
        }
        
        if (isHungry())
            hungryTime += Gdx.graphics.getDeltaTime();
        
        if (hungryTime >= maxHungryTime)
        {
            hungryTime = 0;
            decreaseHealth(5);
        }

        stagePlayerHud.draw();
    }

    public void setHealth(float health) {
        if (health < 0)
            this.healthBar.setValue(0);
        else
            this.healthBar.setValue(health);
    }

    public void decreaseHunger(float amount) {
        float hungerValue = this.hungerBar.getValue()+amount;
        if (hungerValue > 100)
            hungerValue = 100;
        
        this.hungerBar.setValue(hungerValue);
    }

    public float getHungerValue() {
        return 100.0f - hungerBar.getValue();
    }
    
    public float getHealthValue(){
        return healthBar.getValue();
    }
    
    private boolean isHungry(){
        return hungerBar.getValue() == hungerBar.getMaxValue();
    }
    
    public void decreaseHealth(float amount) {
        float healthValue = this.healthBar.getValue()-amount;
        if (healthValue < 0)
            healthValue = 0;
        
        this.healthBar.setValue(healthValue);
    }
    
    public void setMultiplyHunger(float mul){
        multiplyHunger = mul;
    }
    
    
}
