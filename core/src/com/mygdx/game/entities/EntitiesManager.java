/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Constants;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.Block;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class EntitiesManager {

    private int id = 1;
    
    private ArrayList<Villager> villagerList;
    private ArrayList<Girl> girlList;
    private ArrayList<Smith> smithList;
    private ArrayList<Golem> golemList;
   
    
    public EntitiesManager(ArrayList<IntVector2> housesPos) {
        villagerList = new ArrayList<>();
        girlList = new ArrayList<>();
        smithList = new ArrayList<>();
        golemList = new ArrayList<>();
        
        Villager villager;
        Girl girl;
        for (int i = 0; i < 1/*housesPos.size()*/; i++) {
            villager = new Villager(id, housesPos.get(i).X, housesPos.get(i).Y);
            villagerList.add(villager);
            id++;
            if ((int )(Math.random() * 100) > 60 )
            {
                girl = new Girl(id, housesPos.get(i).X+2, housesPos.get(i).Y);
                girlList.add(girl);
                id++;
            }  
        }

        
        //for (; id < 4; id++) {
            golemList.add(new Golem(id, housesPos.get(0).X+4, housesPos.get(0).Y));
        //}
        
        //for (; id < 5; id++) {
            smithList.add(new Smith(id, housesPos.get(0).X+6, housesPos.get(0).Y));
        //}
    }
        
    private Entity getEntityById(int id){
        for (Villager villager : villagerList) {
            if (villager.id == id)
                return villager;
        }
        
        for (Girl girl : girlList) {
            if (girl.id == id)
                return girl;
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

    public void updatePosition(Vector2 cam) {
        for (Villager villager : villagerList) 
        {
            //if entity is out of screen -> freeze them 
            //if entoty is on screen -> set to active (unfreeze them)
            if (isOutOfScreen(cam, villager))
            {
                villager.b2body.setActive(false);
            }
            else
            {
                villager.b2body.setActive(true);
                villager.updatePosition();
            }
        }
        
        for (Girl girl : girlList) 
        {
            //if entity is out of screen -> freeze them 
            //if entoty is on screen -> set to active (unfreeze them)
            if (isOutOfScreen(cam, girl))
            {
                girl.b2body.setActive(false);
            }
            else
            {
                girl.b2body.setActive(true);
                girl.updatePosition();
            }
        }
        for (Golem golem : golemList) 
        {
            //if entity is out of screen -> freeze them 
            //if entoty is on screen -> set to active (unfreeze them)
            if (isOutOfScreen(cam, golem))
            {
                golem.b2body.setActive(false);
            }
            else
            {
                golem.b2body.setActive(true);
                golem.updatePosition();
            }
        }
        for (Smith smith : smithList) 
        {
            //if entity is out of screen -> freeze them 
            //if entoty is on screen -> set to active (unfreeze them)
            if (isOutOfScreen(cam, smith))
            {
                smith.b2body.setActive(false);
            }
            else
            {
                smith.b2body.setActive(true);
                smith.updatePosition();
            }
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (Villager villager : villagerList) {
            villager.draw(spriteBatch);
        }
        
        for (Girl girl : girlList) {
            girl.draw(spriteBatch);
        }
        
        for (Smith smith : smithList) {
            smith.draw(spriteBatch);
        }
        
        for (Golem golem : golemList) {
            golem.draw(spriteBatch);
        }
    }

    public void hitEntity(Body body, Vector2 position) {
        Entity e = getEntityById((int)body.getUserData());
        if (e.health > 0)
            e.health -= 10;
        System.out.println("Entity id = " + e.id + "| health = " + e.health);
        
        e.goToPosition(position);
    }

    public void setPlayerPosition(Vector2 position) {    
        //if distance between player and golem is 11blocks and less - golem will follow player
        for (Golem golem : golemList) {
            if (golem.b2body.getPosition().dst(position) <= 11*Block.size)
                golem.goToPosition(position);
            else
                golem.goToPosition(null);
        }
    }
    
    private boolean isOutOfScreen(Vector2 cam, Entity entity){
        return ((cam.x - Constants.W_IN_M/2 - Constants.ENTITY_SCREEN_OFFSET > entity.b2body.getPosition().x) ||
                (cam.x + Constants.W_IN_M/2 + Constants.ENTITY_SCREEN_OFFSET <  entity.b2body.getPosition().x));
    }
    
    
    public void dispose(){
        for (Villager villager : villagerList) {
            villager.dispose();
        }
        for (Girl girl : girlList) {
            girl.dispose();
        }
        for (Smith smith : smithList) {
            smith.dispose();
        }
        for (Golem golem : golemList) {
            golem.dispose();
        }
    }
}
