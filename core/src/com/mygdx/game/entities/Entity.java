/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public abstract class Entity implements EntityIfc{
    
    protected int id;
    
    protected float WIDTH;
    protected float HEIGHT;
    private float SCALE = 4f;
    
    protected enum typeOfDirection { Left, Right};
    protected enum typeOfMovement { Stand, Walk, Run, Jump, Slash, Hit, Die};

    protected typeOfMovement currentTOM = typeOfMovement.Stand;
    protected typeOfMovement lastTOM = typeOfMovement.Stand;
    protected typeOfDirection direction = typeOfDirection.Right;
   
    protected ArrayList<ArrayList<Animation<AtlasRegion>>> animations;
    protected float stateTime = 0;
    
    protected float health = 100.0f;
    protected Body b2body;

    public Entity(){}
    
    public Entity(int id, float x, float y, String texturePathPrefix) {
        this.id = id;
        defineBody(x, y);
        animations = getAnimations(texturePathPrefix);
        setSize();
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
    
    private ArrayList getAnimations(String path){
        TextureAtlas[][] textureAtlas = loadTextures(path);
        
        ArrayList animations = new ArrayList<>(typeOfDirection.values().length);
        ArrayList animationsOneDir = new ArrayList<>(typeOfMovement.values().length);
        for (int i = 0; i < typeOfDirection.values().length; i++) 
        {
            for (int j = 0; j < typeOfMovement.values().length; j++) 
            { 
                if (j == typeOfMovement.Stand.ordinal())
                    animationsOneDir.add(j, new Animation<>(0.1f, textureAtlas[i][j].getRegions()));
                else
                    animationsOneDir.add(j, new Animation<>(0.03f, textureAtlas[i][j].getRegions()));  
            }
            animations.add(new ArrayList<Animation<AtlasRegion>>(animationsOneDir));
            animationsOneDir.clear();
        }
        animationsOneDir = null;
        
        return animations;
    }
    
    private TextureAtlas[][] loadTextures(String path){        
        TextureAtlas[][] textureAtlas = new TextureAtlas[typeOfDirection.values().length][typeOfMovement.values().length];
        String direction;
        String movement;
        for (int i = 0; i < typeOfDirection.values().length; i++) 
        {
            direction = "/" + typeOfDirection.values()[i].name();
            
            for (int j = 0; j < typeOfMovement.values().length; j++) 
            {            
                movement = "/" + typeOfMovement.values()[j].name();
                movement += "/" + typeOfMovement.values()[j].name().toLowerCase() + ".txt";
                
                try 
                {
                    textureAtlas[i][j] = new TextureAtlas("entities/" + path + direction + movement);                
                } catch (Exception e) {
                    try 
                    {
                        if (i == 0)
                            direction = "/" + typeOfDirection.values()[i+1].name();
                        else
                            direction = "/" + typeOfDirection.values()[i-1].name();
                        
                        textureAtlas[i][j] = new TextureAtlas("entities/" + path + direction + movement);

                        for (AtlasRegion region : textureAtlas[i][j].getRegions()) 
                        {
                            region.flip(true, false);
                        }

                    } catch (Exception ex) {
                        //textureAtlas[i][j] = new TextureAtlas("entities/player/Default" + direction + movement);
                        textureAtlas[i][j] = new TextureAtlas();
                        textureAtlas[i][j].addRegion("Empty", new TextureRegion(new Texture(new Pixmap(0, 0, Pixmap.Format.RGB888))));
                        System.err.println(ex);
                    }
                }
            }
        }
        return textureAtlas;
    }    
    
    private void setSize(){
        float width = animations.get(0).get(0).getKeyFrame(0).getRegionWidth();
        float height = animations.get(0).get(0).getKeyFrame(0).getRegionHeight();
        WIDTH = ((Block.size*SCALE)/height)*width;
        HEIGHT = Block.size*SCALE;
    }
    
    
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if (health <= 0)
            currentTOM = typeOfMovement.Die;
        
        stateTime += Gdx.graphics.getDeltaTime();
        
        if (lastTOM != currentTOM)
            stateTime = 0;
        
        TextureRegion currentFrame;
        //TextureRegion o;
        if (currentTOM == typeOfMovement.Die)
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, false);
        else
            currentFrame = animations.get(direction.ordinal()).get(currentTOM.ordinal()).getKeyFrame(stateTime, true);
        //currentFrame.flip(currentFrame.isFlipX() != leftDirection, false);
        spriteBatch.draw(currentFrame, b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 11.0f/GameScreen.PPM)*2.0f, WIDTH, HEIGHT);
        /*o = new TextureRegion(currentFrame);
        o.flip(true, false);
        spriteBatch.draw(o, b2body.getPosition().x - Block.size*2, b2body.getPosition().y -(Block.size/2.0f + 11.0f/GameScreen.PPM)*2.0f, WIDTH, HEIGHT);
        */
        lastTOM = currentTOM;
    }
    
    
    
    
    public float getX(){
        return b2body.getPosition().x;
    }
    
    public float getY(){
        return b2body.getPosition().y;
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
}


