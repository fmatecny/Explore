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
public class Tree extends WorldObject{

    private Body[][] tree;
    
    public Tree(int width, int height, int x, int y) {
        
        this.tree = new Body[width][height];
        createTrunk(width,height, x, y);
        createCrown(width,height, x, y);

    }

    private void createTrunk(int w, int h, int x, int y) {
        int middle = w/2 + w%2 - 1;
        
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h/2; j++) {
                if (i == middle)
                {
                    this.tree[i][j] = createBodie(GameScreen.world, x+i-w/2, y+j, AllBlocks.wood, false);//AllBlocks.wood;
                }
            }
            
        }
    }
    
    private void createCrown(int w, int h, int x, int y){
        int offset = 0;
        
        for (int j = h/2; j < h; j++, offset++) {
            for (int i = offset; i < w-offset; i++) {
                this.tree[i][j] = createBodie(GameScreen.world, x+i-w/2, y+j, AllBlocks.leaf, false);//AllBlocks.leaf;
            }
        }
    }
    
    

    public Body[][] getTree() {
        return this.tree;
    }
    
}
