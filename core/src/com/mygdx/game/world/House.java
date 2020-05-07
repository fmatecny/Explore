/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.GameScreen;

/**
 *
 * @author Fery
 */
public class House extends WorldObject{

    private Body[][] house;
    
    public House(int width, int height, int x, int y) {
        this.house = new Body[width][height];
        createFrontage(width,height,x,y);
        createRoof(width,height,x,y);
    }

    private void createFrontage(int w, int h, int x, int y) {
        for (int i = 1; i < w-1; i++) {
            for (int j = 0; j < h/2; j++) {
                    if (i == w/2 && j == 0){
                        this.house[i][j] = createBodie(GameScreen.world, x+i-1, y+j, AllBlocks.door_down, false);//AllBlocks.door_down;
                    }
                    else if (i == w/2 && j == 1){
                        this.house[i][j] = createBodie(GameScreen.world, x+i-1, y+j, AllBlocks.door_up, false);//AllBlocks.door_up;
                    }
                    else if ((i == w/2-2 || i == w/2+2) && j == 1){
                        this.house[i][j] = createBodie(GameScreen.world, x+i-1, y+j, AllBlocks.window, false);//AllBlocks.window;
                    }
                    else{
                        this.house[i][j] = createBodie(GameScreen.world, x+i-1, y+j, AllBlocks.stone, false);//new Block(AllBlocks.stone);
                   }
            }
            
        }
    }

    private void createRoof(int w, int h, int x, int y) {
        int offset = 0;
        
        for (int j = h/2; j < h; j++, offset++) {
            for (int i = offset; i < w-offset; i++) {
                this.house[i][j] = createBodie(GameScreen.world, x+i-1, y+j, AllBlocks.wood, false);//new Block(AllBlocks.wood);
            }
        }
    }
    
    public Body[][] getHouse() {
        return this.house;
    }
    
}

