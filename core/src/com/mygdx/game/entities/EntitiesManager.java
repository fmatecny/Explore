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
import com.mygdx.game.world.Block;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class EntitiesManager {

    private int id = 1;
    
    private Player player;
    
    private ArrayList<ArrayList<Entity>> entityList;
    
    private ArrayList<Entity> villagerList;
    private ArrayList<Entity> girlList;
    private ArrayList<Entity> smithList;
    private ArrayList<Entity> golemList;
    private ArrayList<Entity> knightList;
   
    
    public EntitiesManager(ArrayList<IntVector2> housesPos, ArrayList<IntVector2> knightPositions) {
        entityList = new ArrayList<ArrayList<Entity>>();
        villagerList = new ArrayList<>();
        girlList = new ArrayList<>();
        smithList = new ArrayList<>();
        golemList = new ArrayList<>();
        knightList = new ArrayList<>();
        
        for (int i = 0; i < housesPos.size(); i++) 
        {
            //generate smith
            if ((int )(Math.random() * 100) > 80 )
            {
                smithList.add(new Smith(id, housesPos.get(0).X+6, housesPos.get(0).Y));
                id++;
            }
            //generate villager
            else
            {
                villagerList.add(new Villager(id, housesPos.get(i).X, housesPos.get(i).Y));
                id++;
                if ((int )(Math.random() * 100) > 60 )
                {
                    girlList.add(new Girl(id, housesPos.get(i).X, housesPos.get(i).Y));
                    id++;
                } 
            }
        }

        Golem golem;
        for (int i = 0; i < 3; i++) 
        {
            golem = new Golem(id, 0f, 0f);
            golem.b2body.setActive(false);
            golemList.add(golem);
            id++;
        }
        
        for (IntVector2 knightPos : knightPositions) {
            knightList.add(new Knight(id, knightPos.X, knightPos.Y));
            id++;
        }
        
        
        entityList.add(Constants.typeOfEntity.villager.ordinal(), villagerList);
        entityList.add(Constants.typeOfEntity.girl.ordinal(), girlList);
        entityList.add(Constants.typeOfEntity.smith.ordinal(), smithList);
        entityList.add(Constants.typeOfEntity.golem.ordinal(), golemList);
        entityList.add(Constants.typeOfEntity.knight.ordinal(), knightList);
    }
        
    private Entity getEntityById(int id){
        for (ArrayList<Entity> entities : entityList) 
        {
            for (Entity entity : entities) 
            {
                if (entity.id == id)
                    return entity;
            }
        }

        return null;
    }

    public void updatePosition(Vector2 cam) {
        for (ArrayList<Entity> entities : entityList) 
        {
            for (Entity entity : entities) 
            {
                //if entity is out of screen -> freeze them 
                //if entoty is on screen -> set to active (unfreeze them)
                if (isOutOfScreen(cam, entity))
                {
                    entity.setActive(false);
                }
                else
                {
                    entity.setActive(true);
                    entity.updatePosition();
                }
            }
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (ArrayList<Entity> entities : entityList) 
        {
            for (Entity entity : entities) 
            {
                entity.draw(spriteBatch);
            }
        }
    }

    public void playerHitEntity(Body hitEntityBody){
        if (hitEntityBody.getUserData() != null)
        {
            System.out.println((int)hitEntityBody.getUserData());
            if ((int)hitEntityBody.getUserData() != 0)
            {
                Entity e = getEntityById((int)hitEntityBody.getUserData());
                e.hit(player.getHitForce());
                e.followBody(player.b2body);
            }
        }
    }
    
    public void hitEntity(Entity hitEntity, Entity attackEntity) {
        //fixture.getBody().getUserData() instanceof IntVector2
        
        if (hitEntity.b2body.getUserData() != null)
        {
            System.out.println((int)hitEntity.b2body.getUserData());
            if ((int)hitEntity.b2body.getUserData() != 0)
            {
                hitEntity.hit(attackEntity.getHitForce());
                hitEntity.followEntity(attackEntity);
                if (hitEntity.IsAlive() == false)
                    attackEntity.followEntity(null);
            }
        }
    }
    
    public void manageHit() {
        for (ArrayList<Entity> entities : entityList) 
        {
            for (Entity entity : entities) 
            {
                if (entity.getFollowedBody() != null && entity.isGaveHit() && entity.IsAlive() && entity.b2body.isActive())
                {
                    if (entity.getFollowedEntity() != null)
                    {
                        //System.out.println("managegit");
                        this.hitEntity(entity.getFollowedEntity(), entity);
                        entity.gaveHitDone();
                    }
                    else
                    {
                        if ((int)entity.getFollowedBody().getUserData() != 0)
                        {
                            Entity e = getEntityById((int)entity.getFollowedBody().getUserData());
                            this.hitEntity(e, entity);
                            entity.gaveHitDone();
                        }
                        else
                        {
                            player.hit(entity.getHitForce());
                            entity.gaveHitDone();
                        }
                    }
                }
            }
        }
    }
    
    public void findNearestEntity() {    
        Entity nearestEntity = null;
        float minDst;
        float dst = 1000;
        boolean followPlayer = false;
        //if distance between player and golem is 11blocks and less - golem will follow player
        for (Entity golem : golemList) 
        {
            minDst = 50*Block.size;
            if (golem.b2body.getPosition().dst(player.b2body.getPosition()) <= 11*Block.size && player.IsAlive())
            {
                golem.followBody(player.b2body);
                followPlayer = true;   
            }

            for (ArrayList<Entity> entities : entityList) 
            {
                for (Entity entity : entities) 
                {
                    if (entity instanceof Golem == false) //except golem
                    {
                        dst = golem.b2body.getPosition().dst(entity.b2body.getPosition());
                        if (dst <= 11*Block.size && dst < minDst && entity.IsAlive() && entity.b2body.isActive())
                        {
                            if (followPlayer == false)
                            {
                                nearestEntity = entity;
                                minDst = dst;
                            }
                            entity.goToHouse();
                        }
                        else if (dst > 15*Block.size && entity.IsAlive() && entity.isInHouse())
                        {
                            entity.goOutOfHouse();
                        }
                    }
                }
            }
            if (followPlayer == false)
                golem.followEntity(nearestEntity);
        }
    }
    
    
    private boolean isOutOfScreen(Vector2 cam, Entity entity){
        return ((cam.x - Constants.W_IN_M/2 - Constants.ENTITY_SCREEN_OFFSET > entity.b2body.getPosition().x) ||
                (cam.x + Constants.W_IN_M/2 + Constants.ENTITY_SCREEN_OFFSET <  entity.b2body.getPosition().x));
    }
    
    
    public void dispose(){
        for (ArrayList<Entity> entities : entityList) 
        {
            for (Entity entity : entities) 
            {
                entity.dispose();
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void spawnGolems(Vector2 position){
        if (position == null)
            return;

        for (Entity golem : golemList) 
        {
            if (golem.b2body.isActive() == false)
            {
                golem.setPosition(position.x, position.y);
                golem.b2body.setActive(true);
                golem.setSpawned(true);
                return;
            }
        }
    
    }
    
    public void despawnGolems(){
        for (Entity golem : golemList) {
            golem.b2body.setActive(false);
            golem.setSpawned(false);
            golem.health = 100;
        }
    
    }
    
}
