/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.mygdx.game.IntVector2;

/**
 *
 * @author Fery
 */
public class House{

    private Block[][] house;
    private IntVector2 doorUpPos;
    
    public House(int width, int height, int x, int y) {
        this.house = new Block[width][height];
        createFrontage(width,height,x,y);
        createRoof(width,height,x,y);
    }

    private void createFrontage(int w, int h, int x, int y) {
        for (int i = 1; i < w-1; i++) {
            for (int j = 0; j < h/2; j++) {
                    if (i == w/2 && j == 0){
                        this.house[i][j] = new Block(AllBlocks.door_down);
                    }
                    else if (i == w/2 && j == 1){
                        this.house[i][j] = new Block(AllBlocks.door_up);
                        doorUpPos = new IntVector2(x+i-1, y+j);
                    }
                    else if ((i == w/2-2 || i == w/2+2) && j == 1){
                        this.house[i][j] = new Block(AllBlocks.window);
                    }
                    else{
                        this.house[i][j] = new Block(AllBlocks.rock);
                   }
                    
                   this.house[i][j].blocked = false;
            }
            
        }
    }

    private void createRoof(int w, int h, int x, int y) {
        int offset = 0;
        
        for (int j = h/2; j < h; j++, offset++) {
            for (int i = offset; i < w-offset; i++) {
                this.house[i][j] = new Block(AllBlocks.wood);
                this.house[i][j].blocked = false;
            }
        }
    }
    
    public Block[][] getHouse() {
        return this.house;
    }

    public IntVector2 getDoorUpPos() {
        return doorUpPos;
    }
    
}

