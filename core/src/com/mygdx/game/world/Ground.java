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
public class Ground{
    
    private Block[][] groundBlocksArray;
    private Block[][] backgroundBlocksArray;
    private PerlinNoise2D perlinNoise2D;
    private int[] noiseArr;  
    
    public Ground(int width, int height) {
        
        this.groundBlocksArray = new Block[width][height];
        this.backgroundBlocksArray = new Block[width][height];
        this.perlinNoise2D = new PerlinNoise2D();
        noiseArr = perlinNoise2D.getNoiseArr(height);

        for (int i = 0; i < width; i++) 
        {
            for (int j = 0; j < height; j++) 
            {
                if (j == noiseArr[i/2]){
                    this.groundBlocksArray[i][j] = new Block(AllBlocks.ground);
                    this.backgroundBlocksArray[i][j] = AllBlocks.groundBck;
                }
                else if (j-1 == noiseArr[i/2]){
                    this.groundBlocksArray[i][j] = new Block(AllBlocks.grassy_ground);
                    //this.backgroundBlocksArray[i][j] = AllBlocks.groundBck;
                }
                else if (j < noiseArr[i/2]){
                    this.groundBlocksArray[i][j] = new Block(AllBlocks.stone);
                    this.backgroundBlocksArray[i][j] = AllBlocks.groundBck;
                }
                    
            }
            //System.out.println(noiseArr[i]);
        }
    }    

    public Block[][] getGroundBlocksArray() {
        return groundBlocksArray;
    }

    public Block[][] getBackgroundBlocksArray() {
        return backgroundBlocksArray;
    }
    

    
}
