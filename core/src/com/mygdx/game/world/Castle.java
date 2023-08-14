/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.IntVector2;
import com.sun.org.apache.bcel.internal.generic.BALOAD;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Castle {

    private Block[][] castle;
    private Vector2 kingPosition;
    private ArrayList<IntVector2> knightPositions;
    
    public Castle(int width, int height, int x, int y) {
        this.castle = new Block[width][height];
        knightPositions = new ArrayList<>();
        createCastle(x, y);
    }

    private void createCastle(int x, int y) {
        
        for (int i = 0; i < castle.length; i++) 
        {
            for (int j = 0; j < castle[i].length; j++) 
            {
                //floor
                if (j < 2)
                    castle[i][j] = new Block(AllBlocks.stone);
                else if(j < 7 && i > castle.length/2 - 20 + j && i < castle.length/2 + 20 - j)
                {
                    //stairs
                    if (i-1 == castle.length/2 - 20 + j || i+1 == castle.length/2 + 20 - j)
                    {
                        castle[i][j] = new Block(AllBlocks.stone_stairs);
                        if (i < castle.length/2)
                            castle[i][j].textureRotation = 90;
                        
                        castle[i][j].blocked = true;   
                    }
                    else if (j == 6)  //top platform  
                        castle[i][j] = new Block(AllBlocks.stone);
                    else if (j == 4 && (i == castle.length/2 - 2 || i == castle.length/2 + 2))
                        castle[i][j] = new Block(AllBlocks.torch);
                    else if (j == 2 && (i == castle.length/2 - 2 || i == castle.length/2 + 2))
                        castle[i][j] = new Block(AllBlocks.chest);
                }
                else if(i == 0 || i == castle.length-1)
                {
                    castle[i][j] = new Block(AllBlocks.stone);
                    castle[i][j].blocked = false;
                }
            }
        }
        knightPositions.add(new IntVector2(castle.length/2-5+x, 7+y));
        knightPositions.add(new IntVector2(castle.length/2+5+x, 7+y));
        createThrone(castle.length/2-2, 7);
        
        int towerWidth = 7;
        int towerHeigh = 5;
        createTower(0, castle[0].length-towerHeigh, towerWidth, towerHeigh);
        createTower(castle.length-towerWidth, castle[0].length-towerHeigh, towerWidth, towerHeigh);
        createTower(castle.length/2-(towerWidth+1)/2, castle[0].length-towerHeigh, towerWidth+2, towerHeigh);
    }
    
    private void createThrone(int x, int y){
        kingPosition = new Vector2(x+2, y);
        for (int i = 0; i < 4; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                if (i == 0 || i == 3)
                {
                    castle[x+i][y+j] = new Block(AllBlocks.half_plank);
                    if (i == 0)
                        castle[x+i][y+j].textureRotation = 90;
                    else
                        castle[x+i][y+j].textureRotation = 270;
                }
                else if(j < 1)
                    castle[x+i][y+j] = new Block(AllBlocks.wood);
                else if (j < 3)
                    castle[x+i][y+j] = new Block(AllBlocks.plank);
                
                if (castle[x+i][y+j] != null)
                    castle[x+i][y+j].blocked = false;
            }
        }
    }
    
    private void createTower(int x, int y, int w, int h){
        for (int i = x; i < x+w; i++) 
        {
            for (int j = y; j < y+h; j++) 
            {
                if ((j == y+h-1 && i%2 == 0) || j < y+h-1)
                {
                    castle[i][j] = new Block(AllBlocks.stone);
                    castle[i][j].blocked = false;
                }
            } 
        }
    }
    
    public Block[][] getCastle() {
        return castle;
    }

    public Vector2 getKingPosition() {
        return kingPosition;
    }

    public ArrayList<IntVector2> getKnightPositions() {
        return knightPositions;
    }
    
}
