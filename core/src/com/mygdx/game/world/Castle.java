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
    private IntVector2 kingPosition;
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
                    
                    //fill up empty space - backround will be added
                    if (castle[i][j] == null)
                        castle[i][j] = AllBlocks.empty;
                }//put torches over stairs
                else if((j == 6 && (i == castle.length/2 - 19 || i == castle.length/2 + 19))||
                        (j == 8 && (i == castle.length/2 - 16 || i == castle.length/2 + 16)) ||
                        (j == 10 && (i == castle.length/2 - 13 || i == castle.length/2 + 13)))
                {
                    castle[i][j] = new Block(AllBlocks.torch);
                }
            }
        }
        knightPositions.add(new IntVector2(castle.length/2-5+x, 7+y));
        knightPositions.add(new IntVector2(castle.length/2+5+x, 7+y));
        
        int throneWidth = 5;
        int throneHeight = 4;
        castle[castle.length/2-3][7+throneHeight] = new Block(AllBlocks.torch);
        castle[castle.length/2+3][7+throneHeight] = new Block(AllBlocks.torch);
        createThrone(castle.length/2-2, 7, throneWidth, throneHeight);
        kingPosition = new IntVector2(castle.length/2+x, 7+y);
        
        int towerWidth = 7;
        int towerHeigh = 4;
        int towerTopPadding = 4;
        
        //side towers
        createTower(0, castle[0].length-towerHeigh-towerTopPadding, towerWidth, towerHeigh);
        createTower(castle.length-towerWidth, castle[0].length-towerHeigh-towerTopPadding, towerWidth, towerHeigh);
        //connection of towers
        generateHorizontalLine(0, castle.length, castle[0].length-towerHeigh-towerTopPadding-1);
        //central tower
        towerHeigh += towerTopPadding;
        towerWidth += 2;
        createTower(castle.length/2-towerWidth/2, castle[0].length-towerHeigh, towerWidth, towerHeigh);

        //walls and torches above the entries
        generateVerticalLine(0, 5, castle[0].length-towerHeigh);
        castle[1][5] = new Block(AllBlocks.torch);
        generateVerticalLine(castle.length-1, 5, castle[0].length-towerHeigh);
        castle[castle.length-2][5] = new Block(AllBlocks.torch);
        
        //fill up empty space with background
        changeNullToEmptyblock(0, 0, castle.length-1, castle[0].length-towerHeigh);
    }
    
    private void createThrone(int x, int y, int w, int h){

        for (int i = 0; i < w; i++) 
        {
            for (int j = 0; j < h; j++) 
            {
                if (i == 0 || i == w-1)
                {
                    castle[x+i][y+j] = new Block(AllBlocks.half_plank);
                    if (i == 0)
                        castle[x+i][y+j].textureRotation = 90;
                    else
                        castle[x+i][y+j].textureRotation = 270;
                }
                else if(j < 1)
                    castle[x+i][y+j] = new Block(AllBlocks.wood);
                else if (j < h-1)
                    castle[x+i][y+j] = new Block(AllBlocks.plank);
                
                //fill up empty space - backround will be added
                if (castle[x+i][y+j] == null)
                    castle[x+i][y+j] = AllBlocks.empty;
                else
                    castle[x+i][y+j].blocked = false;
            }
        }
    }
    
    private void createTower(int x, int y, int w, int h){
        int moduloResult = 0;
        if(x>0)
        {
            if (x%2!=0)
                moduloResult = 1;
        }
        
        for (int i = x; i < x+w; i++) 
        {
            for (int j = y; j < y+h; j++) 
            {
                if ((j == y+h-1 && i%2 == moduloResult) || j < y+h-1)
                {
                    castle[i][j] = new Block(AllBlocks.stone);
                    castle[i][j].blocked = false;
                }
            } 
        }
    }
    
    private void generateHorizontalLine(int fromX, int toX, int y){
        for (int i = fromX; i < toX; i++) {
            if (castle[i][y] == null)
            {
                castle[i][y] = new Block(AllBlocks.stone);
                castle[i][y].blocked = true;
            }
        }
    }
    
    private void generateVerticalLine(int x, int fromY, int toY){
        for (int i = fromY; i < toY; i++) {
            if (castle[x][i] == null)
            {
                castle[x][i] = new Block(AllBlocks.stone);
                castle[x][i].blocked = true;
            }
        }
    }
    
    private void changeNullToEmptyblock(int fromX, int fromY, int toX, int toY){
        for (int x = fromX; x < toX; x++) {
            for (int y = fromY; y < toY; y++) {
                if (castle[x][y] == null)
                    castle[x][y] = AllBlocks.empty;
            }
        }
    }
    
    public Block[][] getCastle() {
        return castle;
    }

    public IntVector2 getKingPosition() {
        return kingPosition;
    }

    public ArrayList<IntVector2> getKnightPositions() {
        return knightPositions;
    }
    
}
