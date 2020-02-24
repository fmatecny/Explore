/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import static java.lang.Math.abs;

/**
 *
 * @author Fery
 */
public class Map {
    
    private Block[][] blockArray;
    private Block[][] groundBckArr;
    
    private int width;
    private int height;
    
    private int groundIndexX = 0;
    private int groundIndexY = 0;

    public Map(int width, int height) {
        
        this.width = width;
        this.height = height;
        blockArray = new Block[width][height];        
        
        Ground ground = new Ground(width, height-10);
        Block[][] groundArr = ground.getBlockArray();
        groundBckArr = new Block[width][height];
       
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height-10; j++) {
                blockArray[i][j] = groundArr[i][j];
                groundBckArr[i][j] = ground.getBlockBckArray()[i][j];
            }
        } 
        
        groundIndexX = (int )(Math.random() * 10 + 10);
        
        while (groundIndexX < width-20) 
        {            
            for (int j = 0; j < height-10; j++) {
                if (groundArr[groundIndexX][j] == null)
                {
                    groundIndexY = j;
                    break;
                }
            }           
            
            int typeOfCreation = (int )(Math.random() * 100);
            if (typeOfCreation > 70) {
                createHouses(groundArr);
            }
            else{
                createTrees();
            }
        }        
    }
    
    private void createHouses(Block[][] groundArr) {
        int houseWidth = 0;
        int houseHeight = 0;
        int diff = 0;
        
        houseWidth = (int )(Math.random() * 2 + 9);

        
        for (int j = 0; j < height-10; j++) {
            if (groundArr[groundIndexX+houseWidth-1][j] == null)
            {
                diff = abs(j-groundIndexY);
                groundIndexY = (j+groundIndexY)/2;
                break;
            }
        }
        
        if (diff < 2)
        {
            houseHeight = (int )(Math.random() * 2 + 6);
            House house = new House(houseWidth,houseHeight);
            Block[][] houseArr = house.getHouse();

            for (int i = 0; i < houseWidth; i++) {
                for (int j = 0; j < houseHeight; j++) {
                    if (houseArr[i][j] != null){
                        blockArray[i+groundIndexX-1][j+groundIndexY] = houseArr[i][j];}
                }
            }
            groundIndexX += (int )(Math.random() * 15 + houseWidth);
        }
        

    }
    
    private void createTrees(){
        int treeWidth = 0;
        int treeHeight = 0;
        
        treeWidth = (int )(Math.random() * 5 + 4);
        if (treeWidth%2 == 0)
            treeWidth++;

        treeHeight = (int )(Math.random() * 5 + 5);
        Tree tree = new Tree(treeWidth,treeHeight);
        Block[][] treeArr = tree.getTree();

        for (int i = 0; i < treeWidth; i++) {
            for (int j = 0; j < treeHeight; j++) {
                if (treeArr[i][j] != null && (blockArray[i+groundIndexX-treeWidth/2][j+groundIndexY] == null)){
                    blockArray[i+groundIndexX-treeWidth/2][j+groundIndexY] = treeArr[i][j];}
            }
        }
        groundIndexX += (int )(Math.random() * 15 + treeWidth);
    
    }
    
    
    public Block getBlock(int x, int y){
        if ((y/Block.size) <= 0)
            y = Block.size;
        
        return blockArray[x/Block.size][y/Block.size];
    
    }
    
    public Block getBlockByIdx(IntVector2 v){
        return blockArray[v.X][v.Y];
    
    }

    public Block[][] getBlockArray() {
        return blockArray;
    }
    
    public void removeBlock(int x, int y){
        blockArray[x][y] = AllBlocks.empty;
    }
    
    public void removeBlock(IntVector2 v){
        blockArray[v.X][v.Y] = AllBlocks.empty;
    }
    
    public IntVector2 getBlockIdxFromMouseXY(int x, int y, IntVector2 cam){
        
        IntVector2 v = new IntVector2();
        
        v.X = (x+(cam.X-MyGdxGame.width/2))/Block.size;
        v.Y = (MyGdxGame.height-(y-(cam.Y-MyGdxGame.height/2)))/Block.size;
        
        return v;
    
    
    }
    
    public boolean isBlockEmpty(IntVector2 idx){
        Block b = getBlockByIdx(idx);
        
        return (b == null || b == AllBlocks.empty);
    
    }
    
    public void draw(Stage stage){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (blockArray[i][j] != AllBlocks.empty && blockArray[i][j] != null)
                    //stage.getBatch().draw( blockArray[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
                    drawWithRotation(stage, i, j);
                else if (groundBckArr[i][j] != AllBlocks.empty && groundBckArr[i][j] != null)
                    stage.getBatch().draw( groundBckArr[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
            }

        }
    }

    private void drawWithRotation(Stage stage, int i, int j) {
       stage.getBatch().draw(blockArray[i][j].texture, 
                            i*Block.size, j*Block.size, 
                            Block.size/2, Block.size/2, 
                            Block.size, Block.size, 
                            1.0f, 1.0f, 
                            blockArray[i][j].textureRotation, 
                            0, 0, 
                            blockArray[i][j].texture.getWidth(), blockArray[i][j].texture.getHeight(),  
                            false, false); 
    }
    
}
