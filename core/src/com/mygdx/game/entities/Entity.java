/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Block;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public abstract class Entity implements EntityIfc{
    
    protected int id;
    private int speed = 2;
    private Vector2 followPosition = null;
    private long counter = 20;
    private int timeMove = (int )(Math.random() * 20) + 5;
    private int timeToState = (int )(Math.random() * 100) + 100;
    
    protected float WIDTH;
    protected float HEIGHT;
    private float SCALE = 6f;
    private float heightOffset = Block.size/2f;

    protected Constants.typeOfMovement currentTOM = Constants.typeOfMovement.Stand;
    protected Constants.typeOfMovement lastTOM = Constants.typeOfMovement.Stand;
    protected Constants.typeOfDirection direction = Constants.typeOfDirection.Right;
   
    protected ArrayList<ArrayList<Animation<AtlasRegion>>> animations;
    protected float stateTime = 0;
    
    protected float health = 100.0f;
    protected Body b2body;

    public Entity(){}
    
    public Entity(int id, float x, float y, Constants.typeOfEntity typeOfEntity) {
        this.id = id;
        defineBody(x, y);
        animations = MyAssetManager.instance.getEntiryAnimations(typeOfEntity);
        setSize();
    }
    
    private void defineBody(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x*Block.size, y*Block.size);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = GameScreen.world.createBody(bdef);
        b2body.setUserData(id);
        b2body.setActive(false);

        FixtureDef fdef = new FixtureDef();
       
        CircleShape square = new CircleShape();
        square.setRadius(Block.size/2.0f + 2.0f/GameScreen.PPM);

        fdef.shape = square;
        fdef.filter.categoryBits = Constants.GOLEM_BIT;
        fdef.filter.maskBits = Constants.BLOCK_BIT;
        b2body.createFixture(fdef);//.setUserData(this);
        
        square.setRadius(Block.size/2.0f + 6.0f/GameScreen.PPM);
        square.setPosition(new Vector2(0, (Block.size/2.0f + 1.0f/GameScreen.PPM)*2.0f));
        b2body.createFixture(fdef);

        square.dispose();
    }
    
    private void setSize(){
        float width = animations.get(0).get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).get(0).getKeyFrame(0).getRegionHeight();
        WIDTH = ((Block.size*SCALE)/height)*width;
        HEIGHT = Block.size*SCALE;
    }
    
    @Override
    public void updatePosition(){
        if (currentTOM == Constants.typeOfMovement.Die || b2body.isActive() == false)
            return;
        
        if (b2body.getLinearVelocity().x == 0)
            currentTOM = Constants.typeOfMovement.Stand;
        
        speed = 2;
        /*if (Inputs.instance.run){
            speed *= 2;
            //currentTOM = typeOfMovement.run;
        }*/

        if (followPosition != null)
        {
            if (followPosition.x-Block.size > b2body.getPosition().x && b2body.getLinearVelocity().x <= speed){
                b2body.applyLinearImpulse(new Vector2(0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = Constants.typeOfMovement.Walk;
                direction = Constants.typeOfDirection.Right;
            }

            if (followPosition.x+Block.size < b2body.getPosition().x && b2body.getLinearVelocity().x >= -speed){
                b2body.applyLinearImpulse(new Vector2(-0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = Constants.typeOfMovement.Walk;
                direction = Constants.typeOfDirection.Left;
            }
        }
        else
        {
            if (doRight() && b2body.getLinearVelocity().x <= speed){
                b2body.applyLinearImpulse(new Vector2(0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = Constants.typeOfMovement.Walk;
                direction = Constants.typeOfDirection.Right;
            }

            if (doLeft() && b2body.getLinearVelocity().x >= -speed){
                b2body.applyLinearImpulse(new Vector2(-0.2f, 0), b2body.getWorldCenter(), true);
                currentTOM = Constants.typeOfMovement.Walk;
                direction = Constants.typeOfDirection.Left;
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
        if (currentTOM == Constants.typeOfMovement.Die)
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
        else
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
        //currentFrame.flip(currentFrame.isFlipX() != leftDirection, false);
        spriteBatch.draw(currentFrame, b2body.getPosition().x - (WIDTH/2), b2body.getPosition().y -(HEIGHT/2.0f) + heightOffset, WIDTH, HEIGHT);
        /*o = new TextureRegion(currentFrame);
        o.flip(true, false);
        spriteBatch.draw(o, b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 11.0f/GameScreen.PPM)*2.0f, WIDTH, HEIGHT);
        */
        lastTOM = currentTOM;
    }
    
    private boolean doLeft(){ 
        return counter < timeMove;
    }
    
    
    private boolean doRight(){
        
        if (counter > 2*timeMove + 2*timeToState)
            counter = 0;
        
        if (counter > 2*timeMove + timeToState)
            return false;
        
        if (counter > timeMove + timeToState)
            return true;

        return false;
    }
    
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
    }
    
    public int getSpeed() {
        return speed;
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
    
    void goToPosition(Vector2 position) {
        followPosition = position;
    } 
    
    @Override
    public void dispose(){
    }
    
    public void changeScale(float s){
        SCALE = s;
        
        float width = animations.get(0).get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).get(0).getKeyFrame(0).getRegionHeight();
        WIDTH = ((Block.size*SCALE)/height)*width;
        HEIGHT = Block.size*SCALE;
    }
    
    public void setHeightOffset(float offset) {
    	heightOffset = offset;
    }
    
}


