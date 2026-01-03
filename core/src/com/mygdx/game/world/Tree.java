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
public class Tree{

    private Block[][] tree;
    private int rootXPos;
    private int plantXPos;
    private Block plant = null;
    
    public Tree(int width, int height, int x, int y) {
        
        this.tree = new Block[width][height];
        createTrunk(width,height, x, y);
        createCrown(width,height, x, y);

    }

    private void createTrunk(int w, int h, int x, int y) {
        rootXPos = w/2 + w%2 - 1;
        
        if ((int )(Math.random() * 100) > 50 )
            createPlant(w);
        
        for (int j = 0; j < h/2; j++)
        {
                this.tree[rootXPos][j] = new Block(AllBlocks.wood);
                this.tree[rootXPos][j].blocked = false;
        }
    }
    
    private void createCrown(int w, int h, int x, int y){
        int offset = 0;
        
        for (int j = h/2; j < h; j++, offset++) {
            for (int i = offset; i < w-offset; i++) {
                this.tree[i][j] = new Block(AllBlocks.leaf);
                this.tree[i][j].blocked = false;
            }
        }
    }
    
    private void createPlant(int w){
        plantXPos = (int )(Math.random() * w);
        if (plantXPos == rootXPos )
            plantXPos++;

        plant = new Block(((int )(Math.random() * 100) > 40 ) ? AllBlocks.blackMushroom : AllBlocks.amanitaMushroom);
        plant.blocked = false;
    }

    public int getRootXPos() {
        return rootXPos;
    }

    public Block[][] getTree() {
        return this.tree;
    }

    public int getPlantXPos() {
        return plantXPos;
    }

    public Block getPlant() {
        return plant;
    }
    
    public boolean hasPlant(){
        return plant != null;
    }
    
}
