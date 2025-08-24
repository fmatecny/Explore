/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.world;

/**
 *
 * @author Fery
 */
public class Caves {

    private Block[][] cavesArray;
    private final int MAX_HALL_HEIGHT = 6;
    
    public Caves(int width, int height, int[][] noiseArr2d) {
        this.cavesArray = new Block[width][height];
        createCaves(noiseArr2d);
    }

    private void createCaves(int[][] NoiseArr2d) {
        
        for (int i = 0; i < cavesArray.length; i++) 
        {
            for (int j = 0; j < cavesArray[i].length; j++) 
            {
                if (NoiseArr2d[i][j] <= 0)
                {
                    cavesArray[i][j] = null;
                }
                else
                {
                    cavesArray[i][j] = new Block(AllBlocks.empty);
                }
            }
        } 
    }

    public Block[][] getCavesArray() {
        return cavesArray;
    }
    
}
