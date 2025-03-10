/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.mygdx.game.MyContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.inventory.Inventory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.Inputs;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.AllTools;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Tool;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Player {

    private Inventory inventory;
    private HUD hud;
    private float health = 100.0f;
    
    private Constants.typeOfMovement currentTOM = Constants.typeOfMovement.Stand;
    private Constants.typeOfMovement lastTOM = Constants.typeOfMovement.Stand;
    private Constants.typeOfDirection direction = Constants.typeOfDirection.Right;
    private Constants.typeOfArmor typeOfArmor = Constants.typeOfArmor.Default;
   
    private ArrayList<ArrayList<Animation<AtlasRegion>>> animations;
    private float stateTime = 0;

    private final float SCALE = 6f;
    private float WIDTH;
    private float HEIGHT;
    
    private final float DEFAULT_SPEED = 2;//0.5f;
    private float speed = DEFAULT_SPEED;//2/GameScreen.PPM;
    private float powerOfImpuls = 0.2f;

    private boolean isFalling = false;
    private boolean isJumping = false;
    
    public Body b2body;
    //private Vector2 position =new Vector2();
    
    private Vector3 v3 = new Vector3();
    private boolean isShotFinished = true;
    
    public boolean isMining = false;
    
    private final int DEMAGE_BY_HAND = 5;

    public Player(Vector2 position) {
    	definePlayer(position);
        animations = MyAssetManager.instance.getPlayerAnimations(typeOfArmor);
        setSize();
        inventory = new Inventory();
        hud = new HUD();
    }

    private void setSize(){
        float width = animations.get(0).get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).get(0).getKeyFrame(0).getRegionHeight();
        WIDTH = ((Block.size*SCALE)/height)*width;
        HEIGHT = Block.size*SCALE;
    }
    
    private void definePlayer(Vector2 position){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = GameScreen.world.createBody(bdef);
        b2body.setFixedRotation(true);
        b2body.setUserData(0);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 5.0f;
        //fdef.restitution = 0.0f;
        fdef.density = 0.5f;
        /*PolygonShape square = new PolygonShape();
        square.setAsBox(Block.size, Block.size);*/
        CircleShape square = new CircleShape();
        square.setRadius(Block.size/2.0f + 2.0f/GameScreen.PPM);
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        //square.
        //fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        /*fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;*/

        fdef.shape = square;
        b2body.createFixture(fdef);//.setUserData(this);
        
        square.setRadius(Block.size/2.0f + 4.0f/GameScreen.PPM);
        square.setPosition(new Vector2(0, (Block.size/2.0f + 1.0f/GameScreen.PPM)*2.0f));
        b2body.createFixture(fdef);
        
        EdgeShape right = new EdgeShape();
        right.set(new Vector2(0.15f, -0.1f), new Vector2(0.23f, -0.1f));
        //right.set(new Vector2(0.23f, -0.15f), new Vector2(0.23f, 0.5f));
        fdef.filter.categoryBits = Constants.PLAYER_RIGHT_BIT;
        fdef.shape = right;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        
        EdgeShape left = new EdgeShape();
        left.set(new Vector2(-0.15f, -0.1f), new Vector2(-0.23f, -0.1f));
        fdef.filter.categoryBits = Constants.PLAYER_LEFT_BIT;
        fdef.shape = left;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        
        square.dispose();
        right.dispose();
        left.dispose();
    }

    
    public void updatePosition(OrthographicCamera camera){
        
        if (currentTOM == Constants.typeOfMovement.Die)
            return;
        
        if (b2body.getLinearVelocity().x == 0 && isShotFinished)
            currentTOM = Constants.typeOfMovement.Stand;
        
        speed = DEFAULT_SPEED;
        powerOfImpuls = 0.2f;
        
        if (MyContactListener.swim){
            speed /=4;
            powerOfImpuls = 0.03f;
            if (Inputs.instance.run)
                currentTOM = Constants.typeOfMovement.Stand;      
        }
        
        if (Inputs.instance.run && currentTOM != Constants.typeOfMovement.Stand){
            speed *= 3;
            //currentTOM = typeOfMovement.run;
        }
        
        if (Inputs.instance.mouseLeft && (isShotFinished || isMining))
        {
            currentTOM = Constants.typeOfMovement.Slash;
            isShotFinished = false;
        }
        else if (Inputs.instance.mouseLeft == false && isShotFinished == false)
            isShotFinished = true;
        
        if (Inputs.instance.right && b2body.getLinearVelocity().x <= speed && MyContactListener.blockOnRight==0 && 
            b2body.getPosition().x < Constants.WIDTH_OF_MAP*Block.size-Constants.W_IN_M/2)//stop player before end of map
        {
            b2body.applyLinearImpulse(new Vector2(powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = Constants.typeOfMovement.Walk;
        }
        
        if (Inputs.instance.left && b2body.getLinearVelocity().x >= -speed && MyContactListener.blockOnLeft==0 && 
            b2body.getPosition().x > Constants.W_IN_M/3) //stop player before end of map
        {
            b2body.applyLinearImpulse(new Vector2(-powerOfImpuls, 0), b2body.getWorldCenter(), true);
            currentTOM = Constants.typeOfMovement.Walk;
        }

        /*if (Inputs.instance.up && b2body.getLinearVelocity().y <= speed/5){
            b2body.applyLinearImpulse(new Vector2(0, powerOfImpuls), b2body.getWorldCenter(), true);
        }
        else{
            //if playerOnLadder
        b2body.setGravityScale(0);
        Vector2 vel = b2body.getLinearVelocity();
        vel.y = 0f;
        b2body.setLinearVelocity(vel);
        }*/
        
        //TODO fix gravity
        if (MyContactListener.climb > 0 && b2body.getLinearVelocity().x <= speed/10)
        {
            b2body.setGravityScale(0);
        }
        else if (b2body.getGravityScale() == 0)
        {
            b2body.setGravityScale(1.0f);
        }
        
        if (Inputs.instance.up && Math.abs(b2body.getLinearVelocity().x) <= speed/10 && b2body.getLinearVelocity().y <= speed/2 && MyContactListener.climb > 0)
        {
            b2body.applyLinearImpulse(new Vector2(0, powerOfImpuls), b2body.getWorldCenter(), true);
        }
        
        if (Inputs.instance.down && MyContactListener.swim){
            b2body.applyLinearImpulse(new Vector2(0, -powerOfImpuls), b2body.getWorldCenter(), true);
        }
        
        
        if (b2body.getLinearVelocity().y < 0 && isJumping )
            isFalling = true;
        
        if (b2body.getLinearVelocity().y == 0 && isFalling){
            isJumping = false;
            isFalling = false;
        }
        
        if (Inputs.instance.jump && !isJumping && (!isFalling || MyContactListener.swim)){
            b2body.applyLinearImpulse(new Vector2(0, 0.6f), b2body.getWorldCenter(), true);
            currentTOM = Constants.typeOfMovement.Jump;
            isJumping = true;
        }

        if (Inputs.instance.run && currentTOM != Constants.typeOfMovement.Stand){
            //speed *= 2;
            currentTOM = Constants.typeOfMovement.Run;
        }
        
        camera.unproject(v3.set(Inputs.instance.mouseX, Inputs.instance.mouseY, 0f));
        
        //System.out.println(v3.x + "|" + b2body.getPosition().x);
        if (v3.x < b2body.getPosition().x)
        	direction = Constants.typeOfDirection.Left;
        
        if (v3.x > b2body.getPosition().x)
        	direction = Constants.typeOfDirection.Right;
    }
    
    /*private int getScaleOfMovement(){
        
        switch (currentTOM) 
        {
            case stand: return 6;
            case walk:  return 2;
            case run:  return 2;
            case jump:  return 2;
            
            default:    throw new AssertionError();
        }
    
    }*/
    
    
    public void hit(int demage){
        //updatePosition(camera);
        health -= demage;
        hud.setHealth(health);
        if (health <= 0)
            currentTOM = Constants.typeOfMovement.Die;
    }
    
    public void draw(SpriteBatch spriteBatch){
        //hud.draw();
        typeOfArmor = inventory.getTypeOfArmor();
        animations = MyAssetManager.instance.getPlayerAnimations(typeOfArmor);
        
        if (currentTOM != Constants.typeOfMovement.Jump)
        {
            //backwalk
            if (((direction == Constants.typeOfDirection.Left && Inputs.instance.right) || 
                (direction == Constants.typeOfDirection.Right && Inputs.instance.left)) && IsAlive())
                animations.get(direction.ordinal()).get(currentTOM.ordinal()).setPlayMode(Animation.PlayMode.REVERSED );
            else
                animations.get(direction.ordinal()).get(currentTOM.ordinal()).setPlayMode(Animation.PlayMode.NORMAL );
            
            stateTime += Gdx.graphics.getDeltaTime();
        }
        
        if (lastTOM != currentTOM)
            stateTime = 0;
        
        TextureRegion currentFrame;
        if (currentTOM == Constants.typeOfMovement.Die || currentTOM == Constants.typeOfMovement.Slash)
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, isMining);
        else if ((direction == Constants.typeOfDirection.Right) && (currentTOM == Constants.typeOfMovement.Walk || currentTOM == Constants.typeOfMovement.Run))
        {//start form middle of movement - left arm animations = arm goes back and front, but right arm goes front and back
        	currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime+0.03f*6, true);
        }
        else if (currentTOM == Constants.typeOfMovement.Jump)
            currentFrame = animations.get(direction.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).getKeyFrame(0.0f);
        else
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
        
        //print tool
        if (direction == Constants.typeOfDirection.Left)
            printTool(spriteBatch);
        
        //print player
        spriteBatch.draw(currentFrame, b2body.getPosition().x - (WIDTH/2), b2body.getPosition().y -(HEIGHT/2.0f) + Block.size/2f, WIDTH, HEIGHT);
        
        //print tool
        if (direction == Constants.typeOfDirection.Right)
        {
        	printTool(spriteBatch);
        	printArm(spriteBatch);
        }

        
        if (currentTOM == Constants.typeOfMovement.Slash && isMining == false && animations.get(direction.ordinal()).get(currentTOM.ordinal()).isAnimationFinished(stateTime))
            currentTOM = Constants.typeOfMovement.Stand;
        
        lastTOM = currentTOM;
        
        /*if (Inputs.instance.showInventory)
            inventory.setAvatar(typeOfArmor);*/
        
    }
    
    
    public Tool getToolInUsed(){
        if (inventory.getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getTool() != null)
        {
            return inventory.getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getTool();
        }
        
        return null;
    }
    
    public int getHitForce(){
        Tool t = this.getToolInUsed();
        if (t == null)
            return DEMAGE_BY_HAND;
        else
            return t.damage;
            
    }
    
    private void printTool(SpriteBatch spriteBatch) {
        if (inventory.getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getTool() != null)
        {
            TextureRegion currentFrame;
            int id = inventory.getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getTool().id;
            if (currentTOM == Constants.typeOfMovement.Die) //|| currentTOM == Constants.typeOfMovement.Slash)
    		currentFrame = MyAssetManager.instance.getToolsAnimations(AllTools.typeOfTools.values()[id]).get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
            else if (currentTOM == Constants.typeOfMovement.Jump)
            	currentFrame = MyAssetManager.instance.getToolsAnimations(AllTools.typeOfTools.values()[id]).get(direction.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).getKeyFrame(stateTime, true);
            else
            	currentFrame = MyAssetManager.instance.getToolsAnimations(AllTools.typeOfTools.values()[id]).get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
    			
            spriteBatch.draw(currentFrame, b2body.getPosition().x - (WIDTH/2), b2body.getPosition().y -(HEIGHT/2.0f) + Block.size/2f, WIDTH, HEIGHT);
        }
    }
    
    private void printArm(SpriteBatch spriteBatch) {
    	TextureRegion currentFrame;
        if (inventory.getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getTool() != null)
        {
            if (currentTOM == Constants.typeOfMovement.Die)//|| currentTOM == Constants.typeOfMovement.Slash)
    		currentFrame = MyAssetManager.instance.getPlayerArmAnimations(typeOfArmor).get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
            else if (currentTOM == Constants.typeOfMovement.Jump)
                currentFrame = MyAssetManager.instance.getPlayerArmAnimations(typeOfArmor).get(direction.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).getKeyFrame(stateTime, true);
            else
            	currentFrame = MyAssetManager.instance.getPlayerArmAnimations(typeOfArmor).get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
    		
            spriteBatch.draw(currentFrame, b2body.getPosition().x - (WIDTH/2), b2body.getPosition().y -(HEIGHT/2.0f) + Block.size/2f, WIDTH, HEIGHT);
        }
    }
    
    
    
    public void dispose(){
        inventory.dispose();
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

    public Inventory getInventory() {
        return inventory;
    }
    
    public HUD getHud() {
        return hud;
    }
    
    public boolean IsAlive() {
        return health > 0;
    }
}
