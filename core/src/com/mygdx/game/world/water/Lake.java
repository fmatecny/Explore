/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.water;

import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.Block;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Lake {
    
    private ArrayList<Water> waterList = new ArrayList<>();

    public Lake(int xIdx, int yIdx, int width, int height) {
        
        int widthOfWater = width;
        
        int foo = height/(width/4);
        int i = 1;

        for (int y = height; y > 0; y--, i++) 
        { 
            Water water;
            if (y == height){
                water = new Water();
            }
            else{
                water = new Water(false, false);
                water.setDensity(0.8f);
            }
            
            water.createBody(GameScreen.world, 
                            xIdx*Block.size_in_meters + Block.size_in_meters*width/2.0f, 
                            yIdx*Block.size_in_meters - Block.size_in_meters*height + y*Block.size_in_meters - Block.size_in_meters/2.0f, 
                            widthOfWater*Block.size_in_meters, 
                            Block.size_in_meters); //world, x, y, width, height
            water.setX(xIdx + width/2 - widthOfWater/2);
            water.setY(yIdx-1 - height  + y);
            waterList.add(water);
            
            if (i == foo)
            {
                widthOfWater -= 2;  
                i  = 0;
            }
        }
        
        
        
    }
    
    
    public void draw(){
        for (Water water : waterList)
        {
            water.update();
            water.draw(GameScreen.camera);
        }
    
    }

    public ArrayList<Water> getWaterList() {
        return waterList;
    }
    
    
    

}
