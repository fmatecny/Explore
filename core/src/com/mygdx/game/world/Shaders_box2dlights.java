/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Constants;
import com.mygdx.game.entities.Player;
import com.mygdx.game.screens.GameScreen;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Shaders_box2dlights {

    private RayHandler rayHandler;
    private DirectionalLight sun;
    private PointLight myLight;
    
    //private PointLight torchLight;
    private ArrayList<PointLight> torchLightList;
    
    private Color color;
    private final float lightSoftnessLength_sun = 0.5f;
    private final float lightSoftnessLength_torch = 0.5f;
    private final float lightSoftnessLength_player = 0.5f;
    
    //private float stateTimeLight = 0;
    //private boolean doBrighter = true;
    
    public Shaders_box2dlights() {
        color = new Color(0, 0, 0, Constants.ALPHA_MAX);
        
      //RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(GameScreen.world);
        rayHandler.setAmbientLight(0f);
        rayHandler.setBlur(true);
        rayHandler.setShadows(true);
        
        //set degree to -89f or -91f to shows light (it seems like bug)
        //degree can be set to -90f when viewport is bigger - see bellow calling setCombinedMatrix
        sun =  new DirectionalLight(rayHandler, 2000, color, -90f);
        sun.setSoft(true);
        sun.setSoftnessLength(lightSoftnessLength_sun);
        sun.setStaticLight(false);
        sun.setContactFilter((short)1, (short)1, (short)Constants.BLOCK_BIT);
        
        torchLightList = new ArrayList<>();
    }
    
    public void updateSun(double currentHour){

        if (currentHour >= Constants.SUNRISE_HOUR && currentHour <= Constants.SUNRISE_HOUR + Constants.SUNRISE_DURATION)
        {
            color.a = map(  Constants.SUNRISE_HOUR, Constants.SUNRISE_HOUR + Constants.SUNRISE_DURATION, 
                                    Constants.ALPHA_MIN, Constants.ALPHA_MAX, 
                                    currentHour);
            sun.setColor(color);
        }


        if (currentHour >= Constants.SUNSET_HOUR && currentHour <= Constants.SUNSET_HOUR + Constants.SUNSET_DURATION)
        {
            color.a = map(  Constants.SUNSET_HOUR, Constants.SUNSET_HOUR + Constants.SUNSET_DURATION,
                            Constants.ALPHA_MAX, Constants.ALPHA_MIN, 
                            currentHour);
            
            sun.setColor(color);
        }
    }
    
    
    public void updateRayHandler(){
        
        /*
        changing of brightnes
        if (stateTimeLight > 0.5f)
            doBrighter = false;
        
        if (stateTimeLight < 0)
            doBrighter = true;
        
        
        if (stateTimeLight < 0.5f && doBrighter)
        {
            stateTimeLight += Gdx.graphics.getDeltaTime()*2;
        }
        else
        {
            stateTimeLight -= Gdx.graphics.getDeltaTime()*2;
        }
        
        System.err.println("light = " + (4+stateTimeLight));
        
        for (int i = 0; i < torchLightList.size(); i++) 
        {
            torchLightList.get(i).setDistance(4.5f + stateTimeLight);
        }
  */
        


        //DirectionalLight is limitated by screen size dependent values which are set or updated when setCombinedMatric is called
        //so we need to increase viewport height 
        //to keep blocks on top (above player - e.g when player mine big "well") included in shader calculation
        rayHandler.setCombinedMatrix(GameScreen.camera.combined,
                                     GameScreen.camera.position.x,
                                     GameScreen.camera.position.y,
                                     GameScreen.camera.viewportWidth * GameScreen.camera.zoom,
                                     GameScreen.camera.viewportHeight*8 * GameScreen.camera.zoom);
        /*System.out.println("GameScreen.camera.viewportWidth = " + GameScreen.camera.viewportWidth +
                            "GameScreen.camera.viewportHeight = " + GameScreen.camera.viewportHeight +
                            ", Zoom = " + GameScreen.camera.zoom);*/
        rayHandler.updateAndRender();
    }
    
    public void dispose(){
        sun.dispose();
        rayHandler.dispose();
    }
    
    
    private float map(double a1, double a2, double b1, double b2, double input){
        return (float) (b1 + ((input - a1)*(b2 - b1))/(a2 - a1));
    }
    
    public void lightTorch(Player player) {
        if(myLight == null){
            myLight = new PointLight(rayHandler, 100, Color.BLACK, 5, 10, 30);
            myLight.setSoftnessLength(lightSoftnessLength_player);
            myLight.setContactFilter((short)1, (short)1, (short)Constants.BLOCK_BIT);
            myLight.attachToBody(player.b2body, 0.5f, 0.5f);
        }
        else{
            myLight.setActive(true);
        }
    }

    public void lightTorchOff() {
       if(myLight != null){
           myLight.setActive(false);
       }
    }

    public void setTorchLight(float x, float y) {
        int i = torchLightList.size();
        
        x = centerPosOfBlock(x);
        y = centerPosOfBlock(y);
        
        //System.out.println("setTorchLight: index: " + i + ", x: " + x + ", y: " + y);
        
        torchLightList.add(i, new PointLight(rayHandler, 100, Color.BLACK, 5, x, y));
        torchLightList.get(i).setSoftnessLength(lightSoftnessLength_torch);
        torchLightList.get(i).setContactFilter((short)1, (short)1, (short)Constants.BLOCK_BIT);
    }

    public void removeTorchLightFromPos(float x, float y) {
        x = centerPosOfBlock(x);
        y = centerPosOfBlock(y);
        
        //System.out.println("removeTorchLightFromPos: x: " + x + ", y: " + y);
        for (int i = 0; i < torchLightList.size(); i++) 
        {
            if (torchLightList.get(i).getX() == x && torchLightList.get(i).getY() == y)
            {
                torchLightList.get(i).setActive(false);
                torchLightList.get(i).dispose();
                torchLightList.remove(i);
            }
        }
    }
    
    private float centerPosOfBlock(float c){
        return (int)(c*GameScreen.PPM/Block.size_in_pixels) * Block.size_in_meters + Block.size_in_meters/2.0f;
    }
    
}
