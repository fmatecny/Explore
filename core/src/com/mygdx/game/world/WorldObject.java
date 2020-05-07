/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Fery
 */
public abstract class WorldObject {
    
    
    
    protected Body createBodie(World world, int x, int y, Block b, Boolean blocked) {
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Set our body's starting position in the world
        bodyDef.position.set(x*Block.size+Block.size/2, y*Block.size+Block.size/2);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);
        
        // Create a circle shape and set its radius to 6
        PolygonShape square = new PolygonShape();
        square.setAsBox(Block.size/2.0f, Block.size/2.0f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = square;
        fixtureDef.isSensor = !blocked;
        //fixtureDef.density = 0.5f;
        //fixtureDef.friction = 0.5f;
        //fixtureDef.restitution = 0.5f;

        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
        Block bAsUserData = new Block(b);
        bAsUserData.blocked = blocked;
        body.setUserData(bAsUserData);

        square.dispose();
        
        return body; 
    }
    
    
    protected Body createBodie(World world, int x, int y, Block b) {
        return createBodie(world, x, y, b, true);
    }
}
