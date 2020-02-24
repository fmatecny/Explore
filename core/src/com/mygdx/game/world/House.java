/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

/**
 *
 * @author Fery
 */
public class House {

    private Block[][] house;
    
    public House(int width, int height) {
        this.house = new Block[width][height];
        createFrontage(width,height);
        createRoof(width,height);
    }

    private void createFrontage(int w, int h) {
        for (int i = 1; i < w-1; i++) {
            for (int j = 0; j < h/2; j++) {
                    if (i == w/2 && j == 0){
                        this.house[i][j] = AllBlocks.door_down;
                    }
                    else if (i == w/2 && j == 1){
                        this.house[i][j] = AllBlocks.door_up;
                    }
                    else if ((i == w/2-2 || i == w/2+2) && j == 1){
                        this.house[i][j] = AllBlocks.window;
                    }
                    else{
                    this.house[i][j] = new Block(AllBlocks.stone);
                    this.house[i][j].blocked = false;}
            }
            
        }
    }

    private void createRoof(int w, int h) {
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
    
}

