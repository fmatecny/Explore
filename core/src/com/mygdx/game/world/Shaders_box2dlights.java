/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
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
    
    private PointLight torchLight;
    private ArrayList<PointLight> torchLightList;
    
    private Color color;
    
    
    public Shaders_box2dlights() {
        color = new Color(0, 0, 0, Constants.ALPHA_MAX);
        
      //RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(GameScreen.world);
        rayHandler.setAmbientLight(0f);
        rayHandler.setBlur(true);
        rayHandler.setShadows(true);
        
        
        sun =  new DirectionalLight(rayHandler, 620, color, -89f);
        sun.setSoft(true);
        sun.setSoftnessLength(1f);
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
            
        
        /*if (currentHour >= 4 && currentHour <= 22)
            sun.setDirection((float)map(4, 22, -15, -165, currentHour));
        else if (currentHour > 22 && currentHour <= 24)
            sun.setDirection((float)map(22, 24, -15, -65, currentHour));
        else if (currentHour >= 0 && currentHour < 4)
            sun.setDirection((float)map(0, 4, -65, -165, currentHour));*/
           
        if (currentHour >= Constants.SUNSET_HOUR && currentHour <= Constants.SUNSET_HOUR + Constants.SUNSET_DURATION)
        {
            color.a = map(  Constants.SUNSET_HOUR, Constants.SUNSET_HOUR + Constants.SUNSET_DURATION,
                            Constants.ALPHA_MAX, Constants.ALPHA_MIN, 
                            currentHour);
            
            sun.setColor(color);
        }
    }
    
    
    public void updateRayHandler(){
        rayHandler.setCombinedMatrix(GameScreen.camera);
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
            myLight.setSoftnessLength(0.5f);
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
        torchLightList.get(i).setSoftnessLength(0.5f);
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
        return (int)(c*GameScreen.PPM/Block.size_in_pixels) * Block.size + Block.size/2.0f;
    }
    
}
