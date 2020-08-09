/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.water.Water;
import java.util.AbstractMap;

public class MyContactListener implements ContactListener {

    
    public static boolean swim = false;
    public static int blockOnLeft = 0;
    public static int blockOnRight = 0;

	@Override
	public void beginContact(Contact contact) {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            
            int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
            
            if(fixtureA.getBody().getUserData() instanceof Water && fixtureB.getBody().getType() == BodyDef.BodyType.DynamicBody){
                Water water = (Water) fixtureA.getBody().getUserData();
                //water.getFixturePairs().add(new Pair<Fixture, Fixture>(fixtureA, fixtureB));
                water.getFixturePairs().add(new AbstractMap.SimpleEntry<Fixture, Fixture>(fixtureA, fixtureB));
                if (fixtureB.isSensor()){
                   swim = true;
                }
            }
            else if(fixtureB.getBody().getUserData() instanceof Water && fixtureA.getBody().getType() == BodyDef.BodyType.DynamicBody){
                Water water = (Water) fixtureB.getBody().getUserData();
                water.getFixturePairs().add(new AbstractMap.SimpleEntry<Fixture, Fixture>(fixtureB, fixtureA));
                if (fixtureA.isSensor()){
                    swim = true;
                }
            }
            else if( cDef == (Constants.PLAYER_LEFT_BIT | Constants.BLOCK_BIT) ){
                blockOnLeft++;
            }
            else if ( cDef == (Constants.PLAYER_RIGHT_BIT | Constants.BLOCK_BIT) ){
                blockOnRight++;
            }
            //System.out.println(blockOnRight); 
	}

	@Override
	public void endContact(Contact contact) {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();

            int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
            
            if(fixtureA.getBody().getUserData() instanceof Water && fixtureB.getBody().getType() == BodyDef.BodyType.DynamicBody){
                    Water water = (Water) fixtureA.getBody().getUserData();
                     water.getFixturePairs().remove(new AbstractMap.SimpleEntry<Fixture, Fixture>(fixtureA, fixtureB));
                     if (fixtureB.isSensor()){
                         swim = false;
                     }
            }
            else if(fixtureB.getBody().getUserData() instanceof Water && fixtureA.getBody().getType() == BodyDef.BodyType.DynamicBody){
                    Water water = (Water) fixtureB.getBody().getUserData();
                     water.getFixturePairs().add(new AbstractMap.SimpleEntry<Fixture, Fixture>(fixtureA, fixtureB));
                     if (fixtureB.isSensor()){
                         swim = false;
                     }
            }
            else if( cDef == (Constants.PLAYER_LEFT_BIT | Constants.BLOCK_BIT) ){
                blockOnLeft--;
            }
            else if ( cDef == (Constants.PLAYER_RIGHT_BIT | Constants.BLOCK_BIT) ){
                blockOnRight--;
            }
            //System.out.println(blockOnRight);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}

