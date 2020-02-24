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
public class Ground {
    
    private Block[][] blockArray;
    private Block[][] blockBckArray;
    private PerlinNoise2D perlinNoise2D;
    private int[] noiseArr;  
    
    public Ground(int width, int height) {
        
        this.blockArray = new Block[width][height];
        this.blockBckArray = new Block[width][height];
        this.perlinNoise2D = new PerlinNoise2D();
        noiseArr = perlinNoise2D.getNoiseArr(height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (j == noiseArr[i/2]){
                    this.blockArray[i][j] = new Block(AllBlocks.ground); // new Block(Block.t.ground);
                    this.blockBckArray[i][j] = AllBlocks.groundBck;
                }
                else if (j-1 == noiseArr[i/2]){
                    this.blockArray[i][j] = new Block(AllBlocks.grassy_ground);//new Block(Block.t.grassy_ground);
                    //this.blockBckArray[i][j] = AllBlocks.groundBck;
                }
                else if (j < noiseArr[i/2]){
                    this.blockArray[i][j] = new Block(AllBlocks.stone);//new Block(Block.t.stone);
                    this.blockBckArray[i][j] = AllBlocks.groundBck;
                }
                    
            }
            //System.out.println(noiseArr[i]);
        }
    }    
    
    public Block[][] getBlockArray() {
        return this.blockArray;
    }

    public Block[][] getBlockBckArray() {
        return blockBckArray;
    }
    
}
