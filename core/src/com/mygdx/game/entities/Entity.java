/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.mygdx.game.MyContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.inventory.InventoryShop;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.AbstractMap;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public abstract class Entity implements EntityIfc{
    
    protected int id;
    protected int demage = 2;
    protected float speed = 1.0f;
    protected float powerOfImpuls = 0.2f;
    private final float dazedTimeout = 0.8f;
    private float currentDazedTimeout = 0;
    private boolean isDazed = false;
    private boolean gotHit = false;
    private boolean gaveHit = false;
    private Entity followedEntity = null;
    private Body followedBody = null;
    private long counter = 20;
    protected int timeMove = (int )(Math.random() * 300) + 10;
    private int timeToState = (int )(Math.random() * 300) + 100;
    
    private final float entityTextureSizeRatio = 197f/512f;
    private float heightInBlocks = 2f;   
    protected float texture_width;
    protected float texture_height;
    private float TEXTURE_SCALE = heightInBlocks/entityTextureSizeRatio;
    private float heightOffset = Block.size_in_meters/2f;

    protected Constants.typeOfMovement currentTOM = Constants.typeOfMovement.Stand;
    protected Constants.typeOfMovement lastTOM = Constants.typeOfMovement.Stand;
    protected Constants.typeOfDirection direction = Constants.typeOfDirection.Right;
   
    protected ArrayList<ArrayList<Animation<AtlasRegion>>> animations;
    protected float stateTime = 0;
    
    protected float health = 100.0f;
    protected Body b2body;
    private Vector2 housePos;
    private boolean goToHouse = false;
    private boolean spawned = true;
    
    private InventoryShop inventoryShop = null;
    private Constants.typeOfEntity typeOfEntity;

    protected boolean isQuadruped = false;
    
    
    public Entity(){}

    public Entity(int id, float x, float y, Constants.typeOfEntity typeOfEntity) {
        this.id = id;
        this.typeOfEntity = typeOfEntity;
        defineBody(x, y);
        housePos = new Vector2(x*Block.size_in_meters, y*Block.size_in_meters);
        System.out.println("id= " + id + " | HousePos = " + housePos);
        animations = MyAssetManager.instance.getEntityAnimations(this.typeOfEntity);
        setSize();
    }
    
    
    public Entity(int id, float x, float y, Constants.typeOfEntity typeOfEntity, float scale) {
        this.TEXTURE_SCALE = scale;
        this.id = id;
        this.typeOfEntity = typeOfEntity;
        defineBody(x, y);
        housePos = new Vector2(x*Block.size_in_meters, y*Block.size_in_meters);
        System.out.println("id= " + id + " | HousePos = " + housePos);
        animations = MyAssetManager.instance.getEntityAnimations(this.typeOfEntity);
        setSize();
    }
    
    public Entity(int id, float x, float y, Constants.typeOfEntity typeOfEntity, float scale, boolean isQuadruped) {
        isQuadruped = isQuadruped;
        this.TEXTURE_SCALE = scale;
        this.id = id;
        this.typeOfEntity = typeOfEntity;
        defineBody(x, y);
        housePos = new Vector2(x*Block.size_in_meters, y*Block.size_in_meters);
        System.out.println("id= " + id + " | HousePos = " + housePos);
        animations = MyAssetManager.instance.getEntityAnimations(this.typeOfEntity);
        setSize();
    }
    
    public void createInventoryShop(){
        inventoryShop = new InventoryShop(typeOfEntity);
    }
    
    public InventoryShop getInventoryShop(){
        return inventoryShop;
    }
    
    private void defineBody(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x*Block.size_in_meters, y*Block.size_in_meters);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = GameScreen.world.createBody(bdef);
        b2body.setUserData(id);
        b2body.setActive(false);

        FixtureDef fdef = new FixtureDef();
       
        CircleShape square = new CircleShape();
        square.setRadius(Block.size_in_meters*0.45f);

        fdef.shape = square;
        fdef.filter.categoryBits = Constants.ENTITY_BIT;
        fdef.filter.maskBits = Constants.BLOCK_BIT;
        b2body.createFixture(fdef);//.setUserData(this);
        
        square.setRadius(Block.size_in_meters*0.45f);
        if (isQuadruped)
            square.setPosition(new Vector2(Block.size_in_meters, 0));
        else
            square.setPosition(new Vector2(0, Block.size_in_meters));
        b2body.createFixture(fdef);

        EdgeShape right = new EdgeShape();
        right.set(new Vector2(0.15f, -0.1f), new Vector2(0.23f, -0.1f));
        //right.set(new Vector2(0.23f, -0.15f), new Vector2(0.23f, 0.5f));
        fdef.filter.categoryBits = Constants.ENTITY_RIGHT_BIT;
        fdef.shape = right;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        
        EdgeShape left = new EdgeShape();
        left.set(new Vector2(-0.15f, -0.1f), new Vector2(-0.23f, -0.1f));
        fdef.filter.categoryBits = Constants.ENTITY_LEFT_BIT;
        fdef.shape = left;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        
        square.dispose();
        right.dispose();
        left.dispose();
    }
    
    private void setSize(){
        float width = animations.get(0).get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).get(0).getKeyFrame(0).getRegionHeight();
        texture_width = ((Block.size_in_meters*TEXTURE_SCALE)/height)*width;
        texture_height = Block.size_in_meters*TEXTURE_SCALE;
    }
    
    @Override
    public void updatePosition(){
        if (currentTOM == Constants.typeOfMovement.Die || b2body.isActive() == false)
            return;
        
        if (b2body.getLinearVelocity().x == 0)
            currentTOM = Constants.typeOfMovement.Stand;
        
        if (gotHit || isDazed)
        {
            // if - continue
            // else - sleep for while due to hit
            if (currentDazedTimeout > dazedTimeout)
            {
                gotHit = false;
                isDazed = false;
                //gaveHit = false;
                currentDazedTimeout = 0;
            }
            else
            {
                currentDazedTimeout += Gdx.graphics.getDeltaTime();
                return;
            }
        }
        
        //speed = 2;
        /*if (Inputs.instance.run){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }*/
        if (goToHouse)
        {
            goToPosition(housePos.x, housePos.y);
            System.out.println("id= " + id + " | HousePos = " + housePos + "| X = " + this.b2body.getPosition().x);
        }
        else if (followedBody != null)
        {   
            goToPosition(followedBody.getPosition().x, followedBody.getPosition().y);
        }
        else
        {
            if (doRight() && b2body.getLinearVelocity().x <= speed &&
                b2body.getPosition().x < Constants.WIDTH_OF_MAP*Block.size_in_meters-Constants.W_IN_M/2)
            {
                b2body.applyLinearImpulse(new Vector2(powerOfImpuls, 0), b2body.getWorldCenter(), true);
                currentTOM = Constants.typeOfMovement.Walk;
                direction = Constants.typeOfDirection.Right;
            }

            if (doLeft() && b2body.getLinearVelocity().x >= -speed &&
                b2body.getPosition().x > Constants.W_IN_M/3)
            {
                b2body.applyLinearImpulse(new Vector2(-powerOfImpuls, 0), b2body.getWorldCenter(), true);
                currentTOM = Constants.typeOfMovement.Walk;
                direction = Constants.typeOfDirection.Left;
            }
            /*Array<Body> bodies = new Array<>();
            b2body.getWorld().getBodies(bodies);
            for (Body body : bodies) {
                if (body.getPosition().dst(b2body.getPosition()) < 0.2 && 
                        Math.abs(body.getPosition().y - b2body.getPosition().y) < 0.2 &&
                        body.getUserData() instanceof IntVector2) 
                {
                    IntVector2 a = (IntVector2)body.getUserData();
                    System.err.println(a.X + " | " + a.Y);
                }
            }*/
        }
        
        if (currentTOM == Constants.typeOfMovement.Walk && direction == Constants.typeOfDirection.Left)
        {
            for (AbstractMap.SimpleEntry<Integer, Integer> simpleEntry : MyContactListener.entityLeftContactArray) 
            {
                if (simpleEntry.getKey() == id && simpleEntry.getValue() > 0)
                {
                    b2body.applyLinearImpulse(new Vector2(0, 0.4f), b2body.getWorldCenter(), true);
                    currentTOM = Constants.typeOfMovement.Jump;
                }
            }
        }
        else if (currentTOM == Constants.typeOfMovement.Walk && direction == Constants.typeOfDirection.Right)
        {
            for (AbstractMap.SimpleEntry<Integer, Integer> simpleEntry : MyContactListener.entityRightContactArray) 
            {
                if (simpleEntry.getKey() == id && simpleEntry.getValue() > 0)
                {
                    b2body.applyLinearImpulse(new Vector2(0, 0.4f), b2body.getWorldCenter(), true);
                    currentTOM = Constants.typeOfMovement.Jump;
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if (!b2body.isActive())
            return;
        
        if (health > 0)
            counter++;
        
        if (health <= 0)
            currentTOM = Constants.typeOfMovement.Die;
        
        stateTime += Gdx.graphics.getDeltaTime();
        
        if (lastTOM != currentTOM)
            stateTime = 0;
        
        TextureRegion currentFrame;
        //TextureRegion o;
        // if slash is finished then wait - cant slash nonstop
        if (currentTOM == Constants.typeOfMovement.Slash && 
            animations.get(direction.ordinal()).get(currentTOM.ordinal()).isAnimationFinished(stateTime))
        {
            currentTOM = Constants.typeOfMovement.Stand;
            currentDazedTimeout = 0;
            gaveHit = true;
            isDazed = true;
        }
        
        if (currentTOM == Constants.typeOfMovement.Die || currentTOM == Constants.typeOfMovement.Slash)
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
        else
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
        //currentFrame.flip(currentFrame.isFlipX() != leftDirection, false);
        
        spriteBatch.draw(currentFrame,
                            b2body.getPosition().x - (texture_width/2),
                            b2body.getPosition().y -(texture_height/2.0f) + heightOffset,
                            texture_width,
                            texture_height);

        lastTOM = currentTOM;
    }
    
    protected boolean doLeft(){ 
        return counter < timeMove;
    }
    
    
    protected boolean doRight(){
        
        if (counter > 2*timeMove + 2*timeToState)
            counter = 0;
        
        if (counter > 2*timeMove + timeToState)
            return false;
        
        if (counter > timeMove + timeToState)
            return true;

        return false;
    }
    
    private void goToPosition(float x, float y){
        float positionOffset;
        
        if (goToHouse)
            positionOffset = 0.3f*Block.size_in_meters;
        else
            positionOffset = 1.5f*Block.size_in_meters;
        
        if (x-positionOffset > b2body.getPosition().x && b2body.getLinearVelocity().x <= speed){
            b2body.applyLinearImpulse(new Vector2(powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = Constants.typeOfMovement.Walk;
            direction = Constants.typeOfDirection.Right;
        }
        else if (x+positionOffset < b2body.getPosition().x && b2body.getLinearVelocity().x >= -speed){
            b2body.applyLinearImpulse(new Vector2(-powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = Constants.typeOfMovement.Walk;
            direction = Constants.typeOfDirection.Left;
        }
        else if (goToHouse && Math.abs(x - b2body.getPosition().x) < positionOffset)
            this.b2body.setActive(false);
        else if(goToHouse == false && (Math.abs(x - b2body.getPosition().x) < positionOffset) )
            currentTOM = Constants.typeOfMovement.Slash;

    }
    
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    protected float setSpeed(float speed) {
        return this.speed = speed;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    @Override
    public void setPosition(float x, float y){
        b2body.setTransform(x, y, 0);
    }
    
    void followEntity(Entity entity) {
        followedEntity = entity;
        if (entity == null)
            followedBody = null;
        else
            followedBody = followedEntity.b2body;
    } 
    
    @Override
    public void dispose(){
    }
    
    public void changeScale(float s){
        TEXTURE_SCALE = s;
        
        float width = animations.get(0).get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).get(0).getKeyFrame(0).getRegionHeight();
        texture_width = ((Block.size_in_meters*TEXTURE_SCALE)/height)*width;
        texture_height = Block.size_in_meters*TEXTURE_SCALE;
    }
    
    public void setHeightOffset(float offset) {
    	heightOffset = offset;
    }
    
    public void hit(int demage){
        if (health > 0)
            health -= demage;
        System.out.println("Entity id = " + id + "| health = " + health);
        gotHit = true;
        currentDazedTimeout = 0;
    }
    
    protected int getHitForce(){
        return demage;
    }

    public Entity getFollowedEntity() {
        return followedEntity;
    }

    public void followBody(Body followedBody) {
        followedEntity = null;
        this.followedBody = followedBody;
    }

    public Body getFollowedBody() {
        return followedBody;
    }
    
    public boolean isGaveHit() {
        return gaveHit;
    }

    public void gaveHitDone() {
        this.gaveHit = false;
    }
    
    public boolean IsAlive(){
        return health > 0;
    }
    
    public void goToHouse(){
        goToHouse = true;
        followedBody = null;
        followedEntity = null;
    }
    
    public boolean isInHouse(){
        return goToHouse && b2body.isAwake();
    }
    
    public void goOutOfHouse(){
        goToHouse = false;
        b2body.setActive(true);
    }
    
    public void setActive(boolean active){
        b2body.setActive(active && spawned && IsAlive());
    }
    
    public void setSpawned(boolean spawned){
        this.spawned = spawned;
    }
}


