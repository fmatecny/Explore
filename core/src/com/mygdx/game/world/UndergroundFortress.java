/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.world;

/**
 *
 * @author Fery
 */
public class UndergroundFortress {

    private Block[][] undegroundFortress;
    private final int MAX_HALL_HEIGHT = 6;
    
    public UndergroundFortress(int width, int height, int[][] path) {
        this.undegroundFortress = new Block[width][height];
        createUF(path);
    }

    private void createUF(int[][] path) {
        
        int hallHeight = 2;
        //int hallCenter = 0;
        
        for (int i = 0; i < undegroundFortress.length; i++) 
        {
            //hallHeight = (int )(Math.random() * (MAX_HALL_HEIGHT-2) + 2);
            //hallCenter = undegroundFortress[i].length/2;
            /*System.err.println(path[i]-8 + "|" + ((path[i+undegroundFortress.length])-4));
            for (int j = path[i]-8; j < (path[i+undegroundFortress.length]-4) && j < undegroundFortress[i].length; j++)
            {
                undegroundFortress[i][j] = new Block(AllBlocks.leaf);
            }*/
            for (int j = 0; j < undegroundFortress[i].length; j++) {
                if (path[i][j] < 0)
                {
                    undegroundFortress[i][j] = null;//new Block(AllBlocks.stone);
                }
                else if (path[i][j] == 0)
                {
                    undegroundFortress[i][j] = null;
                }
                else
                {
                    undegroundFortress[i][j] = new Block(AllBlocks.unbreakable);
                }
            }
        } 
    }

    public Block[][] getUFArray() {
        return undegroundFortress;
    }
    
}
