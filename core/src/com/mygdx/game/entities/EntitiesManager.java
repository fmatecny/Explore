/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class EntitiesManager {

    private int id = 1;
    
    private ArrayList<Villager> villagerList;
    private ArrayList<Smith> smithList;
    private ArrayList<Golem> golemList;
   
    
    public EntitiesManager() {
        villagerList = new ArrayList<>();
        smithList = new ArrayList<>();
        golemList = new ArrayList<>();
        
        Villager villager;
        for (; id < 3; id++) {
            villager = new Villager(id, 6+id, 20);
            //villager.setPosition(6+id, 20);
            villagerList.add(villager);
        }
        
        for (; id < 4; id++) {
            golemList.add(new Golem(id, 10.0f, 20.0f));
        }
        
        for (; id < 5; id++) {
            smithList.add(new Smith(id, 15.0f, 20.0f));
        }
    }
        
    private Entity getEntityById(int id){
        for (Villager villager : villagerList) {
            if (villager.id == id)
                return villager;
        }
        
        for (Smith smith : smithList) {
            if (smith.id == id)
                return smith;
        }
        
        for (Golem golem : golemList) {
            if (golem.id == id)
                return golem;
        }

        return null;
    }

    public void updatePosition() {
        for (Villager villager : villagerList) {
            villager.updatePosition();
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (Villager villager : villagerList) {
            villager.draw(spriteBatch);
        }
        
        for (Smith smith : smithList) {
            smith.draw(spriteBatch);
        }
        
        for (Golem golem : golemList) {
            golem.draw(spriteBatch);
        }
    }

    public void hitEntity(Body body) {
        Entity e = getEntityById((int)body.getUserData());
        if (e.health > 0)
            e.health -= 10;
        System.out.println("Entity id = " + e.id + "| health = " + e.health);
    }

    public void setPlayerPosition(Vector2 position) {
        for (Villager villager : villagerList) {
            if (villager.health < 100)
                villager.goToPosition(position);
        }
    }
    
    public void dispose(){
        for (Villager villager : villagerList) {
            villager.dispose();
        }
        for (Smith smith : smithList) {
            smith.dispose();
        }
        for (Golem golem : golemList) {
            golem.dispose();
        }
    }
}
