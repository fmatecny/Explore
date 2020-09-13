/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.Player;

/**
 *
 * @author Fery
 */
public class Shaders {
    
    private ShaderProgram basic;
    private ShaderProgram vignette;
    Vector3 v3 = new Vector3();
    public Shaders() {
        ShaderProgram.pedantic = false;
        
        basic = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"),Gdx.files.internal("shaders/passthrough.fsh"));
        vignette = new ShaderProgram(Gdx.files.internal("shaders/vignette.vsh"),Gdx.files.internal("shaders/vignette.fsh"));
        
        System.out.println(basic.isCompiled() ? "basic compiled" : basic.getLog());
        System.out.println(vignette.isCompiled() ? "vignette compiled" : vignette.getLog());
    }
    
    public void setDefaultShader(SpriteBatch spriteBatch)
    {
            spriteBatch.setShader(basic);
    }
    
    public void setVignetteShader(SpriteBatch spriteBatch)
    {
            spriteBatch.setShader(vignette);
    }
    
    public void updateValues(int width, int height){
        //System.out.println(width + "|" + height);
        vignette.begin();
        vignette.setUniformf("u_resolution",width, height);
        vignette.end();
    }
    
    public void updatePlayerPos(Player player, Vector2 cam, OrthographicCamera camera){
        //System.out.println(player.getX()*GameScreen.PPM - (cam.x*GameScreen.PPM - MyGdxGame.width/2) + "|" + player.getY()*GameScreen.PPM);
        
        camera.project(v3.set(player.getX(), player.getY(), 0f));
        //System.out.println(v3.x + "|||" + v3.y);
        
        vignette.begin();
        vignette.setUniformf("u_playerPos", v3.x, v3.y);
        vignette.end();
        

        
    }
    
    public void dispose(){
        basic.dispose();
        vignette.dispose();
    }
    
}
