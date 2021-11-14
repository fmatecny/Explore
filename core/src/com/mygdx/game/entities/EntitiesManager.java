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

    //private int id = 0;
    
    private ArrayList<Villager> villagerList;
    private Golem golem;
   
    
    public EntitiesManager() {
        //id++;
        //golem = new Golem(id);
        villagerList = new ArrayList<>();
        
        Villager villager;
        for (int i = 1; i < 3; i++) {
            villager = new Villager(i);
            villager.setPosition(6+i, 20);
            villagerList.add(villager);
        }
    }
        
    private Villager getEntityById(int id){
        for (Villager villager : villagerList) {
            if (villager.id == id)
                return villager;
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
    }

    public void hitEntity(Body foo) {
        Villager v = getEntityById((int)foo.getUserData());
        if (v.health > 0)
            v.health -= 10;
        System.out.println("Entity id = " + v.id + "| health = " + v.health);
    }

    public void setPlayerPosition(Vector2 position) {
        for (Villager villager : villagerList) {
            if (villager.health < 100)
                villager.goToPosition(position);
        }
    }
}
