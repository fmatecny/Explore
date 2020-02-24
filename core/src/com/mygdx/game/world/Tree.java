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
public class Tree{

    private Block[][] tree;
    
    public Tree(int width, int height) {
        
        this.tree = new Block[width][height];
        createTrunk(width,height);
        createCrown(width,height);

    }

    private void createTrunk(int w, int h) {
        int middle = w/2 + w%2 - 1;
        
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h/2; j++) {
                if (i == middle)
                {
                    this.tree[i][j] = AllBlocks.wood;
                    this.tree[i][j].blocked = false;
                }
            }
            
        }
    }
    
    private void createCrown(int w, int h){
        int offset = 0;
        
        for (int j = h/2; j < h; j++, offset++) {
            for (int i = offset; i < w-offset; i++) {
                this.tree[i][j] = AllBlocks.leaf;
                this.tree[i][j].blocked = false;
            }
        }
    }
    
    

    public Block[][] getTree() {
        return this.tree;
    }
    
}
