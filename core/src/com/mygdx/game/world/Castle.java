/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.world;

import com.mygdx.game.IntVector2;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Castle {

    private Block[][] castle;
    private Block[][] castleBackground;
    private IntVector2 kingPosition;
    private ArrayList<IntVector2> knightPositions;
    
    public Castle(int width, int height, int x, int y) {
        this.castle = new Block[width][height];
        this.castleBackground = new Block[width][height];
        this.knightPositions = new ArrayList<>();
        createCastle(x, y);
    }

    private void createCastle(int x, int y) {
        
        int towerWidth = 7;
        int towerHeigh = 17;
        int towerTopPadding = 4;
                
        createDefenseTower(0, 0, towerWidth, towerHeigh);
        createDefenseTower(castle.length-towerWidth-1, 0, towerWidth, towerHeigh);
        knightPositions.add(new IntVector2(x, y));
        knightPositions.add(new IntVector2(x+castle.length-towerWidth-1, y));
        
        createKingHouse(castle.length/2-25, 0, 50, castle[0].length/2);
        kingPosition = new IntVector2(castle.length/2+x, y+2);
        
        knightPositions.add(new IntVector2(castle.length/2-5+x, y+1));
        knightPositions.add(new IntVector2(castle.length/2+5+x, y+1));
        
        //fill up empty space with background
        //changeNullToEmptyblock(0, 0, castle.length-1, castle[0].length-towerHeigh);
    }
    
    private void createThrone(int x, int y, int w, int h){

        for (int i = 0; i < w; i++) 
        {
            castleBackground[x+i][y] = AllBlocks.gravel;
            
            if (i == 0 || i == w-1)
            {
                castle[x+i][y] = new Block(AllBlocks.half_stone);
            }
            else
                castle[x+i][y] = new Block(AllBlocks.stone);
        }
        
        for (int i = 1; i < w-1; i++) 
        {
            if (i == 1 || i == w-2)
            {
                castle[x+i][y+1] = new Block(AllBlocks.half_plank);
                castle[x+i][y+1].textureRotation = (i == 1) ? 90 : 270;
            }
            else
                castle[x+i][y+1] = new Block(AllBlocks.plank);
            
            castle[x+i][y+1].blocked = false;
        }
        
        for (int i = 1; i < w-1; i++) 
        {
            if (i == 1 || i == w-2)
            {
                castle[x+i][y+2] = new Block(AllBlocks.half_plank);
                castle[x+i][y+2].textureRotation = (i == 1) ? 90 : 270;
            }
            else
                castle[x+i][y+2] = new Block(AllBlocks.wood);
            
            castle[x+i][y+2].blocked = false;
        }
        castle[x+2][y+3] = new Block(AllBlocks.wood);
        castle[x+2][y+3].blocked = false;
        castle[x+3][y+3] = new Block(AllBlocks.wood);
        castle[x+3][y+3].blocked = false;
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
    
    private void createDefenseTower(int x, int y, int w, int h){

        for (int i = 0; i < w; i++) 
        {
            for (int j = 0; j < h; j++) 
            {
                if ((j == h-1 && i%2 == 0) || // tower 
                     j == h-2 || // tower top floor
                    (j < h-2 && (i == 0 || i == w-1)))    // left and right side
                {
                    castle[i+x][j+y] = new Block(AllBlocks.stone);
                    castle[i+x][j+y].blocked = !(j == h-1 && i%2 == 0); //do not set tower as blocked - otherwise floor will be in the shadow (same as roof of houses is not blocked - must be visiable)
                }
                else if (j < h-1)
                {  
                    if (i == 1)
                    {
                        castle[i+x][j+y] = new Block(AllBlocks.ladder);
                        castle[i+x][j+y].blocked = false;
                    }

                    castleBackground[i+x][j+y] = AllBlocks.gravel;
                }
            } 
        }
        
        // Add door (2) on both the left and right sides at the bottom of the tower
        int doorStartLeftX = 0; // Left side
        int doorStartRightX = w - 1; // Right side
        int doorHeight = 3; // height of door is 3

        for (int j = 0; j < doorHeight; j++) 
        {
            castle[x+doorStartLeftX][y+j] = new Block(AllBlocks.plank);
            castle[x+doorStartRightX][y+j] = new Block(AllBlocks.plank);
            
            castle[x+doorStartLeftX][y+j].blocked = false;
            castle[x+doorStartRightX][y+j].blocked = false;
        }
        
        // add torchs next to the doors
        castle[x+doorStartLeftX+2][y+doorHeight] = new Block(AllBlocks.torch);
        castle[x+doorStartRightX-2][y+doorHeight] = new Block(AllBlocks.torch);
        
        // Add windows (3) in the middle of the tower width
        int windowHeight = 3;
        int windowStartX = w / 2; // Middle of the tower width

        // First window, 2 blocks from the bottom
        int firstWindowStartY = 4;
        int secondWindowStartY = firstWindowStartY + windowHeight + 3;
        for (int i = 0; i < windowHeight; i++) 
        {
            castleBackground[x+windowStartX][y+firstWindowStartY + i] = AllBlocks.window;
            castleBackground[x+windowStartX][y+secondWindowStartY + i] = AllBlocks.window;
        }      
    }
    
    private void createKingHouse(int x, int y, int w, int h) {
        int tableWidth = 6;
        int tableHeigh = 2;
        int offset = 0;
        
        // floor
        generateHorizontalLine(x+1, x+w-1, y, AllBlocks.plank);
        castle[x-1][y] = new Block(AllBlocks.stone_stairs);
        castle[x-1][y].textureRotation = 90;
        castle[x+w][y] = new Block(AllBlocks.stone_stairs);
        int floorHeigh = 1;
        
        // roof
        for (int j = h-h/3; j < h; j++, offset++) {
            for (int i = offset; i < w-offset; i++) {
                this.castle[i+x][j+y] = new Block(AllBlocks.wood);
                this.castle[i+x][j+y].blocked = false;
            }
        }
        for (int i = 0; i < w; i++) 
        {
            for (int j = 0; j < h-h/3; j++) 
            {
                if (j == h-h/3-1 )// bottom of the roof   
                {
                    castle[i+x][j+y] = new Block(AllBlocks.stone);
                    castle[i+x][j+y].blocked = true;
                }
                else if (i == 0 || i == w-1)  // left and right side
                {
                    if (j < 4 && j > 0)
                    {
                        castleBackground[i+x][j+y] = AllBlocks.plank;
                    }
                    else
                    {
                        castle[i+x][j+y] = new Block(AllBlocks.stone);
                        castle[i+x][j+y].blocked = true;
                    }
                }
                else
                {
                    castleBackground[i+x][j+y] = AllBlocks.gravel;
                }
            } 
        }
        
        int throneWidth = 6;
        int throneHeight = 4;
        castle[w/2-3+x][throneHeight+y+floorHeigh] = new Block(AllBlocks.torch);
        castle[w/2+3+x][throneHeight+y+floorHeigh] = new Block(AllBlocks.torch);
        createThrone(w/2-throneWidth/2+x, y+floorHeigh, throneWidth, throneHeight);
        castle[w/2-throneWidth/2+x-1][y+floorHeigh] = new Block(AllBlocks.chest);
        castle[w/2-throneWidth/2+x+throneWidth][y+floorHeigh] = new Block(AllBlocks.chest);
        
        
        for (int i = 5; i < w/2-throneWidth; i+=5) 
        {
            castle[i+x][throneHeight+floorHeigh+y] = new Block(AllBlocks.torch);
            castle[x+w-i][throneHeight+floorHeigh+y] = new Block(AllBlocks.torch);
        }
        
        // table on left and right side
        createTable(x+w/4, y+floorHeigh, tableWidth, tableHeigh);
        createTable(x+w-w/4-tableWidth, y+floorHeigh, tableWidth, tableHeigh);
        
    }
    
    private void createTable(int x, int y, int w, int h) {
        
        // table top
        for (int i = 0; i < w; i++) 
        {
            castle[i+x][y+h-1] = new Block(AllBlocks.half_plank);
            castle[i+x][y+h-1].blocked = false;
        }
        
        for (int i = 0; i < h-1; i++) 
        {
            // left leg
            castle[x+1][y+i] = new Block(AllBlocks.half_plank);
            castle[x+1][y+i].textureRotation = 270;
            castle[x+1][y+i].blocked = false;

            // right leg
            castle[x+w-2][y+i] = new Block(AllBlocks.half_plank);
            castle[x+w-2][y+i].textureRotation = 90;
            castle[x+w-2][y+i].blocked = false;
        }

    }
    
    private void generateHorizontalLine(int fromX, int toX, int y, Block b){
        for (int i = fromX; i < toX; i++) {
            if (castle[i][y] == null)
            {
                castle[i][y] = new Block(b);
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

    public Block[][] getCastleBackground() {
        return castleBackground;
    }
    
    public IntVector2 getKingPosition() {
        return kingPosition;
    }

    public ArrayList<IntVector2> getKnightPositions() {
        return knightPositions;
    }   


}
