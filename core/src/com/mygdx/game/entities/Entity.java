/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fery
 */
public abstract class Entity implements EntityIfc{
    
    protected int id;
    private String texturePathPrefix = "";
    
    protected float WIDTH;
    protected float HEIGHT;
    private final float SCALE = 4f;
    
    protected enum typeOfMovement { stand, walk, run, jump, shot, hit, die};
    protected typeOfMovement currentTOM = typeOfMovement.stand;
    protected typeOfMovement lastTOM = typeOfMovement.stand;
   
    protected TextureAtlas[] textureAtlas;
    protected List<Animation<AtlasRegion>> animations;
    protected float stateTime = 0;
    
    protected float health = 100.0f;
    protected Body b2body;

    public Entity(int id, float x, float y, String texturePathPrefix) {
        this.id = id;
        this.texturePathPrefix = texturePathPrefix;
        defineBody(x, y);
        loadTextures();
        createAnimations(); 
    }
    
    private void defineBody(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = GameScreen.world.createBody(bdef);
        b2body.setUserData(id);

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
    
    
    private void loadTextures(){
        if (texturePathPrefix != "")
            texturePathPrefix = "/" + texturePathPrefix;
        
        textureAtlas = new TextureAtlas[typeOfMovement.values().length];
        textureAtlas[typeOfMovement.stand.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Stand/stand.txt");
        textureAtlas[typeOfMovement.walk.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Walk/walk.txt");
        textureAtlas[typeOfMovement.run.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Run/run.txt");
        textureAtlas[typeOfMovement.jump.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Jump/jump.txt");
        textureAtlas[typeOfMovement.shot.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Shot/shot.txt");
        textureAtlas[typeOfMovement.hit.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Hit/hit.txt");
        textureAtlas[typeOfMovement.die.ordinal()] = new TextureAtlas("entities" + texturePathPrefix + "/Die/die.txt");   
    }
    
    private void createAnimations() {
        animations = new ArrayList<>(typeOfMovement.values().length);
        
        for (int i = 0; i < typeOfMovement.values().length; i++) 
        { 
            if (i == typeOfMovement.stand.ordinal())
                animations.add(i, new Animation<>(0.1f, textureAtlas[i].getRegions()));
            else
                animations.add(i, new Animation<>(0.03f, textureAtlas[i].getRegions()));  
        }

        float width = animations.get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).getKeyFrame(0).getRegionHeight();
        WIDTH = ((Block.size*SCALE)/height)*width;
        HEIGHT = Block.size*SCALE;
    }
    
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
    }
}


