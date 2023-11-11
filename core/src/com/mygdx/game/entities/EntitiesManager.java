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
import com.mygdx.game.inventory.InventoryShop;
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
    private ArrayList<Entity> knightList;
    private ArrayList<Entity> hostileList;
    private King king;
   
    
    public EntitiesManager(ArrayList<IntVector2> housesPos, ArrayList<IntVector2> knightPositions, IntVector2 kingPos) {
        entityList = new ArrayList<ArrayList<Entity>>();
        villagerList = new ArrayList<>();
        girlList = new ArrayList<>();
        smithList = new ArrayList<>();
        hostileList = new ArrayList<>();
        knightList = new ArrayList<>();
        
        for (int i = 0; i < housesPos.size(); i++) 
        {
            //generate smith
            if ((int )(Math.random() * 100) > 90 )
            {
                smithList.add(new Smith(id, housesPos.get(0).X+6, housesPos.get(0).Y));
                id++;
            }
            //generate villager
            else
            {
                villagerList.add(new Villager(id, housesPos.get(i).X, housesPos.get(i).Y));
                id++;
                if ((int )(Math.random() * 100) > 80 )
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
            hostileList.add(golem);
            id++;
        }
        
        Skeleton skeleton;
        for (int i = 0; i < 3; i++) 
        {
            skeleton = new Skeleton(id, 0f, 0f);
            skeleton.b2body.setActive(false);
            hostileList.add(skeleton);
            id++;
        }
        
        for (IntVector2 knightPos : knightPositions) {
            knightList.add(new Knight(id, knightPos.X, knightPos.Y));
            id++;
        }
        king = new King(id, kingPos.X, kingPos.Y);
        knightList.add(king);
        
        entityList.add(villagerList);
        entityList.add(girlList);
        entityList.add(smithList);
        entityList.add(hostileList);
        entityList.add(knightList);     
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

    public InventoryShop getEntityShopById(int id){
        return getEntityById(id).getInventoryShop();
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
        for (Entity hostile : hostileList) 
        {
            minDst = 50*Block.size;
            if (hostile.b2body.getPosition().dst(player.b2body.getPosition()) <= 11*Block.size && player.IsAlive())
            {
                hostile.followBody(player.b2body);
                followPlayer = true;   
            }

            for (ArrayList<Entity> entities : entityList) 
            {
                for (Entity entity : entities) 
                {
                    if (entity instanceof Golem == false && entity instanceof Skeleton == false) //except hostile mobs
                    {
                        dst = hostile.b2body.getPosition().dst(entity.b2body.getPosition());
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
                hostile.followEntity(nearestEntity);
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
    
    public void spawnHostileEntity(Vector2 position){
        if (position == null)
            return;

        for (Entity hostile : hostileList) 
        {
            if (hostile.b2body.isActive() == false)
            {
                hostile.setPosition(position.x, position.y);
                hostile.b2body.setActive(true);
                hostile.setSpawned(true);
                return;
            }
        }
    }
    
    
    public void despawnHostileEntity(){
        for (Entity hostile : hostileList) {
            hostile.b2body.setActive(false);
            hostile.setSpawned(false);
            hostile.health = 100;
        }
    }
    
}
