/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;

/**
 *
 * @author Fery
 */
public abstract class EntityBipedal extends Entity{
    
    public EntityBipedal(Builder b){
        super(b);
        if (heightInBlocks == 0)
            heightInBlocks = 2f;
        if (entityTextureSizeRatio == 0)
            entityTextureSizeRatio = 197f/512f;
        setSize();
        this.defineBody(b.getX(), b.getY());
        
        timeMove = (int )(Math.random() * 300) + 10;
        timeToState = (int )(Math.random() * 500) + 500;
    }
    
    @Override
    protected void defineBody(float x, float y){
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
    
}
